package com.tencent.msdk.stat;

import android.content.Context;

final class aa implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ StatSpecifyReportedInfo c;

    aa(String str, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = str;
        this.b = context;
        this.c = statSpecifyReportedInfo;
    }

    public void run() {
        if (this.a == null || this.a.trim().length() == 0) {
            StatServiceImpl.q.w("qq num is null or empty.");
        } else {
            StatServiceImpl.b(this.b, new f(this.a), this.c);
        }
    }
}
