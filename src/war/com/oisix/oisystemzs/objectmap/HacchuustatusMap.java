package com.oisix.oisystemzs.objectmap;

import java.util.TreeMap;

public class HacchuustatusMap {
    private static TreeMap hacchuustatusMap = null;

    static {
        newHacchuustatusMap();
    }

    public static TreeMap newHacchuustatusMap() {
        hacchuustatusMap = new TreeMap();
        hacchuustatusMap.put(new Integer(10), "���o��");
        hacchuustatusMap.put(new Integer(20), "�o�͍ς�");
        hacchuustatusMap.put(new Integer(30), "FAX�ς�");
        hacchuustatusMap.put(new Integer(40), "FAX�o�b�N�m�F�ς�");
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
