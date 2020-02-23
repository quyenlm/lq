package com.tencent.imsdk.android.friend.tools;

import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;

public class IMRetCode {
    public static final int CANCEL = 2;
    public static final int INVALID_ARGUMENT = 11;
    public static final int LOGIN_CACHE_EXPRE = 1002;
    public static final int LOGIN_NOCACHE = 1001;
    public static final int NEED_APP = 15;
    public static final int NEED_INIT = 17;
    public static final int NEED_LOGIN = 10;
    public static final int NETWORK = 4;
    public static final int NO_SUPPORT = 7;
    public static final int SERVER = 5;
    public static final int SUCCESS = 1;
    public static final int SYSTEM = 3;
    public static final int THIRD = 9999;
    public static final int UNKOWN = 0;

    public static void retByIMSDK(int imsdkRetCode, int thirdRetCode, String thirdRetMsg, IMCallback callback) {
        IMException exception = new IMException(imsdkRetCode, imsdkRetCode, thirdRetCode, thirdRetMsg);
        if (callback != null) {
            callback.onError(exception);
        }
    }
}
