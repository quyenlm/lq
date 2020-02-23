package com.facebook.login;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.internal.NativeProtocol;
import com.facebook.login.LoginClient;

class FacebookLiteLoginMethodHandler extends NativeAppLoginMethodHandler {
    public static final Parcelable.Creator<FacebookLiteLoginMethodHandler> CREATOR = new Parcelable.Creator<FacebookLiteLoginMethodHandler>() {
        public FacebookLiteLoginMethodHandler createFromParcel(Parcel source) {
            return new FacebookLiteLoginMethodHandler(source);
        }

        public FacebookLiteLoginMethodHandler[] newArray(int size) {
            return new FacebookLiteLoginMethodHandler[size];
        }
    };

    FacebookLiteLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    /* access modifiers changed from: package-private */
    public String getNameForLogging() {
        return "fb_lite_login";
    }

    /* access modifiers changed from: package-private */
    public boolean tryAuthorize(LoginClient.Request request) {
        String e2e = LoginClient.getE2E();
        Intent intent = NativeProtocol.createFacebookLiteIntent(this.loginClient.getActivity(), request.getApplicationId(), request.getPermissions(), e2e, request.isRerequest(), request.hasPublishPermission(), request.getDefaultAudience(), getClientState(request.getAuthId()), request.getAuthType());
        addLoggingExtra("e2e", e2e);
        return tryIntent(intent, LoginClient.getLoginRequestCode());
    }

    FacebookLiteLoginMethodHandler(Parcel source) {
        super(source);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
