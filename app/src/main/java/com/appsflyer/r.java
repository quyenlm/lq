package com.appsflyer;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.share.internal.ShareConstants;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class r {

    /* renamed from: ˋ  reason: contains not printable characters */
    private static r f166;

    /* renamed from: ʻ  reason: contains not printable characters */
    private final String f167;

    /* renamed from: ʻॱ  reason: contains not printable characters */
    private final String f168;

    /* renamed from: ʼ  reason: contains not printable characters */
    private final String f169;

    /* renamed from: ʼॱ  reason: contains not printable characters */
    private final String f170;

    /* renamed from: ʽ  reason: contains not printable characters */
    private final String f171;

    /* renamed from: ʽॱ  reason: contains not printable characters */
    private final String f172;

    /* renamed from: ʾ  reason: contains not printable characters */
    private final String f173;

    /* renamed from: ʿ  reason: contains not printable characters */
    private final String f174;

    /* renamed from: ˈ  reason: contains not printable characters */
    private final String f175;

    /* renamed from: ˉ  reason: contains not printable characters */
    private int f176;

    /* renamed from: ˊ  reason: contains not printable characters */
    private final String f177;

    /* renamed from: ˊˊ  reason: contains not printable characters */
    private JSONArray f178;

    /* renamed from: ˊˋ  reason: contains not printable characters */
    private final String f179;

    /* renamed from: ˊॱ  reason: contains not printable characters */
    private final String f180;

    /* renamed from: ˊᐝ  reason: contains not printable characters */
    private JSONObject f181;

    /* renamed from: ˋˊ  reason: contains not printable characters */
    private final String f182;

    /* renamed from: ˋˋ  reason: contains not printable characters */
    private boolean f183;

    /* renamed from: ˋॱ  reason: contains not printable characters */
    private final String f184;

    /* renamed from: ˎ  reason: contains not printable characters */
    private final String f185;

    /* renamed from: ˎˎ  reason: contains not printable characters */
    private String f186;

    /* renamed from: ˏ  reason: contains not printable characters */
    private boolean f187;

    /* renamed from: ˏॱ  reason: contains not printable characters */
    private final String f188;

    /* renamed from: ͺ  reason: contains not printable characters */
    private final String f189;

    /* renamed from: ॱ  reason: contains not printable characters */
    private boolean f190;

    /* renamed from: ॱˊ  reason: contains not printable characters */
    private final String f191;

    /* renamed from: ॱˋ  reason: contains not printable characters */
    private final String f192;

    /* renamed from: ॱˎ  reason: contains not printable characters */
    private final String f193;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private final String f194;

    /* renamed from: ॱᐝ  reason: contains not printable characters */
    private final String f195;

    /* renamed from: ᐝ  reason: contains not printable characters */
    private final String f196;

    /* renamed from: ᐝॱ  reason: contains not printable characters */
    private final String f197;

    private r() {
        this.f187 = true;
        this.f190 = true;
        this.f185 = "brand";
        this.f177 = "model";
        this.f171 = "platform";
        this.f167 = "platform_version";
        this.f194 = ServerParameters.ADVERTISING_ID_PARAM;
        this.f196 = "imei";
        this.f169 = "android_id";
        this.f180 = "sdk_version";
        this.f184 = "devkey";
        this.f189 = "originalAppsFlyerId";
        this.f191 = GGLiveConstants.PARAM.UID;
        this.f188 = "app_id";
        this.f168 = "app_version";
        this.f195 = "channel";
        this.f197 = "preInstall";
        this.f193 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        this.f192 = "r_debugging_off";
        this.f172 = "r_debugging_on";
        this.f173 = "public_api_call";
        this.f170 = "exception";
        this.f175 = "server_request";
        this.f174 = "server_response";
        this.f182 = "yyyy-MM-dd HH:mm:ssZ";
        this.f179 = "MM-dd HH:mm:ss.SSS";
        this.f176 = 0;
        this.f186 = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        this.f178 = new JSONArray();
        this.f176 = 0;
        this.f183 = false;
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static r m125() {
        if (f166 == null) {
            f166 = new r();
        }
        return f166;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final synchronized void m137(String str) {
        this.f186 = str;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final synchronized void m131() {
        this.f183 = true;
        m128("r_debugging_on", new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())), new String[0]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final synchronized void m136() {
        m128("r_debugging_off", new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())), new String[0]);
        this.f183 = false;
        this.f187 = false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final synchronized void m132() {
        this.f181 = null;
        this.f178 = null;
        f166 = null;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* renamed from: ˋ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m124(java.lang.String r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
            r3 = this;
            monitor-enter(r3)
            org.json.JSONObject r0 = r3.f181     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "brand"
            r0.put(r1, r4)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            org.json.JSONObject r0 = r3.f181     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "model"
            r0.put(r1, r5)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            org.json.JSONObject r0 = r3.f181     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "platform"
            java.lang.String r2 = "Android"
            r0.put(r1, r2)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            org.json.JSONObject r0 = r3.f181     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "platform_version"
            r0.put(r1, r6)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            if (r7 == 0) goto L_0x002e
            int r0 = r7.length()     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            if (r0 <= 0) goto L_0x002e
            org.json.JSONObject r0 = r3.f181     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "advertiserId"
            r0.put(r1, r7)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
        L_0x002e:
            if (r8 == 0) goto L_0x003d
            int r0 = r8.length()     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            if (r0 <= 0) goto L_0x003d
            org.json.JSONObject r0 = r3.f181     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "imei"
            r0.put(r1, r8)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
        L_0x003d:
            if (r9 == 0) goto L_0x004c
            int r0 = r9.length()     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            if (r0 <= 0) goto L_0x004c
            org.json.JSONObject r0 = r3.f181     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "android_id"
            r0.put(r1, r9)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
        L_0x004c:
            monitor-exit(r3)
            return
        L_0x004e:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x0051:
            r0 = move-exception
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.r.m124(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* renamed from: ˊ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m122(java.lang.String r3, java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            r2 = this;
            monitor-enter(r2)
            org.json.JSONObject r0 = r2.f181     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            java.lang.String r1 = "sdk_version"
            r0.put(r1, r3)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r4 == 0) goto L_0x0017
            int r0 = r4.length()     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r0 <= 0) goto L_0x0017
            org.json.JSONObject r0 = r2.f181     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            java.lang.String r1 = "devkey"
            r0.put(r1, r4)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
        L_0x0017:
            if (r5 == 0) goto L_0x0026
            int r0 = r5.length()     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r0 <= 0) goto L_0x0026
            org.json.JSONObject r0 = r2.f181     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            java.lang.String r1 = "originalAppsFlyerId"
            r0.put(r1, r5)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
        L_0x0026:
            if (r6 == 0) goto L_0x0035
            int r0 = r6.length()     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r0 <= 0) goto L_0x0035
            org.json.JSONObject r0 = r2.f181     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            java.lang.String r1 = "uid"
            r0.put(r1, r6)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
        L_0x0035:
            monitor-exit(r2)
            return
        L_0x0037:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        L_0x003a:
            r0 = move-exception
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.r.m122(java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* renamed from: ˏ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m126(java.lang.String r3, java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 == 0) goto L_0x0010
            int r0 = r3.length()     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            if (r0 <= 0) goto L_0x0010
            org.json.JSONObject r0 = r2.f181     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            java.lang.String r1 = "app_id"
            r0.put(r1, r3)     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
        L_0x0010:
            if (r4 == 0) goto L_0x001f
            int r0 = r4.length()     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            if (r0 <= 0) goto L_0x001f
            org.json.JSONObject r0 = r2.f181     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            java.lang.String r1 = "app_version"
            r0.put(r1, r4)     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
        L_0x001f:
            if (r5 == 0) goto L_0x002e
            int r0 = r5.length()     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            if (r0 <= 0) goto L_0x002e
            org.json.JSONObject r0 = r2.f181     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            java.lang.String r1 = "channel"
            r0.put(r1, r5)     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
        L_0x002e:
            if (r6 == 0) goto L_0x003d
            int r0 = r6.length()     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            if (r0 <= 0) goto L_0x003d
            org.json.JSONObject r0 = r2.f181     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            java.lang.String r1 = "preInstall"
            r0.put(r1, r6)     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
        L_0x003d:
            monitor-exit(r2)
            return
        L_0x003f:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        L_0x0042:
            r0 = move-exception
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.r.m126(java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m133(String str, String... strArr) {
        m128("public_api_call", str, strArr);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m134(Throwable th) {
        String message;
        StackTraceElement[] stackTrace;
        String[] strArr;
        Throwable cause = th.getCause();
        String simpleName = th.getClass().getSimpleName();
        if (cause == null) {
            message = th.getMessage();
        } else {
            message = cause.getMessage();
        }
        if (cause == null) {
            stackTrace = th.getStackTrace();
        } else {
            stackTrace = cause.getStackTrace();
        }
        if (stackTrace == null) {
            strArr = new String[]{message};
        } else {
            String[] strArr2 = new String[(stackTrace.length + 1)];
            strArr2[0] = message;
            for (int i = 1; i < stackTrace.length; i++) {
                strArr2[i] = stackTrace[i].toString();
            }
            strArr = strArr2;
        }
        m128("exception", simpleName, strArr);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final void m138(String str, String str2) {
        m128("server_request", str, str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m135(String str, int i, String str2) {
        m128("server_response", str, String.valueOf(i), str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final void m140(String str, String str2) {
        m128((String) null, str, str2);
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private synchronized void m128(String str, String str2, String... strArr) {
        String format;
        boolean z = true;
        synchronized (this) {
            if (!this.f190 || (!this.f187 && !this.f183)) {
                z = false;
            }
            if (z && this.f176 < 98304) {
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    String str3 = "";
                    if (strArr.length > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int length = strArr.length - 1; length > 0; length--) {
                            sb.append(strArr[length]).append(", ");
                        }
                        sb.append(strArr[0]);
                        str3 = sb.toString();
                    }
                    String format2 = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.ENGLISH).format(Long.valueOf(currentTimeMillis));
                    if (str != null) {
                        format = String.format("%18s %5s _/%s [%s] %s %s", new Object[]{format2, Long.valueOf(Thread.currentThread().getId()), AppsFlyerLib.LOG_TAG, str, str2, str3});
                    } else {
                        format = String.format("%18s %5s %s/%s %s", new Object[]{format2, Long.valueOf(Thread.currentThread().getId()), str2, AppsFlyerLib.LOG_TAG, str3});
                    }
                    this.f178.put(format);
                    this.f176 = format.getBytes().length + this.f176;
                } catch (Throwable th) {
                }
            }
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* renamed from: ʼ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.lang.String m121() {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            org.json.JSONObject r1 = r4.f181     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            java.lang.String r2 = "data"
            org.json.JSONArray r3 = r4.f178     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            r1.put(r2, r3)     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            org.json.JSONObject r1 = r4.f181     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            java.lang.String r0 = r1.toString()     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            r4.m129()     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
        L_0x0014:
            monitor-exit(r4)
            return r0
        L_0x0016:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x0019:
            r1 = move-exception
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.r.m121():java.lang.String");
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private synchronized void m123(String str, PackageManager packageManager) {
        AppsFlyerProperties instance = AppsFlyerProperties.getInstance();
        AppsFlyerLib instance2 = AppsFlyerLib.getInstance();
        String string = instance.getString("remote_debug_static_data");
        if (string != null) {
            try {
                this.f181 = new JSONObject(string);
            } catch (Throwable th) {
            }
        } else {
            this.f181 = new JSONObject();
            m124(Build.BRAND, Build.MODEL, Build.VERSION.RELEASE, instance.getString(ServerParameters.ADVERTISING_ID_PARAM), instance2.f268, instance2.f269);
            m122("4.8.18.413", instance.getString(AppsFlyerProperties.AF_KEY), instance.getString("KSAppsFlyerId"), instance.getString(GGLiveConstants.PARAM.UID));
            try {
                int i = packageManager.getPackageInfo(str, 0).versionCode;
                m126(str, String.valueOf(i), instance.getString("channel"), instance.getString("preInstallName"));
            } catch (Throwable th2) {
            }
            instance.set("remote_debug_static_data", this.f181.toString());
        }
        try {
            this.f181.put("launch_counter", this.f186);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }

    /* renamed from: ᐝ  reason: contains not printable characters */
    private synchronized void m129() {
        this.f178 = null;
        this.f178 = new JSONArray();
        this.f176 = 0;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final synchronized void m139() {
        this.f187 = false;
        m129();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ʻ  reason: contains not printable characters */
    public final void m130() {
        this.f190 = false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱॱ  reason: contains not printable characters */
    public final boolean m141() {
        return this.f183;
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    static void m127(String str, PackageManager packageManager) {
        try {
            if (f166 == null) {
                f166 = new r();
            }
            f166.m123(str, packageManager);
            if (f166 == null) {
                f166 = new r();
            }
            String r0 = f166.m121();
            m mVar = new m((Context) null, AppsFlyerLib.getInstance().isTrackingStopped());
            mVar.f146 = r0;
            mVar.m99();
            mVar.execute(new String[]{new StringBuilder().append(ServerConfigHandler.getUrl("https://monitorsdk.%s/remote-debug?app_id=")).append(str).toString()});
        } catch (Throwable th) {
        }
    }
}
