package com.tencent.imsdk.tool.etc;

import android.app.Activity;
import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;

public final class IMRunOnUIUtils {
    private static boolean isEnvError(Context context, IMCallback callback) {
        if (callback == null) {
            IMLogger.e("callback is null");
            return true;
        } else if (context != null) {
            return false;
        } else {
            IMLogger.e("unknown error occur, context is null");
            return true;
        }
    }

    public static void onSuccessRunOnUIThread(Context context, final IMResult result, final IMCallback callback) {
        if (!isEnvError(context, callback)) {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                }
            });
        }
    }

    public static void onCancelRunOnUIThread(Context context, final IMCallback callback) {
        if (!isEnvError(context, callback)) {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    if (callback != null) {
                        callback.onCancel();
                    }
                }
            });
        }
    }

    public static void onErrorRunOnUIThread(Context context, int code, String errorMessage, IMCallback callback) {
        onErrorRunOnUIThread(context, new IMException(code, errorMessage), callback);
    }

    public static void onErrorRunOnUIThread(Context context, final IMException ex, final IMCallback callback) {
        if (!isEnvError(context, callback)) {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    if (callback != null) {
                        callback.onError(ex);
                    }
                }
            });
        }
    }
}
