package com.oisix.oisystemzs.objectmap;

import java.util.TreeMap;

public class HanbaikubunMap {
    private static TreeMap hanbaikubunMap = null;

    static {
        newHanbaikubunMap();
    }

    public static TreeMap newHanbaikubunMap() {
        hanbaikubunMap = new TreeMap();
        hanbaikubunMap.put(new Integer(0), "TH");
        hanbaikubunMap.put(new Integer(1), "NS");
        hanbaikubunMap.put(new Integer(2), "‹¤’Ê");
        hanbaikubunMap.put(new Integer(3), "‚»‚Ì‘¼");
        return hanbaikubunMap;

    }

    public static void setHanbaikubunMap() {
        if (hanbaikubunMap == null || hanbaikubunMap.isEmpty()) {
            newHanbaikubunMap();
        }
    }

    public static TreeMap getHanbaikubunMap() {
        setHanbaikubunMap();
        return hanbaikubunMap;
    }

    public static void clearHanbaikubunMap() {
        hanbaikubunMap = null;
    }

    public static String get(int id) {
        setHanbaikubunMap();
        return (String)hanbaikubunMap.get(new Integer(id));
    }
}
