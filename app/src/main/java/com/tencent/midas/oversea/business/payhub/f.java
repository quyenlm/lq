package com.tencent.midas.oversea.business.payhub;

import com.tencent.midas.oversea.api.IGetPurchaseCallback;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.data.channel.APPayReceipt;
import java.util.List;

class f implements IGetPurchaseCallback {
    final /* synthetic */ APBaseRestore a;

    f(APBaseRestore aPBaseRestore) {
        this.a = aPBaseRestore;
    }

    public void callback(List<APPayReceipt> list) {
        List unused = this.a.c = list;
        if (list == null || list.size() == 0) {
            APLog.i(APBaseRestore.a, "purchase list is empty");
            this.a.dispose();
            return;
        }
        APLog.i(APBaseRestore.a, "purchase list size:" + list.size());
        this.a.a(true);
    }
}
