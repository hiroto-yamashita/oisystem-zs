package com.oisix.oisystemzs.objectmap;

import java.util.TreeMap;

public class NyuukokubunFormatMap {
    private static TreeMap nyuukokubunFormatMap = null;

    static {
        newNyuukokubunFormatMap();
    }

    public static TreeMap newNyuukokubunFormatMap() {
        nyuukokubunFormatMap = new TreeMap();
        nyuukokubunFormatMap.put(new Integer(1), "d“ü");
        nyuukokubunFormatMap.put(new Integer(2), "•Ô•i“üŒÉ");
        nyuukokubunFormatMap.put(new Integer(3), "U‘Ö“üŒÉ");
        nyuukokubunFormatMap.put(new Integer(4), "’I‰µ’²®");
        return nyuukokubunFormatMap;

    }

    public static void setNyuukokubunFormatMap() {
        if (nyuukokubunFormatMap == null || nyuukokubunFormatMap.isEmpty()) {
            new NyuukokubunFormatMap();

        }
    }

    public static TreeMap getNyuukokubunFormatMap() {
        setNyuukokubunFormatMap();
        return nyuukokubunFormatMap;
    }

    public static void clearNyuukokubunFormatMap() {
        nyuukokubunFormatMap = null;
    }

    public static String get(int id) {
        setNyuukokubunFormatMap();
        return (String)nyuukokubunFormatMap.get(new Integer(id));
    }
}
