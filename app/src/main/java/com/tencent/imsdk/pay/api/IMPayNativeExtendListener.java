package com.tencent.imsdk.pay.api;

import java.util.List;

public class IMPayNativeExtendListener {
    public static native void onGetMpCallBack(String str);

    public static native void onGetProductCallBack(int i, String str, List<String> list);

    public static native void onLoginExpiry();

    public static native void onPayCallBack(String str);

    public static native void onPayUpdateCallBack(int i, String str);
}
