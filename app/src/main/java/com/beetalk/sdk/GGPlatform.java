package com.beetalk.sdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGTextShare;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.GGAppConfig;
import com.beetalk.sdk.cache.PersistentCache;
import com.beetalk.sdk.cache.SharedPrefStorage;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.DataModel;
import com.beetalk.sdk.data.TagsData;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.exception.SessionAccessException;
import com.beetalk.sdk.facebook.FBPostItem;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.helper.Validate;
import com.beetalk.sdk.line.LinePostItem;
import com.beetalk.sdk.ndk.ShareRet;
import com.beetalk.sdk.ndk.WakeupRet;
import com.beetalk.sdk.networking.GarenaUserAgent;
import com.beetalk.sdk.plugin.GGPluginCallback;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.fb.FBGraphItemSharePlugin;
import com.beetalk.sdk.plugin.impl.fb.FBInvitePlugin;
import com.beetalk.sdk.plugin.impl.fb.data.FBMessageData;
import com.beetalk.sdk.plugin.impl.friends.GGSendBuddyRequestPlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.plugin.impl.gglive.data.GGLiveChannelInfoUpdateData;
import com.beetalk.sdk.plugin.impl.gglive.data.GGLiveChannelRegisterData;
import com.beetalk.sdk.plugin.impl.gglive.data.GGLiveChannelStreamInitData;
import com.beetalk.sdk.plugin.impl.misc.FeedbackPlugin;
import com.beetalk.sdk.request.GuestBindAccountRequest;
import com.beetalk.sdk.request.GuestGrandTokenRequest;
import com.beetalk.sdk.request.ResponseType;
import com.beetalk.sdk.update.UpdateManager;
import com.beetalk.sdk.vk.VKPostItem;
import com.beetalk.sdk.vk.VKUtils;
import com.facebook.AccessToken;
import com.garena.android.GPNManager;
import com.garena.android.PushNotificationStateListener;
import com.garena.android.gpns.utility.DeviceUtil;
import com.garena.android.push.NotificationData;
import com.garena.android.push.PushClient;
import com.garena.network.AsyncHttpClient;
import com.garena.network.AsyncHttpResponse;
import com.garena.overlay.FloatDotViewUtil;
import com.garena.pay.android.GGErrorCode;
import com.vk.sdk.VKSdk;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class GGPlatform {
    private static String _appId = "0";
    private static volatile GGAppConfig config;
    private static volatile Activity mContext;
    private static String mGarenaLoginTitle;
    private static PushClient.PushRequestBuilder pushRequestBuilder;

    public interface FriendGroupListCallback extends GGPluginCallback<DataModel.FriendGroupsRet> {
    }

    public interface FriendInfoCallback extends GGPluginCallback<DataModel.FriendsInfoRet> {
    }

    public interface GroupFriendInfoCallback extends GGPluginCallback<DataModel.GroupFriendsInfoRet> {
    }

    public interface ShareCallback extends GGPluginCallback<ShareRet> {
    }

    public interface UserFriendIDListCallback extends GGPluginCallback<DataModel.FriendIDsRet> {
    }

    public interface UserInfoCallback extends GGPluginCallback<DataModel.UserInfoRet> {
    }

    public interface WakeupNotifyCallback {
        void OnWakeupNotify(WakeupRet wakeupRet);
    }

    /* access modifiers changed from: private */
    public static void secondRoundLogin(GGLoginSession session, GGLoginSession.SessionCallback secondCallback) {
        BBLogger.e("ndk_login_auth_callback error %s", session.getSessionStatus().toString());
        GGLoginSession.Builder builder = new GGLoginSession.Builder(mContext);
        builder.setApplicationKey(session.getApplicationKey()).setAuthMode(session.getMode()).setApplicationId(session.getApplicationId()).setSessionProvider(session.getSessionProvider()).setRequestCode(session.getRequestCode());
        GGLoginSession.setCurrentSession(builder.build());
        GGLoginSession.forceOpenSession(mContext, secondCallback);
    }

    public static GGAppConfig getAppConfig() {
        return config;
    }

    public static void initialize(GGLoginSession newSession) {
        Context ctx = GGLoginSession.getApplicationContext();
        int vkId = VKUtils.lookUpAppId(ctx);
        if (vkId != -1) {
            VKSdk.customInitialize(ctx, vkId, (String) null);
        }
        GGLoginSession.setCurrentSession(newSession);
        if (config == null) {
            config = new GGAppConfig(GGLoginSession.getApplicationContext());
            config.init();
        }
    }

    public static void initializeForBind(GGLoginSession newSession) {
        Context ctx = GGLoginSession.getApplicationContext();
        int vkId = VKUtils.lookUpAppId(ctx);
        if (vkId != -1) {
            VKSdk.customInitialize(ctx, vkId, (String) null);
        }
        GGLoginSession.setCurrentBindSession(newSession);
        if (config == null) {
            config = new GGAppConfig(GGLoginSession.getApplicationContext());
            config.init();
        }
    }

    public static void initialize(Activity entryActivity) {
        mContext = entryActivity;
        Context ctx = entryActivity.getApplicationContext();
        GGLoginSession.initContext(ctx);
        GarenaUserAgent.init(ctx);
        int vkId = VKUtils.lookUpAppId(ctx);
        if (vkId != -1) {
            VKSdk.customInitialize(ctx, vkId, (String) null);
        }
        if (config == null) {
            config = new GGAppConfig(mContext.getApplicationContext());
            config.init();
        }
    }

    public static Integer getChannelSource() {
        if (GGLoginSession.getCurrentSession() != null) {
            return GGLoginSession.getCurrentSession().getSourceId();
        }
        if (mContext != null) {
            return Helper.getMetaDataSourceId(mContext);
        }
        throw new SessionAccessException("Please initialize the session before continuing");
    }

    public static String getAppId() {
        return _appId;
    }

    public static void setAppId(String appId) {
        _appId = appId;
    }

    public static void login(Activity context, GGLoginSession.SessionCallback mCallback) {
        mContext = context;
        GGLoginSession.SessionCallback wrappedCallback = wrapCallback(mCallback);
        if (!Helper.isNetworkConnected(context)) {
            GGLoginSession session = GGLoginSession.getCurrentSession();
            if (session != null) {
                session.setSessionStatus(SessionStatus.CLOSED_WITH_ERROR);
                session.setErrorCode(GGErrorCode.NETWORK_EXCEPTION.getCode().intValue());
                wrappedCallback.onSessionProcessed(session, (Exception) null);
                return;
            }
            return;
        }
        GGLoginSession.obtainActiveSession(mContext, true, wrappedCallback);
    }

    public static void setLoginSessionCallback(Context context, GGLoginSession.SessionCallback mCallback) {
        GGLoginSession.setCallback(context, wrapCallback(mCallback));
    }

    public static void GGGetSessionForBind(Activity context, GGLoginSession.SessionCallback callback) {
        mContext = context;
        GGLoginSession.obtainActiveBindSession(mContext, wrapCallbackWithoutLoginAgain(callback));
    }

    public static String GGResetGuest(Context context) {
        GGLoginSession bindSession = GGLoginSession.getCurrentBindSession();
        if (bindSession != null) {
            bindSession.setCache(new SharedPrefStorage(context));
            GGLoginSession.setCurrentSession(bindSession);
            new SharedPrefStorage(context).putToken(bindSession.getTokenValue());
        } else {
            GGLoginSession.clearSession();
        }
        return PersistentCache.getInstance().deleteGuestInfo();
    }

    public static boolean GGHasGuestAccount(Context context) {
        return !StringUtils.isEmpty(PersistentCache.getInstance().getGuestUID());
    }

    public static void bind(Activity context, int platform, GGLoginSession.SessionCallback callback, String appKey, String appId) {
        GGLoginSession.SessionProvider provider;
        GGLoginSession.SessionCallback wrappedCallback = wrapCallback(callback);
        if (GGLoginSession.getCurrentSession() != null && GGLoginSession.getCurrentSession().getSessionProvider() == GGLoginSession.SessionProvider.GUEST && GGLoginSession.getCurrentSession().getSessionStatus() == SessionStatus.TOKEN_AVAILABLE) {
            if (platform == 3) {
                bindPlatformWhenGuestLogin(context, GGLoginSession.SessionProvider.FACEBOOK, wrappedCallback);
            } else if (platform == 1) {
                bindPlatformWhenGuestLogin(context, GGLoginSession.SessionProvider.GARENA, wrappedCallback);
            } else if (platform != 4 && platform != 0) {
                if (platform == 5) {
                    bindPlatformWhenGuestLogin(context, GGLoginSession.SessionProvider.VK, wrappedCallback);
                } else if (platform == 6) {
                    bindPlatformWhenGuestLogin(context, GGLoginSession.SessionProvider.LINE, wrappedCallback);
                } else if (platform == 8) {
                    bindPlatformWhenGuestLogin(context, GGLoginSession.SessionProvider.GOOGLE, wrappedCallback);
                }
            }
        } else if (GGLoginSession.getCurrentSession() != null && GGLoginSession.getCurrentSession().getSessionProvider() != GGLoginSession.SessionProvider.GUEST && GGLoginSession.getCurrentSession().getSessionStatus() == SessionStatus.TOKEN_AVAILABLE) {
            bindCurrentPlatformWithGuest(context, wrappedCallback);
        } else if (GGLoginSession.getCurrentSession() == null || GGLoginSession.getCurrentSession().getSessionStatus() != SessionStatus.TOKEN_AVAILABLE) {
            if (GGLoginSession.getCurrentSession() != null) {
                BBLogger.i("session exists", new Object[0]);
            }
            switch (platform) {
                case 1:
                    provider = GGLoginSession.SessionProvider.GARENA;
                    break;
                case 3:
                    provider = GGLoginSession.SessionProvider.FACEBOOK;
                    break;
                case 5:
                    provider = GGLoginSession.SessionProvider.VK;
                    break;
                case 6:
                    provider = GGLoginSession.SessionProvider.LINE;
                    break;
                case 8:
                    provider = GGLoginSession.SessionProvider.GOOGLE;
                    break;
                default:
                    return;
            }
            initialize(new GGLoginSession.Builder(context).setApplicationId(appId).setAuthMode(AuthMode.LEGACY_ENABLED).setSessionProvider(provider).build());
            if (!TextUtils.isEmpty(appKey) && GGLoginSession.getCurrentSession() != null) {
                GGLoginSession.getCurrentSession().setApplicationKey(appKey);
            }
            loginWithBind(context, wrappedCallback);
        }
    }

    public static void loginWithBind(final Activity context, final GGLoginSession.SessionCallback callback) {
        BBLogger.i("loginWithBind", new Object[0]);
        GGLoginSession.obtainActiveSession(context, true, new GGLoginSession.SessionCallback() {
            public void onSessionProcessed(GGLoginSession session, Exception exception) {
                if (session.getSessionStatus() == SessionStatus.TOKEN_AVAILABLE) {
                    BBLogger.i("obtain valid platfor token, start to bind guest", new Object[0]);
                    GGPlatform.bindCurrentPlatformWithGuest(context, callback);
                } else if (session.getSessionStatus() == SessionStatus.OPENING) {
                } else {
                    if (session.getSessionStatus() == SessionStatus.CLOSED_WITH_ERROR) {
                        callback.onSessionProcessed(session, exception);
                    } else if (session.getSessionStatus() == SessionStatus.INSPECTION_WITH_ERROR) {
                        callback.onSessionProcessed(session, exception);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void processSessionBindFail() {
        GGLoginSession.getCurrentSession().setSessionStatus(SessionStatus.CLOSED_WITH_BIND_FAIL);
    }

    /* access modifiers changed from: private */
    public static void bindCurrentPlatformWithGuest(Activity context, final GGLoginSession.SessionCallback callback) {
        BBLogger.i("bindCurrentPlatformWithGuest", new Object[0]);
        final GGLoginSession currentSession = GGLoginSession.getCurrentSession();
        currentSession.setIsBindSuccess(false);
        if (currentSession.getSessionProvider() == GGLoginSession.SessionProvider.GUEST) {
            processSessionBindFail();
            callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
        } else if (currentSession.getSessionStatus() != SessionStatus.TOKEN_AVAILABLE) {
            processSessionBindFail();
            callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
        } else {
            final AuthToken platformToken = currentSession.getTokenValue();
            AuthToken guestToken = currentSession.getCache().getGuestToken();
            String uid = PersistentCache.getInstance().getGuestUID();
            String password = PersistentCache.getInstance().getGuestPassword();
            if (StringUtils.isEmpty(uid)) {
                processSessionBindFail();
                callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
            } else if (guestToken == null) {
                new GuestGrandTokenRequest(Long.valueOf(uid).longValue(), password, ResponseType.TOKEN, currentSession.getApplicationId()).send(new AsyncHttpClient.JSONObjectCallback() {
                    public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                        if (e != null || StringUtils.responseHasError(result)) {
                            BBLogger.e(e);
                            GGPlatform.processSessionBindFail();
                            callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                        } else if (result == null) {
                            try {
                                GGPlatform.processSessionBindFail();
                                callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                            } catch (JSONException jsonE) {
                                BBLogger.e(jsonE);
                                GGPlatform.processSessionBindFail();
                                callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                            }
                        } else if (result.has("open_id")) {
                            String accessToken = result.getString("access_token");
                            String refreshToken = result.getString("refresh_token");
                            int expireTime = result.getInt(GGLiveConstants.PARAM.EXPIRY_TIME);
                            AuthToken authToken = new AuthToken(accessToken, TokenProvider.GUEST);
                            authToken.setRefreshToken(refreshToken);
                            authToken.setExpiryTimestamp(expireTime);
                            currentSession.getCache().putGuestToken(authToken);
                            GGPlatform.inspectGuestTokenAndBindWhenCurrentPlatformTokenAvailable(platformToken, authToken, callback);
                        } else if (result.has("error")) {
                            BBLogger.e("grand token result: " + result.toString(), new Object[0]);
                            if (SDKConstants.ErrorFlags.AUTH_ERROR.equalsIgnoreCase(result.optString("error"))) {
                                PersistentCache.getInstance().deleteGuestInfo();
                            }
                            GGPlatform.processSessionBindFail();
                            callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                        } else {
                            BBLogger.e("grand token result: " + result.toString(), new Object[0]);
                            GGPlatform.processSessionBindFail();
                            callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                        }
                    }
                });
            } else {
                inspectGuestTokenAndBindWhenCurrentPlatformTokenAvailable(platformToken, guestToken, callback);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void inspectGuestTokenAndBindWhenCurrentPlatformTokenAvailable(final AuthToken platformToken, final AuthToken guestToken, final GGLoginSession.SessionCallback callback) {
        final GGLoginSession platformSession = GGLoginSession.getCurrentSession();
        GGLoginSession.getCurrentSession().inspectGuestToken(guestToken, new GGLoginSession.OnTokenResultListener() {
            public void onTokenResult(AuthToken authToken, GGErrorCode status) {
                if (status == GGErrorCode.SUCCESS) {
                    new GuestBindAccountRequest(platformToken.getAuthToken(), guestToken.getAuthToken()).send(new AsyncHttpClient.JSONObjectCallback() {
                        public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                            if (e != null || StringUtils.responseHasError(result)) {
                                BBLogger.e(e);
                                GGPlatform.processSessionBindFail();
                                callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                                return;
                            }
                            try {
                                BBLogger.i("bind result: " + result.toString(), new Object[0]);
                                if (result.getInt(GGLiveConstants.PARAM.RESULT) == 0) {
                                    platformSession.setIsBindSuccess(true);
                                    PersistentCache.getInstance().deleteGuestInfo();
                                    callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                                    return;
                                }
                                BBLogger.e("bind result: " + result.toString(), new Object[0]);
                                GGPlatform.processSessionBindFail();
                                callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                            } catch (JSONException e1) {
                                BBLogger.e("bind exception: " + e1.getLocalizedMessage(), new Object[0]);
                                e1.printStackTrace();
                                GGPlatform.processSessionBindFail();
                                callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                            }
                        }
                    });
                    return;
                }
                GGPlatform.processSessionBindFail();
                callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
            }
        });
    }

    public static void bindPlatformWhenGuestLogin(final Activity context, GGLoginSession.SessionProvider sessionProvider, final GGLoginSession.SessionCallback callback) {
        final GGLoginSession currentSession = GGLoginSession.getCurrentSession();
        if (currentSession.getSessionProvider() == GGLoginSession.SessionProvider.GUEST && currentSession.getSessionStatus() == SessionStatus.TOKEN_AVAILABLE) {
            final AuthToken guestToken = currentSession.getTokenValue();
            GGLoginSession.clearSession();
            initialize(new GGLoginSession.Builder(context).setApplicationId(currentSession.getApplicationId()).setApplicationKey(currentSession.getApplicationKey()).setAuthMode(AuthMode.LEGACY_ENABLED).setSessionProvider(sessionProvider).build());
            BBLogger.i("start to login platform", new Object[0]);
            login(context, new GGLoginSession.SessionCallback() {
                public void onSessionProcessed(GGLoginSession session, Exception exception) {
                    session.setIsBindSuccess(false);
                    if (session.getSessionStatus() != SessionStatus.OPENING) {
                        boolean isLoginSuccess = false;
                        if (exception != null) {
                            BBLogger.e("bindPlatformWhenGuestLogin: login platform fail with exception", new Object[0]);
                            callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                        } else if (session.getSessionStatus() == SessionStatus.TOKEN_AVAILABLE) {
                            isLoginSuccess = true;
                        } else {
                            GGPlatform.reLoginSession(context, currentSession, callback);
                        }
                        if (isLoginSuccess) {
                            BBLogger.i("bindPlatformWhenGuestLogin: login platform success", new Object[0]);
                            new GuestBindAccountRequest(GGLoginSession.getCurrentSession().getTokenValue().getAuthToken(), guestToken.getAuthToken()).send(new AsyncHttpClient.JSONObjectCallback() {
                                public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                                    if (e != null || StringUtils.responseHasError(result)) {
                                        BBLogger.e(e);
                                        GGPlatform.reLoginSession(context, currentSession, callback);
                                        return;
                                    }
                                    try {
                                        BBLogger.i("bind successful", new Object[0]);
                                        if (result.getInt(GGLiveConstants.PARAM.RESULT) == 0) {
                                            GGLoginSession.getCurrentSession().setIsBindSuccess(true);
                                            PersistentCache.getInstance().deleteGuestInfo();
                                            callback.onSessionProcessed(GGLoginSession.getCurrentSession(), (Exception) null);
                                            return;
                                        }
                                        BBLogger.i("bind failed", new Object[0]);
                                        GGPlatform.reLoginSession(context, currentSession, callback);
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                        GGPlatform.reLoginSession(context, currentSession, callback);
                                    }
                                }
                            });
                            return;
                        }
                        GGPlatform.reLoginSession(context, currentSession, callback);
                    }
                }
            });
            return;
        }
        currentSession.setIsBindSuccess(false);
        callback.onSessionProcessed(currentSession, (Exception) null);
    }

    @Deprecated
    public static String deleteLocalGuest(Context context) {
        if (GGLoginSession.getCurrentSession() == null) {
            initialize(new GGLoginSession.Builder(context).setApplicationId("0").setAuthMode(AuthMode.LEGACY_ENABLED).setSessionProvider(GGLoginSession.SessionProvider.GARENA).build());
        }
        String uid = PersistentCache.getInstance().deleteGuestInfo();
        GGLoginSession.clearSession();
        return uid;
    }

    /* access modifiers changed from: private */
    public static void reLoginSession(Activity activity, GGLoginSession sessionToLogin, GGLoginSession.SessionCallback callback) {
        GGLoginSession.clearSession();
        initialize(sessionToLogin);
        login(activity, callback);
    }

    static GGLoginSession.SessionCallback wrapCallback(GGLoginSession.SessionCallback callback) {
        Validate.notNull(callback, "Session Callback to be wrapped cannot be null");
        return wrapCallback(callback, true);
    }

    static GGLoginSession.SessionCallback wrapCallbackWithoutLoginAgain(GGLoginSession.SessionCallback callback) {
        Validate.notNull(callback, "Session Callback to be wrapped cannot be null");
        return wrapCallback(callback, false);
    }

    static GGLoginSession.SessionCallback wrapCallback(final GGLoginSession.SessionCallback callback, final boolean needSecondRoundLogin) {
        Validate.notNull(callback, "Session Callback to be wrapped cannot be null");
        return new GGLoginSession.SessionCallback() {
            public void onSessionProcessed(GGLoginSession session, Exception exception) {
                if (session != null) {
                    switch (session.getSessionStatus()) {
                        case CREATED:
                        case OPENING:
                        case TOKEN_AVAILABLE:
                        case CLOSED:
                            callback.onSessionProcessed(session, exception);
                            return;
                        case CLOSED_WITH_ERROR:
                        case INSPECTION_WITH_ERROR:
                            if (!GGErrorCode.isFatal(session.getErrorCode()) || !needSecondRoundLogin) {
                                callback.onSessionProcessed(session, exception);
                                return;
                            } else {
                                GGPlatform.secondRoundLogin(session, callback);
                                return;
                            }
                        default:
                            callback.onSessionProcessed(session, exception);
                            return;
                    }
                } else {
                    callback.onSessionProcessed(session, exception);
                }
            }
        };
    }

    public static void handleActivityResult(Activity context, int requestCode, int resultCode, Intent data) {
        if (requestCode == SDKConstants.OBTAIN_BIND_SESSION_REQUEST_CODE.intValue()) {
            if (GGLoginSession.getCurrentBindSession() != null) {
                GGLoginSession.getCurrentBindSession().onActivityResult(context, requestCode, resultCode, data);
            }
        } else if (GGLoginSession.getCurrentSession() != null) {
            GGLoginSession.getCurrentSession().onActivityResult(context, requestCode, resultCode, data);
        } else {
            BBLogger.d("onActivityResult: no available session", new Object[0]);
        }
    }

    public static void refreshAccessToken(Activity context, GGLoginSession.SessionCallback mCallback) {
        if (GGLoginSession.getCurrentSession() != null) {
            GGLoginSession.getCurrentSession().refreshAccessToken(context, mCallback);
        }
    }

    public static boolean getLastLoginSession(Activity context) {
        GGLoginSession.SessionProvider sProvider;
        AuthToken token = new SharedPrefStorage(context).getToken();
        if (token == null || TextUtils.isEmpty(token.getAuthToken())) {
            return false;
        }
        switch (token.getTokenProvider()) {
            case FACEBOOK:
                sProvider = GGLoginSession.SessionProvider.FACEBOOK;
                break;
            case GARENA_WEB_ANDROID:
            case GARENA_NATIVE_ANDROID:
                sProvider = GGLoginSession.SessionProvider.GARENA;
                break;
            case GUEST:
                sProvider = GGLoginSession.SessionProvider.GUEST;
                break;
            case VK:
                sProvider = GGLoginSession.SessionProvider.VK;
                break;
            case LINE:
                sProvider = GGLoginSession.SessionProvider.LINE;
                break;
            case GOOGLE:
                sProvider = GGLoginSession.SessionProvider.GOOGLE;
                break;
            default:
                sProvider = GGLoginSession.SessionProvider.REFRESH_TOKEN;
                break;
        }
        GGLoginSession newSession = new GGLoginSession.Builder(context).setApplicationId(_appId).setApplicationKey("").setAuthMode(AuthMode.LEGACY_ENABLED).setSessionProvider(sProvider).build();
        if (newSession.getSessionStatus() != SessionStatus.TOKEN_AVAILABLE) {
            return false;
        }
        initialize(newSession);
        return true;
    }

    public static void GGSetTags(Activity activity, String tags, String pushAppKey, GGPluginCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin((GGPluginCallback<PluginResult>) callback, SDKConstants.PLUGIN_KEYS.BEEPOST_SET_TAGS);
            return;
        }
        TagsData tagsData = new TagsData();
        tagsData.mTags = tags;
        tagsData.mAppId = _appId;
        tagsData.mAppKey = pushAppKey;
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.BEEPOST_SET_TAGS, tagsData, callback);
    }

    public static void GGDeleteTags(Activity activity, String tags, String pushAppKey, GGPluginCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin((GGPluginCallback<PluginResult>) callback, SDKConstants.PLUGIN_KEYS.BEEPOST_DELETE_TAGS);
            return;
        }
        TagsData tagsData = new TagsData();
        tagsData.mTags = tags;
        tagsData.mAppId = _appId;
        tagsData.mAppKey = pushAppKey;
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.BEEPOST_DELETE_TAGS, tagsData, callback);
    }

    private static boolean __checkInValidityOfSession() {
        return GGLoginSession.getCurrentSession() == null || GGLoginSession.getCurrentSession().getSessionStatus() != SessionStatus.TOKEN_AVAILABLE;
    }

    public static void GGSendGameToSession(Activity activity, GGTextShare data, GGPluginCallback callback) {
        if (GGLoginSession.getCurrentSession().getSessionProvider() == GGLoginSession.SessionProvider.GARENA) {
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GAS_SHARE_TEXT, data, callback);
        }
    }

    public static void GGSendMediaToSession(Activity activity, GGPhotoShare data, GGPluginCallback callback) {
        if (GGLoginSession.getCurrentSession().getSessionProvider() == GGLoginSession.SessionProvider.GARENA) {
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GAS_SHARE_PHOTO, data, callback);
        }
    }

    public static void GGLoadFacebookInfo(Activity activity, GGPluginCallback callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.FACEBOOK_REQUEST_ME, (Object) null, callback);
    }

    public static void GGFacebookGooglePlay(Activity activity) {
        Helper.openInGooglePlay(activity, "com.facebook.katana");
    }

    public static void GGVkGooglePlay(Activity activity) {
        Helper.openInGooglePlay(activity, SDKConstants.VK_PACKAGE);
    }

    public static void GGSetEnvironment(SDKConstants.GGEnvironment environment) {
        SDKConstants.setSandboxMode(environment);
    }

    public static SDKConstants.GGEnvironment GGGetEnvironment() {
        return SDKConstants.getEnvironment();
    }

    public static void GGParseWakeUpNotify(Activity activity, WakeupNotifyCallback callback) {
        final Bundle bundle = activity.getIntent().getBundleExtra(SDKConstants.COM_GARENA_MSDK_GAME_LAUNCH_BUNDLE);
        WakeupRet wakeupRet = new WakeupRet() {
            {
                if (bundle != null) {
                    this.openId = bundle.getString(SDKConstants.COM_GARENA_MSDK_GAME_LAUNCH_OPEN_ID);
                    this.mediaTag = bundle.getString(SDKConstants.COM_GARENA_MSDK_GAME_LAUNCH_MEDIA_TAG);
                    this.fromOpenId = bundle.getString(SDKConstants.COM_GARENA_MSDK_GAME_LAUNCH_OPEN_ID_SOURCE);
                }
            }
        };
        if (callback != null && bundle != null) {
            callback.OnWakeupNotify(wakeupRet);
        }
    }

    public static void GGSendToGameFriends(Activity activity, String title, String message, String imageURL, String data, String objectId, List<String> buddies, GGPluginCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin((GGPluginCallback<PluginResult>) callback, SDKConstants.PLUGIN_KEYS.APP_FRIEND_REQUEST);
        } else if (GGLoginSession.getCurrentSession().getSessionProvider() == GGLoginSession.SessionProvider.GARENA) {
            final String str = title;
            final String str2 = message;
            final String str3 = imageURL;
            final String str4 = data;
            final String str5 = objectId;
            final List<String> list = buddies;
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.APP_FRIEND_REQUEST, new GGSendBuddyRequestPlugin.SendBuddyRequestData() {
                {
                    this.mTitle = str;
                    this.mMessageBody = str2;
                    this.mImageURL = str3;
                    this.mToken = GGLoginSession.getCurrentSession().getTokenValue().getAuthToken();
                    this.mMetaData = str4;
                    this.mObjectId = str5;
                    this.mFriendIDs = list;
                    this.mAppKey = GGLoginSession.getCurrentSession().getApplicationKey();
                }
            }, callback);
        } else {
            final String str6 = title;
            final String str7 = message;
            final String str8 = imageURL;
            final String str9 = data;
            final String str10 = objectId;
            final List<String> list2 = buddies;
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.FACEBOOK_GRAPH_SHARE, new FBGraphItemSharePlugin.FBGraphShareItem() {
                {
                    this.mTitle = str6;
                    this.mMessageBody = str7;
                    this.mImageURL = str8;
                    this.mToken = GGLoginSession.getCurrentSession().getTokenValue().getAuthToken();
                    this.mMetaData = str9;
                    this.mObjectId = str10;
                    this.mFriendIDs = list2;
                    this.mAppKey = GGLoginSession.getCurrentSession().getApplicationKey();
                }
            }, callback);
        }
    }

    public static void GGSendRequestInvitationToFacebook(Activity activity, final String aTitle, final String aMessage, GGPluginCallback callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.FACEBOOK_INVITE, new FBInvitePlugin.FBInviteData() {
            {
                this.title = aTitle;
                this.message = aMessage;
            }
        }, callback);
    }

    public static void GGSendLinkToFacebook(Activity activity, FBPostItem item, GGPluginCallback callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.FACEBOOK_SHARE_FALLBACK, item, callback);
    }

    public static void GGShareToFacebook(Activity activity, FBPostItem data, GGPluginCallback<PluginResult> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.FACEBOOK_SHARE, data, callback);
    }

    public static void GGSendMessageToFacebook(Activity activity, String aTitle, String aDescription, String aContentUrl, String aImageUrl, GGPluginCallback callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.FACEBOOK_MESSAGE, new FBMessageData.Builder().title(aTitle).description(aDescription).contentUrl(aContentUrl).imageUrl(aImageUrl).build(), callback);
    }

    public static void GGSendImageToFBMessenger(Activity activity, byte[] data, GGPluginCallback callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.FACEBOOK_MESSENGER_SEND_IMAGE, data, callback);
    }

    public static void GGSendGameRequestToFacebookUser(Activity activity, long fbUid, String title, String message, String data, GGPluginCallback<PluginResult> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.FACEBOOK_GAME_MESSAGE, new FBMessageData.Builder().uid(fbUid).title(title).description(message).data(data).build(), callback);
    }

    private static void __complainAboutLogin(GGPluginCallback<PluginResult> callback, String aSource) {
        PluginResult result = new PluginResult();
        result.message = "Please Login.";
        result.source = aSource;
        result.flag = GGErrorCode.GOP_ERROR_TOKEN.getCode().intValue();
        result.status = -1;
        callback.onPluginResult(result);
    }

    private static void __complainAboutLogin(ShareCallback callback) {
        ShareRet result = new ShareRet();
        result.flag = GGErrorCode.GOP_ERROR_TOKEN.getCode().intValue();
        result.desc = "Please Login.";
        callback.onPluginResult(result);
    }

    private static void __complainAboutLogin(UserInfoCallback callback) {
        DataModel.UserInfoRet ret = new DataModel.UserInfoRet();
        ret.flag = GGErrorCode.GOP_ERROR_TOKEN.getCode().intValue();
        callback.onPluginResult(ret);
    }

    private static void __complainAboutLogin(UserFriendIDListCallback callback, String aSource) {
        DataModel.FriendIDsRet ret = new DataModel.FriendIDsRet();
        ret.platform = -1;
        ret.flag = GGErrorCode.GOP_ERROR_TOKEN.getCode().intValue();
        callback.onPluginResult(ret);
    }

    private static void __complainAboutLogin(FriendGroupListCallback callback, String aSource) {
        DataModel.FriendGroupsRet ret = new DataModel.FriendGroupsRet();
        ret.flag = GGErrorCode.GOP_ERROR_TOKEN.getCode().intValue();
        callback.onPluginResult(ret);
    }

    private static void __complainAboutLogin(GroupFriendInfoCallback callback, String aSource) {
        DataModel.GroupFriendsInfoRet ret = new DataModel.GroupFriendsInfoRet();
        ret.flag = GGErrorCode.GOP_ERROR_TOKEN.getCode().intValue();
        callback.onPluginResult(ret);
    }

    public static String GGGetSDKVersion() {
        return SDKConstants.VERSION.VERSION_NAME;
    }

    public static void GGFeedback(Activity activity, final String text, GGPluginCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin((GGPluginCallback<PluginResult>) callback, SDKConstants.PLUGIN_KEYS.APP_FEEDBACK);
            return;
        }
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.APP_FEEDBACK, new FeedbackPlugin.FeedbackData() {
            {
                this.appId = GGLoginSession.getCurrentSession().getApplicationId();
                this.openId = GGLoginSession.getCurrentSession().getOpenId();
                this.feedback = text;
            }
        }, callback);
    }

    public static void GGEnableDebugLog() {
        SDKConstants.setDebugMode(true);
    }

    public static boolean GGIsPlatformInstalled(Activity activity, int platform) {
        switch (platform) {
            case 1:
                return Helper.isPackageInstalled(SDKConstants.GAS_PACKAGE, activity) || Helper.isPackageInstalled(SDKConstants.GAS_LITE_PACKAGE, activity);
            case 3:
                return Helper.isPackageInstalled("com.facebook.katana", activity);
            case 5:
                return Helper.isPackageInstalled(SDKConstants.VK_PACKAGE, activity);
            case 6:
                return Helper.isPackageInstalled(SDKConstants.LINE_PACKAGE, activity);
            default:
                return false;
        }
    }

    public static String GGPlatformInstalledVersion(Activity activity, int platform) {
        switch (platform) {
            case 1:
                if (Helper.isPackageInstalled(SDKConstants.GAS_PACKAGE, activity)) {
                    return Helper.getPackageVersion(activity, SDKConstants.GAS_PACKAGE);
                }
                return Helper.getPackageVersion(activity, SDKConstants.GAS_LITE_PACKAGE);
            case 3:
                return Helper.getPackageVersion(activity, "com.facebook.katana");
            case 5:
                return Helper.getPackageVersion(activity, SDKConstants.VK_PACKAGE);
            case 6:
                return Helper.getPackageVersion(activity, SDKConstants.LINE_PACKAGE);
            default:
                return "";
        }
    }

    public static int GGPlatformInstalledVersionCode(Activity activity, int platform) {
        switch (platform) {
            case 1:
                if (Helper.isPackageInstalled(SDKConstants.GAS_PACKAGE, activity)) {
                    return Helper.getPackageVersionCode(activity, SDKConstants.GAS_PACKAGE);
                }
                return Helper.getPackageVersionCode(activity, SDKConstants.GAS_LITE_PACKAGE);
            case 3:
                return Helper.getPackageVersionCode(activity, "com.facebook.katana");
            case 5:
                return Helper.getPackageVersionCode(activity, SDKConstants.VK_PACKAGE);
            case 6:
                return Helper.getPackageVersionCode(activity, SDKConstants.LINE_PACKAGE);
            default:
                return 0;
        }
    }

    public static void GGGetUserInfo(Activity activity, UserInfoCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin(callback);
        } else {
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GET_USER_INFO, (Object) null, callback);
        }
    }

    public static void GGLoadAppFriendIDs(Activity activity, UserFriendIDListCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin(callback, SDKConstants.PLUGIN_KEYS.GET_INAPP_FRIEND_ID_LIST);
        } else {
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GET_INAPP_FRIEND_ID_LIST, (Object) null, callback);
        }
    }

    public static void GGLoadFriendIDs(Activity activity, UserFriendIDListCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin(callback, SDKConstants.PLUGIN_KEYS.GET_USER_FRIEND_ID_LIST);
        } else {
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GET_USER_FRIEND_ID_LIST, (Object) null, callback);
        }
    }

    public static void GGLoadFriendsInfo(Activity activity, List<String> friendIds, FriendInfoCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin(callback, SDKConstants.PLUGIN_KEYS.GET_FRIEND_INFO);
        } else {
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GET_FRIEND_INFO, friendIds, callback);
        }
    }

    public static void GGLoadAppFriendGroups(Activity activity, FriendGroupListCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin(callback, SDKConstants.PLUGIN_KEYS.LOAD_INAPP_FRIEND_GROUPS_LIST);
        } else {
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.LOAD_INAPP_FRIEND_GROUPS_LIST, (Object) null, callback);
        }
    }

    public static void GGLoadFriendGroups(Activity activity, FriendGroupListCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin(callback, SDKConstants.PLUGIN_KEYS.LOAD_FRIEND_GROUPS_LIST);
        } else {
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.LOAD_FRIEND_GROUPS_LIST, (Object) null, callback);
        }
    }

    public static void GGLoadGroupFriendsInfo(Activity activity, List<String> friendIds, GroupFriendInfoCallback callback) {
        if (__checkInValidityOfSession()) {
            __complainAboutLogin(callback, SDKConstants.PLUGIN_KEYS.LOAD_GROUP_FRIEND_INFO);
        } else {
            GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.LOAD_GROUP_FRIEND_INFO, friendIds, callback);
        }
    }

    public static void GGSendLinkToSession(Activity activity, int scene, String mediaTagName, String url, String title, String caption, String desc, String mediaUrl, ShareCallback callback) {
        int appId = Integer.parseInt(GGLoginSession.getCurrentSession().getApplicationId());
        switch (GGLoginSession.getCurrentSession().getPlatform().intValue()) {
            case 1:
                final ShareCallback shareCallback = callback;
                GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GAS_SHARE_URL, new GGTextShare.Builder(appId).setScene(Integer.valueOf(scene)).setDesc(desc).setMediaTag(mediaTagName).setTitle(title).setUrl(url).build(), new GGPluginCallback<Object>() {
                    public void onPluginResult(Object result) {
                        PluginResult pluginResult = (PluginResult) result;
                        ShareRet ret = new ShareRet();
                        ret.platform = 1;
                        ret.flag = pluginResult.flag;
                        ret.desc = pluginResult.message;
                        shareCallback.onPluginResult(ret);
                    }
                });
                return;
            case 3:
                if (scene == 1) {
                    FBPostItem item = new FBPostItem(title, caption, desc, url, mediaUrl);
                    item.mediaTagName = mediaTagName;
                    final ShareCallback shareCallback2 = callback;
                    GGSendLinkToFacebook(activity, item, new GGPluginCallback() {
                        public void onPluginResult(Object result) {
                            PluginResult pluginResult = (PluginResult) result;
                            ShareRet ret = new ShareRet();
                            ret.platform = 3;
                            ret.flag = pluginResult.flag;
                            ret.desc = pluginResult.message;
                            shareCallback2.onPluginResult(ret);
                        }
                    });
                    return;
                }
                final ShareCallback shareCallback3 = callback;
                GGSendMessageToFacebook(activity, title, desc, url, mediaUrl, new GGPluginCallback() {
                    public void onPluginResult(Object result) {
                        PluginResult pluginResult = (PluginResult) result;
                        ShareRet ret = new ShareRet();
                        ret.platform = 3;
                        ret.flag = pluginResult.flag;
                        ret.desc = pluginResult.message;
                        shareCallback3.onPluginResult(ret);
                    }
                });
                return;
            default:
                BBLogger.e("Unsupported Platform Chosen", new Object[0]);
                return;
        }
    }

    private static void __complainAboutLogin(FriendInfoCallback callback, String getFriendInfo) {
        DataModel.FriendsInfoRet ret = new DataModel.FriendsInfoRet();
        ret.flag = GGErrorCode.GOP_ERROR_TOKEN.getCode().intValue();
        ret.platform = -1;
        callback.onPluginResult(ret);
    }

    @Deprecated
    public static void GGPushRegister(Activity activity, PushClient.PushRequest pushRequest, PushNotificationStateListener listener) {
        GPNManager.register(activity, pushRequest);
        GPNManager.getInstance().setListener(listener);
        GPNManager.getInstance().startService();
    }

    public static void GGPushRegister(Activity activity, PushClient.PushRequest pushRequest) {
        GPNManager.register(activity, pushRequest);
        GPNManager.getInstance().startService();
    }

    public static void GGPushScheduleLocal(NotificationData data, Context context, long timestamp) {
        Validate.notNull(context, "Application Context");
        Validate.notNull(data, "Notification Data");
        data.scheduleNotification(context, 1000 * timestamp);
    }

    public static void GGPushCancelLocal(Context context, int notificationID) {
        Validate.notNull(context, "Application Context");
        PushClient.cancelNotification(context, notificationID);
    }

    public static void GGPushCancelAll(Context context) {
        Validate.notNull(context, "Application Context");
        PushClient.cancelAll(context);
    }

    public static String GGGetDeviceID(Context context) {
        return String.valueOf(Math.abs(DeviceUtil.generateDeviceId(context)));
    }

    public static void setGarenaLoginTitle(String title) {
        mGarenaLoginTitle = title;
    }

    public static String getGarenaLoginTitle() {
        return mGarenaLoginTitle;
    }

    public static boolean GGIsUpdateAvailable(int versionCode) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            BBLogger.e("Calling GGIsUpdateAvailable in UI thread: you are strongly advised to call it off UI thread to avoid congesting UI flow", new Object[0]);
        }
        return UpdateManager.isUpdateDownloadedInGas(mContext, versionCode);
    }

    public static void GGInstallUpdate(int versionCode) {
        UpdateManager.installUpdateInGas(mContext, versionCode);
    }

    public static void GGOpenUrlInGas(String url) {
        Intent intent;
        if (!TextUtils.isEmpty(url)) {
            if (GGPlatformInstalledVersionCode(mContext, 1) >= 89) {
                if (url.startsWith(SDKConstants.FLOATING_MENU.ACTION_GARENA_DEEPLINK)) {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                } else {
                    intent = new Intent("com.garena.gas.intent.OPEN_GAS");
                    intent.putExtra("url", url);
                }
                try {
                    mContext.startActivity(intent);
                    return;
                } catch (ActivityNotFoundException e) {
                    BBLogger.e(e);
                }
            }
            mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        }
    }

    public static String GGGetFacebookAccessToken() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null || token.isExpired()) {
            return "";
        }
        return token.getToken();
    }

    public static void showFloatingView(Activity activity) {
        FloatDotViewUtil.show(activity);
    }

    public static void hideFloatingView(Activity activity) {
        FloatDotViewUtil.hide(activity);
    }

    public static void GGLiveLogin(Activity activity, GGPluginCallback<PluginResult> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GGLIVE_LOGIN, (Object) null, callback);
    }

    public static void GGLiveLogout(Activity activity, GGPluginCallback<PluginResult> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GGLIVE_LOGOUT, (Object) null, callback);
    }

    public static void GGLiveHeartbeat(Activity activity, GGPluginCallback<DataModel.GGLiveHeartbeatRet> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GGLIVE_HEARTBEAT, (Object) null, callback);
    }

    public static void GGLiveChannelRegister(Activity activity, String name, String desc, GGPluginCallback<PluginResult> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GGLIVE_REGISTER_CHANNEL, new GGLiveChannelRegisterData(name, desc), callback);
    }

    public static void GGLiveChannelInfoGet(Activity activity, GGPluginCallback<DataModel.GGLiveGetChannelInfoRet> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GGLIVE_GET_CHANNEL_INFO, (Object) null, callback);
    }

    public static void GGLiveChannelInfoUpdate(Activity activity, String name, String desc, GGPluginCallback<PluginResult> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GGLIVE_UPDATE_CHANNEL_INFO, new GGLiveChannelInfoUpdateData(name, desc), callback);
    }

    public static void GGLiveChannelStreamInit(Activity activity, String region, int categoryId, GGPluginCallback<DataModel.GGLiveChannelStreamInitRet> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GGLIVE_INIT_CHANNEL_STREAM, new GGLiveChannelStreamInitData(region, categoryId), callback);
    }

    public static void GGLiveChannelStreamStop(Activity activity, GGPluginCallback<PluginResult> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GGLIVE_STOP_CHANNEL_STREAM, (Object) null, callback);
    }

    public static void GGLiveChannelStreamVerify(Activity activity, String streamKey, GGPluginCallback<DataModel.GGLiveChannelStreamVerifyRet> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.GGLIVE_VERIFY_CHANNEL_STREAM, streamKey, callback);
    }

    public static void GGShareToVk(Activity activity, VKPostItem data, GGPluginCallback<PluginResult> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.VK_SHARE, data, callback);
    }

    public static void GGShareToLine(Activity activity, LinePostItem data, GGPluginCallback<PluginResult> callback) {
        GGPluginManager.getInstance().invokePlugin(activity, SDKConstants.PLUGIN_KEYS.LINE_SHARE, data, callback);
    }

    public static boolean GGCheckLineInstalled(Context context) {
        return Helper.isPackageInstalled(SDKConstants.LINE_PACKAGE, context);
    }
}
