package com.tencent.midas.oversea.network.modle;

import android.text.TextUtils;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APToolAES;
import com.tencent.midas.oversea.network.http.APHttpsReqPost;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.midas.oversea.safe.APSecretKeyManager;
import com.vk.sdk.VKAccessToken;
import java.util.HashMap;
import java.util.Locale;

public class APOverSeaInitReq extends APHttpsReqPost {
    public static final int KEYTYPE_CRYPTOKEY = 1;
    public static final int KEYTYPE_SECRETKEY = 0;
    private String a;
    private String b;
    private String c;
    public String cmd;
    public int curReqType = -1;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    public String openId;

    public APOverSeaInitReq(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        this.h = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{str2});
        this.i = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{str2});
        this.j = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{str2});
        this.k = "";
        try {
            this.k = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{APAppDataInterface.singleton().getCustomCgi(), str2});
        } catch (Exception e2) {
        }
        setUrl(this.k, this.h, this.i, this.j);
        this.cmd = str;
        this.openId = str3;
        this.a = str4;
        this.b = str5;
        this.c = str6;
        this.d = str7;
        this.e = str8;
        this.f = str9;
        this.g = str10;
    }

    public void constructParam() {
        String str;
        this.httpParam.reqParam.put("openid", this.openId);
        this.httpParam.reqParam.put(UnityPayHelper.PF, this.d);
        this.httpParam.reqParam.put("pfkey", this.e);
        this.httpParam.reqParam.put("zoneid", this.f);
        this.httpParam.reqParam.put("format", "json");
        this.httpParam.reqParam.put("key_len", "newkey");
        this.httpParam.reqParam.put("key_time", APAppDataInterface.singleton().getCryptKeyTime());
        HashMap hashMap = new HashMap();
        hashMap.put("openid", this.openId);
        hashMap.put("openkey", this.a);
        hashMap.put("session_id", this.b);
        hashMap.put("session_type", this.c);
        hashMap.put("session_channel", this.g);
        if (!TextUtils.isEmpty(APDataInterface.singleton().getSessionToken())) {
            this.httpParam.reqParam.put(GGLiveConstants.PARAM.SESSION_TOKEN, APDataInterface.singleton().getSessionToken());
        }
        hashMap.put("sdkversion", "androidoversea_v" + APGlobalInfo.SDK_VERSION);
        try {
            hashMap.put("language", Locale.getDefault().getISO3Language());
        } catch (Exception e2) {
        }
        APMidasPayAPI.singleton();
        String readSecretKey = APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).readSecretKey(this.openId);
        APAppDataInterface.singleton().setSecretKey(readSecretKey);
        APMidasPayAPI.singleton();
        String readCryptKey = APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).readCryptKey(this.openId);
        APAppDataInterface.singleton().setCryptKey(readCryptKey);
        APMidasPayAPI.singleton();
        String readCryptKeyTime = APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).readCryptKeyTime(this.openId);
        APAppDataInterface.singleton().setCryptKeyTime(readCryptKeyTime);
        if (TextUtils.isEmpty(readCryptKeyTime) || TextUtils.isEmpty(readCryptKey) || TextUtils.isEmpty(readSecretKey)) {
            if (!this.cmd.contains(APNetworkManager2.HTTP_KEY_GET_KEY)) {
                this.cmd += "|";
                this.cmd += APNetworkManager2.HTTP_KEY_GET_KEY;
                this.httpParam.setReqWithHttps();
                this.httpParam.port = "442";
                setUrl(this.k, this.h, this.i, this.j);
            }
            this.curReqType = 0;
            str = APAppDataInterface.singleton().getBaseKey();
            hashMap.put("key", APAppDataInterface.singleton().getBaseKey());
            this.httpParam.reqParam.put("get_key_type", VKAccessToken.SECRET);
            this.httpParam.reqParam.put("vid", APAppDataInterface.singleton().getVid());
            this.curReqType = 0;
        } else {
            this.curReqType = -1;
            str = APAppDataInterface.singleton().getCryptoKey();
            this.httpParam.setReqWithHttp();
            setUrl(this.k, this.h, this.i, this.j);
        }
        String MaptoString = APCommMethod.MaptoString(hashMap);
        String doEncode = APToolAES.doEncode(MaptoString, str);
        this.httpParam.reqParam.put("overseas_cmd", this.cmd);
        this.httpParam.reqParam.put("encrypt_msg", doEncode);
        this.httpParam.reqParam.put("msg_len", Integer.toString(MaptoString.length()));
    }

    public void startService() {
        startRequest();
    }
}
