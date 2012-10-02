package com.oisix.oisystemzs.objectmap;

import java.util.TreeMap;

public class ShukkajoukyouMap {
    private static TreeMap joukyouMap = null;

    static {
        newShukkajoukyouMap();
    }

    public static TreeMap newShukkajoukyouMap() {
        joukyouMap = new TreeMap();
        joukyouMap.put(new Integer(1), "–¢o‰×");
        joukyouMap.put(new Integer(2), "ˆê•”o‰×");
        joukyouMap.put(new Integer(3), "o‰×Ï‚İ");
        return joukyouMap;

    }

    public static void setShukkajoukyouMap() {
        if (joukyouMap == null || joukyouMap.isEmpty()) {
            newShukkajoukyouMap();

        }
    }

    public static TreeMap getShukkajoukyouMap() {
        setShukkajoukyouMap();
        return joukyouMap;
    }

    public static void clearShukkajoukyouMap() {
        joukyouMap = null;
    }

    public static String get(int id) {
        setShukkajoukyouMap();
        return (String)joukyouMap.get(new Integer(id));
    }
}
