package com.facebook.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.common.R;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class LoginClient implements Parcelable {
    public static final Parcelable.Creator<LoginClient> CREATOR = new Parcelable.Creator<LoginClient>() {
        public LoginClient createFromParcel(Parcel source) {
            return new LoginClient(source);
        }

        public LoginClient[] newArray(int size) {
            return new LoginClient[size];
        }
    };
    BackgroundProcessingListener backgroundProcessingListener;
    boolean checkedInternetPermission;
    int currentHandler = -1;
    Fragment fragment;
    LoginMethodHandler[] handlersToTry;
    Map<String, String> loggingExtras;
    private LoginLogger loginLogger;
    OnCompletedListener onCompletedListener;
    Request pendingRequest;

    interface BackgroundProcessingListener {
        void onBackgroundProcessingStarted();

        void onBackgroundProcessingStopped();
    }

    public interface OnCompletedListener {
        void onCompleted(Result result);
    }

    public LoginClient(Fragment fragment2) {
        this.fragment = fragment2;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    /* access modifiers changed from: package-private */
    public void setFragment(Fragment fragment2) {
        if (this.fragment != null) {
            throw new FacebookException("Can't set fragment once it is already set.");
        }
        this.fragment = fragment2;
    }

    /* access modifiers changed from: package-private */
    public FragmentActivity getActivity() {
        return this.fragment.getActivity();
    }

    public Request getPendingRequest() {
        return this.pendingRequest;
    }

    public static int getLoginRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
    }

    /* access modifiers changed from: package-private */
    public void startOrContinueAuth(Request request) {
        if (!getInProgress()) {
            authorize(request);
        }
    }

    /* access modifiers changed from: package-private */
    public void authorize(Request request) {
        if (request != null) {
            if (this.pendingRequest != null) {
                throw new FacebookException("Attempted to authorize while a request is pending.");
            } else if (!AccessToken.isCurrentAccessTokenActive() || checkInternetPermission()) {
                this.pendingRequest = request;
                this.handlersToTry = getHandlersToTry(request);
                tryNextHandler();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean getInProgress() {
        return this.pendingRequest != null && this.currentHandler >= 0;
    }

    /* access modifiers changed from: package-private */
    public void cancelCurrentHandler() {
        if (this.currentHandler >= 0) {
            getCurrentHandler().cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public LoginMethodHandler getCurrentHandler() {
        if (this.currentHandler >= 0) {
            return this.handlersToTry[this.currentHandler];
        }
        return null;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.pendingRequest != null) {
            return getCurrentHandler().onActivityResult(requestCode, resultCode, data);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public LoginMethodHandler[] getHandlersToTry(Request request) {
        ArrayList<LoginMethodHandler> handlers = new ArrayList<>();
        LoginBehavior behavior = request.getLoginBehavior();
        if (behavior.allowsGetTokenAuth()) {
            handlers.add(new GetTokenLoginMethodHandler(this));
        }
        if (behavior.allowsKatanaAuth()) {
            handlers.add(new KatanaProxyLoginMethodHandler(this));
        }
        if (behavior.allowsFacebookLiteAuth()) {
            handlers.add(new FacebookLiteLoginMethodHandler(this));
        }
        if (behavior.allowsCustomTabAuth()) {
            handlers.add(new CustomTabLoginMethodHandler(this));
        }
        if (behavior.allowsWebViewAuth()) {
            handlers.add(new WebViewLoginMethodHandler(this));
        }
        if (behavior.allowsDeviceAuth()) {
            handlers.add(new DeviceAuthMethodHandler(this));
        }
        LoginMethodHandler[] result = new LoginMethodHandler[handlers.size()];
        handlers.toArray(result);
        return result;
    }

    /* access modifiers changed from: package-private */
    public boolean checkInternetPermission() {
        if (this.checkedInternetPermission) {
            return true;
        }
        if (checkPermission("android.permission.INTERNET") != 0) {
            Activity activity = getActivity();
            complete(Result.createErrorResult(this.pendingRequest, activity.getString(R.string.com_facebook_internet_permission_error_title), activity.getString(R.string.com_facebook_internet_permission_error_message)));
            return false;
        }
        this.checkedInternetPermission = true;
        return true;
    }

    /* access modifiers changed from: package-private */
    public void tryNextHandler() {
        if (this.currentHandler >= 0) {
            logAuthorizationMethodComplete(getCurrentHandler().getNameForLogging(), "skipped", (String) null, (String) null, getCurrentHandler().methodLoggingExtras);
        }
        while (this.handlersToTry != null && this.currentHandler < this.handlersToTry.length - 1) {
            this.currentHandler++;
            if (tryCurrentHandler()) {
                return;
            }
        }
        if (this.pendingRequest != null) {
            completeWithFailure();
        }
    }

    private void completeWithFailure() {
        complete(Result.createErrorResult(this.pendingRequest, "Login attempt failed.", (String) null));
    }

    private void addLoggingExtra(String key, String value, boolean accumulate) {
        if (this.loggingExtras == null) {
            this.loggingExtras = new HashMap();
        }
        if (this.loggingExtras.containsKey(key) && accumulate) {
            value = this.loggingExtras.get(key) + "," + value;
        }
        this.loggingExtras.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public boolean tryCurrentHandler() {
        boolean tried = false;
        LoginMethodHandler handler = getCurrentHandler();
        if (!handler.needsInternetPermission() || checkInternetPermission()) {
            tried = handler.tryAuthorize(this.pendingRequest);
            if (tried) {
                getLogger().logAuthorizationMethodStart(this.pendingRequest.getAuthId(), handler.getNameForLogging());
            } else {
                getLogger().logAuthorizationMethodNotTried(this.pendingRequest.getAuthId(), handler.getNameForLogging());
                addLoggingExtra("not_tried", handler.getNameForLogging(), true);
            }
        } else {
            addLoggingExtra("no_internet_permission", "1", false);
        }
        return tried;
    }

    /* access modifiers changed from: package-private */
    public void completeAndValidate(Result outcome) {
        if (outcome.token == null || !AccessToken.isCurrentAccessTokenActive()) {
            complete(outcome);
        } else {
            validateSameFbidAndFinish(outcome);
        }
    }

    /* access modifiers changed from: package-private */
    public void complete(Result outcome) {
        LoginMethodHandler handler = getCurrentHandler();
        if (handler != null) {
            logAuthorizationMethodComplete(handler.getNameForLogging(), outcome, handler.methodLoggingExtras);
        }
        if (this.loggingExtras != null) {
            outcome.loggingExtras = this.loggingExtras;
        }
        this.handlersToTry = null;
        this.currentHandler = -1;
        this.pendingRequest = null;
        this.loggingExtras = null;
        notifyOnCompleteListener(outcome);
    }

    /* access modifiers changed from: package-private */
    public OnCompletedListener getOnCompletedListener() {
        return this.onCompletedListener;
    }

    /* access modifiers changed from: package-private */
    public void setOnCompletedListener(OnCompletedListener onCompletedListener2) {
        this.onCompletedListener = onCompletedListener2;
    }

    /* access modifiers changed from: package-private */
    public BackgroundProcessingListener getBackgroundProcessingListener() {
        return this.backgroundProcessingListener;
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundProcessingListener(BackgroundProcessingListener backgroundProcessingListener2) {
        this.backgroundProcessingListener = backgroundProcessingListener2;
    }

    /* access modifiers changed from: package-private */
    public int checkPermission(String permission) {
        return getActivity().checkCallingOrSelfPermission(permission);
    }

    /* access modifiers changed from: package-private */
    public void validateSameFbidAndFinish(Result pendingResult) {
        Result result;
        if (pendingResult.token == null) {
            throw new FacebookException("Can't validate without a token");
        }
        AccessToken previousToken = AccessToken.getCurrentAccessToken();
        AccessToken newToken = pendingResult.token;
        if (!(previousToken == null || newToken == null)) {
            try {
                if (previousToken.getUserId().equals(newToken.getUserId())) {
                    result = Result.createTokenResult(this.pendingRequest, pendingResult.token);
                    complete(result);
                }
            } catch (Exception ex) {
                complete(Result.createErrorResult(this.pendingRequest, "Caught exception", ex.getMessage()));
                return;
            }
        }
        result = Result.createErrorResult(this.pendingRequest, "User logged in as different Facebook user.", (String) null);
        complete(result);
    }

    private static AccessToken createFromTokenWithRefreshedPermissions(AccessToken token, Collection<String> grantedPermissions, Collection<String> declinedPermissions) {
        return new AccessToken(token.getToken(), token.getApplicationId(), token.getUserId(), grantedPermissions, declinedPermissions, token.getSource(), token.getExpires(), token.getLastRefresh());
    }

    private LoginLogger getLogger() {
        if (this.loginLogger == null || !this.loginLogger.getApplicationId().equals(this.pendingRequest.getApplicationId())) {
            this.loginLogger = new LoginLogger(getActivity(), this.pendingRequest.getApplicationId());
        }
        return this.loginLogger;
    }

    private void notifyOnCompleteListener(Result outcome) {
        if (this.onCompletedListener != null) {
            this.onCompletedListener.onCompleted(outcome);
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyBackgroundProcessingStart() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStarted();
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyBackgroundProcessingStop() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStopped();
        }
    }

    private void logAuthorizationMethodComplete(String method, Result result, Map<String, String> loggingExtras2) {
        logAuthorizationMethodComplete(method, result.code.getLoggingValue(), result.errorMessage, result.errorCode, loggingExtras2);
    }

    private void logAuthorizationMethodComplete(String method, String result, String errorMessage, String errorCode, Map<String, String> loggingExtras2) {
        if (this.pendingRequest == null) {
            getLogger().logUnexpectedError("fb_mobile_login_method_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.", method);
        } else {
            getLogger().logAuthorizationMethodComplete(this.pendingRequest.getAuthId(), method, result, errorMessage, errorCode, loggingExtras2);
        }
    }

    static String getE2E() {
        JSONObject e2e = new JSONObject();
        try {
            e2e.put("init", System.currentTimeMillis());
        } catch (JSONException e) {
        }
        return e2e.toString();
    }

    public static class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() {
            public Request createFromParcel(Parcel source) {
                return new Request(source);
            }

            public Request[] newArray(int size) {
                return new Request[size];
            }
        };
        private final String applicationId;
        private final String authId;
        private String authType;
        private final DefaultAudience defaultAudience;
        private String deviceRedirectUriString;
        private boolean isRerequest;
        private final LoginBehavior loginBehavior;
        private Set<String> permissions;

        Request(LoginBehavior loginBehavior2, Set<String> permissions2, DefaultAudience defaultAudience2, String authType2, String applicationId2, String authId2) {
            this.isRerequest = false;
            this.loginBehavior = loginBehavior2;
            this.permissions = permissions2 == null ? new HashSet<>() : permissions2;
            this.defaultAudience = defaultAudience2;
            this.authType = authType2;
            this.applicationId = applicationId2;
            this.authId = authId2;
        }

        /* access modifiers changed from: package-private */
        public Set<String> getPermissions() {
            return this.permissions;
        }

        /* access modifiers changed from: package-private */
        public void setPermissions(Set<String> permissions2) {
            Validate.notNull(permissions2, NativeProtocol.RESULT_ARGS_PERMISSIONS);
            this.permissions = permissions2;
        }

        /* access modifiers changed from: package-private */
        public LoginBehavior getLoginBehavior() {
            return this.loginBehavior;
        }

        /* access modifiers changed from: package-private */
        public DefaultAudience getDefaultAudience() {
            return this.defaultAudience;
        }

        /* access modifiers changed from: package-private */
        public String getApplicationId() {
            return this.applicationId;
        }

        /* access modifiers changed from: package-private */
        public String getAuthId() {
            return this.authId;
        }

        /* access modifiers changed from: package-private */
        public boolean isRerequest() {
            return this.isRerequest;
        }

        /* access modifiers changed from: package-private */
        public void setRerequest(boolean isRerequest2) {
            this.isRerequest = isRerequest2;
        }

        /* access modifiers changed from: package-private */
        public String getDeviceRedirectUriString() {
            return this.deviceRedirectUriString;
        }

        /* access modifiers changed from: package-private */
        public void setDeviceRedirectUriString(String deviceRedirectUriString2) {
            this.deviceRedirectUriString = deviceRedirectUriString2;
        }

        /* access modifiers changed from: package-private */
        public String getAuthType() {
            return this.authType;
        }

        /* access modifiers changed from: package-private */
        public void setAuthType(String authType2) {
            this.authType = authType2;
        }

        /* access modifiers changed from: package-private */
        public boolean hasPublishPermission() {
            for (String permission : this.permissions) {
                if (LoginManager.isPublishPermission(permission)) {
                    return true;
                }
            }
            return false;
        }

        private Request(Parcel parcel) {
            LoginBehavior loginBehavior2;
            boolean z;
            DefaultAudience defaultAudience2 = null;
            this.isRerequest = false;
            String enumValue = parcel.readString();
            if (enumValue != null) {
                loginBehavior2 = LoginBehavior.valueOf(enumValue);
            } else {
                loginBehavior2 = null;
            }
            this.loginBehavior = loginBehavior2;
            ArrayList<String> permissionsList = new ArrayList<>();
            parcel.readStringList(permissionsList);
            this.permissions = new HashSet(permissionsList);
            String enumValue2 = parcel.readString();
            this.defaultAudience = enumValue2 != null ? DefaultAudience.valueOf(enumValue2) : defaultAudience2;
            this.applicationId = parcel.readString();
            this.authId = parcel.readString();
            if (parcel.readByte() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.isRerequest = z;
            this.deviceRedirectUriString = parcel.readString();
            this.authType = parcel.readString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            String str = null;
            dest.writeString(this.loginBehavior != null ? this.loginBehavior.name() : null);
            dest.writeStringList(new ArrayList(this.permissions));
            if (this.defaultAudience != null) {
                str = this.defaultAudience.name();
            }
            dest.writeString(str);
            dest.writeString(this.applicationId);
            dest.writeString(this.authId);
            dest.writeByte((byte) (this.isRerequest ? 1 : 0));
            dest.writeString(this.deviceRedirectUriString);
            dest.writeString(this.authType);
        }
    }

    public static class Result implements Parcelable {
        public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
            public Result createFromParcel(Parcel source) {
                return new Result(source);
            }

            public Result[] newArray(int size) {
                return new Result[size];
            }
        };
        final Code code;
        final String errorCode;
        final String errorMessage;
        public Map<String, String> loggingExtras;
        final Request request;
        final AccessToken token;

        enum Code {
            SUCCESS("success"),
            CANCEL("cancel"),
            ERROR("error");
            
            private final String loggingValue;

            private Code(String loggingValue2) {
                this.loggingValue = loggingValue2;
            }

            /* access modifiers changed from: package-private */
            public String getLoggingValue() {
                return this.loggingValue;
            }
        }

        Result(Request request2, Code code2, AccessToken token2, String errorMessage2, String errorCode2) {
            Validate.notNull(code2, "code");
            this.request = request2;
            this.token = token2;
            this.errorMessage = errorMessage2;
            this.code = code2;
            this.errorCode = errorCode2;
        }

        static Result createTokenResult(Request request2, AccessToken token2) {
            return new Result(request2, Code.SUCCESS, token2, (String) null, (String) null);
        }

        static Result createCancelResult(Request request2, String message) {
            return new Result(request2, Code.CANCEL, (AccessToken) null, message, (String) null);
        }

        static Result createErrorResult(Request request2, String errorType, String errorDescription) {
            return createErrorResult(request2, errorType, errorDescription, (String) null);
        }

        static Result createErrorResult(Request request2, String errorType, String errorDescription, String errorCode2) {
            return new Result(request2, Code.ERROR, (AccessToken) null, TextUtils.join(": ", Utility.asListNoNulls(errorType, errorDescription)), errorCode2);
        }

        private Result(Parcel parcel) {
            this.code = Code.valueOf(parcel.readString());
            this.token = (AccessToken) parcel.readParcelable(AccessToken.class.getClassLoader());
            this.errorMessage = parcel.readString();
            this.errorCode = parcel.readString();
            this.request = (Request) parcel.readParcelable(Request.class.getClassLoader());
            this.loggingExtras = Utility.readStringMapFromParcel(parcel);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code.name());
            dest.writeParcelable(this.token, flags);
            dest.writeString(this.errorMessage);
            dest.writeString(this.errorCode);
            dest.writeParcelable(this.request, flags);
            Utility.writeStringMapToParcel(dest, this.loggingExtras);
        }
    }

    public LoginClient(Parcel source) {
        Object[] o = source.readParcelableArray(LoginMethodHandler.class.getClassLoader());
        this.handlersToTry = new LoginMethodHandler[o.length];
        for (int i = 0; i < o.length; i++) {
            this.handlersToTry[i] = (LoginMethodHandler) o[i];
            this.handlersToTry[i].setLoginClient(this);
        }
        this.currentHandler = source.readInt();
        this.pendingRequest = (Request) source.readParcelable(Request.class.getClassLoader());
        this.loggingExtras = Utility.readStringMapFromParcel(source);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(this.handlersToTry, flags);
        dest.writeInt(this.currentHandler);
        dest.writeParcelable(this.pendingRequest, flags);
        Utility.writeStringMapToParcel(dest, this.loggingExtras);
    }
}
