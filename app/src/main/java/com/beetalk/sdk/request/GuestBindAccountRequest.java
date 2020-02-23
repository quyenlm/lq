package com.beetalk.sdk.request;

import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.network.AsyncHttpClient;
import com.garena.network.AsyncNetworkRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class GuestBindAccountRequest {
    private String mAccessToken;
    private String mGuestAccessToken;

    public GuestBindAccountRequest(String accessToken, String guestAccessToken) {
        this.mAccessToken = accessToken;
        this.mGuestAccessToken = guestAccessToken;
    }

    public void send(AsyncHttpClient.JSONObjectCallback jsonObjectCallback) {
        AsyncNetworkRequest.RequestBuilder builder = new AsyncNetworkRequest.RequestBuilder();
        try {
            builder.setRequestMethod("POST").setUri(getUri()).addHttpParam("access_token", this.mAccessToken).addHttpParam("guest_access_token", this.mGuestAccessToken);
            AsyncHttpClient.getInstance().getJSONObject(builder.build(), jsonObjectCallback);
        } catch (MalformedURLException e) {
            BBLogger.e(e);
        }
    }

    /* access modifiers changed from: protected */
    public URL getUri() throws MalformedURLException {
        return new URL(SDKConstants.getBindGuestAccountURL());
    }
}
