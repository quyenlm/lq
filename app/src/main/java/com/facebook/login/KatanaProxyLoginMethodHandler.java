package com.facebook.login;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.internal.NativeProtocol;
import com.facebook.login.LoginClient;

class KatanaProxyLoginMethodHandler extends NativeAppLoginMethodHandler {
    public static final Parcelable.Creator<KatanaProxyLoginMethodHandler> CREATOR = new Parcelable.Creator<KatanaProxyLoginMethodHandler>() {
        public KatanaProxyLoginMethodHandler createFromParcel(Parcel source) {
            return new KatanaProxyLoginMethodHandler(source);
        }

        public KatanaProxyLoginMethodHandler[] newArray(int size) {
            return new KatanaProxyLoginMethodHandler[size];
        }
    };

    KatanaProxyLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    /* access modifiers changed from: package-private */
    public String getNameForLogging() {
        return "katana_proxy_auth";
    }

    /* access modifiers changed from: package-private */
    public boolean tryAuthorize(LoginClient.Request request) {
        String e2e = LoginClient.getE2E();
        Intent intent = NativeProtocol.createProxyAuthIntent(this.loginClient.getActivity(), request.getApplicationId(), request.getPermissions(), e2e, request.isRerequest(), request.hasPublishPermission(), request.getDefaultAudience(), getClientState(request.getAuthId()), request.getAuthType());
        addLoggingExtra("e2e", e2e);
        return tryIntent(intent, LoginClient.getLoginRequestCode());
    }

    KatanaProxyLoginMethodHandler(Parcel source) {
        super(source);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
