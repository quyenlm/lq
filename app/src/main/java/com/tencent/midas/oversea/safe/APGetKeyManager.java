package com.tencent.midas.oversea.safe;

import android.content.Context;
import android.widget.Toast;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APErrorCode;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.modle.APGetKeyAns;

public class APGetKeyManager {
    private int a = 0;
    /* access modifiers changed from: private */
    public IAPGetKeyCallBack b;
    protected Context context;

    public APGetKeyManager(Context context2) {
    }

    private void a(int i) {
        APNetworkManager.getInstance().getKey(i, this.a, new a(this));
    }

    /* access modifiers changed from: private */
    public void a(APBaseHttpAns aPBaseHttpAns) {
        int resultCode = aPBaseHttpAns.getResultCode();
        APGetKeyAns aPGetKeyAns = (APGetKeyAns) aPBaseHttpAns;
        aPGetKeyAns.getKeyType();
        switch (resultCode) {
            case 0:
                String secretKey = APAppDataInterface.singleton().getSecretKey();
                String cryptoKey = APAppDataInterface.singleton().getCryptoKey();
                if (secretKey.length() <= 0 || cryptoKey.length() <= 0) {
                    APMidasPayAPI.singleton();
                    Context context2 = APMidasPayAPI.applicationContext;
                    StringBuilder sb = new StringBuilder();
                    APMidasPayAPI.singleton();
                    Toast.makeText(context2, sb.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append("\n").append(APErrorCode.getErrorCode(2001)).toString(), 1).show();
                    if (this.b != null) {
                        IAPGetKeyCallBack iAPGetKeyCallBack = this.b;
                        StringBuilder sb2 = new StringBuilder();
                        APMidasPayAPI.singleton();
                        iAPGetKeyCallBack.onGetKeyFail(-1, sb2.append(APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy")).append("\n").append(APErrorCode.getErrorCode(2001)).toString());
                        return;
                    }
                    return;
                } else if (this.b != null) {
                    this.b.onGetKeySucc("");
                    return;
                } else {
                    return;
                }
            case 1018:
                this.b.onLoginError();
                return;
            case APGlobalInfo.RET_SECKEYVALID:
                a(0);
                return;
            default:
                if (this.b != null) {
                    IAPGetKeyCallBack iAPGetKeyCallBack2 = this.b;
                    int resultCode2 = aPGetKeyAns.getResultCode();
                    APMidasPayAPI.singleton();
                    iAPGetKeyCallBack2.onGetKeyFail(resultCode2, APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_pay_busy"));
                    return;
                }
                return;
        }
    }

    public void changeKey(IAPGetKeyCallBack iAPGetKeyCallBack) {
        this.b = iAPGetKeyCallBack;
        if (APAppDataInterface.singleton().getSecretKey().length() <= 0) {
            a(0);
        } else if (APAppDataInterface.singleton().getCryptoKey().equals("")) {
            a(1);
        } else {
            iAPGetKeyCallBack.onGetKeySucc("");
        }
    }

    public void getCryptoKey(IAPGetKeyCallBack iAPGetKeyCallBack) {
        this.b = iAPGetKeyCallBack;
        a(1);
    }

    public void getSecretyKey(IAPGetKeyCallBack iAPGetKeyCallBack) {
        this.b = iAPGetKeyCallBack;
        a(0);
    }

    public void getSecretyKey(IAPGetKeyCallBack iAPGetKeyCallBack, int i) {
        this.a = i;
        getSecretyKey(iAPGetKeyCallBack);
    }
}
