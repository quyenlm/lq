package com.tencent.smtt.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.smtt.sdk.TbsLogReport;

class aw extends Handler {
    final /* synthetic */ TbsLogReport a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    aw(TbsLogReport tbsLogReport, Looper looper) {
        super(looper);
        this.a = tbsLogReport;
    }

    public void handleMessage(Message message) {
        if (message.what == 600) {
            if (message.obj instanceof TbsLogReport.TbsLogInfo) {
                int i = message.arg1;
                this.a.a(i, (TbsLogReport.TbsLogInfo) message.obj);
            }
        } else if (message.what == 601) {
            this.a.g();
        }
    }
}
