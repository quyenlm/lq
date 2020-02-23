package com.garena.game.kgtw;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.webkit.WebView;

public class NGameApplication extends MultiDexApplication {
    private static final String TAG = NGameApplication.class.getSimpleName();

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "attachBaseContext");
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        try {
            String packageName = getPackageName();
            String processName = getCurProcessName();
            Log.d(TAG, "processName:" + processName);
            if (Build.VERSION.SDK_INT >= 28) {
                WebView.setDataDirectorySuffix(packageName + processName);
            }
        } catch (Exception e) {
            Log.w(TAG, "OtherPushImpl.registerPush Exception!");
        }
    }

    private String getCurProcessName() {
        try {
            int pid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo processInfo : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
                if (processInfo.pid == pid) {
                    return processInfo.processName;
                }
            }
        } catch (Exception e) {
            Log.w(TAG, Log.getStackTraceString(e));
        }
        return null;
    }
}
