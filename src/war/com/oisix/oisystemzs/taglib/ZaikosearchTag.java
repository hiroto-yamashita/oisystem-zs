package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ZaikosearchTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        Debug.println("findDBCollection start", this);
        Collection col = new LinkedList();
        String sql = makeSql();
        if (sql == null) {
            //検索ボタンが押されていない(初回表示)
            return null;
        }
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs == null) { Debug.println("rs null", this);return null; }
        int length = 0;
        //skip to startindex
        if (startind > 0) {
            int skipcount = startind;
            while (skipcount > 0) {
                skipcount--;
                if (rs.next()) {
                    hasprev = true;
                    length++;
                } else {
                    skipcount = 0;
                }
            }
        }

        //fetch
        HashMap item = null;
        int rows = size;
        DecimalFormat df = new DecimalFormat("##########.##");
        while ((rows != 0) && rs.next()) {
            rows--;
            length++;
            item = new HashMap();
            int index = 1;
            int zaikomeisai_id = rs.getInt(index++);
            item.put("shouhin_id", rs.getString(index++));
            item.put("shouhinmei", rs.getString(index++));
            item.put("kikaku", rs.getString(index++));
            java.util.Date shoumikigen = rs.getDate(index++);
            if (shoumikigen != null) {
                item.put("shoumikigen", shoumikigen);
            }
            java.util.Date shukkakigen = rs.getDate(index++);
            if (shukkakigen != null) {
                item.put("shukkakigen", shukkakigen);
            }
            item.put("zaikodate", rs.getDate(index++));
            float meisaisuuryou = rs.getFloat(index++);
            float suuryou = rs.getFloat(index++);
            if (zaikomeisai_id != 0) {
                suuryou = meisaisuuryou;
            }
            //item.put("suuryou", String.valueOf(suuryou));
            item.put("suuryou", df.format(suuryou));
            if (shukkakigen == null) {
                item.put("kigeneditstart", "<!--");
                item.put("kigeneditend", "-->");
            }
            item.put("lineparam", "id=" + zaikomeisai_id);
            int shoumikigen_flg = rs.getInt(index++);
            if ((shoumikigen_flg == Names.OFF) || (shukkakigen != null)) {
                //賞味期限切れ検索の場合、shukkakigen==nullは賞味期限が
                //切れていない在庫
                //通常の検索でも賞味期限管理しない商品はshoumikigen_flg==OFF
                col.add(item);
            }
        }

        //set numitems
        while (rs.next()) {
            length++;
            hasnext = true;
        }

        numitems = length;
        Debug.println("numitems:"+numitems);
        Debug.println("startind:"+startind);
        Debug.println("size:"+size);
        Debug.println("hasprev:"+hasprev);
        Debug.println("hasnext:"+hasnext);
        rs.close();
        if (ps!= null) { ps.close(); }
        return col;
    }

    public String makeSql() {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String opt = request.getParameter("searchoption");
        HttpSession session = request.getSession();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        String souko_id = souko.getSouko_id();
        String sql =
          "SELECT " +
          "t2.ZAIKOMEISAI_ID, " +
          "m1.SHOUHIN_ID, " +
          "m1.SHOUHIN, " +
          "m1.KIKAKU, " +
          "t2.SHOUMIKIGEN, " +
          "t2.SHUKKAKIGEN, " +
          "t1.ZAIKODATE, " +
          "t2.SUURYOU, " +
          "t1.SUURYOU, " +
          "m1.SHOUMIKIGEN_FLG " +
          "FROM ZT_ZAIKO t1 LEFT OUTER JOIN ZT_ZAIKOMEISAI t2 " +
          "ON t2.ZAIKO_ID=t1.ZAIKO_ID, ZM_SHOUHIN m1, " +
          "(SELECT " +
            "MAX(t3.ZORDER) AS MZORDER, " +
            "t3.SHOUHIN_ID, " +
            "t3.SOUKO_ID " +
            "FROM ZT_ZAIKO t3 " +
            "WHERE t3.SOUKO_ID = '" + souko_id + "' ";
        java.util.Date zaikoday = null;
        if (opt != null && opt.equals("someconditions")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String toyear = request.getParameter("#toyear");
            String tomonth = request.getParameter("#tomonth");
            String todate = request.getParameter("#todate");
            Calendar cal = DateUtil.getCalendar(toyear, tomonth, todate);
            cal.add(Calendar.DATE, 1);
            java.util.Date today = cal.getTime();
            sql += "AND t3.ZAIKODATE<='" + sdf.format(today) + "' ";
            String shouhin_id = request.getParameter("#shouhin_id");
            if ((shouhin_id != null) && (!shouhin_id.equals(""))) {
                sql += "AND t3.SHOUHIN_ID = '" + shouhin_id + "' ";
            }
        } else if (opt != null && opt.equals("shukkakigengire")) {
            String zaikoyear = request.getParameter("#zaikoyear");
            String zaikomonth = request.getParameter("#zaikomonth");
            String zaikodate = request.getParameter("#zaikodate");
            Calendar cal = DateUtil.getCalendar(
              zaikoyear, zaikomonth, zaikodate);
            cal.add(Calendar.DATE, 1);
            zaikoday = cal.getTime();
        } else {
            // 検索する前
            return null;
        }
        sql += "GROUP BY " +
               "t3.SHOUHIN_ID, " +
               "t3.SOUKO_ID) t4 ";
        sql += "WHERE t1.SHOUHIN_ID = m1.SHOUHIN_ID " +
          "AND t2.SUURYOU > 0 " +
          "AND t1.ZORDER=t4.MZORDER " +
          "AND t1.SHOUHIN_ID=t4.SHOUHIN_ID " + 
          "AND t1.SOUKO_ID=t4.SOUKO_ID ";
        if (opt != null && opt.equals("shukkakigengire")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sql += "AND t2.SHUKKAKIGEN<'" + sdf.format(zaikoday) + "' ";
        }
        sql += "ORDER BY " +
               "t2.SHOUHIN_ID, " +
               "t2.LOCATION_ID, " +
               "t1.ZAIKODATE, " +
               "t2.SHOUMIKIGEN ";
        Debug.println(sql);
        return sql;
    }
}
