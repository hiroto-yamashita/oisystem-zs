package com.oisix.oisystemfr;

import java.util.Calendar;

public class DateUtil {

    private static int testtime;

    protected static void setTesttime(int atesttime) {
        testtime = atesttime;
    }

    public static java.util.Date getDate() {
        java.util.Date date = new java.util.Date();
        if (testtime != 0) {
            long time = date.getTime() + testtime;
            return new java.util.Date(time);
        }
        return date;
    }

    public static java.util.Date getDate(String year, String month,
      String date) throws NumberFormatException {
        Calendar cal = getCalendar(year, month, date);
        return cal.getTime();
    }

    public static Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate());
        return cal;
    }

    public static Calendar getCalendar(String year, String month, String date)
      throws NumberFormatException {
        int iyear = Integer.parseInt(year);
        int imonth = Integer.parseInt(month) - 1;
        int idate = Integer.parseInt(date);
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(iyear, imonth, idate);
        return cal;
    }
}
