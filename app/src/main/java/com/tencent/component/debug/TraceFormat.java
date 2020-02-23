package com.tencent.component.debug;

import android.text.format.Time;
import android.util.Log;
import com.tencent.component.net.download.multiplex.http.Apn;

public final class TraceFormat {
    public static final TraceFormat DEFAULT = new TraceFormat();
    public static final String STR_ASSERT = "A";
    public static final String STR_DEBUG = "D";
    public static final String STR_ERROR = "E";
    public static final String STR_INFO = "I";
    public static final String STR_UNKNOWN = "-";
    public static final String STR_VERBOSE = "V";
    public static final String STR_WARN = "W";
    public static final String TRACE_TIME_FORMAT = "%Y-%m-%d %H:%M:%S";

    public final String getLevelPrefix(int level) {
        switch (level) {
            case 1:
                return STR_VERBOSE;
            case 2:
                return STR_DEBUG;
            case 4:
                return STR_INFO;
            case 8:
                return STR_WARN;
            case 16:
                return STR_ERROR;
            case 32:
                return STR_ASSERT;
            default:
                return "-";
        }
    }

    public String formatTrace(int level, Thread thread, long time, String tag, String msg, Throwable tr) {
        long ms = time % 1000;
        Time timeObj = new Time();
        timeObj.set(time);
        StringBuilder builder = new StringBuilder();
        builder.append(getLevelPrefix(level)).append('/').append(timeObj.format(TRACE_TIME_FORMAT)).append('.');
        if (ms < 10) {
            builder.append("00");
        } else if (ms < 100) {
            builder.append('0');
        }
        builder.append(ms).append(' ').append('[');
        if (thread == null) {
            builder.append(Apn.APN_UNKNOWN);
        } else {
            builder.append(thread.getName());
        }
        builder.append(']').append('[').append(tag).append(']').append(' ').append(msg).append(10);
        if (tr != null) {
            builder.append("* Exception : \n").append(Log.getStackTraceString(tr)).append(10);
        }
        return builder.toString();
    }
}
