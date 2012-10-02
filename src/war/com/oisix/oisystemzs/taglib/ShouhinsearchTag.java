package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import com.oisix.oisystemzs.objectmap.TaniMap;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShouhinsearchTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        Debug.println("findDBCollection start", this);
        Collection col = new LinkedList();
        String sql = makeSql();
        if (sql == null) {
            //åüçıÉ{É^ÉìÇ™âüÇ≥ÇÍÇƒÇ¢Ç»Ç¢(èââÒï\é¶orñ¢ì¸óÕ)
            return null;
        }
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs == null) { Debug.println("rs null", this);return null; }
        //fetch
        HashMap item = null;
        while (rs.next()) {
            item = new HashMap();
            int index = 1;
            item.put("shouhin_id", rs.getString(index++));
            item.put("shouhinmei", rs.getString(index++));
            item.put("kikaku", rs.getString(index++));
            item.put("tani", rs.getString(index++));
            item.put("hacchuutani", rs.getString(index++));
            item.put("irisuu", rs.getString(index++));
            item.put("tanka", rs.getString(index++));
            item.put("hyoujuntanka", rs.getString(index++));
            int shoumikigennissuu = rs.getInt(index++);
            Calendar shoumikigen = Calendar.getInstance();
            shoumikigen.add(Calendar.DATE, shoumikigennissuu);
            int year = shoumikigen.get(Calendar.YEAR);
            int month = shoumikigen.get(Calendar.MONTH) + 1;
            int date = shoumikigen.get(Calendar.DATE);
            item.put("shoumiyear", (new Integer(year)).toString());
            item.put("shoumimonth", (new Integer(month)).toString());
            item.put("shoumidate", (new Integer(date)).toString());
            int shukkakigennissuu = rs.getInt(index++);
            Calendar shukkakigen = Calendar.getInstance();
            shukkakigen.add(Calendar.DATE, shukkakigennissuu);
            year = shukkakigen.get(Calendar.YEAR);
            month = shukkakigen.get(Calendar.MONTH) + 1;
            date = shukkakigen.get(Calendar.DATE);
            item.put("shukkayear", (new Integer(year)).toString());
            item.put("shukkamonth", (new Integer(month)).toString());
            item.put("shukkadate", (new Integer(date)).toString());
            int tani_id = Integer.parseInt((String)item.get("tani"));
            item.put("tanimei", TaniMap.getTani(tani_id).getTani());
            tani_id = Integer.parseInt((String)item.get("hacchuutani"));
            item.put("hacchuutanimei", TaniMap.getTani(tani_id).getTani());
            col.add(item);
        }

        rs.close();
        if (ps!= null) { ps.close(); }
        return col;
    }

    public String makeSql() {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String shouhinmei = request.getParameter("searchshouhinmei");
        if (shouhinmei == null || shouhinmei.equals("")) {
            return null;
        }
        String sql =
          "SELECT " + 
          "t1.SHOUHIN_ID, " +
          "t1.SHOUHIN, " +
          "t1.KIKAKU, " +
          "t1.TANI, " +
          "t1.HACCHUUTANI, " +
          "t1.IRISUU, " +
          "t1.TANKA, " +
          "t1.HYOUJUNTANKA, " +
          "t1.SHOUMIKIGENNISSUU, " +
          "t1.SHUKKAKIGENNISSUU " +
          "FROM ZM_SHOUHIN t1 " +
          "WHERE SHOUHIN LIKE '%" + shouhinmei + "%' " +
          "ORDER BY SHOUHIN_ID ";
        return sql;
    }
}
