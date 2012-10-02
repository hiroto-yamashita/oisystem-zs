package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemfr.EventHandlerSupport;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.objectmap.NyuukokubunMap;
import com.oisix.oisystemzs.objectmap.ShukkokubunMap;
import com.oisix.oisystemzs.objectmap.TaniMap;
import com.oisix.oisystemzs.objectmap.YouchuuikubunMap;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Set;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.naming.NamingException;

public class YoteizaikoEvent extends EventHandlerSupport {

    private int result = 0;
    private String errormsg;

    private TreeMap yoteimap = new TreeMap();
    private boolean kubun = true;
    private java.util.Date inputdate;
    private int dates;
    private float zaikosuuryou;
    private TreeMap datesmapbase;
    private int shoumikigen_flg;
    private TreeMap shoumimap;
    private TreeMap shukkamap;
    private boolean isHacchuu = false;
    private String shiiresaki;
    private String type;

    public int getResult() { return result; }
    public String getErrormsg() { return errormsg; }
    public TreeMap getYoteimap() { return yoteimap; }
    public TreeMap getDatesmapbase() { return datesmapbase; }
    public boolean getKubun() { return kubun; }
    public java.util.Date getInputdate() { return inputdate; }
    public int getDates() { return dates; }
    public boolean isHacchuu() { return isHacchuu; }
    public String getShiiresaki() { return shiiresaki; }
    public String getType() { return type; }

    public void init(HttpServletRequest request) {
        DecimalFormat df = new DecimalFormat("##########.##");
        HttpSession session = request.getSession();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        String souko_id = souko.getSouko_id();
        type = request.getParameter("type");
        if (type.equals("hacchuu")) {
            isHacchuu = true;
        }
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String date = request.getParameter("date");
        if (year == null) {
            year = date.substring(0, 4);
            month = date.substring(4, 6);
            date = date.substring(6, 8);
        }
        Calendar cal = DateUtil.getCalendar(year, month, date);
        inputdate = cal.getTime();
        String datesstr = request.getParameter("dates");
        try {
            dates = Integer.parseInt(datesstr);
        } catch (NumberFormatException nfe) {
            result = 1;
            errormsg = "日数が数字ではありません。";
        }
        String shuubai_flgstr = request.getParameter("shuubai_flg");
        boolean shuubai_flg = false;
        if ((shuubai_flgstr != null) && (shuubai_flgstr.equals("1"))) {
            shuubai_flg = true;
        }

        DataSource ds = null;
        try {
            ds = ServiceLocator.getDataSource();
        } catch (NamingException ne) {
            ne.printStackTrace();
            result = 1;
            errormsg = "システムエラーです。NamingException";
        }
        java.sql.Date dummyshoumi = new java.sql.Date(Long.MAX_VALUE);
        Connection con = null;
        PreparedStatement idps = null;
        ResultSet idrs = null;
        PreparedStatement shiireps = null;
        ResultSet shiirers = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs1 = null;
        PreparedStatement ps4 = null;
        ResultSet rs2 = null;
        try {
            con = ds.getConnection();

            if (type.equals("kobetsu")) {
                String idsql = "select shouhin_id from zm_shouhin " +
                  "where kobetsuhacchuu_flg=1";
                if (!shuubai_flg) {
                    idsql += " and shuubai_flg=0";
                }
                idps = con.prepareStatement(idsql);
                idrs = idps.executeQuery();
                while (idrs.next()) {
                    String id = idrs.getString(1);
                    TreeMap ukeharaimap = getNewUkeharaimap();
                    yoteimap.put(id, new TreeMap(ukeharaimap));
                }
                idrs.close();
                idps.close();
            } else if (type.equals("chuui")) {
                String youchuuistr = request.getParameter("youchuuikubun");
                int youchuui = 0;
                try {
                    youchuui = Integer.parseInt(youchuuistr);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                String idsql = "select shouhin_id from zm_shouhin " +
                  "where youchuuihin_flg=?";
                if (!shuubai_flg) {
                    idsql += " and shuubai_flg=0";
                }
                idps = con.prepareStatement(idsql);
                idps.setInt(1, youchuui);
                idrs = idps.executeQuery();
                while (idrs.next()) {
                    String id = idrs.getString(1);
                    TreeMap ukeharaimap = getNewUkeharaimap();
                    yoteimap.put(id, new TreeMap(ukeharaimap));
                }
                idrs.close();
                idps.close();
            } else if (type.equals("shiiresaki") || (isHacchuu)) {
                String  shiiresaki_id = request.getParameter("shiiresaki_id");
                String idsql = "select shouhin_id from zm_shouhin where " +
                  "shiiresaki_id=?";
                if (!shuubai_flg) {
                    idsql += " and shuubai_flg=0";
                }
                idsql += " order by daibunrui, shouhin_id";
                idps = con.prepareStatement(idsql);
                idps.setString(1, shiiresaki_id);
                idrs = idps.executeQuery();
                while (idrs.next()) {
                    String id = idrs.getString(1);
                    TreeMap ukeharaimap = getNewUkeharaimap();
                    yoteimap.put(id, new TreeMap(ukeharaimap));
                }
                idrs.close();
                idps.close();
                String shiiresql = "select name from zm_shiiresaki " +
                  "where shiiresaki_id=?";
                shiireps = con.prepareStatement(shiiresql);
                shiireps.setString(1, shiiresaki_id);
                shiirers = shiireps.executeQuery();
                while (shiirers.next()) {
                    shiiresaki = shiirers.getString(1);
                }
                shiirers.close();
                shiireps.close();
            } else {
                Enumeration params = request.getParameterNames();
                String key = null;
                String value = null;
                while (params.hasMoreElements()) {
                    key = (String)params.nextElement();
                    if (key.startsWith("shouhin_id")) {
                        value = request.getParameter(key);
                        if ((value != null) && (!value.equals(""))) {
                            TreeMap ukeharaimap = getNewUkeharaimap();
                            yoteimap.put(value, new TreeMap(ukeharaimap));
                        }
                    }
                }
            }

            java.util.Date today = DateUtil.getDate();
            Calendar todaycal = Calendar.getInstance();
            todaycal.setTime(today);
            todaycal.set(Calendar.HOUR_OF_DAY, 0);
            todaycal.set(Calendar.MINUTE, 0);
            todaycal.set(Calendar.SECOND, 0);
            todaycal.set(Calendar.MILLISECOND, 0);
            today = todaycal.getTime();
            java.util.Date kensakudate = (java.util.Date)inputdate.clone();
            if (isHacchuu) {
                cal.add(Calendar.DATE, -3);
                inputdate = cal.getTime();
                dates += 3;
            }

            Set ids = yoteimap.keySet();
            Iterator iter = ids.iterator();
            while (iter.hasNext()) {
                String shouhin_id = (String)iter.next();
                TreeMap ukeharaimap = (TreeMap)yoteimap.get(shouhin_id);

                String sql = "select " +
                  "t1.shouhin, " + 
                  "t1.kikaku, " +
                  "t1.hacchuutanisuu, " +
                  "t1.hacchuutani, " +
                  "t1.hacchuukikaku, " +
                  "t1.irisuu, " +
                  "t1.hacchuucomment, " +
                  "t1.shukkakigennissuu, " +
                  "t1.youchuuihin_flg, " +
                  "t1.saiteihacchuusuu, " +
                  "t1.shoumikigen_flg, " +
                  "t1.shiireleadtime, " +
                  "t1.hacchuuten, " +
                  "t1.mochikoshi_flg " +
                  "from zm_shouhin t1 " + 
                  "where t1.shouhin_id = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, shouhin_id);
                rs = ps.executeQuery();
                String shouhin = null;
                String kikaku = null;
                int hacchuutanisuu = 0;
                String hacchuutani = null;
                String hacchuukikaku = null;
                float irisuu = 0;
                String hacchuucomment = null;
                int shukkakigennissuu = 0;
                String youchuuikubun = null;
                int saiteihacchuusuu = 0;
                int shiireleadtime = 0;
                int hacchuuten = 0;
                int mochikoshi_flg = 0;
                while (rs.next()) {
                    int index = 1;
                    shouhin = rs.getString(index++);
                    kikaku = rs.getString(index++);
                    hacchuutanisuu = rs.getInt(index++);
                    int ihacchuutani = rs.getInt(index++);
                    hacchuutani = TaniMap.getTani(ihacchuutani).getTani();
                    hacchuukikaku = rs.getString(index++);
                    irisuu = rs.getFloat(index++);
                    hacchuucomment = rs.getString(index++);
                    shukkakigennissuu = rs.getInt(index++);
                    int youchuuihin_flg = rs.getInt(index++);
                    youchuuikubun = YouchuuikubunMap.get(youchuuihin_flg);
                    saiteihacchuusuu = rs.getInt(index++);
                    shoumikigen_flg = rs.getInt(index++);
                    shiireleadtime = rs.getInt(index++);
                    hacchuuten = rs.getInt(index++);
                    mochikoshi_flg = rs.getInt(index++);
                    ukeharaimap.put("shouhin_id", shouhin_id);
                    ukeharaimap.put("shouhin", shouhin);
                    ukeharaimap.put("kikaku", kikaku);
                    ukeharaimap.put("shiireleadtime",
                      new Integer(shiireleadtime));
                    ukeharaimap.put("saiteihacchuusuu",
                      new Integer(saiteihacchuusuu));
                    ukeharaimap.put("hacchuutanisuu",
                      new Integer(hacchuutanisuu));
                    ukeharaimap.put("hacchuukikaku", hacchuukikaku);
                    ukeharaimap.put("irisuu", df.format(irisuu));
                    ukeharaimap.put("shukkakigennissuu",
                      new Integer(shukkakigennissuu));
                    ukeharaimap.put("youchuuikubun", youchuuikubun);
                    ukeharaimap.put("hacchuucomment", hacchuucomment);
                    ukeharaimap.put("hacchuuten", new Integer(hacchuuten));
                    ukeharaimap.put("mochikoshi_flg",
                      new Integer(mochikoshi_flg));
                }
                rs.close();
                ps.close();

                //最短納品日判定
                Calendar saitancal = Calendar.getInstance();
                saitancal.setTime(today);
                saitancal.add(Calendar.DATE, shiireleadtime);
                java.util.Date saitan = saitancal.getTime();
                if (saitan.after(kensakudate)) {
                    ukeharaimap.put("timeover", new Boolean(true));
                }

                if (shoumikigen_flg == Names.ON) {
                    shoumimap = new TreeMap();
                    shukkamap = new TreeMap();
                    shoumimap.put(dummyshoumi, new Float(0));
                }

                //開始日付と日数の決定
                java.sql.Date startdate =
                  new java.sql.Date(inputdate.getTime());
                sql = "select min(t1.nyuukayotei_date) from " +
                  "zt_nyuukayoteimeisai t1 " +
                  "where souko_id=? and shouhin_id=? and " +
                  "(t1.nyuukasuuryou - t1.jitsunyuukasuu) > 0 and " +
                  "t1.nyuukajoukyou<>3";
                ps = con.prepareStatement(sql);
                int index = 1;
                ps.setString(index++, souko_id);
                ps.setString(index++, shouhin_id);
                rs = ps.executeQuery();
                java.sql.Date lastnyuukayoteidate = null;
                while (rs.next()) {
                    lastnyuukayoteidate = rs.getDate(1);
                }
                rs.close();
                ps.close();
                if ((lastnyuukayoteidate != null) &&
                  lastnyuukayoteidate.before(startdate)) {
                    startdate = lastnyuukayoteidate;
                }
                sql = "select min(t1.shukkayotei_date) " +
                  "from zt_shukkayotei t1, zt_shukkayoteimeisai t2 " +
                  "where t1.shukkayotei_bg = t2.shukkayotei_bg and " +
                  "t2.souko_id=? and t2.shouhin_id=? and " +
                  "(t2.shukkayoteisuuryou - t2.jitsushukkasuuryou)> 0 and " +
                  "t2.shukkajoukyou<>3";
                ps = con.prepareStatement(sql);
                index = 1;
                ps.setString(index++, souko_id);
                ps.setString(index++, shouhin_id);
                rs = ps.executeQuery();
                java.sql.Date lastshukkayoteidate = null;
                while (rs.next()) {
                    lastshukkayoteidate = rs.getDate(1);
                }
                rs.close();
                ps.close();
                if ((lastshukkayoteidate != null) &&
                  lastshukkayoteidate.before(startdate)) {
                    startdate = lastshukkayoteidate;
                }
                sql = "select t1.zaikodate from zt_zaiko t1 " +
                  "where t1.souko_id=? and t1.shouhin_id=? and " +
                  "t1.zorder=(select max(zorder) from zt_zaiko t2 " +
                  "where t2.souko_id=? and t2.shouhin_id=? and " +
                  "t2.zaikodate<=?)";
                sql = "select t1.zaikodate from zt_zaiko t1, " +
                  "(select max(zorder) as mzorder, " +
                  "t2.shouhin_id, t2.souko_id from zt_zaiko t2 " +
                  "where t2.souko_id=? and t2.shouhin_id=? and " +
                  "t2.zaikodate<=? " +
                  "group by t2.shouhin_id,t2.souko_id) as t3 " +
                  "where t1.zorder=t3.mzorder and " +
                  "t1.shouhin_id=t3.shouhin_id and " + 
                  "t1.souko_id=t3.souko_id";
                ps = con.prepareStatement(sql);
                index = 1;
                ps.setString(index++, souko_id);
                ps.setString(index++, shouhin_id);
                ps.setDate(index++, startdate);
                rs = ps.executeQuery();
                java.sql.Date lastzaikodate = null;
                while (rs.next()) {
                	lastzaikodate = rs.getDate(1);
                }
                rs.close();
                ps.close();
                if ((lastzaikodate != null) &&
                  lastzaikodate.before(startdate)) {
                    startdate = lastzaikodate;
                }

                int days = (int)((inputdate.getTime() -
                  startdate.getTime())/(1000*60*60*24)) + dates;

                datesmapbase = new TreeMap();
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(startdate);
                while (days > 0) {
                    datesmapbase.put(cal1.getTime(), null);
                    cal1.add(Calendar.DATE, 1);
                    days--;
                }

                String sql1 = "select " +
                  "t1.nyuukokubun, " +
                  "t1.nyuukosuuryou, " +
                  "t1.shoumikigen, " +
                  "t1.nyuukotanka, " +
                  "t1.shukkakigen, " +
                  "t2.shukkokubun, " +
                  "t2.suuryou, " +
                  "t2.shoumikigen, " +
                  "t2.tanka, " +
                  "t2.kingaku, " +
                  "t3.zaiko_id, " +
                  "t3.suuryou, " +
                  "t3.tanka, " +
                  "t3.kingaku " +
                  "from zt_zaiko t3 " +
                  "left outer join zt_nyuuko t1 " +
                  "on t1.nyuuko_id=t3.nyuuko_id " +
                  "left outer join zt_shukko t2 " +
                  "on t2.shukko_id=t3.shukko_id " +
                  "where " +
                  "t3.zaikodate >= ? and " +
                  "t3.zaikodate < ? and " +
                  "t3.souko_id = ? and " +
                  "t3.shouhin_id = ? " +
                  "order by t3.zorder";
                String sql2 = "select " +
                  "t1.nyuukokubun, " +
                  "t1.nyuukasuuryou, " +
                  "t1.hacchuusuuryou, " +
                  "t1.hacchuutanka, " +
                  "t1.jitsunyuukasuu, " +
                  "t1.shoumikigen, " +
                  "t1.shukkakigen " +
                  "from zt_nyuukayoteimeisai t1 " +
                  "where t1.souko_id = ? and " +
                  "t1.shouhin_id = ? and " +
                  "t1.nyuukayotei_date >= ? and " +
                  "t1.nyuukayotei_date < ? and " +
                  "(t1.nyuukajoukyou = 1 or t1.nyuukajoukyou = 2)";
                String sql3 = "select " +
                  "t1.shukkokubun, " +
                  "t2.shukkayoteisuuryou, " +
                  "t2.jitsushukkasuuryou " +
                  "from zt_shukkayotei t1, zt_shukkayoteimeisai t2 " +
                  "where t1.shukkayotei_bg = t2.shukkayotei_bg and " +
                  "t2.souko_id = ? and " +
                  "t2.shouhin_id = ? and " +
                  "t1.shukkayotei_date >= ? and " +
                  "t1.shukkayotei_date < ? and " +
                  "(t2.shukkajoukyou = 1 or t2.shukkajoukyou = 2)";
                Set dkeys = datesmapbase.keySet();
                Iterator diter = dkeys.iterator();
                boolean firstflag = true;
                TreeMap datesmap = null;
                zaikosuuryou = 0;
                while (diter.hasNext()) {
                    java.util.Date linedate = (java.util.Date)diter.next();
                    cal.setTime(linedate);
                    java.sql.Date date1 = new java.sql.Date(
                      cal.getTimeInMillis());
                    cal.add(Calendar.DATE, 1);
                    java.sql.Date date2 = new java.sql.Date(
                      cal.getTimeInMillis());
                    index = 1;
                    ps1 = con.prepareStatement(sql1);
                    ps1.setDate(index++, date1);
                    ps1.setDate(index++, date2);
                    ps1.setString(index++, souko_id);
                    ps1.setString(index++, shouhin_id);
                    rs = ps1.executeQuery();
                    while (rs.next()) {
                        boolean calczaiko = true;
                        index = 1;
                        int nyuukokubun = rs.getInt(index++);
                        float nyuukosuuryou = rs.getFloat(index++);
                        java.sql.Date nyuukoshoumi = rs.getDate(index++);
                        float nyuukotanka = rs.getFloat(index++);
                        java.sql.Date shukkakigen = rs.getDate(index++);
                        int shukkokubun = rs.getInt(index++);
                        float shukkosuuryou = rs.getFloat(index++);
                        java.sql.Date shukkoshoumi = rs.getDate(index++);
                        float shukkotanka = rs.getFloat(index++);
                        float shukkokingaku = rs.getFloat(index++);
                        int zaiko_id = rs.getInt(index++);
                        if (firstflag) {
                            firstflag = false;
                            calczaiko = false;
                            //最初の1行
                            zaikosuuryou = rs.getFloat(index++);
                            if (shoumikigen_flg == Names.ON) {
                                //ロケーション1箇所が前提
                                sql = "select " +
                                  "t1.shoumikigen, " +
                                  "t1.suuryou, " +
                                  "t1.shukkakigen " +
                                  "from zt_zaikomeisai t1 " +
                                  "where zaiko_id = ?";
                                ps = con.prepareStatement(sql);
                                ps.setInt(1, zaiko_id);
                                rs1 = ps.executeQuery();
                                while (rs1.next()) {
                                    index = 1;
                                    java.sql.Date shoumi = rs1.getDate(index++);
                                    float fsuuryou = rs1.getFloat(index++);
                                    java.sql.Date shukka = rs1.getDate(index++);
                                    Float suuryou = new Float(fsuuryou);
                                    sql = "select " +
                                      "max(t1.shukko_date) " +
                                      "from zt_shukko t1 " +
                                      "where t1.souko_id=? and " +
                                      "t1.shouhin_id=? and " +
                                      "t1.shoumikigen=?";
                                    ps4 = con.prepareStatement(sql);
                                    index = 1;
                                    ps4.setString(index++, souko_id);
                                    ps4.setString(index++, shouhin_id);
                                    ps4.setDate(index++, shoumi);
                                    rs2 = ps4.executeQuery();
                                    java.sql.Date maxshukka = null;
                                    while (rs2.next()) {
                                        maxshukka = rs2.getDate(1);
                                    }
                                    ps4.close();
                                    rs2.close();
                                    if ((maxshukka != null) &&
                                      shukka.before(maxshukka)) {
                                        shukka = maxshukka;
                                    }
                                    shoumimap.put(shoumi, suuryou);
                                    shukkamap.put(shukka, shoumi);
                                }
                                rs1.close();
                                ps.close();
                            }
                        }
                        if (nyuukosuuryou != 0) {
                            HashMap nyuukomap = (HashMap)
                              ukeharaimap.get("01nyuuko");
                            Integer nkubun = new Integer(0);
                            if (kubun) {
                                nkubun = new Integer(nyuukokubun);
                            }
                            datesmap = (TreeMap)nyuukomap.get(nkubun);
                            if (datesmap == null) {
                                datesmap = new TreeMap(datesmapbase);
                                nyuukomap.put(nkubun, datesmap);
                            }
                            Float suuryou = (Float)datesmap.get(linedate);
                            float fsuuryou = 0;
                            if (suuryou != null) {
                                fsuuryou = suuryou.floatValue();
                            }
                            fsuuryou += nyuukosuuryou;
                            datesmap.put(linedate, new Float(fsuuryou));
                            if (calczaiko) {
                                zaikosuuryou += nyuukosuuryou;
                                if (shoumikigen_flg == Names.ON) {
                                    Float ssuuryou = (Float)shoumimap.get(
                                      nyuukoshoumi);
                                    if (ssuuryou == null) {
                                        ssuuryou = new Float(0);
                                    }
                                    float fssuuryou = ssuuryou.floatValue();
                                    fssuuryou += nyuukosuuryou;
                                    ssuuryou = new Float(fssuuryou);
                                    shoumimap.put(nyuukoshoumi, ssuuryou);
                                    //shukkamap.put(shukkakigen, nyuukoshoumi);
                                    if (!shukkamap.containsValue(
                                      nyuukoshoumi)) {
                                        sql = "select " +
                                          "max(t1.shukko_date) " +
                                          "from zt_shukko t1 " +
                                          "where t1.souko_id=? and " +
                                          "t1.shouhin_id=? and " +
                                          "t1.shoumikigen=?";
                                        ps4 = con.prepareStatement(sql);
                                        index = 1;
                                        ps4.setString(index++, souko_id);
                                        ps4.setString(index++, shouhin_id);
                                        ps4.setDate(index++, nyuukoshoumi);
                                        rs2 = ps4.executeQuery();
                                        java.sql.Date maxshukka = null;
                                        while (rs2.next()) {
                                            maxshukka = rs2.getDate(1);
                                        }
                                        ps4.close();
                                        rs2.close();
                                        if ((maxshukka != null) &&
                                          shukkakigen.before(maxshukka)) {
                                            shukkakigen = maxshukka;
                                        }
                                        shukkamap.put(
                                          shukkakigen, nyuukoshoumi);
                                    }
                                }
                            }
                        }
                        if (shukkosuuryou != 0) {
                            HashMap shukkomap = (HashMap)
                              ukeharaimap.get("02shukko");
                            Integer skubun = new Integer(0);
                            if (kubun) {
                                skubun = new Integer(shukkokubun);
                            }
                            datesmap = (TreeMap)shukkomap.get(skubun);
                            if (datesmap == null) {
                                datesmap = new TreeMap(datesmapbase);
                                shukkomap.put(skubun, datesmap);
                            }
                            Float suuryou = (Float)datesmap.get(linedate);
                            float fsuuryou = 0;
                            if (suuryou != null) {
                                fsuuryou = suuryou.floatValue();
                            }
                            fsuuryou += shukkosuuryou;
                            datesmap.put(linedate, new Float(fsuuryou));
                            if (calczaiko) {
                                zaikosuuryou -= shukkosuuryou;
                                if (shoumikigen_flg == Names.ON) {
                                    Float ssuuryou = (Float)shoumimap.get(
                                      shukkoshoumi);
                                    float fssuuryou = ssuuryou.floatValue();
                                    fssuuryou -= shukkosuuryou;
                                    ssuuryou = new Float(fssuuryou);
                                    shoumimap.put(shukkoshoumi, ssuuryou);
                                }
                            }
                        }
                    }
                    rs.close();
                    ps1.close();
                    //入荷予定
                    index = 1;
                    ps2 = con.prepareStatement(sql2);
                    ps2.setString(index++, souko_id);
                    ps2.setString(index++, shouhin_id);
                    ps2.setDate(index++, date1);
                    ps2.setDate(index++, date2);
                    rs = ps2.executeQuery();
                    while (rs.next()) {
                        firstflag = false;
                        // 一番最後の在庫データに対して加算していく
                        index = 1;
                        int nyuukokubun = rs.getInt(index++);
                        float nyuukosuuryou = rs.getFloat(index++);
                        float hacchuusuuryou = rs.getFloat(index++);
                        float hacchuutanka = rs.getFloat(index++);
                        float jitsunyuukasuu = rs.getFloat(index++);
                        java.sql.Date nyshoumi = rs.getDate(index++);
                        java.sql.Date nyshukka = rs.getDate(index++);
                        nyuukosuuryou -= jitsunyuukasuu;
                        HashMap nyuukomap =
                          (HashMap)ukeharaimap.get("01nyuuko");
                        Integer nkubun = new Integer(0);
                        if (kubun) {
                            nkubun = new Integer(nyuukokubun);
                        }
                        datesmap = (TreeMap)nyuukomap.get(nkubun);
                        if (datesmap == null) {
                            datesmap = new TreeMap(datesmapbase);
                            nyuukomap.put(nkubun, datesmap);
                        }
                        Float suuryou = (Float)datesmap.get(linedate);
                        float fsuuryou = 0;
                        if (suuryou != null) {
                             fsuuryou = suuryou.floatValue();
                        }
                        fsuuryou += nyuukosuuryou;
                        datesmap.put(linedate, new Float(fsuuryou));
                        zaikosuuryou += nyuukosuuryou;
                        if (shoumikigen_flg == Names.ON) {
                            Float ssuuryou = (Float)shoumimap.get(nyshoumi);
                            if (ssuuryou == null) {
                                ssuuryou = new Float(0);
                            }
                            //Float ssuuryou =
                            //  (Float)shoumimap.get(dummyshoumi);
                            float fssuuryou = ssuuryou.floatValue();
                            fssuuryou += nyuukosuuryou;
                            ssuuryou = new Float(fssuuryou);
                            //shoumimap.put(dummyshoumi, ssuuryou);
                            shoumimap.put(nyshoumi, ssuuryou);
                            if (!shukkamap.containsValue(nyshoumi)) {
                                 shukkamap.put(nyshukka, nyshoumi);
                            }
                        }
                    }
                    rs.close();
                    ps2.close();
                    //出荷予定
                    index = 1;
                    ps3 = con.prepareStatement(sql3);
                    ps3.setString(index++, souko_id);
                    ps3.setString(index++, shouhin_id);
                    ps3.setDate(index++, date1);
                    ps3.setDate(index++, date2);
                    rs = ps3.executeQuery();
                    while (rs.next()) {
                        firstflag = false;
                        // 一番最後の在庫データに対して加算していく
                        index = 1;
                        int shukkokubun = rs.getInt(index++);
                        float shukkosuuryou = rs.getFloat(index++);
                        float jitsushukkasuu = rs.getFloat(index++);
                        shukkosuuryou -= jitsushukkasuu;
                        HashMap shukkomap = (HashMap)ukeharaimap.get("02shukko");
                        Integer skubun = new Integer(0);
                        if (kubun) {
                            skubun = new Integer(shukkokubun);
                        }
                        datesmap = (TreeMap)shukkomap.get(skubun);
                        if (datesmap == null) {
                            datesmap = new TreeMap(datesmapbase);
                            shukkomap.put(skubun, datesmap);
                        }
                        Float suuryou = (Float)datesmap.get(linedate);
                        float fsuuryou = 0;
                        if (suuryou != null) {
                            fsuuryou = suuryou.floatValue();
                        }
                        fsuuryou += shukkosuuryou;
                        datesmap.put(linedate, new Float(fsuuryou));
                        zaikosuuryou -= shukkosuuryou;
                        if (shoumikigen_flg == Names.ON) {
                            //賞味期限の短いものから引いていく
                            Set shoumis = shoumimap.keySet();
                            Iterator siter = shoumis.iterator();
                            while (siter.hasNext()) {
                                java.sql.Date shoumi = (java.sql.Date)
                                  siter.next();
                                Float ssuuryou = (Float)shoumimap.get(shoumi);
                                float fssuuryou = ssuuryou.floatValue();
                                if (fssuuryou == 0) {
                                    continue;
                                }
                                if (fssuuryou >= shukkosuuryou) {
                                    fssuuryou -= shukkosuuryou;
                                    ssuuryou = new Float(fssuuryou);
                                    shoumimap.put(shoumi, ssuuryou);
                                    break;
                                } else {
                                    shukkosuuryou -= fssuuryou;
                                    fssuuryou = 0;
                                    ssuuryou = new Float(fssuuryou);
                                    shoumimap.put(shoumi, ssuuryou);
                                }
                            }
                        }
                    }
                    rs.close();
                    ps3.close();

                    if (shukkamap != null) {
                        TreeMap shukkamapkey = new TreeMap(shukkamap);
                        Set shukkas = shukkamapkey.keySet();
                        Iterator siter = shukkas.iterator();
                        while (siter.hasNext()) {
                            java.sql.Date shukka = (java.sql.Date)siter.next();
                            if (shukka.compareTo(linedate) <= 0) {
                                //出荷期限切れを引き落とし
                                java.sql.Date shoumi = (java.sql.Date)
                                  shukkamap.get(shukka);
                                Float suuryou = (Float)shoumimap.get(shoumi);
                                float fsuuryou = suuryou.floatValue();
                                if (fsuuryou > 0) {
                                    HashMap shukkomap = (HashMap)
                                      ukeharaimap.get("02shukko");
                                    Integer skubun = new Integer(0);
                                    if (kubun) {
                                        skubun = new Integer(18);
                                    }
                                    datesmap = (TreeMap)shukkomap.get(skubun);
                                    if (datesmap == null) {
                                        datesmap = new TreeMap(datesmapbase);
                                        shukkomap.put(skubun, datesmap);
                                    }
                                    Float ssuuryou = 
                                      (Float)datesmap.get(linedate);
                                    float fssuuryou = 0;
                                    if (ssuuryou != null) {
                                        fssuuryou = ssuuryou.floatValue();
                                    }
                                    fssuuryou += fsuuryou;
                                    datesmap.put(linedate,
                                      new Float(fssuuryou));
                                    zaikosuuryou -= fsuuryou;
                                    shukkamap.remove(shukka);
                                    shoumimap.remove(shoumi);
                                }
                            }
                        }
                    }
                    HashMap zaikomap = (HashMap)ukeharaimap.get("03zaiko");
                    datesmap = (TreeMap)zaikomap.get("zaiko");
                    if (datesmap == null) {
                        datesmap = new TreeMap(datesmapbase);
                        zaikomap.put("zaiko", datesmap);
                    }
                    datesmap.put(linedate, new Float(zaikosuuryou));
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
            result = 1;
            errormsg = "システムエラーです。SQLException";
        } finally {
            try {
                if (idrs != null) { idrs.close(); }
                if (idps != null) { idps.close(); }
                if (shiirers != null) { shiirers.close(); }
                if (shiireps != null) { shiireps.close(); }
                if (rs != null) { rs.close(); }
                if (rs1 != null) { rs1.close(); }
                if (rs2 != null) { rs2.close(); }
                if (ps != null) { ps.close(); }
                if (ps1 != null) { ps1.close(); }
                if (ps2 != null) { ps2.close(); }
                if (ps3 != null) { ps3.close(); }
                if (ps4 != null) { ps4.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException se) {
                se.printStackTrace();
                result = 1;
                errormsg = "システムエラーです。SQLException";
            }
        }
    }

    public void handleEvent(HashMap attr) {
    }

    private TreeMap getNewUkeharaimap() {
        TreeMap ukeharaimap = new TreeMap();
        ukeharaimap.put("01nyuuko", new HashMap());
        ukeharaimap.put("02shukko", new HashMap());
        ukeharaimap.put("03zaiko", new HashMap());
        ukeharaimap.put("04hacchuuten", null);
        ukeharaimap.put("05kabusoku", null);
        return ukeharaimap;
    }
}
