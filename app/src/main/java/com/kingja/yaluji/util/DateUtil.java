package com.kingja.yaluji.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:TODO
 * Create Time:2018/7/9 11:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DateUtil {
    private static final int MILLIS_DAY = 1000 * 60 * 60 * 24;
    private static final int MILLIS_HOUR = 1000 * 60 * 60;
    private static final int MILLIS_MIN = 1000 * 60;
    private static final String FORMAT_DATE_HOUR_MIN_SEC = "yyyy-MM-dd HH:mm:ss";

    public static int[] getDeadlineDate(String deadline) {
        Date date = getDateFromString(deadline);
        long deadlineMillis = date.getTime();
        long nowMillis = System.currentTimeMillis();
        long leftMillis = deadlineMillis - nowMillis;
        if (leftMillis < 0) {
            return new int[]{0, 0, 0};
        }
        int leftDay = (int) (leftMillis / MILLIS_DAY);
        int leftHour = (int) (leftMillis % MILLIS_DAY / MILLIS_HOUR);
        int leftMin = (int) (leftMillis % MILLIS_HOUR / MILLIS_MIN);
        return new int[]{leftDay, leftHour, leftMin};
    }

    private static Date getDateFromString(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_HOUR_MIN_SEC);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static boolean isOverDue(String deadline) {
        long deadlineMillis = getDateFromString(deadline).getTime();
        return deadlineMillis - System.currentTimeMillis() < 0;
    }

    public static boolean isBeginSell(String startTime) {
        long starttimeMillis = getDateFromString(startTime).getTime();
        return starttimeMillis - System.currentTimeMillis() <= 0;
    }

    public static String getDateString(long time) {
        return getDateString(time, "yyyy-MM-dd");
    }

    public static String getNowDate() {
        return getDateString(new Date().getTime(), "yyyy-MM-dd");
    }

    public static String getDateString(long time, String fromat) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(fromat);
        return sf.format(d);
    }

}
