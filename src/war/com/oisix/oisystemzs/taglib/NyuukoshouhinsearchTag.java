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

public class NyuukoshouhinsearchTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        Debug.println("findDBCollection start", this);
        Collection col = new LinkedList();
        String sql = makeSql();
        if (sql == null) {
            //ŒŸõƒ{ƒ^ƒ“‚ª‰Ÿ‚³‚ê‚Ä‚¢‚È‚¢(‰‰ñ•\Ž¦or–¢“ü—Í)
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
            item.put("shouhin_id", rs.getString(index++));
            item.put("shouhinmei", rs.getString(index++));
            item.put("kikaku", rs.getString(index++));
            item.put("shoumikigennissuu",new Integer(rs.getInt(index++)));
            item.put("shukkakigennissuu",new Integer(rs.getInt(index++)));
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
        String shouhinmei = request.getParameter("#searchshouhinmei");
        if (shouhinmei == null || shouhinmei.equals("")) {
            return null;
        }
        String sql =
          "SELECT " + 
          "t1.SHOUHIN_ID, " +
          "t1.SHOUHIN, " +
          "t1.KIKAKU " +
          "t1.SHOUMIKIGENNISSUU " +
          "t1.SHUKKAKIGENNISSUU " +
          "FROM ZM_SHOUHIN t1 " +
          "WHERE SHOUHIN LIKE '%" + shouhinmei + "%' ";
        return sql;
    }
}
