package com.tencent.midas.oversea.business.payhub;

import com.tencent.midas.oversea.api.ICallback;
import com.tencent.midas.oversea.comm.APLog;

class g implements ICallback {
    final /* synthetic */ APBaseRestore a;

    g(APBaseRestore aPBaseRestore) {
        this.a = aPBaseRestore;
    }

    public void callback(int i) {
        if (i == 0) {
            APLog.i(APBaseRestore.a, "init success");
            this.a.b();
            return;
        }
        APLog.i(APBaseRestore.a, "init error");
        this.a.dispose();
    }
}
