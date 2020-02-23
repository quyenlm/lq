package com.tencent.midas.oversea.network.http;

import com.tencent.component.net.download.multiplex.http.HttpHeader;

public abstract class APHttpReqPost extends APBaseHttpReq {
    public APHttpReqPost() {
        this.httpParam.setReqWithHttp();
        this.httpParam.setSendWithPost();
    }

    private void a() {
        try {
            this.httpURLConnection.setRequestMethod("POST");
            this.httpURLConnection.setRequestProperty(HttpHeader.RSP.CHARSET, "UTF-8");
            this.httpURLConnection.setDoInput(true);
            this.httpURLConnection.setDoOutput(true);
            this.httpURLConnection.setRequestProperty("Content-Length", String.valueOf(this.httpParam.urlParams.getBytes().length));
            this.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: protected */
    public void setBody() {
        super.setBody();
    }

    /* access modifiers changed from: protected */
    public void setHeader() {
        a();
    }
}
