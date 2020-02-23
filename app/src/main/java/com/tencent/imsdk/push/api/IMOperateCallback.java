package com.tencent.imsdk.push.api;

public abstract class IMOperateCallback {
    public abstract void onFail(Object obj, int i, String str);

    public abstract void onSuccess(Object obj, int i);
}
