package com.tencent.midas.oversea.network.http;

public interface IAPHttpAnsObserver {
    void onError(APBaseHttpAns aPBaseHttpAns);

    void onFinish(APBaseHttpAns aPBaseHttpAns);

    void onStop(APBaseHttpAns aPBaseHttpAns);
}
