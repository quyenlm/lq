package com.tencent.imsdk.garena.login;

import MTT.EFvrECode;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.beetalk.sdk.AuthMode;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.SessionStatus;
import com.beetalk.sdk.facebook.FBClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.pay.android.GGErrorCode;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMLoginBase;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.sns.base.IMLoginSqlLiteHelper;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;

public class GarenaLogin extends IMLoginBase implements GGLoginSession.SessionCallback {
    /* access modifiers changed from: private */
    public static String GARENA_GUID_BIND_URL = (IMConfig.getSdkServerUrl() + "/bind/bind_garena");
    private static final String META_APPLICATION_ID = "com.garena.sdk.applicationId";
    private static final String META_APP_LOGIN_TITLE = "com.tencent.imsdk.garena.loginTitle";
    private static final String META_APP_SDK_KEY = "com.tencent.imsdk.garena.APP_SDK_KEY";
    private static final String META_Environment = "com.tencent.imsdk.garena.Environment";
    private static final String RETURN_ERROR_MSG_TAG = "login error,";
    private static final String VERSION = "1.16.0";
    /* access modifiers changed from: private */
    public String CHANNEL = "Garena";
    /* access modifiers changed from: private */
    public int CHANNEL_ID = 13;
    private final String DEFAULT_LOGIN_TITLE = "user login";
    private final String ENV_PRODUCTION = "PRODUCTION";
    private final String ENV_TEST = "TEST";
    private final int GARENA_TOKEN_NOT_AVAILABLE = EFvrECode._ERR_FVR_IMGCVT_ERR;
    private final int GARENA_USER_CANCEL = 2002;
    private final String PLUGIN_NAME = "imsdkgarena";
    private final String SQLITE_GARENA_CHANNEL_KEY = GarenaLogin.class.getName();
    /* access modifiers changed from: private */
    public IMCallback<IMLoginResult> bindCallback;
    /* access modifiers changed from: private */
    public GarenaBindInfo bindInfo;
    /* access modifiers changed from: private */
    public IMCallback<IMLoginResult> imCallback;
    /* access modifiers changed from: private */
    public boolean imNeedGuid = false;
    /* access modifiers changed from: private */
    public Activity mActivity;
    private String mAppEnv = "";
    private String mAppSdkId = "";
    private String mAppSdkKey = "";
    private String mBindSubChannel = "";
    /* access modifiers changed from: private */
    public IMException mException = null;
    private boolean mIsNormalLoginFlag = true;
    private String mLoginTitle = "user login";
    /* access modifiers changed from: private */
    public String mLoginType = "GRN_Gas";
    /* access modifiers changed from: private */
    public InnerStat.Builder mSTBuilder;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public static class GarenaBindInfo {
        String garenaGUOpenid;
        String garenaGUToken;
        String garenaSNSOpenId;
        String garenaSNSToken;
        String iChannel;
        String imsdkInnerToken;
        String imsdkOpenId;
    }

    public static class GarenaLoginResult {
        String Access_Token;
        long expire;
        String iWithChannel;
        String loginType;
        String openId;
        String refreshToken;
        String token;
    }

    public boolean initialize(Context context) {
        boolean returnFlag = super.initialize(context);
        this.mSTBuilder = new InnerStat.Builder(context, "1.16.0", GGPlatform.GGGetSDKVersion(), "imsdkgarena");
        IMLogger.d("java garena initialize");
        if (context == null) {
            IMLogger.d("java garena initialize failed, context is null");
            return false;
        }
        try {
            this.mActivity = (Activity) context;
            GARENA_GUID_BIND_URL = IMConfig.getSdkServerUrl() + "/bind/bind_garena";
            initMetaData(this.mActivity);
            Properties properties = new Properties();
            properties.setProperty("appKey", convertNullToEmpty(this.mAppSdkKey));
            properties.setProperty("appId", convertNullToEmpty(this.mAppSdkId));
            properties.setProperty("appEnv", convertNullToEmpty(this.mAppEnv));
            properties.setProperty("version", convertNullToEmpty("1.16.0"));
            this.mainHandler = new Handler(Looper.getMainLooper());
            if (this.mainHandler == null) {
                IMLogger.e("cannot get main handler");
            }
            GGPlatform.initialize(this.mActivity);
            if ("TEST".equalsIgnoreCase(this.mAppEnv)) {
                GGPlatform.GGSetEnvironment(SDKConstants.GGEnvironment.TEST);
                GGPlatform.GGEnableDebugLog();
            } else if ("PRODUCTION".equalsIgnoreCase(this.mAppEnv)) {
                GGPlatform.GGSetEnvironment(SDKConstants.GGEnvironment.PRODUCTION);
            } else {
                IMLogger.w("Garena Environment is empty, use PRODUCTION for default");
                GGPlatform.GGSetEnvironment(SDKConstants.GGEnvironment.PRODUCTION);
            }
            if (!TextUtils.isEmpty(this.mAppSdkId)) {
                GGPlatform.setAppId(this.mAppSdkId);
                if (TextUtils.isEmpty(this.mAppSdkKey)) {
                    IMLogger.e("Garena mAppSdkKey is empty,pls set meta : com.tencent.imsdk.garena.APP_SDK_KEY");
                    return false;
                }
                GGPlatform.setGarenaLoginTitle(this.mLoginTitle);
                return returnFlag;
            }
            IMLogger.e("Garena mAppSdkId is empty,pls set meta : com.garena.sdk.applicationId");
            return false;
        } catch (Exception e) {
            IMLogger.w("catch exceptiion : " + e.getMessage());
            if (this.mSTBuilder != null) {
                this.mSTBuilder.setMethod("initialize").setStage("check").setResult("catch exception : " + e.getMessage()).create().reportEvent();
            }
            return false;
        }
    }

    private void initMetaData(Context context) {
        if (context != null) {
            this.mAppSdkId = String.valueOf(MetaDataUtil.readMetaIntFromApplication(context, "com.garena.sdk.applicationId"));
            this.mAppSdkKey = MetaDataUtil.readMetaDataFromApplication(context, META_APP_SDK_KEY);
            this.mAppEnv = MetaDataUtil.readMetaDataFromApplication(context, META_Environment);
            String temp = MetaDataUtil.readMetaDataFromApplication(context, META_APP_LOGIN_TITLE);
            if (temp == null || temp.length() <= 0) {
                IMLogger.w("please set meta-data : com.tencent.imsdk.garena.loginTitle");
            } else {
                this.mLoginTitle = temp;
            }
            IMLogger.d("mAppSdkId:" + this.mAppSdkId);
            IMLogger.d("mAppSdkKey:" + this.mAppSdkKey);
            IMLogger.d("mAppEnv:" + this.mAppEnv);
            IMLogger.d("mLoginTitle:" + this.mLoginTitle);
        }
    }

    public String getChannel() {
        return this.CHANNEL;
    }

    public int getChannelId() {
        return this.CHANNEL_ID;
    }

    public IMLoginResult getLoginResult() {
        if (this.loginResult == null && isLogin()) {
            this.loginResult = IMLoginSqlLiteHelper.GetSavedLoginData(this.mActivity, this.SQLITE_GARENA_CHANNEL_KEY);
        }
        return this.loginResult;
    }

    public void setLoginType(String loginType) {
        if (!TextUtils.isEmpty(loginType)) {
            this.mLoginType = loginType;
        }
    }

    public boolean isLogin() {
        try {
            IMLoginResult result = IMLoginSqlLiteHelper.GetSavedLoginData(this.mActivity, this.SQLITE_GARENA_CHANNEL_KEY);
            if (result != null && result.retCode == 1 && result.guidTokenExpire > System.currentTimeMillis() / 1000) {
                return true;
            }
            return false;
        } catch (Exception e) {
            IMLogger.e("check isLogin error : " + e.getMessage());
            if (this.mSTBuilder == null) {
                return false;
            }
            this.mSTBuilder.setMethod("isLogin").setStage("check").setResult("catch exception : " + e.getMessage()).create().reportEvent();
            return false;
        }
    }

    public void loginWithPermission(List<String> list, IMCallback<IMLoginResult> callback, boolean needGuid) {
        this.mIsNormalLoginFlag = true;
        this.loginResult = null;
        IMLoginSqlLiteHelper.CleanSavedLoginData(this.mActivity, this.SQLITE_GARENA_CHANNEL_KEY);
        this.imCallback = callback;
        this.imNeedGuid = needGuid;
        if (TextUtils.isEmpty(this.mLoginType)) {
            this.imCallback.onError(IMRetCode.rebuild(new IMException(3, "login error,loginType is empty,pls setLoginType"), IMRetCode.NEED_SET_CHANNEL, -1, (String) null, (String) null));
            return;
        }
        try {
            this.mainHandler.post(new Runnable() {
                public void run() {
                    IMLogger.d("GarenaLogin loginWithPermission mLoginType:" + GarenaLogin.this.mLoginType);
                    GGLoginSession newSession = GarenaLogin.this.switchLoginType(GarenaLogin.this.mLoginType);
                    if (newSession != null) {
                        GGPlatform.initialize(newSession);
                        GGPlatform.login(GarenaLogin.this.mActivity, GarenaLogin.this);
                        IMLogger.d("GarenaLogin login");
                        return;
                    }
                    GarenaLogin.this.imCallback.onError(IMRetCode.rebuild(new IMException(3, "login error,new session is null"), IMRetCode.SYSTEM_ERROR, -1, (String) null, (String) null));
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            this.imCallback.onError(IMRetCode.rebuild(new IMException(3, "login error,catch exception : " + e.getMessage()), IMRetCode.SYSTEM_ERROR, -1, (String) null, (String) null));
        }
    }

    public void logout() {
        try {
            if (this.loginResult == null) {
                this.loginResult = getLoginResult();
            }
            if (this.loginResult.retCode == 1) {
                HashMap<String, String> params = new HashMap<>();
                params.put("sInnerToken", this.loginResult.guidToken);
                params.put("iOpenid", this.loginResult.openId);
                params.put("iChannel", String.valueOf(this.loginResult.channelId));
                httpClient.get(GUID_LOGOUT_URL, params, new IMCallback<String>() {
                    public void onSuccess(String result) {
                        IMLogger.d("logout ok");
                    }

                    public void onCancel() {
                        if (GarenaLogin.this.mSTBuilder != null) {
                            GarenaLogin.this.mSTBuilder.setMethod("logout").setResult("onCancel").create().reportEvent();
                        }
                    }

                    public void onError(IMException exception) {
                        IMLogger.d("logout error : " + exception.getMessage());
                        if (GarenaLogin.this.mSTBuilder != null) {
                            GarenaLogin.this.mSTBuilder.setMethod("logout").setResult("catch exception : " + exception.getMessage()).create().reportEvent();
                        }
                    }
                });
            }
            GGLoginSession.clearSession();
        } catch (Exception e) {
            IMLogger.d("logout process error : " + e.getMessage());
        }
        this.loginResult = null;
        IMLoginSqlLiteHelper.CleanSavedLoginData(this.currentContext, this.SQLITE_GARENA_CHANNEL_KEY);
        IMLogger.d("Logout Success");
    }

    public void quickLogin(final IMCallback<IMLoginResult> callback) {
        this.mIsNormalLoginFlag = false;
        IMLogger.d("go on imsdk quicklogin");
        if (this.mActivity == null) {
            callback.onError(IMRetCode.rebuild(new IMException(11, "login error,context is null"), IMRetCode.INITIALIZE_ERROR, -1, "context is null", (String) null));
            return;
        }
        IMLoginResult result = getLoginResult();
        if (result == null) {
            callback.onError(IMRetCode.rebuild(new IMException(1000, "login error,saved login data is null"), IMRetCode.NO_CACHED_DATA, -1, (String) null, (String) null));
        } else if (result.guid == null || result.guid.length() <= 0 || result.guidTokenExpire >= System.currentTimeMillis() / 1000) {
            try {
                if (!GGPlatform.getLastLoginSession(this.mActivity)) {
                    callback.onError(IMRetCode.rebuild(new IMException(1000, "login error,session is invalid"), IMRetCode.SYSTEM_ERROR, -1, "session is invalid", (String) null));
                    return;
                }
                IMLogger.d("quickLogin getLastLoginSession start");
                this.mainHandler.post(new Runnable() {
                    public void run() {
                        try {
                            IMCallback unused = GarenaLogin.this.imCallback = callback;
                            boolean unused2 = GarenaLogin.this.imNeedGuid = true;
                            GGPlatform.login(GarenaLogin.this.mActivity, GarenaLogin.this);
                            IMLogger.d("quickLogin getLastLoginSession end");
                        } catch (Exception e) {
                            IMLogger.e(e.getMessage());
                            callback.onError(IMRetCode.rebuild(new IMException(3), IMRetCode.SYSTEM_ERROR, -1, (String) null, (String) null));
                        }
                    }
                });
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                callback.onError(IMRetCode.rebuild(new IMException(3), IMRetCode.SYSTEM_ERROR, -1, (String) null, (String) null));
            }
        } else {
            callback.onError(IMRetCode.rebuild(new IMException(1000, "login error,login guid data expired"), IMRetCode.LOCAL_DATA_EXPIRED, -1, (String) null, (String) null));
        }
    }

    public void strictLogin(List<String> list, IMCallback<IMLoginResult> callback, boolean needGuid) {
        IMLogger.w("no support for strictLogin");
        if (callback != null) {
            IMException imException = new IMException(7, "no support for strictLogin");
            callback.onError(IMRetCode.rebuild(imException, IMRetCode.NO_SUPPORT, 7, imException.getMessage(), (String) null));
        }
    }

    public void onSessionProcessed(GGLoginSession session, Exception exception) {
        IMLogger.d("onSessionProcessed start");
        if (session.getSessionStatus() == SessionStatus.OPENING) {
            IMLogger.d("opening new session");
        } else if (exception != null) {
            IMLogger.e("Exception" + exception.getMessage());
            this.mException = new IMException((Throwable) new IMException(3, RETURN_ERROR_MSG_TAG + exception.getMessage()));
            this.imCallback.onError(IMRetCode.rebuild(this.mException, IMRetCode.RETURN_THIRD, session.getErrorCode(), exception.getMessage(), (String) null));
        } else if (session.getSessionStatus() == SessionStatus.TOKEN_AVAILABLE) {
            HashMap<String, String> tokenMap = new HashMap<>();
            String session_token = session.getTokenValue().getAuthToken();
            if (!this.mIsNormalLoginFlag) {
                checkAndGoToIMQuickLogin(session_token, this.imCallback);
                return;
            }
            tokenMap.put(GGLiveConstants.PARAM.SESSION_TOKEN, session_token);
            session.getSessionProvider();
            String open_id = session.getOpenId();
            String expire = String.valueOf(session.getTokenValue().getExpiryTimestamp());
            tokenMap.put("open_id", open_id);
            tokenMap.put("expire", expire);
            String refresh_token = session.getTokenValue().getRefreshToken();
            if (!TextUtils.isEmpty(refresh_token)) {
                tokenMap.put("refresh_token", refresh_token);
            }
            String platform = "";
            GGLoginSession.SessionProvider sessionProvider = session.getSessionProvider();
            if (GGLoginSession.SessionProvider.FACEBOOK == sessionProvider) {
                platform = "GRN_FB";
            } else if (GGLoginSession.SessionProvider.GARENA == sessionProvider) {
                platform = "GRN_Gas";
            } else if (GGLoginSession.SessionProvider.GUEST == sessionProvider) {
                platform = "GRN_GU";
            } else if (GGLoginSession.SessionProvider.GOOGLE == sessionProvider) {
                platform = "GRN_GG";
            } else if (GGLoginSession.SessionProvider.LINE == sessionProvider) {
                platform = "GRN_LN";
            } else if (GGLoginSession.SessionProvider.VK == sessionProvider) {
                platform = "GRN_VK";
            }
            tokenMap.put("platform", platform);
            IMLogger.d((Object) tokenMap);
            GarenaLoginResult mGarenaLoginResult = new GarenaLoginResult();
            mGarenaLoginResult.openId = open_id;
            mGarenaLoginResult.loginType = platform;
            mGarenaLoginResult.token = session_token;
            mGarenaLoginResult.refreshToken = refresh_token;
            mGarenaLoginResult.expire = Long.valueOf(expire).longValue();
            if (platform.equalsIgnoreCase("GRN_FB")) {
                mGarenaLoginResult.iWithChannel = "1";
                mGarenaLoginResult.Access_Token = GGPlatform.GGGetFacebookAccessToken();
            }
            loginReturn(mGarenaLoginResult);
        } else if (session.getSessionStatus() == SessionStatus.CLOSED_WITH_ERROR || session.getSessionStatus() == SessionStatus.CLOSED) {
            IMLogger.e("session.getErrorCode():" + session.getErrorCode());
            if (session.getErrorCode() == 2002) {
                this.imCallback.onCancel();
                return;
            }
            String garenaRetMsg = GGErrorCode.getErrorStringFromCode(session.getErrorCode());
            this.mException = new IMException(3, RETURN_ERROR_MSG_TAG + garenaRetMsg);
            this.imCallback.onError(IMRetCode.rebuild(this.mException, IMRetCode.RETURN_THIRD, session.getErrorCode(), garenaRetMsg, (String) null));
        } else if (session.getSessionStatus() == SessionStatus.INSPECTION_WITH_ERROR) {
            IMLogger.e("Inspection Error");
            String garenaRetMsg2 = GGErrorCode.getErrorStringFromCode(session.getErrorCode());
            this.mException = new IMException((Throwable) new IMException(3, RETURN_ERROR_MSG_TAG + garenaRetMsg2));
            this.imCallback.onError(IMRetCode.rebuild(this.mException, IMRetCode.RETURN_THIRD, session.getErrorCode(), garenaRetMsg2, (String) null));
        }
    }

    /* access modifiers changed from: protected */
    public void sendBindRequest(final IMLoginResult sourceLoginResult, final IMCallback<IMLoginResult> callback) {
        if (sourceLoginResult == null || sourceLoginResult.retCode != 1) {
            this.mException = new IMException(10, "login error,need login to one channel first");
            callback.onError(IMRetCode.rebuild(this.mException, 10, -1, (String) null, (String) null));
            return;
        }
        this.mActivity.runOnUiThread(new Runnable() {
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("sInnerToken", sourceLoginResult.guidToken);
                params.put("iOpenid", sourceLoginResult.openId);
                params.put("iChannel", String.valueOf(sourceLoginResult.channelId));
                params.put("iBindChannel", String.valueOf(GarenaLogin.this.getChannelId()));
                params.put("BindAccess_token", GarenaLogin.this.getLoginResult().channelToken);
                GarenaLogin.httpClient.get(IMLoginBase.GUID_BIND_URL, params, new IMCallback<String>() {
                    public void onSuccess(String result) {
                        try {
                            IMLoginResult bindResult = new IMLoginResult(result);
                            if (bindResult.retCode != 1) {
                                IMException unused = GarenaLogin.this.mException = new IMException(5, GarenaLogin.RETURN_ERROR_MSG_TAG + bindResult.retMsg);
                                callback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SERVER_ERROR, bindResult.retCode, bindResult.retMsg, (String) null));
                                return;
                            }
                            IMLogger.d("bind result : " + bindResult.toJSONString());
                            GarenaLogin.this.setLoginResult(bindResult);
                            callback.onSuccess(IMRetCode.rebuildForSuccess(bindResult));
                        } catch (JSONException e) {
                            IMException unused2 = GarenaLogin.this.mException = new IMException(3, "login error,parse bind result error");
                            callback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SYSTEM_ERROR, -1, e.getMessage(), (String) null));
                        }
                    }

                    public void onCancel() {
                        callback.onCancel();
                    }

                    public void onError(IMException exception) {
                        IMException unused = GarenaLogin.this.mException = new IMException(3, GarenaLogin.RETURN_ERROR_MSG_TAG + exception.getMessage());
                        callback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SYSTEM_ERROR, -1, exception.getMessage(), (String) null));
                    }
                });
            }
        });
    }

    private void getGuidData(final IMCallback<IMLoginResult> callback, final GarenaLoginResult garenaLoginResult) {
        HashMap<String, String> params = new HashMap<>();
        params.put("iGameId", String.valueOf(IMConfig.getGameId()));
        params.put("iChannel", String.valueOf(getChannelId()));
        try {
            params.put("token", URLEncoder.encode(garenaLoginResult.token, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        if (garenaLoginResult.loginType.equalsIgnoreCase("GRN_FB")) {
            IMLogger.d("getGuidData Access_Token:" + garenaLoginResult.Access_Token);
            IMLogger.d("getGuidData iWithChannel:" + garenaLoginResult.iWithChannel);
            params.put("Access_Token", garenaLoginResult.Access_Token);
            params.put("iWithChannel", garenaLoginResult.iWithChannel);
        }
        httpClient.get(GUID_LOGIN_URL, params, new IMCallback<String>() {
            public void onSuccess(final String result) {
                GarenaLogin.this.mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (result != null && result.length() != 0) {
                            String netContent = result;
                            IMLogger.d("in get guid callback : " + netContent);
                            try {
                                IMLoginResult unused = GarenaLogin.this.loginResult = new IMLoginResult(new JSONObject(netContent));
                                if (GarenaLogin.this.loginResult.retCode != 1) {
                                    IMException unused2 = GarenaLogin.this.mException = new IMException(5, GarenaLogin.RETURN_ERROR_MSG_TAG + GarenaLogin.this.loginResult.retMsg);
                                    GarenaLogin.this.loginResult.thirdRetCode = GarenaLogin.this.loginResult.retCode;
                                    GarenaLogin.this.loginResult.retCode = 5;
                                    GarenaLogin.this.loginResult.imsdkRetCode = IMRetCode.SERVER_ERROR;
                                    callback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SERVER_ERROR, GarenaLogin.this.loginResult.thirdRetCode, GarenaLogin.this.loginResult.retMsg, (String) null));
                                    return;
                                }
                                GarenaLogin.this.setChannelLoginData(garenaLoginResult);
                                GarenaLogin.this.setLoginResult(GarenaLogin.this.loginResult);
                                IMLogger.d("call on success ");
                                callback.onSuccess(IMRetCode.rebuildForSuccess(GarenaLogin.this.loginResult));
                            } catch (JSONException e) {
                                IMException unused3 = GarenaLogin.this.mException = new IMException(5, "login error,parse server return data error");
                                callback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SYSTEM_ERROR, -1, e.getMessage(), (String) null));
                            } catch (Exception e2) {
                                callback.onError(IMRetCode.rebuild(new IMException(4, GarenaLogin.RETURN_ERROR_MSG_TAG + e2.getMessage()), IMRetCode.SYSTEM_ERROR, -1, e2.getMessage(), (String) null));
                            }
                        }
                    }
                });
            }

            public void onCancel() {
                GarenaLogin.this.mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        callback.onCancel();
                    }
                });
            }

            public void onError(final IMException exception) {
                GarenaLogin.this.mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        IMException unused = GarenaLogin.this.mException = new IMException(3, GarenaLogin.RETURN_ERROR_MSG_TAG + exception.getMessage());
                        callback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SYSTEM_ERROR, -1, exception.getMessage(), (String) null));
                    }
                });
            }
        });
    }

    private void loginReturn(GarenaLoginResult mGarenaLoginResult) {
        IMLogger.d("Garena loginType:" + mGarenaLoginResult.loginType);
        IMLogger.d("Garena accessToken:" + mGarenaLoginResult.token);
        IMLogger.d("Garena expiredTime:" + mGarenaLoginResult.expire);
        if (this.imNeedGuid) {
            getGuidData(this.imCallback, mGarenaLoginResult);
            return;
        }
        this.loginResult = new IMLoginResult();
        this.loginResult.retCode = 1;
        this.loginResult.retMsg = "SUCCESS";
        this.loginResult.channelUserId = mGarenaLoginResult.openId;
        this.loginResult.channelToken = mGarenaLoginResult.token;
        this.loginResult.channelTokenExpire = mGarenaLoginResult.expire;
        this.loginResult.channelPermissions = new ArrayList();
        this.loginResult.channel = mGarenaLoginResult.loginType;
        if (mGarenaLoginResult.loginType.equalsIgnoreCase("GRN_FB")) {
            this.loginResult.channelId = 1;
        } else {
            this.loginResult.channelId = 13;
        }
        setChannelLoginData(mGarenaLoginResult);
        setLoginResult(this.loginResult);
        this.imCallback.onSuccess(IMRetCode.rebuildForSuccess(this.loginResult));
    }

    public void setLoginResult(IMLoginResult result) {
        this.loginResult = result;
        try {
            if (!TextUtils.isEmpty(this.loginResult.channel)) {
                if (this.loginResult.channel.equalsIgnoreCase("GRN_FB")) {
                    this.loginResult.channel = "Facebook";
                    this.loginResult.channelId = 1;
                }
            } else if (this.loginResult.channelId == 1) {
                this.loginResult.channel = "Facebook";
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
        IMLoginSqlLiteHelper.SaveLoginData(this.currentContext, this.loginResult, this.SQLITE_GARENA_CHANNEL_KEY);
    }

    /* access modifiers changed from: private */
    public void setChannelLoginData(GarenaLoginResult garenaLoginResult) {
        this.loginResult.channel = garenaLoginResult.loginType;
        if (!TextUtils.isEmpty(garenaLoginResult.token)) {
            this.loginResult.channelUserId = garenaLoginResult.openId;
            this.loginResult.channelToken = garenaLoginResult.token;
            this.loginResult.channelTokenExpire = Long.valueOf(garenaLoginResult.expire).longValue();
        }
    }

    /* access modifiers changed from: private */
    public GGLoginSession switchLoginType(String loginType) {
        GGLoginSession.Builder bulider = new GGLoginSession.Builder(this.mActivity);
        bulider.setApplicationId(this.mAppSdkId);
        bulider.setApplicationKey(this.mAppSdkKey);
        bulider.setAuthMode(AuthMode.LEGACY_ENABLED);
        if (loginType.equalsIgnoreCase("GRN_Gas")) {
            bulider.setSessionProvider(GGLoginSession.SessionProvider.GARENA);
        } else if (loginType.equalsIgnoreCase("GRN_GU")) {
            bulider.setSessionProvider(GGLoginSession.SessionProvider.GUEST);
        } else if (loginType.equalsIgnoreCase("GRN_FB")) {
            bulider.setSessionProvider(GGLoginSession.SessionProvider.FACEBOOK);
            bulider.setRequestCode(SDKConstants.DEFAULT_ACTIVITY_FACEBOOK_REQUEST_CODE.intValue());
        } else if (loginType.equalsIgnoreCase("GRN_GG")) {
            bulider.setSessionProvider(GGLoginSession.SessionProvider.GOOGLE);
        } else if (loginType.equalsIgnoreCase("GRN_LN")) {
            bulider.setSessionProvider(GGLoginSession.SessionProvider.LINE);
        } else if (loginType.equalsIgnoreCase("GRN_VK")) {
            bulider.setSessionProvider(GGLoginSession.SessionProvider.VK);
        } else {
            IMLogger.e("Garena login type not support, loginType=" + loginType);
            return null;
        }
        return bulider.build();
    }

    private boolean checkQuickLogin(String newToken) {
        IMLoginResult oldLoginResult = IMLogin.getLoginResult();
        if (TextUtils.isEmpty(newToken) || oldLoginResult == null || TextUtils.isEmpty(oldLoginResult.channelToken) || !newToken.equalsIgnoreCase(oldLoginResult.channelToken)) {
            return false;
        }
        return true;
    }

    private void goToIMQuickLogin(IMCallback<IMLoginResult> callback) {
        IMLogger.d("go on imsdk quicklogin");
        IMLoginResult result = getLoginResult();
        if (result != null) {
            callback.onSuccess(IMRetCode.rebuildForSuccess(result));
        } else {
            callback.onError(IMRetCode.rebuild(new IMException(3, RETURN_ERROR_MSG_TAG), IMRetCode.SYSTEM_ERROR, -1, (String) null, (String) null));
        }
    }

    private void checkAndGoToIMQuickLogin(String token, IMCallback<IMLoginResult> callback) {
        IMLogger.d("check quicklogin");
        if (checkQuickLogin(token)) {
            goToIMQuickLogin(callback);
        } else {
            callback.onError(IMRetCode.rebuild(new IMException(1000, "login error,quick login error"), IMRetCode.SYSTEM_ERROR, -1, (String) null, (String) null));
        }
    }

    public void bindChannel(IMLoginResult sourceLoginResult, IMCallback<IMLoginResult> callback) {
        IMLogger.d("bindChannel");
        if (!TextUtils.isEmpty(this.mBindSubChannel)) {
            this.bindCallback = callback;
            garenaBind(this.mBindSubChannel);
        }
    }

    public void setBindSubChannel(String bindSubChannel, JSONObject extras) {
        this.mBindSubChannel = bindSubChannel;
    }

    public void garenaBind(String bindChannel) {
        if (bindChannel.equalsIgnoreCase("GRN_GU")) {
            this.mException = new IMException(3, "login error,GRN_GU can not be bind");
            this.bindCallback.onError(IMRetCode.rebuild(this.mException, IMRetCode.SYSTEM_ERROR, 2012, "guest can not be bind", (String) null));
            return;
        }
        this.bindInfo = new GarenaBindInfo();
        IMLoginResult loginResult = IMLogin.getLoginResult();
        if (loginResult.retCode != 1) {
            this.mException = new IMException(3, "login error,you must login first");
            this.bindCallback.onError(IMRetCode.rebuild(this.mException, IMRetCode.NEED_LOGIN, -1, (String) null, (String) null));
            return;
        }
        this.bindInfo.iChannel = "13";
        this.bindInfo.imsdkOpenId = loginResult.openId;
        this.bindInfo.imsdkInnerToken = loginResult.guidToken;
        this.bindInfo.garenaGUOpenid = loginResult.channelUserId;
        this.bindInfo.garenaGUToken = loginResult.channelToken;
        GGLoginSession newSession = switchBindChannel(bindChannel);
        if (newSession == null) {
            this.mException = new IMException(3, "login error,GGLoginSession Fail");
            this.bindCallback.onError(IMRetCode.rebuild(this.mException, IMRetCode.SYSTEM_ERROR, -1, "GGLoginSession is null", (String) null));
            return;
        }
        GGPlatform.initializeForBind(newSession);
        GGPlatform.GGGetSessionForBind(this.mActivity, new GGLoginSession.SessionCallback() {
            public void onSessionProcessed(GGLoginSession session, Exception exception) {
                IMLogger.d("Bind onSessionProcessed start");
                if (session.getSessionStatus() == SessionStatus.OPENING) {
                    IMLogger.d("opening new session");
                } else if (exception != null) {
                    FBClient.clearSession(GarenaLogin.this.mActivity);
                    if (exception != null) {
                        IMException unused = GarenaLogin.this.mException = new IMException(3, GarenaLogin.RETURN_ERROR_MSG_TAG + exception.getMessage());
                        GarenaLogin.this.bindCallback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SYSTEM_ERROR, -1, exception.getMessage(), (String) null));
                    }
                } else if (session.getSessionStatus() == SessionStatus.TOKEN_AVAILABLE) {
                    HashMap<String, String> tokenMap = new HashMap<>();
                    String session_token = session.getTokenValue().getAuthToken();
                    tokenMap.put(GGLiveConstants.PARAM.SESSION_TOKEN, session_token);
                    session.getSessionProvider();
                    String open_id = session.getOpenId();
                    String expire = String.valueOf(session.getTokenValue().getExpiryTimestamp());
                    tokenMap.put("open_id", open_id);
                    tokenMap.put("expire", expire);
                    String refresh_token = session.getTokenValue().getRefreshToken();
                    if (!TextUtils.isEmpty(refresh_token)) {
                        tokenMap.put("refresh_token", refresh_token);
                    }
                    String platform = "";
                    GGLoginSession.SessionProvider sessionProvider = session.getSessionProvider();
                    if (GGLoginSession.SessionProvider.FACEBOOK == sessionProvider) {
                        platform = "GRN_FB";
                    } else if (GGLoginSession.SessionProvider.GARENA == sessionProvider) {
                        platform = "GRN_Gas";
                    } else if (GGLoginSession.SessionProvider.GUEST == sessionProvider) {
                        platform = "GRN_GU";
                    } else if (GGLoginSession.SessionProvider.GOOGLE == sessionProvider) {
                        platform = "GRN_GG";
                    } else if (GGLoginSession.SessionProvider.LINE == sessionProvider) {
                        platform = "GRN_LN";
                    } else if (GGLoginSession.SessionProvider.VK == sessionProvider) {
                        platform = "GRN_VK";
                    }
                    GarenaLogin.this.bindInfo.garenaSNSToken = session_token;
                    GarenaLogin.this.bindInfo.garenaSNSOpenId = open_id;
                    tokenMap.put("platform", platform);
                    IMLogger.d((Object) tokenMap);
                    HashMap<String, String> params = new HashMap<>();
                    params.put("sInnerToken", GarenaLogin.this.bindInfo.imsdkInnerToken);
                    params.put("iOpenid", GarenaLogin.this.bindInfo.imsdkOpenId);
                    params.put("iChannel", GarenaLogin.this.bindInfo.iChannel);
                    params.put("gu_openid", GarenaLogin.this.bindInfo.garenaGUOpenid);
                    params.put("gu_token", GarenaLogin.this.bindInfo.garenaGUToken);
                    params.put("sns_token", GarenaLogin.this.bindInfo.garenaSNSToken);
                    params.put("sns_openid", GarenaLogin.this.bindInfo.garenaSNSOpenId);
                    IMLogger.d("bind params:" + params.toString());
                    final String finalPlatform = platform;
                    final GGLoginSession gGLoginSession = session;
                    GarenaLogin.httpClient.get(GarenaLogin.GARENA_GUID_BIND_URL, params, new IMCallback<String>() {
                        public void onSuccess(String result) {
                            try {
                                IMLogger.d("onSuccess result" + result.toString());
                                IMLoginResult bindResult = new IMLoginResult(result);
                                if (bindResult.retCode != 1) {
                                    FBClient.clearSession(GarenaLogin.this.mActivity);
                                    IMException unused = GarenaLogin.this.mException = new IMException(5, GarenaLogin.RETURN_ERROR_MSG_TAG + bindResult.retMsg);
                                    GarenaLogin.this.bindCallback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SERVER_ERROR, bindResult.retCode, bindResult.retMsg, (String) null));
                                    return;
                                }
                                IMLogger.d("bind result : " + bindResult.toJSONString());
                                GGPlatform.GGResetGuest(GarenaLogin.this.mActivity);
                                if (finalPlatform.equals("Facebook") || finalPlatform.equals("GRN_FB")) {
                                    bindResult.channel = "Facebook";
                                    bindResult.channelId = 1;
                                } else {
                                    bindResult.channel = finalPlatform;
                                    bindResult.channelId = GarenaLogin.this.CHANNEL_ID;
                                }
                                bindResult.channelUserId = gGLoginSession.getOpenId();
                                bindResult.channelToken = gGLoginSession.getTokenValue().getAuthToken();
                                bindResult.channelTokenExpire = (long) gGLoginSession.getTokenValue().getExpiryTimestamp();
                                bindResult.channelPermissions = new ArrayList();
                                GarenaLogin.this.setLoginResult(bindResult);
                                GarenaLogin.this.bindCallback.onSuccess(bindResult);
                                IMLogin.setChannel(GarenaLogin.this.CHANNEL);
                            } catch (JSONException e) {
                                FBClient.clearSession(GarenaLogin.this.mActivity);
                                IMException unused2 = GarenaLogin.this.mException = new IMException(3, "login error,parse bind result error");
                                GarenaLogin.this.bindCallback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SYSTEM_ERROR, -1, e.getMessage(), (String) null));
                            }
                        }

                        public void onCancel() {
                            FBClient.clearSession(GarenaLogin.this.mActivity);
                        }

                        public void onError(IMException exception) {
                            IMLogger.d("onError");
                            FBClient.clearSession(GarenaLogin.this.mActivity);
                            if (exception != null) {
                                IMException unused = GarenaLogin.this.mException = new IMException(3, GarenaLogin.RETURN_ERROR_MSG_TAG + exception.getMessage());
                                GarenaLogin.this.bindCallback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.SYSTEM_ERROR, -1, exception.getMessage(), (String) null));
                            }
                        }
                    });
                } else {
                    IMLogger.e("Exception:Session TOKEN_IS_NOT_AVAILABLE");
                    FBClient.clearSession(GarenaLogin.this.mActivity);
                    IMException unused2 = GarenaLogin.this.mException = new IMException(3, "login error,TOKEN_IS_NOT_AVAILABLE");
                    GarenaLogin.this.bindCallback.onError(IMRetCode.rebuild(GarenaLogin.this.mException, IMRetCode.RETURN_THIRD, (int) EFvrECode._ERR_FVR_IMGCVT_ERR, "Token inspection Failed", (String) null));
                }
            }
        });
    }

    private GGLoginSession switchBindChannel(String bindChannel) {
        GGLoginSession.Builder builder = new GGLoginSession.Builder(this.mActivity);
        builder.setApplicationId(this.mAppSdkId);
        builder.setApplicationKey(this.mAppSdkKey);
        builder.setAuthMode(AuthMode.LEGACY_ENABLED);
        if (bindChannel.equalsIgnoreCase("GRN_Gas")) {
            builder.setSessionProvider(GGLoginSession.SessionProvider.GARENA);
        } else if (bindChannel.equalsIgnoreCase("GRN_GG")) {
            builder.setSessionProvider(GGLoginSession.SessionProvider.GOOGLE);
        } else if (bindChannel.equalsIgnoreCase("GRN_LN")) {
            builder.setSessionProvider(GGLoginSession.SessionProvider.LINE);
        } else if (bindChannel.equalsIgnoreCase("GRN_VK")) {
            builder.setSessionProvider(GGLoginSession.SessionProvider.VK);
        } else if (bindChannel.equalsIgnoreCase("GRN_GU")) {
            builder.setSessionProvider(GGLoginSession.SessionProvider.GUEST);
        } else if (bindChannel.equalsIgnoreCase("GRN_FB")) {
            builder.setSessionProvider(GGLoginSession.SessionProvider.FACEBOOK);
        } else {
            IMLogger.e("Garena bindChannel not support, bindChannel=" + bindChannel);
            return null;
        }
        builder.setRequestCode(SDKConstants.OBTAIN_BIND_SESSION_REQUEST_CODE.intValue());
        return builder.build();
    }

    public boolean isChannelInstalled() {
        if (TextUtils.isEmpty(this.mLoginType)) {
            return false;
        }
        int installPlatform = 0;
        if (this.mLoginType.equalsIgnoreCase("GRN_Gas")) {
            installPlatform = 1;
        } else if (this.mLoginType.equalsIgnoreCase("GRN_FB")) {
            installPlatform = 3;
        } else if (this.mLoginType.equalsIgnoreCase("GRN_GU")) {
            installPlatform = 4;
        } else if (this.mLoginType.equalsIgnoreCase("GRN_VK")) {
            installPlatform = 5;
        } else if (this.mLoginType.equalsIgnoreCase("GRN_LN")) {
            installPlatform = 6;
        } else if (this.mLoginType.equalsIgnoreCase("GRN_GG")) {
            installPlatform = 8;
        }
        return GGPlatform.GGIsPlatformInstalled(this.mActivity, installPlatform);
    }

    /* access modifiers changed from: protected */
    public String getStatVersion() {
        return "1.16.0";
    }

    private String convertNullToEmpty(String value) {
        if (value != null) {
            return value;
        }
        return "";
    }
}
