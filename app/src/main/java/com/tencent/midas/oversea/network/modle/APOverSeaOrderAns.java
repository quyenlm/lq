package com.tencent.midas.oversea.network.modle;

import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APToolAES;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APBaseHttpReq;
import com.tencent.midas.oversea.network.http.APErrorCode;
import com.tencent.midas.oversea.network.http.APHttpHandle;
import com.tencent.midas.oversea.network.http.APHttpInstrument;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.tp.a.h;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class APOverSeaOrderAns extends APBaseHttpAns {
    private String a;
    private String b;
    private JSONObject c;
    private String d;
    private String e;

    public APOverSeaOrderAns(APHttpHandle aPHttpHandle, APHttpInstrument aPHttpInstrument, HashMap<String, APBaseHttpReq> hashMap, String str) {
        super(aPHttpHandle, aPHttpInstrument.getObserver(), hashMap, str);
    }

    private void a(JSONObject jSONObject) {
        try {
            if (jSONObject.has("order_info")) {
                this.a = jSONObject.getString("order_info");
            }
            if (jSONObject.has("billno")) {
                this.b = jSONObject.getString("billno");
            }
            if (jSONObject.has(APNetworkManager2.HTTP_KEY_OVERSEAINFO)) {
                this.c = jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_OVERSEAINFO);
            }
            if (jSONObject.has("currency_amt")) {
                this.d = jSONObject.getString("currency_amt");
            }
            if (jSONObject.has("currency_type")) {
                this.e = jSONObject.getString("currency_type");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private void a(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        boolean z = false;
        if (bArr == null) {
            this.resultCode = -1;
            this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n" + APErrorCode.getErrorCode(1004);
            return;
        }
        try {
            String str = new String(bArr);
            JSONObject jSONObject = new JSONObject(str);
            this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
            APLog.i("APImpAns", "resultData=" + str);
            if (this.resultCode == 0) {
                if (!jSONObject.has("use_old_process") || !"1".equals(jSONObject.getString("use_old_process"))) {
                    z = true;
                }
                APAppDataInterface.singleton().setIsNewCGI(z);
                String string = jSONObject.getString("rsp_msg");
                int indexOf = string.indexOf("_");
                a(new JSONObject(APToolAES.doDecode(string.substring(0, indexOf), APAppDataInterface.singleton().getCryptoKey(), Integer.parseInt(string.substring(indexOf + 1, string.length())))));
                return;
            }
            this.resultMsg = jSONObject.getString("msg");
            String str2 = jSONObject.getString("err_code").toString();
            if (!str2.equals("")) {
                this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n(" + str2 + h.b;
            }
        } catch (Exception e2) {
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
        a(bArr, aPBaseHttpReq);
    }

    public void onReceiveAns(byte[] bArr, int i, long j, APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStartAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStopAns(APBaseHttpReq aPBaseHttpReq) {
    }
}
