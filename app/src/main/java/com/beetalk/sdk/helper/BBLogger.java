package com.beetalk.sdk.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.banalytics.BARecordService;
import com.banalytics.BAReportService;
import com.banalytics.BATrackerConst;
import com.banalytics.Log2FileService;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.SessionStatus;
import com.beetalk.sdk.networking.GarenaUserAgent;
import com.tencent.component.debug.TraceFormat;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.tp.a.h;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

public class BBLogger {
    public static String APP_LOG_FLAG = ("com.garena.msdk[" + GGPlatform.GGGetSDKVersion() + "]");
    private static boolean HIDE_TOKENS = (SDKConstants.getEnvironment() == SDKConstants.GGEnvironment.PRODUCTION);
    private static String appContextPackage = "";

    public static void init(Context applicationContext) {
        if (applicationContext != null && applicationContext.getApplicationContext() != null) {
            appContextPackage = applicationContext.getApplicationContext().getPackageName();
        }
    }

    private static void logToFile(String severity, String tag, String message) {
        try {
            String finalTag = severity + Constants.URL_PATH_DELIMITER + tag + "[" + appContextPackage + "]";
            Context context = GGLoginSession.getApplicationContext();
            if (context != null) {
                Intent intent = new Intent(context, Log2FileService.class);
                intent.putExtra("time", System.currentTimeMillis());
                intent.putExtra("log", finalTag + message);
                intent.putExtra("forceFlush", severity.equals(TraceFormat.STR_ERROR));
                context.startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getLogString(String format, Object... args) {
        if (TextUtils.isEmpty(format)) {
            return "";
        }
        if (args == null || args.length <= 0) {
            return __generateLineInfo() + getThreadInfo() + format;
        }
        try {
            return __generateLineInfo() + getThreadInfo() + stripTokens(format(format, args));
        } catch (Exception exception) {
            e(exception);
            return __generateLineInfo() + getThreadInfo() + format;
        }
    }

    private static String __generateLineInfo() {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        int currentIndex = -1;
        int traceElement = 0;
        while (true) {
            if (traceElement >= stackTraceElement.length) {
                break;
            }
            if (stackTraceElement[traceElement].getClassName().equals(BBLogger.class.getName())) {
                String fullClassName = stackTraceElement[traceElement].getMethodName();
                if (fullClassName.equals(APDataReportManager.ACCOUNTINPUT_PRE) || fullClassName.equals("w") || fullClassName.equals("i") || fullClassName.equals(APDataReportManager.GOODSANDMONTHSINPUT_PRE)) {
                    currentIndex = traceElement + 1;
                }
            }
            traceElement++;
        }
        currentIndex = traceElement + 1;
        if (currentIndex == -1) {
            Log.i(APP_LOG_FLAG, "CANNOT GENERATE DEBUG");
            return "";
        }
        StackTraceElement element = stackTraceElement[currentIndex];
        String fullClassName2 = element.getClassName();
        return element.getMethodName() + h.a + fullClassName2.substring(fullClassName2.lastIndexOf(".") + 1) + ".java:" + element.getLineNumber() + "): ";
    }

    public static void e(String format, Object... args) {
        if (!SDKConstants.NO_LOG) {
            String msg = getLogString(format, args);
            Log.e(APP_LOG_FLAG, msg);
            logToFile(TraceFormat.STR_ERROR, APP_LOG_FLAG, msg);
        }
        if (format(format, args).contains("UnknownFormatConversionException")) {
            i("OK", new Object[0]);
        }
    }

    private static String getThreadInfo() {
        return String.format(Locale.ENGLISH, "[thread_id:%d name=%s] ", new Object[]{Long.valueOf(Thread.currentThread().getId()), Thread.currentThread().getName()});
    }

    public static void w(String format, Object... args) {
        if (!SDKConstants.NO_LOG) {
            String msg = getLogString(format, args);
            Log.w(APP_LOG_FLAG, msg);
            logToFile(TraceFormat.STR_WARN, APP_LOG_FLAG, msg);
        }
        if (format(format, args).contains("UnknownFormatConversionException")) {
            i("OK", new Object[0]);
        }
    }

    public static void d(String format, Object... args) {
        if (!SDKConstants.NO_LOG) {
            String message = getLogString(format, args);
            Log.d(APP_LOG_FLAG, message);
            logToFile(TraceFormat.STR_DEBUG, APP_LOG_FLAG, message);
        }
        if (format(format, args).contains("UnknownFormatConversionException")) {
            i("OK", new Object[0]);
        }
    }

    public static void display(String format, Object... args) {
        w(format, args);
    }

    public static void i(String format, Object... args) {
        if (!SDKConstants.NO_LOG) {
            if (!SDKConstants.RELEASE_VERSION) {
                __generateDEBUG();
            }
            String message = getLogString(format, args);
            Log.i(APP_LOG_FLAG, message);
            logToFile(TraceFormat.STR_INFO, APP_LOG_FLAG, message);
        }
    }

    private static String stripTokens(String log) {
        if (HIDE_TOKENS) {
            return log.replaceAll("token=\\S*", "token=ACCESS_TOKEN_REMOVED ").replaceAll("client_secret=\\S*", "client_secret=CLIENT_SECRET_REMOVED ").replaceAll("app_key=\\S*", "appKey=APP_SECRET_REMOVED ").replaceAll("access_token=\\S*", "access_token=ACCESS_TOKEN_REMOVED ").replaceAll("code=\\S*", "code=SOME_CODE_REMOVED ");
        }
        return log;
    }

    private static String format(String format, Object... args) {
        return (args == null || args.length == 0) ? format : String.format(Locale.ENGLISH, format, args);
    }

    public static void e(Exception e) {
        if (!SDKConstants.NO_LOG && e != null) {
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
                String message = "at " + fullClassName + "." + stackTraceElement[currentIndex].getMethodName() + h.a + className + ".java:" + String.valueOf(stackTraceElement[currentIndex].getLineNumber()) + h.b;
                Log.e(APP_LOG_FLAG + " position", message);
                logToFile(TraceFormat.STR_ERROR, APP_LOG_FLAG + " position", message);
                return;
            }
            Writer result = new StringWriter();
            e.printStackTrace(new PrintWriter(result));
            Log.e(APP_LOG_FLAG, result.toString());
            logToFile(TraceFormat.STR_ERROR, APP_LOG_FLAG, result.toString());
        }
    }

    public static int __getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

    private static void __generateDEBUG() {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        int currentIndex = -1;
        int i = 0;
        while (true) {
            if (i >= stackTraceElement.length) {
                break;
            } else if (stackTraceElement[i].getMethodName().compareTo("i") == 0) {
                currentIndex = i + 1;
                break;
            } else {
                i++;
            }
        }
        if (currentIndex == -1) {
            Log.i(APP_LOG_FLAG, "CANNOT GENERATE DEBUG");
            return;
        }
        String fullClassName = stackTraceElement[currentIndex].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = stackTraceElement[currentIndex].getMethodName();
        Log.i(APP_LOG_FLAG + " position", "at " + fullClassName + "." + methodName + h.a + className + ".java:" + String.valueOf(stackTraceElement[currentIndex].getLineNumber()) + h.b);
    }

    public static void r(Context context, String tag, String info, Object... args) {
        if (context != null) {
            try {
                if (GGPlatform.getAppConfig() != null && GGPlatform.getAppConfig().getAppLogConfig().booleanValue()) {
                    startRecordService(context, format(info, args) + "|ua:" + new GarenaUserAgent().toString(), tag);
                    return;
                }
            } catch (Exception e) {
                return;
            }
        }
        i("not record remote log due to non-context or client log disabled on server", new Object[0]);
    }

    public static void r(String tag, String info, Object... args) {
        if (GGLoginSession.getApplicationContext() != null) {
            r(GGLoginSession.getApplicationContext(), tag, info, args);
        }
    }

    public static void startRecordService(Context context, String remarks, String cmdType) {
        Intent trackerIntent = new Intent(context, BARecordService.class);
        trackerIntent.putExtra("command", 2);
        if (GGLoginSession.getCurrentSession() == null || GGLoginSession.getCurrentSession().getSessionStatus() != SessionStatus.TOKEN_AVAILABLE) {
            trackerIntent.putExtra(BATrackerConst.JSON_KEYS.USER_ID_STR, BATrackerConst.BA_DEFAULT.STR);
        } else {
            trackerIntent.putExtra(BATrackerConst.JSON_KEYS.USER_ID_STR, GGLoginSession.getCurrentSession().getOpenId());
            remarks = remarks + "|platform:" + GGLoginSession.getCurrentSession().getSessionProvider().getValue();
        }
        trackerIntent.putExtra("description", remarks);
        trackerIntent.putExtra(BATrackerConst.JSON_KEYS.CMD_TYPE, cmdType);
        trackerIntent.putExtra("country", LocaleHelper.getDefaultDeviceCountry());
        trackerIntent.putExtra("app_version", Helper.getIntegerAppId(context));
        context.startService(trackerIntent);
        Intent serviceIntent = new Intent(context, BAReportService.class);
        serviceIntent.putExtra("command", 3);
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(1, System.currentTimeMillis() + BATrackerConst.TRACKER_WAKE_UP_INTERVAL, PendingIntent.getService(context, BATrackerConst.PENDING_INTENT_ID, serviceIntent, 134217728));
    }
}
