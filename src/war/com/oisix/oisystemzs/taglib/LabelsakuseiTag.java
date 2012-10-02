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

public class LabelsakuseiTag extends DBCollectionTagBase {

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
        while((rows != 0) && rs.next()) {
            rows--;
            length++;
            item = new HashMap();
            int index =1;
            item.put("hacchuu_bg",rs.getString(index++));
            item.put("nyuukayotei_id",new Integer(rs.getInt(index++)));
            item.put("shouhin_id",rs.getString(index++));
            item.put("shouhin",rs.getString(index++));
            item.put("hacchuusuuryou",new Float(rs.getFloat(index++)));
            item.put("shoumikigen",rs.getDate(index++));
            item.put("shukkakigen",rs.getDate(index++));
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
          "SELECT "+ 
          "t1.HACCHUU_BG, " + 
          "t1.NYUUKAYOTEI_ID, " + 
          "t2.SHOUHIN_ID, " + 
          "t2.SHOUHIN, " + 
          "t1.HACCHUUSUURYOU, " + 
          "t1.SHOUMIKIGEN," + 
          "t1.SHUKKAKIGEN " + 
          "FROM ZT_NYUUKAYOTEIMEISAI t1, ZM_SHOUHIN  t2 " + 
          "WHERE t1.SHOUHIN_ID=t2.SHOUHIN_ID ";
        String[] nyuukayotei_id = request.getParameterValues("nyuukayotei_id");
        if ((nyuukayotei_id == null) || (nyuukayotei_id.equals(""))) {
            return null;
        }
        StringBuffer sb = new StringBuffer("AND t1.NYUUKAYOTEI_ID IN (");
        for (int i=0; i<nyuukayotei_id.length; i++) {
            sb.append("'").append(nyuukayotei_id[i]).append("'");
            if (i < (nyuukayotei_id.length - 1)) {
                sb.append(", ");
            }
        }
        sb.append(")");
        sql += sb;
        Debug.println(sql);
        return sql;
    }
}
