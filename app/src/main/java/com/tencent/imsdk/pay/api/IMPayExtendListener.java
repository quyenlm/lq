package com.tencent.imsdk.pay.api;

import java.util.List;

public abstract class IMPayExtendListener {
    public abstract void onLoginExpiry();

    public abstract void onPayCallBack(Object obj);

    public void onGetProductCallBack(int retCode, String retMsg, List<Object> list) {
    }

    public void onGetMpCallBack(Object retObject) {
    }

    public void onPayUpdateCallBack(int retCode, String info) {
    }
}
