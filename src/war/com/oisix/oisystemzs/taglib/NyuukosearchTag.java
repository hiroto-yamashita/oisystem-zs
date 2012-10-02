package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.text.DecimalFormat;

public class NyuukosearchTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        Debug.println("findDBCollection start", this);
        DecimalFormat df = new DecimalFormat("##########.##");
        Collection col = new LinkedList();
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String shouhin_id = request.getParameter("#shouhin_id");
        String nyuuko_bg = request.getParameter("#nyuuko_bg");

        String beginyearstr = request.getParameter("#beginyear");
        java.sql.Date begin = null;
        java.sql.Date end = null;
        if (beginyearstr != null) {
            String beginmonthstr = request.getParameter("#beginmonth");
            String begindatestr = request.getParameter("#begindate");
            java.util.Date begindate = null;
            try{
                begindate = DateUtil.getDate(
                  beginyearstr, beginmonthstr, begindatestr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
            }
            begin = new java.sql.Date(begindate.getTime());

            String endyearstr = request.getParameter("#endyear");
            String endmonthstr = request.getParameter("#endmonth");
            String enddatestr = request.getParameter("#enddate");
            java.util.Date enddate = null;
            try{
                enddate = DateUtil.getDate(
                  endyearstr, endmonthstr, enddatestr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
            }
            end = new java.sql.Date(enddate.getTime());
        }

        String sql =
          "SELECT " + 
          "t1.NYUUKO_ID, " +
          "t1.NYUUKO_BG, " +
          "t1.NYUUKO_DATE, " +
          "t3.NYUUKOKUBUN, " + 
          "t4.NAME, " + 
          "t2.SHOUHIN_ID, " + 
          "t2.SHOUHIN, " + 
          "t2.KIKAKU, " + 
          "t1.NYUUKOSUURYOU, " + 
          "t1.NYUUKOTANI, " + 
          "t1.SHIIRESUURYOU, " + 
          "t1.SHIIRETANI, " + 
          "t1.NYUUKOTANKA, " + 
          "t1.NYUUKOTANKA*t1.NYUUKOSUURYOU, " + 
          "t1.SHOUMIKIGEN, " + 
          "t1.SHUKKAKIGEN " +
          "FROM " +
          "ZT_NYUUKO t1 LEFT OUTER JOIN ZM_SHIIRESAKI t4 " + 
          "ON t1.SHIIRESAKI_ID=t4.SHIIRESAKI_ID, " +
          "ZM_SHOUHIN t2, ZM_NYUUKOKUBUN t3 " +
          "WHERE t1.SHOUHIN_ID = t2.SHOUHIN_ID " +
          "AND t1.NYUUKOKUBUN = t3.NYUUKOKUBUN_ID ";
        if ((nyuuko_bg != null) && (!nyuuko_bg.equals(""))) {
            sql += "AND t1.NYUUKO_BG = ? ";
        } else {
            if ((shouhin_id != null) && (!shouhin_id.equals(""))) {
                sql += "AND t2.SHOUHIN_ID = ?";
            }
            sql += " AND t1.NYUUKO_DATE >= ?" +
              " AND t1.NYUUKO_DATE <= ?";
        }

        PreparedStatement ps = con.prepareStatement(sql);
        int ind = 1;
        if ((nyuuko_bg != null) && (!nyuuko_bg.equals(""))) {
            ps.setString(ind++, nyuuko_bg);
        } else {
            if ((shouhin_id != null) && (!shouhin_id.equals(""))) {
                ps.setString(ind++, shouhin_id);
            }
            ps.setDate(ind++, begin);
            ps.setDate(ind++, end);
        }
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
            int nyuuko_id = rs.getInt(index++);
            item.put("nyuuko_id", new Integer(nyuuko_id));
            item.put("nyuuko_bg", rs.getString(index++));
            item.put("nyuuko_date", rs.getDate(index++));
            item.put("nyuukokubun", rs.getString(index++));
            item.put("shiiresaki", rs.getString(index++));
            item.put("shouhin_id", rs.getString(index++));
            item.put("shouhin", rs.getString(index++));
            item.put("kikaku", rs.getString(index++));
            item.put("nyuukosuuryou", df.format(rs.getFloat(index++)));
            item.put("nyuukotani", rs.getString(index++));
            item.put("shiiresuuryou", df.format(rs.getFloat(index++)));
            item.put("shiiretani", rs.getString(index++));
            item.put("nyuukotanka", df.format(rs.getFloat(index++)));
            item.put("kingaku", df.format(rs.getFloat(index++)));
            item.put("shoumikigen", rs.getDate(index++));
            item.put("shukkakigen", rs.getDate(index++));
            item.put("lineparam", "nyuuko_id="+nyuuko_id);
            col.add(item);
        }

        //set numitems
        while (rs.next()) {
            length++;
            hasnext = true;
        }

        numitems = length;
        rs.close();
        if (ps!= null) { ps.close(); }
        return col;
    }

}
