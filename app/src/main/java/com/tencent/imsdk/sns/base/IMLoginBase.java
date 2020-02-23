package com.tencent.imsdk.sns.base;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import com.amazonaws.services.s3.internal.Constants;
import com.facebook.internal.NativeProtocol;
import com.tencent.imsdk.BuildConfig;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMHandlerThread;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.IMVersion;
import com.tencent.imsdk.stat.innerapi.IMInnerStat;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.IMRunOnUIUtils;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import com.tencent.imsdk.tool.etc.T;
import com.tencent.imsdk.tool.json.JsonSerializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class IMLoginBase extends IMSNSBase {
    private static final String CHECK_ThIRD_TOKEN = "com.tencent.imsdk.quicklogincheck";
    public static String GUID_BIND_URL = (IMConfig.getSdkServerUrl() + "/bind/bind");
    public static String GUID_CHECK_3RD_TOKEN = (IMConfig.getSdkServerUrl() + "/user/checkTokenValid");
    public static String GUID_GET_BIND_URL = (IMConfig.getSdkServerUrl() + "/bind/bindRelationInfo");
    public static String GUID_LOGIN_URL = (IMConfig.getSdkServerUrl() + "/user/login");
    public static String GUID_LOGOUT_URL = (IMConfig.getSdkServerUrl() + "/user/logout");
    public static String GUID_STRICT_LOGIN_URL = (IMConfig.getSdkServerUrl() + "/user/checkandlogin");
    private static final String HTTP_SCHEME = "http";
    static final int IMSDK_LOCAL_DATA_EXPIRE_CODE = 1002;
    static final int IMSDK_NEED_GUID_CODE = 1003;
    static final int IMSDK_NEED_LOGIN_CODE = 10;
    static final int IMSDK_NETWORK_ERROR_CODE = 4;
    static final int IMSDK_NO_CACHED_DATA_CODE = 1001;
    static final int IMSDK_NO_SUPPORT_CODE = 7;
    static final int IMSDK_SERVER_ERROR_CODE = 5;
    static final int IMSDK_SYSTEM_ERROR_CODE = 3;
    private static final String MODULE_LOGIN = "base_login";
    static final int SERVER_KEYSTORE_ERROR_CODE = -905;
    static final int SERVER_STRICT_LOGIN_ERROR_CODE = -258;
    private static boolean isNeedCheck3rdTokenWhileQuickLogin = false;
    protected static Handler mHandler;
    private static HandlerThread mHandlerThread;
    private static InnerStat.Builder mSTBuilder;
    private static volatile String mServerUrl = null;
    protected boolean isNeedCheckWithLogin = false;
    /* access modifiers changed from: protected */
    public volatile IMLoginResult loginResult = null;
    protected IMInnerStat mInnerStat;

    public enum LoginAction {
        LOGIN,
        BIND
    }

    public abstract String getChannel();

    public abstract int getChannelId();

    /* access modifiers changed from: protected */
    public String getStatVersion() {
        return IMVersion.getVersion();
    }

    /* access modifiers changed from: protected */
    public String getStatID() {
        return "login_" + getChannel().toLowerCase();
    }

    /* access modifiers changed from: protected */
    public String getStatOpenId() {
        if (this.loginResult != null) {
            return this.loginResult.openId;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void reportEvent(String function, String stage, String result, Properties properties) {
        if (this.mInnerStat != null) {
            this.mInnerStat.reportEvent(getStatID(), false, function, stage, result, getStatOpenId(), properties);
        }
    }

    /* access modifiers changed from: protected */
    public void reportEvent(String function, String stage, String result, Properties properties, boolean encrypt) {
        if (this.mInnerStat != null) {
            this.mInnerStat.reportEvent(getStatID(), false, function, stage, result, getStatOpenId(), properties, encrypt);
        }
    }

    public boolean initialize(Context context) {
        boolean retFlag = super.setContext(context);
        if (this.mInnerStat == null) {
            this.mInnerStat = new IMInnerStat(context, getStatVersion());
            this.mInnerStat.reportEvent(getStatID(), true, "initialize", "create", "success", getStatOpenId(), IMInnerStat.convertProperties(context));
        }
        mSTBuilder = new InnerStat.Builder(context, BuildConfig.VERSION_NAME);
        synchronized (mLock) {
            if (mHandler == null) {
                mHandlerThread = new HandlerThread(getSqlChannelKey());
                mHandlerThread.start();
                mHandler = new Handler(mHandlerThread.getLooper());
            }
        }
        if (mServerUrl == null) {
            mServerUrl = IMConfig.getSdkServerUrl();
            innerReportEvent("initialize", "get server url", mServerUrl != null ? "success" : "error", IMInnerStat.convertProperties(mServerUrl));
            GUID_LOGIN_URL = mServerUrl + "/user/login";
            GUID_STRICT_LOGIN_URL = mServerUrl + "/user/checkandlogin";
            GUID_LOGOUT_URL = mServerUrl + "/user/logout";
            GUID_BIND_URL = mServerUrl + "/bind/bind";
            GUID_GET_BIND_URL = mServerUrl + "/bind/bindRelationInfo";
            GUID_CHECK_3RD_TOKEN = mServerUrl + "/user/checkTokenValid";
        }
        isNeedCheck3rdTokenWhileQuickLogin = MetaDataUtil.readMetaDataFromApplication(context, CHECK_ThIRD_TOKEN, false);
        IMLogger.d("isNeedCheck3rdTokenWhileQuickLogin = " + isNeedCheck3rdTokenWhileQuickLogin);
        innerReportEvent("initialize", "check need verify token config", "success", IMInnerStat.convertProperties(isNeedCheck3rdTokenWhileQuickLogin));
        return retFlag;
    }

    public void setLoginType(String loginType) {
        IMLogger.d("not support setLoginType " + loginType);
    }

    public void setBindSubChannel(String bindSubChannel, JSONObject extras) {
        IMLogger.d("not support setBindSubChannel : " + bindSubChannel + ", " + extras);
    }

    public void strictLogin(List<String> permissionList, IMCallback<IMLoginResult> callback, boolean needGuid) {
        Properties properties = new Properties();
        if (permissionList != null) {
            properties.setProperty(NativeProtocol.RESULT_ARGS_PERMISSIONS, permissionList.toString());
        } else {
            properties.setProperty(NativeProtocol.RESULT_ARGS_PERMISSIONS, Constants.NULL_VERSION_ID);
        }
        properties.setProperty("callback", callback == null ? "true" : "false");
        properties.setProperty("needGuid", String.valueOf(needGuid));
        innerReportEvent("strictLogin", "start", "success", properties);
        this.isNeedCheckWithLogin = true;
        loginWithPermission(permissionList, callback, needGuid);
    }

    public void setLoginResult(IMLoginResult result) {
        innerReportEvent("setLoginResult", "start", "success", IMInnerStat.convertProperties((JsonSerializable) result), true);
        if (result != null) {
            this.loginResult = result;
            IMLoginSqlLiteHelper.SaveLoginData(this.currentContext, this.loginResult, getSqlChannelKey());
        } else {
            IMLogger.d("setLoginResult result is null , plz check!");
        }
        innerReportEvent("setLoginResult", "save login result", "success", IMInnerStat.convertProperties(getSqlChannelKey()));
    }

    public boolean isChannelInstalled() {
        return true;
    }

    public boolean isChannelSupportApi() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void login2IMSDK(IMCallback<IMLoginResult> callback, List<String> permission, boolean needGuid) {
        boolean z;
        final IMCallback<IMLoginResult> innerCallback;
        StringBuilder append = new StringBuilder().append(", callback != null ");
        if (callback == null) {
            z = true;
        } else {
            z = false;
        }
        IMLogger.d(append.append(z).append(", permissions = ").append(permission.toString()).append(", needGuid = ").append(needGuid).toString());
        String url = GUID_LOGIN_URL;
        if (this.isNeedCheckWithLogin) {
            this.isNeedCheckWithLogin = false;
            url = GUID_STRICT_LOGIN_URL;
            innerReportEvent("login2IMSDK", "strict mode", "success", IMInnerStat.convertProperties(GUID_STRICT_LOGIN_URL));
        }
        IMHttpClient httpClient = new IMHttpClient();
        HashMap<String, String> params = new HashMap<>();
        params.put("iChannel", String.valueOf(getChannelId()));
        params.putAll(getExtraRequestParams(LoginAction.LOGIN));
        if (callback != null) {
            innerCallback = callback;
        } else {
            innerCallback = new IMCallback<IMLoginResult>() {
                public void onSuccess(IMLoginResult result) {
                    IMLogger.d("callback is null. login result = " + result.toString());
                }

                public void onCancel() {
                    IMLogger.d("callback is null. login cancel");
                }

                public void onError(IMException exception) {
                    IMLogger.d("callback is null. login error : " + exception.getMessage());
                }
            };
        }
        innerReportEvent("login2IMSDK", "send http request", "function start ", IMInnerStat.convertProperties(url, params), true);
        httpClient.get(url, params, new IMCallback<String>() {
            public void onSuccess(final String result) {
                IMLoginBase.this.innerReportEvent("login2IMSDK", "get http response", "success", IMInnerStat.convertProperties(result), true);
                IMLoginBase.mHandler.post(new Runnable() {
                    public void run() {
                        int thirdCode = 0;
                        String thirdMessage = "";
                        try {
                            IMLoginResult iMLoginResult = new IMLoginResult(new JSONObject(result));
                            IMLoginResult channelResult = IMLoginBase.this.getChannelLoginResult(iMLoginResult.channelToken, iMLoginResult.channelPermissions, iMLoginResult.channelTokenExpire);
                            int thirdCode2 = channelResult.thirdRetCode;
                            try {
                                thirdMessage = channelResult.thirdRetMsg;
                                iMLoginResult.retExtraJson = IMLoginBase.this.combineExtraJson(iMLoginResult.retExtraJson, IMLoginBase.this.getChannelExtra());
                                if (iMLoginResult.retCode == 1) {
                                    iMLoginResult.channel = channelResult.channel;
                                    iMLoginResult.channelId = channelResult.channelId;
                                    if (!T.ckIsEmpty(channelResult.channelToken)) {
                                        iMLoginResult.channelToken = channelResult.channelToken;
                                    }
                                    if (channelResult.channelTokenExpire > 0) {
                                        iMLoginResult.channelTokenExpire = channelResult.channelTokenExpire;
                                    }
                                    if (!T.ckIsEmpty(channelResult.channelUserId)) {
                                        iMLoginResult.channelUserId = channelResult.channelUserId;
                                    }
                                    if (channelResult.channelPermissions != null && channelResult.channelPermissions.size() > 0) {
                                        iMLoginResult.channelPermissions = channelResult.channelPermissions;
                                    }
                                    iMLoginResult.imsdkRetCode = iMLoginResult.retCode;
                                    iMLoginResult.imsdkRetMsg = iMLoginResult.retMsg;
                                    iMLoginResult.thirdRetCode = channelResult.thirdRetCode;
                                    iMLoginResult.thirdRetMsg = channelResult.thirdRetMsg;
                                    IMLogger.d("login result = " + channelResult.toJSONString());
                                    IMLoginSqlLiteHelper.SaveLoginData(IMLoginBase.this.currentContext, iMLoginResult, IMLoginBase.this.getSqlChannelKey());
                                    IMLoginBase.this.loginResult = iMLoginResult;
                                    IMLoginBase.this.innerReportEvent("login2IMSDK", "login success", "success", IMInnerStat.convertProperties((JsonSerializable) iMLoginResult), true);
                                    IMRunOnUIUtils.onSuccessRunOnUIThread(IMLoginBase.this.currentContext, iMLoginResult, innerCallback);
                                    int i = thirdCode2;
                                    return;
                                }
                                if (iMLoginResult.retCode == -258) {
                                    IMLoginBase.this.innerReportEvent("login2IMSDK", "strict login error", "error", IMInnerStat.convertProperties((JsonSerializable) iMLoginResult));
                                } else if (iMLoginResult.retCode == IMLoginBase.SERVER_KEYSTORE_ERROR_CODE) {
                                    IMLogger.e("keystore sha1 error, please contact us to update the config !");
                                    IMLoginBase.this.innerReportEvent("login2IMSDK", "login keystore error", "error", IMInnerStat.convertProperties(IMHttpClient.getLastValidString()));
                                } else {
                                    IMLoginBase.this.innerReportEvent("login2IMSDK", "login server error", "error", IMInnerStat.convertProperties((JsonSerializable) iMLoginResult));
                                }
                                int thirdCode3 = iMLoginResult.retCode;
                                thirdMessage = iMLoginResult.retMsg;
                                IMException exception = new IMException(5, iMLoginResult.retMsg, 5, IMErrorMsg.getMessageByCode(5), thirdCode3, thirdMessage);
                                exception.retExtraJson = iMLoginResult.retExtraJson;
                                IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, exception, innerCallback);
                            } catch (JSONException e) {
                                e = e;
                                thirdCode = thirdCode2;
                                IMLoginBase.this.innerReportEvent("login2IMSDK", "parse http response error", "error", IMInnerStat.convertProperties((Exception) e));
                                IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(5, "parse server return data error", 5, IMErrorMsg.getMessageByCode(5), thirdCode, thirdMessage), innerCallback);
                            } catch (Exception e2) {
                                e = e2;
                                thirdCode = thirdCode2;
                                IMLoginBase.this.innerReportEvent("login2IMSDK", "deal http response error", "error", IMInnerStat.convertProperties(e));
                                IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(4, e.getMessage(), 4, IMErrorMsg.getMessageByCode(5), thirdCode, thirdMessage), innerCallback);
                            }
                        } catch (JSONException e3) {
                            e = e3;
                        } catch (Exception e4) {
                            e = e4;
                            IMLoginBase.this.innerReportEvent("login2IMSDK", "deal http response error", "error", IMInnerStat.convertProperties(e));
                            IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(4, e.getMessage(), 4, IMErrorMsg.getMessageByCode(5), thirdCode, thirdMessage), innerCallback);
                        }
                    }
                });
            }

            public void onCancel() {
                IMLoginBase.this.innerReportEvent("login2IMSDK", "http response cancel", "error", new Properties());
                IMRunOnUIUtils.onCancelRunOnUIThread(IMLoginBase.this.currentContext, innerCallback);
            }

            public void onError(IMException exception) {
                IMLoginBase.this.innerReportEvent("login2IMSDK", "send http request error", "error", IMInnerStat.convertProperties(exception));
                IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, exception, innerCallback);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void login2Channel(LoginAction action, @Nullable List<String> permissionsList, IMCallback innerCallback) {
        if (permissionsList != null) {
            IMLogger.d("permissions = " + permissionsList.toString() + ", callback == null ? " + (innerCallback == null) + ", loginAction = " + action.name());
        }
    }

    /* access modifiers changed from: private */
    public String getSqlChannelKey() {
        return getClass().getName();
    }

    /* access modifiers changed from: protected */
    public boolean isChannelLogin(@Nullable IMCallback<IMLoginResult> iMCallback) {
        return true;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, String> getExtraRequestParams(LoginAction action) {
        return new HashMap<>();
    }

    public boolean isLogin() {
        boolean z = true;
        try {
            if (!isChannelLogin(new IMCallback<IMLoginResult>() {
                public void onSuccess(IMLoginResult result) {
                    IMLogger.d("channel is login");
                }

                public void onCancel() {
                    IMLogger.d("check channel login canceled");
                }

                public void onError(IMException exception) {
                    IMLogger.w("check channel login failed : " + exception.getMessage());
                }
            })) {
                return false;
            }
            IMLoginResult result = IMLoginSqlLiteHelper.GetSavedLoginData(this.currentContext, getSqlChannelKey());
            if (result == null || result.retCode != 1 || result.guidTokenExpire < System.currentTimeMillis() / 1000) {
                z = false;
            }
            return z;
        } catch (Exception e) {
            IMLogger.e("check isLogin() error : " + e.getMessage());
            return false;
        }
    }

    public IMLoginResult getLoginResult() {
        if (this.loginResult == null && isLogin()) {
            this.loginResult = IMLoginSqlLiteHelper.GetSavedLoginData(this.currentContext, getSqlChannelKey());
        }
        return this.loginResult;
    }

    private void check3RDTokenValidWhileQuickLogin(final IMLoginResult result, final IMCallback<IMLoginResult> callback) {
        IMLogger.d("isNeedCheck3rdTokenWhileQuickLogin = " + isNeedCheck3rdTokenWhileQuickLogin);
        mHandler.post(new Runnable() {
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("iOpenid", result.openId);
                params.put("sInnerToken", result.guidToken);
                params.put("iChannel", String.valueOf(result.channelId));
                IMLoginBase.this.innerReportEvent("check3RDTokenValidWhileQuickLogin", "send http request", "function start ", IMInnerStat.convertProperties(IMLoginBase.GUID_CHECK_3RD_TOKEN, params), true);
                IMSNSBase.httpClient.get(IMLoginBase.GUID_CHECK_3RD_TOKEN, params, new IMCallback<String>() {
                    public void onSuccess(String netResult) {
                        String errorMessage;
                        IMLoginBase.this.innerReportEvent("check3RDTokenValidWhileQuickLogin", "get http response", "success", IMInnerStat.convertProperties(netResult), true);
                        try {
                            IMResult tmResult = new IMResult(netResult);
                            if (tmResult.retCode == 1) {
                                IMLoginBase.this.innerReportEvent("check3RDTokenValidWhileQuickLogin", "check success", "success", IMInnerStat.convertProperties((JsonSerializable) tmResult), true);
                                IMRunOnUIUtils.onSuccessRunOnUIThread(IMLoginBase.this.currentContext, result, callback);
                                return;
                            }
                            IMLoginBase.this.innerReportEvent("check3RDTokenValidWhileQuickLogin", "check error", "error", IMInnerStat.convertProperties((JsonSerializable) tmResult));
                            IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(10, "Third token valid error", 10, IMErrorMsg.getMessageByCode(10), tmResult.retCode, tmResult.retMsg), callback);
                        } catch (JSONException e) {
                            IMLoginBase.this.innerReportEvent("check3RDTokenValidWhileQuickLogin", "parse http response error", "error", IMInnerStat.convertProperties((Exception) e));
                            errorMessage = "Third token valid error" + e.getMessage();
                            IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(3, errorMessage, 3, IMErrorMsg.getMessageByCode(3)), callback);
                        } catch (Exception e2) {
                            IMLoginBase.this.innerReportEvent("check3RDTokenValidWhileQuickLogin", "deal http response error", "error", IMInnerStat.convertProperties(e2));
                            errorMessage = "Third token valid error" + e2.getMessage();
                            IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(3, errorMessage, 3, IMErrorMsg.getMessageByCode(3)), callback);
                        }
                    }

                    public void onCancel() {
                        IMLoginBase.this.innerReportEvent("check3RDTokenValidWhileQuickLogin", "http response cancel", "error", new Properties());
                        IMRunOnUIUtils.onCancelRunOnUIThread(IMLoginBase.this.currentContext, callback);
                    }

                    public void onError(IMException exception) {
                        IMLoginBase.this.innerReportEvent("check3RDTokenValidWhileQuickLogin", "send http request error", "error", IMInnerStat.convertProperties(exception));
                        IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, exception, callback);
                    }
                });
            }
        });
    }

    public void quickLogin(IMCallback<IMLoginResult> callback) {
        if (isChannelLogin(callback)) {
            IMLoginResult result = IMLoginSqlLiteHelper.GetSavedLoginData(this.currentContext, getSqlChannelKey());
            if (result == null) {
                innerReportEvent("quickLogin", "no local cached data", "error", new Properties());
                IMRunOnUIUtils.onErrorRunOnUIThread(this.currentContext, new IMException(1000, "saved login data is null", 1001, IMErrorMsg.getMessageByCode(1001)), callback);
            } else if (result.channelTokenExpire > System.currentTimeMillis() / 1000) {
                IMLogger.d("isNeedCheck3rdTokenWhileQuickLogin = " + isNeedCheck3rdTokenWhileQuickLogin);
                if (isNeedCheck3rdTokenWhileQuickLogin) {
                    innerReportEvent("quickLogin", "need check token", "success", new Properties());
                    check3RDTokenValidWhileQuickLogin(result, callback);
                    return;
                }
                innerReportEvent("quickLogin", "quick login success", "success", IMInnerStat.convertProperties((JsonSerializable) result), true);
                IMRunOnUIUtils.onSuccessRunOnUIThread(this.currentContext, result, callback);
            } else {
                innerReportEvent("quickLogin", "quick data expired", "error", IMInnerStat.convertProperties((JsonSerializable) result));
                IMRunOnUIUtils.onErrorRunOnUIThread(this.currentContext, new IMException(1000, "imsdk login data expired", 1002, IMErrorMsg.getMessageByCode(1002)), callback);
            }
        }
    }

    public void loginWithPermission(final List<String> permissionList, final IMCallback<IMLoginResult> callback, final boolean needGuid) {
        login2Channel(LoginAction.LOGIN, permissionList, new IMCallback<Object>() {
            public void onSuccess(Object res) {
                IMLoginBase.this.innerReportEvent("loginWithPermission", "onSuccess", "success", new Properties());
                IMLoginBase.this.login2IMSDK(callback, permissionList, needGuid);
            }

            public void onCancel() {
                IMLoginBase.this.innerReportEvent("loginWithPermission", "onCancel", "cancel", new Properties());
                IMRunOnUIUtils.onCancelRunOnUIThread(IMLoginBase.this.currentContext, callback);
            }

            public void onError(IMException exception) {
                IMLoginBase.this.innerReportEvent("loginWithPermission", "onError", "error", IMInnerStat.convertProperties(exception));
                IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, exception, callback);
            }
        });
    }

    /* access modifiers changed from: private */
    public void need2Logout(boolean isLogin) {
        if (!isLogin) {
            logout();
        }
    }

    /* access modifiers changed from: private */
    public void sendBindRequest(final boolean isLogin, final IMLoginResult srcLoginResult, final IMCallback<IMLoginResult> callback) {
        if (srcLoginResult == null || srcLoginResult.retCode != 1) {
            innerReportEvent("sendBindRequest", "need login before bind", "error", IMInnerStat.convertProperties((JsonSerializable) srcLoginResult), true);
            IMRunOnUIUtils.onErrorRunOnUIThread(this.currentContext, 10, "need login to one channel first", callback);
            return;
        }
        mHandler.post(new Runnable() {
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("iOpenid", srcLoginResult.openId);
                params.put("iChannel", String.valueOf(srcLoginResult.channelId));
                params.put("sInnerToken", srcLoginResult.guidToken);
                params.put("iBindChannel", String.valueOf(IMLoginBase.this.getChannelId()));
                params.putAll(IMLoginBase.this.getExtraRequestParams(LoginAction.BIND));
                IMLoginBase.this.innerReportEvent("sendBindRequest", "send http request", "function start ", IMInnerStat.convertProperties(IMLoginBase.GUID_BIND_URL, params), true);
                IMSNSBase.httpClient.get(IMLoginBase.GUID_BIND_URL, params, new IMCallback<String>() {
                    public void onSuccess(String result) {
                        try {
                            IMLoginBase.this.innerReportEvent("sendBindRequest", "get http response", "success", IMInnerStat.convertProperties(result), true);
                            IMLoginResult bindResult = new IMLoginResult(result);
                            bindResult.retExtraJson = IMLoginBase.this.combineExtraJson(bindResult.retExtraJson, IMLoginBase.this.getChannelExtra());
                            if (bindResult.retCode == 1) {
                                IMLogger.d("bind result : " + bindResult.toJSONString());
                                IMLoginResult channelLoginResult = IMLoginBase.this.getChannelLoginResult(bindResult.channelToken, bindResult.channelPermissions, bindResult.channelTokenExpire);
                                if (bindResult.channelId == IMLoginBase.this.getChannelId()) {
                                    bindResult.channel = channelLoginResult.channel;
                                    bindResult.channelId = channelLoginResult.channelId;
                                    bindResult.channelToken = channelLoginResult.channelToken;
                                    bindResult.channelUserId = channelLoginResult.channelUserId;
                                    bindResult.channelPermissions = channelLoginResult.channelPermissions;
                                    bindResult.channelTokenExpire = channelLoginResult.channelTokenExpire;
                                    IMLoginBase.this.setLoginResult(bindResult);
                                    IMLoginBase.this.innerReportEvent("sendBindRequest", "bind success", "success", IMInnerStat.convertProperties((JsonSerializable) bindResult), true);
                                    IMRunOnUIUtils.onSuccessRunOnUIThread(IMLoginBase.this.currentContext, bindResult, callback);
                                } else if (bindResult.channelId == srcLoginResult.channelId) {
                                    bindResult.channel = srcLoginResult.channel;
                                    bindResult.channelId = srcLoginResult.channelId;
                                    bindResult.channelToken = srcLoginResult.channelToken;
                                    bindResult.channelUserId = srcLoginResult.channelUserId;
                                    bindResult.channelTokenExpire = srcLoginResult.channelTokenExpire;
                                    bindResult.channelPermissions = srcLoginResult.channelPermissions;
                                    IMLoginBase.this.setLoginResult(bindResult);
                                    IMLoginBase.this.innerReportEvent("sendBindRequest", "bind success", "success", IMInnerStat.convertProperties((JsonSerializable) bindResult), true);
                                    IMRunOnUIUtils.onSuccessRunOnUIThread(IMLoginBase.this.currentContext, bindResult, callback);
                                } else {
                                    IMLogger.e("return channel id is " + bindResult.channelId + ", but source channel id is " + srcLoginResult.channelId + ", target channel id is " + IMLoginBase.this.getChannelId());
                                    IMLoginBase.this.innerReportEvent("sendBindRequest", "bind error", "error", new Properties());
                                    IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(10, "bind error : unknown data from server", 10, IMErrorMsg.getMessageByCode(10)), callback);
                                    IMLoginBase.this.need2Logout(isLogin);
                                }
                            } else {
                                IMException exception = new IMException(5, bindResult.retCode + ":" + bindResult.retMsg, 5, IMErrorMsg.getMessageByCode(5), bindResult.retCode, bindResult.retMsg);
                                exception.retExtraJson = bindResult.retExtraJson;
                                IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, exception, callback);
                                IMLoginBase.this.need2Logout(isLogin);
                            }
                        } catch (JSONException e) {
                            IMLoginBase.this.innerReportEvent("sendBindRequest", "parse http response error", "error", IMInnerStat.convertProperties((Exception) e));
                            IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(3, "parse bind result error", 3, IMErrorMsg.getMessageByCode(3)), callback);
                            IMLoginBase.this.need2Logout(isLogin);
                        }
                    }

                    public void onCancel() {
                        IMLoginBase.this.innerReportEvent("sendBindRequest", "http response cancel", "cancel", new Properties());
                        IMRunOnUIUtils.onCancelRunOnUIThread(IMLoginBase.this.currentContext, callback);
                        IMLoginBase.this.need2Logout(isLogin);
                    }

                    public void onError(IMException exception) {
                        IMLoginBase.this.innerReportEvent("sendBindRequest", "http response error", "error", IMInnerStat.convertProperties(exception));
                        IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, exception, callback);
                        IMLoginBase.this.need2Logout(isLogin);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: protected */
    public IMLoginResult getChannelLoginResult(String tokenOrCode, List<String> list, long channelTokenExpire) {
        IMLoginResult channelLoginResult = new IMLoginResult();
        channelLoginResult.channel = getChannel();
        channelLoginResult.channelId = getChannelId();
        return channelLoginResult;
    }

    /* access modifiers changed from: protected */
    public JSONObject getChannelExtra() {
        return null;
    }

    /* access modifiers changed from: protected */
    public String combineExtraJson(String originalExtra, JSONObject jsonObject) {
        JSONObject originalObj;
        if (jsonObject == null) {
            return originalExtra;
        }
        try {
            originalObj = new JSONObject(originalExtra);
        } catch (JSONException e) {
            originalObj = new JSONObject();
        }
        Iterator<String> sIterator = originalObj.keys();
        while (sIterator.hasNext()) {
            try {
                String key = sIterator.next();
                jsonObject.put(key, originalObj.get(key));
            } catch (JSONException e2) {
                IMLogger.w("catch exception : " + e2.getMessage());
            }
        }
        String extraJson = jsonObject.toString();
        IMLogger.d("combineExtraJson is : " + extraJson);
        return extraJson;
    }

    public void bindChannel(final IMLoginResult sourceLoginResult, final IMCallback<IMLoginResult> callback) {
        if (callback == null) {
            IMLogger.e("callback is null");
        } else if (sourceLoginResult.guidToken == null || sourceLoginResult.guidToken.length() < 0) {
            innerReportEvent("bindChannel", "need login before bind", "error", new Properties());
            IMRunOnUIUtils.onErrorRunOnUIThread(this.currentContext, new IMException(1003, "bind function need first login with guid", 1003, IMErrorMsg.getMessageByCode(1003)), callback);
        } else {
            final boolean isLogin = isLogin();
            if (!isLogin) {
                login2Channel(LoginAction.BIND, new ArrayList(), new IMCallback<Object>() {
                    public void onSuccess(Object result) {
                        IMLoginBase.this.innerReportEvent("bindChannel", "login to channel success", "success", new Properties());
                        IMLoginBase.this.sendBindRequest(isLogin, sourceLoginResult, callback);
                    }

                    public void onCancel() {
                        IMLoginBase.this.innerReportEvent("bindChannel", "login to channel cancel", "cancel", new Properties());
                        IMRunOnUIUtils.onCancelRunOnUIThread(IMLoginBase.this.currentContext, callback);
                    }

                    public void onError(IMException exception) {
                        IMLoginBase.this.innerReportEvent("bindChannel", "login to channel error", "error", IMInnerStat.convertProperties(exception));
                        IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(10, "binding error : " + exception.getMessage(), 10, IMErrorMsg.getMessageByCode(10)), callback);
                    }
                });
            } else if (getLoginResult() != null) {
                innerReportEvent("bindChannel", "login status ok", "success", IMInnerStat.convertProperties((JsonSerializable) getLoginResult()), true);
                sendBindRequest(isLogin, sourceLoginResult, callback);
            } else {
                innerReportEvent("bindChannel", "login status error", "error", new Properties());
                IMRunOnUIUtils.onErrorRunOnUIThread(this.currentContext, new IMException(10, "channel is login, process binding error", 10, IMErrorMsg.getMessageByCode(10)), callback);
            }
        }
    }

    public void logout() {
        try {
            if (this.loginResult == null) {
                this.loginResult = getLoginResult();
            }
            if (this.loginResult != null) {
                innerReportEvent("logout", "send logout request", "function start ", new Properties());
                sendIMSDKLogoutRequest(this.loginResult);
            }
        } catch (Exception e) {
            innerReportEvent("logout", "send logout request error", "error", IMInnerStat.convertProperties(e));
            IMLogger.d("logout() error : " + e.getMessage());
        }
        this.loginResult = null;
        IMLoginSqlLiteHelper.CleanSavedLoginData(this.currentContext, getSqlChannelKey());
        innerReportEvent("logout", "clean local data", "success", IMInnerStat.convertProperties(getSqlChannelKey()));
        IMLogger.d("Logout Success");
    }

    /* access modifiers changed from: protected */
    public void sendIMSDKLogoutRequest(@Nullable final IMLoginResult result) {
        if (result != null) {
            try {
                new Thread(new Runnable() {
                    public void run() {
                        if (result.retCode == 1) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("sInnerToken", result.guidToken);
                            params.put("iOpenid", result.openId);
                            params.put("iChannel", String.valueOf(result.channelId));
                            IMLoginBase.this.innerReportEvent("sendIMSDKLogoutRequest", "send http request", "function start ", IMInnerStat.convertProperties(IMLoginBase.GUID_LOGOUT_URL, params), true);
                            IMSNSBase.httpClient.get(IMLoginBase.GUID_LOGOUT_URL, params, new IMCallback<String>() {
                                public void onSuccess(String result) {
                                    IMLoginBase.this.innerReportEvent("sendIMSDKLogoutRequest", "onSuccess", "success", IMInnerStat.convertProperties(result), true);
                                    IMLogger.d("logout result : " + result);
                                }

                                public void onCancel() {
                                    IMLoginBase.this.innerReportEvent("sendIMSDKLogoutRequest", "onCancel", "cancel", new Properties());
                                }

                                public void onError(IMException exception) {
                                    IMLoginBase.this.innerReportEvent("sendIMSDKLogoutRequest", "onError", "error", IMInnerStat.convertProperties(exception));
                                    IMLogger.d("logout error : " + exception.getMessage());
                                }
                            });
                        }
                    }
                }).start();
            } catch (Exception e) {
                innerReportEvent("sendIMSDKLogoutRequest", "catch error", "error", IMInnerStat.convertProperties(e));
                IMLogger.d("send logout request error : " + e.getMessage());
            }
        }
    }

    public void getBindInfo(final IMCallback<IMLoginBindResult> callback) {
        final IMLoginResult imLoginResult = getLoginResult();
        if (imLoginResult == null || imLoginResult.retCode != 1) {
            innerReportEvent("getBindInfo", "need login", "error", IMInnerStat.convertProperties((JsonSerializable) imLoginResult), true);
            callback.onError(new IMException(10, 10));
            return;
        }
        IMHandlerThread.getHandler().post(new Runnable() {
            public void run() {
                IMHttpClient httpClient = new IMHttpClient();
                httpClient.initialize();
                Map<String, String> params = new HashMap<>();
                params.put("sInnerToken", imLoginResult.guidToken);
                params.put("iOpenid", imLoginResult.openId);
                params.put("iChannel", String.valueOf(IMLoginBase.this.getChannelId()));
                IMLoginBase.this.innerReportEvent("getBindInfo", "send http request", "function start ", IMInnerStat.convertProperties(IMLoginBase.GUID_GET_BIND_URL, params), true);
                httpClient.get(IMLoginBase.GUID_GET_BIND_URL, params, new IMCallback<String>() {
                    public void onSuccess(String result) {
                        IMLoginBase.this.reportEvent("getBindInfo", "onSuccess", "success", IMInnerStat.convertProperties(result), true);
                        try {
                            IMLoginBindResult loginBindResult = new IMLoginBindResult(result);
                            if (loginBindResult.retCode != 1) {
                                IMLoginBase.this.innerReportEvent("getBindInfo", "get bind info failed", "error", IMInnerStat.convertProperties((JsonSerializable) loginBindResult), true);
                                IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(5, loginBindResult.retMsg, 5, IMErrorMsg.getMessageByCode(5), loginBindResult.retCode, loginBindResult.retMsg), callback);
                                return;
                            }
                            IMLoginBase.this.innerReportEvent("getBindInfo", "get bind info success", "success", IMInnerStat.convertProperties((JsonSerializable) loginBindResult), true);
                            IMRunOnUIUtils.onSuccessRunOnUIThread(IMLoginBase.this.currentContext, loginBindResult, callback);
                        } catch (JSONException e) {
                            IMLoginBase.this.innerReportEvent("getBindInfo", "parse server result error", "success", IMInnerStat.convertProperties((Exception) e));
                            IMRunOnUIUtils.onErrorRunOnUIThread(IMLoginBase.this.currentContext, new IMException(5, "parse server result error !", 5, IMErrorMsg.getMessageByCode(5)), callback);
                        }
                    }

                    public void onCancel() {
                        IMLoginBase.this.innerReportEvent("getBindInfo", "onCancel", "cancel", new Properties());
                        callback.onCancel();
                    }

                    public void onError(IMException exception) {
                        IMLoginBase.this.innerReportEvent("getBindInfo", "onError", "error", IMInnerStat.convertProperties(exception));
                        callback.onError(exception);
                    }
                });
            }
        });
    }

    public void logout4callback(IMCallback<IMResult> callback) {
        IMException exception = new IMException(7);
        exception.imsdkRetCode = 7;
        callback.onError(exception);
        innerReportEvent("logout4callback", "not support", "error", IMInnerStat.convertProperties(exception));
    }

    /* access modifiers changed from: protected */
    public void innerReportEvent(String function, String stage, String result, Properties properties) {
        if (!IMConfig.getNewInnerStatFlag()) {
            reportEvent(function, stage, result, properties);
        } else if (mSTBuilder != null) {
            mSTBuilder.setMethod(function).setStage(stage).setResult(result).setExtraProp(InnerStat.convertMap(properties)).create().reportEvent();
        } else {
            IMLogger.w("mSTBuilder is null");
        }
    }

    /* access modifiers changed from: protected */
    public void innerReportEvent(String function, String stage, String result, Properties properties, boolean encrypt) {
        if (!IMConfig.getNewInnerStatFlag()) {
            reportEvent(function, stage, result, properties, encrypt);
        } else if (mSTBuilder != null) {
            mSTBuilder.setMethod(function).setStage(stage).setResult(result).setExtraProp(InnerStat.convertMap(properties)).setCrypt(encrypt).create().reportEvent();
        } else {
            IMLogger.w("mSTBuilder is null");
        }
    }
}
