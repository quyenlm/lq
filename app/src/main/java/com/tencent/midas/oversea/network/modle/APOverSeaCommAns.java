package com.tencent.midas.oversea.network.modle;

import android.text.TextUtils;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.APMidasPayAPI;
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
import com.tencent.midas.oversea.network.http.APBaseHttpParam;
import com.tencent.midas.oversea.network.http.APBaseHttpReq;
import com.tencent.midas.oversea.network.http.APErrorCode;
import com.tencent.midas.oversea.network.http.APHttpError;
import com.tencent.midas.oversea.network.http.APHttpHandle;
import com.tencent.midas.oversea.network.http.APHttpInstrument;
import com.tencent.midas.oversea.network.http.APIPList;
import com.tencent.midas.oversea.network.http.APIPState;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.midas.oversea.safe.APSecretKeyManager;
import com.tencent.tp.a.h;
import com.vk.sdk.api.VKApiConst;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APOverSeaCommAns extends APBaseHttpAns {
    private String a;
    private String b;
    private String c;
    private boolean d = false;
    private String e = "";
    private ArrayList<ChannelGoods> f = new ArrayList<>();
    private String g;
    private String h;
    private JSONObject i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;

    public APOverSeaCommAns(APHttpHandle aPHttpHandle, APHttpInstrument aPHttpInstrument, HashMap<String, APBaseHttpReq> hashMap, String str) {
        super(aPHttpHandle, aPHttpInstrument.getObserver(), hashMap, str);
    }

    private String a(String str, String str2) {
        String[] split = str.split(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        HashMap hashMap = new HashMap();
        for (String split2 : split) {
            String[] split3 = split2.split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            if (split3.length > 1) {
                hashMap.put(split3[0], split3[1]);
            } else {
                hashMap.put(split3[0], "");
            }
        }
        return (String) hashMap.get(str2);
    }

    private void a(JSONObject jSONObject) {
        int i2 = 0;
        try {
            if (jSONObject.has("offer_name")) {
                this.l = jSONObject.getString("offer_name");
            }
            if (jSONObject.has("unit")) {
                this.a = jSONObject.getString("unit");
            }
            if (jSONObject.has("rate")) {
                this.b = jSONObject.getString("rate");
            }
            if (jSONObject.has(VKApiConst.COUNT)) {
                this.c = jSONObject.getString(VKApiConst.COUNT);
            }
            if (jSONObject.has("country_code")) {
                this.e = jSONObject.getString("country_code");
            }
            if (jSONObject.has("isReport") && jSONObject.getString("isReport").equals("0")) {
                APDataInterface.singleton().setIsSendReport(false);
            }
            if (jSONObject.has("channel")) {
                JSONArray jSONArray = jSONObject.getJSONArray("channel");
                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                    ChannelGoods channelGoods = new ChannelGoods();
                    channelGoods.channel.id = jSONArray.getJSONObject(i3).getString("id");
                    channelGoods.channel.name = jSONArray.getJSONObject(i3).getString("name");
                    if (jSONArray.getJSONObject(i3).has("key")) {
                        String string = jSONArray.getJSONObject(i3).getString("key");
                        if (!TextUtils.isEmpty(string)) {
                            channelGoods.channel.key = string;
                        }
                        String string2 = jSONArray.getJSONObject(i3).getString("appcode");
                        if (!TextUtils.isEmpty(string2)) {
                            channelGoods.channel.appcode = string2;
                        }
                    }
                    this.f.add(channelGoods);
                }
            }
            if (jSONObject.has(APPayHub.PRODUCTIDLIST)) {
                JSONArray jSONArray2 = jSONObject.getJSONArray(APPayHub.PRODUCTIDLIST);
                for (int i4 = 0; i4 < jSONArray2.length(); i4++) {
                    APLog.i(APPayHub.PRODUCTIDLIST, "channelId:" + jSONArray2.getJSONObject(i4).toString());
                    String string3 = jSONArray2.getJSONObject(i4).getString("id");
                    ChannelGoods channelGoods2 = null;
                    int i5 = 0;
                    while (true) {
                        if (i5 >= this.f.size()) {
                            break;
                        } else if (this.f.get(i5).channel.id.equals(string3)) {
                            channelGoods2 = this.f.get(i5);
                            break;
                        } else {
                            i5++;
                        }
                    }
                    JSONArray jSONArray3 = jSONArray2.getJSONObject(i4).getJSONArray("productid_info");
                    if (jSONArray3.length() != 0) {
                        channelGoods2.channel.isHasProduct = true;
                        for (int i6 = 0; i6 < jSONArray3.length(); i6++) {
                            GoodsItem goodsItem = new GoodsItem();
                            goodsItem.productid = jSONArray3.getJSONObject(i6).getString("productid");
                            goodsItem.num = jSONArray3.getJSONObject(i6).getString("num");
                            if (jSONArray3.getJSONObject(i6).has("producttype")) {
                                goodsItem.productType = jSONArray3.getJSONObject(i6).getString("producttype");
                            } else {
                                goodsItem.productType = "1";
                            }
                            goodsItem.price = jSONArray3.getJSONObject(i6).getString(FirebaseAnalytics.Param.PRICE);
                            goodsItem.producename = APTools.urlDecode(jSONArray3.getJSONObject(i6).getString("name"), 1);
                            if (jSONArray3.getJSONObject(i6).has("country")) {
                                goodsItem.country = jSONArray3.getJSONObject(i6).getString("country");
                            }
                            if (jSONArray3.getJSONObject(i6).has("currency_type")) {
                                goodsItem.currency_type = jSONArray3.getJSONObject(i6).getString("currency_type");
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
                        if (i2 >= this.f.size()) {
                            break;
                        } else if (this.f.get(i2).channel.id.equals("mol_pin")) {
                            this.f.get(i2).channel.isHasProduct = true;
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                APMolPinInfo.anaylseMolPinInfo(jSONArray4, this.a);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void a(JSONObject jSONObject, APBaseHttpReq aPBaseHttpReq) {
        try {
            if (jSONObject.length() != 0) {
                this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
                if (this.resultCode != 0) {
                    this.resultMsg = jSONObject.getString("msg");
                    String str = jSONObject.getString("err_code").toString();
                    if (!str.equals("")) {
                        this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n(" + str + h.b;
                    }
                } else if (jSONObject.has(APNetworkManager2.HTTP_KEY_OVERSEAINFO)) {
                    JSONArray jSONArray = jSONObject.getJSONArray(APNetworkManager2.HTTP_KEY_OVERSEAINFO);
                    HashMap hashMap = new HashMap();
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                        APIPState aPIPState = new APIPState();
                        String string = jSONObject2.getString(APBaseHttpParam.IP_ACCESS);
                        aPIPState.ip = string;
                        hashMap.put(string, aPIPState);
                    }
                    try {
                        APIPList.getInstance().updateIPList(hashMap);
                        APIPList.getInstance().saveUpdateTime();
                    } catch (Exception e2) {
                    }
                }
            }
        } catch (JSONException e3) {
            this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n" + APErrorCode.getErrorCode(1003);
            e3.printStackTrace();
        }
    }

    private void b(JSONObject jSONObject) {
        try {
            if (jSONObject.has("mp_info")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("mp_info");
                APLog.i("APOverSeaInfoAns", "mp_info=" + jSONObject2.toString());
                if (jSONObject2.has("first_mpinfo") || jSONObject2.has("utp_mpinfo") || jSONObject2.has("tuan_mpinfo")) {
                    this.d = true;
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

    private void b(JSONObject jSONObject, APBaseHttpReq aPBaseHttpReq) {
        try {
            if (jSONObject.length() != 0) {
                this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
                if (this.resultCode == 0) {
                    String string = jSONObject.getString("key_info");
                    String string2 = jSONObject.getString("key_info_len");
                    if (jSONObject.has("need_change_key")) {
                        if (jSONObject.getInt("need_change_key") == 1) {
                            APAppDataInterface.singleton().setChangeKey(true);
                        } else {
                            APAppDataInterface.singleton().setChangeKey(false);
                        }
                    }
                    String substring = APToolAES.doDecode(string, APAppDataInterface.singleton().getBaseKey()).substring(0, Integer.valueOf(string2).intValue());
                    String a2 = a(substring, "key");
                    String a3 = a(substring, "cryptkey");
                    String a4 = a(substring, "cryptkeytime");
                    APAppDataInterface.singleton().setSecretKey(a2);
                    APAppDataInterface.singleton().setCryptKey(a3);
                    APAppDataInterface.singleton().setCryptKeyTime(a4);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveSecretKey(APDataInterface.singleton().getUserInfo().openId, a2);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveCryptKey(APDataInterface.singleton().getUserInfo().openId, a3);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveCryptKeyTime(APDataInterface.singleton().getUserInfo().openId, a4);
                    return;
                }
                this.resultMsg = jSONObject.getString("msg");
                String str = jSONObject.getString("err_code").toString();
                if (!str.equals("")) {
                    StringBuilder sb = new StringBuilder();
                    APMidasPayAPI.singleton();
                    this.resultMsg = sb.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append("\n(").append(str).append(h.b).toString();
                }
            }
        } catch (JSONException e2) {
            StringBuilder sb2 = new StringBuilder();
            APMidasPayAPI.singleton();
            this.resultMsg = sb2.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append(APHttpError.HTTP_ERROR_JSONERROR).toString();
            e2.printStackTrace();
        }
    }

    private void c(JSONObject jSONObject) {
        try {
            if (jSONObject.has("order_info")) {
                this.g = jSONObject.getString("order_info");
            }
            if (jSONObject.has("billno")) {
                this.h = jSONObject.getString("billno");
            }
            if (jSONObject.has(APNetworkManager2.HTTP_KEY_OVERSEAINFO)) {
                this.i = jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_OVERSEAINFO);
            }
            if (jSONObject.has("currency_amt")) {
                this.j = jSONObject.getString("currency_amt");
            }
            if (jSONObject.has("currency_type")) {
                this.k = jSONObject.getString("currency_type");
            }
            if (jSONObject.has("num")) {
                this.n = jSONObject.getString("num");
            }
            if (jSONObject.has("product_name")) {
                this.m = APTools.urlDecode(jSONObject.getString("product_name"), 1);
            }
            if (jSONObject.has("offer_name")) {
                this.l = APTools.urlDecode(jSONObject.getString("offer_name"), 1);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private void c(JSONObject jSONObject, APBaseHttpReq aPBaseHttpReq) {
        boolean z = false;
        try {
            if (jSONObject.length() != 0) {
                this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
                if (this.resultCode == 0) {
                    if (!jSONObject.has("s") || !"1".equals(jSONObject.getString("use_old_process"))) {
                        z = true;
                    }
                    APAppDataInterface.singleton().setIsNewCGI(z);
                    String string = jSONObject.getString("rsp_msg");
                    int indexOf = string.indexOf("_");
                    JSONObject jSONObject2 = new JSONObject(APToolAES.doDecode(string.substring(0, indexOf), APAppDataInterface.singleton().getCryptoKey(), Integer.parseInt(string.substring(indexOf + 1, string.length()))));
                    APLog.i("APOverSeaInfoAns", "msg=" + jSONObject2);
                    c(jSONObject2);
                    return;
                }
                this.resultMsg = jSONObject.getString("msg");
                String str = jSONObject.getString("err_code").toString();
                if (!str.equals("")) {
                    StringBuilder sb = new StringBuilder();
                    APMidasPayAPI.singleton();
                    this.resultMsg = sb.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append("\n(").append(str).append(h.b).toString();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void d(JSONObject jSONObject, APBaseHttpReq aPBaseHttpReq) {
        try {
            if (jSONObject.length() != 0) {
                this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
                if (this.resultCode != 0) {
                    this.resultMsg = jSONObject.getString("msg");
                    String str = jSONObject.getString("err_code").toString();
                    if (!str.equals("")) {
                        StringBuilder sb = new StringBuilder();
                        APMidasPayAPI.singleton();
                        this.resultMsg = sb.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append("\n(").append(str).append(h.b).toString();
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void e(JSONObject jSONObject, APBaseHttpReq aPBaseHttpReq) {
        try {
            b(jSONObject);
            if (jSONObject.has(GGLiveConstants.PARAM.SESSION_TOKEN)) {
                APDataInterface.singleton().setSessionToken(jSONObject.getString(GGLiveConstants.PARAM.SESSION_TOKEN));
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
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public String getAmount() {
        return this.j;
    }

    public String getBillno() {
        return this.h;
    }

    public ArrayList<ChannelGoods> getChannelGoods() {
        return this.f;
    }

    public String getCount() {
        return this.c;
    }

    public String getCountryCode() {
        return this.e;
    }

    public String getCurrentType() {
        return this.k;
    }

    public JSONObject getInfo() {
        return this.i;
    }

    public String getNum() {
        return this.n;
    }

    public String getOfferName() {
        return this.l;
    }

    public String getOfferName4Order() {
        return this.l;
    }

    public String getOrderInfo() {
        return this.g;
    }

    public String getProductName() {
        return this.m;
    }

    public String getRate() {
        return this.b;
    }

    public String getUnit() {
        return this.a;
    }

    public String getgw_playkey() {
        return this.n;
    }

    public String getpackageName() {
        return this.n;
    }

    public void onErrorAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onFinishAns(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        super.onFinishAns(bArr, aPBaseHttpReq);
        progressJson(bArr, aPBaseHttpReq);
    }

    public void onReceiveAns(byte[] bArr, int i2, long j2, APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStartAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStopAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void progressJson(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        String str = ((APOverSeaCommReq) aPBaseHttpReq).cmd;
        APLog.d("APOverSeaAns", "content=" + bArr);
        if (bArr == null) {
            this.resultCode = -1;
            StringBuilder sb = new StringBuilder();
            APMidasPayAPI.singleton();
            this.resultMsg = sb.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append("\n").append(APErrorCode.getErrorCode(1004)).toString();
            return;
        }
        try {
            String str2 = new String(bArr);
            APLog.d("APOverSeaAns", "resultData=" + str2);
            JSONObject jSONObject = new JSONObject(str2);
            if (str.contains("use_old_process")) {
            }
            if (str.contains(APNetworkManager2.HTTP_KEY_GETIPLIST)) {
                a(jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_GETIPLIST), aPBaseHttpReq);
            }
            if (str.contains(APNetworkManager2.HTTP_KEY_GET_KEY)) {
                b(jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_GET_KEY), aPBaseHttpReq);
            }
            if (str.contains(APNetworkManager2.HTTP_KEY_OVERSEAINFO)) {
                e(jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_OVERSEAINFO), aPBaseHttpReq);
            }
            if (str.contains(APNetworkManager2.HTTP_KEY_OVERSEAORDER)) {
                c(jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_OVERSEAORDER), aPBaseHttpReq);
            }
            if (str.contains(APNetworkManager2.HTTP_KEY_OVERSEAPROVIDE)) {
                d(jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_OVERSEAPROVIDE), aPBaseHttpReq);
            }
        } catch (Exception e2) {
        }
    }
}
