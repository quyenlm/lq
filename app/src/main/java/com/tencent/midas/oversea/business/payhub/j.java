package com.tencent.midas.oversea.business.payhub;

import android.os.Message;
import com.tencent.midas.oversea.TestConfig;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import com.tencent.midas.oversea.network.modle.APOverSeaCommAns;
import com.tencent.midas.oversea.network.modle.APOverSeaInfoAns;

class j implements IAPHttpAnsObserver {
    final /* synthetic */ APPayHub a;

    j(APPayHub aPPayHub) {
        this.a = aPPayHub;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
        Message obtainMessage = APPayMananger.singleton().getCurHandler().obtainMessage();
        obtainMessage.obj = aPBaseHttpAns.getResultMessage();
        obtainMessage.arg1 = -1;
        obtainMessage.what = 9;
        APPayMananger.singleton().getCurHandler().sendMessage(obtainMessage);
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        int resultCode = aPBaseHttpAns.getResultCode();
        if (TestConfig.retCodeInfo != 0) {
            resultCode = TestConfig.retCodeInfo;
            APLog.i("APHttpHandle", "TestConfig");
        }
        switch (resultCode) {
            case 0:
                if (APAppDataInterface.singleton().isNewCGI()) {
                    APOverSeaCommAns aPOverSeaCommAns = (APOverSeaCommAns) aPBaseHttpAns;
                    this.a.offer_name = aPOverSeaCommAns.getOfferName();
                    this.a.rate = aPOverSeaCommAns.getRate();
                    this.a.unit = aPOverSeaCommAns.getUnit();
                    this.a.count = aPOverSeaCommAns.getCount();
                    this.a.countryCode = aPOverSeaCommAns.getCountryCode();
                    this.a.a(aPOverSeaCommAns.getChannelGoods());
                } else {
                    APOverSeaInfoAns aPOverSeaInfoAns = (APOverSeaInfoAns) aPBaseHttpAns;
                    this.a.offer_name = aPOverSeaInfoAns.getOfferName();
                    this.a.rate = aPOverSeaInfoAns.getRate();
                    this.a.unit = aPOverSeaInfoAns.getUnit();
                    this.a.count = aPOverSeaInfoAns.getCount();
                    this.a.countryCode = aPOverSeaInfoAns.getCountryCode();
                    this.a.a(aPOverSeaInfoAns.getChannelGoods());
                }
                APPayMananger.singleton().getCurHandler().sendEmptyMessage(8);
                return;
            case 1018:
                APPayMananger.singleton().getCurHandler().sendEmptyMessage(1);
                return;
            case APGlobalInfo.RET_SECKEYERROR /*1094*/:
            case APGlobalInfo.RET_SECKEYVALID /*1099*/:
                if (aPBaseHttpAns.getHttpReqKey().equals(APNetworkManager.HTTP_KEY_GET_KEY)) {
                    APPayMananger.singleton().getCurHandler().sendEmptyMessage(2);
                    return;
                }
                return;
            default:
                Message obtainMessage = APPayMananger.singleton().getCurHandler().obtainMessage();
                obtainMessage.obj = aPBaseHttpAns.getResultMessage();
                obtainMessage.arg1 = -1;
                obtainMessage.what = 9;
                APPayMananger.singleton().getCurHandler().sendMessage(obtainMessage);
                return;
        }
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
        APPayMananger.singleton().getCurHandler().sendEmptyMessage(10);
    }
}
