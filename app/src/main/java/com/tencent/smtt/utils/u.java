package com.tencent.smtt.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

public class u {
    private static u a = null;
    private static Context b = null;

    private u() {
    }

    public static u a(Context context) {
        b = context.getApplicationContext();
        if (a == null) {
            a = new u();
        }
        return a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0041 A[SYNTHETIC, Splitter:B:19:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004a A[SYNTHETIC, Splitter:B:24:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.lang.String r5) {
        /*
            r4 = this;
            boolean r0 = com.tencent.smtt.utils.k.b()
            if (r0 != 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            r1 = 0
            java.io.File r0 = com.tencent.smtt.utils.k.c()     // Catch:{ Exception -> 0x003b }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x003b }
            java.lang.String r3 = "ins.dat"
            r2.<init>(r0, r3)     // Catch:{ Exception -> 0x003b }
            boolean r0 = r2.exists()     // Catch:{ Exception -> 0x003b }
            if (r0 != 0) goto L_0x001c
            r2.createNewFile()     // Catch:{ Exception -> 0x003b }
        L_0x001c:
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x003b }
            java.io.FileOutputStream r2 = com.tencent.smtt.utils.k.d((java.io.File) r2)     // Catch:{ Exception -> 0x003b }
            r0.<init>(r2)     // Catch:{ Exception -> 0x003b }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x003b }
            r2.<init>(r0)     // Catch:{ Exception -> 0x003b }
            r2.writeUTF(r5)     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
            r2.flush()     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
            if (r2 == 0) goto L_0x0006
            r2.close()     // Catch:{ IOException -> 0x0036 }
            goto L_0x0006
        L_0x0036:
            r0 = move-exception
        L_0x0037:
            r0.printStackTrace()
            goto L_0x0006
        L_0x003b:
            r0 = move-exception
        L_0x003c:
            r0.printStackTrace()     // Catch:{ all -> 0x0047 }
            if (r1 == 0) goto L_0x0006
            r1.close()     // Catch:{ IOException -> 0x0045 }
            goto L_0x0006
        L_0x0045:
            r0 = move-exception
            goto L_0x0037
        L_0x0047:
            r0 = move-exception
        L_0x0048:
            if (r1 == 0) goto L_0x004d
            r1.close()     // Catch:{ IOException -> 0x004e }
        L_0x004d:
            throw r0
        L_0x004e:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x004d
        L_0x0053:
            r0 = move-exception
            r1 = r2
            goto L_0x0048
        L_0x0056:
            r0 = move-exception
            r1 = r2
            goto L_0x003c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.u.b(java.lang.String):void");
    }

    private void c(String str) {
        try {
            Settings.System.putString(b.getContentResolver(), "sys_setting_tbs_qb_installer", str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            b(str);
            c(str);
        }
    }
}
