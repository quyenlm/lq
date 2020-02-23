package com.tencent.midas.oversea.network.http;

class b implements IAPHttpAnsObserver {
    final /* synthetic */ boolean a;
    final /* synthetic */ APBaseHttpAns b;

    b(APBaseHttpAns aPBaseHttpAns, boolean z) {
        this.b = aPBaseHttpAns;
        this.a = z;
    }

    public void onError(APBaseHttpAns aPBaseHttpAns) {
    }

    public void onFinish(APBaseHttpAns aPBaseHttpAns) {
        this.b.a(aPBaseHttpAns, this.a);
    }

    public void onStop(APBaseHttpAns aPBaseHttpAns) {
    }
}
