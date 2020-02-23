package com.tencent.midas.oversea.business.payhub;

import com.tencent.midas.oversea.api.IPostProvideCallback;

class i implements IPostProvideCallback {
    final /* synthetic */ APBaseRestore a;

    i(APBaseRestore aPBaseRestore) {
        this.a = aPBaseRestore;
    }

    public void callback(int i, String str) {
        String unused = this.a.h = str;
        this.a.dispose();
    }
}
