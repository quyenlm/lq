package com.appsflyer;

import android.net.http.Headers;
import android.text.TextUtils;
import com.tencent.imsdk.tool.net.RequestParams;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;

public abstract class OneLinkHttpTask implements Runnable {

    /* renamed from: ˊ  reason: contains not printable characters */
    private HttpsUrlConnectionProvider f60;

    /* renamed from: ˏ  reason: contains not printable characters */
    private AppsFlyerLib f61;

    /* renamed from: ॱ  reason: contains not printable characters */
    String f62;

    /* access modifiers changed from: package-private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public abstract void m32(HttpsURLConnection httpsURLConnection) throws JSONException, IOException;

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public abstract void m33();

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public abstract String m34();

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public abstract void m35(String str);

    OneLinkHttpTask(AppsFlyerLib appsFlyerLib) {
        this.f61 = appsFlyerLib;
    }

    public void setConnProvider(HttpsUrlConnectionProvider httpsUrlConnectionProvider) {
        this.f60 = httpsUrlConnectionProvider;
    }

    public static class HttpsUrlConnectionProvider {
        /* renamed from: ˎ  reason: contains not printable characters */
        static HttpsURLConnection m36(String str) throws IOException {
            return (HttpsURLConnection) new URL(str).openConnection();
        }
    }

    public void run() {
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        String str = "";
        String str2 = "";
        String r4 = m34();
        AFLogger.afRDLog("oneLinkUrl: ".concat(String.valueOf(r4)));
        try {
            HttpsURLConnection r5 = HttpsUrlConnectionProvider.m36(r4);
            r5.addRequestProperty(Headers.CONTENT_TYPE, RequestParams.APPLICATION_JSON);
            StringBuilder sb = new StringBuilder();
            sb.append(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY)).append(currentTimeMillis);
            r5.addRequestProperty("authorization", t.m161(sb.toString()));
            r5.addRequestProperty("af-timestamp", String.valueOf(currentTimeMillis));
            r5.setReadTimeout(3000);
            r5.setConnectTimeout(3000);
            m32(r5);
            int responseCode = r5.getResponseCode();
            str = AppsFlyerLib.m183((HttpURLConnection) r5);
            if (responseCode == 200) {
                AFLogger.afInfoLog("Status 200 ok");
            } else {
                str2 = new StringBuilder("Response code = ").append(responseCode).append(" content = ").append(str).toString();
            }
        } catch (Throwable th) {
            AFLogger.afErrorLog("Error while calling ".concat(String.valueOf(r4)), th);
            str2 = new StringBuilder("Error while calling ").append(r4).append(" stacktrace: ").append(th.toString()).toString();
        }
        if (TextUtils.isEmpty(str2)) {
            AFLogger.afInfoLog("Connection call succeeded: ".concat(String.valueOf(str)));
            m35(str);
            return;
        }
        AFLogger.afWarnLog("Connection error: ".concat(String.valueOf(str2)));
        m33();
    }
}
