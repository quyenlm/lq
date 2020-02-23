package com.tencent.imsdk.pay.api;

import com.tencent.imsdk.pay.entity.IMPayRequestInfo;
import com.tencent.imsdk.pay.entity.IMPayResponseInfo;

public class IMPayNativeListener {
    public static native void onDeliverFinishFailuer(IMPayRequestInfo iMPayRequestInfo, String str, int i);

    public static native void onDeliverFinishSuccess(IMPayRequestInfo iMPayRequestInfo);

    public static native void onGetMpCallBack(IMPayResponseInfo iMPayResponseInfo);

    public static native void onGetSkuCallBack(IMPayResponseInfo iMPayResponseInfo);

    public static native void onLoginExpiry();

    public static native void onNetWorkError(IMPayRequestInfo iMPayRequestInfo, int i);

    public static native void onOrderFinishFailuer(IMPayRequestInfo iMPayRequestInfo, String str, int i);

    public static native void onOrderFinishSuccess(IMPayRequestInfo iMPayRequestInfo);

    public static native void onPayCallBack(IMPayResponseInfo iMPayResponseInfo);

    public static native void onPayUpdateCallBack(int i, String str);

    public static native void onPurchaseFinishFailuer(IMPayRequestInfo iMPayRequestInfo, String str, int i);

    public static native void onPurchaseFinishSuccess(IMPayRequestInfo iMPayRequestInfo);

    public static native void onQueryProductInfoFailure(IMPayRequestInfo iMPayRequestInfo);

    public static native void onRestoreFinishFailue(String str, int i);

    public static native void onSetUpFinishFailure(IMPayRequestInfo iMPayRequestInfo, String str, int i);

    public static native void onSetUpFinishSuccess();
}
