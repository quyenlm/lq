package com.tencent.qqgamemi.util;

import com.tencent.component.debug.FileTracerConfig;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
    public static final long MILLIS_IN_DAY = 86400000;
    public static final int SECONDS_IN_DAY = 86400;

    public static boolean isSameDayOfMillis(long ms1, long ms2) {
        return compareByDay(ms1, ms2) == 0;
    }

    public static long compareByDay(long ms1, long ms2) {
        return toDay(ms1) - toDay(ms2);
    }

    public static long toDay(long millis) {
        return (((long) TimeZone.getDefault().getOffset(millis)) + millis) / MILLIS_IN_DAY;
    }

    public static String easyReadTime(long timestampMillsec, boolean withTime) {
        long delta = System.currentTimeMillis() - timestampMillsec;
        if (delta < 0) {
            return chinaFormatTime(timestampMillsec, withTime);
        }
        if (delta < Constants.WATCHDOG_WAKE_TIMER) {
            return "刚刚";
        }
        if (delta < 3600000) {
            return (delta / Constants.WATCHDOG_WAKE_TIMER) + "分钟前";
        }
        if (delta < MILLIS_IN_DAY) {
            return (delta / 3600000) + "小时前";
        }
        if (delta < 604800000) {
            return (delta / MILLIS_IN_DAY) + "天前";
        }
        return chinaFormatTime(timestampMillsec, withTime);
    }

    public static String chinaFormatTime(long timestampMillsec, boolean withTime) {
        String formatString = FileTracerConfig.DEF_FOLDER_FORMAT;
        if (withTime) {
            formatString = formatString + " HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString, Locale.ROOT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return dateFormat.format(new Date(timestampMillsec));
    }
}
