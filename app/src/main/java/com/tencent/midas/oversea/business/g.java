package com.tencent.midas.oversea.business;

import android.content.DialogInterface;
import com.tencent.midas.oversea.business.order.APOrder;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APUICommonMethod;

class g implements DialogInterface.OnClickListener {
    final /* synthetic */ APPayMananger a;

    g(APPayMananger aPPayMananger) {
        this.a = aPPayMananger;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        APUICommonMethod.showWaitDialog(this.a.f, APCommMethod.getStringId(this.a.f, "unipay_order_sanbox_tip") + APCommMethod.getVersion(), true, new h(this));
        if (APAppDataInterface.singleton().isNewCGI()) {
            APOrder gerCurOrder = this.a.gerCurOrder();
            if (gerCurOrder != null) {
                gerCurOrder.pay(this.a.h);
            } else {
                APLog.i(APPayMananger.a, "no order to pay");
            }
        } else {
            this.a.i();
        }
    }
}
