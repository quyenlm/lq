package com.tencent.imsdk.webview.request;

import android.text.TextUtils;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.framework.request.HttpResponseHandler;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MD5;
import com.tencent.imsdk.tool.etc.SafeJSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class WebviewGetTicketRequest extends HttpRequest {
    public static final String WEBVIEW_GETTICKET_ACTION = "/getTicket";
    private HttpResponseHandler<WebviewGetTicketResponse> mHandler = null;
    private WebviewGetTicketParams mWebviewGetTicketParams = null;

    public WebviewGetTicketRequest(WebviewGetTicketParams params, HttpResponseHandler<WebviewGetTicketResponse> handler) {
        this.mWebviewGetTicketParams = params;
        this.mHandler = handler;
        this.isEncode = false;
    }

    /* access modifiers changed from: protected */
    public String getUrl() {
        String url = "";
        try {
            url = IMConfig.getWebviewTicketServerUrl() + WEBVIEW_GETTICKET_ACTION;
            IMLogger.d("url: " + url);
            String str = url;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            String str2 = url;
            return url;
        }
    }

    /* access modifiers changed from: protected */
    public String getClientSecret() {
        return null;
    }

    /* access modifiers changed from: protected */
    public String getReqValue(String key) {
        return null;
    }

    /* access modifiers changed from: protected */
    public SafeJSONObject getBody() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onRequestSuccess(int statusCode, Header[] headers, byte[] response) {
        WebviewGetTicketResponse getTicketResponse = new WebviewGetTicketResponse();
        getTicketResponse.parseSuccessResponse(statusCode, headers, response);
        IMLogger.d("pushSetResponse==" + getTicketResponse.toString());
        if (this.mHandler != null) {
            this.mHandler.onResponse(getTicketResponse);
        }
    }

    /* access modifiers changed from: protected */
    public void onRequestFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable error) {
        WebviewGetTicketResponse getTicketResponse = new WebviewGetTicketResponse();
        getTicketResponse.parseFailureResponse(statusCode, headers, errorResponse, error);
        IMLogger.d(getTicketResponse.toString());
        if (this.mHandler != null) {
            this.mHandler.onResponse(getTicketResponse);
        }
    }

    /* access modifiers changed from: protected */
    public List<NameValuePair> getBodyForPost() {
        Map<String, String> mapParams = new HashMap<>();
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mOpenId)) {
            mapParams.put("iOpenid", this.mWebviewGetTicketParams.mOpenId);
        }
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mInnerToken)) {
            mapParams.put("sInnerToken", this.mWebviewGetTicketParams.mInnerToken);
        }
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mGameId)) {
            mapParams.put("iGameId", this.mWebviewGetTicketParams.mGameId);
        }
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mChannel)) {
            mapParams.put("iChannel", this.mWebviewGetTicketParams.mChannel);
        }
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mPlatform)) {
            mapParams.put("iPlatform", this.mWebviewGetTicketParams.mPlatform);
        }
        this.mWebviewGetTicketParams.mValidKey = MD5.getMd5(mapParams);
        List<NameValuePair> params = new ArrayList<>();
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mOpenId)) {
            params.add(new BasicNameValuePair("iOpenid", this.mWebviewGetTicketParams.mOpenId));
        }
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mInnerToken)) {
            params.add(new BasicNameValuePair("sInnerToken", this.mWebviewGetTicketParams.mInnerToken));
        }
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mGameId)) {
            params.add(new BasicNameValuePair("iGameId", this.mWebviewGetTicketParams.mGameId));
        }
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mChannel)) {
            params.add(new BasicNameValuePair("iChannel", this.mWebviewGetTicketParams.mChannel));
        }
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mPlatform)) {
            params.add(new BasicNameValuePair("iPlatform", this.mWebviewGetTicketParams.mPlatform));
        }
        if (!TextUtils.isEmpty(this.mWebviewGetTicketParams.mValidKey)) {
            params.add(new BasicNameValuePair("sValidKey", this.mWebviewGetTicketParams.mValidKey));
        }
        return params;
    }
}
