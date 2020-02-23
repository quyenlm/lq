package com.tencent.midas.oversea.network.modle;

import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APToolAES;
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
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APOverSeaInitAns extends APBaseHttpAns {
    private String a;
    private String b;
    private JSONObject c;
    private String d;
    private String e;

    public APOverSeaInitAns(APHttpHandle aPHttpHandle, APHttpInstrument aPHttpInstrument, HashMap<String, APBaseHttpReq> hashMap, String str) {
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
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
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
                    APOverSeaInitReq aPOverSeaInitReq = (APOverSeaInitReq) aPBaseHttpReq;
                    String substring = APToolAES.doDecode(string, APAppDataInterface.singleton().getBaseKey()).substring(0, Integer.valueOf(string2).intValue());
                    String a2 = a(substring, "key");
                    String a3 = a(substring, "cryptkey");
                    String a4 = a(substring, "cryptkeytime");
                    APAppDataInterface.singleton().setSecretKey(a2);
                    APAppDataInterface.singleton().setCryptKey(a3);
                    APAppDataInterface.singleton().setCryptKeyTime(a4);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveSecretKey(aPOverSeaInitReq.openId, a2);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveCryptKey(aPOverSeaInitReq.openId, a3);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveCryptKeyTime(aPOverSeaInitReq.openId, a4);
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

    public String getAmount() {
        return this.d;
    }

    public String getBillno() {
        return this.b;
    }

    public String getCurrentType() {
        return this.e;
    }

    public JSONObject getInfo() {
        return this.c;
    }

    public String getOrderInfo() {
        return this.a;
    }

    public void onErrorAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onFinishAns(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        super.onFinishAns(bArr, aPBaseHttpReq);
        progressJson(bArr, aPBaseHttpReq);
    }

    public void onReceiveAns(byte[] bArr, int i, long j, APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStartAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStopAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void progressJson(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        String str = ((APOverSeaInitReq) aPBaseHttpReq).cmd;
        APLog.d("APOverSeaInitAns", "content=" + bArr);
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
        } catch (Exception e2) {
        }
    }
}
