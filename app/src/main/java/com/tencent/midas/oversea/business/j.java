package com.tencent.midas.oversea.business;

import com.tencent.midas.oversea.business.order.APOrder;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APUICommonMethod;
import com.tencent.midas.oversea.safe.IAPGetKeyCallBack;

class j implements IAPGetKeyCallBack {
    final /* synthetic */ APPayMananger a;

    j(APPayMananger aPPayMananger) {
        this.a = aPPayMananger;
    }

    public void onGetKeyCancel() {
        APUICommonMethod.dismissWaitDialog();
        this.a.a(2, "");
    }

    public void onGetKeyFail(int i, String str) {
        if (i == 1100) {
            APUICommonMethod.showToast(this.a.f, APCommMethod.getStringId(this.a.f, "unipay_key_time_error"));
        }
        APUICommonMethod.dismissWaitDialog();
        this.a.a(i, str);
    }

    public void onGetKeySucc(String str) {
        APOrder gerCurOrder = this.a.gerCurOrder();
        if (gerCurOrder != null) {
            gerCurOrder.pay(this.a.h);
        }
    }

    public void onLoginError() {
        APUICommonMethod.dismissWaitDialog();
        this.a.f();
    }
}
