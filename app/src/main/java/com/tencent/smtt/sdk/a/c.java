package com.tencent.smtt.sdk.a;

import MTT.ThirdAppInfoNew;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.appinvite.PreviewActivity;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.p;
import com.tencent.smtt.utils.x;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

final class c extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ ThirdAppInfoNew b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    c(String str, Context context, ThirdAppInfoNew thirdAppInfoNew) {
        super(str);
        this.a = context;
        this.b = thirdAppInfoNew;
    }

    public void run() {
        String str;
        JSONObject jSONObject;
        if (Build.VERSION.SDK_INT >= 8) {
            if (b.a == null) {
                try {
                    b.a = "65dRa93L".getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    b.a = null;
                    TbsLog.e("sdkreport", "Post failed -- get POST_DATA_KEY failed!");
                }
            }
            if (b.a == null) {
                TbsLog.e("sdkreport", "Post failed -- POST_DATA_KEY is null!");
                return;
            }
            String string = TbsDownloadConfig.getInstance(this.a).mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_DESkEY_TOKEN, "");
            String str2 = "";
            if (!TextUtils.isEmpty(string)) {
                str2 = string.substring(0, string.indexOf(HttpRequest.HTTP_REQ_ENTITY_JOIN));
                str = string.substring(string.indexOf(HttpRequest.HTTP_REQ_ENTITY_JOIN) + 1, string.length());
            } else {
                str = "";
            }
            boolean z = TextUtils.isEmpty(str2) || str2.length() != 96 || TextUtils.isEmpty(str) || str.length() != 24;
            try {
                x a2 = x.a();
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(z ? a2.b() + p.a().b() : a2.f() + str2).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setConnectTimeout(20000);
                if (Build.VERSION.SDK_INT > 13) {
                    httpURLConnection.setRequestProperty("Connection", PreviewActivity.ON_CLICK_LISTENER_CLOSE);
                }
                try {
                    jSONObject = b.c(this.b, this.a);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    jSONObject = null;
                }
                if (jSONObject == null) {
                    TbsLog.e("sdkreport", "post -- jsonData is null!");
                    return;
                }
                try {
                    byte[] bytes = jSONObject.toString().getBytes("utf-8");
                    byte[] a3 = z ? p.a().a(bytes) : p.a(bytes, str);
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(a3.length));
                    try {
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        outputStream.write(a3);
                        outputStream.flush();
                        if (httpURLConnection.getResponseCode() != 200) {
                            TbsLog.e("sdkreport", "Post failed -- not 200");
                        }
                    } catch (Throwable th) {
                        TbsLog.e("sdkreport", "Post failed -- exceptions:" + th.getMessage());
                    }
                } catch (Throwable th2) {
                }
            } catch (IOException e3) {
                TbsLog.e("sdkreport", "Post failed -- IOException:" + e3);
            } catch (AssertionError e4) {
                TbsLog.e("sdkreport", "Post failed -- AssertionError:" + e4);
            } catch (NoClassDefFoundError e5) {
                TbsLog.e("sdkreport", "Post failed -- NoClassDefFoundError:" + e5);
            }
        }
    }
}
