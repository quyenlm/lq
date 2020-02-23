package com.tencent.midas.oversea.network.modle;

import com.tencent.midas.oversea.network.http.APBaseHttpReq;
import com.tencent.midas.oversea.network.http.IAPHttpAns;
import com.tencent.midas.oversea.network.http.IAPHttpReportObserver;

public class APDataReportOnceAns implements IAPHttpAns {
    private IAPHttpReportObserver a = null;

    public APDataReportOnceAns(IAPHttpReportObserver iAPHttpReportObserver) {
        this.a = iAPHttpReportObserver;
    }

    public void onError(APBaseHttpReq aPBaseHttpReq, int i, String str) {
    }

    public void onFinish(APBaseHttpReq aPBaseHttpReq) {
        if (this.a != null) {
            this.a.onFinish();
        }
    }

    public void onReceive(byte[] bArr, int i, long j, APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStart(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStop(APBaseHttpReq aPBaseHttpReq) {
    }
}
