package com.tencent.midas.oversea.network.modle;

import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.network.http.APHttpReqPost;
import com.tencent.midas.oversea.network.http.APUrlConf;
import java.net.URLEncoder;

public class APMpReq extends APHttpReqPost {
    private String a;
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
    private String m;

    public APMpReq(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        String offerid = APAppDataInterface.singleton().getOfferid();
        String format = String.format("/v1/r/%s/mobile_mp_info", new Object[]{offerid});
        String format2 = String.format("/v1/r/%s/mobile_mp_info", new Object[]{offerid});
        String format3 = String.format("/v1/r/%s/mobile_mp_info", new Object[]{offerid});
        String str8 = "";
        try {
            str8 = String.format(APUrlConf.AP_QUERY_MP_CUSTOM_FCG, new Object[]{APAppDataInterface.singleton().getCustomCgi(), offerid});
        } catch (Exception e2) {
        }
        setUrl(str8, format, format2, format3);
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = str7;
    }

    public APMpReq(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        this(str, str2, str3, str4, str5, str6, str7);
        this.h = str8;
        this.l = str9;
        this.i = str10;
        this.j = str11;
        this.k = str12;
        this.m = str13;
    }

    public void constructParam() {
        APDataInterface.singleton();
        this.httpParam.reqParam.put("openid", this.a);
        this.httpParam.reqParam.put(UnityPayHelper.PF, this.f);
        this.httpParam.reqParam.put("pfkey", this.g);
        this.httpParam.reqParam.put("format", "json");
        this.httpParam.reqParam.put(GGLiveConstants.PARAM.SESSION_TOKEN, APDataInterface.singleton().getSessionToken());
        if (this.h != null) {
            this.httpParam.reqParam.put("drm_info", URLEncoder.encode(this.h));
            APLog.i("APMpReq", "drm_info:" + URLEncoder.encode(this.h));
        }
        this.httpParam.reqParam.put(GGLiveConstants.PARAM.SESSION_TOKEN, "");
        this.httpParam.reqParam.put("openkey", this.b);
        this.httpParam.reqParam.put("session_id", this.c);
        this.httpParam.reqParam.put("zoneid", this.e);
        this.httpParam.reqParam.put("session_type", this.d);
        this.httpParam.reqParam.put("sdkversion", APCommMethod.getVersion());
        this.httpParam.reqParam.put("key_len", "newkey");
        this.httpParam.reqParam.put("key_time", APAppDataInterface.singleton().getCryptKeyTime());
        this.httpParam.reqParam.put("extend", this.l);
        this.httpParam.reqParam.put("currency_type", this.j);
        this.httpParam.reqParam.put("country", this.i);
        this.httpParam.reqParam.put("pay_method", this.k);
        this.httpParam.reqParam.put("type", this.m);
        this.httpParam.reqParam.put("session_channel", APDataInterface.singleton().getUserInfo().iChannel);
        this.httpParam.reqParam.put("xg_mid", APAppDataInterface.singleton().getXGMid());
        if (APAppDataInterface.singleton().getEnv().equals(APGlobalInfo.DevEnv)) {
            this.httpParam.reqParam.put("offer_id", APAppDataInterface.singleton().getOfferid());
        }
    }

    public void starService() {
        startRequest();
    }
}
