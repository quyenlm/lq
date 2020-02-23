package com.appsflyer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.appsflyer.j;
import com.tencent.imsdk.tool.net.RequestParams;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.json.JSONObject;

final class m extends AsyncTask<String, Void, String> {

    /* renamed from: ʻ  reason: contains not printable characters */
    private boolean f139;

    /* renamed from: ʼ  reason: contains not printable characters */
    private boolean f140;

    /* renamed from: ʽ  reason: contains not printable characters */
    private WeakReference<Context> f141;

    /* renamed from: ˊ  reason: contains not printable characters */
    private boolean f142 = false;

    /* renamed from: ˋ  reason: contains not printable characters */
    private boolean f143 = false;

    /* renamed from: ˎ  reason: contains not printable characters */
    private String f144 = "";

    /* renamed from: ˏ  reason: contains not printable characters */
    Map<String, String> f145;

    /* renamed from: ॱ  reason: contains not printable characters */
    String f146;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private URL f147;

    /* renamed from: ᐝ  reason: contains not printable characters */
    private HttpURLConnection f148;

    m(Context context, boolean z) {
        this.f141 = new WeakReference<>(context);
        this.f139 = true;
        this.f140 = true;
        this.f143 = z;
    }

    /* access modifiers changed from: protected */
    public final void onPreExecute() {
        if (this.f146 == null) {
            this.f146 = new JSONObject(this.f145).toString();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final String doInBackground(String... strArr) {
        if (this.f143) {
            return null;
        }
        try {
            this.f147 = new URL(strArr[0]);
            if (this.f139) {
                r.m125().m138(this.f147.toString(), this.f146);
                int length = this.f146.getBytes("UTF-8").length;
                j.AnonymousClass2.m92(new StringBuilder("call = ").append(this.f147).append("; size = ").append(length).append(" byte").append(length > 1 ? "s" : "").append("; body = ").append(this.f146).toString());
            }
            this.f148 = (HttpURLConnection) this.f147.openConnection();
            this.f148.setReadTimeout(30000);
            this.f148.setConnectTimeout(30000);
            this.f148.setRequestMethod("POST");
            this.f148.setDoInput(true);
            this.f148.setDoOutput(true);
            this.f148.setRequestProperty("Content-Type", RequestParams.APPLICATION_JSON);
            OutputStream outputStream = this.f148.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(this.f146);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            this.f148.connect();
            int responseCode = this.f148.getResponseCode();
            if (this.f140) {
                AppsFlyerLib.getInstance();
                this.f144 = AppsFlyerLib.m183(this.f148);
            }
            if (this.f139) {
                r.m125().m135(this.f147.toString(), responseCode, this.f144);
            }
            if (responseCode == 200) {
                AFLogger.afInfoLog("Status 200 ok");
                Context context = this.f141.get();
                if (this.f147.toString().startsWith(ServerConfigHandler.getUrl(AppsFlyerLib.f243)) && context != null) {
                    SharedPreferences.Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
                    edit.putBoolean("sentRegisterRequestToAF", true);
                    edit.apply();
                    AFLogger.afDebugLog("Successfully registered for Uninstall Tracking");
                }
            } else {
                this.f142 = true;
            }
        } catch (Throwable th) {
            AFLogger.afErrorLog(new StringBuilder("Error while calling ").append(this.f147.toString()).toString(), th);
            this.f142 = true;
        }
        return this.f144;
    }

    /* access modifiers changed from: protected */
    public final void onCancelled() {
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void onPostExecute(String str) {
        if (this.f142) {
            AFLogger.afInfoLog("Connection error: ".concat(String.valueOf(str)));
        } else {
            AFLogger.afInfoLog("Connection call succeeded: ".concat(String.valueOf(str)));
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m99() {
        this.f139 = false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final HttpURLConnection m100() {
        return this.f148;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m97() {
        this.f140 = false;
    }
}
