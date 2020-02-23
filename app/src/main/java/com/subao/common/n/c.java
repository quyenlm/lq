package com.subao.common.n;

import android.support.annotation.NonNull;
import com.tencent.qqgamemi.util.TimeUtils;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/* compiled from: CalendarUtils */
public class c {
    public static final TimeZone a = new SimpleTimeZone(0, "UTC");
    public static final TimeZone b = new SimpleTimeZone(28800000, "CST");

    public static int a(long j) {
        return (int) (j / TimeUtils.MILLIS_IN_DAY);
    }

    public static int a() {
        return a(System.currentTimeMillis() + 28800000);
    }

    @NonNull
    public static Calendar b(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance;
    }

    public static String a(Calendar calendar, int i) {
        boolean z = true;
        StringBuilder sb = new StringBuilder(64);
        boolean z2 = (i & 1) != 0;
        if (z2) {
            a(sb, calendar.get(1)).append('-');
            a(sb, calendar.get(2) + 1).append('-');
            a(sb, calendar.get(5));
        }
        if ((i & 2) == 0) {
            z = false;
        }
        if (z) {
            if (z2) {
                sb.append(' ');
            }
            a(sb, calendar.get(11)).append(':');
            a(sb, calendar.get(12)).append(':');
            a(sb, calendar.get(13));
        }
        if ((i & 4) != 0) {
            if (z2 || z) {
                sb.append(' ');
            }
            int i2 = calendar.get(15) / 3600000;
            if (i2 >= 0) {
                sb.append('+');
            }
            sb.append(i2);
        }
        return sb.toString();
    }

    private static StringBuilder a(StringBuilder sb, int i) {
        if (i < 10) {
            sb.append('0');
        }
        return sb.append(i);
    }
}
