package com.tencent.midas.oversea.network.modle;

import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APToolAES;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APBaseHttpReq;
import com.tencent.midas.oversea.network.http.APHttpError;
import com.tencent.midas.oversea.network.http.APHttpHandle;
import com.tencent.midas.oversea.network.http.APHttpInstrument;
import com.tencent.midas.oversea.safe.APSecretKeyManager;
import com.tencent.mtt.spcialcall.sdk.RecommendParams;
import com.tencent.tp.a.h;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class APGetKeyAns extends APBaseHttpAns {
    private String a = "";
    private int b;

    public APGetKeyAns(APHttpHandle aPHttpHandle, APHttpInstrument aPHttpInstrument, HashMap<String, APBaseHttpReq> hashMap, String str) {
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
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("user_info");
            String string = jSONObject2.getString(RecommendParams.KEY_UIN);
            int i = jSONObject2.getInt("uin_len");
            int i2 = jSONObject2.getInt("codeindex");
            if (!string.equals("") && i2 < this.AesEncodeKey.length) {
                APToolAES.doDecode(string, this.AesEncodeKey[i2]).substring(0, i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void a(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        String a2;
        String a3;
        String str = new String(bArr);
        APGetKeyReq aPGetKeyReq = (APGetKeyReq) aPBaseHttpReq;
        APLog.i("APGetKeyAns", "resultData=" + str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
            if (this.resultCode == 0) {
                String string = jSONObject.getString("key_info");
                String string2 = jSONObject.getString("key_info_len");
                this.b = aPGetKeyReq.curReqType;
                if (jSONObject.has("need_change_key")) {
                    if (jSONObject.getInt("need_change_key") == 1) {
                        APAppDataInterface.singleton().setChangeKey(true);
                    } else {
                        APAppDataInterface.singleton().setChangeKey(false);
                    }
                }
                String str2 = null;
                if (this.b == 0) {
                    String substring = APToolAES.doDecode(string, APAppDataInterface.singleton().getBaseKey()).substring(0, Integer.valueOf(string2).intValue());
                    str2 = a(substring, "key");
                    a2 = a(substring, "cryptkey");
                    a3 = a(substring, "cryptkeytime");
                } else {
                    String substring2 = APToolAES.doDecode(string, APAppDataInterface.singleton().getSecretKey()).substring(0, Integer.valueOf(string2).intValue());
                    a2 = a(substring2, "key");
                    a3 = a(substring2, "cryptkeytime");
                }
                if (aPGetKeyReq.changeKeyReason == 1 || aPGetKeyReq.changeKeyReason == 2) {
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveSecretKey(APDataInterface.singleton().getUserInfo().payId, str2);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveCryptKey(APDataInterface.singleton().getUserInfo().payId, a2);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveCryptKeyTime(APDataInterface.singleton().getUserInfo().payId, a3);
                } else {
                    APAppDataInterface.singleton().setSecretKey(str2);
                    APAppDataInterface.singleton().setCryptKey(a2);
                    APAppDataInterface.singleton().setCryptKeyTime(a3);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveSecretKey(APDataInterface.singleton().getUserInfo().openId, str2);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveCryptKey(APDataInterface.singleton().getUserInfo().openId, a2);
                    APMidasPayAPI.singleton();
                    APSecretKeyManager.getInstance(APMidasPayAPI.applicationContext).saveCryptKeyTime(APDataInterface.singleton().getUserInfo().openId, a3);
                }
                a(jSONObject);
                return;
            }
            this.resultMsg = jSONObject.getString("msg");
            String str3 = jSONObject.getString("err_code").toString();
            if (!str3.equals("")) {
                this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n(" + str3 + h.b;
            }
        } catch (JSONException e) {
            this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + APHttpError.HTTP_ERROR_JSONERROR;
            e.printStackTrace();
        }
    }

    public String getKey() {
        return this.a;
    }

    public int getKeyType() {
        return this.b;
    }

    public void onErrorAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onFinishAns(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        super.onFinishAns(bArr, aPBaseHttpReq);
        a(bArr, aPBaseHttpReq);
    }

    public void onReceiveAns(byte[] bArr, int i, long j, APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStartAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStopAns(APBaseHttpReq aPBaseHttpReq) {
    }
}
