package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.sdk.QbSdk;

final class l implements TbsListener {
    final /* synthetic */ Context a;
    final /* synthetic */ QbSdk.PreInitCallback b;

    l(Context context, QbSdk.PreInitCallback preInitCallback) {
        this.a = context;
        this.b = preInitCallback;
    }

    public void onDownloadFinish(int i) {
    }

    public void onDownloadProgress(int i) {
    }

    public void onInstallFinish(int i) {
        QbSdk.preInit(this.a, this.b);
    }
}
