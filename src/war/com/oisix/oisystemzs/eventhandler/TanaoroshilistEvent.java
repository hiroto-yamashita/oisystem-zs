package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.ServiceLocator;
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

public class TanaoroshilistEvent extends EventHandlerSupport {

    private java.util.Date zaikodate;
    private SoukoData souko;
    private String souko_id;
    private String filename;
    private String[] daibunruis;
    private boolean isYotei;

    public String getFileName() { return filename; }

    public void init(HttpServletRequest request) {
        String yearstr = request.getParameter("year");
        String monthstr = request.getParameter("month");
        String datestr = request.getParameter("date");
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

        daibunruis = request.getParameterValues("daibunrui");

        String isyoteistr = request.getParameter("isyotei");
        if ((isyoteistr != null) && (isyoteistr.equals("true"))) {
            isYotei = true;
        } else {
            isYotei = false;
        }

        souko = (SoukoData)session.getAttribute("SOUKO");
        souko_id = souko.getSouko_id();
    }

    public void handleEvent(HashMap attr) {
        if (zaikodate == null) { return; }

        TanaoroshilistPdf listpdf = new TanaoroshilistPdf();
        listpdf.setZaikodate(zaikodate);

        // 棚卸リストコレクション作成
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement nyps = null;
        ResultSet nyrs = null;
        PreparedStatement syps = null;
        ResultSet syrs = null;
        try {
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();

            Calendar cal = DateUtil.getCalendar();
            cal.setTime(zaikodate);
            cal.add(Calendar.DATE, 1);
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            java.sql.Date searchDate =new java.sql.Date(cal.getTimeInMillis());

            HashMap nymap = new HashMap();
            HashMap symap = new HashMap();
            if (isYotei) {
                String sql =
                  "SELECT " +
                  "SHOUHIN_ID, " +
                  "SUM(NYUUKASUURYOU-JITSUNYUUKASUU) " +
                  "FROM ZT_NYUUKAYOTEIMEISAI " +
                  "WHERE SOUKO_ID=? AND " +
                  "NYUUKAYOTEI_DATE<? AND " +
                  "(NYUUKAJOUKYOU=1 OR NYUUKAJOUKYOU=2) " +
                  "GROUP BY SHOUHIN_ID";
                nyps = con.prepareStatement(sql);
                int index = 1;
                nyps.setString(index++, souko_id);
                nyps.setDate(index++, searchDate);
                nyrs = nyps.executeQuery();
                while (nyrs.next()) {
                    index = 1;
                    String shouhin_id = nyrs.getString(index++);
                    nymap.put(shouhin_id, new Float(nyrs.getFloat(index++)));
                }
                nyrs.close();
                nyps.close();

                sql = 
                  "SELECT " +
                  "t1.SHOUHIN_ID, " +
                  "SUM(t1.SHUKKAYOTEISUURYOU-t1.JITSUSHUKKASUURYOU) " +
                  "FROM ZT_SHUKKAYOTEIMEISAI t1, ZT_SHUKKAYOTEI t2 " +
                  "WHERE t1.SHUKKAYOTEI_BG=t2.SHUKKAYOTEI_BG AND " +
                  "t1.SOUKO_ID=? AND " +
                  "t2.SHUKKAYOTEI_DATE<? AND " +
                  "(t1.SHUKKAJOUKYOU=1 OR t1.SHUKKAJOUKYOU=2) " +
                  "GROUP BY t1.SHOUHIN_ID";
                syps = con.prepareStatement(sql);
                index = 1;
                syps.setString(index++, souko_id);
                syps.setDate(index++, searchDate);
                syrs = syps.executeQuery();
                while (syrs.next()) {
                    index = 1;
                    String shouhin_id = syrs.getString(index++);
                    symap.put(shouhin_id, new Float(syrs.getFloat(index++)));
                }
                syrs.close();
                syps.close();
            }

            String daibunruiargs = "";
            if (daibunruis != null) {
                for (int i=0; i<daibunruis.length; i++) {
                    if (i == 0) {
                        daibunruiargs +=
                          "and (m1.daibunrui='" + daibunruis[0] + "'";
                    } else {
                        daibunruiargs +=
                          " or m1.daibunrui='" + daibunruis[i] + "'";
                    }
                }
                if (daibunruiargs.length() > 1) {
                    daibunruiargs += ") ";
                }
            }
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
              "m1.LOCATION_ID3, " +
              "m1.SHUUBAI_FLG, " +
              "m1.DAIBUNRUI " +
              "FROM ZT_ZAIKO t1 LEFT OUTER JOIN ZT_ZAIKOMEISAI t2 " +
              "ON t2.ZAIKO_ID=t1.ZAIKO_ID, ZM_SHOUHIN m1, " +
              "(SELECT " +
              "MAX(t3.ZORDER) AS MZORDER, " +
              "t3.SHOUHIN_ID, " +
              "t3.SOUKO_ID " +
              "FROM ZT_ZAIKO t3 " +
              "WHERE t3.SOUKO_ID = ? " +
              "AND t3.ZAIKODATE < ? " +
              "GROUP BY " +
              "t3.SHOUHIN_ID, " +
              "t3.SOUKO_ID) t4 " +
              "WHERE t1.SHOUHIN_ID = m1.SHOUHIN_ID " +
              "AND t1.SUURYOU > 0 " +
              "AND t1.ZORDER=t4.MZORDER " +
              "AND t1.SHOUHIN_ID=t4.SHOUHIN_ID " +
              "AND t1.SOUKO_ID=t4.SOUKO_ID " +
              daibunruiargs +
              "ORDER BY " +
              "m1.LOCATION_ID1, " +
              "t1.SHOUHIN_ID, " +
              "t2.SHOUMIKIGEN ";
            ps = con.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, souko_id);
            ps.setDate(index++, searchDate);
            rs = ps.executeQuery();
            if (rs == null) {
                Debug.println("rs null", this);
                return;
            }
            LinkedList tanaoroshilist = new LinkedList();
            HashMap shouhincount = new HashMap();
            String preshouhin_id = null;
            LinkedList meisailist = null;
            while (rs.next()) {
                // 棚卸情報の取得
                index = 1;
                HashMap map = new HashMap();
                String shouhin_id = rs.getString(index++);
                String shouhinmei = rs.getString(index++);
                String kikaku = rs.getString(index++);
                java.util.Date shoumikigen = rs.getDate(index++);
                java.util.Date shukkakigen = rs.getDate(index++);
                float meisaisuuryou = rs.getFloat(index++);
                float zaikosuuryou = rs.getFloat(index++);
                String location_id1 = rs.getString(index++);
                String location_id2 = rs.getString(index++);
                String location_id3 = rs.getString(index++);
                String shuubai_flg = rs.getString(index++);
                String daibunrui = rs.getString(index++);
                if (!shouhin_id.equals(preshouhin_id)) {
                    preshouhin_id = shouhin_id;
                    if (meisailist != null) {
                        tanaoroshilist.add(meisailist);
                    }
                    meisailist = new LinkedList();
                    Float nyuukayoteisuu = (Float)nymap.get(shouhin_id);
                    if (nyuukayoteisuu != null) {
                        HashMap nyuukayotei = new HashMap();
                        nyuukayotei.put("SHOUHIN_ID", shouhin_id);
                        nyuukayotei.put("SHOUHINMEI", shouhinmei);
                        nyuukayotei.put("KIKAKU", kikaku);
                        nyuukayotei.put("SHUKKAKIGEN", null);
                        nyuukayotei.put("LOCATION_ID1", location_id1);
                        nyuukayotei.put("LOCATION_ID2", location_id2);
                        nyuukayotei.put("LOCATION_ID3", location_id3);
                        nyuukayotei.put("SUURYOU", nyuukayoteisuu);
                        nyuukayotei.put("SHOUMIKIGEN", null);
                        nyuukayotei.put("SHUUBAI_FLG", shuubai_flg);
                        nyuukayotei.put("DAIBUNRUI", daibunrui);
                        nyuukayotei.put("NYUUKAYOTEI", "TRUE");
                        meisailist.add(nyuukayotei);
                        nymap.remove(shouhin_id);
                        Integer count = (Integer)shouhincount.get(shouhin_id);
                        if (count == null) {
                            count = new Integer(1);
                        } else {
                            count = new Integer(count.intValue() + 1);
                        }
                        shouhincount.put(shouhin_id, count);
                    }
                }
                map.put("SHOUHIN_ID", shouhin_id);
                map.put("SHOUHINMEI", shouhinmei);
                map.put("KIKAKU", kikaku);
                map.put("SHUKKAKIGEN", shukkakigen);
                map.put("LOCATION_ID1", location_id1);
                map.put("LOCATION_ID2", location_id2);
                map.put("LOCATION_ID3", location_id3);
                if (shoumikigen == null) {
                    meisaisuuryou = zaikosuuryou;
                }
                if (isYotei) {
                    Float shukkayoteisuu = (Float)symap.get(shouhin_id);
                    if (shukkayoteisuu != null) {
                        float sysuu = shukkayoteisuu.floatValue();
                        if (meisaisuuryou < sysuu) {
                            sysuu = meisaisuuryou;
                        }
                        meisaisuuryou = meisaisuuryou - sysuu;
                        float aftersuu = shukkayoteisuu.floatValue();
                        aftersuu = aftersuu - sysuu;
                        if (aftersuu == 0) {
                            symap.remove(shouhin_id);
                        } else {
                            symap.put(shouhin_id, new Float(aftersuu));
                        }
                    }
                }
                map.put("SUURYOU", new Float(meisaisuuryou));
                map.put("SHOUMIKIGEN", shoumikigen);
                map.put("SHUUBAI_FLG", shuubai_flg);
                map.put("DAIBUNRUI", daibunrui);
                meisailist.add(map);
                Integer count = (Integer)shouhincount.get(shouhin_id);
                if (count == null) {
                    count = new Integer(1);
                } else {
                    count = new Integer(count.intValue() + 1);
                }
                shouhincount.put(shouhin_id, count);
            }
            if ((meisailist != null) && (!meisailist.isEmpty())) {
                tanaoroshilist.add(meisailist);
            }

            listpdf.setTanaoroshilist(tanaoroshilist);
            listpdf.setShouhincount(shouhincount);
            try {
                listpdf.makeDocument();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
                //Debug.println(ex);
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
                if (nyrs != null) { nyrs.close(); }
                if (nyps != null) { nyps.close(); }
                if (syrs != null) { syrs.close(); }
                if (syps != null) { syps.close(); }
                if (con != null) { con.close(); }
            }
            catch (SQLException sqle) { Debug.println(sqle); }
        }
    }
}
