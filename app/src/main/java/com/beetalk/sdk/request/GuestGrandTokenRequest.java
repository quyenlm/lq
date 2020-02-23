package com.beetalk.sdk.request;

import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.internal.ServerProtocol;
import com.garena.network.AsyncHttpClient;
import com.garena.network.AsyncNetworkRequest;
import com.google.firebase.auth.EmailAuthProvider;
import java.net.MalformedURLException;
import java.net.URL;

public class GuestGrandTokenRequest {
    private String mClientId;
    private String mPassword;
    private String mResponseType;
    private long mUID;

    public GuestGrandTokenRequest(long uid, String password, ResponseType responseType, String client_id) {
        this.mUID = uid;
        this.mPassword = password;
        this.mResponseType = responseType.getStringValue();
        this.mClientId = client_id;
    }

    public void send(AsyncHttpClient.JSONObjectCallback jsonObjectCallback) {
        AsyncNetworkRequest.RequestBuilder builder = new AsyncNetworkRequest.RequestBuilder();
        try {
            builder.setRequestMethod("POST").setUri(getUri()).addHttpParam(GGLiveConstants.PARAM.UID, String.valueOf(this.mUID)).addHttpParam(EmailAuthProvider.PROVIDER_ID, String.valueOf(this.mPassword)).addHttpParam(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, this.mResponseType).addHttpParam(GGLiveConstants.PARAM.CLIENT_TYPE, String.valueOf(RequestConstants.CLIENT_TYPE)).addHttpParam("client_id", String.valueOf(this.mClientId));
            AsyncHttpClient.getInstance().getJSONObject(builder.build(), jsonObjectCallback);
        } catch (MalformedURLException e) {
            BBLogger.e(e);
        }
    }

    /* access modifiers changed from: protected */
    public URL getUri() throws MalformedURLException {
        return new URL(SDKConstants.getGuestGrantTokenURL());
    }
}
