package com.oisix.oisystemzs.objectmap;

import java.util.TreeMap;

public class HacchuustatusMap {
    private static TreeMap hacchuustatusMap = null;

    static {
        newHacchuustatusMap();
    }

    public static TreeMap newHacchuustatusMap() {
        hacchuustatusMap = new TreeMap();
        hacchuustatusMap.put(new Integer(10), "未出力");
        hacchuustatusMap.put(new Integer(20), "出力済み");
        hacchuustatusMap.put(new Integer(30), "FAX済み");
        hacchuustatusMap.put(new Integer(40), "FAXバック確認済み");
        return hacchuustatusMap;

    }

    public static void setHacchuustatusMap() {
        if (hacchuustatusMap == null || hacchuustatusMap.isEmpty()) {
            newHacchuustatusMap();
        }
    }

    public static TreeMap getHacchuustatusMap() {
        setHacchuustatusMap();
        return hacchuustatusMap;
    }

    public static void clearHacchuustatusMap() {
        hacchuustatusMap = null;
    }

    public static String get(int id) {
        setHacchuustatusMap();
        return (String)hacchuustatusMap.get(new Integer(id));
    }
}
