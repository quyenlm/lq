package com.tencent.imsdk.push.base;

import android.app.Activity;
import android.content.Context;
import com.tencent.imsdk.push.api.IMOperateCallback;
import com.tencent.imsdk.push.api.IMPushListener;
import com.tencent.imsdk.push.entity.IMLocalMessage;
import com.tencent.imsdk.tool.etc.IMLogger;

public abstract class IMPushBase {
    public Context context;

    public abstract void deleteTag(Context context2, String str);

    public abstract void onActivityStoped(Activity activity);

    public abstract void registerPush(Context context2);

    public abstract void registerPush(Context context2, IMOperateCallback iMOperateCallback);

    public abstract void registerPush(Context context2, String str);

    public abstract void registerPush(Context context2, String str, IMOperateCallback iMOperateCallback);

    public abstract void registerPush(Context context2, String str, String str2, int i, String str3, IMOperateCallback iMOperateCallback);

    public abstract void setPushListener(IMPushListener iMPushListener);

    public abstract void setTag(Context context2, String str);

    public abstract void unregisterPush(Context context2);

    protected IMPushBase() {
    }

    public void initialize(Context ctx) {
        this.context = ctx;
    }

    public String toString() {
        return "IMPushBase{context=" + this.context + '}';
    }

    public void addLocalNotification(IMLocalMessage localMessage) {
        IMLogger.w("not implements yet");
    }

    public void clearLocalNotifications() {
        IMLogger.w("not implements yet");
    }
}
