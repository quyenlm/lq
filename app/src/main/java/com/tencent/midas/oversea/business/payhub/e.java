package com.tencent.midas.oversea.business.payhub;

import android.os.Message;
import com.tencent.midas.oversea.TestConfig;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;

class e implements IAPHttpAnsObserver {
    final /* synthetic */ APBasePayChannel a;

    e(APBasePayChannel aPBasePayChannel) {
        this.a = aPBasePayChannel;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
        Message obtainMessage = this.a.UIHandler.obtainMessage();
        obtainMessage.obj = aPBaseHttpAns.getResultMessage();
        obtainMessage.arg1 = aPBaseHttpAns.getResultCode();
        obtainMessage.what = 50;
        this.a.UIHandler.sendMessage(obtainMessage);
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        int resultCode = aPBaseHttpAns.getResultCode();
        String resultMessage = aPBaseHttpAns.getResultMessage();
        if (TestConfig.retCodeReProvide != 0) {
            resultCode = TestConfig.retCodeReProvide;
            resultMessage = "test error msg:" + resultCode;
            APLog.i("APHttpHandle", "TestConfig");
        }
        switch (resultCode) {
            case 0:
                this.a.UIHandler.sendEmptyMessage(49);
                return;
            case 1018:
                this.a.dispose();
                this.a.b.sendEmptyMessage(1);
                return;
            case APGlobalInfo.RET_SECKEYERROR /*1094*/:
            case APGlobalInfo.RET_SECKEYVALID /*1099*/:
                if (aPBaseHttpAns.getHttpReqKey().equals(APNetworkManager.HTTP_KEY_GET_KEY)) {
                    this.a.dispose();
                    this.a.b.sendEmptyMessage(2);
                    return;
                }
                return;
            default:
                Message obtainMessage = this.a.UIHandler.obtainMessage();
                obtainMessage.obj = resultMessage;
                obtainMessage.arg1 = resultCode;
                obtainMessage.what = 50;
                this.a.UIHandler.sendMessage(obtainMessage);
                return;
        }
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
        this.a.dispose();
        this.a.UIHandler.sendEmptyMessage(30);
    }
}
