package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.servlet.http.HttpServletRequest;

public class NyuukokakuteiinputTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        DecimalFormat df = new DecimalFormat("##########.##########");
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
            item.put("nyuukayotei_id", new Integer(rs.getInt(index++)));
            item.put("hacchuu_bg",rs.getString(index++));
            item.put("shiiremei",rs.getString(index++));
            item.put("shouhin_id",rs.getString(index++));
            item.put("shouhin",rs.getString(index++));
            item.put("kikaku",rs.getString(index++));
            item.put("shoumikigen",rs.getDate(index++));
            item.put("shukkakigen",rs.getDate(index++));
            item.put("nyuukokubun",rs.getString(index++));
            float nyuukosuuryou = rs.getFloat(index++);
            item.put("nyuukotani", rs.getString(index++));
            float shiiresuuryou = rs.getFloat(index++);
            item.put("shiiretani", rs.getString(index++));
            float shiiretanka = rs.getFloat(index++);
            item.put("shiiretanka", df.format(shiiretanka));
            float kingaku = rs.getFloat(index++);
            //item.put("kingaku", df.format(rs.getFloat(index++)));
            float jitsunyuukasuu = rs.getFloat(index++);

            shiiresuuryou = shiiresuuryou -
              shiiresuuryou * jitsunyuukasuu / nyuukosuuryou;
            nyuukosuuryou = nyuukosuuryou - jitsunyuukasuu;
            item.put("nyuukosuuryou", df.format(nyuukosuuryou));
            item.put("shiiresuuryou", df.format(shiiresuuryou));
            item.put("kingaku", df.format(shiiresuuryou * shiiretanka));

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
          "t1.NYUUKAYOTEI_ID, " +
          "t1.HACCHUU_BG, " + 
          "t2.NAME, " +
          "t4.SHOUHIN_ID, " + 
          "t4.SHOUHIN, " + 
          "t4.KIKAKU, " + 
          "t1.SHOUMIKIGEN, " + 
          "t1.SHUKKAKIGEN, " + 
          "t5.NYUUKOKUBUN, " +
          "t1.NYUUKASUURYOU, " + 
          "t1.NYUUKATANI, " + 
          "t1.HACCHUUSUURYOU, " + 
          "t1.HACCHUUTANI, " + 
          "t1.HACCHUUTANKA, " +
          "t1.HACCHUUSUURYOU*t1.HACCHUUTANKA, " +
          "t1.JITSUNYUUKASUU " +
          "FROM ZT_NYUUKAYOTEIMEISAI t1, ZM_SHIIRESAKI t2, " +
          "ZT_HACCHUU t3, ZM_SHOUHIN  t4, ZM_NYUUKOKUBUN t5 "+
          "WHERE t1.HACCHUU_BG=t3.HACCHUU_BG AND t2.SHIIRESAKI_ID = t3.SHIIRESAKI_ID " +
          " AND t1.SHOUHIN_ID=t4.SHOUHIN_ID AND t1.NYUUKOKUBUN = t5.NYUUKOKUBUN_ID " +
          " AND t1.NYUUKAJOUKYOU IN (1,2) ";
        String[] nyuukayotei_id = request.getParameterValues("nyuukayotei_id");
        if ((nyuukayotei_id == null) || (nyuukayotei_id.length == 0)) {
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
        sql += " ORDER BY t1.NYUUKAYOTEI_DATE , t2.SHIIRESAKI_ID DESC" ;
        Debug.println(sql);
        return sql;
    }
}
