package com.appsflyer.share;

import android.content.Context;
import android.os.AsyncTask;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerProperties;
import com.appsflyer.ServerConfigHandler;
import com.appsflyer.ServerParameters;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class CrossPromotionHelper {
    public static void trackAndOpenStore(Context context, String str, String str2) {
        trackAndOpenStore(context, str, str2, (Map<String, String>) null);
    }

    public static void trackAndOpenStore(Context context, String str, String str2, Map<String, String> map) {
        LinkGenerator r0 = m150(context, str, str2, map, ServerConfigHandler.getUrl(Constants.BASE_URL_APP_APPSFLYER_COM));
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
            AFLogger.afInfoLog("CustomerUserId not set, track And Open Store is disabled", true);
            return;
        }
        HashMap hashMap = new HashMap();
        if (map != null) {
            hashMap.putAll(map);
        }
        hashMap.put("af_campaign", str2);
        AppsFlyerLib.getInstance().trackEvent(context, "af_cross_promotion", hashMap);
        new e(new a(), context, AppsFlyerLib.getInstance().isTrackingStopped()).execute(new String[]{r0.generateLink()});
    }

    public static void trackCrossPromoteImpression(Context context, String str, String str2) {
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
            AFLogger.afInfoLog("CustomerUserId not set, Promote Impression is disabled", true);
            return;
        }
        LinkGenerator r0 = m150(context, str, str2, (Map<String, String>) null, ServerConfigHandler.getUrl("https://impression.%s"));
        new e((a) null, (Context) null, AppsFlyerLib.getInstance().isTrackingStopped()).execute(new String[]{r0.generateLink()});
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static LinkGenerator m150(Context context, String str, String str2, Map<String, String> map, String str3) {
        LinkGenerator linkGenerator = new LinkGenerator("af_cross_promotion");
        linkGenerator.m155(str3).m154(str).addParameter(Constants.URL_SITE_ID, context.getPackageName());
        if (str2 != null) {
            linkGenerator.setCampaign(str2);
        }
        if (map != null) {
            linkGenerator.addParameters(map);
        }
        String string = AppsFlyerProperties.getInstance().getString(ServerParameters.ADVERTISING_ID_PARAM);
        if (string != null) {
            linkGenerator.addParameter(Constants.URL_ADVERTISING_ID, string);
        }
        return linkGenerator;
    }

    static class e extends AsyncTask<String, Void, Void> {

        /* renamed from: ˊ  reason: contains not printable characters */
        private a f201;

        /* renamed from: ˏ  reason: contains not printable characters */
        private boolean f202;

        /* renamed from: ॱ  reason: contains not printable characters */
        private WeakReference<Context> f203;

        e(a aVar, Context context, boolean z) {
            this.f201 = aVar;
            this.f203 = new WeakReference<>(context);
            this.f202 = z;
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00a5  */
        /* renamed from: ˊ  reason: contains not printable characters */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.String... r7) {
            /*
                r6 = this;
                r3 = 0
                boolean r0 = r6.f202
                if (r0 == 0) goto L_0x0006
            L_0x0005:
                return r3
            L_0x0006:
                r0 = 0
                r1 = r7[r0]     // Catch:{ Throwable -> 0x00b0, all -> 0x00a9 }
                java.net.URL r0 = new java.net.URL     // Catch:{ Throwable -> 0x00b0, all -> 0x00a9 }
                r0.<init>(r1)     // Catch:{ Throwable -> 0x00b0, all -> 0x00a9 }
                java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Throwable -> 0x00b0, all -> 0x00a9 }
                java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Throwable -> 0x00b0, all -> 0x00a9 }
                r2 = 10000(0x2710, float:1.4013E-41)
                r0.setConnectTimeout(r2)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                r2 = 0
                r0.setInstanceFollowRedirects(r2)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                int r2 = r0.getResponseCode()     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                r4 = 200(0xc8, float:2.8E-43)
                if (r2 != r4) goto L_0x0039
                java.lang.String r2 = "Cross promotion impressions success: "
                java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.String r1 = r2.concat(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                r2 = 0
                com.appsflyer.AFLogger.afInfoLog(r1, r2)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
            L_0x0033:
                if (r0 == 0) goto L_0x0005
                r0.disconnect()
                goto L_0x0005
            L_0x0039:
                r4 = 301(0x12d, float:4.22E-43)
                if (r2 == r4) goto L_0x0041
                r4 = 302(0x12e, float:4.23E-43)
                if (r2 != r4) goto L_0x0084
            L_0x0041:
                java.lang.String r2 = "Cross promotion redirection success: "
                java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.String r1 = r2.concat(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                r2 = 0
                com.appsflyer.AFLogger.afInfoLog(r1, r2)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                com.appsflyer.share.a r1 = r6.f201     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                if (r1 == 0) goto L_0x0033
                java.lang.ref.WeakReference<android.content.Context> r1 = r6.f203     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.Object r1 = r1.get()     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                if (r1 == 0) goto L_0x0033
                java.lang.String r1 = "Location"
                java.lang.String r1 = r0.getHeaderField(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                com.appsflyer.share.a r2 = r6.f201     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                r2.m157(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                com.appsflyer.share.a r2 = r6.f201     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.ref.WeakReference<android.content.Context> r1 = r6.f203     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.Object r1 = r1.get()     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                android.content.Context r1 = (android.content.Context) r1     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                r2.m156(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                goto L_0x0033
            L_0x0074:
                r1 = move-exception
                r2 = r0
            L_0x0076:
                java.lang.String r0 = r1.getMessage()     // Catch:{ all -> 0x00ac }
                r4 = 1
                com.appsflyer.AFLogger.afErrorLog(r0, r1, r4)     // Catch:{ all -> 0x00ac }
                if (r2 == 0) goto L_0x0005
                r2.disconnect()
                goto L_0x0005
            L_0x0084:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.String r5 = "call to "
                r4.<init>(r5)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.String r4 = " failed: "
                java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                com.appsflyer.AFLogger.afInfoLog(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a1 }
                goto L_0x0033
            L_0x00a1:
                r1 = move-exception
                r3 = r0
            L_0x00a3:
                if (r3 == 0) goto L_0x00a8
                r3.disconnect()
            L_0x00a8:
                throw r1
            L_0x00a9:
                r0 = move-exception
                r1 = r0
                goto L_0x00a3
            L_0x00ac:
                r0 = move-exception
                r1 = r0
                r3 = r2
                goto L_0x00a3
            L_0x00b0:
                r0 = move-exception
                r1 = r0
                r2 = r3
                goto L_0x0076
            */
            throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.share.CrossPromotionHelper.e.doInBackground(java.lang.String[]):java.lang.Void");
        }
    }
}
