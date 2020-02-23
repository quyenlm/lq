package com.tencent.midas.oversea.api;

import org.json.JSONException;
import org.json.JSONObject;

public class UnityResponse {
    public String appExtends;
    public String billno;
    public String payChannel;
    public String resultCode;
    public String resultInerCode;
    public String resultMsg;

    public String toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(UnityPayHelper.RES_CODE, this.resultCode);
            obj.put("resultInerCode", this.resultInerCode);
            obj.put("billno", this.billno);
            obj.put(UnityPayHelper.PAYCHANNEL, this.payChannel);
            obj.put(UnityPayHelper.RES_MSG, this.resultMsg);
            obj.put(UnityPayHelper.APPEXTENDS, this.appExtends);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
}
