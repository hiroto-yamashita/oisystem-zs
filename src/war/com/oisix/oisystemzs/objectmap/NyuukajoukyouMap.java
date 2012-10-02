package com.oisix.oisystemzs.objectmap;

import java.util.TreeMap;

public class NyuukajoukyouMap {
    private static TreeMap nyuukajoukyouMap = null;

    static {
        newNyuukajoukyouMap();
    }

    public static TreeMap newNyuukajoukyouMap() {
        nyuukajoukyouMap = new TreeMap();
        nyuukajoukyouMap.put(new Integer(1), "–¢“ü‰×");
        nyuukajoukyouMap.put(new Integer(2), "ˆê•”“ü‰×");
        nyuukajoukyouMap.put(new Integer(3), "“ü‰×Ï‚İ");
        return nyuukajoukyouMap;

    }

    public static void setNyuukajoukyouMap() {
        if (nyuukajoukyouMap == null || nyuukajoukyouMap.isEmpty()) {
            newNyuukajoukyouMap();

        }
    }

    public static TreeMap getNyuukajoukyouMap() {
        setNyuukajoukyouMap();
        return nyuukajoukyouMap;
    }

    public static void clearNyuukajoukyouMap() {
        nyuukajoukyouMap = null;
    }

    public static String get(int id) {
        setNyuukajoukyouMap();
        return (String)nyuukajoukyouMap.get(new Integer(id));
    }
}
