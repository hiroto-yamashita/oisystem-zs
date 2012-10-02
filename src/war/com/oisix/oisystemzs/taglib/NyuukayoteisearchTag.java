package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.Calendar;
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

public class NyuukayoteisearchTag extends DBCollectionTagBase {

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
        DecimalFormat df = new DecimalFormat("##########.##");
        while ((rows != 0) && rs.next()) {
            rows--;
            length++;
            item = new HashMap();
            int index = 1;
            int hacchuu_id = rs.getInt(index++);
            item.put("hacchuu_id",new Integer(hacchuu_id));
            item.put("hacchuu_bg", rs.getString(index++));
            item.put("nyuukayotei_date", rs.getDate(index++));
            item.put("nyuukokubun", rs.getString(index++));
            String shiiresaki_id = rs.getString(index++);
            item.put("name", rs.getString(index++));
            String shouhin_id = rs.getString(index++);
            item.put("shouhin_id",shouhin_id);
            item.put("shouhin", rs.getString(index++));
            float nyuukasuuryou = rs.getFloat(index++);
            float jitsunyuukasuu = rs.getFloat(index++);
            String nyuukasuu = df.format(nyuukasuuryou - jitsunyuukasuu);
            if (jitsunyuukasuu != 0) {
                nyuukasuu += "<br>(" + df.format(jitsunyuukasuu) + "入荷済み)";
            }
            item.put("nyuukasuuryou", nyuukasuu);
            item.put("nyuukatani", rs.getString(index++));
            int nyuukayotei_id = rs.getInt(index++);
            item.put("nyuukayotei_id", new Integer(nyuukayotei_id));
            item.put("lineparam","hacchuu_id="+hacchuu_id+"&shouhin_id="+shouhin_id+"&shiiresaki_id="+shiiresaki_id+"&nyuukayotei_id="+nyuukayotei_id);
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
        String sql =
          "SELECT " + 
          "t3.HACCHUU_ID, " + 
          "t1.HACCHUU_BG, " +
          "t1.NYUUKAYOTEI_DATE, " +
          "t5.NYUUKOKUBUN, " + 
          "t2.SHIIRESAKI_ID, " +
          "t2.NAME, " + 
          "t4.SHOUHIN_ID, " + 
          "t4.SHOUHIN, " + 
          "t1.NYUUKASUURYOU, " + 
          "t1.JITSUNYUUKASUU, " +
          "t1.NYUUKATANI, " +
          "t1.NYUUKAYOTEI_ID " +
          "FROM ZT_NYUUKAYOTEIMEISAI t1, " +
          "ZT_HACCHUU t3 LEFT OUTER JOIN ZM_SHIIRESAKI t2 " +
          "ON t3.SHIIRESAKI_ID=t2.SHIIRESAKI_ID, " +
          "ZM_SHOUHIN  t4, ZM_NYUUKOKUBUN t5 "+
          "WHERE t1.HACCHUU_BG=t3.HACCHUU_BG AND " +
          " t1.SHOUHIN_ID=t4.SHOUHIN_ID AND " +
          " t1.NYUUKOKUBUN = t5.NYUUKOKUBUN_ID " +
          " AND t1.NYUUKAJOUKYOU IN (1,2) " +
          " AND t1.SOUKO_ID = ? ";
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        int beginyearint = 2003;
        int beginmonthint = 1;
        int begindateint = 1;
        int endyearint = 2003;
        int endmonthint = 1;
        int enddateint = 1;
        Calendar begincal = null;
        Calendar endcal = null;
        String hacchuuyearstr = request.getParameter("#hacchuuyear");
        String hacchuumonthstr = request.getParameter("#hacchuumonth");
        String hacchuudatestr = request.getParameter("#hacchuudate");
        int hacchuuyear = 2002;
        int hacchuumonth = 1;
        int hacchuudate = 1;
        int nyuukayear = 2002;
        int nyuukamonth = 1;
        int nyuukadate = 1;
        String one = "one";
        String two = "two";
        String hacchuubi = request.getParameter("#hacchuubi");
        //発注日指定あり
        if ((hacchuubi != null) && (!hacchuubi.equals(""))) {
            if(hacchuubi.equals(one)){
                try {
                    hacchuuyear = Integer.parseInt(hacchuuyearstr);
                    hacchuumonth = Integer.parseInt(hacchuumonthstr);
                    hacchuudate = Integer.parseInt(hacchuudatestr);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                sql += " AND YEAR(t3.HACCHUU_DATE) = " + hacchuuyear +
                       " AND MONTH(t3.HACCHUU_DATE) = " + hacchuumonth +
                       " AND DAYOFMONTH(t3.HACCHUU_DATE) = " + hacchuudate;
            }
        }
        String nyuukayotei = request.getParameter("#nyuukayotei");
        //入荷予定日指定あり
        if ((nyuukayotei != null) && (!nyuukayotei.equals(""))) {
            if(nyuukayotei.equals(two)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String beginyearstr = request.getParameter("#beginyear");
                String beginmonthstr = request.getParameter("#beginmonth");
                String begindatestr = request.getParameter("#begindate");
                try{
                    beginyearint = Integer.parseInt(beginyearstr);
                    beginmonthint = Integer.parseInt(beginmonthstr);
                    begindateint = Integer.parseInt(begindatestr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                }
                begincal = Calendar.getInstance();
                begincal.set(beginyearint,beginmonthint-1,begindateint);
                java.util.Date begindate = begincal.getTime();

                String endyearstr = request.getParameter("#endyear");
                String endmonthstr = request.getParameter("#endmonth");
                String enddatestr = request.getParameter("#enddate");
                try{
                    endyearint = Integer.parseInt(endyearstr);
                    endmonthint = Integer.parseInt(endmonthstr);
                    enddateint = Integer.parseInt(enddatestr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                }
                endcal = Calendar.getInstance();
                endcal.set(endyearint,endmonthint-1,enddateint);
                java.util.Date enddate = endcal.getTime();

                sql += " AND t1.NYUUKAYOTEI_DATE >= '" +
                  sdf.format(begindate) + "'";
                sql += " AND t1.NYUUKAYOTEI_DATE <= '" +
                  sdf.format(enddate) + "'";
            }
        }
        String shouhin_id = request.getParameter("#shouhin_id");
        if ((shouhin_id != null) && (!shouhin_id.equals(""))) {
            sql += "AND t1.SHOUHIN_ID = '" + shouhin_id + "'";
        }
        String shiiresaki_id = request.getParameter("#shiiresaki_id");
        if ((shiiresaki_id != null) && (!shiiresaki_id.equals(""))) {
            sql += "AND t3.SHIIRESAKI_ID = '" + shiiresaki_id + "'";
        }
        String hacchuu_bg = request.getParameter("#hacchuu_bg");
        if ((hacchuu_bg != null) && (!hacchuu_bg.equals(""))) {
            sql += "AND t3.HACCHUU_BG = '" + hacchuu_bg + "'";
        }
        String nouhinsaki_id = request.getParameter("#nouhinsaki_id");
        if ((nouhinsaki_id != null) && (!nouhinsaki_id.equals(""))) {
            sql += "AND t3.NOUHINSAKI_ID = '" + nouhinsaki_id + "'";
        }
        sql += " ORDER BY t1.NYUUKAYOTEI_DATE, t4.DAIBUNRUI, " +
          "t2.SHIIRESAKI_ID DESC" ;
        return sql;
    }
}
