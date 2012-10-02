package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.oisix.oisystemzs.ejb.SoukoData;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TanaoroshiinputEvent extends TransactionEvent {

    private java.util.Date tanaoroshidate;
    public java.util.Date getTanaoroshidate() { return tanaoroshidate; }
    private TreeMap alltanaoroshilist;

    private LinkedList tanaoroshilist = new LinkedList();
    public LinkedList getTanaoroshilist() { return tanaoroshilist; }

    private int page_id;
    public int getPage_id() { return page_id; }
//    private final int onePageColumn = 20;
    private final int onePageColumn = 10;
    public int countTanaoroshi() {
        return alltanaoroshilist.size();
    }
    public int firstIndex() {
        return onePageColumn * page_id + 1;
    }
    public int lastIndex() {
        int last = onePageColumn * (page_id + 1);
        if (last > countTanaoroshi()) {
            last = countTanaoroshi();
        }
        return last;
    }
    public boolean hasPrevpage() {
        boolean rt = false;
        // 現在の最初のindexが1より大きい場合
        if (firstIndex() > 1) {
            return true;
        }
        return rt;
    }
    public boolean hasNextpage() {
        boolean rt = false;
        // 現在の最後のindexが全要素数より小さい場合
        if (lastIndex() < countTanaoroshi()) {
            return true;
        }
        return rt;
    }

    public void init(HttpServletRequest request) {
        String yearstr = request.getParameter("zaikoyear");
        String monthstr = request.getParameter("zaikomonth");
        String datestr = request.getParameter("zaikodate");
        if (yearstr != null && monthstr != null && datestr != null) {
            try {
                int year = Integer.parseInt(yearstr);
                int month = Integer.parseInt(monthstr);
                int date = Integer.parseInt(datestr);
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(year, month - 1, date);
                tanaoroshidate = cal.getTime();
                session.setAttribute("#TANAOROSHIDATE", tanaoroshidate);
                alltanaoroshilist = makeTanaoroshilist();
                session.setAttribute("#TANAOROSHILIST", alltanaoroshilist);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("棚卸日が取得できません");
                result = RC_INPUTERROR;
                return;
            }
        } else {
            tanaoroshidate =
              (java.util.Date)session.getAttribute("#TANAOROSHIDATE");
            // 棚卸リストの取得
            alltanaoroshilist =
              (TreeMap)session.getAttribute("#TANAOROSHILIST");
            if (alltanaoroshilist == null) {
                result = RC_INPUTERROR;
                errorlist.add("棚卸リストが取得できません。");
                return;
            }
        }
        if (tanaoroshidate == null) {
            errorlist.add("棚卸日が取得できません");
            result = RC_INPUTERROR;
            return;
        }

        // ページ番号の取得
        Integer page_idint = (Integer)session.getAttribute("#TANAOROSHIPAGE");
        if (page_idint == null) {
            page_idint = new Integer(0);
        }
        try {
            page_id = page_idint.intValue();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            result = RC_INPUTERROR;
            errorlist.add("ページ番号が取得できません。");
        }

        if (result == RC_INPUTERROR) {
            return;
        }

        Debug.println("now TanaoroshiList make", this);
        for (int i = firstIndex(); i <= lastIndex(); i++) {
            Integer indexint = new Integer(i);
            HashMap tanaoroshi = (HashMap)alltanaoroshilist.get(indexint);
            tanaoroshi.put("TANAOROSHIINDEX", indexint);
            tanaoroshilist.add(tanaoroshi);
        }
    }

    public void handleEvent(HashMap attr) {
    }

    private TreeMap makeTanaoroshilist() {
        if (tanaoroshidate == null) { return null; }

        // 棚卸リストコレクション作成
        TreeMap tanaoroshilist = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Context ic = new InitialContext();
            DataSource ds =
              (DataSource)ic.lookup("java:comp/env/oisystemPool");
            con = ds.getConnection();

            String sql = makeSql();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs == null) {
                Debug.println("rs null", this);
                return null;
            }
            tanaoroshilist = new TreeMap();
            int tanaoroshiindex = 1;
            while (rs.next()) {
                // 棚卸情報の取得
                int index = 1;
                HashMap item = new HashMap();
                item.put("SHOUHIN_ID", rs.getString(index++));
                item.put("SHOUHINMEI", rs.getString(index++));
                item.put("KIKAKU", rs.getString(index++));
                item.put("TANI", rs.getString(index++));
                int hyoukahou = rs.getInt(index++);
                java.util.Date shoumikigen = rs.getDate(index++);
                item.put("SHOUMIKIGEN", shoumikigen);
                item.put("SHUKKAKIGEN", rs.getDate(index++));
                float zaikotanka = rs.getFloat(index++);
                float hyoujuntanka = rs.getFloat(index++);
                if (hyoukahou == 1) {
                    item.put("TANKA", new Float(zaikotanka));
                } else {
                    item.put("TANKA", new Float(hyoujuntanka));
                }
                float meisaisuuryou = rs.getFloat(index++);
                float zaikosuuryou = rs.getFloat(index++);
                if (shoumikigen == null) {
                    meisaisuuryou = zaikosuuryou;
                }
                Float suuryou = new Float(meisaisuuryou);
                item.put("SUURYOU", suuryou);
                item.put("MOTOSUURYOU", suuryou);
                tanaoroshilist.put(new Integer(tanaoroshiindex++), item);
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
            }
            catch (SQLException sqle) { Debug.println(sqle); }
        }
        return tanaoroshilist;
    }

    private String makeSql() {
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String sql =
          "SELECT " +
          "m1.SHOUHIN_ID, " +
          "m1.SHOUHIN, " +
          "m1.KIKAKU, " +
          "m2.TANI, " +
          "m1.ZAIKOHYOUKAHOUHOU, " +
          "t2.SHOUMIKIGEN, " +
          "t2.SHUKKAKIGEN, " +
          "t1.TANKA, " +
          "m1.HYOUJUNTANKA, " +
          "t2.SUURYOU, " +
          "t1.SUURYOU " +
          "FROM ZT_ZAIKO t1, ZT_ZAIKOMEISAI t2, " +
          "ZM_SHOUHIN m1, ZM_TANI m2 " +
          "WHERE t2.ZAIKO_ID(+) = t1.ZAIKO_ID " +
          "AND t1.SHOUHIN_ID = m1.SHOUHIN_ID " +
          "AND m1.TANI = m2.TANI_ID " +
          "AND t1.SUURYOU > 0 " +
          "AND t2.SUURYOU(+) > 0 " +
          "AND (t1.ZORDER, t1.SHOUHIN_ID, t1.SOUKO_ID) in (" +
            "SELECT " +
            "MAX(t3.ZORDER), " +
            "t3.SHOUHIN_ID, " +
            "t3.SOUKO_ID " +
            "FROM ZT_ZAIKO t3 " +
            "WHERE t3.SOUKO_ID = '" + souko.getSouko_id() + "' " +
            "AND t3.ZAIKODATE <= TO_DATE('" + sdf.format(tanaoroshidate) +
              " 23:59:59', 'YYYY/MM/DD HH24:MI:SS') " +
            "GROUP BY " +
            "t3.SHOUHIN_ID, " +
            "t3.SOUKO_ID) " +
          "ORDER BY " +
          "m1.LOCATION_ID1, " +
          "t1.SHOUHIN_ID, " +
          "t2.SHOUMIKIGEN ";
        Debug.println(sql);
        return sql;
    }
}
