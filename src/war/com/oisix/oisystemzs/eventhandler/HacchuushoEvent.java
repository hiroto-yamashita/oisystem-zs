package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.OfficeData;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.ejb.HacchuuPK;
import com.oisix.oisystemzs.ejb.HacchuuLocal;
import com.oisix.oisystemzs.ejb.HacchuuLocalHome;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiPK;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.pdf.HacchuushoPdf;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.naming.NamingException;
import javax.ejb.FinderException;


public class HacchuushoEvent extends TransactionEvent {

    private String filename;
    private String[] hacchuu_ids;
    private UserLocal user;
    private String name1;
    private OfficeData office;
    private int result = 0;
    private String errormsg;

    public String getFileName() { return filename; }
    public int getResult() { return result; }
    public String getErrormsg() { return errormsg; }

    public void init(HttpServletRequest request) {
        hacchuu_ids = request.getParameterValues("id");
        HttpSession session = request.getSession();
        user = (UserLocal)session.getAttribute("USER");
        name1 = user.getName1();
        office = (OfficeData)session.getAttribute("OFFICE");

        HacchuushoPdf hp = new HacchuushoPdf();
        LinkedList hacchuulist = new LinkedList();

        makeData(hacchuulist);
        hp.setData(hacchuulist);

        try {
            hp.makeDocument();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        filename = hp.getFileName();

    }

    public void handleEvent(HashMap attr) {
        for (int i=0; i<hacchuu_ids.length; i++) {
            try {
                HacchuuLocalHome hh = (HacchuuLocalHome)ServiceLocator.
                  getLocalHome("java:comp/env/ejb/HacchuuLocal");
                int hacchuu_id = 0;
                try {
                    hacchuu_id = Integer.parseInt(hacchuu_ids[i]);
                } catch (NumberFormatException nfe) {
                    //とりあえず無視
                    nfe.printStackTrace();
                }
                HacchuuPK hpk = new HacchuuPK(hacchuu_id);
                HacchuuLocal hacchuu = hh.findByPrimaryKey(hpk);
                Collection meisai = hacchuu.getNyuukayoteimeisai();
                Iterator meisaiiter = meisai.iterator();
                while (meisaiiter.hasNext()) {
                    NyuukayoteimeisaiLocal nym = (NyuukayoteimeisaiLocal)
                      meisaiiter.next();
                    nym.setStatus(20); // 出力済み
                }
            } catch (NamingException ne) {
                Debug.println(ne);
                result = RC_INPUTERROR;
                errorlist.add("システムエラー・NamingException");
                setRollbackOnly();
                return;
            } catch (FinderException fe) {
                Debug.println(fe);
                errorlist.add("システムエラー・FinderException");
                setRollbackOnly();
                return;
            }
        }
    }

    private void makeData(LinkedList hacchuulist) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<hacchuu_ids.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(hacchuu_ids[i]);
        }
        String hacchuu_idlist = sb.toString();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();
            String sql1 =
              "SELECT t1.hacchuu_bg, " +
              "t1.bikou, " +
              "t2.name, " + //仕入先
              "t2.tel, " +
              "t2.fax, " +
              "t3.name, " + //納品先
              "t3.yuubin, " +
              "t3.addr, " +
              "t3.tel, " +
              "t3.fax, " +
              "t1.format " +
              "FROM zt_hacchuu t1, zm_shiiresaki t2, zm_nouhinsaki t3 " +
              "WHERE t1.shiiresaki_id = t2.shiiresaki_id AND " +
              "t1.nouhinsaki_id = t3.nouhinsaki_id AND " +
              "t1.hacchuu_id IN (" + hacchuu_idlist + ")";
            String sql2 =
              "SELECT t1.hacchuu_bg, " +
              "t3.hacchuu_bg, " +
              "t2.shouhin_id, " +
              "t2.hacchuushouhin1, " +
              "t2.hacchuushouhin2, " +
              "t2.hacchuushouhin3, " +
              "t2.hacchuukikaku, " +
              "t2.irisuu, " +
              "t3.hacchuutanka, " +
              "t3.hacchuusuuryou, " +
              "t3.hacchuutani, " + 
              "t3.nyuukasuuryou, " +
              "t3.nyuukayotei_date, " +
              "t3.touchakujikan " +
              "FROM zt_hacchuu t1, zm_shouhin t2, zt_nyuukayoteimeisai t3 " +
              "WHERE t1.hacchuu_bg = t3.hacchuu_bg AND " +
              "t3.shouhin_id = t2.shouhin_id AND " +
              "t1.hacchuu_id IN (" + hacchuu_idlist + ") AND " +
              "t1.sakujo_flg=0 ORDER BY t2.shouhin_id";

            ps = con.prepareStatement(sql2);
            rs = ps.executeQuery();
            if (rs == null) {
                result = 1;
                errormsg = "システムエラー・sql2-rs is null";
                return;
            }
            HashMap meisailist = new HashMap();
            while (rs.next()) {
                int index = 1;
                String hacchuu_bg = rs.getString(index++);
                String mhacchuu_bg = rs.getString(index++);
                String shouhin_id = rs.getString(index++);
                String hacchuushouhin1 = rs.getString(index++);
                String hacchuushouhin2 = rs.getString(index++);
                String hacchuushouhin3 = rs.getString(index++);
                String hacchuukikaku = rs.getString(index++);
                //int irisuu = rs.getInt(index++);
                float irisuu = rs.getFloat(index++);
                float hacchuutanka = rs.getFloat(index++);
                float hacchuusuuryou = rs.getFloat(index++);
                String hacchuutani = rs.getString(index++);
                float nyuukasuuryou = rs.getFloat(index++);
                java.sql.Date nyuukayotei_date = rs.getDate(index++);
                int touchakujikan = rs.getInt(index++);
                TreeMap meisai = (TreeMap)meisailist.get(hacchuu_bg);
                if (meisai == null) {
                    meisai = new TreeMap();
                    meisailist.put(hacchuu_bg, meisai);
                }
                TreeMap datemeisai = (TreeMap)meisai.get(shouhin_id);
                if (datemeisai == null) {
                    datemeisai = new TreeMap();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(nyuukayotei_date);
                    while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        cal.add(Calendar.DATE, -1);
                    }
                    java.sql.Date startdate = new java.sql.Date(
                      cal.getTimeInMillis());
                    for (int i=0; i<7; i++) {
                        java.sql.Date date =  new java.sql.Date(
                          cal.getTimeInMillis());
                        datemeisai.put(date, null);
                        cal.add(Calendar.DATE, 1);
                    }
                    java.sql.Date enddate = new java.sql.Date(
                      cal.getTimeInMillis());
                    meisai.put(shouhin_id, datemeisai);
                }
                HacchuushoMeisai m = (HacchuushoMeisai)datemeisai.get(
                  nyuukayotei_date);
                if (m == null) {
                    m = new HacchuushoMeisai();
                    m.setHacchuu_bg(hacchuu_bg);
                    m.setMhacchuu_bg(mhacchuu_bg);
                    m.setShouhin_id(shouhin_id);
                    m.setHacchuushouhin1(hacchuushouhin1);
                    m.setHacchuushouhin2(hacchuushouhin2);
                    m.setHacchuushouhin3(hacchuushouhin3);
                    m.setHacchuukikaku(hacchuukikaku);
                    m.setIrisuu(irisuu);
                    m.setHacchuutanka(hacchuutanka);
                    m.setHacchuusuuryou(hacchuusuuryou);
                    m.setHacchuutani(hacchuutani);
                    m.setNyuukasuuryou(nyuukasuuryou);
                    m.setNyuukayotei_date(nyuukayotei_date);
                    m.setTouchakujikan(touchakujikan);
                    datemeisai.put(nyuukayotei_date, m);
                } else {
                    //同じ発注に同一商品コード、同一納品日があった場合
                    float hacchuukingaku =
                      m.getHacchuusuuryou() * m.getHacchuutanka();
                    hacchuukingaku += hacchuusuuryou * hacchuutanka;
                    float tanka = hacchuukingaku / hacchuusuuryou;
                    m.setHacchuusuuryou(m.getHacchuusuuryou() + 
                      hacchuusuuryou);
                    m.setHacchuutanka(tanka);
                    m.setNyuukasuuryou(m.getNyuukasuuryou() + 
                      nyuukasuuryou);
                    //到着時間、単位、入り数などはどうなるかよくわからない
                }
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement(sql1);
            rs = ps.executeQuery();
            if (rs == null) {
                result = 1;
                errormsg = "システムエラー・sql1-rs is null";
                return;
            }

            while (rs.next()) {
                int index = 1;
                String hacchuu_bg = rs.getString(index++);
                String bikou = rs.getString(index++);
                String shiiresaki = rs.getString(index++);
                String shiiretel = rs.getString(index++);
                String shiirefax = rs.getString(index++);
                String nouhinsaki = rs.getString(index++);
                String nouhinyuubin = rs.getString(index++);
                String nouhinaddr = rs.getString(index++);
                String nouhintel = rs.getString(index++);
                String nouhinfax = rs.getString(index++);
                int format = rs.getInt(index++);
                HacchuushoHeader h = new HacchuushoHeader();
                h.setHacchuu_bg(hacchuu_bg);
                h.setBikou(bikou);
                h.setShiiresaki(shiiresaki);
                h.setShiiretel(shiiretel);
                h.setShiirefax(shiirefax);
                h.setNouhinsaki(nouhinsaki);
                h.setNouhinyuubin(nouhinyuubin);
                h.setNouhinaddr(nouhinaddr);
                h.setNouhintel(nouhintel);
                h.setNouhinfax(nouhinfax);
                h.setTantousha(name1);
                h.setFormat(format);
                TreeMap meisai = (TreeMap)meisailist.get(hacchuu_bg);
                h.setMeisai(meisai);
                h.setOfficeData(office);
                if (format == 2) {
                    setOtherHacchuu(hacchuu_bg, meisai, con);
                }
                hacchuulist.add(h);
            }
        } catch (NamingException ne) {
            ne.printStackTrace();
            result = 1;
            errormsg = "システムエラー・NamingException";
        } catch (SQLException se) {
            se.printStackTrace();
            result = 1;
            errormsg = "システムエラー・SQLException";
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (ps != null) { ps.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException se) { se.printStackTrace(); }
        }
    }

    private void setOtherHacchuu (
      String ahacchuu_bg, TreeMap meisai, Connection con) throws SQLException {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            String sql3 = 
              "SELECT t1.hacchuu_bg, " +
              "t3.hacchuu_bg, " +
              "t2.shouhin_id, " +
              "t2.hacchuushouhin1, " +
              "t2.hacchuushouhin2, " +
              "t2.hacchuushouhin3, " +
              "t2.hacchuukikaku, " +
              "t2.irisuu, " +
              "t3.hacchuutanka, " +
              "t3.hacchuusuuryou, " +
              "t3.hacchuutani, " + 
              "t3.nyuukasuuryou, " +
              "t3.nyuukayotei_date, " +
              "t3.touchakujikan " +
              "FROM zt_hacchuu t1, zm_shouhin t2, zt_nyuukayoteimeisai t3 " +
              "WHERE t1.hacchuu_bg = t3.hacchuu_bg AND " +
              "t3.shouhin_id = t2.shouhin_id AND " +
              "t3.shouhin_id IN (?) AND " +
              "t3.nyuukayotei_date >= ? AND " +
              "(t3.nyuukayotei_date-1) < ? AND " +
              "t3.hacchuu_bg <> ? ";
            ps2 = con.prepareStatement(sql3);
            Set mkeys = meisai.keySet();
            Iterator miter = mkeys.iterator();
            StringBuffer sid = new StringBuffer();
            boolean first = true;
            TreeMap datemeisai = null;
            while (miter.hasNext()) {
                String shouhin_id = (String)miter.next();
                if (!first) {
                    first = false;
                    sid.append(",");
                }
                sid.append(shouhin_id);
                datemeisai = (TreeMap)meisai.get(shouhin_id);
                //1週間の抽出条件を最後の行から取っているので
                //1発注に週をまたがる納品日があるとおかしくなる
            }
            int index = 1;
            ps2.setString(index++, sid.toString());
            ps2.setDate(index++, (java.sql.Date)datemeisai.firstKey());
            ps2.setDate(index++, (java.sql.Date)datemeisai.lastKey());
            ps2.setString(index++, ahacchuu_bg);
            rs2 = ps2.executeQuery();
            while (rs2.next()) {
                index = 1;
                String hacchuu_bg = rs2.getString(index++);
                String mhacchuu_bg = rs2.getString(index++);
                String shouhin_id = rs2.getString(index++);
                String hacchuushouhin1 = rs2.getString(index++);
                String hacchuushouhin2 = rs2.getString(index++);
                String hacchuushouhin3 = rs2.getString(index++);
                String hacchuukikaku = rs2.getString(index++);
                int irisuu = rs2.getInt(index++);
                float hacchuutanka = rs2.getFloat(index++);
                float hacchuusuuryou = rs2.getFloat(index++);
                String hacchuutani = rs2.getString(index++);
                float nyuukasuuryou = rs2.getFloat(index++);
                java.sql.Date nyuukayotei_date = rs2.getDate(index++);
                int touchakujikan = rs2.getInt(index++);
                datemeisai = (TreeMap)meisai.get(shouhin_id);
                HacchuushoMeisai m = (HacchuushoMeisai)datemeisai.get(
                  nyuukayotei_date);
                if (m == null) {
                    m = new HacchuushoMeisai();
                    m.setHacchuu_bg(hacchuu_bg);
                    m.setMhacchuu_bg(mhacchuu_bg);
                    m.setShouhin_id(shouhin_id);
                    m.setHacchuushouhin1(hacchuushouhin1);
                    m.setHacchuushouhin2(hacchuushouhin2);
                    m.setHacchuushouhin3(hacchuushouhin3);
                    m.setHacchuukikaku(hacchuukikaku);
                    m.setIrisuu(irisuu);
                    m.setHacchuutanka(hacchuutanka);
                    m.setHacchuusuuryou(hacchuusuuryou);
                    m.setHacchuutani(hacchuutani);
                    m.setNyuukasuuryou(nyuukasuuryou);
                    m.setNyuukayotei_date(nyuukayotei_date);
                    m.setTouchakujikan(touchakujikan);
                    datemeisai.put(nyuukayotei_date, m);
                } else {
                    //同一商品コード、同一納品日があった場合
                    float hacchuukingaku =
                      m.getHacchuusuuryou() * m.getHacchuutanka();
                    hacchuukingaku += hacchuusuuryou * hacchuutanka;
                    float tanka = hacchuukingaku / hacchuusuuryou;
                    m.setHacchuusuuryou(m.getHacchuusuuryou() + 
                      hacchuusuuryou);
                    m.setHacchuutanka(tanka);
                    m.setNyuukasuuryou(m.getNyuukasuuryou() + 
                      nyuukasuuryou);
                    //到着時間、単位、入り数などはどうなるかよくわからない
                }
            }
            rs2.close();
            ps2.close();
        } catch (SQLException se) {
            throw se;
        } finally {
            try {
                if (rs2 != null) { rs2.close(); }
                if (ps2 != null) { ps2.close(); }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
