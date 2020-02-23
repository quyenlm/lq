package com.tencent.midas.oversea.business;

import android.content.DialogInterface;
import com.tencent.midas.oversea.api.APMidasResponse;
import com.tencent.midas.oversea.business.order.APOrder;
import com.tencent.midas.oversea.comm.APLog;

class k implements DialogInterface.OnCancelListener {
    final /* synthetic */ APPayMananger a;

    k(APPayMananger aPPayMananger) {
        this.a = aPPayMananger;
    }

    public void onCancel(DialogInterface dialogInterface) {
        APOrder gerCurOrder = this.a.gerCurOrder();
        if (gerCurOrder != null) {
            APMidasResponse aPMidasResponse = gerCurOrder.getAPMidasResponse();
            aPMidasResponse.resultCode = 2;
            gerCurOrder.getCallback().MidasPayCallBack(aPMidasResponse);
            return;
        }
        APLog.e(APPayMananger.a, "order is null");
    }
}
