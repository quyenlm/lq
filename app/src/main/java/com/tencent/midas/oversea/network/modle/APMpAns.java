package com.tencent.midas.oversea.network.modle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.data.mp.APMPSendInfo;
import com.tencent.midas.oversea.data.mp.APProductItem;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APBaseHttpReq;
import com.tencent.midas.oversea.network.http.APHttpHandle;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import com.tencent.tp.a.h;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class APMpAns extends APBaseHttpAns {
    private List<String> a = new ArrayList();
    private List<String> b = new ArrayList();
    private List<String> c = new ArrayList();
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private List<APProductItem> h = new ArrayList();
    private String i = "";

    public APMpAns(APHttpHandle aPHttpHandle, IAPHttpAnsObserver iAPHttpAnsObserver, HashMap<String, APBaseHttpReq> hashMap, String str) {
        super(aPHttpHandle, iAPHttpAnsObserver, hashMap, str);
    }

    public String getBeginTime() {
        return this.f;
    }

    public String getEndTime() {
        return this.g;
    }

    public String getFirstsave_present_count() {
        return this.e;
    }

    public String getMpJson() {
        return this.i;
    }

    public List<String> getMpList() {
        return this.a;
    }

    public List<String> getMpPresentList() {
        return this.c;
    }

    public List<String> getMpValueList() {
        return this.b;
    }

    public List<APProductItem> getProductList() {
        return this.h;
    }

    public String getRate() {
        return this.d;
    }

    public void onErrorAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onFinishAns(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        super.onFinishAns(bArr, aPBaseHttpReq);
        String str = new String(bArr);
        this.i = str;
        APLog.i("APMpAns", "resultData=" + str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
            if (this.resultCode == 0) {
                APMPSendInfo.getInstance().analyzeGroupBuyInfo(jSONObject.getJSONObject("mp_info"));
                if (jSONObject.has("product_list")) {
                    JSONArray jSONArray = jSONObject.getJSONArray("product_list");
                    this.h.clear();
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                        APProductItem aPProductItem = new APProductItem();
                        try {
                            aPProductItem.name = jSONObject2.getString("name");
                            aPProductItem.productId = jSONObject2.getString("productid");
                            aPProductItem.price = jSONObject2.getString(FirebaseAnalytics.Param.PRICE);
                            aPProductItem.num = jSONObject2.getString("num");
                            this.h.add(aPProductItem);
                        } catch (Exception e2) {
                        }
                    }
                }
                this.d = jSONObject.getString("rate");
                APCommMethod.transformStrToList(jSONObject.getString(HttpRequestParams.NOTICE_LIST), this.a);
                this.e = jSONObject.getString("firstsave_present_count");
                APCommMethod.transformStrToMpInfoList(jSONObject.getString("present_level"), this.b, this.c);
                this.f = jSONObject.getString("begin_time");
                this.g = jSONObject.getString("end_time");
                return;
            }
            this.resultMsg = jSONObject.getString("msg");
            String str2 = jSONObject.getString("err_code").toString();
            if (!str2.equals("")) {
                this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n(" + str2 + h.b;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void onReceiveAns(byte[] bArr, int i2, long j, APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStartAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStopAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void setBeginTime(String str) {
        this.f = str;
    }

    public void setEndTime(String str) {
        this.g = str;
    }

    public void setFirstsave_present_count(String str) {
        this.e = str;
    }

    public void setMpList(List<String> list) {
        this.a = list;
    }

    public void setMpPresentList(List<String> list) {
        this.c = list;
    }

    public void setMpValueList(List<String> list) {
        this.b = list;
    }

    public void setRate(String str) {
        this.d = str;
    }
}
