package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.EventHandlerSupport;
import com.oisix.oisystemfr.ServiceLocator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.oisix.oisystemzs.ejb.ShukkayoteiPK;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocalHome;
import com.oisix.oisystemzs.ejb.ShukkayoteiData;
import com.oisix.oisystemzs.ejb.SoukoData;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class ShukkokakuteiinputEvent extends EventHandlerSupport {

    private int shukkayotei_id;
    private boolean isFirst = false;
    private LinkedList errors = new LinkedList();
    private LinkedList errorids = new LinkedList();

    private ShukkayoteiData shukkayotei;
    private Collection shukkayoteimeisai;

    private final int HYOUKA_IDOUHEIKINTANKA = 1;
    private final int HYOUKA_HYOUJUNTANKA = 2;

    private int shukko_year;
    private int shukko_month;
    private int shukko_date;
    private java.util.Date shukkobi;

    private SoukoData souko;

    public boolean getIsFirst() { return isFirst; }
    public LinkedList getErrors() { return errors; }
    public LinkedList getErrorids() { return errorids; }

    public ShukkayoteiData getShukkayotei() { return shukkayotei; }
    public Collection getShukkayoteimeisai() { return shukkayoteimeisai; }

    public int getShukko_year() { return shukko_year; }
    public int getShukko_month() { return shukko_month; }
    public int getShukko_date() { return shukko_date; }
    public java.util.Date getShukkobi() { return shukkobi; }

    public void init(HttpServletRequest request) {
        String idstr = request.getParameter("id");
        if (idstr == null) {
            idstr = (String)session.getAttribute("#shukkayotei_id");
        }
        try {
            shukkayotei_id = Integer.parseInt(idstr);
        } catch (NumberFormatException nfe) {
            isFirst = true;
            return;
        }

        String yearstr = request.getParameter("#shukko_year");
        String monthstr = request.getParameter("#shukko_month");
        String datestr = request.getParameter("#shukko_date");
        if (yearstr == null) {
            yearstr = (String)session.getAttribute("#shukko_year");
        }
        if (monthstr == null) {
            monthstr = (String)session.getAttribute("#shukko_month");
        }
        if (datestr == null) {
            datestr = (String)session.getAttribute("#shukko_date");
        }
        // 出庫日が入力されていない場合
        if (yearstr == null || monthstr == null || datestr == null) {
            shukkobi = (java.util.Date)session.getAttribute("SHUKKO_DATE");
            if (shukkobi == null) {
                isFirst = true;
            } else {
                SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
                SimpleDateFormat monthsdf = new SimpleDateFormat("M");
                SimpleDateFormat datesdf = new SimpleDateFormat("d");
                yearstr = yearsdf.format(shukkobi);
                monthstr = monthsdf.format(shukkobi);
                datestr = datesdf.format(shukkobi);
                try {
                    shukko_year = Integer.parseInt(yearstr);
                    shukko_month = Integer.parseInt(monthstr);
                    shukko_date = Integer.parseInt(datestr);
                } catch (NumberFormatException nfe) {
                    errors.add("出庫日が不正です。");
                    isFirst = true;
                    return;
                }
            }
        } else {
            try {
                shukko_year = Integer.parseInt(yearstr);
                shukko_month = Integer.parseInt(monthstr);
                shukko_date = Integer.parseInt(datestr);
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(shukko_year, shukko_month - 1, shukko_date);
                shukkobi = cal.getTime();
            } catch (NumberFormatException nfe) {
                isFirst = true;
                return;
            }
        }

        souko = (SoukoData)session.getAttribute("SOUKO");
    }

    public void handleEvent(HashMap attr) {
        Debug.println("handleEvent start", this);

        // 出荷予定データの取得
        try {
            ShukkayoteiPK pk = new ShukkayoteiPK(shukkayotei_id);
            ShukkayoteiLocalHome hclh = (ShukkayoteiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ShukkayoteiLocal");
            ShukkayoteiLocal syl = hclh.findByPrimaryKey(pk);
            shukkayotei = syl.getShukkayoteiData();
        } catch (NamingException ne) {
            ne.printStackTrace();
            return;
        } catch (FinderException fe) {
            fe.printStackTrace();
            return;
        }

        if (isFirst) { return; }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement zaikops = null;
        ResultSet zaikors = null;
        try {
            //Context ic = new InitialContext();
            //DataSource ds =
            //  (DataSource)ic.lookup("java:comp/env/oisystemPool");
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();

            String sql = makeSql();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs == null) {
                Debug.println("rs null", this);
                return;
            }
            shukkayoteimeisai = new LinkedList();
            HashMap zaikomap = new HashMap();
            while (rs.next()) {
                HashMap item = new HashMap();
                int index = 1;
                item.put("MEISAI_ID", new Integer(rs.getInt(index++)));
                item.put("SHOUHIN_ID", rs.getString(index++));
                item.put("SHOUHINMEI", rs.getString(index++));
                item.put("KIKAKU", rs.getString(index++));
                int hyoukahouhou = rs.getInt(index++);
                float hyoujuntanka = rs.getFloat(index++);
                item.put("TANI", rs.getString(index++));
                float yoteisuu = rs.getFloat(index++);
                float jitsusuu = rs.getFloat(index++);
                float jitsuyoteisuu = yoteisuu - jitsusuu;
                float hyoujunbaika = rs.getFloat(index++);
                item.put("HYOUJUNBAIKA", new Float(hyoujunbaika));

                String shouhin_id = (String)item.get("SHOUHIN_ID");

                LinkedList motozaikolist =
                  (LinkedList)zaikomap.get(shouhin_id);
                if (motozaikolist == null) {
                    motozaikolist = new LinkedList();
                    String zaikosql = makeZaikoSql(shouhin_id);
                    zaikops = con.prepareStatement(zaikosql);
                    zaikors = zaikops.executeQuery();
                    if (zaikors == null) {
                        item.put("ZAIKOMEISAI", motozaikolist);
                        shukkayoteimeisai.add(item);
                        continue;
                    }
                    while (zaikors.next()) {
                        Motozaiko moto = new Motozaiko();
                        int zindex = 1;
                        moto.tanka = zaikors.getFloat(zindex++);
                        moto.shoumikigen = zaikors.getDate(zindex++);
                        moto.meisaisuu = zaikors.getFloat(zindex++);
                        float zaikosuu = zaikors.getFloat(zindex++);
                        moto.kingaku = zaikors.getFloat(zindex++);
                        if (moto.shoumikigen == null) {
                            moto.meisaisuu = zaikosuu;
                        }
                        motozaikolist.add(moto);
                    }
                    zaikors.close();
                    zaikops.close();
                    zaikomap.put(shouhin_id, motozaikolist);
                }

                LinkedList zaikolist = new LinkedList();
                Iterator motozaikoiter = motozaikolist.iterator();
                float genzaikosuu = 0;
                float genzaikokingaku = 0;
                while (motozaikoiter.hasNext()) {
                    Motozaiko moto = (Motozaiko)motozaikoiter.next();
                    genzaikosuu += moto.meisaisuu;
                    genzaikokingaku = moto.kingaku;
                }
                motozaikoiter = motozaikolist.iterator();
                while (motozaikoiter.hasNext()) {
                    HashMap zitem = new HashMap();
                    Motozaiko moto = (Motozaiko)motozaikoiter.next();
                    float shukkasuuryou = 0;
                    // 実出荷数のほうが明細数より多い場合、
                    // この在庫はすべて出し切る。
                    if (jitsuyoteisuu >= moto.meisaisuu) {
                        shukkasuuryou = moto.meisaisuu;
                    } else {
                        shukkasuuryou = jitsuyoteisuu;
                    }
                    jitsuyoteisuu -= shukkasuuryou;
                    genzaikosuu -= shukkasuuryou;
                    // 在庫数が0以下になった場合エラー
                    //if (genzaikosuu < 0) {
                    if (genzaikosuu < -0.1f) {
                        String err = "商品コード" +
                          (String)item.get("SHOUHIN_ID") +
                          "の商品の在庫が足りません。";
                        errors.add(err);
                        errorids.add(item.get("SHOUHIN_ID"));
                    }
                    float kingaku = 0;
                    float tanka = 0;
                    // 現在庫数が0の場合、金額は現在庫金額
                    //if (genzaikosuu == 0) {
                    if (genzaikosuu <= 0) {
                        kingaku = genzaikokingaku;
                        tanka = kingaku / shukkasuuryou;
                    } else {
                        // 評価方法によって単価の決定
                        if (hyoukahouhou == HYOUKA_IDOUHEIKINTANKA) {
                            tanka = moto.tanka;
                        } else if (hyoukahouhou == HYOUKA_HYOUJUNTANKA) {
                            tanka = hyoujuntanka;
                        }
                        kingaku = shukkasuuryou * tanka;
                        genzaikokingaku -= kingaku;
                    }
                    // 在庫金額が0以下になった場合エラー
                    //if (genzaikokingaku < 0) {
                    if (genzaikokingaku < -0.1) {
                        String err = "商品コード" +
                          (String)item.get("SHOUHIN_ID") +
                          "の商品の在庫金額が負になってしまいます。";
                        errors.add(err);
                        errorids.add(item.get("SHOUHIN_ID"));
                    }
                    zitem.put("TANKA", new Float(tanka));
                    zitem.put("KINGAKU", new Float(kingaku));
                    zitem.put("SHOUMIKIGEN", moto.shoumikigen);
                    zitem.put("MEISAISUU", new Float(moto.meisaisuu));
                    zitem.put("SHUKKASUU", new Float(shukkasuuryou));
                    zaikolist.add(zitem);
                    moto.meisaisuu -= shukkasuuryou;
                }
                // 出荷予定がまだ残ってる場合エラー（在庫が足りない）
                if (jitsuyoteisuu > 0) {
                    String err = "商品コード" +
                      (String)item.get("SHOUHIN_ID") +
                      "の商品の在庫が足りません。";
                    errors.add(err);
                    errorids.add(item.get("SHOUHIN_ID"));
                }

                item.put("ZAIKOMEISAI", zaikolist);
                shukkayoteimeisai.add(item);
            }
        } catch (NamingException nme) {
            Debug.println(nme);
        } catch (SQLException sqle) {
            Debug.println(sqle);
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (ps != null) { ps.close(); }
                if (zaikors != null) { zaikors.close(); }
                if (zaikops != null) { zaikops.close(); }
                if (con != null) { con.close(); }
            }
            catch (SQLException sqle) { Debug.println(sqle); }
        }
    }

    public String makeSql() {
        String yotei_bg = shukkayotei.getShukkayotei_bg();
        String sql =
          "SELECT " +
          "t1.SHUKKAYOTEIMEISAI_ID, " +
          "m1.SHOUHIN_ID, " +
          "m1.SHOUHIN, " +
          "m1.KIKAKU, " +
          "m1.ZAIKOHYOUKAHOUHOU, " +
          "m1.HYOUJUNTANKA, " +
          "t1.TANI, " +
          "t1.SHUKKAYOTEISUURYOU, " +
          "t1.JITSUSHUKKASUURYOU, " +
          "t1.HYOUJUNBAIKA " +
          "FROM ZM_SHOUHIN m1, ZT_SHUKKAYOTEIMEISAI t1 " +
          "WHERE t1.SHUKKAYOTEI_BG = '" + yotei_bg + "' " +
          "AND t1.SHOUHIN_ID = m1.SHOUHIN_ID " +
          "ORDER BY m1.SHOUHIN_ID ";
        return sql;
    }

    public String makeZaikoSql(String shouhin_id) {
        String souko_id = souko.getSouko_id();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(shukkobi);
        cal.add(Calendar.DATE, 1);
        String shukkobistr = sdf.format(cal.getTime());
        String sql =
          "SELECT " +
          "t1.TANKA, " +
          "t2.SHOUMIKIGEN, " +
          "t2.SUURYOU, " +
          "t1.SUURYOU, " +
          "t1.KINGAKU " +
          "FROM ZT_ZAIKO t1 LEFT OUTER JOIN ZT_ZAIKOMEISAI t2 " + 
          "ON t2.ZAIKO_ID=t1.ZAIKO_ID, " +
          "(SELECT " +
            "MAX(t3.ZORDER) AS MZORDER " +
            "FROM ZT_ZAIKO t3 " +
            "WHERE t3.SHOUHIN_ID = '"+shouhin_id+"' " +
            "AND t3.SOUKO_ID = '"+souko_id+"' " +
            "AND t3.ZAIKODATE <= '" + shukkobistr + "' " +
            "GROUP BY t3.SHOUHIN_ID, t3.SOUKO_ID) t4 " +
          "WHERE t1.SHOUHIN_ID = '"+shouhin_id+"' " +
          "AND t1.SOUKO_ID = '"+souko_id+"' " +
          "AND t2.SUURYOU > 0 " +
          "AND t1.ZORDER = t4.MZORDER " +
          "ORDER BY t2.SHOUMIKIGEN " +
          "";
        return sql;
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(
          "#shukkayotei_id", String.valueOf(shukkayotei_id));
    }

    class Motozaiko {
        float tanka;
        java.util.Date shoumikigen;
        float meisaisuu;
        //明細別でない商品全体の在庫金額
        float kingaku;
    }

}
