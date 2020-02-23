package com.tencent.midas.oversea.business.payhub;

import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.data.channel.APPayReceipt;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;

class h implements IAPHttpAnsObserver {
    final /* synthetic */ APBaseRestore a;

    h(APBaseRestore aPBaseRestore) {
        this.a = aPBaseRestore;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
        APBaseRestore.e(this.a);
        this.a.a(true);
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        int resultCode = aPBaseHttpAns.getResultCode();
        APLog.i(APBaseRestore.a, "connect to server result:" + resultCode);
        switch (resultCode) {
            case 0:
            case 5017:
                APLog.i(APBaseRestore.a, "provide success add to consume list:" + ((APPayReceipt) this.a.c.get(this.a.e)).sku);
                this.a.d.add(((APPayReceipt) this.a.c.get(this.a.e)).sku);
                APBaseRestore.e(this.a);
                this.a.a(true);
                return;
            case 1018:
                APBaseRestore.e(this.a);
                this.a.a(false);
                return;
            case APGlobalInfo.RET_SECKEYERROR /*1094*/:
            case APGlobalInfo.RET_SECKEYVALID /*1099*/:
                APBaseRestore.e(this.a);
                this.a.a(false);
                return;
            default:
                APBaseRestore.e(this.a);
                this.a.a(true);
                return;
        }
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
        this.a.a(true);
    }
}
