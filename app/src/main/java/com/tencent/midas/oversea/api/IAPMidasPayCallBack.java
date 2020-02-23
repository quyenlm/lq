package com.tencent.midas.oversea.api;

public interface IAPMidasPayCallBack {
    void MidasPayCallBack(APMidasResponse aPMidasResponse);

    void MidasPayNeedLogin();
}
