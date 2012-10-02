package com.oisix.oisystemfr.ejb;

import com.oisix.oisystemfr.EventHandler;
import com.oisix.oisystemfr.Debug;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * The session bean that generates primary key
 *
 * @author Hiroto Yamashita
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="KeyGenerator"
 *           display-name="Key Generator"
 *           type="Stateless"
 *           jndi-name="ejb/KeyGenerator"
 *           local-jndi-name="ejb/KeyGeneratorLocal"
 *           view-type="both"
 *           transaction-type="Bean"
 *
 * @ejb:resource-ref res-name="${res.name}"
 *                   res-type="javax.sql.DataSource"
 *                   res-auth="Container"
 *
 * @jboss:resource-ref res-ref-name="${res.name}"
 *                     jndi-name="${datasource.name}"
 *
 **/
public class KeyGeneratorBean implements SessionBean {

    /**
    * @ejb:interface-method view-type="both"
    * シーケンスが使えない場合は主キーテーブルなどを使って実装
    **/
    public int getNext(String tablename) {
        if (tablename == null) {
            return 0;
        }
        int id = 0;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();
            /* for oracle 
            ps = con.prepareStatement(
              "SELECT " + tablename + ".NEXTVAL FROM DUAL");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                Debug.println("couldn't get " + tablename + " new id");
            }
            */
            //注意 この実装はデモ用です。本番稼動には問題あると思います。
            tablename = tablename.toUpperCase();
            ps = con.prepareStatement(
              "update sequence set id=id+1 where table_name=?");
            ps.setString(1, tablename);
            int n = ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement(
              "select id from sequence where table_name=?");
            ps.setString(1, tablename);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                Debug.println("couldn't get " + tablename + " new id");
            }
        } catch (SQLException ex) {
            Debug.println(ex);
            //throw ex;
        } catch (NamingException ex) {
            Debug.println(ex);
            //throw ex;
        } finally {
            try {
                if (ps != null) { ps.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException ex) {
                Debug.println(ex);
            }
        }
        return id;
    }

    public void ejbCreate() {}
    public void setSessionContext(SessionContext ctx) {}
    public void ejbRemove() {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}
}
