package com.facebook.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient;
import com.facebook.share.internal.ShareConstants;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoginManager {
    private static final String EXPRESS_LOGIN_ALLOWED = "express_login_allowed";
    private static final String MANAGE_PERMISSION_PREFIX = "manage";
    private static final Set<String> OTHER_PUBLISH_PERMISSIONS = getOtherPublishPermissions();
    private static final String PREFERENCE_LOGIN_MANAGER = "com.facebook.loginManager";
    private static final String PUBLISH_PERMISSION_PREFIX = "publish";
    private static volatile LoginManager instance;
    private String authType = ServerProtocol.DIALOG_REREQUEST_AUTH_TYPE;
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
    private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
    private final SharedPreferences sharedPreferences;

    LoginManager() {
        Validate.sdkInitialized();
        this.sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(PREFERENCE_LOGIN_MANAGER, 0);
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public void resolveError(Activity activity, GraphResponse response) {
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequestFromResponse(response));
    }

    public void resolveError(Fragment fragment, GraphResponse response) {
        resolveError(new FragmentWrapper(fragment), response);
    }

    public void resolveError(android.app.Fragment fragment, GraphResponse response) {
        resolveError(new FragmentWrapper(fragment), response);
    }

    private void resolveError(FragmentWrapper fragment, GraphResponse response) {
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequestFromResponse(response));
    }

    private LoginClient.Request createLoginRequestFromResponse(GraphResponse response) {
        Validate.notNull(response, "response");
        AccessToken failedToken = response.getRequest().getAccessToken();
        return createLoginRequest(failedToken != null ? failedToken.getPermissions() : null);
    }

    public void registerCallback(CallbackManager callbackManager, final FacebookCallback<LoginResult> callback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl) callbackManager).registerCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int resultCode, Intent data) {
                return LoginManager.this.onActivityResult(resultCode, data, callback);
            }
        });
    }

    public void unregisterCallback(CallbackManager callbackManager) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl) callbackManager).unregisterCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode());
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int resultCode, Intent data) {
        return onActivityResult(resultCode, data, (FacebookCallback<LoginResult>) null);
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int resultCode, Intent data, FacebookCallback<LoginResult> callback) {
        FacebookException exception = null;
        AccessToken newToken = null;
        LoginClient.Result.Code code = LoginClient.Result.Code.ERROR;
        Map<String, String> loggingExtras = null;
        LoginClient.Request originalRequest = null;
        boolean isCanceled = false;
        if (data != null) {
            LoginClient.Result result = (LoginClient.Result) data.getParcelableExtra("com.facebook.LoginFragment:Result");
            if (result != null) {
                originalRequest = result.request;
                code = result.code;
                if (resultCode == -1) {
                    if (result.code == LoginClient.Result.Code.SUCCESS) {
                        newToken = result.token;
                    } else {
                        exception = new FacebookAuthorizationException(result.errorMessage);
                    }
                } else if (resultCode == 0) {
                    isCanceled = true;
                }
                loggingExtras = result.loggingExtras;
            }
        } else if (resultCode == 0) {
            isCanceled = true;
            code = LoginClient.Result.Code.CANCEL;
        }
        if (exception == null && newToken == null && !isCanceled) {
            exception = new FacebookException("Unexpected call to LoginManager.onActivityResult");
        }
        logCompleteLogin((Context) null, code, loggingExtras, exception, true, originalRequest);
        finishLogin(newToken, originalRequest, exception, isCanceled, callback);
        return true;
    }

    public LoginBehavior getLoginBehavior() {
        return this.loginBehavior;
    }

    public LoginManager setLoginBehavior(LoginBehavior loginBehavior2) {
        this.loginBehavior = loginBehavior2;
        return this;
    }

    public DefaultAudience getDefaultAudience() {
        return this.defaultAudience;
    }

    public LoginManager setDefaultAudience(DefaultAudience defaultAudience2) {
        this.defaultAudience = defaultAudience2;
        return this;
    }

    public String getAuthType() {
        return this.authType;
    }

    public LoginManager setAuthType(String authType2) {
        this.authType = authType2;
        return this;
    }

    public void logOut() {
        AccessToken.setCurrentAccessToken((AccessToken) null);
        Profile.setCurrentProfile((Profile) null);
        setExpressLoginStatus(false);
    }

    public void retrieveLoginStatus(Context context, LoginStatusCallback responseCallback) {
        retrieveLoginStatus(context, Constants.ACTIVE_THREAD_WATCHDOG, responseCallback);
    }

    public void retrieveLoginStatus(Context context, long toastDurationMs, LoginStatusCallback responseCallback) {
        retrieveLoginStatusImpl(context, responseCallback, toastDurationMs);
    }

    public void logInWithReadPermissions(Fragment fragment, Collection<String> permissions) {
        logInWithReadPermissions(new FragmentWrapper(fragment), permissions);
    }

    public void logInWithReadPermissions(android.app.Fragment fragment, Collection<String> permissions) {
        logInWithReadPermissions(new FragmentWrapper(fragment), permissions);
    }

    private void logInWithReadPermissions(FragmentWrapper fragment, Collection<String> permissions) {
        validateReadPermissions(permissions);
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequest(permissions));
    }

    public void logInWithReadPermissions(Activity activity, Collection<String> permissions) {
        validateReadPermissions(permissions);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(permissions));
    }

    public void logInWithPublishPermissions(Fragment fragment, Collection<String> permissions) {
        logInWithPublishPermissions(new FragmentWrapper(fragment), permissions);
    }

    public void logInWithPublishPermissions(android.app.Fragment fragment, Collection<String> permissions) {
        logInWithPublishPermissions(new FragmentWrapper(fragment), permissions);
    }

    private void logInWithPublishPermissions(FragmentWrapper fragment, Collection<String> permissions) {
        validatePublishPermissions(permissions);
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequest(permissions));
    }

    public void logInWithPublishPermissions(Activity activity, Collection<String> permissions) {
        validatePublishPermissions(permissions);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(permissions));
    }

    private void validateReadPermissions(Collection<String> permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (isPublishPermission(permission)) {
                    throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", new Object[]{permission}));
                }
            }
        }
    }

    private void validatePublishPermissions(Collection<String> permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (!isPublishPermission(permission)) {
                    throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", new Object[]{permission}));
                }
            }
        }
    }

    static boolean isPublishPermission(String permission) {
        return permission != null && (permission.startsWith(PUBLISH_PERMISSION_PREFIX) || permission.startsWith(MANAGE_PERMISSION_PREFIX) || OTHER_PUBLISH_PERMISSIONS.contains(permission));
    }

    private static Set<String> getOtherPublishPermissions() {
        return Collections.unmodifiableSet(new HashSet<String>() {
            {
                add("ads_management");
                add("create_event");
                add("rsvp_event");
            }
        });
    }

    /* access modifiers changed from: protected */
    public LoginClient.Request createLoginRequest(Collection<String> permissions) {
        LoginClient.Request request = new LoginClient.Request(this.loginBehavior, Collections.unmodifiableSet(permissions != null ? new HashSet(permissions) : new HashSet()), this.defaultAudience, this.authType, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
        request.setRerequest(AccessToken.isCurrentAccessTokenActive());
        return request;
    }

    private void startLogin(StartActivityDelegate startActivityDelegate, LoginClient.Request request) throws FacebookException {
        logStartLogin(startActivityDelegate.getActivityContext(), request);
        CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int resultCode, Intent data) {
                return LoginManager.this.onActivityResult(resultCode, data);
            }
        });
        if (!tryFacebookActivity(startActivityDelegate, request)) {
            FacebookException exception = new FacebookException("Log in attempt failed: FacebookActivity could not be started. Please make sure you added FacebookActivity to the AndroidManifest.");
            logCompleteLogin(startActivityDelegate.getActivityContext(), LoginClient.Result.Code.ERROR, (Map<String, String>) null, exception, false, request);
            throw exception;
        }
    }

    private void logStartLogin(Context context, LoginClient.Request loginRequest) {
        LoginLogger loginLogger = LoginLoggerHolder.getLogger(context);
        if (loginLogger != null && loginRequest != null) {
            loginLogger.logStartLogin(loginRequest);
        }
    }

    private void logCompleteLogin(Context context, LoginClient.Result.Code result, Map<String, String> resultExtras, Exception exception, boolean wasLoginActivityTried, LoginClient.Request request) {
        LoginLogger loginLogger = LoginLoggerHolder.getLogger(context);
        if (loginLogger != null) {
            if (request == null) {
                loginLogger.logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
                return;
            }
            HashMap<String, String> pendingLoggingExtras = new HashMap<>();
            pendingLoggingExtras.put("try_login_activity", wasLoginActivityTried ? "1" : "0");
            loginLogger.logCompleteLogin(request.getAuthId(), pendingLoggingExtras, result, resultExtras, exception);
        }
    }

    private boolean tryFacebookActivity(StartActivityDelegate startActivityDelegate, LoginClient.Request request) {
        Intent intent = getFacebookActivityIntent(request);
        if (!resolveIntent(intent)) {
            return false;
        }
        try {
            startActivityDelegate.startActivityForResult(intent, LoginClient.getLoginRequestCode());
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private boolean resolveIntent(Intent intent) {
        if (FacebookSdk.getApplicationContext().getPackageManager().resolveActivity(intent, 0) != null) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public Intent getFacebookActivityIntent(LoginClient.Request request) {
        Intent intent = new Intent();
        intent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        intent.setAction(request.getLoginBehavior().toString());
        Bundle extras = new Bundle();
        extras.putParcelable(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, request);
        intent.putExtra("com.facebook.LoginFragment:Request", extras);
        return intent;
    }

    static LoginResult computeLoginResult(LoginClient.Request request, AccessToken newToken) {
        Set<String> requestedPermissions = request.getPermissions();
        Set<String> grantedPermissions = new HashSet<>(newToken.getPermissions());
        if (request.isRerequest()) {
            grantedPermissions.retainAll(requestedPermissions);
        }
        Set<String> deniedPermissions = new HashSet<>(requestedPermissions);
        deniedPermissions.removeAll(grantedPermissions);
        return new LoginResult(newToken, grantedPermissions, deniedPermissions);
    }

    private void finishLogin(AccessToken newToken, LoginClient.Request origRequest, FacebookException exception, boolean isCanceled, FacebookCallback<LoginResult> callback) {
        if (newToken != null) {
            AccessToken.setCurrentAccessToken(newToken);
            Profile.fetchProfileForCurrentAccessToken();
        }
        if (callback != null) {
            LoginResult loginResult = newToken != null ? computeLoginResult(origRequest, newToken) : null;
            if (isCanceled || (loginResult != null && loginResult.getRecentlyGrantedPermissions().size() == 0)) {
                callback.onCancel();
            } else if (exception != null) {
                callback.onError(exception);
            } else if (newToken != null) {
                setExpressLoginStatus(true);
                callback.onSuccess(loginResult);
            }
        }
    }

    private void retrieveLoginStatusImpl(Context context, LoginStatusCallback responseCallback, long toastDurationMs) {
        String applicationId = FacebookSdk.getApplicationId();
        String loggerRef = UUID.randomUUID().toString();
        final LoginLogger logger = new LoginLogger(context, applicationId);
        if (!isExpressLoginAllowed()) {
            logger.logLoginStatusFailure(loggerRef);
            responseCallback.onFailure();
            return;
        }
        LoginStatusClient client = new LoginStatusClient(context, applicationId, loggerRef, FacebookSdk.getGraphApiVersion(), toastDurationMs);
        final String str = loggerRef;
        final LoginStatusCallback loginStatusCallback = responseCallback;
        final String str2 = applicationId;
        client.setCompletedListener(new PlatformServiceClient.CompletedListener() {
            public void completed(Bundle result) {
                if (result != null) {
                    String errorType = result.getString(NativeProtocol.STATUS_ERROR_TYPE);
                    String errorDescription = result.getString(NativeProtocol.STATUS_ERROR_DESCRIPTION);
                    if (errorType != null) {
                        LoginManager.handleLoginStatusError(errorType, errorDescription, str, logger, loginStatusCallback);
                        return;
                    }
                    String token = result.getString(NativeProtocol.EXTRA_ACCESS_TOKEN);
                    long expires = result.getLong(NativeProtocol.EXTRA_EXPIRES_SECONDS_SINCE_EPOCH);
                    ArrayList<String> permissions = result.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
                    String signedRequest = result.getString(NativeProtocol.RESULT_ARGS_SIGNED_REQUEST);
                    String userId = null;
                    if (!Utility.isNullOrEmpty(signedRequest)) {
                        userId = LoginMethodHandler.getUserIDFromSignedRequest(signedRequest);
                    }
                    if (Utility.isNullOrEmpty(token) || permissions == null || permissions.isEmpty() || Utility.isNullOrEmpty(userId)) {
                        logger.logLoginStatusFailure(str);
                        loginStatusCallback.onFailure();
                        return;
                    }
                    AccessToken accessToken = new AccessToken(token, str2, userId, permissions, (Collection<String>) null, (AccessTokenSource) null, new Date(expires), (Date) null);
                    AccessToken.setCurrentAccessToken(accessToken);
                    Profile profile = LoginManager.getProfileFromBundle(result);
                    if (profile != null) {
                        Profile.setCurrentProfile(profile);
                    } else {
                        Profile.fetchProfileForCurrentAccessToken();
                    }
                    logger.logLoginStatusSuccess(str);
                    loginStatusCallback.onCompleted(accessToken);
                    return;
                }
                logger.logLoginStatusFailure(str);
                loginStatusCallback.onFailure();
            }
        });
        logger.logLoginStatusStart(loggerRef);
        if (!client.start()) {
            logger.logLoginStatusFailure(loggerRef);
            responseCallback.onFailure();
        }
    }

    private void setExpressLoginStatus(boolean isExpressLoginAllowed) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(EXPRESS_LOGIN_ALLOWED, isExpressLoginAllowed);
        editor.apply();
    }

    private boolean isExpressLoginAllowed() {
        return this.sharedPreferences.getBoolean(EXPRESS_LOGIN_ALLOWED, true);
    }

    /* access modifiers changed from: private */
    @Nullable
    public static Profile getProfileFromBundle(Bundle result) {
        String name = result.getString(NativeProtocol.EXTRA_ARGS_PROFILE_NAME);
        String firstName = result.getString(NativeProtocol.EXTRA_ARGS_PROFILE_FIRST_NAME);
        String middleName = result.getString(NativeProtocol.EXTRA_ARGS_PROFILE_MIDDLE_NAME);
        String lastName = result.getString(NativeProtocol.EXTRA_ARGS_PROFILE_LAST_NAME);
        String link = result.getString(NativeProtocol.EXTRA_ARGS_PROFILE_LINK);
        String appScopedUserId = result.getString(NativeProtocol.EXTRA_ARGS_PROFILE_USER_ID);
        if (name == null || firstName == null || middleName == null || lastName == null || link == null || appScopedUserId == null) {
            return null;
        }
        return new Profile(appScopedUserId, firstName, middleName, lastName, name, Uri.parse(link));
    }

    /* access modifiers changed from: private */
    public static void handleLoginStatusError(String errorType, String errorDescription, String loggerRef, LoginLogger logger, LoginStatusCallback responseCallback) {
        Exception exception = new FacebookException(errorType + ": " + errorDescription);
        logger.logLoginStatusError(loggerRef, exception);
        responseCallback.onError(exception);
    }

    private static class ActivityStartActivityDelegate implements StartActivityDelegate {
        private final Activity activity;

        ActivityStartActivityDelegate(Activity activity2) {
            Validate.notNull(activity2, "activity");
            this.activity = activity2;
        }

        public void startActivityForResult(Intent intent, int requestCode) {
            this.activity.startActivityForResult(intent, requestCode);
        }

        public Activity getActivityContext() {
            return this.activity;
        }
    }

    private static class FragmentStartActivityDelegate implements StartActivityDelegate {
        private final FragmentWrapper fragment;

        FragmentStartActivityDelegate(FragmentWrapper fragment2) {
            Validate.notNull(fragment2, "fragment");
            this.fragment = fragment2;
        }

        public void startActivityForResult(Intent intent, int requestCode) {
            this.fragment.startActivityForResult(intent, requestCode);
        }

        public Activity getActivityContext() {
            return this.fragment.getActivity();
        }
    }

    private static class LoginLoggerHolder {
        private static LoginLogger logger;

        private LoginLoggerHolder() {
        }

        /* access modifiers changed from: private */
        public static synchronized LoginLogger getLogger(Context context) {
            LoginLogger loginLogger;
            synchronized (LoginLoggerHolder.class) {
                if (context == null) {
                    context = FacebookSdk.getApplicationContext();
                }
                if (context == null) {
                    loginLogger = null;
                } else {
                    if (logger == null) {
                        logger = new LoginLogger(context, FacebookSdk.getApplicationId());
                    }
                    loginLogger = logger;
                }
            }
            return loginLogger;
        }
    }
}
