package com.tencent.midas.oversea.network.http;

import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APTools;

class c implements IAPHttpAnsObserver {
    final /* synthetic */ APHttpInstrument a;

    c(APHttpInstrument aPHttpInstrument) {
        this.a = aPHttpInstrument;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
        int i;
        long currentTimeMillis = System.currentTimeMillis() - this.a.a;
        String cmd = this.a.getCmd(aPBaseHttpAns);
        String errorMessage = aPBaseHttpAns.getErrorMessage();
        try {
            errorMessage = APTools.urlEncode(aPBaseHttpAns.getErrorMessage(), 3);
        } catch (Exception e) {
        }
        try {
            i = aPBaseHttpAns.httpClient.httpParam.requestTimes;
        } catch (Exception e2) {
            i = 0;
        }
        APDataReportManager.getInstance().insertData(this.a.e, APMidasPayAPI.singleton().mBuyType, errorMessage, String.valueOf(i), cmd, aPBaseHttpAns.getResultCode() + "");
        this.a.b.onError(aPBaseHttpAns);
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        int i;
        long currentTimeMillis = System.currentTimeMillis() - this.a.a;
        String cmd = this.a.getCmd(aPBaseHttpAns);
        try {
            i = aPBaseHttpAns.httpClient.httpParam.requestTimes;
        } catch (Exception e) {
            i = 0;
        }
        if (aPBaseHttpAns.getResultCode() == 0) {
            APDataReportManager.getInstance().insertData(this.a.d, APMidasPayAPI.singleton().mBuyType, String.valueOf(i), cmd, String.valueOf(currentTimeMillis));
        } else {
            APDataReportManager.getInstance().insertData(this.a.e, APMidasPayAPI.singleton().mBuyType, String.valueOf(i), cmd, aPBaseHttpAns.getResultCode() + "");
        }
        this.a.b.onFinish(aPBaseHttpAns);
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
        int i;
        long currentTimeMillis = System.currentTimeMillis() - this.a.a;
        String cmd = this.a.getCmd(aPBaseHttpAns);
        try {
            i = aPBaseHttpAns.httpClient.httpParam.requestTimes;
        } catch (Exception e) {
            i = 0;
        }
        APDataReportManager.getInstance().insertData("onStop", APMidasPayAPI.singleton().mBuyType, String.valueOf(i), cmd, aPBaseHttpAns.getResultCode() + "");
        this.a.b.onStop(aPBaseHttpAns);
    }
}
