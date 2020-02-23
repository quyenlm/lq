package com.tencent.midas.oversea.network.modle;

import android.text.TextUtils;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.network.http.APHttpReqPost;
import com.tencent.midas.oversea.network.http.APUrlConf;

public class APGetIPListReq extends APHttpReqPost {
    public APGetIPListReq() {
        String offerid = APAppDataInterface.singleton().getOfferid();
        String format = String.format("/v1/r/%s/get_ip_list", new Object[]{offerid});
        String format2 = String.format("/v1/r/%s/get_ip_list", new Object[]{offerid});
        String format3 = String.format("/v1/r/%s/get_ip_list", new Object[]{offerid});
        String str = "";
        try {
            str = String.format(APUrlConf.AP_QUERY_IPLIST_CUSTOM_FCG, new Object[]{APAppDataInterface.singleton().getCustomCgi(), offerid});
        } catch (Exception e) {
        }
        setUrl(str, format, format2, format3);
        this.httpParam.reTryTimes = 0;
    }

    public void constructParam() {
        this.httpParam.reqParam.put("openid", APDataInterface.singleton().getUserInfo().openId);
        this.httpParam.reqParam.put("openkey", APDataInterface.singleton().getUserInfo().openKey);
        this.httpParam.reqParam.put("session_id", APDataInterface.singleton().getUserInfo().sessionId);
        this.httpParam.reqParam.put("session_type", APDataInterface.singleton().getUserInfo().sessionType);
        this.httpParam.reqParam.put(UnityPayHelper.PF, APDataInterface.singleton().getUserInfo().pf);
        this.httpParam.reqParam.put("pfkey", APDataInterface.singleton().getUserInfo().pfKey);
        this.httpParam.reqParam.put("format", "json");
        this.httpParam.reqParam.put("session_channel", APDataInterface.singleton().getUserInfo().iChannel);
        if (!TextUtils.isEmpty(APDataInterface.singleton().getSessionToken())) {
            this.httpParam.reqParam.put(GGLiveConstants.PARAM.SESSION_TOKEN, APDataInterface.singleton().getSessionToken());
        }
        this.httpParam.reqParam.put("sdkversion", APCommMethod.getVersion());
        this.httpParam.reqParam.put("key_len", "newkey");
        this.httpParam.reqParam.put("key_time", APAppDataInterface.singleton().getCryptKeyTime());
        this.httpParam.reqParam.put("xg_mid", APAppDataInterface.singleton().getXGMid());
        this.httpParam.reqParam.put("sdkversion", "androidoversea_v" + APGlobalInfo.SDK_VERSION);
        if (APAppDataInterface.singleton().getEnv().equals(APGlobalInfo.DevEnv)) {
            this.httpParam.reqParam.put("offer_id", APAppDataInterface.singleton().getOfferid());
        }
    }

    public void startService() {
        constructParam();
        startRequest();
    }
}
