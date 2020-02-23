package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzamh;
import com.google.android.gms.internal.zzamj;
import com.google.android.gms.internal.zzaoa;
import com.google.android.gms.internal.zzaor;
import com.google.android.gms.internal.zzaos;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Tracker extends zzamh {
    private final Map<String, String> zzHa = new HashMap();
    private boolean zzaeo;
    private final Map<String, String> zzaep = new HashMap();
    /* access modifiers changed from: private */
    public final zzaoa zzaeq;
    /* access modifiers changed from: private */
    public final zza zzaer;
    private ExceptionReporter zzaes;
    /* access modifiers changed from: private */
    public zzaor zzaet;

    class zza extends zzamh implements GoogleAnalytics.zza {
        private boolean zzaeC;
        private int zzaeD;
        private long zzaeE = -1;
        private boolean zzaeF;
        private long zzaeG;

        protected zza(zzamj zzamj) {
            super(zzamj);
        }

        private final void zzjF() {
            if (this.zzaeE >= 0 || this.zzaeC) {
                zzku().zza(Tracker.this.zzaer);
            } else {
                zzku().zzb(Tracker.this.zzaer);
            }
        }

        public final void enableAutoActivityTracking(boolean z) {
            this.zzaeC = z;
            zzjF();
        }

        public final void setSessionTimeout(long j) {
            this.zzaeE = j;
            zzjF();
        }

        /* access modifiers changed from: protected */
        public final void zzjD() {
        }

        public final synchronized boolean zzjE() {
            boolean z;
            z = this.zzaeF;
            this.zzaeF = false;
            return z;
        }

        public final void zzl(Activity activity) {
            String canonicalName;
            String stringExtra;
            if (this.zzaeD == 0) {
                if (zzkq().elapsedRealtime() >= this.zzaeG + Math.max(1000, this.zzaeE)) {
                    this.zzaeF = true;
                }
            }
            this.zzaeD++;
            if (this.zzaeC) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    Tracker.this.setCampaignParamsOnNextHit(intent.getData());
                }
                HashMap hashMap = new HashMap();
                hashMap.put("&t", "screenview");
                Tracker tracker = Tracker.this;
                if (Tracker.this.zzaet != null) {
                    zzaor zzk = Tracker.this.zzaet;
                    String canonicalName2 = activity.getClass().getCanonicalName();
                    canonicalName = zzk.zzaiN.get(canonicalName2);
                    if (canonicalName == null) {
                        canonicalName = canonicalName2;
                    }
                } else {
                    canonicalName = activity.getClass().getCanonicalName();
                }
                tracker.set("&cd", canonicalName);
                if (TextUtils.isEmpty((CharSequence) hashMap.get("&dr"))) {
                    zzbo.zzu(activity);
                    Intent intent2 = activity.getIntent();
                    if (intent2 == null) {
                        stringExtra = null;
                    } else {
                        stringExtra = intent2.getStringExtra("android.intent.extra.REFERRER_NAME");
                        if (TextUtils.isEmpty(stringExtra)) {
                            stringExtra = null;
                        }
                    }
                    if (!TextUtils.isEmpty(stringExtra)) {
                        hashMap.put("&dr", stringExtra);
                    }
                }
                Tracker.this.send(hashMap);
            }
        }

        public final void zzm(Activity activity) {
            this.zzaeD--;
            this.zzaeD = Math.max(0, this.zzaeD);
            if (this.zzaeD == 0) {
                this.zzaeG = zzkq().elapsedRealtime();
            }
        }
    }

    Tracker(zzamj zzamj, String str, zzaoa zzaoa) {
        super(zzamj);
        if (str != null) {
            this.zzHa.put("&tid", str);
        }
        this.zzHa.put("useSecure", "1");
        this.zzHa.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        this.zzaeq = new zzaoa("tracking", zzkq());
        this.zzaer = new zza(zzamj);
    }

    private static String zza(Map.Entry<String, String> entry) {
        String key = entry.getKey();
        entry.getValue();
        if (!(key.startsWith(HttpRequest.HTTP_REQ_ENTITY_JOIN) && key.length() >= 2)) {
            return null;
        }
        return entry.getKey().substring(1);
    }

    private static void zzb(Map<String, String> map, Map<String, String> map2) {
        zzbo.zzu(map2);
        if (map != null) {
            for (Map.Entry next : map.entrySet()) {
                String zza2 = zza((Map.Entry<String, String>) next);
                if (zza2 != null) {
                    map2.put(zza2, (String) next.getValue());
                }
            }
        }
    }

    private static void zzc(Map<String, String> map, Map<String, String> map2) {
        zzbo.zzu(map2);
        if (map != null) {
            for (Map.Entry next : map.entrySet()) {
                String zza2 = zza((Map.Entry<String, String>) next);
                if (zza2 != null && !map2.containsKey(zza2)) {
                    map2.put(zza2, (String) next.getValue());
                }
            }
        }
    }

    public void enableAdvertisingIdCollection(boolean z) {
        this.zzaeo = z;
    }

    public void enableAutoActivityTracking(boolean z) {
        this.zzaer.enableAutoActivityTracking(z);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void enableExceptionReporting(boolean r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            com.google.android.gms.analytics.ExceptionReporter r0 = r3.zzaes     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x000a
            r0 = 1
        L_0x0006:
            if (r0 != r4) goto L_0x000c
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
        L_0x0009:
            return
        L_0x000a:
            r0 = 0
            goto L_0x0006
        L_0x000c:
            if (r4 == 0) goto L_0x002c
            android.content.Context r0 = r3.getContext()     // Catch:{ all -> 0x0029 }
            java.lang.Thread$UncaughtExceptionHandler r1 = java.lang.Thread.getDefaultUncaughtExceptionHandler()     // Catch:{ all -> 0x0029 }
            com.google.android.gms.analytics.ExceptionReporter r2 = new com.google.android.gms.analytics.ExceptionReporter     // Catch:{ all -> 0x0029 }
            r2.<init>(r3, r1, r0)     // Catch:{ all -> 0x0029 }
            r3.zzaes = r2     // Catch:{ all -> 0x0029 }
            com.google.android.gms.analytics.ExceptionReporter r0 = r3.zzaes     // Catch:{ all -> 0x0029 }
            java.lang.Thread.setDefaultUncaughtExceptionHandler(r0)     // Catch:{ all -> 0x0029 }
            java.lang.String r0 = "Uncaught exceptions will be reported to Google Analytics"
            r3.zzbo(r0)     // Catch:{ all -> 0x0029 }
        L_0x0027:
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            goto L_0x0009
        L_0x0029:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            throw r0
        L_0x002c:
            com.google.android.gms.analytics.ExceptionReporter r0 = r3.zzaes     // Catch:{ all -> 0x0029 }
            java.lang.Thread$UncaughtExceptionHandler r0 = r0.zzjn()     // Catch:{ all -> 0x0029 }
            java.lang.Thread.setDefaultUncaughtExceptionHandler(r0)     // Catch:{ all -> 0x0029 }
            java.lang.String r0 = "Uncaught exceptions will not be reported to Google Analytics"
            r3.zzbo(r0)     // Catch:{ all -> 0x0029 }
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.Tracker.enableExceptionReporting(boolean):void");
    }

    public String get(String str) {
        zzkD();
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (this.zzHa.containsKey(str)) {
            return this.zzHa.get(str);
        }
        if (str.equals("&ul")) {
            return zzaos.zza(Locale.getDefault());
        }
        if (str.equals("&cid")) {
            return zzkz().zzli();
        }
        if (str.equals("&sr")) {
            return zzkC().zzlB();
        }
        if (str.equals("&aid")) {
            return zzkB().zzkW().zzhl();
        }
        if (str.equals("&an")) {
            return zzkB().zzkW().zzjG();
        }
        if (str.equals("&av")) {
            return zzkB().zzkW().zzjH();
        }
        if (str.equals("&aiid")) {
            return zzkB().zzkW().zzjI();
        }
        return null;
    }

    public void send(Map<String, String> map) {
        long currentTimeMillis = zzkq().currentTimeMillis();
        if (zzku().getAppOptOut()) {
            zzbp("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean isDryRunEnabled = zzku().isDryRunEnabled();
        HashMap hashMap = new HashMap();
        zzb(this.zzHa, hashMap);
        zzb(map, hashMap);
        boolean zzf = zzaos.zzf(this.zzHa.get("useSecure"), true);
        zzc(this.zzaep, hashMap);
        this.zzaep.clear();
        String str = (String) hashMap.get("t");
        if (TextUtils.isEmpty(str)) {
            zzkr().zze(hashMap, "Missing hit type parameter");
            return;
        }
        String str2 = (String) hashMap.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zzkr().zze(hashMap, "Missing tracking id parameter");
            return;
        }
        boolean z = this.zzaeo;
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int parseInt = Integer.parseInt(this.zzHa.get("&a")) + 1;
                if (parseInt >= Integer.MAX_VALUE) {
                    parseInt = 1;
                }
                this.zzHa.put("&a", Integer.toString(parseInt));
            }
        }
        zzkt().zzf(new zzp(this, hashMap, z, str, currentTimeMillis, isDryRunEnabled, zzf, str2));
    }

    public void set(String str, String str2) {
        zzbo.zzb(str, (Object) "Key should be non-null");
        if (!TextUtils.isEmpty(str)) {
            this.zzHa.put(str, str2);
        }
    }

    public void setAnonymizeIp(boolean z) {
        set("&aip", zzaos.zzI(z));
    }

    public void setAppId(String str) {
        set("&aid", str);
    }

    public void setAppInstallerId(String str) {
        set("&aiid", str);
    }

    public void setAppName(String str) {
        set("&an", str);
    }

    public void setAppVersion(String str) {
        set("&av", str);
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri != null && !uri.isOpaque()) {
            String queryParameter = uri.getQueryParameter("referrer");
            if (!TextUtils.isEmpty(queryParameter)) {
                String valueOf = String.valueOf(queryParameter);
                Uri parse = Uri.parse(valueOf.length() != 0 ? "http://hostname/?".concat(valueOf) : new String("http://hostname/?"));
                String queryParameter2 = parse.getQueryParameter("utm_id");
                if (queryParameter2 != null) {
                    this.zzaep.put("&ci", queryParameter2);
                }
                String queryParameter3 = parse.getQueryParameter("anid");
                if (queryParameter3 != null) {
                    this.zzaep.put("&anid", queryParameter3);
                }
                String queryParameter4 = parse.getQueryParameter("utm_campaign");
                if (queryParameter4 != null) {
                    this.zzaep.put("&cn", queryParameter4);
                }
                String queryParameter5 = parse.getQueryParameter("utm_content");
                if (queryParameter5 != null) {
                    this.zzaep.put("&cc", queryParameter5);
                }
                String queryParameter6 = parse.getQueryParameter("utm_medium");
                if (queryParameter6 != null) {
                    this.zzaep.put("&cm", queryParameter6);
                }
                String queryParameter7 = parse.getQueryParameter("utm_source");
                if (queryParameter7 != null) {
                    this.zzaep.put("&cs", queryParameter7);
                }
                String queryParameter8 = parse.getQueryParameter("utm_term");
                if (queryParameter8 != null) {
                    this.zzaep.put("&ck", queryParameter8);
                }
                String queryParameter9 = parse.getQueryParameter("dclid");
                if (queryParameter9 != null) {
                    this.zzaep.put("&dclid", queryParameter9);
                }
                String queryParameter10 = parse.getQueryParameter("gclid");
                if (queryParameter10 != null) {
                    this.zzaep.put("&gclid", queryParameter10);
                }
                String queryParameter11 = parse.getQueryParameter(FirebaseAnalytics.Param.ACLID);
                if (queryParameter11 != null) {
                    this.zzaep.put("&aclid", queryParameter11);
                }
            }
        }
    }

    public void setClientId(String str) {
        set("&cid", str);
    }

    public void setEncoding(String str) {
        set("&de", str);
    }

    public void setHostname(String str) {
        set("&dh", str);
    }

    public void setLanguage(String str) {
        set("&ul", str);
    }

    public void setLocation(String str) {
        set("&dl", str);
    }

    public void setPage(String str) {
        set("&dp", str);
    }

    public void setReferrer(String str) {
        set("&dr", str);
    }

    public void setSampleRate(double d) {
        set("&sf", Double.toString(d));
    }

    public void setScreenColors(String str) {
        set("&sd", str);
    }

    public void setScreenName(String str) {
        set("&cd", str);
    }

    public void setScreenResolution(int i, int i2) {
        if (i >= 0 || i2 >= 0) {
            set("&sr", new StringBuilder(23).append(i).append("x").append(i2).toString());
        } else {
            zzbr("Invalid width or height. The values should be non-negative.");
        }
    }

    public void setSessionTimeout(long j) {
        this.zzaer.setSessionTimeout(1000 * j);
    }

    public void setTitle(String str) {
        set("&dt", str);
    }

    public void setUseSecure(boolean z) {
        set("useSecure", zzaos.zzI(z));
    }

    public void setViewportSize(String str) {
        set("&vp", str);
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzaor zzaor) {
        boolean z = true;
        zzbo("Loading Tracker config values");
        this.zzaet = zzaor;
        if (this.zzaet.zzado != null) {
            String str = this.zzaet.zzado;
            set("&tid", str);
            zza("trackingId loaded", str);
        }
        if (this.zzaet.zzaiI >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            String d = Double.toString(this.zzaet.zzaiI);
            set("&sf", d);
            zza("Sample frequency loaded", d);
        }
        if (this.zzaet.zzaiJ >= 0) {
            int i = this.zzaet.zzaiJ;
            setSessionTimeout((long) i);
            zza("Session timeout loaded", Integer.valueOf(i));
        }
        if (this.zzaet.zzaiK != -1) {
            boolean z2 = this.zzaet.zzaiK == 1;
            enableAutoActivityTracking(z2);
            zza("Auto activity tracking loaded", Boolean.valueOf(z2));
        }
        if (this.zzaet.zzaiL != -1) {
            boolean z3 = this.zzaet.zzaiL == 1;
            if (z3) {
                set("&aip", "1");
            }
            zza("Anonymize ip loaded", Boolean.valueOf(z3));
        }
        if (this.zzaet.zzaiM != 1) {
            z = false;
        }
        enableExceptionReporting(z);
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
        this.zzaer.initialize();
        String zzjG = zzkx().zzjG();
        if (zzjG != null) {
            set("&an", zzjG);
        }
        String zzjH = zzkx().zzjH();
        if (zzjH != null) {
            set("&av", zzjH);
        }
    }
}
