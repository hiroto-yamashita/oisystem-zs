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
import java.text.DecimalFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class KaikakekinsearchTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        DecimalFormat df = new DecimalFormat("#,###,###,###.##");
        Debug.println("findDBCollection start", this);
        Collection col = new LinkedList();
        String sql =
          "SELECT " + 
          "t1.NYUUKO_ID, " +
          "t1.NYUUKO_DATE, " +
          "t3.NAME, " +
          "t2.SHOUHIN_ID, " + 
          "t2.SHOUHIN, " +
          "t2.KIKAKU, " + 
          "t1.NYUUKOSUURYOU, " + 
          "t1.SHIIRESUURYOU, " + 
          "t1.SHIIRETANI, " + 
          "ROUND(t1.SHIIRETANKA,0), " +
          "ROUND(t1.SHIIRESUURYOU*t1.SHIIRETANKA,0), " +
          "ROUND(t1.SHIIRESUURYOU*t1.SHIIRETANKA/t1.NYUUKOSUURYOU,0) " +
          "FROM ZT_NYUUKO t1 " +
          "LEFT OUTER JOIN ZM_NOUHINSAKI t3 " +
          "ON t1.NOUHINSAKI_ID=t3.NOUHINSAKI_ID, ZM_SHOUHIN t2 " +
          "WHERE t1.SHOUHIN_ID=t2.SHOUHIN_ID " +
          " AND t1.NYUUKOKUBUN=1 " + //仕入
          " AND t1.NYUUKO_DATE>=? " +
          " AND t1.NYUUKO_DATE<? " +
          " AND t1.SHIIRESAKI_ID=? " +
          "UNION ALL " +
          "SELECT " +
          "0, " +
          "t4.SHUKKO_DATE, " + 
          "NULL, " + 
          "t4.SHOUHIN_ID, " + 
          "t5.SHOUHIN, " +
          "t5.KIKAKU, " +
          "(-1)*t4.SUURYOU, " + 
          "(-1)*t4.SUURYOU, " +
          "t4.TANI, " +
          "ROUND(t4.TANKA,0), " +
          "ROUND((-1)*t4.KINGAKU,0), " +
          "ROUND(t4.TANKA,0) " +
          "FROM ZT_SHUKKO t4, ZM_SHOUHIN t5 " +
          "WHERE t4.SHOUHIN_ID=t5.SHOUHIN_ID " +
          " AND t4.SHUKKOKUBUN IN (14, 16) " + //廃棄赤伝、返品出庫
          " AND t4.SHUKKO_DATE>=? " +
          " AND t4.SHUKKO_DATE<? " +
          " AND t5.SHIIRESAKI_ID=? ";
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String year1str = request.getParameter("year1");
        String month1str = request.getParameter("month1");
        String date1str = request.getParameter("date1");
        String year2str = request.getParameter("year2");
        String month2str = request.getParameter("month2");
        String date2str = request.getParameter("date2");
        java.sql.Date startdate = null;
        java.sql.Date enddate = null;
        try{
            Calendar startcal =
              DateUtil.getCalendar(year1str, month1str, date1str);
            startdate = new java.sql.Date(startcal.getTimeInMillis());
            Calendar endcal =
              DateUtil.getCalendar(year2str, month2str, date2str);
            endcal.add(Calendar.DATE, 1);
            enddate = new java.sql.Date(endcal.getTimeInMillis());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        String shiiresaki_id = request.getParameter("shiiresaki_id");
        if ((shiiresaki_id != null) && (!shiiresaki_id.equals(""))) {
        }else{
            sql = null;
            System.out.println("仕入先コードを入力して下さい。");
        }
        Debug.println(sql);
        PreparedStatement ps = con.prepareStatement(sql);
        int i = 1;
        ps.setDate(i++, startdate);
        ps.setDate(i++, enddate);
        ps.setString(i++, shiiresaki_id);
        ps.setDate(i++, startdate);
        ps.setDate(i++, enddate);
        ps.setString(i++, shiiresaki_id);
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
            String nyuuko_id = rs.getString(index++);
            item.put("nyuuko_id", nyuuko_id);
            item.put("nyuuko_date",rs.getDate(index++));
            item.put("nouhinsaki", rs.getString(index++));
            item.put("shouhin_id", rs.getString(index++));
            item.put("shouhin", rs.getString(index++));
            item.put("kikaku", rs.getString(index++));
            item.put("nyuukasuuryou", df.format(rs.getFloat(index++)));
            item.put("hacchuusuuryou", df.format(rs.getFloat(index++)));
            item.put("hacchuutani", rs.getString(index++));
            String tanka = df.format(rs.getFloat(index++));
            item.put("hacchuutanka", tanka);
            item.put("hacchuukingaku", df.format(rs.getFloat(index++)));
            String nyuukotanka = df.format(rs.getFloat(index++));
            item.put("nyuukotanka", nyuukotanka);
            item.put("param", "nyuuko_id=" + nyuuko_id + "&tanka=" + tanka);
            col.add(item);
        }
        //set numitems
        while (rs.next()) {
            length++;
            hasnext = true;
        }
        rs.close();
        if (ps!= null) { ps.close(); }
        
        String goukeisql =
          "SELECT " + 
          "SUM(t1.SHIIRESUURYOU*t1.SHIIRETANKA), t2.NAME " +
          "FROM ZT_NYUUKO t1, ZM_SHIIRESAKI t2 "+
          "WHERE t1.SHIIRESAKI_ID=t2.SHIIRESAKI_ID " +
          "AND t1.NYUUKOKUBUN=1 " + //仕入
          "AND t1.NYUUKO_DATE>=? " +
          "AND t1.NYUUKO_DATE<? " +
          "AND t1.SHIIRESAKI_ID=? " +
          "GROUP BY t2.NAME";
        PreparedStatement psg = con.prepareStatement(goukeisql);
        i = 1;
        psg.setDate(i++, startdate);
        psg.setDate(i++, enddate);
        psg.setString(i++, shiiresaki_id);
        ResultSet rsg = psg.executeQuery();
        i = 1;
        float goukeikingaku = 0;
        HashMap goukei = new HashMap();
        while(rsg.next()){
            goukeikingaku = rsg.getFloat(i++);
            goukei.put("shiiresaki",rsg.getString(i++));
        }
        rsg.close();
        if (psg!= null) { psg.close(); }

        goukeisql =
          "SELECT " + 
          "SUM(t1.KINGAKU) FROM ZT_SHUKKO t1, ZM_SHOUHIN t2 " +
          "WHERE t1.SHOUHIN_ID=t2.SHOUHIN_ID " +
          " AND t1.SHUKKOKUBUN IN (14,16) " + //廃棄赤伝、返品出庫
          " AND t1.SHUKKO_DATE>=? " +
          " AND t1.SHUKKO_DATE<? " +
          " AND t2.SHIIRESAKI_ID=? ";
        psg = con.prepareStatement(goukeisql);
        i = 1;
        psg.setDate(i++, startdate);
        psg.setDate(i++, enddate);
        psg.setString(i++, shiiresaki_id);
        rsg = psg.executeQuery();
        i = 1;
        float akadenkingaku = 0;
        while(rsg.next()){
            akadenkingaku = rsg.getFloat(i++);
        }
        rsg.close();
        if (psg!= null) { psg.close(); }

        goukei.put("goukei", df.format(goukeikingaku - akadenkingaku));
        request.setAttribute("GOUKEI", goukei);

        numitems = length;
        Debug.println("numitems:"+numitems);
        Debug.println("startind:"+startind);
        Debug.println("size:"+size);
        Debug.println("hasprev:"+hasprev);
        Debug.println("hasnext:"+hasnext);
        
        return col;
    }

}
