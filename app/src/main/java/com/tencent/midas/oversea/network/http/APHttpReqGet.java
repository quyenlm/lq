package com.tencent.midas.oversea.network.http;

import com.google.android.gms.appinvite.PreviewActivity;

public abstract class APHttpReqGet extends APBaseHttpReq {
    public APHttpReqGet() {
        this.httpParam.setSendWithGet();
    }

    private void a() {
        try {
            this.httpURLConnection.setRequestMethod("GET");
            this.httpURLConnection.setDoInput(true);
            this.httpURLConnection.setRequestProperty("Connection", PreviewActivity.ON_CLICK_LISTENER_CLOSE);
            this.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: protected */
    public void setBody() {
        super.setBody();
    }

    public void setHeader() {
        a();
    }
}
