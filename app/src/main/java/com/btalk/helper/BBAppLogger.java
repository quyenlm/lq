package com.btalk.helper;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.banalytics.BARecordService;
import com.banalytics.BAReportService;
import com.banalytics.BATrackerConst;
import com.banalytics.Log2FileService;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.tp.a.h;
import com.vk.sdk.api.VKApiConst;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

public class BBAppLogger {
    public static String APP_LOG_FLAG = "garena";
    public static String LOG_FILE_DIR;
    public static String NOTIFICATION_CHANNEL = "seagroup.android.logger";
    public static int NOTIFICATION_ID = 999999;
    public static boolean NO_SERVICE = true;
    public static boolean RELEASE_VERSION = true;
    public static boolean REPORTING_STATS = false;
    public static String country = "";
    @Deprecated
    public static boolean isReportStats = false;
    private static Application mApp;
    public static int versionCode;

    public static void attachApp(Application app) {
        mApp = app;
    }

    private static String getLogString(String format, Object... args) {
        if (TextUtils.isEmpty(format)) {
            return "";
        }
        if (args == null || args.length <= 0) {
            return __generateLineInfo() + getThreadInfo() + format;
        }
        try {
            return __generateLineInfo() + getThreadInfo() + String.format(Locale.ENGLISH, format, args);
        } catch (Exception e) {
            e(e);
            return __generateLineInfo() + getThreadInfo() + format;
        }
    }

    public static void e(String format, Object... args) {
        if (!RELEASE_VERSION) {
            String ss = getLogString(format, args);
            log2file(ss, true);
            Log.e(APP_LOG_FLAG, ss);
            if (ss.contains("UnknownFormatConversionException")) {
                i("OK", new Object[0]);
            }
        }
    }

    public static void w(String format, Object... args) {
        if (!RELEASE_VERSION) {
            String ss = getLogString(format, args);
            log2file(ss, false);
            Log.w(APP_LOG_FLAG, ss);
            if (ss.contains("UnknownFormatConversionException")) {
                i("OK", new Object[0]);
            }
        }
    }

    public static void v(String format, Object... args) {
        if (!RELEASE_VERSION) {
            String ss = getLogString(format, args);
            Log.v(APP_LOG_FLAG, ss);
            log2file(ss, false);
            if (ss.contains("UnknownFormatConversionException")) {
                v("OK", new Object[0]);
            }
        }
    }

    public static void d(String format, Object... args) {
        if (!RELEASE_VERSION) {
            String ss = getLogString(format, args);
            Log.d(APP_LOG_FLAG, ss);
            log2file(ss, false);
            if (ss.contains("UnknownFormatConversionException")) {
                d("OK", new Object[0]);
            }
        }
    }

    private static void log2file(String ss, boolean forceFlush) {
        if (!RELEASE_VERSION && !NO_SERVICE) {
            Log2FileService.log(mApp, ss, forceFlush, LOG_FILE_DIR);
        }
    }

    @Deprecated
    public static void display(String format, Object... args) {
        if (!RELEASE_VERSION) {
            Log.w(APP_LOG_FLAG, getThreadInfo() + String.format(Locale.US, format, args));
        }
    }

    public static void i(String format, Object... args) {
        if (!RELEASE_VERSION) {
            String ss = getLogString(format, args);
            Log.i(APP_LOG_FLAG, ss);
            log2file(ss, false);
            if (ss.contains("UnknownFormatConversionException")) {
                i("OK", new Object[0]);
            }
        }
    }

    public static void e(Throwable e) {
        if (!RELEASE_VERSION) {
            StackTraceElement[] stackTraceElement = e.getStackTrace();
            int currentIndex = -1;
            int i = 0;
            while (true) {
                if (i >= stackTraceElement.length) {
                    break;
                } else if (stackTraceElement[i].getMethodName().compareTo(APDataReportManager.ACCOUNTINPUT_PRE) == 0) {
                    currentIndex = i + 1;
                    break;
                } else {
                    i++;
                }
            }
            if (currentIndex >= 0) {
                String fullClassName = stackTraceElement[currentIndex].getClassName();
                String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
                String methodName = stackTraceElement[currentIndex].getMethodName();
                String lineNumber = String.valueOf(stackTraceElement[currentIndex].getLineNumber());
                String traceContent = String.format(Locale.US, "position at %s.%s(%s.java:%s)", new Object[]{fullClassName, methodName, className, lineNumber});
                Log.e(APP_LOG_FLAG, traceContent);
                log2file(traceContent, true);
                return;
            }
            Writer result = new StringWriter();
            e.printStackTrace(new PrintWriter(result));
            Log.e(APP_LOG_FLAG, result.toString());
            log2file(result.toString(), true);
        }
    }

    private static String __generateLineInfo() {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        int currentIndex = -1;
        int i = 0;
        while (true) {
            if (i >= stackTraceElement.length) {
                break;
            }
            if (stackTraceElement[i].getClassName().equals(BBAppLogger.class.getName())) {
                String method = stackTraceElement[i].getMethodName();
                if (method.equals(APDataReportManager.ACCOUNTINPUT_PRE) || method.equals("w") || method.equals("i") || method.equals(APDataReportManager.GOODSANDMONTHSINPUT_PRE) || method.equals(VKApiConst.VERSION)) {
                    currentIndex = i + 1;
                }
            }
            i++;
        }
        currentIndex = i + 1;
        if (currentIndex == -1) {
            Log.v(APP_LOG_FLAG, "CANNOT GENERATE LINE INFO");
            return "";
        }
        StackTraceElement traceElement = stackTraceElement[currentIndex];
        String fullClassName = traceElement.getClassName();
        return traceElement.getMethodName() + h.a + fullClassName.substring(fullClassName.lastIndexOf(".") + 1) + ".java:" + traceElement.getLineNumber() + "): ";
    }

    private static String getThreadInfo() {
        return String.format(Locale.US, "[thread_id:%d name=%s] ", new Object[]{Long.valueOf(Thread.currentThread().getId()), Thread.currentThread().getName()});
    }

    public static void startRecordService(String remarks, String cmdType, long userId) {
        if (mApp != null && !NO_SERVICE) {
            if (REPORTING_STATS || isReportStats) {
                Intent trackerIntent = new Intent(mApp, BARecordService.class);
                trackerIntent.putExtra("command", 2);
                trackerIntent.putExtra(BATrackerConst.JSON_KEYS.USER_ID, Long.valueOf(userId));
                trackerIntent.putExtra("description", remarks);
                trackerIntent.putExtra(BATrackerConst.JSON_KEYS.CMD_TYPE, cmdType);
                trackerIntent.putExtra("country", country);
                trackerIntent.putExtra("app_version", versionCode);
                mApp.startService(trackerIntent);
                Log.i("startRecordService", trackerIntent.getExtras().toString());
                log2file("startRecordService " + trackerIntent.getDataString(), true);
                Intent serviceIntent = new Intent(mApp, BAReportService.class);
                serviceIntent.putExtra("command", 3);
                ((AlarmManager) mApp.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(1, System.currentTimeMillis() + BATrackerConst.TRACKER_WAKE_UP_INTERVAL, PendingIntent.getService(mApp, BATrackerConst.PENDING_INTENT_ID, serviceIntent, 134217728));
            }
        }
    }
}
