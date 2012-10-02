package com.oisix.oisystemfr;

import javax.sql.DataSource;
import javax.ejb.EJBLocalHome;
import javax.naming.Context;
import javax.naming.NamingException;


/**
 * EJBLocalHome��DataSource���擾���邽�߂̃��[�e�B���e�B�N���X�ł��B
 * InitialContext�I�u�W�F�N�g���L���b�V�����܂��B
 * �܂��AControllerServlet��init�p�����[�^�Ŏw�肳�ꂽDataSource��JNDI��
 * �ɍ�����DataSource��Ԃ��܂��B
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
