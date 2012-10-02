package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.EventHandlerSupport;
import com.oisix.oisystemzs.Names;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import com.oisix.oisystemzs.ejb.SoukoData;
import javax.naming.NamingException;
import javax.ejb.FinderException;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;

public class NyuushukkoshoukaiEvent extends EventHandlerSupport {

    private LinkedList shouhin_idlist = new LinkedList();
    private LinkedList datelist = new LinkedList();
    private int nyuushukkoyear = 0;
    private int nyuushukkomonth = 0;
    private int nyuushukkodate = 0;
    private int nyuushukkospan = 0;
    private int kubunhyouji = 0;
    private LinkedList nyuushukkolist = new LinkedList();

    public LinkedList getShouhin_idlist() { return shouhin_idlist; }
    public LinkedList getDatelist() { return datelist; }
    public int getNyuushukkoyear() { return nyuushukkoyear; }
    public int getNyuushukkomonth() { return nyuushukkomonth; }
    public int getNyuushukkoday() { return nyuushukkodate; }
    public int getNyuushukkospan() { return nyuushukkospan; }
    public int getKubunhyouji() { return kubunhyouji; }
    public LinkedList getNyuushukkolist() { return nyuushukkolist; }
    public java.util.Date getNyuushukkodate() { return startdate; }

    private java.util.Date startdate;
    private java.util.Date enddate;
    private SoukoData souko;

    public void init(HttpServletRequest request) {
        String yearstr = request.getParameter("nyuushukkoyear");
        String monthstr = request.getParameter("nyuushukkomonth");
        String datestr = request.getParameter("nyuushukkodate");
        String spanstr = request.getParameter("nyuushukkospan");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        try {
            nyuushukkoyear = Integer.parseInt(yearstr);
            nyuushukkomonth = Integer.parseInt(monthstr);
            nyuushukkodate = Integer.parseInt(datestr);
            cal.set(nyuushukkoyear, nyuushukkomonth - 1, nyuushukkodate);
            startdate = cal.getTime();
            nyuushukkospan = Integer.parseInt(spanstr);
            for (int i = 0; i < nyuushukkospan; i++) {
                datelist.add(cal.getTime());
                enddate = cal.getTime();
                cal.add(Calendar.DATE, 1);
            }
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            return;
        }

        // 区分を表示するかどうかを取得
        String hyoujistr = request.getParameter("kubunhyouji");
        if (hyoujistr == null || hyoujistr.equals("")) {
            kubunhyouji = 1;
        } else {
            try {
                kubunhyouji = Integer.parseInt(hyoujistr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                return;
            }
        }

        // 商品コードの取得
        for (int i = 0; i < 10; i++) {
            String key = "shouhin_id" + String.valueOf(i);
            String shouhin_id = request.getParameter(key);
            if (shouhin_id != null && !shouhin_id.equals("")) {
                shouhin_idlist.add(shouhin_id);
            }
        }

        souko = (SoukoData)session.getAttribute("SOUKO");
    }

    public void handleEvent(HashMap attr) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();

            Iterator iter = shouhin_idlist.iterator();
            while (iter.hasNext()) {
                HashMap nyuushukko = new HashMap();
                String shouhin_id = (String)iter.next();
                nyuushukko.put("SHOUHIN_ID", shouhin_id);

                // 商品データの取得
                String sql = "";
                ShouhinLocalHome slh;
                try {
                    slh = (ShouhinLocalHome)ServiceLocator.
                      getLocalHome("java:comp/env/ejb/ShouhinLocal");
                } catch (NamingException ne) {
                    Debug.println(ne);
                    return;
                }
                ShouhinLocal shouhin;
                if (shouhin_id != null && !shouhin_id.equals("")) {
                    try {
                        shouhin = slh.findByPrimaryKey(shouhin_id);
                    } catch (FinderException fe) {
                        continue;
                    }
                } else {
                    continue;
                }
                nyuushukko.put("SHOUHINMEI", shouhin.getShouhin());
                nyuushukko.put("KIKAKU", shouhin.getKikaku());

                // 指定日付以前の在庫データ取得
                sql = makePrevZaikoSql(shouhin_id);
                float genzaikosuu = 0;
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs == null) {
                    Debug.println("rs null", this);
                    continue;
                }
                while (rs.next()) {
                    int index = 1;
                    genzaikosuu = rs.getFloat(index++);
                }
                rs.close();
                ps.close();

                // 在庫データの取得
                sql = makeZaikoSql(shouhin_id);
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs == null) {
                    Debug.println("rs null", this);
                    continue;
                }
                HashMap zaikosuuryou = new HashMap();
                while (rs.next()) {
                    int index = 1;
                    java.util.Date zaikodate = rs.getDate(index++);
                    float suuryou = rs.getFloat(index++);
                    zaikosuuryou.put(zaikodate, new Float(suuryou));
                }
                rs.close();
                ps.close();

                // 在庫データがない日の在庫数量の設定
                Iterator dateiter = datelist.iterator();
                while (dateiter.hasNext()) {
                    java.util.Date zaikodate = (java.util.Date)dateiter.next();
                    Float fsuuryou = (Float)zaikosuuryou.get(zaikodate);
                    float suuryou = genzaikosuu;
                    if (fsuuryou != null) {
                        suuryou = fsuuryou.floatValue();
                        genzaikosuu = suuryou;
                    }
                    zaikosuuryou.put(zaikodate, new Float(suuryou));
                }
                nyuushukko.put("ZAIKOSUURYOU", zaikosuuryou);

                int shouhinrow = 1;

                // 入庫データの取得
                sql = makeNyuukoSql(shouhin_id);
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs == null) {
                    Debug.println("rs null", this);
                    continue;
                }
                LinkedList nyuukokubunlist = new LinkedList();
                HashMap nyuukosuuryou = new HashMap();
                while (rs.next()) {
                    int index = 1;
                    java.util.Date zaikodate = rs.getDate(index++);
                    int nyuukokubun = 0;
                    if (kubunhyouji == 1) {
                        nyuukokubun = rs.getInt(index++);
                    }
                    Integer nyuukokubunint = new Integer(nyuukokubun);
                    float suuryou = rs.getFloat(index++);
                    if (!nyuukokubunlist.contains(nyuukokubunint)) {
                        nyuukokubunlist.add(nyuukokubunint);
                    }
                    HashMap suuryoumap =
                      (HashMap)nyuukosuuryou.get(nyuukokubunint);
                    if (suuryoumap == null) {
                        suuryoumap = new HashMap();
                    }
                    suuryoumap.put(zaikodate, new Float(suuryou));
                    nyuukosuuryou.put(nyuukokubunint, suuryoumap);
                }
                rs.close();
                ps.close();
                // 一つもデータがなかった場合、擬似データを作成
                if (nyuukokubunlist.size() == 0) {
                    Integer nyuukokubunint = new Integer(0);
                    nyuukokubunlist.add(nyuukokubunint);
                    HashMap suuryoumap = new HashMap();
                    nyuukosuuryou.put(nyuukokubunint, suuryoumap);
                }
                shouhinrow += nyuukokubunlist.size();
                HashMap nyuukoinfo = new HashMap();
                nyuukoinfo.put("NYUUKOKUBUNLIST", nyuukokubunlist);
                nyuukoinfo.put("NYUUKOSUURYOU", nyuukosuuryou);
                nyuushukko.put("NYUUKOINFO", nyuukoinfo);

                // 出庫データの取得
                sql = makeShukkoSql(shouhin_id);
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs == null) {
                    Debug.println("rs null", this);
                    continue;
                }
                LinkedList shukkokubunlist = new LinkedList();
                HashMap shukkosuuryou = new HashMap();
                while (rs.next()) {
                    int index = 1;
                    java.util.Date zaikodate = rs.getDate(index++);
                    int shukkokubun = 0;
                    if (kubunhyouji == 1) {
                        shukkokubun = rs.getInt(index++);
                    }
                    Integer shukkokubunint = new Integer(shukkokubun);
                    float suuryou = rs.getFloat(index++);
                    if (!shukkokubunlist.contains(shukkokubunint)) {
                        shukkokubunlist.add(shukkokubunint);
                    }
                    HashMap suuryoumap =
                      (HashMap)shukkosuuryou.get(shukkokubunint);
                    if (suuryoumap == null) {
                        suuryoumap = new HashMap();
                    }
                    suuryoumap.put(zaikodate, new Float(suuryou));
                    shukkosuuryou.put(shukkokubunint, suuryoumap);
                }
                rs.close();
                ps.close();
                // 一つもデータがなかった場合、擬似データを作成
                if (shukkokubunlist.size() == 0) {
                    Integer shukkokubunint = new Integer(0);
                    shukkokubunlist.add(shukkokubunint);
                    HashMap suuryoumap = new HashMap();
                    shukkosuuryou.put(shukkokubunint, suuryoumap);
                }
                shouhinrow += shukkokubunlist.size();
                HashMap shukkoinfo = new HashMap();
                shukkoinfo.put("SHUKKOKUBUNLIST", shukkokubunlist);
                shukkoinfo.put("SHUKKOSUURYOU", shukkosuuryou);
                nyuushukko.put("SHUKKOINFO", shukkoinfo);

                nyuushukko.put("SHOUHINROW", new Integer(shouhinrow));

                // 入出庫リストに追加
                nyuushukkolist.add(nyuushukko);
            }
        } catch (NamingException nme) {
            Debug.println(nme);
        } catch (SQLException sqle) {
            Debug.println(sqle);
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (ps != null) { ps.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException sqle) { sqle.printStackTrace(); }
        }
    }

    public String makePrevZaikoSql(String shouhin_id) {
        String souko_id = souko.getSouko_id();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startdatestr = sdf.format(startdate);
        String sql =
          "SELECT " +
          "t1.SUURYOU " +
          "FROM ZT_ZAIKO t1, " +
          "(SELECT MAX(t2.ZORDER) AS MZORDER, t2.SHOUHIN_ID, t2.SOUKO_ID " +
           "FROM ZT_ZAIKO t2 WHERE t2.SOUKO_ID='" + souko_id + "' " +
           "AND t2.ZAIKODATE<'" + startdatestr + "' " + 
           "AND t2.SHOUHIN_ID='" + shouhin_id + "' " + 
           "GROUP BY t2.ZAIKODATE, t2.SHOUHIN_ID, t2.SOUKO_ID) t3 " +
          "WHERE t1.ZORDER=t3.MZORDER AND " +
          "t1.SHOUHIN_ID=t3.SHOUHIN_ID AND " +
          "t1.SOUKO_ID=t3.SOUKO_ID";
        Debug.println(sql);
        return sql;
    }

    public String makeZaikoSql(String shouhin_id) {
        String souko_id = souko.getSouko_id();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startdatestr = sdf.format(startdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(enddate);
        cal.add(Calendar.DATE, 1);
        String enddatestr = sdf.format(cal.getTime());
        String sql =
          "SELECT " +
          "t1.ZAIKODATE, " +
          "t1.SUURYOU " +
          "FROM ZT_ZAIKO t1, " +
          "(SELECT MAX(t2.ZORDER) AS MZORDER, t2.SHOUHIN_ID, t2.SOUKO_ID " +
           "FROM ZT_ZAIKO t2 WHERE " +
           "t2.SOUKO_ID = '" + souko_id + "' " +
           "AND t2.ZAIKODATE>='" + startdatestr + "' " +
           "AND t2.ZAIKODATE<'" + enddatestr + "' " +
           "AND t2.SHOUHIN_ID = '" + shouhin_id + "' " +
           "GROUP BY t2.ZAIKODATE, t2.SHOUHIN_ID, t2.SOUKO_ID) t3 " +
          "WHERE t1.ZORDER=t3.MZORDER AND " +
          "t1.SHOUHIN_ID=t3.SHOUHIN_ID AND " +
          "t1.SOUKO_ID=t3.SOUKO_ID " +
          "ORDER BY t1.ZAIKODATE";
        Debug.println(sql);
        return sql;
    }

    public String makeNyuukoSql(String shouhin_id) {
        String souko_id = souko.getSouko_id();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startdatestr = sdf.format(startdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(enddate);
        cal.add(Calendar.DATE, 1);
        String enddatestr = sdf.format(cal.getTime());
        String sql =
          "SELECT " +
          "t1.NYUUKO_DATE, ";
        if (kubunhyouji == 1) {
            sql += "t1.NYUUKOKUBUN, ";
        }
        sql +=
          "SUM(t1.NYUUKOSUURYOU) " +
          "FROM ZT_NYUUKO t1 " +
          "WHERE t1.SHOUHIN_ID = '"+shouhin_id+"' " +
          "AND t1.SOUKO_ID = '"+souko_id+"' " +
          "AND t1.NYUUKO_DATE >= '" + startdatestr + "' " +
          "AND t1.NYUUKO_DATE <'" + enddatestr + "' " +
          "GROUP BY " +
          "t1.NYUUKO_DATE ";
        if (kubunhyouji == 1) {
            sql += ", t1.NYUUKOKUBUN ";
        }
        sql +=
          "ORDER BY " +
          "t1.NYUUKO_DATE ";
        if (kubunhyouji == 1) {
            sql += ", t1.NYUUKOKUBUN ";
        }
        Debug.println(sql);
        return sql;
    }

    public String makeShukkoSql(String shouhin_id) {
        String souko_id = souko.getSouko_id();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startdatestr = sdf.format(startdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(enddate);
        cal.add(Calendar.DATE, 1);
        String enddatestr = sdf.format(cal.getTime());
        String sql =
          "SELECT " +
          "t1.SHUKKO_DATE, ";
        if (kubunhyouji == 1) {
            sql += "t1.SHUKKOKUBUN, ";
        }
        sql +=
          "SUM(t1.SUURYOU) " +
          "FROM ZT_SHUKKO t1 " +
          "WHERE t1.SHOUHIN_ID = '"+shouhin_id+"' " +
          "AND t1.SOUKO_ID = '"+souko_id+"' " +
          "AND t1.SHUKKO_DATE >= '" + startdatestr + "' " +
          "AND t1.SHUKKO_DATE<'" + enddatestr + "' " +
          "GROUP BY " +
          "t1.SHUKKO_DATE ";
        if (kubunhyouji == 1) {
            sql += ", t1.SHUKKOKUBUN ";
        }
        sql +=
          "ORDER BY " +
          "t1.SHUKKO_DATE ";
        if (kubunhyouji == 1) {
            sql += ", t1.SHUKKOKUBUN ";
        }
        Debug.println(sql);
        return sql;
    }
}
