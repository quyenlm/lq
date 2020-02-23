package com.tencent.midas.oversea.business.payhub;

import android.os.Message;
import com.tencent.midas.oversea.TestConfig;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;

class d implements IAPHttpAnsObserver {
    final /* synthetic */ APBasePayChannel a;

    d(APBasePayChannel aPBasePayChannel) {
        this.a = aPBasePayChannel;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
        this.a.dispose();
        Message obtainMessage = this.a.UIHandler.obtainMessage();
        obtainMessage.obj = aPBaseHttpAns.getResultMessage();
        obtainMessage.what = 47;
        this.a.UIHandler.sendMessage(obtainMessage);
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        int resultCode = aPBaseHttpAns.getResultCode();
        String resultMessage = aPBaseHttpAns.getResultMessage();
        if (TestConfig.retCodeProvide != 0) {
            resultCode = TestConfig.retCodeProvide;
            resultMessage = "test error msg:" + resultCode;
            APLog.i("APHttpHandle", "TestConfig");
        }
        switch (resultCode) {
            case 0:
                this.a.UIHandler.sendEmptyMessage(46);
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
                this.a.dispose();
                Message obtainMessage = this.a.UIHandler.obtainMessage();
                obtainMessage.obj = resultMessage;
                obtainMessage.what = 47;
                this.a.UIHandler.sendMessage(obtainMessage);
                return;
        }
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
        this.a.UIHandler.sendEmptyMessage(30);
    }
}
