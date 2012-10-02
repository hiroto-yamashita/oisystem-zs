package com.oisix.oisystemzs.objectmap;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemzs.ejb.SoukoLocalHome;
import com.oisix.oisystemzs.ejb.SoukoLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.ejb.FinderException;

public class SoukoMap {
    private static HashMap soukoMap = null;

    static {
        newSoukoMap();
    }

    public static HashMap newSoukoMap() {
        soukoMap = new HashMap();
        try {
            InitialContext ctx = new InitialContext();
            Object objref = ctx.lookup("java:comp/env/ejb/SoukoLocal");
            SoukoLocalHome sh = (SoukoLocalHome)objref;
            Collection soukos = sh.findAll();
            if ((soukos == null) || (soukos.isEmpty())) {
                return soukoMap;
            }
            Iterator soukoiter = soukos.iterator();
            while (soukoiter.hasNext()) {
                SoukoLocal sl = (SoukoLocal)soukoiter.next();
                SoukoData souko = sl.getSoukoData();
                soukoMap.put(souko.getSouko_id(), souko);
            }
        } catch (NamingException ex) {
            Debug.println(ex);
            return soukoMap;
        } catch (FinderException ex) {
            Debug.println(ex);
            return soukoMap;
        }
        return soukoMap;
    }

    public static void setSoukoMap() {
        if (soukoMap == null || soukoMap.isEmpty()) {
            newSoukoMap();
        }
    }

    public static HashMap getSoukoMap() {
        setSoukoMap();
        return soukoMap;
    }

    public static void clearSoukoMap() {
        soukoMap = null;
    }

    public static SoukoData getSouko(String souko_id) {
        setSoukoMap();
        return (SoukoData)soukoMap.get(souko_id);
    }
}
