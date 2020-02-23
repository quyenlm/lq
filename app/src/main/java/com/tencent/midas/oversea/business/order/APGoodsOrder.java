package com.tencent.midas.oversea.business.order;

import android.os.Handler;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;

public class APGoodsOrder extends APOrder {
    /* access modifiers changed from: protected */
    public String getOrder(Handler handler) {
        this.mOrder = "";
        handler.sendEmptyMessage(12);
        return "";
    }

    public boolean needOrder() {
        return true;
    }

    public void order(Handler handler) {
    }

    /* access modifiers changed from: protected */
    public void setOrder(APMidasBaseRequest aPMidasBaseRequest) {
        super.setOrder(aPMidasBaseRequest);
    }
}
