package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.v;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TbsExtensionFunctionManager {
    public static final String BUGLY_SWITCH_FILE_NAME = "bugly_switch.txt";
    public static final String COOKIE_SWITCH_FILE_NAME = "cookie_switch.txt";
    public static final String SP_KEY_COOKIE_DB_VERSION = "cookie_db_version";
    public static final String SP_NAME_FOR_COOKIE = "cookie_compatiable";
    public static final int SWITCH_BYTE_COOKIE = 1;
    public static final String USEX5_FILE_NAME = "usex5.txt";
    private static TbsExtensionFunctionManager b;
    private boolean a;

    private TbsExtensionFunctionManager() {
    }

    public static TbsExtensionFunctionManager getInstance() {
        if (b == null) {
            synchronized (TbsExtensionFunctionManager.class) {
                if (b == null) {
                    b = new TbsExtensionFunctionManager();
                }
            }
        }
        return b;
    }

    public synchronized boolean canUseFunction(Context context, String str) {
        boolean z = false;
        synchronized (this) {
            File file = new File(context.getFilesDir(), str);
            if (file == null) {
                TbsLog.i("TbsExtensionFunMana", "canUseFunction," + str + " is null!");
            } else if (file.exists() && file.isFile()) {
                z = true;
            }
        }
        return z;
    }

    public synchronized int getRomCookieDBVersion(Context context) {
        int i = -1;
        synchronized (this) {
            SharedPreferences sharedPreferences = Build.VERSION.SDK_INT >= 11 ? context.getSharedPreferences(SP_NAME_FOR_COOKIE, 4) : context.getSharedPreferences(SP_NAME_FOR_COOKIE, 0);
            if (sharedPreferences != null) {
                i = sharedPreferences.getInt(SP_KEY_COOKIE_DB_VERSION, -1);
            }
        }
        return i;
    }

    public synchronized void initTbsBuglyIfNeed(Context context) {
        String absolutePath;
        if (!this.a) {
            if (!canUseFunction(context, BUGLY_SWITCH_FILE_NAME)) {
                TbsLog.i("TbsExtensionFunMana", "bugly is forbiden!!");
            } else {
                if (TbsShareManager.isThirdPartyApp(context)) {
                    absolutePath = TbsShareManager.c(context);
                } else {
                    File q = am.a().q(context);
                    if (q == null) {
                        TbsLog.i("TbsExtensionFunMana", "getTbsCoreShareDir is null");
                    }
                    if (q.listFiles() == null || q.listFiles().length <= 0) {
                        TbsLog.i("TbsExtensionFunMana", "getTbsCoreShareDir is empty!");
                    } else {
                        absolutePath = q.getAbsolutePath();
                    }
                }
                if (TextUtils.isEmpty(absolutePath)) {
                    TbsLog.i("TbsExtensionFunMana", "bugly init ,corePath is null");
                } else {
                    File q2 = am.a().q(context);
                    if (q2 == null) {
                        TbsLog.i("TbsExtensionFunMana", "bugly init ,optDir is null");
                    } else {
                        File file = new File(absolutePath, "tbs_bugly_dex.jar");
                        try {
                            v.a(new DexLoader(file.getParent(), context, new String[]{file.getAbsolutePath()}, q2.getAbsolutePath(), (Map<String, Object>) null).loadClass("com.tencent.smtt.tbs.bugly.TBSBuglyManager"), "initBugly", (Class<?>[]) new Class[]{Context.class, String.class, String.class, String.class}, context, absolutePath, String.valueOf(WebView.getTbsSDKVersion(context)), String.valueOf(WebView.getTbsCoreVersion(context)));
                            this.a = true;
                            TbsLog.i("TbsExtensionFunMana", "initTbsBuglyIfNeed success!");
                        } catch (Throwable th) {
                            TbsLog.i("TbsExtensionFunMana", "bugly init ,try init bugly failed(need new core):" + Log.getStackTraceString(th));
                        }
                    }
                }
            }
        }
    }

    public synchronized boolean setFunctionEnable(Context context, String str, boolean z) {
        boolean z2 = false;
        synchronized (this) {
            if (context != null) {
                File file = new File(context.getFilesDir(), str);
                if (file == null) {
                    TbsLog.e("TbsExtensionFunMana", "setFunctionEnable," + str + " is null!");
                } else {
                    if (z) {
                        if (!file.exists()) {
                            try {
                                if (file.createNewFile()) {
                                    z2 = true;
                                }
                            } catch (IOException e) {
                                TbsLog.e("TbsExtensionFunMana", "setFunctionEnable,createNewFile fail:" + str);
                                e.printStackTrace();
                            }
                        }
                    } else if (file.exists()) {
                        if (file.delete()) {
                            z2 = true;
                        } else {
                            TbsLog.e("TbsExtensionFunMana", "setFunctionEnable,file.delete fail:" + str);
                        }
                    }
                    z2 = true;
                }
            }
        }
        return z2;
    }
}
