package com.oisix.oisystemzs.objectmap;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemzs.ejb.NyuukokubunLocalHome;
import com.oisix.oisystemzs.ejb.NyuukokubunLocal;
import com.oisix.oisystemzs.ejb.NyuukokubunData;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class NyuukokubunMap {
    private static TreeMap nyuukokubunMap = null;

    static {
        newNyuukokubunMap();
    }

    public static TreeMap newNyuukokubunMap() {
        nyuukokubunMap = new TreeMap();
        try {
            NyuukokubunLocalHome sk = (NyuukokubunLocalHome)ServiceLocator.
                        getLocalHome("java:comp/env/ejb/NyuukokubunLocal");
            if(sk == null) System.out.println("NyuukokubunLocalHome is Null");
            Collection nyuukokubuns = sk.findAll();
            if ((nyuukokubuns == null) || (nyuukokubuns.isEmpty())) {
                return nyuukokubunMap;
            }
            Iterator kubuniter = nyuukokubuns.iterator();
            while (kubuniter.hasNext()) {
                NyuukokubunLocal sl = (NyuukokubunLocal)kubuniter.next();
                NyuukokubunData kubun = sl.getNyuukokubunData();
                nyuukokubunMap.put(
                  new Integer(kubun.getNyuukokubun_id()), kubun);
            }
        } catch (NamingException ex) {
            Debug.println(ex);
            return nyuukokubunMap;
        } catch (FinderException ex) {
            Debug.println(ex);
            return nyuukokubunMap;
        }
        return nyuukokubunMap;
    }

    public static void setNyuukokubunMap() {
        if (nyuukokubunMap == null || nyuukokubunMap.isEmpty()) {
            newNyuukokubunMap();
        }
    }

    public static TreeMap getNyuukokubunMap() {
        setNyuukokubunMap();
        return nyuukokubunMap;
    }

    public static void clearNyuukokubunMap() {
        nyuukokubunMap = null;
    }

    public static NyuukokubunData getNyuukokubun(int id) {
        setNyuukokubunMap();
        return (NyuukokubunData)nyuukokubunMap.get(new Integer(id));
    }
}
