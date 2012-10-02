package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.EventHandlerSupport;
import com.oisix.oisystemzs.pdf.TanaoroshilistPdf;
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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaketanaoroshilistEvent extends EventHandlerSupport {

    private java.util.Date zaikodate;
    private SoukoData souko;
    private String filename;

    public String getFileName() { return filename; }

    public void init(HttpServletRequest request) {
        String yearstr = request.getParameter("zaikoyear");
        String monthstr = request.getParameter("zaikomonth");
        String datestr = request.getParameter("zaikodate");
        int year = 0;
        int month = 0;
        int day = 0;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        try {
            year = Integer.parseInt(yearstr);
            month = Integer.parseInt(monthstr);
            day = Integer.parseInt(datestr);
            cal.set(year, month - 1, day);
            zaikodate = cal.getTime();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            return;
        }

        souko = (SoukoData)session.getAttribute("SOUKO");
    }

    public void handleEvent(HashMap attr) {
        if (zaikodate == null) { return; }

        TanaoroshilistPdf listpdf = new TanaoroshilistPdf();
        listpdf.setZaikodate(zaikodate);

        // 棚卸リストコレクション作成
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
                return;
            }
            LinkedList tanaoroshilist = new LinkedList();
            while (rs.next()) {
                // 棚卸情報の取得
                int index = 1;
                HashMap map = new HashMap();
                map.put("SHOUHIN_ID", rs.getString(index++));
                map.put("SHOUHINMEI", rs.getString(index++));
                map.put("KIKAKU", rs.getString(index++));
                java.util.Date shoumikigen = rs.getDate(index++);
                map.put("SHUKKAKIGEN", rs.getDate(index++));
                float meisaisuuryou = rs.getFloat(index++);
                float zaikosuuryou = rs.getFloat(index++);
                map.put("LOCATION_ID1", rs.getString(index++));
                map.put("LOCATION_ID2", rs.getString(index++));
                map.put("LOCATION_ID3", rs.getString(index++));
                if (shoumikigen == null) {
                    meisaisuuryou = zaikosuuryou;
                }
                map.put("SUURYOU", new Float(meisaisuuryou));
                map.put("SHOUMIKIGEN", shoumikigen);
                tanaoroshilist.add(map);
            }

            listpdf.setTanaoroshilist(tanaoroshilist);
            try {
                listpdf.makeDocument();
            } catch (Exception ex) {
//                throw new RuntimeException(ex);
                Debug.println(ex);
            }
            filename = listpdf.getFileName();
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
    }

    public String makeSql() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String sql =
          "SELECT " +
          "m1.SHOUHIN_ID, " +
          "m1.SHOUHIN, " +
          "m1.KIKAKU, " +
          "t2.SHOUMIKIGEN, " +
          "t2.SHUKKAKIGEN, " +
          "t2.SUURYOU, " +
          "t1.SUURYOU, " +
          "m1.LOCATION_ID1, " +
          "m1.LOCATION_ID2, " +
          "m1.LOCATION_ID3 " +
          "FROM ZT_ZAIKO t1, ZT_ZAIKOMEISAI t2, ZM_SHOUHIN m1 " +
          "WHERE t2.ZAIKO_ID(+) = t1.ZAIKO_ID " +
          "AND t1.SHOUHIN_ID = m1.SHOUHIN_ID " +
          "AND t1.SUURYOU > 0 " +
          "AND t2.SUURYOU(+) > 0 " +
          "AND (t1.ZORDER, t1.SHOUHIN_ID, t1.SOUKO_ID) in (" +
            "SELECT " +
            "MAX(t3.ZORDER), " +
            "t3.SHOUHIN_ID, " +
            "t3.SOUKO_ID " +
            "FROM ZT_ZAIKO t3 " +
            "WHERE t3.SOUKO_ID = '" + souko.getSouko_id() + "' " +
            "AND t3.ZAIKODATE <= TO_DATE('" + sdf.format(zaikodate) +
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
