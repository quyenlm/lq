package com.tencent.midas.oversea.network.modle;

import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APToolAES;
import com.tencent.midas.oversea.comm.APTools;
import com.tencent.midas.oversea.network.http.APHttpsReqPost;
import com.tencent.midas.oversea.network.http.APUrlConf;
import com.tencent.mtt.spcialcall.sdk.RecommendParams;
import com.vk.sdk.VKAccessToken;
import java.util.HashMap;
import java.util.Locale;

public class APGetKeyReq extends APHttpsReqPost {
    public static final int CHANGEKEY_REASON_SAVEACCT = 1;
    public static final int KEYTYPE_CRYPTOKEY = 1;
    public static final int KEYTYPE_SECRETKEY = 0;
    public static final int THIRD_SESSION_CHANGEKEY_REASON_SAVEACCT = 2;
    private int a;
    public int changeKeyReason = 0;
    public int curReqType;

    public APGetKeyReq() {
        this.httpParam.port = "442";
        this.httpParam.reTryTimes = 3;
        String offerid = APAppDataInterface.singleton().getOfferid();
        String format = String.format("/v1/r/%s/mobile_get_key", new Object[]{offerid});
        String format2 = String.format("/v1/r/%s/mobile_get_key", new Object[]{offerid});
        String format3 = String.format("/v1/r/%s/mobile_get_key", new Object[]{offerid});
        String str = "";
        try {
            str = String.format(APUrlConf.AP_GETDECKEY_CUSTOM_FCG, new Object[]{APAppDataInterface.singleton().getCustomCgi(), offerid});
        } catch (Exception e) {
        }
        setUrl(str, format, format2, format3);
    }

    public void constructParam() {
        String MaptoString;
        String doEncode;
        APDataInterface singleton = APDataInterface.singleton();
        this.httpParam.reqParam.put(UnityPayHelper.PF, APDataInterface.singleton().getUserInfo().pf);
        this.httpParam.reqParam.put("pfkey", APDataInterface.singleton().getUserInfo().pfKey);
        this.httpParam.reqParam.put("format", "json");
        this.httpParam.reqParam.put(GGLiveConstants.PARAM.SESSION_TOKEN, APDataInterface.singleton().getSessionToken());
        this.httpParam.reqParam.put("sdkversion", APCommMethod.getVersion());
        this.httpParam.reqParam.put("key_len", "newkey");
        this.httpParam.reqParam.put("user_uuid", APTools.urlEncode(singleton.getUserUniqueUuid(), 1));
        this.httpParam.reqParam.put("user_imei", APTools.urlEncode(singleton.getUserIMEI(), 1));
        this.httpParam.reqParam.put("user_mac", APTools.urlEncode(singleton.getUserMAC(), 1));
        HashMap hashMap = new HashMap();
        hashMap.put("sdkversion", "androidoversea_v" + APGlobalInfo.SDK_VERSION);
        try {
            hashMap.put("language", Locale.getDefault().getISO3Language());
        } catch (Exception e) {
        }
        hashMap.put("extend", singleton.getCgiExtends());
        if (this.changeKeyReason == 1) {
            this.httpParam.reqParam.put("openid", APDataInterface.singleton().getUserInfo().payId);
            hashMap.put("openid", APDataInterface.singleton().getUserInfo().payId);
            hashMap.put("openkey", APDataInterface.singleton().getUserInfo().authKey);
            hashMap.put("session_id", RecommendParams.KEY_UIN);
            hashMap.put("session_type", "skey");
        } else if (this.changeKeyReason == 2) {
            this.httpParam.reqParam.put("openid", APDataInterface.singleton().getUserInfo().payId);
            hashMap.put("openid", APDataInterface.singleton().getUserInfo().payId);
            hashMap.put("openkey", APDataInterface.singleton().getUserInfo().authKey);
            hashMap.put("session_id", RecommendParams.KEY_UIN);
            hashMap.put("session_type", "skey");
        } else {
            this.httpParam.reqParam.put("openid", APDataInterface.singleton().getUserInfo().openId);
            hashMap.put("openid", APDataInterface.singleton().getUserInfo().openId);
            hashMap.put("openkey", APDataInterface.singleton().getUserInfo().openKey);
            hashMap.put("session_id", APDataInterface.singleton().getUserInfo().sessionId);
            hashMap.put("session_type", APDataInterface.singleton().getUserInfo().sessionType);
            hashMap.put("session_channel", APDataInterface.singleton().getUserInfo().iChannel);
        }
        switch (this.a) {
            case 0:
                hashMap.put("key", APAppDataInterface.singleton().getBaseKey());
                this.httpParam.reqParam.put("type", VKAccessToken.SECRET);
                this.httpParam.reqParam.put("vid", APAppDataInterface.singleton().getVid());
                this.curReqType = 0;
                MaptoString = APCommMethod.MaptoString(hashMap);
                doEncode = APToolAES.doEncode(MaptoString, APAppDataInterface.singleton().getBaseKey());
                break;
            case 1:
                hashMap.put("key", APAppDataInterface.singleton().getSecretKey());
                this.httpParam.reqParam.put("type", "crypto");
                this.httpParam.reqParam.put("vid", "");
                this.curReqType = 1;
                MaptoString = APCommMethod.MaptoString(hashMap);
                doEncode = APToolAES.doEncode(MaptoString, APAppDataInterface.singleton().getSecretKey());
                break;
            default:
                return;
        }
        this.httpParam.reqParam.put("encrypt_msg", doEncode);
        this.httpParam.reqParam.put("msg_len", Integer.toString(MaptoString.length()));
        this.httpParam.reqParam.put("xg_mid", APAppDataInterface.singleton().getXGMid());
        if (APAppDataInterface.singleton().getEnv().equals(APGlobalInfo.DevEnv)) {
            this.httpParam.reqParam.put("offer_id", APAppDataInterface.singleton().getOfferid());
        }
    }

    public void starService(int i, int i2) {
        this.a = i;
        this.changeKeyReason = i2;
        startRequest();
    }
}
