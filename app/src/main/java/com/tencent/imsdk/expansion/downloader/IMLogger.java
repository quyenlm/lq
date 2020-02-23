package com.tencent.imsdk.expansion.downloader;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class IMLogger {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    private static String DEBUG_LEVEL_META_NAME = "com.tencent.imsdk.debug.level";
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static String TAG = "iMSDK ExpansionFilesDownloader";
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static int logDeviceLevel = 2;

    public static void init(Context context) {
        d("Before level control : curLevel = " + getDebugLevel(context));
        setLogDeviceLevel(getDebugLevel(context));
    }

    public static void setLogDeviceLevel(int level) {
        if (level >= 2 && level <= 7) {
            logDeviceLevel = level;
        }
    }

    public static void d(String msg) {
        if (logDeviceLevel <= 3) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (logDeviceLevel <= 4) {
            Log.i(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (logDeviceLevel <= 6) {
            Log.e(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (logDeviceLevel <= 5) {
            Log.w(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (logDeviceLevel <= 2) {
            Log.v(TAG, msg);
        }
    }

    private static int getDebugLevel(Context currentContext) {
        int i = 2;
        int level = 2;
        if (currentContext == null) {
            w("config not initialized !");
            return 2;
        }
        String debugLevel = readMetaDataFromApplication(currentContext, DEBUG_LEVEL_META_NAME);
        if (debugLevel == null || debugLevel.length() <= 0) {
            e("need meta in application : " + DEBUG_LEVEL_META_NAME);
            return 2;
        }
        try {
            level = Integer.parseInt(debugLevel);
        } catch (NumberFormatException e) {
        }
        if (level <= 7 && level >= 2) {
            i = level;
        }
        int i2 = level;
        return i;
    }

    private static String readMetaDataFromApplication(Context context, String key) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            try {
                String infoS = String.valueOf(appInfo.metaData.get(key));
                return (infoS == null || infoS.length() <= 0) ? appInfo.metaData.getInt(key) + "" : infoS;
            } catch (Exception e) {
                d("get meta error : " + e.getMessage());
                return "";
            }
        } catch (PackageManager.NameNotFoundException e2) {
            d("get application meta error : " + e2.getMessage());
        } catch (NullPointerException e3) {
            d("Meta Key \"" + key + " \" is not exist");
        }
        return "";
    }
}
