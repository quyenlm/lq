package com.tencent.midas.oversea.safe;

import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;

class a implements IAPHttpAnsObserver {
    final /* synthetic */ APGetKeyManager a;

    a(APGetKeyManager aPGetKeyManager) {
        this.a = aPGetKeyManager;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
        if (this.a.b != null) {
            this.a.b.onGetKeyFail(aPBaseHttpAns.getResultCode(), aPBaseHttpAns.getErrorMessage());
        }
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        this.a.a(aPBaseHttpAns);
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
        if (this.a.b != null) {
            this.a.b.onGetKeyCancel();
        }
    }
}
