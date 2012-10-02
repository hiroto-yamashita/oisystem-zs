package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.pdf.PdfObjectBase;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemfr.EventHandlerSupport;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.oisix.oisystemzs.pdf.YoteihyouPdf;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class YoteihyoupdfEvent extends EventHandlerSupport {

    private String filename;
    private java.util.Date day;
    private String date;
    private String souko_id;
    private LinkedList sysuuryoulist;
    
    public String getFileName() { return filename; }
    
    public void init(HttpServletRequest request) {
        String zero="0";
        String yearstr = request.getParameter("#year");
        String monthstr = request.getParameter("#month");
        if(monthstr.length()==1){
            monthstr=zero+monthstr;
        }
        String datestr = request.getParameter("#date");
        if(datestr.length()==1){
            datestr=zero+datestr;
        }
        String daystr=yearstr+monthstr+datestr;
        date = yearstr+"îN"+monthstr+"åé"+datestr;
        //day = 20020101;
        try {
            day = DateUtil.getDate(yearstr, monthstr, datestr);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        souko_id = souko.getSouko_id();
    }

    public void handleEvent(HashMap attr) {
        YoteihyouPdf ypdf = new YoteihyouPdf();
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();

            String sql = makeSql();
            ps = con.prepareStatement(sql);
            int index = 1;
            java.sql.Date sqlDate = new java.sql.Date(day.getTime());
            ps.setString(index++, souko_id);
            ps.setDate(index++, sqlDate);
            rs = ps.executeQuery();
            if (rs == null) {
                Debug.println("rs null", this);
                return;
            }
            LinkedList nyuukalist = new LinkedList();
            HashMap item = null;
            while (rs.next()) {
                //ì¸â◊ó\íËï\ÇÃèÓïÒéÊìæ
                item = new HashMap();
                index = 1;
                item.put("hacchuu_bg", rs.getString(index++));
                item.put("hacchuukubun",rs.getString(index++));
                item.put("shiiresakimei",rs.getString(index++));
                item.put("shouhin_id",rs.getString(index++));
                item.put("shouhin",rs.getString(index++));
                item.put("kikaku",rs.getString(index++));
                item.put("irisuu",new Float(rs.getFloat(index++)));
                item.put("nyuukasuuryou",new Float(rs.getFloat(index++)));
                item.put("hacchuusuuryou",new Float(rs.getFloat(index++)));
                item.put("touchakujikan",rs.getString(index++));
                item.put("nyuukayotei_date", rs.getDate(index++));
                item.put("shoumikigen", rs.getDate(index++));
                item.put("shoumikigenflg", new Integer(rs.getInt(index++)));
                item.put("shukkakigen", rs.getDate(index++));
                item.put("shukkakigennissuu", new Integer(rs.getInt(index++)));
                item.put("shoumikigennissuu", new Integer(rs.getInt(index++)));
                item.put("ondotai",rs.getString(index++));
                item.put("youraberu_flg",new Integer(rs.getInt(index++)));
                item.put("jitsunyuukasuu",new Float(rs.getFloat(index++)));
                item.put("souko_id",rs.getString(index++));
                item.put("nisugata",rs.getString(index++));
                item.put("daibunrui",rs.getString(index++));
                item.put("location_id1",rs.getString(index++));
                nyuukalist.add(item);
            }
            rs.close();
            ps.close();
            //à¯ìñêîÇÃéÊìæ
            Iterator nyuukalistiter = nyuukalist.iterator();
            sysuuryoulist = new LinkedList();
            
            while(nyuukalistiter.hasNext()){
                HashMap nyuukalistmp = (HashMap)nyuukalistiter.next();
                String strShouhin_id =  (String)nyuukalistmp.get("shouhin_id");
                String strSouko_id = (String)nyuukalistmp.get("souko_id");
                java.util.Date nyuukayoteidate = (java.util.Date)nyuukalistmp.get("nyuukayotei_date");
                HashMap sysuuryou = null;
            }
            
            ypdf.setNyuukaList(nyuukalist);
            ypdf.setSysuuryouList(sysuuryoulist);
            ypdf.setYoteihyouDate(date);
            try {
                ypdf.makeDocument();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            filename = ypdf.getFileName();
        } catch (NamingException nme) {
            Debug.println(nme);
        } catch (SQLException sqle) {
            Debug.println(sqle);
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (ps != null) { ps.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException sqle) { sqle.printStackTrace(); }
        }
    }

    public String makeSql() {
        String sql =
          "SELECT " + 
          "t1.HACCHUU_BG, " + 
          "t3.HACCHUUKUBUN, " + 
          "t4.NAME, " + 
          "t2.SHOUHIN_ID, " + 
          "t2.SHOUHIN, " + 
          "t2.KIKAKU, " + 
          "t2.IRISUU, " + 
          "t1.NYUUKASUURYOU, " + 
          "t1.HACCHUUSUURYOU, " +
          "t7.TOUCHAKU, " + 
          "t1.NYUUKAYOTEI_DATE, " + 
          "t1.SHOUMIKIGEN, " + 
          "t2.SHOUMIKIGEN_FLG, " + 
          "t1.SHUKKAKIGEN, " + 
          "t2.SHUKKAKIGENNISSUU, " + 
          "t2.SHOUMIKIGENNISSUU, " + 
          "t5.ONDOTAI, " + 
          "t2.YOURABERU_FLG, " +
          "t1.JITSUNYUUKASUU, " +
          "t1.SOUKO_ID, " +
          "t2.NISUGATA, " +
          "t2.DAIBUNRUI, " +
          "t2.LOCATION_ID1 " +
          "FROM ZT_NYUUKAYOTEIMEISAI t1, ZM_SHOUHIN t2, " +
          "ZM_HACCHUUKUBUN t3, ZM_SHIIRESAKI t4, ZM_ONDOTAI t5, "+ 
          "ZT_HACCHUU t6, ZM_TOUCHAKU t7 "+
          "WHERE t1.SHOUHIN_ID = t2.SHOUHIN_ID AND " +
          "t4.SHIIRESAKI_ID = t6.SHIIRESAKI_ID " +
          " AND t1.HACCHUUKUBUN = t3.HACCHUUKUBUN_ID AND " + 
          "t1.ONDOTAI = t5.ONDOTAI_ID " +
          " AND t1.NYUUKAJOUKYOU IN (1,2) AND t1.HACCHUU_BG = t6.HACCHUU_BG " +
          " AND t1.TOUCHAKUJIKAN = t7.TOUCHAKU_ID " +
          " AND t1.SOUKO_ID = ? " +
          " AND t1.NYUUKAYOTEI_DATE <= ?" +
          " ORDER BY t1.NYUUKAYOTEI_DATE , t2.DAIBUNRUI, " +
          "t4.SHIIRESAKI_ID DESC" ;
        return sql;
    }
}
