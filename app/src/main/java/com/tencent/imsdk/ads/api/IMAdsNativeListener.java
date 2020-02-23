package com.tencent.imsdk.ads.api;

import com.tencent.imsdk.ads.entity.IMAdsResult;

public class IMAdsNativeListener {
    public static native void onAdClosed(IMAdsResult iMAdsResult);

    public static native void onAdLeftApplication(IMAdsResult iMAdsResult);

    public static native void onAdLoaded(IMAdsResult iMAdsResult);

    public static native void onAdOpened(IMAdsResult iMAdsResult);
}
