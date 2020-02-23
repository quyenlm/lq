package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.utils.TbsLog;

final class j extends Handler {
    final /* synthetic */ QbSdk.PreInitCallback a;
    final /* synthetic */ Context b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    j(Looper looper, QbSdk.PreInitCallback preInitCallback, Context context) {
        super(looper);
        this.a = preInitCallback;
        this.b = context;
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                bs c = br.a().c();
                if (c != null) {
                    c.a(this.b);
                }
                if (this.a != null) {
                    this.a.onViewInitFinished(true);
                }
                TbsLog.writeLogToDisk();
                return;
            case 2:
                if (this.a != null) {
                    this.a.onViewInitFinished(false);
                }
                TbsLog.writeLogToDisk();
                return;
            case 3:
                if (this.a != null) {
                    this.a.onCoreInitFinished();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
