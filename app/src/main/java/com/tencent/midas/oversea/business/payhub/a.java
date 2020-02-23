package com.tencent.midas.oversea.business.payhub;

import android.os.Message;
import com.tencent.midas.oversea.comm.APUICommonMethod;

class a implements APUICommonMethod.OnError {
    final /* synthetic */ String a;
    final /* synthetic */ APBasePayChannel b;

    a(APBasePayChannel aPBasePayChannel, String str) {
        this.b = aPBasePayChannel;
        this.a = str;
    }

    public void onError() {
        Message obtainMessage = this.b.b.obtainMessage();
        obtainMessage.arg1 = -1;
        obtainMessage.obj = this.a;
        obtainMessage.what = 17;
        this.b.b.sendMessage(obtainMessage);
    }
}
