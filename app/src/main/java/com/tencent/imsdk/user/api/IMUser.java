package com.tencent.imsdk.user.api;

import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.user.base.IMUserBase;
import com.tencent.imsdk.user.base.IMUserInfo;
import com.tencent.imsdk.user.base.IMUserInfoResult;

public class IMUser {
    protected static Context currentContext;
    protected static IMUserListener currentListener;
    private static IMUserBase userInstance;

    public static boolean initialize(Context context) {
        currentContext = context;
        IMConfig.initialize(currentContext);
        userInstance = new IMUserBase();
        userInstance.initialize(currentContext);
        return currentContext != null;
    }

    public static boolean isInitialized() {
        if (userInstance != null) {
            return true;
        }
        IMLogger.e("can not get instance : " + IMUserBase.class + ", did you add the package and call initialize function ?");
        return false;
    }

    public static void setListener(IMUserListener listener) {
        currentListener = listener;
    }

    public static void get() {
        if (isInitialized()) {
            userInstance.getInfo(new IMCallback<IMUserInfoResult>() {
                public void onSuccess(IMUserInfoResult result) {
                    if (IMUser.currentListener != null) {
                        IMUser.currentListener.onUser(result);
                    }
                }

                public void onCancel() {
                    if (IMUser.currentListener != null) {
                        IMUser.currentListener.onUser(new IMUserInfoResult(2));
                    }
                }

                public void onError(IMException exception) {
                    if (IMUser.currentListener != null) {
                        IMUser.currentListener.onUser(new IMUserInfoResult(exception.errorCode, exception.getMessage()));
                    }
                }
            });
        } else if (currentListener != null) {
            currentListener.onUser(new IMUserInfoResult(9, "not initialized"));
        }
    }

    public static void update(IMUserInfo info) {
        if (isInitialized()) {
            userInstance.setInfo(info, new IMCallback<IMUserInfoResult>() {
                public void onSuccess(IMUserInfoResult result) {
                    if (IMUser.currentListener != null) {
                        IMUser.currentListener.onUser(result);
                    }
                }

                public void onCancel() {
                    if (IMUser.currentListener != null) {
                        IMUser.currentListener.onUser(new IMUserInfoResult(2));
                    }
                }

                public void onError(IMException exception) {
                    if (IMUser.currentListener != null) {
                        IMUser.currentListener.onUser(new IMUserInfoResult(exception.errorCode, exception.getMessage()));
                    }
                }
            });
        } else if (currentListener != null) {
            currentListener.onUser(new IMUserInfoResult(9));
        }
    }
}
