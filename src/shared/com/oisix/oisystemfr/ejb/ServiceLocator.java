package com.oisix.oisystemfr.ejb;

import javax.sql.DataSource;
import javax.ejb.EJBLocalHome;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * EJB tierでEJBLocalHomeやDataSourceを取得するためのユーティリティクラスです。
 * InitialContextオブジェクトをキャッシュします。
 */
public class ServiceLocator {

    private static InitialContext ic;
    private static String dataSourceName;

    static {
        try {
            //ic = JNDIUtil.getContext();
            //web tier版との違い
            ic = new InitialContext();
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
    }

    protected static void setDataSourceName(String str) {
        dataSourceName = str;
    }

    public static EJBLocalHome getLocalHome(String jndiHomeName)
      throws NamingException {
        EJBLocalHome home = (EJBLocalHome)ic.lookup(jndiHomeName);
        return home;
    }

    public static DataSource getDataSource() throws NamingException {
        return (DataSource)ic.lookup(dataSourceName);
    }
}
