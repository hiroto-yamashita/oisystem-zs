package com.oisix.oisystemzs.objectmap;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShukkayoteishubetsuLocalHome;
import com.oisix.oisystemzs.ejb.ShukkayoteishubetsuLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteishubetsuData;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class ShukkayoteishubetsuMap {
    private static TreeMap shubetsuMap = null;

    static {
        newShukkayoteishubetsuMap();
    }

    public static TreeMap newShukkayoteishubetsuMap() {
        shubetsuMap = new TreeMap();
        try {
            ShukkayoteishubetsuLocalHome sk = 
              (ShukkayoteishubetsuLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ShukkayoteishubetsuLocal");
            Collection shubetsus = sk.findAll();
            if ((shubetsus == null) || (shubetsus.isEmpty())) {
                return shubetsuMap;
            }
            Iterator iter = shubetsus.iterator();
            while (iter.hasNext()) {
                ShukkayoteishubetsuLocal sl =
                  (ShukkayoteishubetsuLocal)iter.next();
                ShukkayoteishubetsuData shubetsu =
                  sl.getShukkayoteishubetsuData();
                shubetsuMap.put(
                  new Integer(shubetsu.getShukkayoteishubetsu_id()), shubetsu);
            }
        } catch (NamingException ex) {
            Debug.println(ex);
            return shubetsuMap;
        } catch (FinderException ex) {
            Debug.println(ex);
            return shubetsuMap;
        }
        System.out.println("ShukkayoteishubetsuMap is New!");
        return shubetsuMap;
    }

    public static void setShukkayoteishubetsuMap() {
        if (shubetsuMap == null || shubetsuMap.isEmpty()) {
            newShukkayoteishubetsuMap();
        }
    }

    public static TreeMap getShukkayoteishubetsuMap() {
        setShukkayoteishubetsuMap();
        return shubetsuMap;
    }

    public static void clearShukkayoteishubetsuMap() {
        shubetsuMap = null;
    }

    public static ShukkayoteishubetsuData getShukkayoteishubetsu(int id) {
        setShukkayoteishubetsuMap();
        return (ShukkayoteishubetsuData)shubetsuMap.get(new Integer(id));
    }
}
