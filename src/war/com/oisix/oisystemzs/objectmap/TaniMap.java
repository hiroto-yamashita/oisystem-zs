package com.oisix.oisystemzs.objectmap;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemzs.ejb.TaniLocalHome;
import com.oisix.oisystemzs.ejb.TaniLocal;
import com.oisix.oisystemzs.ejb.TaniData;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class TaniMap {
    private static TreeMap taniMap = null;

    static {
        newTaniMap();
    }

    public static TreeMap newTaniMap() {
        taniMap = new TreeMap();
        try {
            TaniLocalHome tlh = (TaniLocalHome)ServiceLocator
              .getLocalHome("java:comp/env/ejb/TaniLocal");
            Collection tanis = tlh.findAll();
            if ((tanis == null) || (tanis.isEmpty())) {
                return taniMap;
            }
            Iterator iter = tanis.iterator();
            while (iter.hasNext()) {
                TaniLocal tl = (TaniLocal)iter.next();
                TaniData tani = tl.getTaniData();
                taniMap.put(new Integer(tani.getTani_id()), tani);
            }
        } catch (NamingException ex) {
            Debug.println(ex);
            return taniMap;
        } catch (FinderException ex) {
            Debug.println(ex);
            return taniMap;
        }
        return taniMap;
    }

    public static void setTaniMap() {
        if (taniMap == null || taniMap.isEmpty()) {
            newTaniMap();
        }
    }

    public static TreeMap getTaniMap() {
        setTaniMap();
        return taniMap;
    }

    public static void clearTaniMap() {
        taniMap = null;
    }

    public static TaniData getTani(int id) {
        setTaniMap();
        return (TaniData)taniMap.get(new Integer(id));
    }
}
