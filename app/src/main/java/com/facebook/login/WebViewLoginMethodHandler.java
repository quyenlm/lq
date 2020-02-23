package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import com.facebook.login.LoginClient;

class WebViewLoginMethodHandler extends WebLoginMethodHandler {
    public static final Parcelable.Creator<WebViewLoginMethodHandler> CREATOR = new Parcelable.Creator<WebViewLoginMethodHandler>() {
        public WebViewLoginMethodHandler createFromParcel(Parcel source) {
            return new WebViewLoginMethodHandler(source);
        }

        public WebViewLoginMethodHandler[] newArray(int size) {
            return new WebViewLoginMethodHandler[size];
        }
    };
    private String e2e;
    private WebDialog loginDialog;

    WebViewLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    /* access modifiers changed from: package-private */
    public String getNameForLogging() {
        return "web_view";
    }

    /* access modifiers changed from: package-private */
    public AccessTokenSource getTokenSource() {
        return AccessTokenSource.WEB_VIEW;
    }

    /* access modifiers changed from: package-private */
    public boolean needsInternetPermission() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
        if (this.loginDialog != null) {
            this.loginDialog.cancel();
            this.loginDialog = null;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean tryAuthorize(final LoginClient.Request request) {
        Bundle parameters = getParameters(request);
        WebDialog.OnCompleteListener listener = new WebDialog.OnCompleteListener() {
            public void onComplete(Bundle values, FacebookException error) {
                WebViewLoginMethodHandler.this.onWebDialogComplete(request, values, error);
            }
        };
        this.e2e = LoginClient.getE2E();
        addLoggingExtra("e2e", this.e2e);
        FragmentActivity fragmentActivity = this.loginClient.getActivity();
        this.loginDialog = new AuthDialogBuilder(fragmentActivity, request.getApplicationId(), parameters).setE2E(this.e2e).setIsChromeOS(Utility.isChromeOS(fragmentActivity)).setAuthType(request.getAuthType()).setOnCompleteListener(listener).build();
        FacebookDialogFragment dialogFragment = new FacebookDialogFragment();
        dialogFragment.setRetainInstance(true);
        dialogFragment.setDialog(this.loginDialog);
        dialogFragment.show(fragmentActivity.getSupportFragmentManager(), FacebookDialogFragment.TAG);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void onWebDialogComplete(LoginClient.Request request, Bundle values, FacebookException error) {
        super.onComplete(request, values, error);
    }

    static class AuthDialogBuilder extends WebDialog.Builder {
        private static final String OAUTH_DIALOG = "oauth";
        private String authType;
        private String e2e;
        private String redirect_uri = ServerProtocol.DIALOG_REDIRECT_URI;

        public AuthDialogBuilder(Context context, String applicationId, Bundle parameters) {
            super(context, applicationId, OAUTH_DIALOG, parameters);
        }

        public AuthDialogBuilder setE2E(String e2e2) {
            this.e2e = e2e2;
            return this;
        }

        public AuthDialogBuilder setIsRerequest(boolean isRerequest) {
            return this;
        }

        public AuthDialogBuilder setIsChromeOS(boolean isChromeOS) {
            this.redirect_uri = isChromeOS ? ServerProtocol.DIALOG_REDIRECT_CHROME_OS_URI : ServerProtocol.DIALOG_REDIRECT_URI;
            return this;
        }

        public AuthDialogBuilder setAuthType(String authType2) {
            this.authType = authType2;
            return this;
        }

        public WebDialog build() {
            Bundle parameters = getParameters();
            parameters.putString("redirect_uri", this.redirect_uri);
            parameters.putString("client_id", getApplicationId());
            parameters.putString("e2e", this.e2e);
            parameters.putString(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST);
            parameters.putString(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, "true");
            parameters.putString(ServerProtocol.DIALOG_PARAM_AUTH_TYPE, this.authType);
            return WebDialog.newInstance(getContext(), OAUTH_DIALOG, parameters, getTheme(), getListener());
        }
    }

    WebViewLoginMethodHandler(Parcel source) {
        super(source);
        this.e2e = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.e2e);
    }
}
