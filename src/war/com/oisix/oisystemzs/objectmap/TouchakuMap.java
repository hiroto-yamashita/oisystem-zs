package com.oisix.oisystemzs.objectmap;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemzs.ejb.TouchakuLocalHome;
import com.oisix.oisystemzs.ejb.TouchakuLocal;
import com.oisix.oisystemzs.ejb.TouchakuData;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class TouchakuMap {
    private static TreeMap touchakuMap = null;

    static {
        newTouchakuMap();
    }

    public static TreeMap newTouchakuMap() {
        touchakuMap = new TreeMap();
        try {
            TouchakuLocalHome th = (TouchakuLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/TouchakuLocal");
            Collection touchakus = th.findAll();
            if ((touchakus == null) || (touchakus.isEmpty())) {
                return touchakuMap;
            }
            Iterator iter = touchakus.iterator();
            while (iter.hasNext()) {
                TouchakuLocal tl = (TouchakuLocal)iter.next();
                TouchakuData touchaku = tl.getTouchakuData();
                touchakuMap.put(
                  new Integer(touchaku.getTouchaku_id()), touchaku);
            }
        } catch (NamingException ex) {
            Debug.println(ex);
            return touchakuMap;
        } catch (FinderException ex) {
            Debug.println(ex);
            return touchakuMap;
        }
        return touchakuMap;
    }

    public static void setTouchakuMap() {
        if (touchakuMap == null || touchakuMap.isEmpty()) {
            newTouchakuMap();
        }
    }

    public static TreeMap getTouchakuMap() {
        setTouchakuMap();
        return touchakuMap;
    }

    public static void clearTouchakuMap() {
        touchakuMap = null;
    }

    public static TouchakuData getTouchaku(int id) {
        setTouchakuMap();
        return (TouchakuData)touchakuMap.get(new Integer(id));
    }
}
