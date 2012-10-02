package com.oisix.oisystemzs.objectmap;

import java.util.TreeMap;

public class YouchuuikubunMap {
    private static TreeMap youchuuikubunMap = null;

    static {
        newYouchuuikubunMap();
    }

    public static TreeMap newYouchuuikubunMap() {
        youchuuikubunMap = new TreeMap();
        youchuuikubunMap.put(new Integer(0), "");
        youchuuikubunMap.put(new Integer(1), "—v’ˆÓ");
        youchuuikubunMap.put(new Integer(2), "I”„—\’è");
        youchuuikubunMap.put(new Integer(3), "—\”õ1");
        youchuuikubunMap.put(new Integer(4), "—\”õ2");
        return youchuuikubunMap;

    }

    public static void setYouchuuikubunMap() {
        if (youchuuikubunMap == null || youchuuikubunMap.isEmpty()) {
            newYouchuuikubunMap();
        }
    }

    public static TreeMap getYouchuuikubunMap() {
        setYouchuuikubunMap();
        return youchuuikubunMap;
    }

    public static void clearYouchuuikubunMap() {
        youchuuikubunMap = null;
    }

    public static String get(int id) {
        setYouchuuikubunMap();
        return (String)youchuuikubunMap.get(new Integer(id));
    }
}
