package com.tencent.midas.oversea.network.http;

import android.os.Message;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APToolAES;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APBaseHttpAns implements IAPHttpAns {
    protected final String[] AesEncodeKey;
    private APHttpHandle a;
    private HashMap<String, APBaseHttpReq> b;
    private IAPHttpAnsObserver c;
    private int d;
    private final int e;
    protected String errorMsg;
    private int f;
    protected APBaseHttpReq httpClient;
    public String httpReqKey;
    protected int resultCode = -1;
    protected String resultMsg;

    public APBaseHttpAns() {
        StringBuilder sb = new StringBuilder();
        APMidasPayAPI.singleton();
        this.resultMsg = sb.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append("\n").append(APErrorCode.getErrorCode(2000)).toString();
        this.errorMsg = "";
        this.httpReqKey = "";
        this.d = 0;
        this.e = 1;
        this.f = 0;
        this.AesEncodeKey = new String[]{"Td8qRx7IdbbSyw3K", "elddjmxNE2FK8cch", "n6QnJOTocDGX5dXR", "caUdsBbJ1oOxMbPy", "ehDFwSSDOFz3U1d3", "nmiFzdsTgUYGcMeg", "t3W6mdGCbIfFcwdR", "PLSeUfBBSgfDWAuA", "ayGzfJkNBZKE9UZf", "yVBtdRgAEx3EgG31"};
    }

    public APBaseHttpAns(APHttpHandle aPHttpHandle, IAPHttpAnsObserver iAPHttpAnsObserver, HashMap<String, APBaseHttpReq> hashMap, String str) {
        StringBuilder sb = new StringBuilder();
        APMidasPayAPI.singleton();
        this.resultMsg = sb.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append("\n").append(APErrorCode.getErrorCode(2000)).toString();
        this.errorMsg = "";
        this.httpReqKey = "";
        this.d = 0;
        this.e = 1;
        this.f = 0;
        this.AesEncodeKey = new String[]{"Td8qRx7IdbbSyw3K", "elddjmxNE2FK8cch", "n6QnJOTocDGX5dXR", "caUdsBbJ1oOxMbPy", "ehDFwSSDOFz3U1d3", "nmiFzdsTgUYGcMeg", "t3W6mdGCbIfFcwdR", "PLSeUfBBSgfDWAuA", "ayGzfJkNBZKE9UZf", "yVBtdRgAEx3EgG31"};
        this.a = aPHttpHandle;
        this.b = hashMap;
        this.httpReqKey = str;
        this.c = iAPHttpAnsObserver;
        this.a.register(this.httpReqKey, iAPHttpAnsObserver);
    }

    private Object a(JSONObject jSONObject, String str) {
        if (!jSONObject.has("user_msg")) {
            return null;
        }
        try {
            String string = jSONObject.getString("user_msg");
            int indexOf = string.indexOf("_");
            if (indexOf == -1) {
                return null;
            }
            return new JSONObject(APToolAES.doDecode(string.substring(0, indexOf), APAppDataInterface.singleton().getCryptoKey(), Integer.parseInt(string.substring(indexOf + 1, string.length())))).get(str);
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private void a() {
        Message message = new Message();
        message.what = 4;
        message.obj = this;
        this.a.sendMessage(message);
    }

    private void a(int i) {
        this.httpClient.setContent(("{\"ret\":" + i + ",\"err_code\":\"100-100-5001\",\"msg\":\"getKey error\"}").getBytes());
        reRegister();
        onFinish(this.httpClient);
    }

    /* access modifiers changed from: private */
    public void a(APBaseHttpAns aPBaseHttpAns, boolean z) {
        int resultCode2 = aPBaseHttpAns.getResultCode();
        switch (resultCode2) {
            case 0:
                String secretKey = APAppDataInterface.singleton().getSecretKey();
                String cryptoKey = APAppDataInterface.singleton().getCryptoKey();
                if (secretKey.length() <= 0 || cryptoKey.length() <= 0) {
                    if (z) {
                        a(-100);
                        return;
                    }
                    return;
                } else if (z) {
                    requestAgain();
                    return;
                } else {
                    return;
                }
            case 1018:
                if (z) {
                    this.httpClient.setContent("{\"ret\":1018}".getBytes());
                    reRegister();
                    onFinish(this.httpClient);
                    return;
                }
                return;
            default:
                if (z) {
                    a(resultCode2);
                    return;
                }
                return;
        }
    }

    private void a(APBaseHttpReq aPBaseHttpReq) {
        this.b.put(this.httpReqKey, aPBaseHttpReq);
    }

    private void a(byte[] bArr) {
        Message message = new Message();
        message.what = 3;
        message.obj = this;
        this.a.sendMessage(message);
    }

    private void a(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        if (bArr != null) {
            String str = new String(bArr);
            if (!this.httpReqKey.equals("datareport") && !str.startsWith("pushmsg")) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    this.resultCode = Integer.parseInt(jSONObject.getString("ret").toString());
                    APLog.i("APImpAns", "resultCode=" + this.resultCode);
                    switch (this.resultCode) {
                        case 0:
                            if (jSONObject.has("need_change_key") && jSONObject.getInt("need_change_key") == 1) {
                                APAppDataInterface.singleton().setChangeKey(true);
                                changeKey(false);
                                return;
                            }
                            return;
                        case 1018:
                            return;
                        case APGlobalInfo.RET_SECKEYERROR:
                        case APGlobalInfo.RET_SECKEYVALID:
                            APLog.i("APImpAns", "resultData=" + str);
                            changeKey(true);
                            return;
                        default:
                            return;
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                e2.printStackTrace();
            }
        }
    }

    private void b() {
        Message message = new Message();
        message.what = 5;
        message.obj = this;
        this.a.sendMessage(message);
    }

    private void b(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
        int i;
        int i2;
        int i3;
        if (bArr != null) {
            String str = new String(bArr);
            APLog.i("APImpAns", "resultData=" + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has(APNetworkManager2.HTTP_KEY_OVERSEAINFO)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_OVERSEAINFO);
                    if (jSONObject2.has("ret") && ((i3 = jSONObject2.getInt("ret")) == 1094 || i3 == 1099)) {
                        APAppDataInterface.singleton().setSecretKey("");
                        APAppDataInterface.singleton().setCryptKey("");
                        APAppDataInterface.singleton().setCryptKeyTime("");
                        requestAgain();
                        return;
                    }
                }
                if (jSONObject.has(APNetworkManager2.HTTP_KEY_OVERSEAORDER)) {
                    JSONObject jSONObject3 = jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_OVERSEAORDER);
                    if (jSONObject3.has("ret") && ((i2 = jSONObject3.getInt("ret")) == 1094 || i2 == 1099)) {
                        APAppDataInterface.singleton().setSecretKey("");
                        APAppDataInterface.singleton().setCryptKey("");
                        APAppDataInterface.singleton().setCryptKeyTime("");
                        requestAgain();
                        return;
                    }
                }
                if (jSONObject.has(APNetworkManager2.HTTP_KEY_OVERSEAPROVIDE)) {
                    JSONObject jSONObject4 = jSONObject.getJSONObject(APNetworkManager2.HTTP_KEY_OVERSEAPROVIDE);
                    if (jSONObject4.has("ret") && ((i = jSONObject4.getInt("ret")) == 1094 || i == 1099)) {
                        APAppDataInterface.singleton().setSecretKey("");
                        APAppDataInterface.singleton().setCryptKey("");
                        APAppDataInterface.singleton().setCryptKeyTime("");
                        requestAgain();
                        return;
                    }
                }
            } catch (JSONException e2) {
                APLog.i("APBaseHttpAns", "newCGIProgress jsonexception" + e2.toString());
            }
            onFinishAns(bArr, aPBaseHttpReq);
            a(bArr);
        }
    }

    private void c() {
        this.b.remove(this.httpReqKey);
    }

    public void changeKey(boolean z) {
        APNetworkManager.getInstance().getKey(0, this.f, new b(this, z));
    }

    /* access modifiers changed from: protected */
    public JSONArray decJsonArray(JSONObject jSONObject, String str) {
        JSONArray jSONArray = new JSONArray();
        if (APAppDataInterface.singleton().getEnv().equals(APGlobalInfo.DevEnv)) {
            try {
                return jSONObject.getJSONArray(str);
            } catch (JSONException e2) {
                e2.printStackTrace();
                return jSONArray;
            }
        } else if (!jSONObject.has(str)) {
            return jSONArray;
        } else {
            try {
                String string = jSONObject.getString(str);
                int indexOf = string.indexOf("_");
                if (indexOf == -1) {
                    return jSONArray;
                }
                return new JSONArray(APToolAES.doDecode(string.substring(0, indexOf), APAppDataInterface.singleton().getCryptoKey(), Integer.parseInt(string.substring(indexOf + 1, string.length()))));
            } catch (JSONException e3) {
                e3.printStackTrace();
                return jSONArray;
            } catch (NumberFormatException e4) {
                e4.printStackTrace();
                return jSONArray;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int decJsonInt(JSONObject jSONObject, String str) {
        if (APAppDataInterface.singleton().getEnv().equals(APGlobalInfo.DevEnv)) {
            try {
                return jSONObject.getInt(str);
            } catch (JSONException e2) {
                e2.printStackTrace();
                return 0;
            }
        } else {
            Object a2 = a(jSONObject, str);
            if (a2 == null) {
                return 0;
            }
            if (a2 instanceof Integer) {
                return ((Integer) a2).intValue();
            }
            if (a2 instanceof Number) {
                return ((Number) a2).intValue();
            }
            if (a2 instanceof String) {
                try {
                    return (int) Double.parseDouble((String) a2);
                } catch (NumberFormatException e3) {
                }
            }
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public String decJsonString(JSONObject jSONObject, String str) {
        if (APAppDataInterface.singleton().getEnv().equals(APGlobalInfo.DevEnv)) {
            try {
                return jSONObject.getString(str);
            } catch (JSONException e2) {
                e2.printStackTrace();
                return "";
            }
        } else {
            Object a2 = a(jSONObject, str);
            return a2 instanceof String ? (String) a2 : a2 != null ? String.valueOf(a2) : "";
        }
    }

    /* access modifiers changed from: protected */
    public String decString(String str) {
        try {
            APLog.i("APBaseHttpAns", "content:" + str);
            int indexOf = str.indexOf("_");
            if (indexOf == -1) {
                return null;
            }
            return APToolAES.doDecode(str.substring(0, indexOf), APAppDataInterface.singleton().getCryptoKey(), Integer.parseInt(str.substring(indexOf + 1, str.length())));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public String getErrorMessage() {
        return this.errorMsg;
    }

    public String getHttpReqKey() {
        return this.httpReqKey;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getResultMessage() {
        return this.resultMsg;
    }

    public void onError(APBaseHttpReq aPBaseHttpReq, int i, String str) {
        APLog.d("APBaseHttpAns", "onError");
        this.errorMsg = str;
        this.resultMsg = str;
        this.resultCode = i;
        this.httpClient = aPBaseHttpReq;
        c();
        onErrorAns(this.httpClient);
        a();
    }

    public void onErrorAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onFinish(APBaseHttpReq aPBaseHttpReq) {
        APLog.d("APBaseHttpAns", "onFinish");
        c();
        if (aPBaseHttpReq.getContent() == null) {
            this.resultCode = -1;
            StringBuilder sb = new StringBuilder();
            APMidasPayAPI.singleton();
            this.resultMsg = sb.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append(APErrorCode.getErrorCode(1004)).toString();
            a();
            return;
        }
        this.httpClient = aPBaseHttpReq;
        if (APAppDataInterface.singleton().isNewCGI()) {
            b(aPBaseHttpReq.getContent(), aPBaseHttpReq);
            return;
        }
        a(aPBaseHttpReq.getContent(), aPBaseHttpReq);
        onFinishAns(aPBaseHttpReq.getContent(), aPBaseHttpReq);
        a(aPBaseHttpReq.getContent());
    }

    public void onFinishAns(byte[] bArr, APBaseHttpReq aPBaseHttpReq) {
    }

    public void onReceive(byte[] bArr, int i, long j, APBaseHttpReq aPBaseHttpReq) {
        onReceiveAns(bArr, i, j, aPBaseHttpReq);
    }

    public void onReceiveAns(byte[] bArr, int i, long j, APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStart(APBaseHttpReq aPBaseHttpReq) {
        APLog.d("APBaseHttpAns", "onStart");
        a(aPBaseHttpReq);
        onStartAns(aPBaseHttpReq);
    }

    public void onStartAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void onStop(APBaseHttpReq aPBaseHttpReq) {
        APLog.d("APBaseHttpAns", "onStop");
        this.httpClient = aPBaseHttpReq;
        c();
        onStopAns(aPBaseHttpReq);
        b();
    }

    public void onStopAns(APBaseHttpReq aPBaseHttpReq) {
    }

    public void reRegister() {
        this.a.register(this.httpReqKey, this.c);
    }

    public void requestAgain() {
        if (this.httpClient == null || this.d > 1) {
            reRegister();
            onError(this.httpClient, -1, "");
            return;
        }
        this.d++;
        reRegister();
        new Thread(new a(this)).start();
    }
}
