package com.tencent.smtt.utils;

import android.os.Handler;
import android.os.Looper;

class t extends Handler {
    final /* synthetic */ s a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    t(s sVar, Looper looper) {
        super(looper);
        this.a = sVar;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleMessage(android.os.Message r5) {
        /*
            r4 = this;
            int r0 = r5.what     // Catch:{ Exception -> 0x001d }
            switch(r0) {
                case 0: goto L_0x0009;
                case 1: goto L_0x0022;
                case 2: goto L_0x0033;
                default: goto L_0x0005;
            }
        L_0x0005:
            super.handleMessage(r5)
            return
        L_0x0009:
            java.lang.Object r0 = r5.obj     // Catch:{ Exception -> 0x001d }
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ Exception -> 0x001d }
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ Exception -> 0x001d }
            r1 = 0
            r1 = r0[r1]     // Catch:{ Exception -> 0x001d }
            android.content.Context r1 = (android.content.Context) r1     // Catch:{ Exception -> 0x001d }
            r2 = 1
            r0 = r0[r2]     // Catch:{ Exception -> 0x001d }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x001d }
            com.tencent.smtt.utils.s.b(r1, r0)     // Catch:{ Exception -> 0x001d }
            goto L_0x0005
        L_0x001d:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0005
        L_0x0022:
            java.lang.Object r0 = r5.obj     // Catch:{ Exception -> 0x001d }
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ Exception -> 0x001d }
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ Exception -> 0x001d }
            com.tencent.smtt.utils.s r1 = r4.a     // Catch:{ Exception -> 0x001d }
            r2 = 0
            r0 = r0[r2]     // Catch:{ Exception -> 0x001d }
            android.content.Context r0 = (android.content.Context) r0     // Catch:{ Exception -> 0x001d }
            r1.c(r0)     // Catch:{ Exception -> 0x001d }
            goto L_0x0005
        L_0x0033:
            java.lang.Object r0 = r5.obj     // Catch:{ Exception -> 0x001d }
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ Exception -> 0x001d }
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ Exception -> 0x001d }
            com.tencent.smtt.utils.s r2 = r4.a     // Catch:{ Exception -> 0x001d }
            r1 = 0
            r1 = r0[r1]     // Catch:{ Exception -> 0x001d }
            android.content.Context r1 = (android.content.Context) r1     // Catch:{ Exception -> 0x001d }
            r3 = 1
            r0 = r0[r3]     // Catch:{ Exception -> 0x001d }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x001d }
            r2.e(r1, r0)     // Catch:{ Exception -> 0x001d }
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.t.handleMessage(android.os.Message):void");
    }
}
