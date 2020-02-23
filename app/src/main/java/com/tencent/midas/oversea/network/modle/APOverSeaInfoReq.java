package com.tencent.midas.oversea.network.modle;

import android.text.TextUtils;
import com.tencent.midas.oversea.api.UnityPayHelper;
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

public class APOverSeaInfoReq extends APHttpReqPost {
    private static final String a = APOverSeaInfoReq.class.getSimpleName();
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    public String mUUID;

    public APOverSeaInfoReq(String str, String str2, String str3, String str4, String str5, String str6) {
        setUrl("", String.format("/v1/r/%s/mobile_overseas_info", new Object[]{APPayMananger.singleton().getCurBaseRequest().offerId}), String.format("/v1/r/%s/mobile_overseas_info", new Object[]{APPayMananger.singleton().getCurBaseRequest().offerId}), String.format("/v1/r/%s/mobile_overseas_info", new Object[]{APPayMananger.singleton().getCurBaseRequest().offerId}));
        this.b = str;
        this.c = str5;
        this.d = str6;
        this.e = str4;
        this.f = str3;
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
        this.httpParam.reqParam.put("extend", singleton.getCgiExtends());
        HashMap hashMap = new HashMap();
        hashMap.put("openid", singleton.getUserInfo().openId);
        hashMap.put("openkey", singleton.getUserInfo().openKey);
        hashMap.put("session_id", APPayMananger.singleton().getCurBaseRequest().sessionId);
        hashMap.put("session_type", APPayMananger.singleton().getCurBaseRequest().sessionType);
        hashMap.put("session_channel", APDataInterface.singleton().getUserInfo().iChannel);
        hashMap.put("type", this.b);
        hashMap.put("buy_quantity", APPayMananger.singleton().getCurBaseRequest().saveValue);
        hashMap.put("currency_type", this.f);
        hashMap.put("country", this.e);
        hashMap.put("service_code", this.c);
        hashMap.put("sdkversion", "androidoversea_v" + APGlobalInfo.SDK_VERSION);
        if (!TextUtils.isEmpty(this.d)) {
            hashMap.put("userip", this.d);
        }
        try {
            hashMap.put("language", Locale.getDefault().getISO3Language());
        } catch (Exception e2) {
        }
        APLog.i(a, "APOverSeaInfoReq:" + APTools.map2UrlParams(hashMap));
        String MaptoString = APCommMethod.MaptoString(hashMap);
        this.httpParam.reqParam.put("encrypt_msg", APToolAES.doEncode(MaptoString, APAppDataInterface.singleton().getCryptoKey()));
        this.httpParam.reqParam.put("msg_len", Integer.toString(MaptoString.length()));
    }

    public void startService() {
        startRequest();
    }
}
