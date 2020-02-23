package com.tencent.midas.oversea.business.payhub;

import android.os.Message;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.midas.oversea.TestConfig;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import com.tencent.midas.oversea.network.modle.APOverSeaCommAns;
import com.tencent.midas.oversea.network.modle.APOverSeaOrderAns;
import org.json.JSONException;
import org.json.JSONObject;

class c implements IAPHttpAnsObserver {
    final /* synthetic */ APBasePayChannel a;

    c(APBasePayChannel aPBasePayChannel) {
        this.a = aPBasePayChannel;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
        Message obtainMessage = this.a.b.obtainMessage();
        obtainMessage.obj = aPBaseHttpAns.getResultMessage();
        obtainMessage.what = 39;
        this.a.UIHandler.sendMessage(obtainMessage);
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        int resultCode = aPBaseHttpAns.getResultCode();
        if (TestConfig.retCodeOrder != 0) {
            resultCode = TestConfig.retCodeOrder;
            APLog.i("APHttpHandle", "TestConfig");
        }
        switch (resultCode) {
            case 0:
                if (APAppDataInterface.singleton().isNewCGI()) {
                    APOverSeaCommAns aPOverSeaCommAns = (APOverSeaCommAns) aPBaseHttpAns;
                    this.a.mBillNo = aPOverSeaCommAns.getBillno();
                    this.a.mInfo = aPOverSeaCommAns.getInfo();
                    this.a.currency_amt = aPOverSeaCommAns.getAmount();
                    this.a.mCurrency = aPOverSeaCommAns.getCurrentType();
                    this.a.offer_name = aPOverSeaCommAns.getOfferName4Order();
                    this.a.product_name = aPOverSeaCommAns.getProductName();
                    this.a.num = aPOverSeaCommAns.getNum();
                    if (!this.a.d) {
                        this.a.a(aPOverSeaCommAns);
                    }
                    this.a.UIHandler.sendEmptyMessage(38);
                } else {
                    APOverSeaOrderAns aPOverSeaOrderAns = (APOverSeaOrderAns) aPBaseHttpAns;
                    this.a.mBillNo = aPOverSeaOrderAns.getBillno();
                    this.a.mInfo = aPOverSeaOrderAns.getInfo();
                    this.a.currency_amt = aPOverSeaOrderAns.getAmount();
                    this.a.mCurrency = aPOverSeaOrderAns.getCurrentType();
                    this.a.UIHandler.sendEmptyMessage(38);
                }
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("offerid", APPayMananger.singleton().getCurBaseRequest().offerId);
                    jSONObject.put("productid", this.a.mGoodsItem != null ? this.a.mGoodsItem.productid : "");
                    jSONObject.put("productname", this.a.product_name);
                    jSONObject.put(FirebaseAnalytics.Param.PRICE, this.a.i);
                    jSONObject.put("paychannel", this.a.mChannelItem.id);
                    jSONObject.put(FirebaseAnalytics.Param.CURRENCY, this.a.h);
                    APPayMananger.singleton().gerCurOrder().mPayInfo = jSONObject.toString();
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            case 1018:
                this.a.b.sendEmptyMessage(1);
                return;
            case APGlobalInfo.RET_SECKEYERROR /*1094*/:
            case APGlobalInfo.RET_SECKEYVALID /*1099*/:
                if (aPBaseHttpAns.getHttpReqKey().equals(APNetworkManager.HTTP_KEY_GET_KEY)) {
                    this.a.b.sendEmptyMessage(2);
                    return;
                }
                return;
            default:
                Message obtainMessage = this.a.UIHandler.obtainMessage();
                obtainMessage.obj = aPBaseHttpAns.getResultMessage();
                obtainMessage.what = 39;
                this.a.UIHandler.sendMessage(obtainMessage);
                return;
        }
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
        this.a.UIHandler.sendEmptyMessage(30);
    }
}
