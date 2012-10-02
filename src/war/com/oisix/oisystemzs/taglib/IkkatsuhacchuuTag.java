package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.objectmap.TaniMap;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IkkatsuhacchuuTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        Debug.println("findDBCollection start", this);
        DecimalFormat df = new DecimalFormat("##########.##");
        Collection col = new LinkedList();
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        HttpSession session = request.getSession();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        String souko_id = souko.getSouko_id();

        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String date = request.getParameter("date");
        boolean inputnouhindate = false;
        java.util.Date nouhin = null;
        if ((year != null) && (!year.equals(""))) {
            inputnouhindate = true;
            Calendar cal = DateUtil.getCalendar(year, month, date);
            nouhin = cal.getTime();
        }

        Enumeration params = request.getParameterNames();
        String key = null;
        String value = null;
        HashMap suuryoumap = new HashMap();
        StringBuffer shouhin_ids = null;
        while (params.hasMoreElements()) {
            key = (String)params.nextElement();
            if (key.startsWith("suuryou")) {
                String shouhin_id = key.substring(7, key.length());
                value = request.getParameter(key);
                if ((value == null) || (value.equals(""))) {
                    value = "0";
                }
                //Integer suuryou = null;
                Float suuryou = null;
                try {
                    suuryou = new Float(value);
                } catch (NumberFormatException nfe) {
                    System.out.println("numberformat error, shouhin_id="
                      +shouhin_id+" suuryou="+value);
                    return null;
                }
                //if (suuryou.intValue() > 0) {
                if (suuryou.floatValue() > 0) {
                    suuryoumap.put(shouhin_id, suuryou);
                    if (shouhin_ids == null) {
                        shouhin_ids = new StringBuffer();
                    } else {
                        shouhin_ids.append(",");
                    }
                    shouhin_ids.append("'").append(shouhin_id).append("'");
                }
            }
        }

        String sql = 
          "select " + 
          "t1.shouhin_id, " + 
          "t1.shouhin, " +
          "t1.kikaku, " +
          "t1.hacchuutani, " +
          "t1.tanka, " +
          "t1.irisuu, " +
          "t1.tani, " +
          "t1.shiiresaki_id, " + 
          "t2.name, " +
          "t1.shiireleadtime " +
          "from zm_shouhin t1, zm_shiiresaki t2 " +
          "where t1.shiiresaki_id = t2.shiiresaki_id and " +
          "t1.shouhin_id in (" + shouhin_ids + ") " +
          "order by t1.shiiresaki_id";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs == null) { Debug.println("rs null", this);return null; }

        //fetch
        HashMap item = null;
        Calendar cal = Calendar.getInstance();
        java.util.Date now = DateUtil.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        while (rs.next()) {
            int index = 1;
            String shouhin_id = rs.getString(index++);
            String shouhin = rs.getString(index++);
            String kikaku = rs.getString(index++);
            int hacchuutani = rs.getInt(index++);
            float tanka = rs.getFloat(index++);
            //int irisuu = rs.getInt(index++);
            float irisuu = rs.getFloat(index++);
            int tani = rs.getInt(index++);
            String shiiresaki_id = rs.getString(index++);
            String shiiresaki = rs.getString(index++);
            int shiireleadtime = rs.getInt(index++);

            //Integer suuryou = (Integer)suuryoumap.get(shouhin_id);
            //int isuuryou = suuryou.intValue();
            //float kingaku = isuuryou * tanka;
            //float nyuukasuuryou = isuuryou * irisuu;
            Float suuryou = (Float)suuryoumap.get(shouhin_id);
            float fsuuryou = suuryou.floatValue();
            float kingaku = fsuuryou * tanka;
            float nyuukasuuryou = fsuuryou * irisuu;

            if (!inputnouhindate) {
                cal.setTime(now);
                cal.set(Calendar.HOUR, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.add(Calendar.DATE, shiireleadtime);
                nouhin = cal.getTime();
            }
            String nouhindate = sdf.format(nouhin);


            item = new HashMap();
            item.put("shouhin_id", shouhin_id);
            item.put("shouhin", shouhin);
            item.put("kikaku", kikaku);
            //item.put("suuryou", suuryou);
            item.put("suuryou", df.format(suuryou));
            item.put("hacchuutani", TaniMap.getTani(hacchuutani).getTani());
            item.put("tanka", new Float(tanka));
            item.put("kingaku", new Float(kingaku));
            item.put("nyuukasuuryou", new Float(nyuukasuuryou));
            item.put("nyuukatani", TaniMap.getTani(tani).getTani());
            item.put("shiiresaki_id", shiiresaki_id);
            item.put("shiiresaki", shiiresaki);
            item.put("nouhindate", nouhindate);
            col.add(item);
	}
        rs.close();
        if (ps!= null) { ps.close(); }
        return col;
    }

}
