package com.tencent.imsdk.sns.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.stat.innerapi.IMInnerStat;
import com.tencent.imsdk.tool.etc.DeviceUuidFactory;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MD5;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class GuestLogin extends IMLoginBase {
    private static final String CHANNEL = "Guest";
    private static final int CHANNEL_ID = 5;
    private static final String INNER_STAT_EVENT_ID = "login_guestLogin";
    private static final int SERVER_VALID_KEY_ERROR = -105;
    private static final String VERSION = "1.11.2";
    /* access modifiers changed from: private */
    public final String SQLITE_CHANNEL_KEY = GuestLogin.class.getName();
    /* access modifiers changed from: private */
    public IMRetCode mIMRetCode;
    /* access modifiers changed from: private */
    public IMInnerStat mInnerStat;
    /* access modifiers changed from: private */
    public boolean strictLoginFlag = false;

    private static final class GuestLoginHolder {
        static final GuestLogin instance = ((GuestLogin) IMModules.getInstance().getModule(GuestLogin.class.getName()));

        private GuestLoginHolder() {
        }
    }

    public static GuestLogin getInstance() {
        return GuestLoginHolder.instance;
    }

    public boolean initialize(Context context) {
        super.initialize(context);
        if (this.mInnerStat == null) {
            this.mInnerStat = new IMInnerStat(context, VERSION);
            this.mInnerStat.setDebuggable(false);
            this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, true, "initialize", "function start ", "success", getStatOpenId(), this.mInnerStat.setProperty("version", VERSION).build());
        }
        if (this.mIMRetCode == null) {
            this.mIMRetCode = new IMRetCode();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public String getStatOpenId() {
        IMLoginResult loginResult = getLoginResult();
        if (loginResult == null || loginResult.openId == null) {
            return "";
        }
        return loginResult.openId;
    }

    /* access modifiers changed from: protected */
    public String getStatVersion() {
        return VERSION;
    }

    public String getChannel() {
        return CHANNEL;
    }

    public int getChannelId() {
        return 5;
    }

    /* access modifiers changed from: private */
    public String getResultErrorMessage(IMResult result) {
        if (result == null) {
            return "IMLoginResult is null";
        }
        return "[ retMsg = " + result.retMsg + ", imsdkRetMsg = " + result.imsdkRetMsg + ", thirdRetMsg = " + result.thirdRetMsg + "]";
    }

    public void quickLogin(IMCallback<IMLoginResult> callback) {
        IMLoginResult result = IMLoginSqlLiteHelper.GetSavedLoginData(this.currentContext, this.SQLITE_CHANNEL_KEY);
        this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "quickLogin", "function start ", result != null ? "success" : "error", getStatOpenId(), this.mInnerStat.setProperty("IMLoginResult", result != null ? getResultErrorMessage(result) : Constants.NULL_VERSION_ID).build());
        if (result == null) {
            IMException exception = new IMException(1000, "saved login data is null");
            this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "quickLogin", "function end ", "no login guid data", getStatOpenId(), this.mInnerStat.setProperty("IMLoginResult", Constants.NULL_VERSION_ID).build());
            IMRetCode iMRetCode = this.mIMRetCode;
            IMRetCode iMRetCode2 = this.mIMRetCode;
            IMRetCode iMRetCode3 = this.mIMRetCode;
            callback.onError(iMRetCode.rebuild(exception, 1001, -1, (String) null, (String) null));
        } else if (result.channelTokenExpire <= System.currentTimeMillis() / 1000) {
            this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "quickLogin", "function end ", "saved login data expired", result.openId, this.mInnerStat.setProperty("IMLoginResult", getResultErrorMessage(result)).build());
            IMException exception2 = new IMException(1000, "saved login data expired");
            IMRetCode iMRetCode4 = this.mIMRetCode;
            IMRetCode iMRetCode5 = this.mIMRetCode;
            IMRetCode iMRetCode6 = this.mIMRetCode;
            callback.onError(iMRetCode4.rebuild(exception2, 1002, -1, (String) null, (String) null));
        } else if (result.guid.length() <= 0 || result.guidTokenExpire >= System.currentTimeMillis() / 1000) {
            this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "quickLogin", "function end ", "success", result.openId, this.mInnerStat.setProperty("IMLoginResult", getResultErrorMessage(result)).build());
            callback.onSuccess(this.mIMRetCode.rebuildForSuccess(result));
        } else {
            this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "quickLogin", "function end ", "login guid data expired", result.openId, this.mInnerStat.setProperty("IMLoginResult", getResultErrorMessage(result)).build());
            IMException exception3 = new IMException(1000, "login guid data expired");
            IMRetCode iMRetCode7 = this.mIMRetCode;
            IMRetCode iMRetCode8 = this.mIMRetCode;
            IMRetCode iMRetCode9 = this.mIMRetCode;
            callback.onError(iMRetCode7.rebuild(exception3, 1002, -1, (String) null, (String) null));
        }
    }

    public boolean isLogin() {
        try {
            IMLoginResult result = IMLoginSqlLiteHelper.GetSavedLoginData(this.currentContext, this.SQLITE_CHANNEL_KEY);
            if (result == null || result.retCode != 1 || result.imsdkRetCode != 1 || result.guidTokenExpire <= System.currentTimeMillis() / 1000 || result.channelTokenExpire <= System.currentTimeMillis() / 1000) {
                return false;
            }
            return true;
        } catch (Exception e) {
            IMLogger.e("check login error : " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "logout", "imsdk logout start", "function start ", getStatOpenId(), this.mInnerStat.setProperty("IMLoginResult", getResultErrorMessage(this.loginResult)).build());
        try {
            if (this.loginResult == null) {
                this.loginResult = getLoginResult();
            }
            sendIMSDKLogoutRequest(this.loginResult);
        } catch (Exception e) {
            IMLogger.d("logout process error : " + e.getMessage());
            this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "logout", "imsdk logout error", "error", getStatOpenId(), this.mInnerStat.setProperty("IMLoginResult", getResultErrorMessage(this.loginResult)).build());
        }
        this.loginResult = null;
        IMLoginSqlLiteHelper.CleanSavedLoginData(this.currentContext, this.SQLITE_CHANNEL_KEY);
        IMLogger.d("Logout Success");
    }

    private String getUniqueDeviceID() {
        String tempStr = Build.SERIAL + Build.HARDWARE + Build.DEVICE + Build.FINGERPRINT + Build.USER + Build.ID + Build.MANUFACTURER;
        IMLogger.d("device info string : " + tempStr);
        return MD5.getMD5(tempStr);
    }

    /* access modifiers changed from: private */
    public void reportValidKeyErrorEvent(HashMap<String, String> params) {
        if (!params.containsKey("iPlatform")) {
            params.put("iPlatform", IMConfig.getPlatform());
        }
        if (!params.containsKey("sRefer")) {
            params.put("sRefer", IMConfig.getInstallSource());
        }
        ArrayList<String> sorter = new ArrayList<>();
        for (String key : params.keySet()) {
            sorter.add(key);
        }
        Collections.sort(sorter);
        String valueString = "";
        Iterator<String> it = sorter.iterator();
        while (it.hasNext()) {
            String key2 = it.next();
            valueString = valueString + params.get(key2);
            IMLogger.d("key : " + key2 + ", value : " + params.get(key2));
        }
        String md5Key = IMConfig.getMD5Key();
        String md5String = MD5.getMD5(valueString + md5Key);
        this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "loginWithPermission", "valid key error occur", "error", getStatOpenId(), this.mInnerStat.setProperty("valueKey", valueString).setProperty("md5key", md5Key != null ? md5Key : "").setProperty("validKey", md5String).setProperty("combineKey", valueString + " , " + md5Key + " , " + md5String).build());
    }

    public void loginWithPermission(List<String> permissionList, IMCallback<IMLoginResult> callback, boolean needGuid) {
        for (int permissionIndex = 0; permissionIndex < permissionList.size(); permissionIndex++) {
            this.mInnerStat.setProperty("permission" + permissionIndex, permissionList.get(permissionIndex));
        }
        this.mInnerStat.setProperty("needGuid", String.valueOf(needGuid));
        this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, this.strictLoginFlag ? "strictLogin" : "loginWithPermission", "function start ", "success", getStatOpenId(), this.mInnerStat.build());
        this.loginResult = null;
        IMLoginSqlLiteHelper.CleanSavedLoginData(this.currentContext, this.SQLITE_CHANNEL_KEY);
        HashMap<String, String> params = new HashMap<>();
        params.put("iGameId", String.valueOf(IMConfig.getGameId()));
        DeviceUuidFactory uuidFactoryInstance = DeviceUuidFactory.getInstance(this.currentContext);
        String tmpOldUUID = uuidFactoryInstance.getOldUUID();
        if (tmpOldUUID == null) {
            params.put("sGuestId_old", getUniqueDeviceID());
        } else {
            params.put("sGuestId_old", tmpOldUUID);
        }
        params.put("sGuestId", uuidFactoryInstance.getDeviceUuid());
        params.put("iChannel", String.valueOf(getChannelId()));
        params.put("sExtra", uuidFactoryInstance.getDeviceExtra());
        String loginUrl = GUID_LOGIN_URL;
        boolean tempStrictLoginFlag = this.strictLoginFlag;
        if (this.strictLoginFlag) {
            loginUrl = GUID_STRICT_LOGIN_URL;
            this.strictLoginFlag = false;
        }
        this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, this.strictLoginFlag ? "strictLogin" : "loginWithPermission", "begin http request", "success", getStatOpenId(), this.mInnerStat.setProperty("RequestUrl", loginUrl).setProperty("RequestParams", params.toString()).build());
        final HashMap<String, String> hashMap = params;
        final IMCallback<IMLoginResult> iMCallback = callback;
        final boolean z = tempStrictLoginFlag;
        final DeviceUuidFactory deviceUuidFactory = uuidFactoryInstance;
        httpClient.get(loginUrl, params, new IMCallback<String>() {
            public void onSuccess(final String result) {
                ((Activity) GuestLogin.this.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            GuestLogin.this.mInnerStat.reportEvent(GuestLogin.INNER_STAT_EVENT_ID, false, GuestLogin.this.strictLoginFlag ? "strictLogin" : "loginWithPermission", "http request into success callback", "success", GuestLogin.this.getStatOpenId(), GuestLogin.this.mInnerStat.setProperty("LoginResultRaw", result).build(), true);
                            IMLoginResult imLoginResult = new IMLoginResult(new JSONObject(result));
                            if (imLoginResult.retCode == -105) {
                                GuestLogin.this.reportValidKeyErrorEvent(hashMap);
                            }
                            if (imLoginResult.retCode == 1) {
                                imLoginResult.channelUserId = Build.SERIAL;
                                imLoginResult.channelToken = imLoginResult.guidToken;
                                imLoginResult.channelTokenExpire = imLoginResult.guidTokenExpire;
                                imLoginResult.channelPermissions = new ArrayList();
                                imLoginResult.channel = GuestLogin.this.getChannel();
                                imLoginResult.channelId = GuestLogin.this.getChannelId();
                                IMLogger.d("guest login result = " + imLoginResult.toJSONString());
                                IMLoginSqlLiteHelper.SaveLoginData(GuestLogin.this.currentContext, imLoginResult, GuestLogin.this.SQLITE_CHANNEL_KEY);
                                if (deviceUuidFactory.getPrefsDeviceId() == null) {
                                    deviceUuidFactory.storePrefsDeviceId();
                                }
                                GuestLogin.this.loginResult = imLoginResult;
                                if (iMCallback != null) {
                                    iMCallback.onSuccess(GuestLogin.this.mIMRetCode.rebuildForSuccess(imLoginResult));
                                }
                                GuestLogin.this.mInnerStat.reportEvent(GuestLogin.INNER_STAT_EVENT_ID, false, GuestLogin.this.strictLoginFlag ? "strictLogin" : "loginWithPermission", "http request after success callback", "success", imLoginResult.openId, GuestLogin.this.mInnerStat.setProperty("IMLoginResult", GuestLogin.this.getResultErrorMessage(imLoginResult)).build());
                            } else if (iMCallback == null) {
                            } else {
                                if (!z || imLoginResult.retCode != -258) {
                                    IMException ex = new IMException(5, imLoginResult.retMsg);
                                    IMCallback iMCallback = iMCallback;
                                    IMRetCode access$300 = GuestLogin.this.mIMRetCode;
                                    IMRetCode unused = GuestLogin.this.mIMRetCode;
                                    iMCallback.onError(access$300.rebuild(ex, 5, imLoginResult.retCode, imLoginResult.retMsg, (String) null));
                                    return;
                                }
                                IMException ex2 = new IMException(1006, imLoginResult.retMsg);
                                IMCallback iMCallback2 = iMCallback;
                                IMRetCode access$3002 = GuestLogin.this.mIMRetCode;
                                IMRetCode unused2 = GuestLogin.this.mIMRetCode;
                                iMCallback2.onError(access$3002.rebuild(ex2, 5, imLoginResult.retCode, imLoginResult.retMsg, (String) null));
                            }
                        } catch (JSONException e) {
                            GuestLogin.this.mInnerStat.reportEvent(GuestLogin.INNER_STAT_EVENT_ID, false, GuestLogin.this.strictLoginFlag ? "strictLogin" : "loginWithPermission", "http request parse error", "error", GuestLogin.this.getStatOpenId(), GuestLogin.this.mInnerStat.setProperty("LoginException", e.getMessage()).build());
                            if (iMCallback != null) {
                                IMException ex3 = new IMException(5, "parse server return data error");
                                IMCallback iMCallback3 = iMCallback;
                                IMRetCode access$3003 = GuestLogin.this.mIMRetCode;
                                IMRetCode unused3 = GuestLogin.this.mIMRetCode;
                                IMRetCode unused4 = GuestLogin.this.mIMRetCode;
                                iMCallback3.onError(access$3003.rebuild(ex3, 5, -1, e.getMessage(), (String) null));
                            }
                        } catch (Exception e2) {
                            e = e2;
                            if (e == null) {
                                e = new Exception(Constants.NULL_VERSION_ID);
                            }
                            GuestLogin.this.mInnerStat.reportEvent(GuestLogin.INNER_STAT_EVENT_ID, false, GuestLogin.this.strictLoginFlag ? "strictLogin" : "loginWithPermission", "http request unknown error", "function end ", GuestLogin.this.getStatOpenId(), GuestLogin.this.mInnerStat.setProperty("LoginException", e.getMessage()).build());
                            IMException returnException = new IMException(4, e.getMessage());
                            if (iMCallback != null) {
                                IMCallback iMCallback4 = iMCallback;
                                IMRetCode access$3004 = GuestLogin.this.mIMRetCode;
                                IMRetCode unused5 = GuestLogin.this.mIMRetCode;
                                IMRetCode unused6 = GuestLogin.this.mIMRetCode;
                                iMCallback4.onError(access$3004.rebuild(returnException, 5, -1, e.getMessage(), (String) null));
                            }
                        }
                    }
                });
            }

            public void onCancel() {
                if (iMCallback != null) {
                    ((Activity) GuestLogin.this.currentContext).runOnUiThread(new Runnable() {
                        public void run() {
                            iMCallback.onCancel();
                        }
                    });
                }
            }

            public void onError(final IMException exception) {
                GuestLogin.this.mInnerStat.reportEvent(GuestLogin.INNER_STAT_EVENT_ID, false, GuestLogin.this.strictLoginFlag ? "strictLogin" : "loginWithPermission", "http request onError()", "success", GuestLogin.this.getStatOpenId(), GuestLogin.this.mInnerStat.setProperty("IMLoginResult", exception.getMessage()).build());
                ((Activity) GuestLogin.this.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        if (iMCallback != null) {
                            IMException ex = new IMException(4, exception.getMessage());
                            IMCallback iMCallback = iMCallback;
                            IMRetCode access$300 = GuestLogin.this.mIMRetCode;
                            IMRetCode unused = GuestLogin.this.mIMRetCode;
                            IMRetCode unused2 = GuestLogin.this.mIMRetCode;
                            iMCallback.onError(access$300.rebuild(ex, 4, -1, exception.getMessage(), (String) null));
                        }
                    }
                });
            }
        });
    }

    public void strictLogin(List<String> permissionList, IMCallback<IMLoginResult> callback, boolean needGuid) {
        this.strictLoginFlag = true;
        loginWithPermission(permissionList, callback, needGuid);
    }

    public IMLoginResult getLoginResult() {
        if (this.loginResult == null && isLogin()) {
            this.loginResult = IMLoginSqlLiteHelper.GetSavedLoginData(this.currentContext, this.SQLITE_CHANNEL_KEY);
        }
        return this.loginResult;
    }

    public void setLoginResult(IMLoginResult result) {
        this.loginResult = result;
        IMLoginSqlLiteHelper.SaveLoginData(this.currentContext, this.loginResult, this.SQLITE_CHANNEL_KEY);
    }

    public void bindChannel(IMLoginResult sourceLoginResult, IMCallback<IMLoginResult> callback) {
        callback.onError(new IMException(7, "cannot bind to guest"));
    }

    public static class IMRetCode {
        public static final int CANCEL = 2;
        public static final int INVALID = -1;
        public static final int LOGIN_CACHE_EXPIRE = 1002;
        public static final int LOGIN_NEED_USER_DATA = 1005;
        public static final int LOGIN_NOCACHE = 1001;
        public static final int LOGIN_UNKNOWN_ERROR = 1000;
        public static final int NEED_LOGIN = 10;
        public static final int NETWORK = 4;
        public static final int SERVER = 5;
        public static final int SUCCESS = 1;
        public static final int SYSTEM = 3;
        public static final int THIRD = 9999;
        public static final int UNKOWN = 0;

        public IMException rebuild(IMException exception, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
            exception.imsdkRetCode = imsdkCode;
            exception.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
            exception.thirdRetCode = thirdCode;
            if (thirdMsg != null && thirdMsg.length() > 0) {
                exception.thirdRetMsg = thirdMsg;
            }
            if (extra != null && extra.length() > 0) {
                exception.retExtraJson = extra;
            }
            return exception;
        }

        public IMResult rebuild(IMResult result, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
            result.imsdkRetCode = imsdkCode;
            result.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
            result.thirdRetCode = thirdCode;
            if (thirdMsg != null && thirdMsg.length() > 0) {
                result.thirdRetMsg = thirdMsg;
            }
            if (extra != null && extra.length() > 0) {
                result.retExtraJson = extra;
            }
            return result;
        }

        public IMLoginResult rebuildForSuccess(IMLoginResult result) {
            result.imsdkRetCode = 1;
            result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
            return result;
        }

        public IMLoginResult rebuild(IMLoginResult result, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
            result.imsdkRetCode = imsdkCode;
            result.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
            result.thirdRetCode = thirdCode;
            if (thirdMsg != null && thirdMsg.length() > 0) {
                result.thirdRetMsg = thirdMsg;
            }
            if (extra != null && extra.length() > 0) {
                result.retExtraJson = extra;
            }
            return result;
        }
    }
}
