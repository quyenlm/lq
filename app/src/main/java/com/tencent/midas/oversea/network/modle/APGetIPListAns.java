package com.tencent.midas.oversea.network.modle;

import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APBaseHttpParam;
import com.tencent.midas.oversea.network.http.APBaseHttpReq;
import com.tencent.midas.oversea.network.http.APErrorCode;
import com.tencent.midas.oversea.network.http.APHttpHandle;
import com.tencent.midas.oversea.network.http.APIPList;
import com.tencent.midas.oversea.network.http.APIPState;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import com.tencent.tp.a.h;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APGetIPListAns extends APBaseHttpAns {
    public APGetIPListAns(APHttpHandle aPHttpHandle, IAPHttpAnsObserver iAPHttpAnsObserver, HashMap<String, APBaseHttpReq> hashMap, String str) {
        super(aPHttpHandle, iAPHttpAnsObserver, hashMap, str);
    }

    private void a(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        String str = new String(bArr);
        APLog.i("APGetIPListAns", "resultData=" + str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
            if (this.resultCode != 0) {
                this.resultMsg = jSONObject.getString("msg");
                String str2 = jSONObject.getString("err_code").toString();
                if (!str2.equals("")) {
                    this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n(" + str2 + h.b;
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
                } catch (Exception e) {
                }
            }
        } catch (JSONException e2) {
            this.resultMsg = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_busy") + "\n" + APErrorCode.getErrorCode(1003);
            e2.printStackTrace();
        }
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
