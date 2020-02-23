package com.tencent.midas.oversea.api.request;

public class APMidasSubscribeRequest extends APMidasMonthRequest {
    private static final long serialVersionUID = 6111439524338439638L;
    public String productId = "";

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String str) {
        this.productId = str;
    }
}
