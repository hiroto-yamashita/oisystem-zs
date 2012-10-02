package com.oisix.oisystemfr;

import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.naming.Context;

public class KeyGeneratorUtil {

    private static Context ctx;

    public static int getNext(String tablename) {
        if (ctx == null) {
            try {
                ctx = JNDIUtil.getContext();
            } catch (NamingException ne) {
                throw new RuntimeException("couldn't get context");
            }
        }
        int id = 0;
        try {
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)
              ctx.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext(tablename);
        } catch (NamingException ne) {
            Debug.println(ne);
        } catch (CreateException ce) {
            Debug.println(ce);
        }
        return id;
    }

}
