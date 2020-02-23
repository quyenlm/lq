package com.tencent.midas.oversea.network.modle;

import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APMD5;
import com.tencent.midas.oversea.comm.APToolAES;
import com.tencent.midas.oversea.comm.APTools;
import com.tencent.midas.oversea.network.http.APHttpsReqPost;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.midas.oversea.safe.APSecretKeyManager;
import com.tencent.qqgamemi.util.TimeUtils;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;

public class APOverSeaCommReq extends APHttpsReqPost {
    public static final int KEYTYPE_CRYPTOKEY = 1;
    public static final int KEYTYPE_SECRETKEY = 0;
    private static final String a = APOverSeaOrderReq.class.getSimpleName();
    private String b;
    private String c;
    public String cmd;
    public int curReqType = -1;
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
    private String n;
    private boolean o;
    private String p;
    private String q;
    private String r = "";
    private String s = "";
    private String t = "";
    private String u = "";
    private String v = "";
    private String w = "";

    public APOverSeaCommReq(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, boolean z, String str16) {
        String str17 = APDataInterface.singleton().getUserInfo().openId;
        APMidasPayAPI.singleton();
        APAppDataInterface.singleton().setSecretKey(APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).readSecretKey(str17));
        APMidasPayAPI.singleton();
        APAppDataInterface.singleton().setCryptKey(APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).readCryptKey(str17));
        APMidasPayAPI.singleton();
        APAppDataInterface.singleton().setCryptKeyTime(APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).readCryptKeyTime(str17));
        this.t = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{APAppDataInterface.singleton().getOfferid()});
        this.u = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{APAppDataInterface.singleton().getOfferid()});
        this.v = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{APAppDataInterface.singleton().getOfferid()});
        this.w = "";
        setUrl(this.w, this.t, this.u, this.v);
        this.cmd = str;
        this.b = str2;
        this.d = str4;
        this.e = str5;
        this.c = str3;
        this.f = str7;
        this.g = str8;
        this.h = str9;
        this.i = str10;
        this.j = str11;
        this.l = str13;
        this.m = str14;
        this.n = str15;
        this.o = z;
        this.p = str16;
        this.q = str6;
        this.k = str12;
    }

    public void constructParam() {
        String str;
        APDataInterface singleton = APDataInterface.singleton();
        this.httpParam.reqParam.put("openid", singleton.getUserInfo().openId);
        this.httpParam.reqParam.put(UnityPayHelper.PF, singleton.getUserInfo().pf);
        this.httpParam.reqParam.put("pfkey", singleton.getUserInfo().pfKey);
        this.httpParam.reqParam.put("zoneid", singleton.getUserInfo().zoneId);
        this.httpParam.reqParam.put("format", "json");
        this.httpParam.reqParam.put("key_len", "newkey");
        this.httpParam.reqParam.put("key_time", APAppDataInterface.singleton().getCryptKeyTime());
        this.httpParam.reqParam.put("extend", singleton.getCgiExtends());
        this.httpParam.reqParam.put(GGLiveConstants.PARAM.SESSION_TOKEN, APDataInterface.singleton().getSessionToken());
        HashMap hashMap = new HashMap();
        hashMap.put("openid", singleton.getUserInfo().openId);
        hashMap.put("openkey", singleton.getUserInfo().openKey);
        hashMap.put("session_id", singleton.getUserInfo().sessionId);
        hashMap.put("session_type", singleton.getUserInfo().sessionType);
        hashMap.put("session_channel", singleton.getUserInfo().iChannel);
        hashMap.put("drm_info", URLEncoder.encode(singleton.mDrmInfo));
        APLog.i(a, "URLEncoder.encode(dataInterface.mDrmInfo):" + URLEncoder.encode(singleton.mDrmInfo));
        if (!TextUtils.isEmpty(APAppDataInterface.singleton().mType)) {
            hashMap.put("type", APAppDataInterface.singleton().mType);
        }
        APMidasPayAPI.singleton();
        if (System.currentTimeMillis() - APMidasPayAPI.applicationContext.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0).getLong("updateIPPreTime", 0) > TimeUtils.MILLIS_IN_DAY && !this.cmd.contains(APNetworkManager2.HTTP_KEY_GETIPLIST)) {
            this.cmd += "|";
            this.cmd += APNetworkManager2.HTTP_KEY_GETIPLIST;
        }
        hashMap.put("buy_quantity", this.k);
        if (!TextUtils.isEmpty(this.e)) {
            hashMap.put("currency_type", this.e);
        }
        if (!TextUtils.isEmpty(this.d)) {
            hashMap.put("country", this.d);
        }
        if (!TextUtils.isEmpty(this.b)) {
            hashMap.put("service_code", this.b);
        }
        hashMap.put("sdkversion", "androidoversea_v" + APGlobalInfo.SDK_VERSION);
        if (!TextUtils.isEmpty(this.c)) {
            hashMap.put("userip", this.c);
        }
        try {
            hashMap.put("language", Locale.getDefault().getISO3Language());
        } catch (Exception e2) {
        }
        if (APAppDataInterface.singleton().getSecretKey().length() <= 0 || APAppDataInterface.singleton().getCryptoKey().equals("")) {
            if (!this.cmd.contains(APNetworkManager2.HTTP_KEY_GET_KEY)) {
                this.cmd += "|";
                this.cmd += APNetworkManager2.HTTP_KEY_GET_KEY;
                this.httpParam.setReqWithHttps();
                this.httpParam.port = "442";
                setUrl(this.w, this.t, this.u, this.v);
            }
            this.curReqType = 0;
            String baseKey = APAppDataInterface.singleton().getBaseKey();
            hashMap.put("key", APAppDataInterface.singleton().getBaseKey());
            this.httpParam.reqParam.put("get_key_type", VKAccessToken.SECRET);
            this.httpParam.reqParam.put("vid", APAppDataInterface.singleton().getVid());
            this.curReqType = 0;
            str = baseKey;
        } else {
            this.curReqType = -1;
            String cryptoKey = APAppDataInterface.singleton().getCryptoKey();
            this.httpParam.setReqWithHttp();
            setUrl(this.w, this.t, this.u, this.v);
            str = cryptoKey;
        }
        if (!TextUtils.isEmpty(this.h)) {
            hashMap.put("pay_method", this.h);
        }
        if (!TextUtils.isEmpty(this.g)) {
            hashMap.put("productid", this.g);
        }
        if (!TextUtils.isEmpty(this.f)) {
            hashMap.put("amt", this.f);
        }
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(this.j)) {
            sb.append("gw_amt=" + this.j);
            sb.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        }
        if (!TextUtils.isEmpty(this.q)) {
            sb.append("gw_currency=" + this.q);
            sb.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        }
        if (APAppDataInterface.singleton().getEnv().equals(APGlobalInfo.DevEnv) || APAppDataInterface.singleton().getEnv().equals(APGlobalInfo.TestEnv)) {
            sb.append("sandbox=true");
            sb.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        }
        if (!TextUtils.isEmpty(this.h) && this.h.equals("os_doku")) {
            sb.append("from=doku_sdk");
            sb.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        }
        String str2 = APDataInterface.singleton().getUserInfo().extras;
        APLog.i(a, "extra:" + str2);
        if (!TextUtils.isEmpty(str2)) {
            HashMap<String, String> kv2Map = APTools.kv2Map(str2);
            if (kv2Map.containsKey("paySubChannelID")) {
                sb.append("pay_channel=" + kv2Map.get("paySubChannelID"));
            }
        }
        APLog.i(a, "wf_info:" + sb.toString());
        hashMap.put("wf_info", APTools.urlEncode(sb.toString(), 1));
        if (!TextUtils.isEmpty(this.r)) {
            hashMap.put(GGLiveConstants.PARAM.TOKEN_ID, this.r);
        }
        if (this.cmd.contains(APNetworkManager2.HTTP_KEY_OVERSEAPROVIDE)) {
            if (!TextUtils.isEmpty(this.l)) {
                hashMap.put("billno", this.l);
            }
            if (!TextUtils.isEmpty(this.m)) {
                hashMap.put("receipt", this.m);
            }
            hashMap.put("buy_quantity", this.p);
            if (!TextUtils.isEmpty(this.n)) {
                hashMap.put("receipt_sign", this.n);
                hashMap.put(VKApiConst.SIG, APMD5.toMd5(this.m.getBytes()));
            }
            if (this.o) {
                this.httpParam.reqParam.put("action", "reprovide");
            } else {
                this.httpParam.reqParam.put("action", APNetworkManager2.HTTP_KEY_OVERSEAPROVIDE);
            }
        }
        String MaptoString = APCommMethod.MaptoString(hashMap);
        String doEncode = APToolAES.doEncode(MaptoString, str);
        this.httpParam.reqParam.put("overseas_cmd", this.cmd);
        this.httpParam.reqParam.put("encrypt_msg", doEncode);
        this.httpParam.reqParam.put("msg_len", Integer.toString(MaptoString.length()));
    }

    public void startService() {
        if (APPayMananger.singleton().getCurBaseRequest() != null && APPayMananger.singleton().getCurBaseRequest().mType.equals(APMidasBaseRequest.OFFER_TYPE_BG)) {
            this.t = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{APAppDataInterface.singleton().getOfferid()});
            this.u = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{APAppDataInterface.singleton().getOfferid()});
            this.v = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{APAppDataInterface.singleton().getOfferid()});
            if (!TextUtils.isEmpty(this.i)) {
                int lastIndexOf = this.i.lastIndexOf("?");
                if (lastIndexOf > 0) {
                    String substring = this.i.substring(0, lastIndexOf);
                    int lastIndexOf2 = this.i.lastIndexOf("token_id=");
                    if (lastIndexOf2 > 0) {
                        this.r = this.i.substring(lastIndexOf2 + 9);
                        String substring2 = this.i.substring("/v1/".length(), substring.length() - "/v1/".length());
                        this.s = substring2.substring(0, substring2.indexOf(Constants.URL_PATH_DELIMITER));
                        this.t = this.t.replace("/v1/r", "/v1/" + this.s);
                        this.u = this.u.replace("/v1/r", "/v1/" + this.s);
                        this.v = this.v.replace("/v1/r", "/v1/" + this.s);
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            setUrl("", this.t, this.u, this.v);
        }
        startRequest();
    }
}
