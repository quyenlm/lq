package com.tencent.setup.google;

import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.base.IMLoginResult;

public class IMRetCode {
    public static final int CANCEL = 2;
    public static final int LOGIN_CACHE_EXPRE = 1002;
    public static final int LOGIN_NOCACHE = 1001;
    public static final int NEED_APP = 15;
    public static final int NEED_LOGIN = 10;
    public static final int NETWORK = 4;
    public static final int SERVER = 5;
    public static final int SUCCESS = 1;
    public static final int SYSTEM = 3;
    public static final int THIRD = 9999;
    public static final int UNKOWN = 0;

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

    public static IMLoginResult rebuildForSuccess(IMLoginResult result) {
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
