package com.vk.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import com.vk.sdk.VKServiceActivity;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.dialogs.VKOpenAuthDialog;
import com.vk.sdk.util.VKUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class VKSdk {
    public static final boolean DEBUG = false;
    public static final boolean DEBUG_API_ERRORS = false;
    static final String EXTRA_ERROR_ID = "vk_extra_error_id";
    static final int RESULT_ERROR = 0;
    static final int RESULT_OK = -1;
    public static final String SDK_API_VERSION = "com_vk_sdk_ApiVersion";
    public static final String SDK_APP_ID = "com_vk_sdk_AppId";
    public static final String SDK_TAG = "VK SDK";
    private static final String VK_SDK_APP_ID_PREF_KEY = "VK_SDK_APP_ID_PLEASE_DONT_TOUCH";
    private static final String VK_SDK_APP_VERSION_PREF_KEY = "VK_SDK_APP_VERSION_PLEASE_DONT_TOUCH";
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static ArrayList<String> requestedPermissions;
    private static String sCurrentApiVersion;
    private static int sCurrentAppId = 0;
    private static volatile LoginState sCurrentLoginState;
    private static boolean sIsCustomInitialize = false;
    /* access modifiers changed from: private */
    public static final List<VKAccessTokenTracker> sVKTokenListeners = new CopyOnWriteArrayList();
    private static VKSdk vkSdk = null;
    private final Context applicationContext;

    public enum LoginState {
        Unknown,
        LoggedOut,
        Pending,
        LoggedIn
    }

    private VKSdk(Context applicationContext2) {
        this.applicationContext = applicationContext2;
    }

    static void addVKTokenTracker(VKAccessTokenTracker vkAccessTokenTracker) {
        sVKTokenListeners.add(vkAccessTokenTracker);
    }

    static void removeVKTokenTracker(VKAccessTokenTracker vkAccessTokenTracker) {
        sVKTokenListeners.remove(vkAccessTokenTracker);
    }

    static void notifyVKTokenChanged(final VKAccessToken oldToken, final VKAccessToken newToken) {
        handler.post(new Runnable() {
            public void run() {
                for (VKAccessTokenTracker listener : VKSdk.sVKTokenListeners) {
                    listener.onVKAccessTokenChanged(oldToken, newToken);
                }
            }
        });
    }

    public static VKSdk customInitialize(Context ctx, int appId, String apiVer) {
        if (appId == 0) {
            appId = getIntFromPref(ctx, VK_SDK_APP_ID_PREF_KEY);
        }
        if (TextUtils.isEmpty(apiVer)) {
            apiVer = getStringFromPref(ctx, VK_SDK_APP_VERSION_PREF_KEY, "5.21");
        }
        if (appId == 0) {
            throw new RuntimeException("your_app_id is 0");
        }
        sIsCustomInitialize = true;
        VKSdk vkSdk2 = initialize(ctx, appId, apiVer);
        if (sCurrentAppId != 0) {
            storeIntToPref(ctx, VK_SDK_APP_ID_PREF_KEY, sCurrentAppId);
        }
        if (sCurrentApiVersion != null) {
            storeStringToPref(ctx, VK_SDK_APP_VERSION_PREF_KEY, sCurrentApiVersion);
        }
        return vkSdk2;
    }

    public static boolean isCustomInitialize() {
        return sIsCustomInitialize;
    }

    public static VKSdk initialize(Context ctx) {
        if (sCurrentAppId != 0) {
            return vkSdk;
        }
        if (!(ctx instanceof Application)) {
            if (ctx == null) {
                throw new NullPointerException("Application context cannot be null");
            }
            throw new RuntimeException("VKSdk.initialize(Context) must be call from Application#onCreate()");
        } else if (!hasInStack(Application.class, "onCreate")) {
            throw new RuntimeException("VKSdk.initialize(Context) must be call from Application#onCreate()");
        } else {
            int appId = getIntResByName(ctx, "com_vk_sdk_AppId").intValue();
            if (appId != 0) {
                return initialize(ctx, appId, getStringResByName(ctx, SDK_API_VERSION, "5.21"));
            }
            throw new RuntimeException("String <integer name=\"com_vk_sdk_AppId\">your_app_id</integer> did not find in your resources.xml");
        }
    }

    private static synchronized VKSdk initialize(Context applicationContext2, int appId, String appVer) {
        VKSdk vKSdk;
        synchronized (VKSdk.class) {
            if (sCurrentAppId == 0) {
                vkSdk = new VKSdk(applicationContext2);
                sCurrentAppId = appId;
                if (TextUtils.isEmpty(appVer)) {
                    appVer = "5.21";
                }
                sCurrentApiVersion = appVer;
                sCurrentLoginState = LoginState.Unknown;
                wakeUpSession(applicationContext2);
            }
            vKSdk = vkSdk;
        }
        return vKSdk;
    }

    private static String getStringResByName(Context ctx, String aString, String def) {
        try {
            String ret = ctx.getString(ctx.getResources().getIdentifier(aString, "string", ctx.getPackageName()));
            return TextUtils.isEmpty(ret) ? def : ret;
        } catch (Exception e) {
            return def;
        }
    }

    private static Integer getIntResByName(Context ctx, String aString) {
        try {
            return Integer.valueOf(ctx.getResources().getInteger(ctx.getResources().getIdentifier(aString, "integer", ctx.getPackageName())));
        } catch (Exception e) {
            return 0;
        }
    }

    public static void login(@NonNull Activity activity, String... scope) {
        ArrayList<String> preparingScopeList = preparingScopeList(scope);
        requestedPermissions = preparingScopeList;
        VKServiceActivity.startLoginActivity(activity, preparingScopeList);
    }

    public static void login(@NonNull Fragment fragment, String... scope) {
        ArrayList<String> preparingScopeList = preparingScopeList(scope);
        requestedPermissions = preparingScopeList;
        VKServiceActivity.startLoginActivity(fragment, preparingScopeList);
    }

    public static boolean onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull VKCallback<VKAccessToken> vkCallback) {
        long j = 0;
        if (requestCode != VKServiceActivity.VKServiceType.Authorization.getOuterCode()) {
            return false;
        }
        if (resultCode == -1) {
            vkCallback.onResult(VKAccessToken.currentToken());
        } else if (resultCode == 0) {
            if (data != null) {
                j = data.getLongExtra(EXTRA_ERROR_ID, 0);
            }
            vkCallback.onError((VKError) VKObject.getRegisteredObject(j));
        }
        return true;
    }

    @NonNull
    private static ArrayList<String> preparingScopeList(String... scope) {
        if (scope == null) {
            scope = new String[0];
        }
        ArrayList<String> scopeList = new ArrayList<>(Arrays.asList(scope));
        if (!scopeList.contains("offline")) {
            scopeList.add("offline");
        }
        return scopeList;
    }

    static int getsCurrentAppId() {
        return sCurrentAppId;
    }

    static boolean processActivityResult(@NonNull Context ctx, int resultCode, @Nullable Intent result, @Nullable VKCallback<VKAccessToken> callback) {
        if (resultCode != -1 || result == null) {
            if (callback != null) {
                callback.onError(new VKError(-102));
            }
            updateLoginState(ctx);
            return false;
        }
        Map<String, String> tokenParams = null;
        if (result.hasExtra(VKOpenAuthDialog.VK_EXTRA_TOKEN_DATA)) {
            tokenParams = VKUtil.explodeQueryString(result.getStringExtra(VKOpenAuthDialog.VK_EXTRA_TOKEN_DATA));
        } else if (result.getExtras() != null) {
            tokenParams = new HashMap<>();
            for (String key : result.getExtras().keySet()) {
                tokenParams.put(key, String.valueOf(result.getExtras().get(key)));
            }
        }
        CheckTokenResult tokenResult = checkAndSetToken(ctx, tokenParams);
        if (tokenResult.error != null && callback != null) {
            callback.onError(tokenResult.error);
        } else if (tokenResult.token != null) {
            if (tokenResult.oldToken != null) {
                VKRequest validationRequest = VKRequest.getRegisteredRequest(result.getLongExtra(VKOpenAuthDialog.VK_EXTRA_VALIDATION_REQUEST, 0));
                if (validationRequest != null) {
                    validationRequest.unregisterObject();
                    validationRequest.repeat();
                }
            } else {
                trackVisitor((VKRequest.VKRequestListener) null);
            }
            if (callback != null) {
                callback.onResult(tokenResult.token);
            }
        }
        requestedPermissions = null;
        updateLoginState(ctx);
        return true;
    }

    private static CheckTokenResult checkAndSetToken(@NonNull Context ctx, @Nullable Map<String, String> tokenParams) {
        if (!(tokenParams == null || requestedPermissions == null)) {
            tokenParams.put("scope", TextUtils.join(",", requestedPermissions));
        }
        VKAccessToken token = VKAccessToken.tokenFromParameters(tokenParams);
        if (token != null && token.accessToken != null) {
            VKAccessToken old = VKAccessToken.currentToken();
            if (old != null) {
                VKAccessToken newToken = old.copyWithToken(token);
                VKAccessToken.replaceToken(ctx, old.copyWithToken(token));
                notifyVKTokenChanged(old, newToken);
                return new CheckTokenResult(old, token);
            }
            VKAccessToken.replaceToken(ctx, token);
            notifyVKTokenChanged(old, token);
            return new CheckTokenResult(token);
        } else if (tokenParams == null || !tokenParams.containsKey("success")) {
            VKError error = new VKError(tokenParams);
            if (!(error.errorMessage == null && error.errorReason == null)) {
                error = new VKError(-102);
            }
            return new CheckTokenResult(error);
        } else {
            VKAccessToken currentToken = VKAccessToken.currentToken();
            if (token == null) {
                token = VKAccessToken.currentToken();
            }
            return new CheckTokenResult(currentToken, token);
        }
    }

    public static VKAccessToken getAccessToken() {
        return VKAccessToken.currentToken();
    }

    public static boolean wakeUpSession(@NonNull Context context) {
        return wakeUpSession(context, (VKCallback<LoginState>) null);
    }

    public static boolean wakeUpSession(@NonNull final Context context, final VKCallback<LoginState> loginStateCallback) {
        final Context appContext = context.getApplicationContext();
        VKUIHelper.setApplicationContext(appContext);
        VKAccessToken token = VKAccessToken.currentToken();
        if (token == null || token.accessToken == null || token.isExpired()) {
            updateLoginState(context, loginStateCallback);
            return false;
        }
        forceLoginState(LoginState.Pending, loginStateCallback);
        trackVisitor(new VKRequest.VKRequestListener() {
            public void onComplete(VKResponse response) {
                VKSdk.updateLoginState(context, loginStateCallback);
            }

            public void onError(VKError error) {
                if (!(error == null || error.apiError == null || error.apiError.errorCode != 5)) {
                    VKSdk.onAccessTokenIsInvalid(appContext);
                }
                VKSdk.updateLoginState(context, loginStateCallback);
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public static void onAccessTokenIsInvalid(@NonNull Context ctx) {
        VKAccessToken old = VKAccessToken.replaceToken(ctx, (VKAccessToken) null);
        if (old != null) {
            notifyVKTokenChanged(old, (VKAccessToken) null);
        }
    }

    public static void notifySdkAboutApiError(VKError apiError) {
        if (apiError.errorCode == 5) {
            onAccessTokenIsInvalid(VKUIHelper.getApplicationContext());
        }
    }

    @SuppressLint({"NewApi"})
    public static void logout() {
        Context context = VKUIHelper.getApplicationContext();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(context);
            CookieManager.getInstance().removeAllCookie();
        } else {
            CookieManager.getInstance().removeAllCookies((ValueCallback) null);
        }
        VKAccessToken.replaceToken(VKUIHelper.getApplicationContext(), (VKAccessToken) null);
        updateLoginState(context);
    }

    public static boolean isLoggedIn() {
        VKAccessToken token = VKAccessToken.currentToken();
        return token != null && !token.isExpired();
    }

    private static void trackVisitor(VKRequest.VKRequestListener l) {
        VKRequest r = new VKRequest("stats.trackVisitor");
        r.attempts = 0;
        r.executeWithListener(l);
    }

    private static void updateLoginState(Context context) {
        updateLoginState(context, (VKCallback<LoginState>) null);
    }

    /* access modifiers changed from: private */
    public static void updateLoginState(Context context, VKCallback<LoginState> callback) {
        VKUIHelper.setApplicationContext(context);
        if (VKAccessToken.currentToken() != null) {
            forceLoginState(LoginState.LoggedIn, callback);
        } else {
            forceLoginState(LoginState.LoggedOut, callback);
        }
    }

    private static void forceLoginState(LoginState newState, VKCallback<LoginState> callback) {
        sCurrentLoginState = newState;
        if (callback != null) {
            callback.onResult(sCurrentLoginState);
        }
    }

    public static String getApiVersion() {
        return sCurrentApiVersion;
    }

    private static class CheckTokenResult {
        public VKError error;
        public VKAccessToken oldToken;
        public VKAccessToken token;

        public CheckTokenResult(VKAccessToken token2) {
            this.token = token2;
        }

        public CheckTokenResult(VKAccessToken oldToken2, VKAccessToken newToken) {
            this.token = newToken;
            this.oldToken = oldToken2;
        }

        public CheckTokenResult(VKError err) {
            this.error = err;
        }
    }

    private static boolean hasInStack(@NonNull Class<?> clazz, @NonNull String method) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int i = stackTrace.length - 2;
        while (i >= 0) {
            StackTraceElement element = stackTrace[i];
            try {
                Class.forName(element.getClassName()).asSubclass(clazz);
                if (method.equals(element.getMethodName())) {
                    return true;
                }
                i--;
            } catch (ClassCastException | ClassNotFoundException e) {
            }
        }
        return false;
    }

    private static int getIntFromPref(@NonNull Context ctx, @NonNull String key) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(key, 0);
    }

    private static void storeIntToPref(@NonNull Context ctx, @NonNull String key, int value) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.putInt(key, value);
        edit.apply();
    }

    private static String getStringFromPref(@NonNull Context ctx, @NonNull String key, String def) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, def);
    }

    private static void storeStringToPref(@NonNull Context ctx, @NonNull String key, String value) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.putString(key, value);
        edit.apply();
    }
}
