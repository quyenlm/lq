package com.tencent.imsdk.push.xg;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.imsdk.tool.etc.IMLogger;

public class XGAccountManager {

    interface XGAccountCallback {
        void onDeleteAccountResult(boolean z);
    }

    static void deleteAllAccount(Context context, final XGAccountCallback callback) {
        XGPushManager.delAllAccount(context, new XGIOperateCallback() {
            public void onSuccess(Object o, int i) {
                IMLogger.d("delete all account success : " + i);
                callback.onDeleteAccountResult(true);
            }

            public void onFail(Object o, int i, String s) {
                IMLogger.w("delete all account failed : " + i);
                callback.onDeleteAccountResult(false);
            }
        });
    }

    static void makeLaunchAccountUnique(Context context, final XGAccountCallback callback) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("imsdk_xg_topic", 0);
        if (sharedPreferences == null || !sharedPreferences.getBoolean("xg_first_launch", true)) {
            callback.onDeleteAccountResult(false);
        } else {
            deleteAllAccount(context, new XGAccountCallback() {
                public void onDeleteAccountResult(boolean deleteFlag) {
                    if (deleteFlag) {
                        sharedPreferences.edit().putBoolean("xg_first_launch", false).apply();
                    }
                    callback.onDeleteAccountResult(deleteFlag);
                }
            });
        }
    }
}
