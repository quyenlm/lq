package com.tencent.midas.oversea.business;

import com.tencent.midas.oversea.api.IAPMidasNetCallBack;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import com.tencent.midas.oversea.network.modle.APMpAns;

class l implements IAPHttpAnsObserver {
    final /* synthetic */ IAPMidasNetCallBack a;
    final /* synthetic */ APPayMananger b;

    l(APPayMananger aPPayMananger, IAPMidasNetCallBack iAPMidasNetCallBack) {
        this.b = aPPayMananger;
        this.a = iAPMidasNetCallBack;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
        this.a.MidasNetError(aPBaseHttpAns.getHttpReqKey(), aPBaseHttpAns.getResultCode(), aPBaseHttpAns.getErrorMessage());
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        this.a.MidasNetFinish(aPBaseHttpAns.getHttpReqKey(), ((APMpAns) aPBaseHttpAns).getMpJson());
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
        this.a.MidasNetStop(aPBaseHttpAns.getHttpReqKey());
    }
}
