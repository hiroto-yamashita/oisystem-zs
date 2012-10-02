package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.NamingException;

public class ZaikoDAO {

    /**
     * 在庫データが存在しない場合は0を返す
     */
    public int findLatestZaiko_id(java.util.Date zaikodate, String souko_id, 
      String shouhin_id) throws SQLException {
        int zaiko_id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();
            String sql = "select t1.zaiko_id from zt_zaiko t1 " +
              "where t1.souko_id=? and t1.shouhin_id=? and " +
              "t1.zorder = (select max(t2.zorder) from zt_zaiko t2 " +
               "where t2.souko_id=t1.souko_id and " +
               "t2.shouhin_id=t1.shouhin_id and " +
               "t2.zaikodate<?) and t1.zaikodate<?";
               //"zaikodate<?)";
               //もともとこうだったが、どういうわけか１トランザクションで
               //入庫出庫を行うと、トランザクション途中でzaikodateが重複
               //して、入出庫日のあとの日付に入ってしまう現象が発生した
            Calendar cal = Calendar.getInstance();
            cal.setTime(zaikodate);
            cal.add(Calendar.DATE, 1);
            //cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
            ps = con.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, souko_id);
            ps.setString(index++, shouhin_id);
            ps.setDate(index++, date);
            ps.setDate(index++, date);
            rs = ps.executeQuery();
            if (rs.next()) {
                zaiko_id = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (NamingException ne) {
             ne.printStackTrace();
             throw new SQLException("caused by NamingException");
        } catch (SQLException sqle) {
             throw sqle;
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (ps != null) { ps.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException se) {
                throw se;
            }
        }
        return zaiko_id;
    }

    /**
     * 在庫データが存在しない場合は0を返す
     */
    public int findOldestZaiko_id(String souko_id, String shouhin_id)
      throws SQLException {
        int zaiko_id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();
            String sql = "select t1.zaiko_id from zt_zaiko t1 " +
              "where t1.souko_id=? and t1.shouhin_id=? and " +
              "t1.zorder=1";
            ps = con.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, souko_id);
            ps.setString(index++, shouhin_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                zaiko_id = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (NamingException ne) {
             ne.printStackTrace();
             throw new SQLException("caused by NamingException");
        } catch (SQLException sqle) {
             throw sqle;
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (ps != null) { ps.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException se) {
                throw se;
            }
        }
        return zaiko_id;
    }
}
