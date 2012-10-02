package com.oisix.oisystemfr;

import javax.sql.DataSource;
import javax.ejb.EJBLocalHome;
import javax.naming.Context;
import javax.naming.NamingException;


/**
 * EJBLocalHomeやDataSourceを取得するためのユーティリティクラスです。
 * InitialContextオブジェクトをキャッシュします。
 * また、ControllerServletのinitパラメータで指定されたDataSourceのJNDI名
 * に合ったDataSourceを返します。
 */
public class ServiceLocator {

    private static Context ic;
    private static String dataSourceName;

    static {
        try {
            ic = JNDIUtil.getContext();
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
    }

    protected static void setDataSourceName(String str) {
        //dataSourceName = "java:comp/env/" + str;
        dataSourceName = str;
    }
    protected static String getDataSourceName() { return dataSourceName; }

    public static EJBLocalHome getLocalHome(String jndiHomeName)
      throws NamingException {
        EJBLocalHome home = (EJBLocalHome)ic.lookup(jndiHomeName);
        return home;
    }

    public static DataSource getDataSource() throws NamingException {
        return (DataSource)ic.lookup(dataSourceName);
    }
}
