package com.oisix.oisystemzs.objectmap;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemzs.ejb.HacchuukubunLocalHome;
import com.oisix.oisystemzs.ejb.HacchuukubunLocal;
import com.oisix.oisystemzs.ejb.HacchuukubunData;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class HacchuukubunMap {
    private static TreeMap hacchuukubunMap = null;

    static {
        newHacchuukubunMap();
    }

    public static TreeMap newHacchuukubunMap() {
        hacchuukubunMap = new TreeMap();
        try {
            HacchuukubunLocalHome hh = (HacchuukubunLocalHome)ServiceLocator.
              getLocalHome("java:comp/env/ejb/HacchuukubunLocal");
            Collection kubuns = hh.findAll();
            if ((kubuns == null) || (kubuns.isEmpty())) {
                return hacchuukubunMap;
            }
            Iterator iter = kubuns.iterator();
            while (iter.hasNext()) {
                HacchuukubunLocal hl = (HacchuukubunLocal)iter.next();
                HacchuukubunData kubun = hl.getHacchuukubunData();
                hacchuukubunMap.put(
                  new Integer(hl.getHacchuukubun_id()), kubun);
            }
        } catch (NamingException ex) {
            Debug.println(ex);
            return hacchuukubunMap;
        } catch (FinderException ex) {
            Debug.println(ex);
            return hacchuukubunMap;
        }
        Debug.println("HacchuukubunMap loaded");
        return hacchuukubunMap;
    }

    public static void setHacchuukubunMap() {
        if (hacchuukubunMap == null || hacchuukubunMap.isEmpty()) {
            newHacchuukubunMap();
        }
    }

    public static TreeMap getHacchuukubunMap() {
        setHacchuukubunMap();
        return hacchuukubunMap;
    }

    public static void clearHacchuukubunMap() {
        hacchuukubunMap = null;
    }

    public static HacchuukubunData getHacchuukubun(int id) {
        setHacchuukubunMap();
        return (HacchuukubunData)hacchuukubunMap.get(new Integer(id));
    }
}
