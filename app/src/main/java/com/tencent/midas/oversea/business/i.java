package com.tencent.midas.oversea.business;

import android.content.DialogInterface;
import android.view.KeyEvent;
import com.tencent.midas.oversea.api.APMidasResponse;
import com.tencent.midas.oversea.business.order.APOrder;
import com.tencent.midas.oversea.comm.APLog;

class i implements DialogInterface.OnKeyListener {
    final /* synthetic */ APPayMananger a;

    i(APPayMananger aPPayMananger) {
        this.a = aPPayMananger;
    }

    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 1) {
            return false;
        }
        APOrder gerCurOrder = this.a.gerCurOrder();
        if (gerCurOrder != null) {
            APMidasResponse aPMidasResponse = gerCurOrder.getAPMidasResponse();
            aPMidasResponse.resultCode = 2;
            gerCurOrder.getCallback().MidasPayCallBack(aPMidasResponse);
            return false;
        }
        APLog.e(APPayMananger.a, "order is null");
        return false;
    }
}
