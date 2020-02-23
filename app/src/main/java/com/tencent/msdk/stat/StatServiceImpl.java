package com.tencent.msdk.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.msdk.stat.a.a;
import com.tencent.msdk.stat.a.c;
import com.tencent.msdk.stat.a.d;
import com.tencent.msdk.stat.a.f;
import com.tencent.msdk.stat.a.h;
import com.tencent.msdk.stat.common.StatConstants;
import com.tencent.msdk.stat.common.StatLogger;
import com.tencent.msdk.stat.common.b;
import com.tencent.msdk.stat.common.j;
import com.tencent.msdk.stat.common.o;
import com.vk.sdk.api.VKApiConst;
import java.lang.Thread;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class StatServiceImpl {
    private static volatile long A = -1;
    private static StatSpecifyReportedInfo B = null;
    static volatile int a = 0;
    static volatile long b = 0;
    static volatile long c = 0;
    private static Handler d;
    /* access modifiers changed from: private */
    public static volatile Map<c, Long> e = new ConcurrentHashMap();
    private static volatile Map<String, Properties> f = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static volatile Map<Integer, Integer> g = new ConcurrentHashMap(10);
    /* access modifiers changed from: private */
    public static volatile long h = 0;
    private static volatile long i = 0;
    private static volatile long j = 0;
    private static String k = "";
    private static volatile int l = 0;
    /* access modifiers changed from: private */
    public static volatile String m = "";
    /* access modifiers changed from: private */
    public static volatile String n = "";
    /* access modifiers changed from: private */
    public static Map<String, Long> o = new ConcurrentHashMap();
    private static Map<String, Long> p = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static StatLogger q = j.b();
    /* access modifiers changed from: private */
    public static Thread.UncaughtExceptionHandler r = null;
    private static volatile boolean s = true;
    /* access modifiers changed from: private */
    public static Context t = null;
    private static volatile boolean u = false;
    private static volatile boolean v = false;
    private static volatile boolean w = true;
    private static Handler x = null;
    private static List<g> y = new CopyOnWriteArrayList();
    private static volatile Runnable z = null;

    static int a(Context context, boolean z2, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        boolean z3 = true;
        long currentTimeMillis = System.currentTimeMillis();
        boolean z4 = z2 && currentTimeMillis - i >= ((long) StatConfig.getSessionTimoutMillis());
        i = currentTimeMillis;
        if (j == 0) {
            j = j.c();
        }
        if (currentTimeMillis >= j) {
            j = j.c();
            if (aj.a(context).b(context).d() != 1) {
                aj.a(context).b(context).a(1);
            }
            StatConfig.b(0);
            a = 0;
            k = j.a(0);
            z4 = true;
        }
        String str = k;
        if (j.a(statSpecifyReportedInfo)) {
            str = statSpecifyReportedInfo.getAppKey() + k;
        }
        if (p.containsKey(str)) {
            z3 = z4;
        }
        if (z3) {
            if (j.a(statSpecifyReportedInfo)) {
                a(context, statSpecifyReportedInfo);
            } else if (StatConfig.c() < StatConfig.getMaxDaySessionNumbers()) {
                j.w(context);
                a(context, (StatSpecifyReportedInfo) null);
            } else {
                q.e((Object) "Exceed StatConfig.getMaxDaySessionNumbers().");
            }
            p.put(str, 1L);
        }
        if (s) {
        }
        s = false;
        return l;
    }

    static synchronized void a(Context context) {
        synchronized (StatServiceImpl.class) {
            if (context != null) {
                Context applicationContext = context.getApplicationContext();
                if (d == null && b(applicationContext)) {
                    t = applicationContext;
                    HandlerThread handlerThread = new HandlerThread("StatService");
                    handlerThread.start();
                    d = new Handler(handlerThread.getLooper());
                    k = j.a(0);
                    h = System.currentTimeMillis() + StatConfig.i;
                    d.post(new n(applicationContext));
                }
            }
        }
    }

    static void a(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (c(context) != null) {
            if (StatConfig.isDebugEnable()) {
                q.d("start new session, specifyReport:" + statSpecifyReportedInfo);
            }
            if (statSpecifyReportedInfo == null || l == 0) {
                l = j.a();
            }
            StatConfig.a(0);
            StatConfig.b();
            new af(new h(context, l, b(), statSpecifyReportedInfo)).a();
        }
    }

    static boolean a() {
        if (a < 2) {
            return false;
        }
        b = System.currentTimeMillis();
        return true;
    }

    static boolean a(String str) {
        return str == null || str.length() == 0;
    }

    public static void addActionListener(g gVar) {
        y.add(gVar);
    }

    static JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (StatConfig.b.d != 0) {
                jSONObject2.put(VKApiConst.VERSION, StatConfig.b.d);
            }
            jSONObject.put(Integer.toString(StatConfig.b.a), jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            if (StatConfig.a.d != 0) {
                jSONObject3.put(VKApiConst.VERSION, StatConfig.a.d);
            }
            jSONObject.put(Integer.toString(StatConfig.a.a), jSONObject3);
        } catch (JSONException e2) {
            q.e((Throwable) e2);
        }
        return jSONObject;
    }

    /* access modifiers changed from: private */
    public static void b(Context context, f fVar, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        try {
            new af(new a(context, a(context, false, statSpecifyReportedInfo), fVar, statSpecifyReportedInfo)).a();
        } catch (Throwable th) {
            q.e(th);
        }
    }

    static boolean b(Context context) {
        boolean z2 = false;
        long a2 = o.a(context, StatConfig.c, 0);
        long b2 = j.b(StatConstants.VERSION);
        boolean z3 = true;
        if (b2 <= a2) {
            q.error((Object) "MTA is disable for current version:" + b2 + ",wakeup version:" + a2);
            z3 = false;
        }
        long a3 = o.a(context, StatConfig.d, 0);
        if (a3 > System.currentTimeMillis()) {
            q.error((Object) "MTA is disable for current time:" + System.currentTimeMillis() + ",wakeup time:" + a3);
        } else {
            z2 = z3;
        }
        StatConfig.setEnableStatService(z2);
        return z2;
    }

    static Handler c(Context context) {
        if (d == null) {
            synchronized (StatServiceImpl.class) {
                if (d == null) {
                    try {
                        a(context);
                    } catch (Throwable th) {
                        q.error(th);
                        StatConfig.setEnableStatService(false);
                    }
                }
            }
        }
        return d;
    }

    static void c() {
        a = 0;
        b = 0;
    }

    public static void commitEvents(Context context, int i2) {
        if (StatConfig.isEnableStatService()) {
            if (StatConfig.isDebugEnable()) {
                q.i("commitEvents, maxNumber=" + i2);
            }
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.commitEvents() can not be null!");
            } else if (i2 < -1 || i2 == 0) {
                q.error((Object) "The maxNumber of StatService.commitEvents() should be -1 or bigger than 0.");
            } else if (a.a(context2).f() && c(context2) != null) {
                d.post(new t(context2, i2));
            }
        }
    }

    static void d() {
        a++;
        b = System.currentTimeMillis();
        flushDataToDB(t);
    }

    static void d(Context context) {
        c = System.currentTimeMillis() + ((long) (60000 * StatConfig.getSendPeriodMinutes()));
        o.b(context, "last_period_ts", c);
        commitEvents(context, -1);
    }

    static void e(Context context) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.sendNetworkDetector() can not be null!");
                return;
            }
            try {
                k.b(context2).a((d) new f(context2), (j) new u());
            } catch (Throwable th) {
                q.e(th);
            }
        }
    }

    public static void flushDataToDB(Context context) {
        if (StatConfig.isEnableStatService() && StatConfig.m > 0) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.testSpeed() can not be null!");
            } else {
                aj.a(context2).c();
            }
        }
    }

    public static Properties getCommonKeyValueForKVEvent(String str) {
        return f.get(str);
    }

    public static Context getContext(Context context) {
        return context != null ? context : t;
    }

    public static boolean isBackground() {
        return v;
    }

    public static boolean isForeground() {
        return v;
    }

    public static void onLowMemory(Context context) {
        if (StatConfig.isEnableStatService() && c(getContext(context)) != null) {
            d.post(new ad(context));
        }
    }

    public static void onStop(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (c(context2) != null) {
                d.post(new ac(context2));
            }
        }
    }

    public static void removeActionListener(g gVar) {
        y.remove(gVar);
    }

    public static void reportQQ(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "context is null in reportQQ()");
                return;
            }
            StatConfig.f = str;
            if (c(context2) != null) {
                d.post(new aa(str, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void setCommonKeyValueForKVEvent(String str, Properties properties) {
        if (!j.c(str)) {
            q.e((Object) "event_id or commonProp for setCommonKeyValueForKVEvent is invalid.");
        } else if (properties == null || properties.size() <= 0) {
            f.remove(str);
        } else {
            f.put(str, (Properties) properties.clone());
        }
    }

    public static void setContext(Context context) {
        if (context != null) {
            t = context.getApplicationContext();
        }
    }

    public static void setEnvAttributes(Context context, Map<String, String> map) {
        if (map == null || map.size() > 512) {
            q.error((Object) "The map in setEnvAttributes can't be null or its size can't exceed 512.");
            return;
        }
        try {
            b.a(context, map);
        } catch (JSONException e2) {
            q.e((Throwable) e2);
        }
    }

    public static void startNewSession(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.startNewSession() can not be null!");
            } else if (c(context2) != null) {
                d.post(new z(context2, statSpecifyReportedInfo));
            }
        }
    }

    public static boolean startStatService(Context context, String str, String str2, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        try {
            if (!StatConfig.isEnableStatService()) {
                q.error((Object) "MTA StatService is disable.");
                return false;
            }
            if (StatConfig.isDebugEnable()) {
                q.d("MTA SDK version, current: " + StatConstants.VERSION + " ,required: " + str2);
            }
            if (context == null || str2 == null) {
                q.error((Object) "Context or mtaSdkVersion in StatService.startStatService() is null, please check it!");
                StatConfig.setEnableStatService(false);
                return false;
            } else if (j.b(StatConstants.VERSION) < j.b(str2)) {
                q.error((Object) ("MTA SDK version conflicted, current: " + StatConstants.VERSION + ",required: " + str2) + ". please delete the current SDK and download the latest one. official website: http://mta.qq.com/ or http://mta.oa.com/");
                StatConfig.setEnableStatService(false);
                return false;
            } else {
                if (str != null) {
                    StatConfig.setAppKey(context, str);
                }
                if (c(context) != null) {
                    d.post(new ab(context, statSpecifyReportedInfo));
                }
                return true;
            }
        } catch (Throwable th) {
            q.e(th);
            return false;
        }
    }

    public static void stopSession() {
        i = 0;
    }

    public static void trackBeginPage(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null || str == null || str.length() == 0) {
                q.error((Object) "The Context or pageName of StatService.trackBeginPage() can not be null or empty!");
                return;
            }
            String str2 = new String(str);
            if (c(context2) != null) {
                d.post(new x(str2, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void trackCustomBeginEvent(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo, String... strArr) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
                return;
            }
            c cVar = new c(str, strArr, (Properties) null);
            if (c(context2) != null) {
                d.post(new p(str, cVar));
            }
        }
    }

    public static void trackCustomBeginKVEvent(Context context, String str, Properties properties, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
                return;
            }
            c cVar = new c(str, (String[]) null, properties);
            if (c(context2) != null) {
                d.post(new r(str, cVar));
            }
        }
    }

    public static void trackCustomEndEvent(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo, String... strArr) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
                return;
            }
            c cVar = new c(str, strArr, (Properties) null);
            if (c(context2) != null) {
                d.post(new q(str, cVar, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void trackCustomEndKVEvent(Context context, String str, Properties properties, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
                return;
            }
            c cVar = new c(str, (String[]) null, properties);
            if (c(context2) != null) {
                d.post(new s(str, cVar, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void trackCustomEvent(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo, String... strArr) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
            } else if (a(str)) {
                q.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
            } else {
                c cVar = new c(str, strArr, (Properties) null);
                if (c(context2) != null) {
                    d.post(new ae(context2, statSpecifyReportedInfo, cVar));
                }
            }
        }
    }

    public static void trackCustomKVEvent(Context context, String str, Properties properties, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
            } else if (a(str)) {
                q.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
            } else {
                c cVar = new c(str, (String[]) null, properties);
                if (c(context2) != null) {
                    d.post(new o(context2, statSpecifyReportedInfo, cVar));
                }
            }
        }
    }

    public static void trackCustomKVTimeIntervalEvent(Context context, String str, Properties properties, int i2, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                q.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
            } else if (a(str)) {
                q.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
            } else {
                c cVar = new c(str, (String[]) null, properties);
                if (c(context2) != null) {
                    d.post(new v(context2, statSpecifyReportedInfo, cVar, i2));
                }
            }
        }
    }

    public static void trackEndPage(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null || str == null || str.length() == 0) {
                q.error((Object) "The Context or pageName of StatService.trackEndPage() can not be null or empty!");
                return;
            }
            String str2 = new String(str);
            if (c(context2) != null) {
                d.post(new y(context2, str2, statSpecifyReportedInfo));
            }
        }
    }
}
