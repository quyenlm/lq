package com.tencent.midas.oversea.business.order;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.midas.oversea.api.APMidasResponse;
import com.tencent.midas.oversea.api.IAPMidasPayCallBack;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.api.request.APMidasGameRequest;
import com.tencent.midas.oversea.api.request.APMidasGoodsRequest;
import com.tencent.midas.oversea.api.request.APMidasSubscribeRequest;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APLog;

public class APOrder {
    public static final int MSG_ORDER_SHOW_PAY_RESULT = 1;
    public static final int MSG_ORDER_START_PAY = 0;
    private static final String a = APOrder.class.getSimpleName();
    private APMidasBaseRequest b;
    private IAPMidasPayCallBack c = null;
    private APMidasResponse d = new APMidasResponse();
    protected String mOrder = "";
    public String mPayInfo = "";
    public String mReceipt = "";

    private boolean a() {
        if (TextUtils.isEmpty(this.b.offerId)) {
            APLog.e(a, "offerId is empty!!");
            return false;
        } else if (TextUtils.isEmpty(this.b.openId)) {
            APLog.e(a, "openId is empty!!");
            return false;
        } else if (TextUtils.isEmpty(this.b.openKey)) {
            APLog.e(a, "openKey is empty!!");
            return false;
        } else if (TextUtils.isEmpty(this.b.sessionId)) {
            APLog.e(a, "sessionId is empty!!");
            return false;
        } else if (TextUtils.isEmpty(this.b.sessionType)) {
            APLog.e(a, "sessionType is empty!!");
            return false;
        } else if (TextUtils.isEmpty(this.b.pf)) {
            APLog.e(a, "pf is empty!!");
            return false;
        } else if (!TextUtils.isEmpty(this.b.pfKey)) {
            return true;
        } else {
            APLog.e(a, "pfKey is empty!!");
            return false;
        }
    }

    public static APOrder createOrder(APMidasBaseRequest aPMidasBaseRequest) {
        if (aPMidasBaseRequest == null) {
            throw new IllegalArgumentException("request is empty");
        } else if (aPMidasBaseRequest instanceof APMidasGameRequest) {
            APGameOrder aPGameOrder = new APGameOrder();
            aPGameOrder.setOrder(aPMidasBaseRequest);
            return aPGameOrder;
        } else if (aPMidasBaseRequest instanceof APMidasGoodsRequest) {
            APGoodsOrder aPGoodsOrder = new APGoodsOrder();
            aPGoodsOrder.setOrder(aPMidasBaseRequest);
            return aPGoodsOrder;
        } else if (aPMidasBaseRequest instanceof APMidasSubscribeRequest) {
            APSubscribeOrder aPSubscribeOrder = new APSubscribeOrder();
            aPSubscribeOrder.setOrder(aPMidasBaseRequest);
            return aPSubscribeOrder;
        } else {
            throw new IllegalArgumentException("not supported APMidasBaseRequest");
        }
    }

    public void callback(APMidasResponse aPMidasResponse) {
        if (this.c != null) {
            this.c.MidasPayCallBack(aPMidasResponse);
        }
    }

    public boolean checkOrderInfo() {
        return a();
    }

    public APMidasResponse getAPMidasResponse() {
        return this.d;
    }

    public APMidasBaseRequest getBaseRequest() {
        return this.b;
    }

    public IAPMidasPayCallBack getCallback() {
        return this.c;
    }

    public String getOfferid() {
        return this.b != null ? this.b.offerId : "";
    }

    /* access modifiers changed from: protected */
    public String getOrderID(Handler handler) {
        return "";
    }

    public boolean needOrder() {
        return false;
    }

    public boolean needShowMall() {
        if (APPayMananger.singleton().getCurBaseRequest().mType.equals(APMidasBaseRequest.OFFER_TYPE_BG)) {
            return false;
        }
        return TextUtils.isEmpty(this.b.getPayChannel()) || TextUtils.isEmpty(this.b.mpInfo.productid);
    }

    public void order(Handler handler) {
    }

    public void pay(Handler handler) {
        Message obtain = Message.obtain();
        obtain.what = 6;
        handler.sendMessage(obtain);
    }

    public void setCallback(IAPMidasPayCallBack iAPMidasPayCallBack) {
        this.c = iAPMidasPayCallBack;
    }

    /* access modifiers changed from: protected */
    public void setOrder(APMidasBaseRequest aPMidasBaseRequest) {
        this.b = aPMidasBaseRequest;
    }

    public boolean showSuccDetail() {
        return false;
    }
}
