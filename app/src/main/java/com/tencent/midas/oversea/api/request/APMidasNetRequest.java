package com.tencent.midas.oversea.api.request;

public class APMidasNetRequest extends APMidasBaseRequest {
    public static String NET_REQ_MP = "mp";
    private static final long serialVersionUID = 5582061948778338484L;
    public String reqType = "";

    public String getReqType() {
        return this.reqType;
    }

    public void setReqType(String str) {
        this.reqType = str;
    }
}
