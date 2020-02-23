package com.tencent.midas.oversea.comm;

import com.tencent.midas.oversea.network.http.IAPHttpReportObserver;

class h implements IAPHttpReportObserver {
    final /* synthetic */ APDataReportCache a;

    h(APDataReportCache aPDataReportCache) {
        this.a = aPDataReportCache;
    }

    public void onFinish() {
        if (this.a.c != null && this.a.c.exists()) {
            this.a.c.delete();
        }
        APDataReportManager.getInstance().saveDataId();
    }
}
