package com.tencent.tp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.tencent.tp.c.c;
import com.tencent.tp.c.i;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collection;
import java.util.Enumeration;

public class TssSdkRuntime {
    private static Context m_context;
    private static PackageInfo m_packageInfo;

    private static void SendLocalIps() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                    if (inetAddresses != null) {
                        while (inetAddresses.hasMoreElements()) {
                            m.b("ip:" + inetAddresses.nextElement().getHostAddress());
                        }
                    }
                }
            }
        } catch (Throwable th) {
        }
    }

    private static void doAsyncInitializeTask(Context context) {
        Field field;
        Method method = null;
        a aVar = new a(context);
        if (Build.VERSION.SDK_INT > 10) {
            Method[] methods = aVar.getClass().getMethods();
            Field[] fields = aVar.getClass().getFields();
            int i = 0;
            while (true) {
                if (i >= fields.length) {
                    field = null;
                    break;
                } else if (fields[i].getName().equals("THREAD_POOL_EXECUTOR")) {
                    field = fields[i];
                    break;
                } else {
                    i++;
                }
            }
            int i2 = 0;
            while (true) {
                if (i2 >= methods.length) {
                    break;
                } else if (methods[i2].getName().equals("executeOnExecutor")) {
                    method = methods[i2];
                    break;
                } else {
                    i2++;
                }
            }
            if (method == null || field == null) {
                aVar.execute(new Void[0]);
                return;
            }
            try {
                method.invoke(aVar, new Object[]{field.get((Object) null), null});
            } catch (Throwable th) {
            }
        } else {
            aVar.execute(new Void[0]);
        }
    }

    public static void doSyncInitalizeTask2(Context context) {
        h a = h.a();
        m.a("apk_name:" + getPackageName());
        m.a("app_name:" + getAppName());
        m.a("ver:" + getAppVer());
        m.a("vercode:" + getAppVersionCode());
        m.a("imei:" + a.a(context));
        m.a("model:" + r.j());
        m.a("api_level:" + r.c());
        m.a("sys_ver:" + r.r());
        m.a("wifi:" + a.b(context));
        m.a("sdcard:" + getSdCardPath(context));
        m.a("sd_package:" + getExternalPackageDir(context));
        m.a("net_type:" + getNetWorkType());
        m.a("net_provider:" + getNetProviderType(context));
        m.a("ip_beg");
        SendLocalIps();
        m.a("ip_end");
        m.a("dev_macaddress:" + a.c(m_context));
        m.a("dev_imsi:" + a.e(m_context));
        m.a("dev_resolution:" + r.l(m_context));
        m.a("dev_cpuname:" + r.t());
        m.a("dev_romsize:" + r.q());
        c cVar = new c();
        String a2 = cVar.a();
        if (a2 != null) {
            m.a("cert:" + a2);
        }
        String c = cVar.c();
        if (c != null) {
            m.a("cert_author:" + c);
        }
        m.a("certenv:" + (e.a() ? "true" : "false"));
    }

    private static void doSyncInitializeTask(Context context) {
        m.a("jar_ver:3.5.8(2018/08/14)-jar-version");
        m.a("files_dir:" + getFilesDirPath(context));
        m.a("apk_path:" + getApkPath(context));
        m.a("lib_dir:" + getNativeLibraryDir(context));
    }

    private static boolean getAdbEnabled(Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "adb_enabled", 0) != 0;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean getAdbEnabledOverUsb(Context context) {
        return getAdbEnabled(context) && getUsbConnected(context);
    }

    private static String getApkPath(Context context) {
        try {
            return context.getPackageResourcePath();
        } catch (Throwable th) {
            return null;
        }
    }

    public static Context getAppContext() {
        if (m_context == null) {
            try {
                m_context = (Context) g.a("android.app.ActivityThread", g.a("android.app.ActivityThread", "currentActivityThread", new Class[0], new Object[0]), "mInitialApplication");
            } catch (Exception e) {
                m_context = null;
            }
        }
        return m_context;
    }

    public static String getAppName() {
        Context appContext = getAppContext();
        if (appContext != null) {
            return r.i(appContext);
        }
        return null;
    }

    public static String getAppVer() {
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return null;
    }

    public static int getAppVersionCode() {
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    public static Activity getCurrentActivity() {
        Activity unity3dCurrentActivity = getUnity3dCurrentActivity();
        return unity3dCurrentActivity == null ? getFirstActivity() : unity3dCurrentActivity;
    }

    private static String getExternalPackageDir(Context context) {
        try {
            return i.d(context);
        } catch (Throwable th) {
            return null;
        }
    }

    private static String getFilesDirPath(Context context) {
        try {
            return i.a(context);
        } catch (Throwable th) {
            return null;
        }
    }

    private static Activity getFirstActivity() {
        Object a;
        try {
            Object[] array = ((Collection) g.a("java.util.Map", "values", g.a("android.app.ActivityThread", g.a("android.app.ActivityThread", "currentActivityThread", new Class[0], new Object[0]), "mActivities"), new Class[0], new Object[0])).toArray();
            for (Object obj : array) {
                if (obj != null && (a = g.a("android.app.ActivityThread$ActivityClientRecord", obj, "activity")) != null) {
                    return (Activity) a;
                }
            }
            return null;
        } catch (Exception e) {
            if (m.c() == 1) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static String getNativeLibraryDir(Context context) {
        String str;
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (Build.VERSION.SDK_INT >= 9) {
                try {
                    str = (String) c.a("android.content.pm.ApplicationInfo", applicationInfo, "nativeLibraryDir");
                } catch (Exception e) {
                    str = null;
                }
            } else {
                str = null;
            }
            return str == null ? applicationInfo.dataDir + File.separator + "lib" : str;
        } catch (Throwable th) {
            return null;
        }
    }

    public static int getNetProviderType(Context context) {
        int i;
        TelephonyManager telephonyManager;
        if (context == null) {
            return -1;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (!(activeNetworkInfo == null || activeNetworkInfo.getType() != 0 || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null)) {
                switch (telephonyManager.getNetworkType()) {
                    case 1:
                    case 3:
                    case 8:
                        i = 2;
                        break;
                    case 2:
                        i = 1;
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 12:
                        i = 3;
                        break;
                    case 13:
                        i = 5;
                        break;
                }
            }
            i = -1;
            return i;
        } catch (Throwable th) {
            return -1;
        }
    }

    public static int getNetWorkType() {
        int i;
        Context appContext = getAppContext();
        if (appContext == null) {
            return -1;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) appContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                switch (activeNetworkInfo.getType()) {
                    case 0:
                        i = 1;
                        break;
                    case 1:
                        i = 2;
                        break;
                }
            }
            i = -1;
            return i;
        } catch (Throwable th) {
            return -1;
        }
    }

    public static PackageInfo getPackageInfo() {
        PackageManager packageManager;
        if (m_packageInfo == null) {
            try {
                Context appContext = getAppContext();
                String packageName = getPackageName();
                if (!(appContext == null || packageName == null || (packageManager = appContext.getPackageManager()) == null)) {
                    try {
                        m_packageInfo = packageManager.getPackageInfo(packageName, 0);
                    } catch (Exception e) {
                        m_packageInfo = null;
                    }
                }
            } catch (Exception e2) {
                m_packageInfo = null;
            }
        }
        return m_packageInfo;
    }

    public static String getPackageName() {
        try {
            Context appContext = getAppContext();
            if (appContext != null) {
                return appContext.getPackageName();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private static String getSdCardPath(Context context) {
        try {
            return i.c(context);
        } catch (Throwable th) {
            return null;
        }
    }

    public static Activity getUnity3dCurrentActivity() {
        try {
            return (Activity) g.a("com.unity3d.player.UnityPlayer", "currentActivity");
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean getUsbConnected(Context context) {
        try {
            return context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("plugged", -1) == 2;
        } catch (Throwable th) {
            return false;
        }
    }

    public static void initialize2() {
        Context appContext = getAppContext();
        if (appContext != null) {
            try {
                doSyncInitializeTask(appContext);
            } catch (Throwable th) {
            }
            doAsyncInitializeTask(appContext);
            m.a("info:main initialize done");
        }
    }
}
