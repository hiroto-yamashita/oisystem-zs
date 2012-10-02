package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;

public class ShukkayoteisearchTag extends DBCollectionTagBase {

    private final int SHUKKAZUMI = 3;

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
        while ((rows != 0) && rs.next()) {
            rows--;
            length++;
            item = new HashMap();
            int index = 1;
            int shukkayotei_id = rs.getInt(index++);
            item.put("shukkayotei_id", new Integer(shukkayotei_id));
            item.put("shukkayotei_bg", rs.getString(index++));
            item.put("shukkayotei_date", rs.getDate(index++));
            item.put("shukkamoto", rs.getString(index++));
            item.put("nouhinsaki", rs.getString(index++));
            String nouhinsaki = rs.getString(index++);
            // 入力してある納品先名優先
            if (nouhinsaki != null && !nouhinsaki.equals("")) {
                item.put("nouhinsaki", nouhinsaki);
            }
            item.put("shukkokubun", rs.getString(index++));
            int shukkajoukyou = rs.getInt(index++);
            if (shukkajoukyou == SHUKKAZUMI) {
                item.put("shukkajoukyou", "出荷済");
            } else {
                item.put("shukkajoukyou", "未出荷");
            }
            item.put("lineparam", "id=" + shukkayotei_id);
            col.add(item);
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
        String sql =
          "SELECT " + 
          "t1.SHUKKAYOTEI_ID, " +
          "t1.SHUKKAYOTEI_BG, " +
          "t1.SHUKKAYOTEI_DATE, " +
          "t2.NAME, " +
          "t3.NAME, " +
          "t1.NOUHINSAKINAME, " +
          "t4.SHUKKOKUBUN, " + 
          "MIN(t5.SHUKKAJOUKYOU) " +
          "FROM ZT_SHUKKAYOTEI t1 LEFT OUTER JOIN ZM_NOUHINSAKI t3 " +
          "ON t3.NOUHINSAKI_ID=t1.NOUHINSAKI_ID, " +
          "ZM_SOUKO t2, " +
          "ZM_SHUKKOKUBUN t4, ZT_SHUKKAYOTEIMEISAI t5 " +
          "WHERE t1.SHUKKAMOTO_ID = t2.SOUKO_ID " +
          "AND t1.SHUKKOKUBUN = t4.SHUKKOKUBUN_ID " +
          "AND t1.SHUKKAYOTEI_BG = t5.SHUKKAYOTEI_BG ";
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String opt = request.getParameter("searchoption");
        String havingSql = "";
        if (opt != null && opt.equals("shukkayotei_bg")) {
            String shukkayotei_bg = request.getParameter("#shukkayotei_bg");
            if ((shukkayotei_bg != null) && (!shukkayotei_bg.equals(""))) {
                sql += "AND t1.SHUKKAYOTEI_BG = '" + shukkayotei_bg + "' ";
            }
        }
        else if (opt != null && opt.equals("any_conditions")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fryear = request.getParameter("#fromyear");
            String frmonth = request.getParameter("#frommonth");
            String frdates = request.getParameter("#fromdate");
            java.util.Date frdate = DateUtil.getDate(fryear, frmonth, frdates);
            String toyear = request.getParameter("#toyear");
            String tomonth = request.getParameter("#tomonth");
            String todates = request.getParameter("#todate");
            Calendar cal = DateUtil.getCalendar(toyear, tomonth, todates);
            cal.add(Calendar.DATE, 1);
            java.util.Date todate = cal.getTime();
            sql += "AND t1.SHUKKAYOTEI_DATE >= '" + sdf.format(frdate) + "' " +
                   "AND t1.SHUKKAYOTEI_DATE <= '" + sdf.format(todate) + "' ";
            String mishukko = request.getParameter("#mishukko_flg");
            if ((mishukko != null) && mishukko.equals("on")) {
                sql += "AND t5.SHUKKAJOUKYOU<>" + SHUKKAZUMI + " ";
            }
            String shubetsu = request.getParameter("#shukkayoteishubetsu");
            if ((shubetsu != null) && (!shubetsu.equals(""))) {
                sql += "AND t1.SHUKKAYOTEISHUBETSU = " + shubetsu + " ";
            }
        }
        else {
            // 検索する前
            return null;
        }
        sql += "GROUP BY " +
               "t1.SHUKKAYOTEI_ID, " +
               "t1.SHUKKAYOTEI_BG, " +
               "t1.SHUKKAYOTEI_DATE, " +
               "t2.NAME, " +
               "t3.NAME, " +
               "t1.NOUHINSAKINAME, " +
               "t4.SHUKKOKUBUN ";
        sql += "ORDER BY t1.SHUKKAYOTEI_BG DESC ";
        return sql;
    }
}
