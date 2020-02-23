package com.tencent.midas.oversea.business;

import com.tencent.midas.oversea.comm.APDataReportCache;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;

class e implements IAPHttpAnsObserver {
    final /* synthetic */ APPayMananger a;

    e(APPayMananger aPPayMananger) {
        this.a = aPPayMananger;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
        APDataReportCache aPDataReportCache = new APDataReportCache(this.a.getApplicationContext());
        aPDataReportCache.setDataList(APDataReportManager.getInstance().dataReport);
        aPDataReportCache.dataNativeCacheList();
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        APDataReportManager.getInstance().clearData();
        APDataReportManager.getInstance().saveDataId();
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
    }
}
