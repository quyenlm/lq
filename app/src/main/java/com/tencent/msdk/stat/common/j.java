package com.tencent.msdk.stat.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.appsflyer.share.Constants;
import com.banalytics.BATrackerConst;
import com.tencent.component.net.download.multiplex.http.Apn;
import com.tencent.msdk.stat.StatConfig;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import com.tencent.qqgamemi.util.TimeUtils;
import com.tencent.tp.r;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import org.apache.http.HttpHost;
import org.json.JSONObject;

public class j {
    private static String a = null;
    private static String b = null;
    private static String c = null;
    private static String d = null;
    private static Random e = null;
    private static DisplayMetrics f = null;
    private static String g = null;
    private static String h = "";
    private static String i = "";
    private static volatile int j = -1;
    /* access modifiers changed from: private */
    public static StatLogger k = null;
    private static String l = null;
    private static String m = null;
    private static volatile int n = -1;
    private static String o = null;
    private static String p = null;
    private static long q = -1;
    private static String r = "";
    private static m s = null;
    private static String t = "__MTA_FIRST_ACTIVATE__";
    private static int u = -1;
    private static long v = -1;
    private static int w = 0;
    private static String x = "";
    private static volatile String y = null;

    public static boolean A(Context context) {
        ActivityManager activityManager;
        if (context == null || (activityManager = (ActivityManager) context.getSystemService("activity")) == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo next : activityManager.getRunningAppProcesses()) {
            if (next.processName.startsWith(packageName)) {
                return next.importance == 400;
            }
        }
        return false;
    }

    public static String B(Context context) {
        if (context == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity.activityInfo == null || resolveActivity.activityInfo.packageName.equals(SystemMediaRouteProvider.PACKAGE_NAME)) {
            return null;
        }
        return resolveActivity.activityInfo.packageName;
    }

    private static long C(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static int a() {
        return g().nextInt(Integer.MAX_VALUE);
    }

    public static int a(Context context, boolean z) {
        if (z) {
            w = z(context);
        }
        return w;
    }

    public static Long a(String str, String str2, int i2, int i3, Long l2) {
        if (str == null || str2 == null) {
            return l2;
        }
        if (str2.equalsIgnoreCase(".") || str2.equalsIgnoreCase("|")) {
            str2 = "\\" + str2;
        }
        String[] split = str.split(str2);
        if (split.length != i3) {
            return l2;
        }
        try {
            Long l3 = 0L;
            for (String valueOf : split) {
                l3 = Long.valueOf(((long) i2) * (l3.longValue() + Long.valueOf(valueOf).longValue()));
            }
            return l3;
        } catch (NumberFormatException e2) {
            return l2;
        }
    }

    public static String a(int i2) {
        Calendar instance = Calendar.getInstance();
        instance.roll(6, i2);
        return new SimpleDateFormat("yyyyMMdd").format(instance.getTime());
    }

    public static String a(long j2) {
        return new SimpleDateFormat("yyyyMMdd").format(new Date(j2));
    }

    public static String a(Context context, String str) {
        if (!StatConfig.isEnableConcurrentProcess()) {
            return str;
        }
        if (m == null) {
            m = p(context);
        }
        return m != null ? str + "_" + m : str;
    }

    public static String a(String str) {
        if (str == null) {
            return "0";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b2 : digest) {
                byte b3 = b2 & 255;
                if (b3 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(b3));
            }
            return stringBuffer.toString();
        } catch (Throwable th) {
            return "0";
        }
    }

    public static HttpHost a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
                return null;
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getTypeName() != null && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                return null;
            }
            String extraInfo = activeNetworkInfo.getExtraInfo();
            if (extraInfo == null) {
                return null;
            }
            if (extraInfo.equals("cmwap") || extraInfo.equals(Apn.APN_3GWAP) || extraInfo.equals("uniwap")) {
                return new HttpHost("10.0.0.172", 80);
            }
            if (extraInfo.equals("ctwap")) {
                return new HttpHost("10.0.0.200", 80);
            }
            String defaultHost = Proxy.getDefaultHost();
            if (defaultHost != null && defaultHost.trim().length() > 0) {
                return new HttpHost(defaultHost, Proxy.getDefaultPort());
            }
            return null;
        } catch (Throwable th) {
            k.e(th);
        }
    }

    public static void a(Context context, int i2) {
        w = i2;
        o.b(context, "mta.qq.com.difftime", i2);
    }

    public static boolean a(StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (statSpecifyReportedInfo == null) {
            return false;
        }
        return c(statSpecifyReportedInfo.getAppKey());
    }

    public static byte[] a(byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
        byte[] bArr2 = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length * 2);
        while (true) {
            int read = gZIPInputStream.read(bArr2);
            if (read != -1) {
                byteArrayOutputStream.write(bArr2, 0, read);
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayInputStream.close();
                gZIPInputStream.close();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    public static long b(String str) {
        return a(str, ".", 100, 3, 0L).longValue();
    }

    public static synchronized StatLogger b() {
        StatLogger statLogger;
        synchronized (j.class) {
            if (k == null) {
                k = new StatLogger(StatConstants.LOG_TAG);
                k.setDebugEnable(false);
            }
            statLogger = k;
        }
        return statLogger;
    }

    public static synchronized String b(Context context) {
        String str;
        synchronized (j.class) {
            if (a == null || a.trim().length() == 0) {
                a = p.a(context);
                if (a == null || a.trim().length() == 0) {
                    a = Integer.toString(g().nextInt(Integer.MAX_VALUE));
                }
                str = a;
            } else {
                str = a;
            }
        }
        return str;
    }

    public static long c() {
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            instance.set(14, 0);
            return instance.getTimeInMillis() + TimeUtils.MILLIS_IN_DAY;
        } catch (Throwable th) {
            k.e(th);
            return System.currentTimeMillis() + TimeUtils.MILLIS_IN_DAY;
        }
    }

    public static synchronized String c(Context context) {
        String str;
        synchronized (j.class) {
            if (c == null || c.trim().length() == 0) {
                c = p.b(context);
            }
            str = c;
        }
        return str;
    }

    public static boolean c(String str) {
        return (str == null || str.trim().length() == 0) ? false : true;
    }

    public static DisplayMetrics d(Context context) {
        if (f == null) {
            f = new DisplayMetrics();
            ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(f);
        }
        return f;
    }

    public static String d() {
        if (c(p)) {
            return p;
        }
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        p = String.valueOf((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1000000) + Constants.URL_PATH_DELIMITER + String.valueOf(e() / 1000000);
        return p;
    }

    public static long e() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
    }

    public static boolean e(Context context) {
        NetworkInfo[] allNetworkInfo;
        try {
            if (p.a(context, "android.permission.ACCESS_WIFI_STATE")) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
                if (!(connectivityManager == null || (allNetworkInfo = connectivityManager.getAllNetworkInfo()) == null)) {
                    for (int i2 = 0; i2 < allNetworkInfo.length; i2++) {
                        if (allNetworkInfo[i2].getTypeName().equalsIgnoreCase("WIFI") && allNetworkInfo[i2].isConnected()) {
                            return true;
                        }
                    }
                }
                return false;
            }
            k.warn("can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return false;
        } catch (Throwable th) {
            k.e(th);
        }
    }

    public static String f(Context context) {
        if (b != null) {
            return b;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("TA_APPKEY");
                if (string != null) {
                    b = string;
                    return string;
                }
                k.i("Could not read APPKEY meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            k.i("Could not read APPKEY meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String g(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                Object obj = applicationInfo.metaData.get("InstallChannel");
                if (obj != null) {
                    return obj.toString();
                }
                k.w("Could not read InstallChannel meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            k.e((Object) "Could not read InstallChannel meta-data from AndroidManifest.xml");
        }
        return null;
    }

    private static synchronized Random g() {
        Random random;
        synchronized (j.class) {
            if (e == null) {
                e = new Random();
            }
            random = e;
        }
        return random;
    }

    private static long h() {
        if (q > 0) {
            return q;
        }
        long j2 = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            String readLine = bufferedReader.readLine();
            if (!TextUtils.isEmpty(readLine)) {
                j2 = (long) (Integer.valueOf(readLine.split("\\s+")[1]).intValue() * 1024);
            }
            bufferedReader.close();
        } catch (Exception e2) {
        }
        q = j2;
        return q;
    }

    public static String h(Context context) {
        TelephonyManager telephonyManager;
        if (g != null) {
            return g;
        }
        try {
            if (!p.a(context, r.a)) {
                k.e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
            } else if (j(context) && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null) {
                g = telephonyManager.getSimOperator();
            }
        } catch (Throwable th) {
            k.e(th);
        }
        return g;
    }

    public static String i(Context context) {
        if (c(h)) {
            return h;
        }
        try {
            h = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (h == null) {
                return "";
            }
        } catch (Throwable th) {
            k.e(th);
        }
        return h;
    }

    public static boolean j(Context context) {
        return context.getPackageManager().checkPermission(r.a, context.getPackageName()) == 0;
    }

    public static String k(Context context) {
        try {
            if (!p.a(context, "android.permission.INTERNET") || !p.a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                k.e((Object) "can not get the permission of android.permission.ACCESS_WIFI_STATE");
                return "";
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                String typeName = activeNetworkInfo.getTypeName();
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (typeName != null) {
                    return typeName.equalsIgnoreCase("WIFI") ? "WIFI" : typeName.equalsIgnoreCase("MOBILE") ? (extraInfo == null || extraInfo.trim().length() <= 0) ? "MOBILE" : extraInfo : (extraInfo == null || extraInfo.trim().length() <= 0) ? typeName : extraInfo;
                }
            }
            return "";
        } catch (Throwable th) {
            k.e(th);
        }
    }

    public static Integer l(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String m(Context context) {
        if (c(i)) {
            return i;
        }
        try {
            i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (i == null || i.length() == 0) {
                return "unknown";
            }
        } catch (Throwable th) {
            k.e(th);
        }
        return i;
    }

    public static int n(Context context) {
        if (j > -1) {
            return j;
        }
        j = 0;
        synchronized (j.class) {
            try {
                if (n.a()) {
                    j = 1;
                }
            } catch (Throwable th) {
                k.e(th);
            }
        }
        return j;
    }

    public static String o(Context context) {
        String path;
        if (c(l)) {
            return l;
        }
        try {
            if (p.a(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                String externalStorageState = Environment.getExternalStorageState();
                if (externalStorageState == null || !externalStorageState.equals("mounted") || (path = Environment.getExternalStorageDirectory().getPath()) == null) {
                    return null;
                }
                StatFs statFs = new StatFs(path);
                l = String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / 1000000) + Constants.URL_PATH_DELIMITER + String.valueOf((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000);
                return l;
            }
            k.warn("can not get the permission of android.permission.WRITE_EXTERNAL_STORAGE");
            return null;
        } catch (Throwable th) {
            k.e(th);
            return null;
        }
    }

    static String p(Context context) {
        try {
            if (m != null) {
                return m;
            }
            int myPid = Process.myPid();
            Iterator<ActivityManager.RunningAppProcessInfo> it = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ActivityManager.RunningAppProcessInfo next = it.next();
                if (next.pid == myPid) {
                    m = next.processName;
                    break;
                }
            }
            return m;
        } catch (Throwable th) {
        }
    }

    public static String q(Context context) {
        return a(context, StatConstants.DATABASE_NAME);
    }

    public static synchronized Integer r(Context context) {
        Integer valueOf;
        int i2 = 0;
        synchronized (j.class) {
            if (n <= 0) {
                n = o.a(context, "MTA_EVENT_INDEX", 0);
                o.b(context, "MTA_EVENT_INDEX", n + 1000);
            } else if (n % 1000 == 0) {
                try {
                    int i3 = n + 1000;
                    if (n < 2147383647) {
                        i2 = i3;
                    }
                    o.b(context, "MTA_EVENT_INDEX", i2);
                } catch (Throwable th) {
                    k.w(th);
                }
            }
            n++;
            valueOf = Integer.valueOf(n);
        }
        return valueOf;
    }

    public static String s(Context context) {
        try {
            return String.valueOf(C(context) / 1000000) + Constants.URL_PATH_DELIMITER + String.valueOf(h() / 1000000);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static JSONObject t(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("n", k.a());
            String d2 = k.d();
            if (d2 != null && d2.length() > 0) {
                jSONObject.put(BATrackerConst.EVENT_TYPES.NOT_AVAILABLE, d2);
            }
            int b2 = k.b();
            if (b2 > 0) {
                jSONObject.put("fx", b2 / 1000000);
            }
            int c2 = k.c();
            if (c2 > 0) {
                jSONObject.put("fn", c2 / 1000000);
            }
        } catch (Throwable th) {
            Log.w(StatConstants.LOG_TAG, "get cpu error", th);
        }
        return jSONObject;
    }

    public static String u(Context context) {
        List<Sensor> sensorList;
        if (c(r)) {
            return r;
        }
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (!(sensorManager == null || (sensorList = sensorManager.getSensorList(-1)) == null)) {
                StringBuilder sb = new StringBuilder(sensorList.size() * 10);
                for (int i2 = 0; i2 < sensorList.size(); i2++) {
                    sb.append(sensorList.get(i2).getType());
                    if (i2 != sensorList.size() - 1) {
                        sb.append(",");
                    }
                }
                r = sb.toString();
            }
        } catch (Throwable th) {
            k.e(th);
        }
        return r;
    }

    public static synchronized int v(Context context) {
        int i2;
        synchronized (j.class) {
            if (u != -1) {
                i2 = u;
            } else {
                w(context);
                i2 = u;
            }
        }
        return i2;
    }

    public static void w(Context context) {
        u = o.a(context, t, 1);
        if (u == 1) {
            o.b(context, t, 0);
        }
    }

    public static boolean x(Context context) {
        if (v < 0) {
            v = o.a(context, "mta.qq.com.checktime", 0);
        }
        return Math.abs(System.currentTimeMillis() - v) > TimeUtils.MILLIS_IN_DAY;
    }

    public static void y(Context context) {
        v = System.currentTimeMillis();
        o.b(context, "mta.qq.com.checktime", v);
    }

    public static int z(Context context) {
        return o.a(context, "mta.qq.com.difftime", 0);
    }
}
