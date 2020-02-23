package com.tencent.imsdk.push.api;

public class IMPushNativeListener {
    public static native void OnDeleteTag(int i, String str);

    public static native void OnNotifactionClick(String str);

    public static native void OnNotifactionShow(String str);

    public static native void OnNotification(String str);

    public static native void OnRegister(int i, String str);

    public static native void OnSetTag(int i, String str);

    public static native void OnUnregister(int i, String str);
}
