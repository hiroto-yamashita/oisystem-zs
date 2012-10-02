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

public class ShukkosearchTag extends DBCollectionTagBase {

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
            int shukko_id = rs.getInt(index++);
            item.put("shukko_id", new Integer(shukko_id));
            item.put("shukko_bg", rs.getString(index++));
            item.put("shukko_date", rs.getDate(index++));
            item.put("shukkokubun", rs.getString(index++));
            item.put("shouhinmei", rs.getString(index++));
            item.put("suuryou", new Float(rs.getFloat(index++)));
            item.put("kingaku", new Float(rs.getFloat(index++)));
            item.put("shiiresaki", rs.getString(index++));
            item.put("shukkokubun_id", rs.getString(index++));
            item.put("lineparam", "id=" + shukko_id);
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
          "t1.SHUKKO_ID, " +
          "t1.SHUKKO_BG, " +
          "t1.SHUKKO_DATE, " +
          "t2.SHUKKOKUBUN, " +
          "t3.SHOUHIN, " + 
          "t1.SUURYOU, " + 
          "t1.KINGAKU, " +
          "t4.NAME, " +
          "t1.SHUKKOKUBUN " +
          "FROM ZT_SHUKKO t1, ZM_SHUKKOKUBUN t2, ZM_SHOUHIN t3 " +
          "LEFT OUTER JOIN ZM_SHIIRESAKI t4 " + 
          "ON t4.SHIIRESAKI_ID=t3.SHIIRESAKI_ID " +
          "WHERE t1.SHUKKOKUBUN = t2.SHUKKOKUBUN_ID " +
          "AND t1.SHOUHIN_ID = t3.SHOUHIN_ID ";
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String opt = request.getParameter("searchoption");
        if (opt != null && opt.equals("shukko_bg")) {
            String shukko_bg = request.getParameter("#shukko_bg");
            if ((shukko_bg != null) && (!shukko_bg.equals(""))) {
                sql += "AND t1.SHUKKO_BG = '" + shukko_bg + "' ";
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
            sql += "AND t1.SHUKKO_DATE >= '" + sdf.format(frdate) + "' " +
                   "AND t1.SHUKKO_DATE <= '" + sdf.format(todate) + "' ";
            String shouhin_id = request.getParameter("#shouhin_id");
            if ((shouhin_id != null) && (!shouhin_id.equals(""))) {
                sql += "AND t1.SHOUHIN_ID = '" + shouhin_id + "' ";
            }
            String shukkokubun = request.getParameter("#shukkokubun");
            if ((shukkokubun != null) && (!shukkokubun.equals(""))) {
                sql += "AND t1.SHUKKOKUBUN = " + shukkokubun + " ";
            }
            String daibunruicheck = request.getParameter("#daibunruicheck");
            if ((daibunruicheck != null) && (!daibunruicheck.equals(""))) {
                String daibunrui = request.getParameter("#daibunrui");
                sql += "AND t3.DAIBUNRUI='" + daibunrui + "' ";
            }
            String shiiresaki_id = request.getParameter("#shiiresaki_id");
            if ((shiiresaki_id != null) && (!shiiresaki_id.equals(""))) {
                sql += "AND t3.shiiresaki_id='" + shiiresaki_id + "' ";
            }
        }
        else {
            // 検索する前
            return null;
        }
        sql += "ORDER BY t1.SHUKKO_BG DESC ";
        return sql;
    }
}
