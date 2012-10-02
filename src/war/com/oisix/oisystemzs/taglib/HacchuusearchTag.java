package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HacchuusearchTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        Debug.println("findDBCollection start", this);
        Collection col = new LinkedList();
        String sql = makeSql();
        if (sql == null) {
            //検索ボタンが押されていない(初回表示)
            return null;
        }
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        HttpSession session = request.getSession();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        String souko_id = souko.getSouko_id();

        PreparedStatement ps = con.prepareStatement(sql);
        int ind = 1;
        ps.setString(ind++, souko_id);
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
            int hacchuu_id = rs.getInt(index++);
            item.put("hacchuu_id", new Integer(hacchuu_id));
            item.put("hacchuu_bg", rs.getString(index++));
            item.put("hacchuu_date", rs.getDate(index++));
            item.put("shiiresaki_id", rs.getString(index++));
            item.put("shiiresaki_name", rs.getString(index++));
            item.put("status", rs.getString(index++));
            // 削除、詳細リンク用
            item.put("lineparam", "id=" + hacchuu_id);
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
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        if (request.getParameter("#year") == null) {
            return null;
        }
        String sql =
          "SELECT " + 
          "t1.HACCHUU_ID, " +
          "t1.HACCHUU_BG, " +
          "t1.HACCHUU_DATE, " + 
          "t1.SHIIRESAKI_ID, " + 
          "t2.NAME, " +
          "MIN(t3.STATUS) " +
          "FROM ZT_HACCHUU t1 LEFT OUTER JOIN ZM_SHIIRESAKI t2 " +
          "ON t1.SHIIRESAKI_ID = t2.SHIIRESAKI_ID, ZT_NYUUKAYOTEIMEISAI t3 " +
          "WHERE t1.HACCHUU_BG = t3.HACCHUU_BG AND " +
          "t1.SAKUJO_FLG= 0 AND " +
          "t3.SOUKO_ID = ? ";
        String hacchuubi = request.getParameter("#hacchuubi");
        boolean inputflg = false;
        if ((hacchuubi != null) && (!hacchuubi.equals(""))) {
            inputflg = true;
            String yearstr = request.getParameter("#year");
            String monthstr = request.getParameter("#month");
            String datestr = request.getParameter("#date");
            int year = 2002;
            int month = 1;
            int date = 1;
            try {
                year = Integer.parseInt(yearstr);
                month = Integer.parseInt(monthstr);
                date = Integer.parseInt(datestr);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            sql += "AND YEAR(t1.HACCHUU_DATE) = " + year +
                  " AND MONTH(t1.HACCHUU_DATE) = " + month +
                  " AND DAYOFMONTH(t1.HACCHUU_DATE) = " + date;
        }
        String shiiresaki_id = request.getParameter("#shiiresaki_id");
        if ((shiiresaki_id != null) && (!shiiresaki_id.equals(""))) {
            inputflg = true;
            sql += " AND t1.SHIIRESAKI_ID='" + shiiresaki_id + "'";
        }
        String hacchuu_bg = request.getParameter("#hacchuu_bg");
        if ((hacchuu_bg != null) && (!hacchuu_bg.equals(""))) {
            inputflg = true;
            sql += " AND t1.HACCHUU_BG = '" + hacchuu_bg + "'";
        }
        sql += " GROUP BY t1.HACCHUU_ID, t1.HACCHUU_BG, t1.HACCHUU_DATE, " +
          "t1.SHIIRESAKI_ID, t2.NAME";
        if (!inputflg) { return null; }
        Debug.println(sql);
        return sql;
    }
}
