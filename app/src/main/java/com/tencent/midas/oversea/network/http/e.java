package com.tencent.midas.oversea.network.http;

import com.tencent.midas.oversea.comm.APLog;

class e implements IAPHttpAnsObserver {
    final /* synthetic */ APIPList a;

    e(APIPList aPIPList) {
        this.a = aPIPList;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        if (aPBaseHttpAns.getResultCode() == 0) {
            APLog.i("APIPList", "update ip list succ");
            this.a.saveUpdateTime();
        }
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
    }
}
