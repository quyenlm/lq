package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.utils.TbsLog;

class TbsLinuxToolsJni {
    private static boolean a = false;
    private static boolean b = false;

    public TbsLinuxToolsJni(Context context) {
        a(context);
    }

    private native int ChmodInner(String str, String str2);

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.content.Context r6) {
        /*
            r5 = this;
            java.lang.Class<com.tencent.smtt.sdk.TbsLinuxToolsJni> r1 = com.tencent.smtt.sdk.TbsLinuxToolsJni.class
            monitor-enter(r1)
            boolean r0 = b     // Catch:{ all -> 0x0083 }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r1)     // Catch:{ all -> 0x0083 }
        L_0x0008:
            return
        L_0x0009:
            r0 = 1
            b = r0     // Catch:{ all -> 0x0083 }
            boolean r0 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r6)     // Catch:{ Throwable -> 0x008f }
            if (r0 == 0) goto L_0x0086
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x008f }
            java.lang.String r2 = com.tencent.smtt.sdk.TbsShareManager.c(r6)     // Catch:{ Throwable -> 0x008f }
            r0.<init>(r2)     // Catch:{ Throwable -> 0x008f }
        L_0x001b:
            if (r0 == 0) goto L_0x007a
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x008f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008f }
            r3.<init>()     // Catch:{ Throwable -> 0x008f }
            java.lang.String r4 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x008f }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x008f }
            java.lang.String r4 = java.io.File.separator     // Catch:{ Throwable -> 0x008f }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x008f }
            java.lang.String r4 = "liblinuxtoolsfortbssdk_jni.so"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x008f }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x008f }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x008f }
            if (r2 == 0) goto L_0x0047
            boolean r2 = r2.exists()     // Catch:{ Throwable -> 0x008f }
            if (r2 != 0) goto L_0x0055
        L_0x0047:
            boolean r2 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r6)     // Catch:{ Throwable -> 0x008f }
            if (r2 != 0) goto L_0x0055
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x008f }
            java.io.File r0 = r0.p(r6)     // Catch:{ Throwable -> 0x008f }
        L_0x0055:
            if (r0 == 0) goto L_0x007a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008f }
            r2.<init>()     // Catch:{ Throwable -> 0x008f }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ Throwable -> 0x008f }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Throwable -> 0x008f }
            java.lang.String r2 = java.io.File.separator     // Catch:{ Throwable -> 0x008f }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x008f }
            java.lang.String r2 = "liblinuxtoolsfortbssdk_jni.so"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x008f }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x008f }
            java.lang.System.load(r0)     // Catch:{ Throwable -> 0x008f }
            r0 = 1
            a = r0     // Catch:{ Throwable -> 0x008f }
        L_0x007a:
            java.lang.String r0 = "/checkChmodeExists"
            java.lang.String r2 = "700"
            r5.ChmodInner(r0, r2)     // Catch:{ Throwable -> 0x008f }
        L_0x0081:
            monitor-exit(r1)     // Catch:{ all -> 0x0083 }
            goto L_0x0008
        L_0x0083:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0083 }
            throw r0
        L_0x0086:
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x008f }
            java.io.File r0 = r0.q(r6)     // Catch:{ Throwable -> 0x008f }
            goto L_0x001b
        L_0x008f:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0083 }
            r0 = 0
            a = r0     // Catch:{ all -> 0x0083 }
            goto L_0x0081
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsLinuxToolsJni.a(android.content.Context):void");
    }

    public int a(String str, String str2) {
        if (a) {
            return ChmodInner(str, str2);
        }
        TbsLog.e("TbsLinuxToolsJni", "jni not loaded!", true);
        return -1;
    }
}
