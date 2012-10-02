package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

public class NouhinhenkouListTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        Debug.println("findDBCollection start", this);
        Collection col = new LinkedList();
        String sql = makeSql();
        if (sql == null) {
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
            int hacchuu_id = rs.getInt(index++);
            item.put("nyuukayotei_id", new Integer(hacchuu_id));
            item.put("hacchuu_bg", rs.getString(index++));
            item.put("hacchuu_date", rs.getDate(index++));
            item.put("nyuukayotei_date", rs.getDate(index++));
            item.put("shiiresaki_id", rs.getString(index++));
            item.put("shiiresaki_name", rs.getString(index++));
            item.put("shouhin_id", rs.getString(index++));
            item.put("shouhin", rs.getString(index++));
            item.put("hacchuukikaku", rs.getString(index++));
            item.put("hacchuusuuryou", rs.getString(index++));
            item.put("touchakujikan", rs.getString(index++));
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
        String sql =
          "SELECT " + 
          "t1.NYUUKAYOTEI_ID, " +
          "t1.HACCHUU_BG, " +
          "t2.HACCHUU_DATE, " +
          "t1.NYUUKAYOTEI_DATE, " +
          "t2.SHIIRESAKI_ID, " +
          "t3.NAME, " +
          "t1.SHOUHIN_ID, " +
          "t4.SHOUHIN, " +
          "t4.HACCHUUKIKAKU, " +
          "t1.HACCHUUSUURYOU, " +
          "t1.TOUCHAKUJIKAN " +
          "FROM ZT_NYUUKAYOTEIMEISAI t1, ZT_HACCHUU t2, ZM_SHIIRESAKI t3, " +
          "ZM_SHOUHIN t4 " +
          "WHERE t1.HACCHUU_BG=t2.HACCHUU_BG AND " +
          "t2.SHIIRESAKI_ID=t3.SHIIRESAKI_ID AND " +
          "t1.SHOUHIN_ID=t4.SHOUHIN_ID ";
        String[] hacchuu_ids = request.getParameterValues("id");
        if ((hacchuu_ids == null) || (hacchuu_ids.length == 0)) {
            return null;
        }
        StringBuffer sb = new StringBuffer("AND t2.HACCHUU_ID IN (");
        for (int i=0; i<hacchuu_ids.length; i++) {
            sb.append(hacchuu_ids[i]);
            if (i < (hacchuu_ids.length - 1)) {
                sb.append(", ");
            }
        }
        sb.append(")");
        sql += sb;
        Debug.println(sql);
        return sql;
    }
}
