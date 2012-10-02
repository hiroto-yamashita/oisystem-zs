package com.oisix.oisystemzs.objectmap;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemzs.ejb.OndotaiLocalHome;
import com.oisix.oisystemzs.ejb.OndotaiLocal;
import com.oisix.oisystemzs.ejb.OndotaiData;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class OndotaiMap {
    private static TreeMap ondotaiMap = null;

    static {
        newOndotaiMap();
    }

    public static TreeMap newOndotaiMap() {
        ondotaiMap = new TreeMap();
        try {
            OndotaiLocalHome olh = (OndotaiLocalHome)ServiceLocator
              .getLocalHome("java:comp/env/ejb/OndotaiLocal");
            Collection ondotais = olh.findAll();
            if ((ondotais == null) || (ondotais.isEmpty())) {
                return ondotaiMap;
            }
            Iterator iter = ondotais.iterator();
            while (iter.hasNext()) {
                OndotaiLocal ol = (OndotaiLocal)iter.next();
                OndotaiData ondotai = ol.getOndotaiData();
                ondotaiMap.put(new Integer(ondotai.getOndotai_id()), ondotai);
            }
        } catch (NamingException ex) {
            Debug.println(ex);
            return ondotaiMap;
        } catch (FinderException ex) {
            Debug.println(ex);
            return ondotaiMap;
        }
        return ondotaiMap;
    }

    public static void setOndotaiMap() {
        if (ondotaiMap == null || ondotaiMap.isEmpty()) {
            newOndotaiMap();
        }
    }

    public static TreeMap getOndotaiMap() {
        setOndotaiMap();
        return ondotaiMap;
    }

    public static void clearOndotaiMap() {
        ondotaiMap = null;
    }

    public static OndotaiData getOndotai(int id) {
        setOndotaiMap();
        return (OndotaiData)ondotaiMap.get(new Integer(id));
    }
}
