package com.tencent.midas.oversea.safe;

public interface IAPGetKeyCallBack {
    void onGetKeyCancel();

    void onGetKeyFail(int i, String str);

    void onGetKeySucc(String str);

    void onLoginError();
}
