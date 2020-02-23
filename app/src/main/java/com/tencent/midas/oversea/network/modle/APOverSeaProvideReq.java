package com.tencent.midas.oversea.network.modle;

import android.text.TextUtils;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APMD5;
import com.tencent.midas.oversea.comm.APToolAES;
import com.tencent.midas.oversea.comm.APTools;
import com.tencent.midas.oversea.network.http.APHttpReqPost;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.vk.sdk.api.VKApiConst;
import java.util.HashMap;
import java.util.Locale;

public class APOverSeaProvideReq extends APHttpReqPost {
    private static final String a = APOverSeaProvideReq.class.getSimpleName();
    private String b;
    private String c;
    private String d;
    private String e;
    private boolean f;
    private String g;
    private String h;
    private String i;

    public APOverSeaProvideReq(boolean z, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        setUrl("", String.format("/v1/r/%s/mobile_overseas_provide", new Object[]{APAppDataInterface.singleton().mofferId}), String.format("/v1/r/%s/mobile_overseas_provide", new Object[]{APAppDataInterface.singleton().mofferId}), String.format("/v1/r/%s/mobile_overseas_provide", new Object[]{APAppDataInterface.singleton().mofferId}));
        this.b = str5;
        this.d = str6;
        this.e = str7;
        this.c = str3;
        this.f = z;
        this.g = str4;
        this.h = str2;
        this.i = str;
    }

    public void constructParam() {
        APDataInterface singleton = APDataInterface.singleton();
        this.httpParam.reqParam.put("openid", singleton.getUserInfo().openId);
        this.httpParam.reqParam.put(UnityPayHelper.PF, singleton.getUserInfo().pf);
        this.httpParam.reqParam.put("pfkey", singleton.getUserInfo().pfKey);
        this.httpParam.reqParam.put("zoneid", singleton.getUserInfo().zoneId);
        this.httpParam.reqParam.put("format", "json");
        this.httpParam.reqParam.put("key_len", "newkey");
        this.httpParam.reqParam.put("key_time", APAppDataInterface.singleton().getCryptKeyTime());
        if (this.f) {
            this.httpParam.reqParam.put("action", "reprovide");
        } else {
            this.httpParam.reqParam.put("action", APNetworkManager2.HTTP_KEY_OVERSEAPROVIDE);
        }
        this.httpParam.reqParam.put(GGLiveConstants.PARAM.SESSION_TOKEN, APDataInterface.singleton().getSessionToken());
        this.httpParam.reqParam.put("extend", singleton.getCgiExtends());
        HashMap hashMap = new HashMap();
        hashMap.put("openid", singleton.getUserInfo().openId);
        hashMap.put("openkey", singleton.getUserInfo().openKey);
        hashMap.put("session_id", singleton.getUserInfo().sessionId);
        hashMap.put("session_type", singleton.getUserInfo().sessionType);
        hashMap.put("session_channel", APDataInterface.singleton().getUserInfo().iChannel);
        hashMap.put("currency_type", this.i);
        hashMap.put("country", this.h);
        hashMap.put("sdkversion", "androidoversea_v" + APGlobalInfo.SDK_VERSION);
        if (!TextUtils.isEmpty(this.c)) {
            hashMap.put("pay_method", this.c);
        }
        if (!TextUtils.isEmpty(this.b)) {
            hashMap.put("billno", this.b);
        }
        if (!TextUtils.isEmpty(this.d)) {
            hashMap.put("receipt", this.d);
        }
        hashMap.put("buy_quantity", this.g);
        if (!TextUtils.isEmpty(this.e)) {
            hashMap.put("receipt_sign", this.e);
            hashMap.put(VKApiConst.SIG, APMD5.toMd5(this.d.getBytes()));
        }
        hashMap.put("type", APAppDataInterface.singleton().mType);
        try {
            hashMap.put("language", Locale.getDefault().getISO3Language());
        } catch (Exception e2) {
        }
        APLog.i(a, "APOverSeaProvideReq:" + APTools.map2UrlParams(hashMap));
        String MaptoString = APCommMethod.MaptoString(hashMap);
        this.httpParam.reqParam.put("encrypt_msg", APToolAES.doEncode(MaptoString, APAppDataInterface.singleton().getCryptoKey()));
        this.httpParam.reqParam.put("msg_len", Integer.toString(MaptoString.length()));
    }

    public void startService() {
        startRequest();
    }
}
