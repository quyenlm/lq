package com.tencent.midas.oversea.business;

import android.content.DialogInterface;
import com.tencent.midas.oversea.api.APMidasResponse;
import com.tencent.midas.oversea.business.order.APOrder;
import com.tencent.midas.oversea.comm.APLog;

class h implements DialogInterface.OnCancelListener {
    final /* synthetic */ g a;

    h(g gVar) {
        this.a = gVar;
    }

    public void onCancel(DialogInterface dialogInterface) {
        APOrder gerCurOrder = this.a.a.gerCurOrder();
        if (gerCurOrder != null) {
            APMidasResponse aPMidasResponse = gerCurOrder.getAPMidasResponse();
            aPMidasResponse.resultCode = 2;
            gerCurOrder.getCallback().MidasPayCallBack(aPMidasResponse);
            return;
        }
        APLog.e(APPayMananger.a, "order is null");
    }
}
