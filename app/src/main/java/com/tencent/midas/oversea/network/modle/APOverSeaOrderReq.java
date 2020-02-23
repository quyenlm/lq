package com.tencent.midas.oversea.network.modle;

import android.text.TextUtils;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APToolAES;
import com.tencent.midas.oversea.comm.APTools;
import com.tencent.midas.oversea.network.http.APHttpReqPost;
import java.util.HashMap;
import java.util.Locale;

public class APOverSeaOrderReq extends APHttpReqPost {
    private static final String a = APOverSeaOrderReq.class.getSimpleName();
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;

    public APOverSeaOrderReq(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        setUrl("", String.format("/v1/r/%s/mobile_overseas_order", new Object[]{APPayMananger.singleton().getCurBaseRequest().offerId}), String.format("/v1/r/%s/mobile_overseas_order", new Object[]{APPayMananger.singleton().getCurBaseRequest().offerId}), String.format("/v1/r/%s/mobile_overseas_order", new Object[]{APPayMananger.singleton().getCurBaseRequest().offerId}));
        this.b = str;
        this.l = str2;
        this.d = str10;
        this.e = str7;
        this.g = str6;
        this.c = str3;
        this.f = str8;
        this.h = str5;
        this.i = str4;
        this.j = str9;
        this.l = str2;
    }

    public void constructParam() {
        APDataInterface singleton = APDataInterface.singleton();
        this.httpParam.reqParam.put("openid", APPayMananger.singleton().getCurBaseRequest().openId);
        this.httpParam.reqParam.put(UnityPayHelper.PF, APPayMananger.singleton().getCurBaseRequest().pf);
        this.httpParam.reqParam.put("pfkey", APPayMananger.singleton().getCurBaseRequest().pfKey);
        this.httpParam.reqParam.put("zoneid", APPayMananger.singleton().getCurBaseRequest().zoneId);
        this.httpParam.reqParam.put("format", "json");
        this.httpParam.reqParam.put("key_len", "newkey");
        this.httpParam.reqParam.put("key_time", APAppDataInterface.singleton().getCryptKeyTime());
        this.httpParam.reqParam.put(GGLiveConstants.PARAM.SESSION_TOKEN, APDataInterface.singleton().getSessionToken());
        this.httpParam.reqParam.put("extend", singleton.getCgiExtends());
        HashMap hashMap = new HashMap();
        hashMap.put("openid", singleton.getUserInfo().openId);
        hashMap.put("openkey", singleton.getUserInfo().openKey);
        hashMap.put("session_id", APPayMananger.singleton().getCurBaseRequest().sessionId);
        hashMap.put("session_type", APPayMananger.singleton().getCurBaseRequest().sessionType);
        hashMap.put("session_channel", APDataInterface.singleton().getUserInfo().iChannel);
        if (TextUtils.isEmpty(this.c)) {
            hashMap.put("buy_quantity", "1");
        } else {
            hashMap.put("buy_quantity", this.c);
        }
        hashMap.put("currency_type", this.i);
        hashMap.put("country", this.h);
        hashMap.put("pay_method", this.g);
        if (TextUtils.isEmpty(this.e)) {
            hashMap.put("productid", "1");
        } else {
            hashMap.put("productid", this.e);
        }
        hashMap.put("producttype", this.f);
        hashMap.put("service_code", this.d);
        hashMap.put("amt", this.b);
        if (!TextUtils.isEmpty(this.l)) {
            StringBuilder sb = new StringBuilder();
            sb.append("gw_amt=" + this.l);
            sb.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            sb.append("gw_currency=" + this.i);
            hashMap.put("wf_info", APTools.urlEncode(sb.toString(), 1));
        }
        hashMap.put("type", APPayMananger.singleton().getCurBaseRequest().mType);
        hashMap.put("sdkversion", "androidoversea_v" + APGlobalInfo.SDK_VERSION);
        try {
            hashMap.put("language", Locale.getDefault().getISO3Language());
        } catch (Exception e2) {
        }
        APLog.i(a, "APOverSeaOrderReq:" + APTools.map2UrlParams(hashMap));
        String MaptoString = APCommMethod.MaptoString(hashMap);
        this.httpParam.reqParam.put("encrypt_msg", APToolAES.doEncode(MaptoString, APAppDataInterface.singleton().getCryptoKey()));
        this.httpParam.reqParam.put("msg_len", Integer.toString(MaptoString.length()));
    }

    public void startService() {
        if (APPayMananger.singleton().getCurBaseRequest().mType.equals(APMidasBaseRequest.OFFER_TYPE_BG)) {
            int lastIndexOf = this.j.lastIndexOf("?");
            if (lastIndexOf > 0) {
                this.j.substring(0, lastIndexOf);
                int lastIndexOf2 = this.j.lastIndexOf("token_id=");
                if (lastIndexOf2 > 0) {
                    this.k = this.j.substring(lastIndexOf2 + 9);
                    String replace = this.j.replace("mobile_goods_info", "mobile_overseas_order");
                    setUrl(replace, replace, replace, replace);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        startRequest();
    }
}
