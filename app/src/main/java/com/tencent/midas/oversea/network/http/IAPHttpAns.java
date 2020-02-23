package com.tencent.midas.oversea.network.http;

public interface IAPHttpAns {
    void onError(APBaseHttpReq aPBaseHttpReq, int i, String str);

    void onFinish(APBaseHttpReq aPBaseHttpReq);

    void onReceive(byte[] bArr, int i, long j, APBaseHttpReq aPBaseHttpReq);

    void onStart(APBaseHttpReq aPBaseHttpReq);

    void onStop(APBaseHttpReq aPBaseHttpReq);
}
