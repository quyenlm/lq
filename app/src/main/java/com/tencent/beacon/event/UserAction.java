package com.tencent.beacon.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.tencent.beacon.cover.UserActionProxy;
import com.tencent.beacon.cover.g;
import com.tencent.beacon.cover.h;
import com.tencent.beacon.upload.InitHandleListener;
import com.tencent.beacon.upload.UploadHandleListener;
import com.tencent.component.debug.TraceFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserAction {
    private static boolean a = false;
    private static boolean b = false;
    private static Context c;
    private static boolean d = true;
    private static long e = 0;
    private static InitHandleListener f;
    private static UploadHandleListener g;
    private static Boolean h;
    private static Boolean i;
    private static Boolean j;
    private static String k;
    private static String l;
    private static String m;
    private static String n;
    private static String o;
    private static Map<String, String> p;
    private static String q;
    private static String r;
    private static String s;
    private static Boolean t;
    private static long u;
    private static Map<String, String> v;
    private static final ArrayList<a> w = new ArrayList<>();
    private static ClassLoader x;
    private static UserActionProxy y;

    static {
        try {
            y = (UserActionProxy) Class.forName("com.tencent.beacon.core.UserActionProxyImpl").newInstance();
        } catch (Throwable th) {
            g.a(TraceFormat.STR_DEBUG, "load UserActionProxy error: " + th.getMessage(), new Object[0]);
        }
    }

    public static void onCompLoaded(ClassLoader classLoader) {
        if (x == null) {
            x = classLoader;
            if (a() && c != null) {
                initUserAction(c, d, e, f, g);
                c = null;
            }
        }
    }

    private static boolean a() {
        if (y != null) {
            return true;
        }
        if (x == null) {
            return false;
        }
        try {
            y = (UserActionProxy) x.loadClass("com.tencent.beacon.core.UserActionProxyImpl").newInstance();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (y != null) {
            return true;
        }
        return false;
    }

    public static void initUserAction(Context context) {
        initUserAction(context, true);
    }

    public static void initUserAction(Context context, boolean z) {
        initUserAction(context, z, 0);
    }

    public static void initUserAction(Context context, boolean z, long j2) {
        initUserAction(context, z, j2, (InitHandleListener) null);
    }

    public static void initUserAction(Context context, boolean z, long j2, InitHandleListener initHandleListener) {
        initUserAction(context, z, j2, initHandleListener, (UploadHandleListener) null);
    }

    public static void initUserAction(Context context, boolean z, long j2, InitHandleListener initHandleListener, UploadHandleListener uploadHandleListener) {
        if (y != null) {
            if (!a && y != null) {
                if (!(h == null || j == null)) {
                    setLogAble(h.booleanValue(), j.booleanValue());
                    h = null;
                    j = null;
                }
                if (i != null) {
                    enablePagePath(i.booleanValue());
                    i = null;
                }
                if (p != null) {
                    setAdditionalInfo(p);
                    p = null;
                }
                if (k != null) {
                    setAppkey(k);
                    k = null;
                }
                if (l != null) {
                    setAppVersion(l);
                    l = null;
                }
                if (m != null) {
                    setChannelID(m);
                    m = null;
                }
                if (n != null) {
                    setQQ(n);
                    n = null;
                }
                if (o != null) {
                    setUserID(o);
                    o = null;
                }
                if (s != null) {
                    b(s);
                    s = null;
                }
                if (!(q == null || r == null)) {
                    setReportDomain(q, r);
                    q = null;
                    r = null;
                }
            }
            y.initUserAction(context, z, j2, initHandleListener, uploadHandleListener);
            if (!a) {
                if (t != null) {
                    loginEvent(t.booleanValue(), u, v);
                    t = null;
                    v = null;
                }
                synchronized (w) {
                    Iterator<a> it = w.iterator();
                    while (it.hasNext()) {
                        a next = it.next();
                        onUserAction(next.a, next.b, next.c, 0, next.d, next.e, next.f);
                    }
                    w.clear();
                }
            }
            a = true;
            return;
        }
        if (!b) {
            new Thread(h.a(context)).start();
            b = true;
        }
        c = context;
        d = z;
        e = j2;
        f = initHandleListener;
        g = uploadHandleListener;
    }

    public static boolean onUserAction(String str, boolean z, long j2, long j3, Map<String, String> map, boolean z2) {
        return onUserAction(str, z, j2, j3, map, z2, false);
    }

    public static boolean onUserAction(String str, boolean z, long j2, long j3, Map<String, String> map, boolean z2, boolean z3) {
        if (y != null) {
            return y.onUserAction(str, z, j2, j3, map, z2, z3);
        }
        a aVar = new a((byte) 0);
        aVar.a = str;
        aVar.b = z;
        aVar.c = j2;
        aVar.d = map;
        aVar.e = z2;
        aVar.f = z3;
        synchronized (w) {
            if (w.size() < 100) {
                w.add(aVar);
            }
        }
        return false;
    }

    public static boolean loginEvent(boolean z, long j2, Map<String, String> map) {
        if (y != null) {
            return y.loginEvent(z, j2, map);
        }
        t = Boolean.valueOf(z);
        u = j2;
        v = map;
        return false;
    }

    public static void setLogAble(boolean z, boolean z2) {
        g.a = z;
        if (y != null) {
            y.setLogAble(z, z2);
            return;
        }
        h = Boolean.valueOf(z);
        j = Boolean.valueOf(z2);
    }

    public static void enablePagePath(boolean z) {
        if (y != null) {
            y.enablePagePath(z);
        } else {
            i = Boolean.valueOf(z);
        }
    }

    public static String getQIMEI() {
        if (y != null) {
            return y.getQIMEI();
        }
        if (c != null) {
            return getRtQIMEI(c);
        }
        return "";
    }

    public static String getRtQIMEI(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("DENGTA_META", 0);
        String str = "";
        String str2 = "";
        if (sharedPreferences != null) {
            str = sharedPreferences.getString("QIMEI_DENGTA", "");
            str2 = sharedPreferences.getString("IMEI_DENGTA", "");
        }
        if (!"".equals(str)) {
            return str;
        }
        if (!"".equals(str2)) {
            return str2;
        }
        String a2 = a(context);
        if ("".equals(a2)) {
            return b(context);
        }
        return a2;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(android.content.Context r7) {
        /*
            r0 = 1
            r2 = 0
            if (r7 == 0) goto L_0x0053
            java.lang.String r1 = "android.permission.READ_PHONE_STATE"
            int r3 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x005a }
            int r4 = android.os.Process.myUid()     // Catch:{ Throwable -> 0x005a }
            int r1 = r7.checkPermission(r1, r3, r4)     // Catch:{ Throwable -> 0x005a }
            if (r1 != 0) goto L_0x0049
            r1 = r0
        L_0x0015:
            if (r1 != 0) goto L_0x005e
            android.content.pm.PackageManager r3 = r7.getPackageManager()     // Catch:{ Throwable -> 0x0050 }
            java.lang.String r4 = r7.getPackageName()     // Catch:{ Throwable -> 0x0050 }
            r5 = 4096(0x1000, float:5.74E-42)
            android.content.pm.PackageInfo r3 = r3.getPackageInfo(r4, r5)     // Catch:{ Throwable -> 0x0050 }
            java.lang.String[] r3 = r3.requestedPermissions     // Catch:{ Throwable -> 0x0050 }
            if (r3 == 0) goto L_0x004e
            int r4 = r3.length     // Catch:{ Throwable -> 0x0050 }
        L_0x002a:
            if (r2 >= r4) goto L_0x004e
            r5 = r3[r2]     // Catch:{ Throwable -> 0x0050 }
            java.lang.String r6 = "android.permission.READ_PHONE_STATE"
            boolean r5 = r6.equals(r5)     // Catch:{ Throwable -> 0x0050 }
            if (r5 == 0) goto L_0x004b
        L_0x0036:
            if (r0 == 0) goto L_0x005b
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r7.getSystemService(r0)     // Catch:{ Throwable -> 0x005a }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Throwable -> 0x005a }
            java.lang.String r0 = r0.getDeviceId()     // Catch:{ Throwable -> 0x005a }
            if (r0 != 0) goto L_0x0055
            java.lang.String r0 = ""
        L_0x0048:
            return r0
        L_0x0049:
            r1 = r2
            goto L_0x0015
        L_0x004b:
            int r2 = r2 + 1
            goto L_0x002a
        L_0x004e:
            r0 = r1
            goto L_0x0036
        L_0x0050:
            r0 = move-exception
            r0 = r1
            goto L_0x0036
        L_0x0053:
            r0 = r2
            goto L_0x0036
        L_0x0055:
            java.lang.String r0 = r0.toLowerCase()     // Catch:{ Throwable -> 0x005a }
            goto L_0x0048
        L_0x005a:
            r0 = move-exception
        L_0x005b:
            java.lang.String r0 = ""
            goto L_0x0048
        L_0x005e:
            r0 = r1
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.event.UserAction.a(android.content.Context):java.lang.String");
    }

    private static String b(Context context) {
        try {
            String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            if (string == null) {
                return "";
            }
            return string.toLowerCase();
        } catch (Throwable th) {
            return "";
        }
    }

    public static void setUploadMode(boolean z) {
        if (y != null) {
            y.setUploadMode(z);
        }
    }

    public static String getSDKVersion() {
        return "3.0.4";
    }

    public static void setAppKey(String str) {
        h.a = str;
        if (y != null) {
            y.setAppKey(str);
        } else {
            k = str;
        }
    }

    public static void setAppVersion(String str) {
        h.b = str;
        if (y != null) {
            y.setAppVersion(str);
        } else {
            l = str;
        }
    }

    public static void setChannelID(String str) {
        if (y != null) {
            y.setChannelID(str);
        } else {
            m = str;
        }
    }

    public static void setUserID(String str) {
        if (y != null) {
            y.setUserID(str);
        } else {
            o = str;
        }
    }

    public static void setQQ(String str) {
        if (y != null) {
            y.setQQ(str);
        } else {
            n = str;
        }
    }

    public static void setAdditionalInfo(Map<String, String> map) {
        if (y != null) {
            y.setAdditionalInfo(map);
        } else {
            p = map;
        }
    }

    public static void doUploadRecords() {
        if (y != null) {
            y.doUploadRecords();
        }
    }

    public static void flushObjectsToDB(boolean z) {
        if (y != null) {
            y.flushObjectsToDB(z);
        }
    }

    public static String getCloudParas(String str) {
        if (y != null) {
            return y.getCloudParas(str);
        }
        return null;
    }

    public static void setReportDomain(String str, String str2) {
        if (y != null) {
            y.setReportDomain(str, str2);
            return;
        }
        q = str;
        r = str2;
    }

    @Deprecated
    public static void setSDKVersion(String str) {
    }

    @Deprecated
    public static void setAPPVersion(String str) {
        h.b = str;
        if (y != null) {
            y.setAPPVersion(str);
        } else {
            l = str;
        }
    }

    @Deprecated
    public static boolean testSpeedDomain(List<String> list) {
        return false;
    }

    @Deprecated
    public static boolean testSpeedIp(List<String> list) {
        return false;
    }

    @Deprecated
    public static void setAppkey(String str) {
        h.a = str;
        if (y != null) {
            y.setAppkey(str);
        } else {
            k = str;
        }
    }

    /* access modifiers changed from: private */
    public static void b(String str) {
        if (y != null) {
            y.setJsClientId(str);
        } else {
            s = str;
        }
    }

    public static void configBeaconJs(WebView webView) {
        if (webView != null) {
            webView.addJavascriptInterface(new BeaconJsBridge(), "beacon");
            webView.getSettings().setDomStorageEnabled(true);
        }
    }

    private static class BeaconJsBridge {
        private BeaconJsBridge() {
        }

        @JavascriptInterface
        public void onSetJsClientID(String str) {
            UserAction.b(str);
        }
    }

    private static class a {
        String a;
        boolean b;
        long c;
        Map<String, String> d;
        boolean e;
        boolean f;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public static void onPageIn(String str) {
        if (y != null) {
            y.onPageIn(str);
        }
    }

    public static void onPageOut(String str) {
        if (y != null) {
            y.onPageOut(str);
        }
    }
}
