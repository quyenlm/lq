package com.beetalk.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.WebView;
import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.PaymentChannelStorage;
import com.beetalk.sdk.cache.RedeemCache;
import com.beetalk.sdk.cache.SharedPrefStorage;
import com.beetalk.sdk.cache.StorageCache;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.exception.InvalidOperationException;
import com.beetalk.sdk.facebook.FBClient;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.helper.Validate;
import com.beetalk.sdk.networking.CommonEventLoop;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.vk.VKUtils;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.garena.pay.android.GGErrorCode;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.vk.sdk.VKSdk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GGLoginSession {
    private static final String BIND_CACHE_KEY = "com.garena.msdk.bind_token";
    private static volatile Context applicationContext;
    private static GGLoginSession mBindSessionInstance;
    private static GGLoginSession mLoginSessionInstance;
    private String applicationId;
    private String applicationKey;
    /* access modifiers changed from: private */
    public StorageCache cache;
    /* access modifiers changed from: private */
    public int errorCode;
    /* access modifiers changed from: private */
    public final Handler handler;
    private final Object lock;
    private boolean mIsBindSuccess;
    private AuthMode mode;
    private int requestCode;
    /* access modifiers changed from: private */
    public final List<SessionCallback> sessionCallbacks;
    private SessionProvider sessionProvider;
    /* access modifiers changed from: private */
    public SessionStatus sessionStatus;
    private Integer sourceId;
    private boolean thirdPartyPayment;
    /* access modifiers changed from: private */
    public AuthToken tokenValue;

    public interface ClearSessionCallback {
        void onComplete();
    }

    public interface OnTokenResultListener {
        void onTokenResult(AuthToken authToken, GGErrorCode gGErrorCode);
    }

    public interface SessionCallback {
        void onSessionProcessed(GGLoginSession gGLoginSession, Exception exc);
    }

    /* access modifiers changed from: package-private */
    public void setIsBindSuccess(boolean isBindSuccess) {
        this.mIsBindSuccess = isBindSuccess;
    }

    public boolean isExternalPaymentEnabled() {
        return this.thirdPartyPayment;
    }

    private GGLoginSession(Context context, String applicationId2, String appKey, String redirectURI, AuthMode mode2, int requestCode2, SessionProvider sessionProvider2, StorageCache cache2) {
        this.lock = new Object();
        this.sourceId = SDKConstants.CHANNEL_SOURCE.GOOGLE_PLAY;
        this.errorCode = 0;
        this.thirdPartyPayment = true;
        if (context != null && TextUtils.isEmpty(applicationId2)) {
            applicationId2 = Helper.getMetaDataAppId(context);
        }
        this.cache = cache2 == null ? new SharedPrefStorage(context) : cache2;
        AuthToken token = this.cache.getToken();
        if (token != null && SessionProvider.isEqualToSessionProvider(sessionProvider2, token.getTokenProvider()) && token.hasAllFields()) {
            this.tokenValue = token;
        }
        Validate.notNull(applicationId2, "applicationId");
        this.applicationId = applicationId2;
        this.applicationKey = appKey;
        this.mode = mode2;
        this.handler = new Handler(Looper.getMainLooper());
        this.sessionCallbacks = new ArrayList();
        this.requestCode = requestCode2;
        this.sessionProvider = sessionProvider2;
        this.sessionStatus = this.tokenValue != null ? SessionStatus.TOKEN_AVAILABLE : SessionStatus.CREATED;
        initContext(context);
        BBLogger.init(applicationContext);
        Integer source = Helper.getMetaDataSourceId(applicationContext);
        Boolean payExternalEnabled = Helper.getMetaDataThirdParty(applicationContext);
        if (payExternalEnabled != null) {
            this.thirdPartyPayment = payExternalEnabled.booleanValue();
        }
        if (source != null) {
            this.sourceId = source;
        }
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public static void initContext(Context context) {
        if (context != null && applicationContext == null) {
            Context appContext = context.getApplicationContext();
            if (appContext == null) {
                appContext = context;
            }
            applicationContext = appContext;
        }
    }

    public String getApplicationKey() {
        return this.applicationKey;
    }

    public AuthMode getMode() {
        return this.mode;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public static synchronized GGLoginSession getCurrentSession() {
        GGLoginSession gGLoginSession;
        synchronized (GGLoginSession.class) {
            gGLoginSession = mLoginSessionInstance;
        }
        return gGLoginSession;
    }

    public static synchronized void setCurrentSession(GGLoginSession session) {
        synchronized (GGLoginSession.class) {
            mLoginSessionInstance = session;
        }
    }

    public static synchronized GGLoginSession getCurrentBindSession() {
        GGLoginSession gGLoginSession;
        synchronized (GGLoginSession.class) {
            gGLoginSession = mBindSessionInstance;
        }
        return gGLoginSession;
    }

    public static synchronized void setCurrentBindSession(GGLoginSession session) {
        synchronized (GGLoginSession.class) {
            mBindSessionInstance = session;
        }
    }

    public static GGLoginSession obtainActiveSession(Activity activity, boolean doLogin, SessionCallback callback) {
        return obtainActiveLoginSession(activity, doLogin, callback, new AuthRequest(activity, callback));
    }

    public static GGLoginSession obtainActiveBindSession(Activity activity, SessionCallback callback) {
        return obtainActiveBindSession(activity, true, callback, new AuthRequest(activity, callback, SDKConstants.OBTAIN_BIND_SESSION_REQUEST_CODE.intValue(), false, ""));
    }

    private static GGLoginSession obtainActiveSession(Activity activity, boolean doLogin, SessionCallback callback, AuthRequest request, GGLoginSession loginSession) {
        Exception exception;
        if (isSessionValid(loginSession)) {
            switch (loginSession.sessionStatus) {
                case OPENING:
                    if (loginSession.sessionStatus == SessionStatus.OPENING) {
                        exception = new InvalidOperationException("Session is awaiting fulfillment. Please destroy before requesting for a new Session");
                    } else {
                        exception = null;
                    }
                    callback.onSessionProcessed(loginSession, exception);
                    break;
                case TOKEN_AVAILABLE:
                    loginSession.inspectToken(callback);
                    return loginSession;
            }
        } else if (doLogin) {
            GGLoginSession session = loginSession;
            if (session == null) {
                session = new Builder(activity).build();
            }
            synchronized (session.lock) {
                if (session.sessionStatus == SessionStatus.CREATED) {
                    session.sessionStatus = SessionStatus.OPENING;
                }
                if (callback != null) {
                    session.sessionCallbacks.add(callback);
                }
                session.publishStatusChange(SessionStatus.CREATED, SessionStatus.OPENING, (Exception) null);
            }
            if (session.sessionStatus == SessionStatus.OPENING) {
                request.setSession(session);
                session.commenceAuth(request);
            }
            return session;
        }
        return null;
    }

    private static GGLoginSession obtainActiveLoginSession(Activity activity, boolean doLogin, SessionCallback callback, AuthRequest request) {
        GGLoginSession loginSession = getCurrentSession();
        if (loginSession == null) {
            loginSession = new Builder(activity).build();
            setCurrentSession(loginSession);
        }
        obtainActiveSession(activity, doLogin, callback, request, loginSession);
        return getCurrentSession();
    }

    private static GGLoginSession obtainActiveBindSession(Activity activity, boolean doLogin, SessionCallback callback, AuthRequest request) {
        GGLoginSession loginSessionForBind = getCurrentBindSession();
        if (loginSessionForBind != null) {
            loginSessionForBind.setCache(new SharedPrefStorage(activity, BIND_CACHE_KEY));
        } else {
            loginSessionForBind = new Builder(activity).build();
            loginSessionForBind.setCache(new SharedPrefStorage(activity, BIND_CACHE_KEY));
            setCurrentBindSession(loginSessionForBind);
        }
        obtainActiveSession(activity, doLogin, callback, request, loginSessionForBind);
        return getCurrentBindSession();
    }

    public static GGLoginSession forceOpenSession(Activity activity, SessionCallback callback) {
        AuthRequest request = new AuthRequest(activity, callback);
        request.setSession(mLoginSessionInstance);
        mLoginSessionInstance.sessionCallbacks.add(callback);
        Validate.assertEquals(Integer.valueOf(mLoginSessionInstance.sessionCallbacks.size()), 1, "[forceOpenSession] Callback Array Size");
        mLoginSessionInstance.sessionStatus = SessionStatus.OPENING;
        mLoginSessionInstance.commenceAuth(request);
        return mLoginSessionInstance;
    }

    /* access modifiers changed from: private */
    public static void runThroughHandler(Handler handler2, Runnable runnable) {
        if (handler2 != null) {
            handler2.post(runnable);
        }
    }

    private static boolean isSessionValid(GGLoginSession currentSession) {
        return currentSession != null && (currentSession.sessionStatus == SessionStatus.OPENING || currentSession.sessionStatus == SessionStatus.TOKEN_AVAILABLE);
    }

    public static synchronized boolean clearSession(final ClearSessionCallback callback) {
        boolean z = false;
        synchronized (GGLoginSession.class) {
            if (mLoginSessionInstance != null) {
                if (applicationContext != null) {
                    if (FacebookSdk.isInitialized()) {
                        FBClient.clearSession(applicationContext);
                    }
                    if (VKUtils.lookUpAppId(applicationContext) != -1) {
                        if (!VKSdk.isCustomInitialize()) {
                            VKSdk.customInitialize(applicationContext, VKUtils.lookUpAppId(applicationContext), (String) null);
                        }
                        VKSdk.logout();
                    }
                    mLoginSessionInstance.logout();
                    mLoginSessionInstance.invalidateCache();
                    mLoginSessionInstance = null;
                    if (mBindSessionInstance != null) {
                        mBindSessionInstance.invalidateCache();
                        mBindSessionInstance = null;
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            CookieManager.getInstance().removeAllCookie();
                            new WebView(GGLoginSession.getApplicationContext()).clearCache(true);
                        }
                    });
                    PaymentChannelStorage.getInstance().clear();
                    new RedeemCache().clearRedeemCache();
                    GoogleSignInClient mGoogleSignInClient = GoogleAuthRequestHandler.getGoogleSignInClient(applicationContext);
                    if (mGoogleSignInClient != null) {
                        BBLogger.d("Start to sign out Google account.", new Object[0]);
                        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                BBLogger.i("Google account signed out", new Object[0]);
                                if (callback != null) {
                                    callback.onComplete();
                                }
                            }
                        });
                    }
                    z = true;
                }
            }
        }
        return z;
    }

    public static synchronized boolean clearSession() {
        boolean clearSession;
        synchronized (GGLoginSession.class) {
            clearSession = clearSession((ClearSessionCallback) null);
        }
        return clearSession;
    }

    public static boolean checkSessionValidity() {
        AuthToken token;
        GGLoginSession session = getCurrentSession();
        if (session == null || (token = session.getTokenValue()) == null || TextUtils.isEmpty(token.getAuthToken())) {
            return false;
        }
        return true;
    }

    public void logout() {
        this.sessionStatus = SessionStatus.CLOSED;
        CommonEventLoop.getInstance().post(new Runnable() {
            public void run() {
                if (GGLoginSession.this.tokenValue != null) {
                    String accessToken = GGLoginSession.this.tokenValue.getAuthToken();
                    if (!TextUtils.isEmpty(accessToken)) {
                        Map<String, String> param = new HashMap<>();
                        param.put("access_token", accessToken);
                        param.put("refresh_token", GGLoginSession.this.tokenValue.getRefreshToken());
                        SimpleNetworkClient.getInstance().makeGetRequest(SDKConstants.getLogoutUrl(), param);
                    }
                }
            }
        });
    }

    public SessionStatus getSessionStatus() {
        return this.sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus2) {
        this.sessionStatus = sessionStatus2;
    }

    public Integer getSourceId() {
        return this.sourceId;
    }

    public AuthToken getTokenValue() {
        return this.tokenValue;
    }

    public String getOpenId() {
        if (getTokenValue() != null) {
            return getTokenValue().getOpenId();
        }
        return "";
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode2) {
        this.errorCode = errorCode2;
    }

    public void setApplicationKey(String key) {
        this.applicationKey = key;
    }

    public void invalidateCache() {
        if (this.cache != null) {
            this.cache.clearSession();
        }
    }

    public boolean hasCachedTokenExpired() {
        return Helper.getTimeNow() > this.tokenValue.getExpiryTimestamp();
    }

    /* access modifiers changed from: private */
    public void notifyCallback(final SessionCallback callback, final GGLoginSession aSession, final Exception exception) {
        runThroughHandler(this.handler, new Runnable() {
            public void run() {
                callback.onSessionProcessed(aSession, exception);
            }
        });
    }

    private void inspectToken(final SessionCallback callback) {
        if (SDKConstants.DEBUG.FORCE_FB_REFRESH) {
            CommonEventLoop.getInstance().post(new Runnable() {
                public void run() {
                    if (GGLoginSession.this.refreshToken(callback)) {
                        SessionStatus unused = GGLoginSession.this.sessionStatus = SessionStatus.TOKEN_AVAILABLE;
                    } else {
                        SessionStatus unused2 = GGLoginSession.this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
                    }
                    GGLoginSession.this.notifyCallback(callback, GGLoginSession.this, (Exception) null);
                }
            });
        } else if (SDKConstants.DEBUG.TEST_REFRESH_TOKEN_FAIL) {
            this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
            this.errorCode = GGErrorCode.REFRESH_TOKEN_FAILED.getCode().intValue();
            notifyCallback(callback, this, (Exception) null);
        } else if (this.tokenValue == null) {
        } else {
            if (Helper.getTimeNow() - 3600 <= this.tokenValue.getLastInspectTime()) {
                BBLogger.i("no need for another inspection - last %d minutes ago", Integer.valueOf((Helper.getTimeNow() - this.tokenValue.getLastInspectTime()) / 60));
                callback.onSessionProcessed(this, (Exception) null);
                return;
            }
            CommonEventLoop.getInstance().post(new Runnable() {
                public void run() {
                    int seconds = GGLoginSession.this.tokenValue.getExpiryTimestamp() - Helper.getTimeNow();
                    if (seconds >= 172800) {
                        BBLogger.i("%d hours until the token expires", Integer.valueOf(seconds / 3600));
                    } else if (GGLoginSession.this.refreshToken(callback)) {
                        BBLogger.i("refresh token ok - no need for inspection", new Object[0]);
                        return;
                    }
                    HashMap<String, String> data = new HashMap<>(3);
                    data.put("token", GGLoginSession.this.tokenValue.getAuthToken());
                    JSONObject result = SimpleNetworkClient.getInstance().makeGetRequest(SDKConstants.getTokenInspectUrl(), data);
                    if (result == null) {
                        SessionStatus unused = GGLoginSession.this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
                        int unused2 = GGLoginSession.this.errorCode = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                    } else if (result.has("error")) {
                        boolean isRefreshOk = false;
                        try {
                            String errorValue = result.getString("error");
                            BBLogger.i("inspection error %s", errorValue);
                            if (errorValue.equals(SDKConstants.ErrorFlags.INVALID_TOKEN)) {
                                isRefreshOk = GGLoginSession.this.refreshToken(callback);
                            }
                        } catch (JSONException e) {
                            BBLogger.e(e);
                        }
                        if (!isRefreshOk) {
                            SessionStatus unused3 = GGLoginSession.this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
                        } else {
                            return;
                        }
                    } else if (result.has("platform") && result.has("open_id")) {
                        try {
                            if (!GGLoginSession.this.tokenValue.getOpenId().equals(result.getString("open_id"))) {
                                int unused4 = GGLoginSession.this.errorCode = GGErrorCode.LOGIN_FAILED.getCode().intValue();
                                SessionStatus unused5 = GGLoginSession.this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
                            } else {
                                SessionStatus unused6 = GGLoginSession.this.sessionStatus = SessionStatus.TOKEN_AVAILABLE;
                                GGLoginSession.this.tokenValue.setExpiryTimestamp(result.optInt(GGLiveConstants.PARAM.EXPIRY_TIME));
                                GGLoginSession.this.tokenValue.setLastInspectTime(Helper.getTimeNow());
                                GGLoginSession.this.cache.putToken(GGLoginSession.this.tokenValue);
                            }
                            BBLogger.i("verify the open id %s %s", GGLoginSession.this.tokenValue.getOpenId(), result.getString("open_id"));
                        } catch (JSONException e2) {
                            BBLogger.e(e2);
                        }
                    }
                    GGLoginSession.this.notifyCallback(callback, GGLoginSession.this, (Exception) null);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public boolean refreshToken(SessionCallback callback) {
        if (this.tokenValue.getTokenProvider() == TokenProvider.FACEBOOK || TextUtils.isEmpty(this.tokenValue.getRefreshToken())) {
            return refreshFacebookToken(callback);
        }
        return refreshGarenaToken(callback);
    }

    private boolean refreshGarenaToken(SessionCallback callback) {
        HashMap<String, String> data = new HashMap<>(3);
        data.put("refresh_token", this.tokenValue.getRefreshToken());
        data.put("grant_type", "refresh_token");
        JSONObject result = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthRefreshTokenUrl(), (Map<String, String>) data);
        if (result == null) {
            this.errorCode = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
            return false;
        } else if (result.has("open_id")) {
            try {
                this.tokenValue.setAuthToken(result.optString("access_token"));
                this.tokenValue.setRefreshToken(result.optString("refresh_token"));
                this.tokenValue.setExpiryTimestamp(result.optInt(GGLiveConstants.PARAM.EXPIRY_TIME));
                this.tokenValue.setOpenId(result.optString("open_id"));
                this.tokenValue.setLastInspectTime(Helper.getTimeNow());
                this.cache.putToken(this.tokenValue);
                this.sessionStatus = SessionStatus.TOKEN_AVAILABLE;
                BBLogger.i("refresh token successful", new Object[0]);
                notifyCallback(callback, this, (Exception) null);
                return true;
            } catch (Exception e) {
                BBLogger.e(e);
                return false;
            }
        } else if (!result.has("error")) {
            return false;
        } else {
            try {
                String errorMsg = result.optString("error");
                BBLogger.i("refresh error %s", errorMsg);
                if (SDKConstants.ErrorFlags.INVALID_TOKEN.equals(errorMsg)) {
                    removeToken();
                    this.errorCode = GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue();
                    return false;
                } else if (SDKConstants.ErrorFlags.ERROR_USER_BAN.equals(errorMsg)) {
                    this.errorCode = GGErrorCode.ERROR_USER_BANNED.getCode().intValue();
                    return false;
                } else {
                    this.errorCode = GGErrorCode.REFRESH_TOKEN_FAILED.getCode().intValue();
                    return false;
                }
            } catch (Exception e2) {
                BBLogger.e(e2);
                return false;
            }
        }
    }

    public static void setCallback(Context context, SessionCallback wrappedCallback) {
        if (mLoginSessionInstance == null) {
            mLoginSessionInstance = new Builder(context).build();
        }
        mLoginSessionInstance.addCallback(wrappedCallback);
    }

    private void addCallback(SessionCallback wrappedCallback) {
        if (this.sessionCallbacks != null) {
            this.sessionCallbacks.add(wrappedCallback);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean inspectGuestToken(final AuthToken guestToken, final OnTokenResultListener onTokenResultListener) {
        if (guestToken == null || guestToken.getTokenProvider() != TokenProvider.GUEST) {
            return false;
        }
        CommonEventLoop.getInstance().post(new Runnable() {
            public void run() {
                int seconds = guestToken.getExpiryTimestamp() - Helper.getTimeNow();
                if (seconds < 172800) {
                    final GGErrorCode refreshGuestTokenResult = GGLoginSession.this.refreshGuestToken(guestToken);
                    if (refreshGuestTokenResult == GGErrorCode.SUCCESS) {
                        BBLogger.i("refresh guest token ok - no need for inspection", new Object[0]);
                        GGLoginSession.runThroughHandler(GGLoginSession.this.handler, new Runnable() {
                            public void run() {
                                onTokenResultListener.onTokenResult(guestToken, refreshGuestTokenResult);
                            }
                        });
                        return;
                    }
                } else {
                    BBLogger.i("%d hours until the token expires", Integer.valueOf(seconds / 3600));
                }
                HashMap<String, String> data = new HashMap<>(3);
                data.put("token", guestToken.getAuthToken());
                JSONObject result = SimpleNetworkClient.getInstance().makeGetRequest(SDKConstants.getTokenInspectUrl(), data);
                if (result == null) {
                    GGLoginSession.runThroughHandler(GGLoginSession.this.handler, new Runnable() {
                        public void run() {
                            onTokenResultListener.onTokenResult(guestToken, GGErrorCode.NETWORK_EXCEPTION);
                        }
                    });
                } else if (result.has("error")) {
                    try {
                        String errorValue = result.getString("error");
                        BBLogger.i("inspection guest token error %s", errorValue);
                        if (errorValue.equals(SDKConstants.ErrorFlags.INVALID_TOKEN)) {
                            final GGErrorCode refreshResult = GGLoginSession.this.refreshGuestToken(guestToken);
                            GGLoginSession.runThroughHandler(GGLoginSession.this.handler, new Runnable() {
                                public void run() {
                                    onTokenResultListener.onTokenResult(guestToken, refreshResult);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        BBLogger.e(e);
                        GGLoginSession.runThroughHandler(GGLoginSession.this.handler, new Runnable() {
                            public void run() {
                                onTokenResultListener.onTokenResult(guestToken, GGErrorCode.ERROR);
                            }
                        });
                    }
                } else if (result.has("platform") && result.has("open_id")) {
                    try {
                        if (!GGLoginSession.this.tokenValue.getOpenId().equals(result.getString("open_id"))) {
                            GGLoginSession.runThroughHandler(GGLoginSession.this.handler, new Runnable() {
                                public void run() {
                                    onTokenResultListener.onTokenResult(guestToken, GGErrorCode.ERROR);
                                }
                            });
                        } else {
                            GGLoginSession.runThroughHandler(GGLoginSession.this.handler, new Runnable() {
                                public void run() {
                                    onTokenResultListener.onTokenResult(guestToken, GGErrorCode.SUCCESS);
                                }
                            });
                        }
                        BBLogger.i("verify the open id %s %s", guestToken.getOpenId(), result.getString("open_id"));
                    } catch (JSONException e2) {
                        BBLogger.e(e2);
                        GGLoginSession.runThroughHandler(GGLoginSession.this.handler, new Runnable() {
                            public void run() {
                                onTokenResultListener.onTokenResult(guestToken, GGErrorCode.ERROR);
                            }
                        });
                    }
                }
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public GGErrorCode refreshGuestToken(AuthToken guestToken) {
        if (guestToken == null || guestToken.getTokenProvider() != TokenProvider.GUEST) {
            return GGErrorCode.ERROR;
        }
        if (StringUtils.isEmpty(guestToken.getRefreshToken())) {
            return GGErrorCode.ERROR;
        }
        HashMap<String, String> data = new HashMap<>(3);
        data.put("refresh_token", guestToken.getRefreshToken());
        data.put("grant_type", "refresh_token");
        JSONObject result = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthRefreshTokenUrl(), (Map<String, String>) data);
        if (result == null) {
            return GGErrorCode.NETWORK_EXCEPTION;
        }
        if (result.has("open_id")) {
            try {
                guestToken.setAuthToken(result.getString("access_token"));
                guestToken.setRefreshToken(result.getString("refresh_token"));
                guestToken.setExpiryTimestamp(result.getInt(GGLiveConstants.PARAM.EXPIRY_TIME));
                guestToken.setOpenId(result.getString("open_id"));
                this.cache.putGuestToken(guestToken);
                BBLogger.i("refresh token successful", new Object[0]);
                return GGErrorCode.SUCCESS;
            } catch (JSONException e) {
                BBLogger.e(e);
            }
        } else {
            if (result.has("error")) {
                try {
                    String errorMsg = result.getString("error");
                    BBLogger.i("refresh error %s", errorMsg);
                    if (SDKConstants.ErrorFlags.INVALID_TOKEN.equals(errorMsg)) {
                        removeToken();
                        return GGErrorCode.ACCESS_TOKEN_INVALID_GRANT;
                    } else if (SDKConstants.ErrorFlags.ERROR_USER_BAN.equals(errorMsg)) {
                        return GGErrorCode.ERROR_USER_BANNED;
                    } else {
                        return GGErrorCode.REFRESH_TOKEN_FAILED;
                    }
                } catch (JSONException e2) {
                    BBLogger.e(e2);
                }
            }
            return GGErrorCode.ERROR;
        }
    }

    public static void removeInvalidToken() {
        if (mLoginSessionInstance != null) {
            mLoginSessionInstance.removeToken();
        }
    }

    /* access modifiers changed from: private */
    public void removeToken() {
        if (this.tokenValue != null) {
            this.tokenValue.setAuthToken("");
            this.tokenValue.setExpiryTimestamp(0);
            this.tokenValue.setLastInspectTime(0);
            this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
            this.errorCode = GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue();
        }
        if (this.cache != null) {
            this.cache.remove(StorageCache.TOKEN_KEY);
            if (this.sessionProvider == SessionProvider.GUEST) {
                this.cache.removeGuestToken();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean refreshFacebookToken(final SessionCallback callback) {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null) {
            if (this.tokenValue != null && !TextUtils.isEmpty(this.tokenValue.getRefreshToken())) {
                return refreshGarenaToken(callback);
            }
            removeToken();
            this.errorCode = GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue();
            return false;
        } else if (token.isExpired()) {
            AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
                public void OnTokenRefreshed(AccessToken accessToken) {
                    BBLogger.d("facebook token refreshed", new Object[0]);
                    if (!GGLoginSession.this.refreshFacebookToken(callback)) {
                        GGLoginSession.this.notifyCallback(callback, GGLoginSession.this, (Exception) null);
                    }
                }

                public void OnTokenRefreshFailed(FacebookException exception) {
                    if (exception != null) {
                        BBLogger.d("facebook token refresh failed:" + exception.getMessage(), new Object[0]);
                    }
                    SessionStatus unused = GGLoginSession.this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
                    AccessToken.setCurrentAccessToken((AccessToken) null);
                    GGLoginSession.this.removeToken();
                    int unused2 = GGLoginSession.this.errorCode = GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue();
                    GGLoginSession.this.notifyCallback(callback, GGLoginSession.this, (Exception) null);
                }
            });
            return true;
        } else {
            HashMap<String, String> data = new HashMap<>(3);
            data.put("grant_type", "authorization_code");
            data.put("facebook_access_token", token.getToken());
            data.put("redirect_uri", SDKConstants.REDIRECT_URL_PREFIX + mLoginSessionInstance.getApplicationId() + "://auth/");
            data.put("source", GGPlatform.getChannelSource().toString());
            data.put("client_id", mLoginSessionInstance.getApplicationId());
            data.put("client_secret", mLoginSessionInstance.getApplicationKey());
            JSONObject result = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthFacebookTokenExchangeUrl(), (Map<String, String>) data);
            if (result == null) {
                try {
                    this.errorCode = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                    this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
                    return false;
                } catch (Exception e) {
                    BBLogger.e(e);
                    return false;
                }
            } else if (result.has("open_id")) {
                String openId = result.optString("open_id");
                String accessToken = result.optString("access_token");
                int expireTime = result.optInt(GGLiveConstants.PARAM.EXPIRY_TIME);
                this.tokenValue = new AuthToken(accessToken, TokenProvider.FACEBOOK);
                if (result.optInt("platform") == 1) {
                    this.tokenValue.setTokenProvider(TokenProvider.GARENA_NATIVE_ANDROID);
                }
                this.tokenValue.setExpiryTimestamp(expireTime);
                this.tokenValue.setOpenId(openId);
                this.tokenValue.setLastInspectTime(Helper.getTimeNow());
                this.cache.putToken(this.tokenValue);
                this.sessionStatus = SessionStatus.TOKEN_AVAILABLE;
                notifyCallback(callback, this, (Exception) null);
                return true;
            } else {
                if (result.optString("error").equals(SDKConstants.ErrorFlags.INVALID_TOKEN)) {
                    removeToken();
                    AccessToken.setCurrentAccessToken((AccessToken) null);
                    this.errorCode = GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue();
                } else {
                    this.errorCode = GGErrorCode.REFRESH_TOKEN_FAILED.getCode().intValue();
                }
                this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
                return false;
            }
        }
    }

    public Integer getPlatform() {
        return Integer.valueOf(getSessionProvider().getValue());
    }

    private void commenceAuth(AuthRequest request) {
        request.setApplicationId(this.applicationId);
        request.setRequestCode(this.requestCode);
        request.setApplicationKey(this.applicationKey);
        request.setLegacy(this.mode == AuthMode.LEGACY_ENABLED);
        if (!checkIfLoginWorks(request)) {
            synchronized (GGLoginSession.class) {
                SessionStatus previousSessionState = request.getSession().sessionStatus;
                request.getSession().sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
                request.getSession().publishStatusChange(previousSessionState, request.getSession().sessionStatus, new InvalidOperationException("Failed to Validate the authenticity of the internal package.Did you forget to include the BTLoginActivity in your AndroidManifest.xml"));
            }
        }
    }

    private boolean checkIfLoginWorks(AuthRequest request) {
        Intent intent = request.getSession().getLoginActivityIntent(request);
        if (!request.getSession().validIntent(intent)) {
            return false;
        }
        request.getActivityLauncher().startActivityForResult(intent, request.getRequestCode());
        return true;
    }

    private void publishStatusChange(SessionStatus oldStatus, SessionStatus newStatus, final Exception exception) {
        synchronized (this.sessionCallbacks) {
            if (oldStatus != newStatus) {
                runThroughHandler(this.handler, new Runnable() {
                    public void run() {
                        for (final SessionCallback callback : GGLoginSession.this.sessionCallbacks) {
                            GGLoginSession.runThroughHandler(GGLoginSession.this.handler, new Runnable() {
                                public void run() {
                                    callback.onSessionProcessed(GGLoginSession.this, exception);
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    private boolean validIntent(Intent intent) {
        ResolveInfo resolveInfo;
        if (applicationContext == null || applicationContext.getPackageManager() == null) {
            resolveInfo = null;
        } else {
            resolveInfo = applicationContext.getPackageManager().resolveActivity(intent, 0);
        }
        if (resolveInfo != null) {
            return true;
        }
        return false;
    }

    private Intent getLoginActivityIntent(AuthRequest request) {
        Intent intent = new Intent();
        intent.setClass(applicationContext, BTLoginActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(SDKConstants.BUNDLE_REQUEST_KEY, request.getAuthClientRequest());
        intent.putExtras(extras);
        return intent;
    }

    public SessionProvider getSessionProvider() {
        return this.sessionProvider;
    }

    /* access modifiers changed from: package-private */
    public final boolean onActivityResult(Activity currentActivity, int requestCode2, int resultCode, Intent data) {
        Validate.notNull(currentActivity, "currentActivity");
        BBLogger.d("onActivityResult", new Object[0]);
        initContext(currentActivity);
        if (requestCode2 != this.requestCode) {
            return false;
        }
        if (data == null || resultCode != -1) {
            if (resultCode == 0) {
                synchronized (GGLoginSession.class) {
                    SessionStatus oldStatus = this.sessionStatus;
                    AuthClient.Result result = null;
                    try {
                        result = (AuthClient.Result) data.getExtras().getSerializable(SDKConstants.BUNDLE_RESULT_KEY);
                    } catch (NullPointerException e) {
                        BBLogger.e(e);
                    }
                    if (result != null) {
                        if (result.errorCode == 0) {
                            this.sessionStatus = SessionStatus.CLOSED;
                            publishStatusChange(oldStatus, this.sessionStatus, (Exception) null);
                        }
                    }
                    this.errorCode = result == null ? GGErrorCode.UNKNOWN_ERROR.getCode().intValue() : result.errorCode;
                    if (this.errorCode == GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue()) {
                        removeToken();
                    }
                    this.sessionStatus = SessionStatus.CLOSED_WITH_ERROR;
                    publishStatusChange(oldStatus, this.sessionStatus, (Exception) null);
                }
            }
            return false;
        }
        synchronized (GGLoginSession.class) {
            BBLogger.d("onActivityResult: success", new Object[0]);
            SessionStatus oldStatus2 = this.sessionStatus;
            AuthClient.Result result2 = (AuthClient.Result) data.getExtras().getSerializable(SDKConstants.BUNDLE_RESULT_KEY);
            this.tokenValue = result2.token;
            this.tokenValue.setOpenId(result2.openId);
            this.cache.putToken(this.tokenValue);
            switch (this.tokenValue.getTokenProvider()) {
                case FACEBOOK:
                    this.sessionProvider = SessionProvider.FACEBOOK;
                    break;
                case GUEST:
                    this.sessionProvider = SessionProvider.GUEST;
                    break;
                case GARENA_NATIVE_ANDROID:
                case GARENA_WEB_ANDROID:
                    this.sessionProvider = SessionProvider.GARENA;
                    break;
                case VK:
                    this.sessionProvider = SessionProvider.VK;
                    break;
                case LINE:
                    this.sessionProvider = SessionProvider.LINE;
                    break;
                case GOOGLE:
                    this.sessionProvider = SessionProvider.GOOGLE;
                    break;
            }
            if (this.sessionProvider == SessionProvider.GUEST) {
                this.cache.putGuestToken(this.tokenValue);
            }
            this.sessionStatus = SessionStatus.TOKEN_AVAILABLE;
            publishStatusChange(oldStatus2, SessionStatus.TOKEN_AVAILABLE, (Exception) null);
        }
        return true;
    }

    public void refreshAccessToken(Activity context, SessionCallback mCallback) {
        this.sessionProvider = SessionProvider.REFRESH_TOKEN;
        this.sessionStatus = SessionStatus.CREATED;
        AuthRequest request = new AuthRequest(context, mCallback);
        request.setSession(mLoginSessionInstance);
        commenceAuth(request);
    }

    public StorageCache getCache() {
        return this.cache;
    }

    public void setCache(StorageCache cache2) {
        this.cache = cache2;
    }

    public enum SessionProvider {
        GARENA(1),
        FACEBOOK(3),
        GUEST(4),
        VK(5),
        LINE(6),
        GOOGLE(8),
        REFRESH_TOKEN(0);
        
        private int val;

        private SessionProvider(int val2) {
            this.val = val2;
        }

        public static boolean isEqualToSessionProvider(SessionProvider provider, TokenProvider tokenProvider) {
            if (provider == FACEBOOK && tokenProvider == TokenProvider.FACEBOOK) {
                return true;
            }
            if ((provider == GUEST && tokenProvider == TokenProvider.GUEST) || provider == REFRESH_TOKEN) {
                return true;
            }
            if (provider == GARENA && (tokenProvider == TokenProvider.GARENA_WEB_ANDROID || tokenProvider == TokenProvider.GARENA_NATIVE_ANDROID)) {
                return true;
            }
            if (provider == VK && tokenProvider == TokenProvider.VK) {
                return true;
            }
            if (provider == LINE && tokenProvider == TokenProvider.LINE) {
                return true;
            }
            if (provider == GOOGLE && tokenProvider == TokenProvider.GOOGLE) {
                return true;
            }
            return false;
        }

        public int getValue() {
            return this.val;
        }
    }

    public static final class Builder {
        private String applicationId;
        private String applicationKey;
        private StorageCache cache = null;
        private final Context context;
        private String mRedirectURI;
        private AuthMode modes;
        private int requestCode = SDKConstants.DEFAULT_ACTIVITY_LAUNCH_REQUEST_CODE.intValue();
        private SessionProvider sessionProvider = SessionProvider.GARENA;

        public Builder(Context context2) {
            this.context = context2;
        }

        public Builder setApplicationId(String applicationId2) {
            this.applicationId = applicationId2;
            return this;
        }

        public Builder setAuthMode(AuthMode mode) {
            this.modes = mode;
            return this;
        }

        public Builder setApplicationKey(String applicationKey2) {
            this.applicationKey = applicationKey2;
            return this;
        }

        public Builder setSessionProvider(SessionProvider provider) {
            this.sessionProvider = provider;
            return this;
        }

        public Builder setRequestCode(int requestCode2) {
            this.requestCode = requestCode2;
            return this;
        }

        public Builder setRedirectURI(String redirectURI) {
            this.mRedirectURI = redirectURI;
            return this;
        }

        public GGLoginSession build() {
            return new GGLoginSession(this.context, this.applicationId, this.applicationKey, this.mRedirectURI, this.modes, this.requestCode, this.sessionProvider, this.cache);
        }

        public Builder setCache(StorageCache cache2) {
            this.cache = cache2;
            return this;
        }
    }
}
