package com.appsflyer;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

final class h implements Runnable {

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private static String f97 = new StringBuilder("https://validate.%s/api/v").append(AppsFlyerLib.f242).append("/androidevent?buildnumber=4.8.18&app_id=").toString();

    /* renamed from: ʻ  reason: contains not printable characters */
    private String f98;
    /* access modifiers changed from: private */

    /* renamed from: ʼ  reason: contains not printable characters */
    public Map<String, String> f99;

    /* renamed from: ʽ  reason: contains not printable characters */
    private ScheduledExecutorService f100;

    /* renamed from: ˊ  reason: contains not printable characters */
    private String f101;

    /* renamed from: ˋ  reason: contains not printable characters */
    private String f102;
    /* access modifiers changed from: private */

    /* renamed from: ˎ  reason: contains not printable characters */
    public WeakReference<Context> f103 = null;

    /* renamed from: ˏ  reason: contains not printable characters */
    private String f104;

    /* renamed from: ͺ  reason: contains not printable characters */
    private final Intent f105;

    /* renamed from: ॱ  reason: contains not printable characters */
    private String f106;

    /* renamed from: ᐝ  reason: contains not printable characters */
    private String f107;

    h(Context context, String str, String str2, String str3, String str4, String str5, String str6, Map<String, String> map, ScheduledExecutorService scheduledExecutorService, Intent intent) {
        this.f103 = new WeakReference<>(context);
        this.f104 = str;
        this.f101 = str2;
        this.f106 = str4;
        this.f107 = str5;
        this.f98 = str6;
        this.f99 = map;
        this.f102 = str3;
        this.f100 = scheduledExecutorService;
        this.f105 = intent;
    }

    public final void run() {
        boolean z;
        if (this.f104 != null && this.f104.length() != 0 && !AppsFlyerLib.getInstance().isTrackingStopped()) {
            HttpURLConnection httpURLConnection = null;
            try {
                Context context = this.f103.get();
                if (context != null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("public-key", this.f101);
                    hashMap.put("sig-data", this.f106);
                    hashMap.put("signature", this.f102);
                    final HashMap hashMap2 = new HashMap();
                    hashMap2.putAll(hashMap);
                    Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                        public final void run() {
                            h.m78(h.this, hashMap2, h.this.f99, h.this.f103);
                        }
                    }, 5, TimeUnit.MILLISECONDS);
                    hashMap.put("dev_key", this.f104);
                    hashMap.put("app_id", context.getPackageName());
                    hashMap.put(GGLiveConstants.PARAM.UID, AppsFlyerLib.getInstance().getAppsFlyerUID(context));
                    hashMap.put(ServerParameters.ADVERTISING_ID_PARAM, AppsFlyerProperties.getInstance().getString(ServerParameters.ADVERTISING_ID_PARAM));
                    String jSONObject = new JSONObject(hashMap).toString();
                    String url = ServerConfigHandler.getUrl("https://sdk-services.%s/validate-android-signature");
                    r.m125().m138(url, jSONObject);
                    httpURLConnection = m80(jSONObject, url);
                    int i = -1;
                    if (httpURLConnection != null) {
                        i = httpURLConnection.getResponseCode();
                    }
                    AppsFlyerLib.getInstance();
                    String r4 = AppsFlyerLib.m183(httpURLConnection);
                    r.m125().m135(url, i, r4);
                    JSONObject jSONObject2 = new JSONObject(r4);
                    jSONObject2.put("code", i);
                    if (i == 200) {
                        AFLogger.afInfoLog(new StringBuilder("Validate response 200 ok: ").append(jSONObject2.toString()).toString());
                        if (jSONObject2.optBoolean(GGLiveConstants.PARAM.RESULT)) {
                            z = jSONObject2.getBoolean(GGLiveConstants.PARAM.RESULT);
                        } else {
                            z = false;
                        }
                        m77(z, this.f106, this.f107, this.f98, jSONObject2.toString());
                    } else {
                        AFLogger.afInfoLog("Failed Validate request");
                        m77(false, this.f106, this.f107, this.f98, jSONObject2.toString());
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            } catch (Throwable th) {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th;
            }
        }
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private static HttpURLConnection m80(String str, String str2) throws IOException {
        try {
            m mVar = new m((Context) null, AppsFlyerLib.getInstance().isTrackingStopped());
            mVar.f146 = str;
            mVar.m97();
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                AFLogger.afDebugLog(new StringBuilder("Main thread detected. Calling ").append(str2).append(" in a new thread.").toString());
                mVar.execute(new String[]{str2});
            } else {
                AFLogger.afDebugLog(new StringBuilder("Calling ").append(str2).append(" (on current thread: ").append(Thread.currentThread().toString()).append(" )").toString());
                mVar.onPreExecute();
                mVar.onPostExecute(mVar.doInBackground(str2));
            }
            return mVar.m100();
        } catch (Throwable th) {
            AFLogger.afErrorLog("Could not send callStats request", th);
            return null;
        }
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    private static void m77(boolean z, String str, String str2, String str3, String str4) {
        if (AppsFlyerLib.f241 != null) {
            AFLogger.afDebugLog(new StringBuilder("Validate callback parameters: ").append(str).append(" ").append(str2).append(" ").append(str3).toString());
            if (z) {
                AFLogger.afDebugLog("Validate in app purchase success: ".concat(String.valueOf(str4)));
                AppsFlyerLib.f241.onValidateInApp();
                return;
            }
            AFLogger.afDebugLog("Validate in app purchase failed: ".concat(String.valueOf(str4)));
            AppsFlyerInAppPurchaseValidatorListener appsFlyerInAppPurchaseValidatorListener = AppsFlyerLib.f241;
            if (str4 == null) {
                str4 = "Failed validating";
            }
            appsFlyerInAppPurchaseValidatorListener.onValidateInAppFailure(str4);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x0151  */
    /* renamed from: ˏ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void m78(com.appsflyer.h r13, java.util.Map r14, java.util.Map r15, java.lang.ref.WeakReference r16) {
        /*
            r11 = 0
            r9 = 0
            java.lang.Object r1 = r16.get()
            if (r1 == 0) goto L_0x0111
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = f97
            java.lang.String r2 = com.appsflyer.ServerConfigHandler.getUrl(r2)
            java.lang.StringBuilder r2 = r1.append(r2)
            java.lang.Object r1 = r16.get()
            android.content.Context r1 = (android.content.Context) r1
            java.lang.String r1 = r1.getPackageName()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r12 = r1.toString()
            java.lang.Object r1 = r16.get()
            android.content.Context r1 = (android.content.Context) r1
            java.lang.String r2 = "appsflyer-data"
            android.content.SharedPreferences r8 = r1.getSharedPreferences(r2, r9)
            java.lang.String r1 = "referrer"
            java.lang.String r6 = r8.getString(r1, r11)
            if (r6 != 0) goto L_0x003f
            java.lang.String r6 = ""
        L_0x003f:
            com.appsflyer.AppsFlyerLib r1 = com.appsflyer.AppsFlyerLib.getInstance()
            java.lang.Object r2 = r16.get()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r3 = r13.f104
            java.lang.String r4 = "af_purchase"
            java.lang.String r5 = ""
            r7 = 1
            android.content.Intent r10 = r13.f105
            java.util.Map r1 = r1.m224((android.content.Context) r2, (java.lang.String) r3, (java.lang.String) r4, (java.lang.String) r5, (java.lang.String) r6, (boolean) r7, (android.content.SharedPreferences) r8, (boolean) r9, (android.content.Intent) r10)
            java.lang.String r2 = "price"
            java.lang.String r3 = r13.f107
            r1.put(r2, r3)
            java.lang.String r2 = "currency"
            java.lang.String r3 = r13.f98
            r1.put(r2, r3)
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>(r1)
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            java.util.Set r1 = r14.entrySet()     // Catch:{ JSONException -> 0x0092 }
            java.util.Iterator r5 = r1.iterator()     // Catch:{ JSONException -> 0x0092 }
        L_0x0076:
            boolean r1 = r5.hasNext()     // Catch:{ JSONException -> 0x0092 }
            if (r1 == 0) goto L_0x0112
            java.lang.Object r1 = r5.next()     // Catch:{ JSONException -> 0x0092 }
            r0 = r1
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ JSONException -> 0x0092 }
            r2 = r0
            java.lang.Object r1 = r2.getKey()     // Catch:{ JSONException -> 0x0092 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ JSONException -> 0x0092 }
            java.lang.Object r2 = r2.getValue()     // Catch:{ JSONException -> 0x0092 }
            r4.put(r1, r2)     // Catch:{ JSONException -> 0x0092 }
            goto L_0x0076
        L_0x0092:
            r1 = move-exception
            java.lang.String r2 = "Failed to build 'receipt_data'"
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
        L_0x0098:
            if (r15 == 0) goto L_0x00c9
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            java.util.Set r1 = r15.entrySet()     // Catch:{ JSONException -> 0x00c3 }
            java.util.Iterator r5 = r1.iterator()     // Catch:{ JSONException -> 0x00c3 }
        L_0x00a7:
            boolean r1 = r5.hasNext()     // Catch:{ JSONException -> 0x00c3 }
            if (r1 == 0) goto L_0x0118
            java.lang.Object r1 = r5.next()     // Catch:{ JSONException -> 0x00c3 }
            r0 = r1
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ JSONException -> 0x00c3 }
            r2 = r0
            java.lang.Object r1 = r2.getKey()     // Catch:{ JSONException -> 0x00c3 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ JSONException -> 0x00c3 }
            java.lang.Object r2 = r2.getValue()     // Catch:{ JSONException -> 0x00c3 }
            r4.put(r1, r2)     // Catch:{ JSONException -> 0x00c3 }
            goto L_0x00a7
        L_0x00c3:
            r1 = move-exception
            java.lang.String r2 = "Failed to build 'extra_prms'"
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
        L_0x00c9:
            java.lang.String r1 = r3.toString()
            com.appsflyer.r r2 = com.appsflyer.r.m125()
            r2.m138(r12, r1)
            java.net.HttpURLConnection r2 = m80(r1, r12)     // Catch:{ Throwable -> 0x0157, all -> 0x014d }
            r1 = -1
            if (r2 == 0) goto L_0x00df
            int r1 = r2.getResponseCode()     // Catch:{ Throwable -> 0x013f }
        L_0x00df:
            com.appsflyer.AppsFlyerLib.getInstance()     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = com.appsflyer.AppsFlyerLib.m183((java.net.HttpURLConnection) r2)     // Catch:{ Throwable -> 0x013f }
            com.appsflyer.r r4 = com.appsflyer.r.m125()     // Catch:{ Throwable -> 0x013f }
            r4.m135(r12, r1, r3)     // Catch:{ Throwable -> 0x013f }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Throwable -> 0x013f }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x013f }
            r3 = 200(0xc8, float:2.8E-43)
            if (r1 != r3) goto L_0x011e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = "Validate-WH response - 200: "
            r1.<init>(r3)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = r4.toString()     // Catch:{ Throwable -> 0x013f }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x013f }
            com.appsflyer.AFLogger.afInfoLog(r1)     // Catch:{ Throwable -> 0x013f }
        L_0x010c:
            if (r2 == 0) goto L_0x0111
            r2.disconnect()
        L_0x0111:
            return
        L_0x0112:
            java.lang.String r1 = "receipt_data"
            r3.put(r1, r4)     // Catch:{ JSONException -> 0x0092 }
            goto L_0x0098
        L_0x0118:
            java.lang.String r1 = "extra_prms"
            r3.put(r1, r4)     // Catch:{ JSONException -> 0x00c3 }
            goto L_0x00c9
        L_0x011e:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013f }
            java.lang.String r5 = "Validate-WH response failed - "
            r3.<init>(r5)     // Catch:{ Throwable -> 0x013f }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = ": "
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = r4.toString()     // Catch:{ Throwable -> 0x013f }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x013f }
            com.appsflyer.AFLogger.afWarnLog(r1)     // Catch:{ Throwable -> 0x013f }
            goto L_0x010c
        L_0x013f:
            r1 = move-exception
        L_0x0140:
            java.lang.String r3 = r1.getMessage()     // Catch:{ all -> 0x0155 }
            com.appsflyer.AFLogger.afErrorLog(r3, r1)     // Catch:{ all -> 0x0155 }
            if (r2 == 0) goto L_0x0111
            r2.disconnect()
            goto L_0x0111
        L_0x014d:
            r1 = move-exception
            r2 = r11
        L_0x014f:
            if (r2 == 0) goto L_0x0154
            r2.disconnect()
        L_0x0154:
            throw r1
        L_0x0155:
            r1 = move-exception
            goto L_0x014f
        L_0x0157:
            r1 = move-exception
            r2 = r11
            goto L_0x0140
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.h.m78(com.appsflyer.h, java.util.Map, java.util.Map, java.lang.ref.WeakReference):void");
    }
}
