package com.oisix.oisystemfr.ejb;

import javax.sql.DataSource;
import javax.ejb.EJBLocalHome;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * EJB tier��EJBLocalHome��DataSource���擾���邽�߂̃��[�e�B���e�B�N���X�ł��B
 * InitialContext�I�u�W�F�N�g���L���b�V�����܂��B
 */
public class ServiceLocator {

    private static InitialContext ic;
    private static String dataSourceName;

    static {
        try {
            //ic = JNDIUtil.getContext();
            //web tier�łƂ̈Ⴂ
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
