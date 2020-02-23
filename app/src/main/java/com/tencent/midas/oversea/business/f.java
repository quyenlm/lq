package com.tencent.midas.oversea.business;

import com.tencent.midas.oversea.api.APMidasResponse;
import com.tencent.midas.oversea.api.ICallback;
import com.tencent.midas.oversea.business.order.APOrder;
import com.tencent.midas.oversea.comm.APLog;

class f implements ICallback {
    final /* synthetic */ APOrder a;
    final /* synthetic */ int b;
    final /* synthetic */ int c;
    final /* synthetic */ String d;
    final /* synthetic */ int e;
    final /* synthetic */ String f;
    final /* synthetic */ APPayMananger g;

    f(APPayMananger aPPayMananger, APOrder aPOrder, int i, int i2, String str, int i3, String str2) {
        this.g = aPPayMananger;
        this.a = aPOrder;
        this.b = i;
        this.c = i2;
        this.d = str;
        this.e = i3;
        this.f = str2;
    }

    public void callback(int i) {
        if (this.a != null) {
            APMidasResponse aPMidasResponse = this.a.getAPMidasResponse();
            aPMidasResponse.resultCode = 0;
            aPMidasResponse.payState = 0;
            aPMidasResponse.provideState = this.b;
            aPMidasResponse.payChannel = this.c;
            aPMidasResponse.newPayChannel = this.d;
            aPMidasResponse.realSaveNum = this.e;
            aPMidasResponse.newbillno = this.f;
            if (APPayMananger.singleton().gerCurOrder() != null) {
                APLog.i(APPayMananger.a, "pay order info:" + APPayMananger.singleton().gerCurOrder().mPayInfo);
                aPMidasResponse.setPayReserve1(APPayMananger.singleton().gerCurOrder().mPayInfo);
            }
            if (APPayMananger.singleton().gerCurOrder() != null) {
                APLog.i(APPayMananger.a, "pay order info:" + APPayMananger.singleton().gerCurOrder().mReceipt);
                aPMidasResponse.setPayReserve2(APPayMananger.singleton().gerCurOrder().mReceipt);
            }
            this.a.callback(aPMidasResponse);
        }
        this.g.c();
    }
}
