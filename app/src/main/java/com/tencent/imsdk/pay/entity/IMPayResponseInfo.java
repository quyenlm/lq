package com.tencent.imsdk.pay.entity;

import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

public class IMPayResponseInfo extends JsonSerializable {
    @JsonProp(name = "retBillNo")
    public int billno;
    @JsonProp(name = "extendInfo")
    public String extendInfo;
    @JsonProp(name = "imsdkRetCode")
    public int imsdkRetCode;
    @JsonProp(name = "imsdkRetMsg")
    public String imsdkRetMsg;
    @JsonProp(name = "newbillno")
    public String newbillno;
    @JsonProp(name = "payChannel")
    public int payChannel;
    @JsonProp(name = "payReserve1")
    public String payReserve1;
    @JsonProp(name = "payReserve2")
    public String payReserve2;
    @JsonProp(name = "payReserve3")
    public String payReserve3;
    @JsonProp(name = "payState")
    public int payState;
    @JsonProp(name = "provideState")
    public int provideState;
    @JsonProp(name = "realSaveNum")
    public int realSaveNum;
    @JsonProp(name = "retCode")
    public int resultCode;
    @JsonProp(name = "innerCode")
    public int resultInerCode;
    @JsonProp(name = "retMsg")
    public String resultMsg;
    @JsonProp(name = "retExtraJson")
    public String retExtraJson;
    @JsonProp(name = "thirdRetCode")
    public int thirdRetCode;
    @JsonProp(name = "thirdRetMsg")
    public String thirdRetMsg;

    public IMPayResponseInfo() {
    }

    public IMPayResponseInfo(JSONObject object) throws JSONException {
        super(object);
        this.imsdkRetCode = this.resultCode;
        this.imsdkRetMsg = this.resultMsg;
    }

    public IMPayResponseInfo(String json) throws JSONException {
        super(json);
        this.imsdkRetCode = this.resultCode;
        this.imsdkRetMsg = this.resultMsg;
    }

    public String toString() {
        return "IMPayResponseInfo{resultCode=" + this.resultCode + ", resultInerCode=" + this.resultInerCode + ", imsdkRetCode=" + this.imsdkRetCode + ", imsdkRetMsg='" + this.imsdkRetMsg + '\'' + ", thirdRetCode=" + this.thirdRetCode + ", thirdRetMsg='" + this.thirdRetMsg + '\'' + ", retExtraJson='" + this.retExtraJson + '\'' + ", billno=" + this.billno + ", newbillno='" + this.newbillno + '\'' + ", realSaveNum=" + this.realSaveNum + ", payChannel=" + this.payChannel + ", payState=" + this.payState + ", provideState=" + this.provideState + ", resultMsg='" + this.resultMsg + '\'' + ", extendInfo='" + this.extendInfo + '\'' + ", payReserve1='" + this.payReserve1 + '\'' + ", payReserve2='" + this.payReserve2 + '\'' + ", payReserve3='" + this.payReserve3 + '\'' + '}';
    }
}
