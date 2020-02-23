package com.tencent.msdk.stat;

import android.content.Context;
import android.os.Build;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.ShareConstants;
import com.garena.android.gpns.utility.CONSTANT;
import com.tencent.imsdk.framework.consts.InnerErrorCode;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.msdk.a.a.c;
import com.tencent.msdk.stat.common.StatConstants;
import com.tencent.msdk.stat.common.StatLogger;
import com.tencent.msdk.stat.common.j;
import com.tencent.msdk.stat.common.o;
import com.tencent.msdk.stat.common.p;
import com.vk.sdk.api.VKApiConst;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class StatConfig {
    private static String A = null;
    private static String B;
    private static String C;
    private static String D = "mta_channel";
    private static int E = ShareConstants.ERROR_CODE.GG_RESULT_UNKNOWN_ERROR;
    private static String F;
    private static int G = 1024;
    private static long H = 0;
    private static long I = 300000;
    private static volatile String J = StatConstants.MTA_REPORT_FULL_URL;
    private static int K = 0;
    private static volatile int L = 0;
    private static int M = 20;
    private static int N = 0;
    private static boolean O = false;
    private static int P = 4096;
    private static boolean Q = false;
    private static String R = null;
    private static i S = null;
    private static JSONObject T = null;
    private static String U = null;
    private static JSONObject V = null;
    private static HashSet<String> W = new HashSet<>();
    static h a = new h(2);
    static h b = new h(1);
    static String c = "__HIBERNATE__";
    static String d = "__HIBERNATE__TIME";
    static String e = "__MTA_KILL__";
    static String f = "";
    static boolean g = false;
    static int h = 1000;
    static long i = 10000;
    public static boolean isAutoExceptionCaught = true;
    static boolean j = true;
    static volatile String k = StatConstants.MTA_SERVER;
    static boolean l = true;
    static int m = 0;
    static long n = 10000;
    static int o = 512;
    private static StatLogger p = j.b();
    private static StatReportStrategy q = StatReportStrategy.APP_LAUNCH;
    private static boolean r = false;
    private static boolean s = true;
    private static int t = 30000;
    private static int u = InnerErrorCode.SDK_ERROR_BASIC_XG;
    private static int v = 30;
    private static int w = 10;
    private static int x = 100;
    private static int y = 30;
    private static int z = 1;

    static int a() {
        return v;
    }

    static String a(Context context) {
        return p.a(o.a(context, "_mta_ky_tag_", (String) null));
    }

    static synchronized void a(int i2) {
        synchronized (StatConfig.class) {
            L = i2;
        }
    }

    static void a(long j2) {
        o.b(k.a(), c, j2);
        setEnableStatService(false);
        p.warn("MTA is disable for current SDK version");
    }

    static void a(Context context, h hVar) {
        if (hVar.a == b.a) {
            b = hVar;
            a(b.b);
            if (!b.b.isNull("iplist")) {
                a.a(context).a(b.b.getString("iplist"));
            }
            updateDontReportEventIdsSet(b.b.optString("__DONT_REPORT_EI_LIST__", (String) null));
        } else if (hVar.a == a.a) {
            a = hVar;
        }
    }

    static void a(Context context, h hVar, JSONObject jSONObject) {
        boolean z2;
        boolean z3 = false;
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                if (next.equalsIgnoreCase(VKApiConst.VERSION)) {
                    int i2 = jSONObject.getInt(next);
                    z2 = hVar.d != i2 ? true : z3;
                    hVar.d = i2;
                } else if (next.equalsIgnoreCase("c")) {
                    String string = jSONObject.getString("c");
                    if (string.length() > 0) {
                        hVar.b = new JSONObject(string);
                    }
                    z2 = z3;
                } else {
                    if (next.equalsIgnoreCase("m")) {
                        hVar.c = jSONObject.getString("m");
                    }
                    z2 = z3;
                }
                z3 = z2;
            }
            if (z3) {
                aj a2 = aj.a(k.a());
                if (a2 != null) {
                    a2.a(hVar);
                }
                if (hVar.a == b.a) {
                    a(hVar.b);
                    b(hVar.b);
                }
            }
            a(context, hVar);
        } catch (JSONException e2) {
            p.e((Throwable) e2);
        } catch (Throwable th) {
            p.e(th);
        }
    }

    static void a(Context context, String str) {
        if (str != null) {
            o.b(context, "_mta_ky_tag_", p.b(str));
        }
    }

    static void a(Context context, JSONObject jSONObject) {
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                if (next.equalsIgnoreCase(Integer.toString(b.a))) {
                    a(context, b, jSONObject.getJSONObject(next));
                } else if (next.equalsIgnoreCase(Integer.toString(a.a))) {
                    a(context, a, jSONObject.getJSONObject(next));
                } else if (next.equalsIgnoreCase("rs")) {
                    StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt(next));
                    if (statReportStrategy != null) {
                        q = statReportStrategy;
                        if (isDebugEnable()) {
                            p.d("Change to ReportStrategy:" + statReportStrategy.name());
                        }
                    }
                } else {
                    return;
                }
            }
        } catch (JSONException e2) {
            p.e((Throwable) e2);
        }
    }

    static void a(JSONObject jSONObject) {
        try {
            StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt("rs"));
            if (statReportStrategy != null) {
                setStatSendStrategy(statReportStrategy);
            }
        } catch (JSONException e2) {
            if (isDebugEnable()) {
                p.i("rs not found.");
            }
        }
    }

    static boolean a(int i2, int i3, int i4) {
        return i2 >= i3 && i2 <= i4;
    }

    private static boolean a(String str) {
        if (str == null) {
            return false;
        }
        if (B == null) {
            B = str;
            return true;
        } else if (B.contains(str)) {
            return false;
        } else {
            B += "|" + str;
            return true;
        }
    }

    static boolean a(JSONObject jSONObject, String str, String str2) {
        if (!jSONObject.isNull(str)) {
            String optString = jSONObject.optString(str);
            return j.c(str2) && j.c(optString) && str2.equalsIgnoreCase(optString);
        }
    }

    static void b() {
        N++;
    }

    static void b(int i2) {
        if (i2 >= 0) {
            N = i2;
        }
    }

    static void b(Context context, JSONObject jSONObject) {
        JSONObject jSONObject2;
        boolean z2;
        try {
            String optString = jSONObject.optString(e);
            if (j.c(optString) && (jSONObject2 = new JSONObject(optString)) != null && jSONObject2.length() != 0) {
                if (!jSONObject2.isNull("sm")) {
                    Object obj = jSONObject2.get("sm");
                    int intValue = obj instanceof Integer ? ((Integer) obj).intValue() : obj instanceof String ? Integer.valueOf((String) obj).intValue() : 0;
                    if (intValue > 0) {
                        if (isDebugEnable()) {
                            p.i("match sleepTime:" + intValue + " minutes");
                        }
                        o.b(context, d, System.currentTimeMillis() + ((long) (intValue * 60 * 1000)));
                        setEnableStatService(false);
                        p.warn("MTA is disable for current SDK version");
                    }
                }
                if (a(jSONObject2, "sv", StatConstants.VERSION)) {
                    p.i("match sdk version:2.1.9");
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (a(jSONObject2, "md", Build.MODEL)) {
                    p.i("match MODEL:" + Build.MODEL);
                    z2 = true;
                }
                if (a(jSONObject2, "av", j.i(context))) {
                    p.i("match app version:" + j.i(context));
                    z2 = true;
                }
                if (a(jSONObject2, "mf", Build.MANUFACTURER + "")) {
                    p.i("match MANUFACTURER:" + Build.MANUFACTURER + "");
                    z2 = true;
                }
                if (a(jSONObject2, "osv", Build.VERSION.SDK_INT + "")) {
                    p.i("match android SDK version:" + Build.VERSION.SDK_INT);
                    z2 = true;
                }
                if (a(jSONObject2, "ov", Build.VERSION.SDK_INT + "")) {
                    p.i("match android SDK version:" + Build.VERSION.SDK_INT);
                    z2 = true;
                }
                if (a(jSONObject2, "ui", aj.a(context).b(context).b())) {
                    p.i("match imei:" + aj.a(context).b(context).b());
                    z2 = true;
                }
                if (a(jSONObject2, HttpRequestParams.PUSH_XG_ID, getLocalMidOnly(context))) {
                    p.i("match mid:" + getLocalMidOnly(context));
                    z2 = true;
                }
                if (z2) {
                    a(j.b(StatConstants.VERSION));
                }
            }
        } catch (Exception e2) {
            p.e((Throwable) e2);
        }
    }

    static void b(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.length() != 0) {
            try {
                b(k.a(), jSONObject);
                String string = jSONObject.getString(c);
                if (isDebugEnable()) {
                    p.d("hibernateVer:" + string + ", current version:" + StatConstants.VERSION);
                }
                long b2 = j.b(string);
                if (j.b(StatConstants.VERSION) <= b2) {
                    a(b2);
                }
            } catch (JSONException e2) {
                p.d("__HIBERNATE__ not found.");
            }
        }
    }

    static int c() {
        return N;
    }

    public static synchronized String getAppKey(Context context) {
        String str;
        synchronized (StatConfig.class) {
            if (B != null) {
                str = B;
            } else {
                if (context != null) {
                    if (B == null) {
                        B = j.f(context);
                    }
                }
                if (B == null || B.trim().length() == 0) {
                    p.e((Object) "AppKey can not be null or empty, please read Developer's Guide first!");
                }
                str = B;
            }
        }
        return str;
    }

    public static String getAppVersion() {
        return U;
    }

    public static JSONObject getCommonAttr(Context context) {
        if (T == null) {
            try {
                T = new JSONObject(o.a(context, "mta.common.attr.tag", new JSONObject().toString()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return T;
    }

    public static int getCurSessionStatReportCount() {
        return L;
    }

    public static JSONObject getCustomGlobalReportContent() {
        return V;
    }

    public static String getCustomProperty(String str) {
        try {
            return a.b.getString(str);
        } catch (Throwable th) {
            p.e(th);
            return null;
        }
    }

    public static String getCustomProperty(String str, String str2) {
        try {
            String string = a.b.getString(str);
            return string != null ? string : str2;
        } catch (Throwable th) {
            p.e(th);
            return str2;
        }
    }

    public static String getCustomUserId(Context context) {
        if (context == null) {
            p.error((Object) "Context for getCustomUid is null.");
            return null;
        }
        if (R == null) {
            try {
                R = o.a(context, "MTA_CUSTOM_UID", "");
            } catch (ClassCastException e2) {
                p.e((Throwable) e2);
            }
        }
        return R;
    }

    public static long getFlushDBSpaceMS() {
        return n;
    }

    public static synchronized String getInstallChannel(Context context) {
        String str;
        synchronized (StatConfig.class) {
            if (C != null) {
                str = C;
            } else {
                C = o.a(context, D, "");
                if (C == null || C.trim().length() == 0) {
                    C = j.g(context);
                }
                if (C == null || C.trim().length() == 0) {
                    p.w("installChannel can not be null or empty, please read Developer's Guide first!");
                }
                str = C;
            }
        }
        return str;
    }

    public static String getLocalMidOnly(Context context) {
        return c.a(context).a();
    }

    public static String getMTAPreferencesFileName() {
        return F;
    }

    public static int getMaxBatchReportCount() {
        return y;
    }

    public static int getMaxDaySessionNumbers() {
        return M;
    }

    public static int getMaxImportantDataSendRetryCount() {
        return x;
    }

    public static int getMaxParallelTimmingEvents() {
        return G;
    }

    public static int getMaxReportEventLength() {
        return P;
    }

    public static int getMaxSendRetryCount() {
        return w;
    }

    public static int getMaxSessionStatReportCount() {
        return K;
    }

    public static int getMaxStoreEventCount() {
        return u;
    }

    public static String getMid(Context context) {
        return c.a(context).a();
    }

    public static long getMsPeriodForMethodsCalledLimitClear() {
        return i;
    }

    public static int getNumEventsCachedInMemory() {
        return m;
    }

    public static int getNumEventsCommitPerSec() {
        return z;
    }

    public static int getNumOfMethodsCalledLimit() {
        return h;
    }

    public static String getQQ() {
        return f;
    }

    public static String getQQ(Context context) {
        return o.a(context, "mta.acc.qq", f);
    }

    public static int getReportCompressedSize() {
        return o;
    }

    public static int getSendPeriodMinutes() {
        return E;
    }

    public static int getSessionTimoutMillis() {
        return t;
    }

    public static String getStatReportHost() {
        return k;
    }

    public static String getStatReportUrl() {
        return J;
    }

    public static StatReportStrategy getStatSendStrategy() {
        return q;
    }

    public static boolean isAutoExceptionCaught() {
        return isAutoExceptionCaught;
    }

    public static boolean isDebugEnable() {
        return r;
    }

    public static boolean isEnableConcurrentProcess() {
        return Q;
    }

    public static boolean isEnableSmartReporting() {
        return j;
    }

    public static boolean isEnableStatService() {
        return s;
    }

    public static boolean isEventIdInDontReportEventIdsSet(String str) {
        if (W == null || W.size() == 0 || !j.c(str)) {
            return false;
        }
        return W.contains(str.toLowerCase());
    }

    public static boolean isReportEventsByOrder() {
        return l;
    }

    public static void setAppKey(Context context, String str) {
        if (context == null) {
            p.error((Object) "ctx in StatConfig.setAppKey() is null");
        } else if (str == null || str.length() > 256) {
            p.error((Object) "appkey in StatConfig.setAppKey() is null or exceed 256 bytes");
        } else {
            if (B == null) {
                B = a(context);
            }
            if (a(str) || a(j.f(context))) {
                a(context, B);
            }
        }
    }

    public static void setAppKey(String str) {
        if (str == null) {
            p.error((Object) "appkey in StatConfig.setAppKey() is null");
        } else if (str.length() > 256) {
            p.error((Object) "The length of appkey cann't exceed 256 bytes.");
        } else {
            B = str;
        }
    }

    public static void setAppVersion(String str) {
        U = str;
    }

    public static void setAutoExceptionCaught(boolean z2) {
        isAutoExceptionCaught = z2;
    }

    public static void setCommonAttr(Context context, JSONObject jSONObject) {
        T = jSONObject;
        o.b(context, "mta.common.attr.tag", jSONObject != null ? jSONObject.toString() : new JSONObject().toString());
    }

    public static void setCustomGlobalReportContent(JSONObject jSONObject) {
        V = jSONObject;
    }

    public static void setCustomUserId(Context context, String str) {
        if (context == null) {
            p.error((Object) "Context for setCustomUid is null.");
            return;
        }
        o.b(context, "MTA_CUSTOM_UID", str);
        R = str;
    }

    public static void setDebugEnable(boolean z2) {
        r = z2;
        j.b().setDebugEnable(z2);
    }

    public static void setEnableConcurrentProcess(boolean z2) {
        Q = z2;
    }

    public static void setEnableSmartReporting(boolean z2) {
        j = z2;
    }

    public static void setEnableStatService(boolean z2) {
        s = z2;
        if (!z2) {
            p.warn("!!!!!!MTA StatService has been disabled!!!!!!");
        }
    }

    public static void setFlushDBSpaceMS(long j2) {
        if (j2 > 0) {
            n = j2;
        }
    }

    public static void setInstallChannel(Context context, String str) {
        if (str.length() > 128) {
            p.error((Object) "the length of installChannel can not exceed the range of 128 bytes.");
            return;
        }
        C = str;
        o.b(context, D, str);
    }

    public static void setInstallChannel(String str) {
        if (str.length() > 128) {
            p.error((Object) "the length of installChannel can not exceed the range of 128 bytes.");
        } else {
            C = str;
        }
    }

    public static void setMTAPreferencesFileName(String str) {
        F = str;
    }

    public static void setMaxBatchReportCount(int i2) {
        if (!a(i2, 2, 1000)) {
            p.error((Object) "setMaxBatchReportCount can not exceed the range of [2,1000].");
        } else {
            y = i2;
        }
    }

    public static void setMaxDaySessionNumbers(int i2) {
        if (i2 <= 0) {
            p.e((Object) "maxDaySessionNumbers must be greater than 0.");
        } else {
            M = i2;
        }
    }

    public static void setMaxImportantDataSendRetryCount(int i2) {
        if (i2 > 100) {
            x = i2;
        }
    }

    public static void setMaxParallelTimmingEvents(int i2) {
        if (!a(i2, 1, 4096)) {
            p.error((Object) "setMaxParallelTimmingEvents can not exceed the range of [1, 4096].");
        } else {
            G = i2;
        }
    }

    public static void setMaxReportEventLength(int i2) {
        if (i2 <= 0) {
            p.error((Object) "maxReportEventLength on setMaxReportEventLength() must greater than 0.");
        } else {
            P = i2;
        }
    }

    public static void setMaxSendRetryCount(int i2) {
        if (!a(i2, 1, 1000)) {
            p.error((Object) "setMaxSendRetryCount can not exceed the range of [1,1000].");
        } else {
            w = i2;
        }
    }

    public static void setMaxSessionStatReportCount(int i2) {
        if (i2 < 0) {
            p.error((Object) "maxSessionStatReportCount cannot be less than 0.");
        } else {
            K = i2;
        }
    }

    public static void setMaxStoreEventCount(int i2) {
        if (!a(i2, 0, (int) SDKConstants.MAX_IMG_DATA_LENGTH_BYTES)) {
            p.error((Object) "setMaxStoreEventCount can not exceed the range of [0, 500000].");
        } else {
            u = i2;
        }
    }

    public static void setNumEventsCachedInMemory(int i2) {
        if (i2 >= 0) {
            m = i2;
        }
    }

    public static void setNumEventsCommitPerSec(int i2) {
        if (i2 > 0) {
            z = i2;
        }
    }

    public static void setNumOfMethodsCalledLimit(int i2, long j2) {
        h = i2;
        if (j2 >= 1000) {
            i = j2;
        }
    }

    public static void setQQ(Context context, String str) {
        o.b(context, "mta.acc.qq", str);
        f = str;
    }

    public static void setReportCompressedSize(int i2) {
        if (i2 > 0) {
            o = i2;
        }
    }

    public static void setReportEventsByOrder(boolean z2) {
        l = z2;
    }

    public static void setSendPeriodMinutes(int i2) {
        if (!a(i2, 1, 10080)) {
            p.error((Object) "setSendPeriodMinutes can not exceed the range of [1, 7*24*60] minutes.");
        } else {
            E = i2;
        }
    }

    public static void setSessionTimoutMillis(int i2) {
        if (!a(i2, 1000, (int) CONSTANT.TIME.HR_24)) {
            p.error((Object) "setSessionTimoutMillis can not exceed the range of [1000, 24 * 60 * 60 * 1000].");
        } else {
            t = i2;
        }
    }

    public static void setStatExCallBack(i iVar) {
        S = iVar;
    }

    public static void setStatReportUrl(String str) {
        if (str == null || str.length() == 0) {
            p.error((Object) "statReportUrl cannot be null or empty.");
            return;
        }
        J = str;
        try {
            k = new URI(J).getHost();
        } catch (Exception e2) {
            p.w(e2);
        }
        if (isDebugEnable()) {
            p.i("url:" + J + ", domain:" + k);
        }
    }

    public static void setStatSendStrategy(StatReportStrategy statReportStrategy) {
        q = statReportStrategy;
        if (statReportStrategy != StatReportStrategy.PERIOD) {
            StatServiceImpl.c = 0;
        }
        if (isDebugEnable()) {
            p.d("Change to statSendStrategy: " + statReportStrategy);
        }
    }

    public static void updateDontReportEventIdsSet(String str) {
        if (j.c(str)) {
            String[] split = str.toLowerCase().split(";");
            if (split.length > 0) {
                if (W == null) {
                    W = new HashSet<>(split.length);
                }
                W.addAll(Arrays.asList(split));
            }
        }
    }
}
