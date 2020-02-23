package com.beetalk.sdk.request;

import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.GuestAccountRegInfo;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.network.AsyncHttpClient;
import com.garena.network.AsyncNetworkRequest;
import com.google.firebase.auth.EmailAuthProvider;
import java.net.MalformedURLException;
import java.net.URL;

public class GuestAccountRegRequest {
    private GuestAccountRegInfo mGuestAccountRegInfo;

    public GuestAccountRegRequest(GuestAccountRegInfo guestAccountRegInfo) {
        this.mGuestAccountRegInfo = guestAccountRegInfo;
    }

    public void send(AsyncHttpClient.JSONObjectCallback jsonObjectCallback) {
        AsyncNetworkRequest.RequestBuilder builder = new AsyncNetworkRequest.RequestBuilder();
        try {
            builder.setRequestMethod("POST").setUri(getUri()).addHttpParam(EmailAuthProvider.PROVIDER_ID, this.mGuestAccountRegInfo.password).addHttpParam(GGLiveConstants.PARAM.CLIENT_TYPE, String.valueOf(RequestConstants.CLIENT_TYPE)).addHttpParam("app_id", String.valueOf(this.mGuestAccountRegInfo.app_id)).addHttpParam("source", String.valueOf(this.mGuestAccountRegInfo.source));
            AsyncHttpClient.getInstance().getJSONObject(builder.build(), jsonObjectCallback);
        } catch (MalformedURLException e) {
            BBLogger.e(e);
        }
    }

    /* access modifiers changed from: protected */
    public URL getUri() throws MalformedURLException {
        return new URL(SDKConstants.getRegisterGuestAccountURL());
    }
}
