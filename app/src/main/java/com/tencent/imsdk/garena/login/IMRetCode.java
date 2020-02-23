package com.tencent.imsdk.garena.login;

import android.text.TextUtils;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.garena.login.GarenaLogin;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.util.Properties;

public final class IMRetCode {
    public static int CANCELED = 2;
    public static int FILESYSTEM_ERROR = 8;
    public static int INITIALIZE_ERROR = 17;
    public static int INTERFACE_DEPRECATED = 999;
    public static int INVALID_ARGUMENT = 11;
    public static int LOCAL_DATA_EXPIRED = 1002;
    public static int NEED_CONFIGURATION = 13;
    public static int NEED_GUID_DATA = 1003;
    public static int NEED_INSTALL_APP = 15;
    public static int NEED_LOGIN = 10;
    public static int NEED_PLUGIN_PACKAGE = 9;
    public static int NEED_SET_CHANNEL = 18;
    public static int NEED_SYSTEM_PERMISSION = 12;
    public static int NEED_UPGRADE_APP = 16;
    public static int NETWORK_ERROR = 4;
    public static int NO_CACHED_DATA = 1001;
    public static int NO_CACHEED_DATA = 1001;
    public static int NO_SUPPORT = 7;
    public static int OPERATION_TIMEOUT = 6;
    public static int RETURN_THIRD = 9999;
    public static int SERVER_ERROR = 5;
    public static int SERVICE_REFUSED = 14;
    public static int SUCCESS = 1;
    public static int SYSTEM_ERROR = 3;
    public static int UNKNOWN_ERROR = 0;

    public static IMException rebuild(IMException exception, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
        exception.imsdkRetCode = imsdkCode;
        exception.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
        exception.thirdRetCode = thirdCode;
        if (!TextUtils.isEmpty(thirdMsg)) {
            exception.thirdRetMsg = thirdMsg;
        }
        if (!TextUtils.isEmpty(extra)) {
            exception.retExtraJson = extra;
        }
        return exception;
    }

    public static IMResult rebuild(IMResult result, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
        result.imsdkRetCode = imsdkCode;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
        result.thirdRetCode = thirdCode;
        if (!TextUtils.isEmpty(thirdMsg)) {
            result.thirdRetMsg = thirdMsg;
        }
        if (!TextUtils.isEmpty(extra)) {
            result.retExtraJson = extra;
        }
        return result;
    }

    public static IMLoginResult rebuildForSuccess(IMLoginResult result) {
        result.imsdkRetCode = SUCCESS;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
        return result;
    }

    public static IMLoginResult rebuild(IMLoginResult result, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
        result.imsdkRetCode = imsdkCode;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
        result.thirdRetCode = thirdCode;
        if (!TextUtils.isEmpty(thirdMsg)) {
            result.thirdRetMsg = thirdMsg;
        }
        if (!TextUtils.isEmpty(extra)) {
            result.retExtraJson = extra;
        }
        return result;
    }

    public static Properties convertProperties(GarenaLogin.GarenaLoginResult loginResult) {
        Properties properties = new Properties();
        try {
            properties.setProperty(UnityPayHelper.OPENID, loginResult.openId != null ? loginResult.openId : Constants.NULL_VERSION_ID);
            properties.setProperty("loginType", loginResult.loginType != null ? loginResult.loginType : Constants.NULL_VERSION_ID);
            properties.setProperty("token", loginResult.token != null ? loginResult.token : Constants.NULL_VERSION_ID);
            properties.setProperty("refreshToken", loginResult.refreshToken != null ? loginResult.refreshToken : Constants.NULL_VERSION_ID);
            String expire = String.valueOf(loginResult.expire);
            if (expire == null) {
                expire = Constants.NULL_VERSION_ID;
            }
            properties.setProperty("expire", expire);
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
        return properties;
    }
}
