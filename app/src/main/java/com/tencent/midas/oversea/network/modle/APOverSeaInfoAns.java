package com.tencent.midas.oversea.network.modle;

import android.text.TextUtils;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.business.payhub.APPayHub;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APToolAES;
import com.tencent.midas.oversea.comm.APTools;
import com.tencent.midas.oversea.data.channel.APMolPinInfo;
import com.tencent.midas.oversea.data.channel.ChannelGoods;
import com.tencent.midas.oversea.data.channel.GoodsItem;
import com.tencent.midas.oversea.data.mp.APMPGroupBuyInfo;
import com.tencent.midas.oversea.data.mp.APMPSendInfo;
import com.tencent.midas.oversea.data.mp.APSecondMpData;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APBaseHttpReq;
import com.tencent.midas.oversea.network.http.APErrorCode;
import com.tencent.midas.oversea.network.http.APHttpHandle;
import com.tencent.midas.oversea.network.http.APHttpInstrument;
import com.tencent.tp.a.h;
import com.vk.sdk.api.VKApiConst;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class APOverSeaInfoAns extends APBaseHttpAns {
    private String a;
    private String b;
    private String c;
    private String d;
    private boolean e = false;
    private String f = "";
    private ArrayList<ChannelGoods> g = new ArrayList<>();

    public APOverSeaInfoAns(APHttpHandle aPHttpHandle, APHttpInstrument aPHttpInstrument, HashMap<String, APBaseHttpReq> hashMap, String str) {
        super(aPHttpHandle, aPHttpInstrument.getObserver(), hashMap, str);
    }

    private void a(JSONObject jSONObject) {
        int i = 0;
        try {
            if (jSONObject.has("offer_name")) {
                this.a = jSONObject.getString("offer_name");
            }
            if (jSONObject.has("unit")) {
                this.b = jSONObject.getString("unit");
            }
            if (jSONObject.has("rate")) {
                this.c = jSONObject.getString("rate");
            }
            if (jSONObject.has(VKApiConst.COUNT)) {
                this.d = jSONObject.getString(VKApiConst.COUNT);
            }
            if (jSONObject.has("isReport") && jSONObject.getString("isReport").equals("0")) {
                APDataInterface.singleton().setIsSendReport(false);
            }
            if (jSONObject.has("country_code")) {
                this.f = jSONObject.getString("country_code");
            }
            if (jSONObject.has("channel")) {
                JSONArray jSONArray = jSONObject.getJSONArray("channel");
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    ChannelGoods channelGoods = new ChannelGoods();
                    channelGoods.channel.id = jSONArray.getJSONObject(i2).getString("id");
                    channelGoods.channel.name = jSONArray.getJSONObject(i2).getString("name");
                    if (jSONArray.getJSONObject(i2).has("key")) {
                        String string = jSONArray.getJSONObject(i2).getString("key");
                        if (!TextUtils.isEmpty(string)) {
                            channelGoods.channel.key = string;
                        }
                        String string2 = jSONArray.getJSONObject(i2).getString("appcode");
                        if (!TextUtils.isEmpty(string2)) {
                            channelGoods.channel.appcode = string2;
                        }
                    }
                    this.g.add(channelGoods);
                }
            }
            if (jSONObject.has(APPayHub.PRODUCTIDLIST)) {
                JSONArray jSONArray2 = jSONObject.getJSONArray(APPayHub.PRODUCTIDLIST);
                for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                    APLog.i(APPayHub.PRODUCTIDLIST, "channelId:" + jSONArray2.getJSONObject(i3).toString());
                    String string3 = jSONArray2.getJSONObject(i3).getString("id");
                    ChannelGoods channelGoods2 = null;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= this.g.size()) {
                            break;
                        } else if (this.g.get(i4).channel.id.equals(string3)) {
                            channelGoods2 = this.g.get(i4);
                            break;
                        } else {
                            i4++;
                        }
                    }
                    JSONArray jSONArray3 = jSONArray2.getJSONObject(i3).getJSONArray("productid_info");
                    if (jSONArray3.length() != 0) {
                        channelGoods2.channel.isHasProduct = true;
                        for (int i5 = 0; i5 < jSONArray3.length(); i5++) {
                            GoodsItem goodsItem = new GoodsItem();
                            goodsItem.productid = jSONArray3.getJSONObject(i5).getString("productid");
                            goodsItem.num = jSONArray3.getJSONObject(i5).getString("num");
                            if (jSONArray3.getJSONObject(i5).has("producttype")) {
                                goodsItem.productType = jSONArray3.getJSONObject(i5).getString("producttype");
                            } else {
                                goodsItem.productType = "1";
                            }
                            goodsItem.price = jSONArray3.getJSONObject(i5).getString(FirebaseAnalytics.Param.PRICE);
                            goodsItem.producename = APTools.urlDecode(jSONArray3.getJSONObject(i5).getString("name"), 1);
                            if (jSONArray3.getJSONObject(i5).has("country")) {
                                goodsItem.country = jSONArray3.getJSONObject(i5).getString("country");
                            }
                            if (jSONArray3.getJSONObject(i5).has("currency_type")) {
                                goodsItem.currency_type = jSONArray3.getJSONObject(i5).getString("currency_type");
                            }
                            if (TextUtils.isEmpty(goodsItem.country)) {
                                goodsItem.country = APPayMananger.singleton().getCurBaseRequest().country;
                            }
                            if (channelGoods2 != null) {
                                channelGoods2.items.add(goodsItem);
                            }
                        }
                    }
                }
            }
            if (jSONObject.has("mol_pin_show_info")) {
                JSONArray jSONArray4 = jSONObject.getJSONArray("mol_pin_show_info");
                if (jSONArray4.length() > 0) {
                    while (true) {
                        if (i >= this.g.size()) {
                            break;
                        } else if (this.g.get(i).channel.id.equals("mol_pin")) {
                            this.g.get(i).channel.isHasProduct = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                APMolPinInfo.anaylseMolPinInfo(jSONArray4, this.b);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void a(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        if (bArr == null) {
            this.resultCode = -1;
            this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n" + APErrorCode.getErrorCode(1004);
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr));
            if (jSONObject.has(GGLiveConstants.PARAM.SESSION_TOKEN)) {
                APDataInterface.singleton().setSessionToken(jSONObject.getString(GGLiveConstants.PARAM.SESSION_TOKEN));
            }
            b(jSONObject);
            this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
            APLog.i("APImpAns", "resultCode=" + this.resultCode);
            if (this.resultCode == 0) {
                String string = jSONObject.getString("rsp_msg");
                int indexOf = string.indexOf("_");
                String doDecode = APToolAES.doDecode(string.substring(0, indexOf), APAppDataInterface.singleton().getCryptoKey(), Integer.parseInt(string.substring(indexOf + 1, string.length())));
                JSONObject jSONObject2 = new JSONObject(doDecode);
                APLog.i("APOverSeaInfoAns", "msg=" + doDecode);
                a(jSONObject2);
                return;
            }
            this.resultMsg = jSONObject.getString("msg");
            String str = jSONObject.getString("err_code").toString();
            if (!str.equals("")) {
                this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n(" + str + h.b;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void b(JSONObject jSONObject) {
        try {
            if (jSONObject.has("mp_info")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("mp_info");
                APLog.i("APOverSeaInfoAns", "mp_info=" + jSONObject2.toString());
                if (jSONObject2.has("first_mpinfo") || jSONObject2.has("utp_mpinfo") || jSONObject2.has("tuan_mpinfo")) {
                    this.e = true;
                    APMPSendInfo.getInstance().analyzeJson(jSONObject2.toString());
                }
                if (jSONObject.has("comm_config")) {
                    JSONObject jSONObject3 = jSONObject.getJSONObject("comm_config");
                    if (!jSONObject3.has("show_tuan") || !"1".equals(APMPGroupBuyInfo.getInstance().getShow()) || !"1".equals(jSONObject3.getString("show_tuan"))) {
                        APMPGroupBuyInfo.getInstance().setShow("0");
                    } else {
                        APMPGroupBuyInfo.getInstance().setShow("1");
                    }
                }
                APMPSendInfo.getInstance().parseMpTitle(jSONObject2);
                APSecondMpData.singleton().setSecondMp(APMPSendInfo.getInstance().isHasSecondMP(jSONObject2));
                return;
            }
            APSecondMpData.singleton().setSecondMp(false);
        } catch (Exception e2) {
        }
    }

    public ArrayList<ChannelGoods> getChannelGoods() {
        return this.g;
    }

    public String getCount() {
        return this.d;
    }

    public String getCountryCode() {
        return this.f;
    }

    public String getOfferName() {
        return this.a;
    }

    public String getRate() {
        return this.c;
    }

    public String getUnit() {
        return this.b;
    }

    public void onErrorAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onFinishAns(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        super.onFinishAns(bArr, aPBaseHttpReq);
        a(bArr, aPBaseHttpReq);
    }

    public void onReceiveAns(byte[] bArr, int i, long j, APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStartAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStopAns(APBaseHttpReq aPBaseHttpReq) {
    }
}
