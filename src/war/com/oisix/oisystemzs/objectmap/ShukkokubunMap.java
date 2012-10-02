package com.oisix.oisystemzs.objectmap;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShukkokubunLocalHome;
import com.oisix.oisystemzs.ejb.ShukkokubunLocal;
import com.oisix.oisystemzs.ejb.ShukkokubunData;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class ShukkokubunMap {
    private static TreeMap shukkokubunMap = null;

    static {
        newShukkokubunMap();
    }

    public static TreeMap newShukkokubunMap() {
        shukkokubunMap = new TreeMap();
        try {
            ShukkokubunLocalHome sk = (ShukkokubunLocalHome)ServiceLocator.
              getLocalHome("java:comp/env/ejb/ShukkokubunLocal");
            Collection shukkokubuns = sk.findAll();
            if ((shukkokubuns == null) || (shukkokubuns.isEmpty())) {
                return shukkokubunMap;
            }
            Iterator kubuniter = shukkokubuns.iterator();
            while (kubuniter.hasNext()) {
                ShukkokubunLocal sl = (ShukkokubunLocal)kubuniter.next();
                ShukkokubunData kubun = sl.getShukkokubunData();
                shukkokubunMap.put(
                  new Integer(kubun.getShukkokubun_id()), kubun);
            }
        } catch (NamingException ex) {
            Debug.println(ex);
            return shukkokubunMap;
        } catch (FinderException ex) {
            Debug.println(ex);
            return shukkokubunMap;
        }
        System.out.println("ShukkokubunMap is New!");
        return shukkokubunMap;
    }

    public static void setShukkokubunMap() {
        if (shukkokubunMap == null || shukkokubunMap.isEmpty()) {
            newShukkokubunMap();
        }
    }

    public static TreeMap getShukkokubunMap() {
        setShukkokubunMap();
        return shukkokubunMap;
    }

    public static void clearShukkokubunMap() {
        shukkokubunMap = null;
    }

    public static ShukkokubunData getShukkokubun(int id) {
        setShukkokubunMap();
        return (ShukkokubunData)shukkokubunMap.get(new Integer(id));
    }
}
