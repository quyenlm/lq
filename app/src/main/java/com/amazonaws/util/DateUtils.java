package com.amazonaws.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DateUtils {
    public static final String ALTERNATE_ISO8601_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String COMPRESSED_DATE_PATTERN = "yyyyMMdd'T'HHmmss'Z'";
    /* access modifiers changed from: private */
    public static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
    public static final String ISO8601_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String RFC822_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap();

    private static ThreadLocal<SimpleDateFormat> getSimpleDateFormat(final String pattern) {
        ThreadLocal<SimpleDateFormat> sdf = sdfMap.get(pattern);
        if (sdf == null) {
            synchronized (sdfMap) {
                try {
                    sdf = sdfMap.get(pattern);
                    if (sdf == null) {
                        AnonymousClass1 r2 = new ThreadLocal<SimpleDateFormat>() {
                            /* access modifiers changed from: protected */
                            public SimpleDateFormat initialValue() {
                                SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
                                sdf.setTimeZone(DateUtils.GMT_TIMEZONE);
                                sdf.setLenient(false);
                                return sdf;
                            }
                        };
                        try {
                            sdfMap.put(pattern, r2);
                            sdf = r2;
                        } catch (Throwable th) {
                            th = th;
                            AnonymousClass1 r1 = r2;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        return sdf;
    }

    public static Date parse(String pattern, String dateString) {
        try {
            return getSimpleDateFormat(pattern).get().parse(dateString);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    public static String format(String pattern, Date date) {
        return getSimpleDateFormat(pattern).get().format(date);
    }

    public static Date parseISO8601Date(String dateString) {
        try {
            return parse(ISO8601_DATE_PATTERN, dateString);
        } catch (IllegalArgumentException e) {
            return parse(ALTERNATE_ISO8601_DATE_PATTERN, dateString);
        }
    }

    public static String formatISO8601Date(Date date) {
        return format(ISO8601_DATE_PATTERN, date);
    }

    public static Date parseRFC822Date(String dateString) {
        return parse(RFC822_DATE_PATTERN, dateString);
    }

    public static String formatRFC822Date(Date date) {
        return format(RFC822_DATE_PATTERN, date);
    }

    public static Date parseCompressedISO8601Date(String dateString) {
        return parse(COMPRESSED_DATE_PATTERN, dateString);
    }
}
