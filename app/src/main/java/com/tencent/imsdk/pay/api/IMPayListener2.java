package com.tencent.imsdk.pay.api;

import com.tencent.imsdk.pay.entity.IMPayRequestInfo;
import com.tencent.imsdk.pay.entity.IMPayResponseInfo;

public class IMPayListener2 implements IMPayListener {
    public void onPayUpdateCallBack(int retCode, String info) {
    }

    public void onQueryProductInfoFailure(IMPayRequestInfo requestInfo) {
    }

    public void onOrderFinishSuccess(IMPayRequestInfo requestInfo) {
    }

    public void onOrderFinishFailuer(IMPayRequestInfo requestInfo, String errorMessage, int code) {
    }

    public void onDeliverFinishSuccess(IMPayRequestInfo requestInfo) {
    }

    public void onDeliverFinishFailuer(IMPayRequestInfo requestInfo, String errorMessage, int code) {
    }

    public void onSetUpFinishSuccess() {
    }

    public void onSetUpFinishFailure(IMPayRequestInfo requestInfo, String errorMessage, int code) {
    }

    public void onPurchaseFinishSuccess(IMPayRequestInfo requestInfo) {
    }

    public void onPurchaseFinishFailuer(IMPayRequestInfo requestInfo, String errorMessage, int code) {
    }

    public void onRestoreFinishFailue(String errorMessage, int code) {
    }

    public void onNetWorkError(IMPayRequestInfo requestInfo, int code) {
    }

    public void onLoginExpiry() {
    }

    public void onPayCallBack(IMPayResponseInfo responseInfo) {
    }

    public void onGetMpCallBack(IMPayResponseInfo responseInfo) {
    }

    public void onGetSkuCallBack(IMPayResponseInfo responseInfo) {
    }
}
