package com.tencent.midas.oversea.api;

public interface IAPPayUpdateCallBack {
    public static final int RET_ERROR = 1;
    public static final int RET_SUCCESS = 0;

    void onUpdate(int i, String str);
}
