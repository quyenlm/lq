package com.tencent.imsdk.facebook.friend;

import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.sns.base.IMLoginResult;

public class IMRetCode {
    public static final int CANCELED = 2;
    public static final int FILESYSTEM_ERROR = 8;
    public static final int INITIALIZE_ERROR = 17;
    public static final int INTERFACE_DEPRECATED = 999;
    public static final int INVALID_ARGUMENT = 11;
    public static final int NEED_CONFIGURATION = 13;
    public static final int NEED_INSTALL_APP = 15;
    public static final int NEED_LOGIN = 10;
    public static final int NEED_PLUGIN_PACKAGE = 9;
    public static final int NEED_SET_CHANNEL = 18;
    public static final int NEED_SYSTEM_PERMISSION = 12;
    public static final int NEED_UPGRADE_APP = 16;
    public static final int NETWORK_ERROR = 4;
    public static final int NO_SUPPORT = 7;
    public static final int OPERATION_TIMEOUT = 6;
    public static final int RETURN_THIRD = 9999;
    public static final int SERVER_ERROR = 5;
    public static final int SERVICE_REFUSED = 14;
    public static final int SUCCESS = 1;
    public static final int SYSTEM_ERROR = 3;
    public static final int THIRD_DEF_ERROR = -1;
    public static final int THIRD_DEF_SUCCESS = 0;
    public static final int UNKNOWN_ERROR = 0;

    public static IMException rebuild(IMException exception, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
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

    public static IMResult rebuild(IMResult result, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
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

    public static IMResult rebuildForSuccess(IMResult result) {
        result.imsdkRetCode = 1;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
        return result;
    }

    public static IMFriendResult rebuildForSuccess(IMFriendResult result) {
        result.imsdkRetCode = 1;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
        return result;
    }

    public static IMLoginResult rebuild(IMLoginResult result, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
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
