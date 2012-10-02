package com.oisix.oisystemfr;

public class Debug {

    private static boolean flg = false;

    public static void setFlg(boolean b) { flg = b; }

    public static void println(String msg) {
        if (flg) {
            System.out.println(msg);
        }
    }

    public static void println(String msg, Object o) {
        if (flg) {
            System.out.println(o.toString() + ":" + msg);
        }
    }

    public static void println(Exception e) {
        if (flg) {
            e.printStackTrace();
        }
    }
}
