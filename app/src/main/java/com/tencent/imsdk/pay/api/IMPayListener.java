package com.tencent.imsdk.pay.api;

import com.tencent.imsdk.pay.entity.IMPayRequestInfo;
import com.tencent.imsdk.pay.entity.IMPayResponseInfo;

public interface IMPayListener {
    void onDeliverFinishFailuer(IMPayRequestInfo iMPayRequestInfo, String str, int i);

    void onDeliverFinishSuccess(IMPayRequestInfo iMPayRequestInfo);

    void onGetMpCallBack(IMPayResponseInfo iMPayResponseInfo);

    void onGetSkuCallBack(IMPayResponseInfo iMPayResponseInfo);

    void onLoginExpiry();

    void onNetWorkError(IMPayRequestInfo iMPayRequestInfo, int i);

    void onOrderFinishFailuer(IMPayRequestInfo iMPayRequestInfo, String str, int i);

    void onOrderFinishSuccess(IMPayRequestInfo iMPayRequestInfo);

    void onPayCallBack(IMPayResponseInfo iMPayResponseInfo);

    void onPurchaseFinishFailuer(IMPayRequestInfo iMPayRequestInfo, String str, int i);

    void onPurchaseFinishSuccess(IMPayRequestInfo iMPayRequestInfo);

    void onQueryProductInfoFailure(IMPayRequestInfo iMPayRequestInfo);

    void onRestoreFinishFailue(String str, int i);

    void onSetUpFinishFailure(IMPayRequestInfo iMPayRequestInfo, String str, int i);

    void onSetUpFinishSuccess();
}
