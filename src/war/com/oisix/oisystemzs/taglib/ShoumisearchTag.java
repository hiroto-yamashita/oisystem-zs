package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShoumisearchTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        Collection col = new LinkedList();
        String sql = makeSql();
        if (sql == null) {
            return null;
        }
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        HttpSession session = request.getSession();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        PreparedStatement ps = con.prepareStatement(sql);
        int index = 1;
        ps.setString(index++, souko.getSouko_id());
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
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        DecimalFormat df = new DecimalFormat("##########.##");
        while ((rows != 0) && rs.next()) {
            rows--;
            length++;
            item = new HashMap();
            index = 1;
            item.put("shouhin_id", rs.getString(index++));
            item.put("shouhin", rs.getString(index++));
            item.put("kikaku", rs.getString(index++));
            java.sql.Date shoumi = rs.getDate(index++);
            item.put("shoumi", shoumi);
            cal.setTime(shoumi);
            item.put("shoumiyear", String.valueOf(cal.get(Calendar.YEAR)));
            item.put("shoumimonth",
              String.valueOf(cal.get(Calendar.MONTH) + 1));
            item.put("shoumidate", String.valueOf(cal.get(Calendar.DATE)));
            java.sql.Date shukka = rs.getDate(index++);
            item.put("shukkakigen", sdf.format(shukka));
            item.put("suuryou", df.format(rs.getFloat(index++)));
            item.put("shoumikigennissuu", df.format(rs.getInt(index++)));
            item.put("shukkakigennissuu", df.format(rs.getInt(index++)));
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

    public String makeSql() {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        Enumeration params = request.getParameterNames();
        String shouhin_ids = null;
        while (params.hasMoreElements()) {
            String key = (String)params.nextElement();
            if (key.startsWith("shouhin_id")) {
                String shouhin_id = request.getParameter(key);
                if ((shouhin_id != null) && (!shouhin_id.equals(""))) {
                    if (shouhin_ids == null) {
                        shouhin_ids = "'" + shouhin_id + "'";
                    } else {
                        shouhin_ids += ",'" + shouhin_id + "'";
                    }
                }
            }
        }
        if (shouhin_ids == null) {
            return null;
        }
        String sql =
          "select " +
          "t1.shouhin_id, " +
          "t1.shouhin, " +
          "t1.kikaku, " +
          "t3.shoumikigen, " +
          "t3.shukkakigen, " +
          "t3.suuryou, " +
          "t1.shoumikigennissuu, " +
          "t1.shukkakigennissuu " +
          "from zm_shouhin t1,zt_zaiko t2,zt_zaikomeisai t3 " +
          "where  " +
          "t1.shouhin_id=t2.shouhin_id and " +
          "t2.zaiko_id=t3.zaiko_id and " +
          "t1.shouhin_id in (" + shouhin_ids + ") and " +
          "t2.souko_id=? and " +
          "t2.zorder=(select max(t4.zorder) from zt_zaiko t4 where " +
          "t4.shouhin_id=t2.shouhin_id and t4.souko_id=t2.souko_id) " +
          "order by t1.shouhin_id, t3.shoumikigen";
        return sql;
    }
}
