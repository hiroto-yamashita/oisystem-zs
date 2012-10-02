package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.EventHandlerSupport;
import com.oisix.oisystemfr.ServiceLocator;
import java.util.HashMap;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.naming.NamingException;


public class ShouhinidsearchEvent extends EventHandlerSupport {

    private String shouhin_id;
    private String shouhin;
    private String kikaku;
    private String tanka;
    private String hacchuutani;
    private String tani;
    private String irisuu;
    private String hyoujuntanka;
    private String shoumi_y;
    private String shoumi_m;
    private String shoumi_d;
    private String shukka_y;
    private String shukka_m;
    private String shukka_d;
    private int result = 0;
    private String errormsg;

    public String getShouhin_id() { return shouhin_id; }
    public String getShouhin() { return shouhin; }
    public String getKikaku() { return kikaku; }
    public String getTanka() { return tanka; }
    public String getHacchuutani() { return hacchuutani; }
    public String getTani() { return tani; }
    public String getIrisuu() { return irisuu; }
    public String getHyoujuntanka() { return hyoujuntanka; }
    public String getShoumi_y() { return shoumi_y; }
    public String getShoumi_m() { return shoumi_m; }
    public String getShoumi_d() { return shoumi_d; }
    public String getShukka_y() { return shukka_y; }
    public String getShukka_m() { return shukka_m; }
    public String getShukka_d() { return shukka_d; }
    public int getResult() { return result; }
    public String getErrormsg() { return errormsg; }

    public void init(HttpServletRequest request) {
        shouhin_id = request.getParameter("input_id");
    }

    public void handleEvent(HashMap attr) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();
            String sql = 
              "SELECT " + 
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
              "WHERE SHOUHIN_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, shouhin_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int index = 1;
                shouhin = rs.getString(index++);
                kikaku = rs.getString(index++);
                tani = rs.getString(index++);
                hacchuutani = rs.getString(index++);
                irisuu = rs.getString(index++);
                tanka = rs.getString(index++);
                hyoujuntanka = rs.getString(index++);
                int shoumikigennissuu = rs.getInt(index++);
                Calendar shoumikigen = Calendar.getInstance();
                shoumikigen.add(Calendar.DATE, shoumikigennissuu);
                int year = shoumikigen.get(Calendar.YEAR);
                int month = shoumikigen.get(Calendar.MONTH) + 1;
                int date = shoumikigen.get(Calendar.DATE);
                shoumi_y = String.valueOf(year);
                shoumi_m = String.valueOf(month);
                shoumi_d = String.valueOf(date);
                int shukkakigennissuu = rs.getInt(index++);
                Calendar shukkakigen = Calendar.getInstance();
                shukkakigen.add(Calendar.DATE, shukkakigennissuu);
                year = shukkakigen.get(Calendar.YEAR);
                month = shukkakigen.get(Calendar.MONTH) + 1;
                date = shukkakigen.get(Calendar.DATE);
                shukka_y = String.valueOf(year);
                shukka_m = String.valueOf(month);
                shukka_d = String.valueOf(date);
            }
            rs.close();
            ps.close();
            if (shouhin == null) {
                //商品が1件も検索されなかった
                result = 1;
                errormsg = "商品コード" + shouhin_id + "の商品は存在しません。";
            }
        } catch (NamingException ne) {
            ne.printStackTrace();
            result = 1;
            errormsg = "システムエラーです。NamingException";
        } catch (SQLException se) {
            se.printStackTrace();
            result = 1;
            errormsg = "システムエラーです。SQLException";
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (ps != null) { ps.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException se) {
                se.printStackTrace();
                result = 1;
                errormsg = "システムエラーです。SQLException";
            }
        }
    }

}
