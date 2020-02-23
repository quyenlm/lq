package com.tencent.imsdk.extend.garena.base;

import android.text.TextUtils;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.sns.base.IMLoginResult;

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

    public static IMResult rebuildForSuccess(IMResult result) {
        result.imsdkRetCode = SUCCESS;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
        return result;
    }

    public static IMFriendResult rebuildForSuccess(IMFriendResult result) {
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
}
