package com.tencent.imsdk.framework.request;

import android.app.Activity;
import android.content.Context;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import org.apache.http.params.HttpProtocolParams;

public class HttpRequestManager {
    private static final int DEFAULT_TIME_OUT = 15000;
    private AsyncHttpClient mAsyncHttpClient;
    private Context mContext;

    public void init(Activity activity) {
        this.mContext = activity;
        initAsyncHttpClient();
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return this.mAsyncHttpClient;
    }

    public void postRequest(HttpRequest request) {
        if (this.mAsyncHttpClient == null) {
            initAsyncHttpClient();
        }
        if (this.mAsyncHttpClient != null) {
            request.execute(this.mContext, this.mAsyncHttpClient);
        }
    }

    private void initAsyncHttpClient() {
        try {
            this.mAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
            HttpProtocolParams.setUseExpectContinue(this.mAsyncHttpClient.getHttpClient().getParams(), false);
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            e.printStackTrace();
        }
    }
}
