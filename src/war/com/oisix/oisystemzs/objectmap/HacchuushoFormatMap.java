package com.oisix.oisystemzs.objectmap;

import java.util.TreeMap;

public class HacchuushoFormatMap {
    private static TreeMap hacchuushoFormatMap = null;

    static {
        newHacchuushoFormatMap();
    }

    public static TreeMap newHacchuushoFormatMap() {
        hacchuushoFormatMap = new TreeMap();
        hacchuushoFormatMap.put(new Integer(1), "â¡çHïi");
        hacchuushoFormatMap.put(new Integer(3), "íºëó");
        return hacchuushoFormatMap;

    }

    public static void setHacchuushoFormatMap() {
        if (hacchuushoFormatMap == null || hacchuushoFormatMap.isEmpty()) {
            newHacchuushoFormatMap();

        }
    }

    public static TreeMap getHacchuushoFormatMap() {
        setHacchuushoFormatMap();
        return hacchuushoFormatMap;
    }

    public static void clearHacchuushoFormatMap() {
        hacchuushoFormatMap = null;
    }

    public static String get(int id) {
        setHacchuushoFormatMap();
        return (String)hacchuushoFormatMap.get(new Integer(id));
    }
}
