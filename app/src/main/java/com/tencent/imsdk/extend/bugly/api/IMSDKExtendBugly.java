package com.tencent.imsdk.extend.bugly.api;

import android.content.Context;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.extend.bugly.base.ExtendBuglyManager;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMSDKExtendBugly {
    private static Context currentContext;

    public static void initialize(Context context) {
        ExtendBuglyManager.getInstance().init(context);
        currentContext = context;
    }

    public static void setLogCache(int byteSize) {
        try {
            ExtendBuglyManager.getInstance().setLogCache(byteSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buglyLog(int level, String tag, String log) {
        try {
            ExtendBuglyManager.getInstance().buglyLog(level, tag, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buglyPutUserData(String key, String value) {
        ExtendBuglyManager.getInstance().buglyPutUserData(key, value);
    }

    public static void setBuglyAppVersion(String version) {
        if (currentContext != null) {
            IMLogger.d("set bugly app version success, new version: " + version);
            CrashReport.setAppVersion(currentContext, version);
            return;
        }
        IMLogger.e("need init");
    }
}
