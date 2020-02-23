package com.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.AccessTokenSource;
import com.facebook.CustomTabMainActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient;
import com.vk.sdk.api.VKApiConst;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomTabLoginMethodHandler extends WebLoginMethodHandler {
    private static final int API_EC_DIALOG_CANCEL = 4201;
    private static final int CHALLENGE_LENGTH = 20;
    private static final String[] CHROME_PACKAGES = {"com.android.chrome", "com.chrome.beta", "com.chrome.dev"};
    public static final Parcelable.Creator<CustomTabLoginMethodHandler> CREATOR = new Parcelable.Creator() {
        public CustomTabLoginMethodHandler createFromParcel(Parcel source) {
            return new CustomTabLoginMethodHandler(source);
        }

        public CustomTabLoginMethodHandler[] newArray(int size) {
            return new CustomTabLoginMethodHandler[size];
        }
    };
    private static final String CUSTOM_TABS_SERVICE_ACTION = "android.support.customtabs.action.CustomTabsService";
    private static final int CUSTOM_TAB_REQUEST_CODE = 1;
    private String currentPackage;
    private String expectedChallenge;

    CustomTabLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
        this.expectedChallenge = Utility.generateRandomString(20);
    }

    /* access modifiers changed from: package-private */
    public String getNameForLogging() {
        return "custom_tab";
    }

    /* access modifiers changed from: package-private */
    public AccessTokenSource getTokenSource() {
        return AccessTokenSource.CHROME_CUSTOM_TAB;
    }

    /* access modifiers changed from: protected */
    public String getSSODevice() {
        return "chrome_custom_tab";
    }

    /* access modifiers changed from: package-private */
    public boolean tryAuthorize(LoginClient.Request request) {
        if (!isCustomTabsAllowed()) {
            return false;
        }
        Bundle parameters = addExtraParameters(getParameters(request), request);
        Intent intent = new Intent(this.loginClient.getActivity(), CustomTabMainActivity.class);
        intent.putExtra(CustomTabMainActivity.EXTRA_PARAMS, parameters);
        intent.putExtra(CustomTabMainActivity.EXTRA_CHROME_PACKAGE, getChromePackage());
        this.loginClient.getFragment().startActivityForResult(intent, 1);
        return true;
    }

    private boolean isCustomTabsAllowed() {
        return isCustomTabsEnabled() && getChromePackage() != null && isCustomTabsCompatibleWithAutofill() && Validate.hasCustomTabRedirectActivity(FacebookSdk.getApplicationContext());
    }

    private boolean isCustomTabsEnabled() {
        FetchedAppSettings settings = FetchedAppSettingsManager.getAppSettingsWithoutQuery(Utility.getMetadataApplicationId(this.loginClient.getActivity()));
        return settings != null && settings.getCustomTabsEnabled();
    }

    private String getChromePackage() {
        if (this.currentPackage != null) {
            return this.currentPackage;
        }
        Context context = this.loginClient.getActivity();
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentServices(new Intent("android.support.customtabs.action.CustomTabsService"), 0);
        if (resolveInfos != null) {
            Set<String> chromePackages = new HashSet<>(Arrays.asList(CHROME_PACKAGES));
            for (ResolveInfo resolveInfo : resolveInfos) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                if (serviceInfo != null && chromePackages.contains(serviceInfo.packageName)) {
                    this.currentPackage = serviceInfo.packageName;
                    return this.currentPackage;
                }
            }
        }
        return null;
    }

    private boolean isCustomTabsCompatibleWithAutofill() {
        if (!Utility.isAutofillAvailable(this.loginClient.getActivity())) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1) {
            return super.onActivityResult(requestCode, resultCode, data);
        }
        LoginClient.Request request = this.loginClient.getPendingRequest();
        if (resultCode == -1) {
            onCustomTabComplete(data.getStringExtra(CustomTabMainActivity.EXTRA_URL), request);
            return true;
        }
        super.onComplete(request, (Bundle) null, new FacebookOperationCanceledException());
        return false;
    }

    private void onCustomTabComplete(String url, LoginClient.Request request) {
        if (url != null && url.startsWith(CustomTabMainActivity.getRedirectUrl())) {
            Uri uri = Uri.parse(url);
            Bundle values = Utility.parseUrlQueryString(uri.getQuery());
            values.putAll(Utility.parseUrlQueryString(uri.getFragment()));
            if (!validateChallengeParam(values)) {
                super.onComplete(request, (Bundle) null, new FacebookException("Invalid state parameter"));
                return;
            }
            String error = values.getString("error");
            if (error == null) {
                error = values.getString(NativeProtocol.BRIDGE_ARG_ERROR_TYPE);
            }
            String errorMessage = values.getString(VKApiConst.ERROR_MSG);
            if (errorMessage == null) {
                errorMessage = values.getString(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE);
            }
            if (errorMessage == null) {
                errorMessage = values.getString(NativeProtocol.BRIDGE_ARG_ERROR_DESCRIPTION);
            }
            String errorCodeString = values.getString("error_code");
            int errorCode = -1;
            if (!Utility.isNullOrEmpty(errorCodeString)) {
                try {
                    errorCode = Integer.parseInt(errorCodeString);
                } catch (NumberFormatException e) {
                    errorCode = -1;
                }
            }
            if (Utility.isNullOrEmpty(error) && Utility.isNullOrEmpty(errorMessage) && errorCode == -1) {
                super.onComplete(request, values, (FacebookException) null);
            } else if (error != null && (error.equals("access_denied") || error.equals("OAuthAccessDeniedException"))) {
                super.onComplete(request, (Bundle) null, new FacebookOperationCanceledException());
            } else if (errorCode == API_EC_DIALOG_CANCEL) {
                super.onComplete(request, (Bundle) null, new FacebookOperationCanceledException());
            } else {
                super.onComplete(request, (Bundle) null, new FacebookServiceException(new FacebookRequestError(errorCode, error, errorMessage), errorMessage));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void putChallengeParam(JSONObject param) throws JSONException {
        param.put("7_challenge", this.expectedChallenge);
    }

    private boolean validateChallengeParam(Bundle values) {
        try {
            String stateString = values.getString("state");
            if (stateString == null) {
                return false;
            }
            return new JSONObject(stateString).getString("7_challenge").equals(this.expectedChallenge);
        } catch (JSONException e) {
            return false;
        }
    }

    public int describeContents() {
        return 0;
    }

    CustomTabLoginMethodHandler(Parcel source) {
        super(source);
        this.expectedChallenge = source.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.expectedChallenge);
    }
}
