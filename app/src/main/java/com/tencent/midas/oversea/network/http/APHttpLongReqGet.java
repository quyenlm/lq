package com.tencent.midas.oversea.network.http;

import com.google.android.gms.appinvite.PreviewActivity;
import java.net.HttpURLConnection;

public abstract class APHttpLongReqGet extends APBaseHttpReq {
    public APHttpLongReqGet() {
        this.httpParam.setSendWithGet();
    }

    private void a() {
        try {
            this.httpURLConnection.setRequestMethod("GET");
            this.httpURLConnection.setDoInput(true);
            this.httpURLConnection.setRequestProperty("Connection", PreviewActivity.ON_CLICK_LISTENER_CLOSE);
            this.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            HttpURLConnection httpURLConnection = this.httpURLConnection;
            APBaseHttpParam aPBaseHttpParam = this.httpParam;
            httpURLConnection.setConnectTimeout(21000);
            HttpURLConnection httpURLConnection2 = this.httpURLConnection;
            APBaseHttpParam aPBaseHttpParam2 = this.httpParam;
            httpURLConnection2.setReadTimeout(600000);
        } catch (Exception e) {
        }
    }

    public void closeHttpURLConnection() {
        this.httpURLConnection.disconnect();
    }

    /* access modifiers changed from: protected */
    public void setBody() {
        super.setBody();
    }

    public void setHeader() {
        a();
    }
}
