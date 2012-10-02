package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.Debug;
import java.util.Collection;
import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DBCollectionTagBase extends CollectionTagBase {

    protected Collection findCollection() throws Exception {
        Debug.println("findcollection start", this);
        Collection col = null;
        Context ic = new InitialContext(); //ローカルのコネクション取得
        String  datasource =
          (String)pageContext.getServletContext().getAttribute("DATASOURCE");
        DataSource ds = (DataSource)ic.lookup("java:comp/env/" + datasource);
        Connection con = ds.getConnection();
        try {
            col = findDBCollection(con);
        } catch (Exception e) {
            Debug.println(e);
            throw e;
        } finally {
            if (con != null) { con.close(); }
        }
        return col;
    }

    protected abstract Collection findDBCollection(Connection con)
      throws Exception;
}
