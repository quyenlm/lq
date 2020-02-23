package com.tencent.gsdk.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.tencent.gsdk.api.GSDKSystem;
import java.lang.reflect.Method;

/* compiled from: NetworkUtils */
public class j {
    public static int a(Context context) {
        if (context == null) {
            return -1;
        }
        if (a() == 4) {
            String a = a(((WifiManager) context.getSystemService("wifi")).getDhcpInfo().gateway);
            int a2 = a(a);
            Logger.d("ping gateway, ip: " + a + ", delay: " + a2);
            return a2;
        }
        Logger.d("ping gateway fail, not wifi");
        return -1;
    }

    public static int a() {
        return c.c(GSDKSystem.getInstance().a());
    }

    private static String a(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x012c A[SYNTHETIC, Splitter:B:42:0x012c] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0131 A[SYNTHETIC, Splitter:B:45:0x0131] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0174 A[SYNTHETIC, Splitter:B:54:0x0174] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0179 A[SYNTHETIC, Splitter:B:57:0x0179] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(java.lang.String r8) {
        /*
            r0 = -1
            r3 = 0
            r1 = 1000(0x3e8, float:1.401E-42)
            r2 = 0
            r4 = 0
            java.lang.Runtime r5 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x010d, all -> 0x016f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010d, all -> 0x016f }
            r6.<init>()     // Catch:{ Exception -> 0x010d, all -> 0x016f }
            java.lang.String r7 = "/system/bin/ping -c 1 -w 1 "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x010d, all -> 0x016f }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Exception -> 0x010d, all -> 0x016f }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x010d, all -> 0x016f }
            java.lang.Process r5 = r5.exec(r6)     // Catch:{ Exception -> 0x010d, all -> 0x016f }
            int r6 = r5.waitFor()     // Catch:{ Exception -> 0x010d, all -> 0x016f }
            if (r6 == 0) goto L_0x006a
            if (r3 == 0) goto L_0x002c
            r2.close()     // Catch:{ Exception -> 0x0032 }
        L_0x002c:
            if (r3 == 0) goto L_0x0031
            r4.close()     // Catch:{ Exception -> 0x004e }
        L_0x0031:
            return r0
        L_0x0032:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "ping insteam,e:"
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.d(r1)
            goto L_0x002c
        L_0x004e:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "bufferReader,e:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.d(r1)
            goto L_0x0031
        L_0x006a:
            java.io.InputStream r4 = r5.getInputStream()     // Catch:{ Exception -> 0x010d, all -> 0x016f }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
            java.lang.String r6 = "GBK"
            r5.<init>(r4, r6)     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
            r2.<init>(r5)     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
        L_0x007a:
            java.lang.String r5 = r2.readLine()     // Catch:{ Exception -> 0x01be }
            if (r5 == 0) goto L_0x0082
            r3 = r5
            goto L_0x007a
        L_0x0082:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01be }
            r5.<init>()     // Catch:{ Exception -> 0x01be }
            java.lang.String r6 = "ping gateway res: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x01be }
            java.lang.StringBuilder r5 = r5.append(r3)     // Catch:{ Exception -> 0x01be }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x01be }
            com.tencent.gsdk.utils.Logger.d(r5)     // Catch:{ Exception -> 0x01be }
            if (r3 == 0) goto L_0x01c1
            java.lang.String r1 = "="
            int r1 = r3.indexOf(r1)     // Catch:{ Exception -> 0x01be }
            int r1 = r1 + 1
            java.lang.String r5 = "ms"
            int r5 = r3.indexOf(r5)     // Catch:{ Exception -> 0x01be }
            java.lang.String r1 = r3.substring(r1, r5)     // Catch:{ Exception -> 0x01be }
            java.lang.String r1 = r1.trim()     // Catch:{ Exception -> 0x01be }
            r3 = 0
            java.lang.String r5 = "/"
            int r5 = r1.indexOf(r5)     // Catch:{ Exception -> 0x01be }
            java.lang.String r1 = r1.substring(r3, r5)     // Catch:{ Exception -> 0x01be }
            java.lang.String r1 = r1.trim()     // Catch:{ Exception -> 0x01be }
            java.lang.Float r1 = java.lang.Float.valueOf(r1)     // Catch:{ Exception -> 0x01be }
            float r0 = r1.floatValue()     // Catch:{ Exception -> 0x01be }
            int r0 = (int) r0
        L_0x00c8:
            if (r4 == 0) goto L_0x00cd
            r4.close()     // Catch:{ Exception -> 0x00f1 }
        L_0x00cd:
            if (r2 == 0) goto L_0x0031
            r2.close()     // Catch:{ Exception -> 0x00d4 }
            goto L_0x0031
        L_0x00d4:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "bufferReader,e:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.d(r1)
            goto L_0x0031
        L_0x00f1:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "ping insteam,e:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.d(r1)
            goto L_0x00cd
        L_0x010d:
            r1 = move-exception
            r2 = r3
            r4 = r3
        L_0x0110:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01b8 }
            r3.<init>()     // Catch:{ all -> 0x01b8 }
            java.lang.String r5 = "getPingTime error:"
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x01b8 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x01b8 }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ all -> 0x01b8 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01b8 }
            com.tencent.gsdk.utils.Logger.w(r1)     // Catch:{ all -> 0x01b8 }
            if (r4 == 0) goto L_0x012f
            r4.close()     // Catch:{ Exception -> 0x0153 }
        L_0x012f:
            if (r2 == 0) goto L_0x0031
            r2.close()     // Catch:{ Exception -> 0x0136 }
            goto L_0x0031
        L_0x0136:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "bufferReader,e:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.d(r1)
            goto L_0x0031
        L_0x0153:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "ping insteam,e:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.d(r1)
            goto L_0x012f
        L_0x016f:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x0172:
            if (r4 == 0) goto L_0x0177
            r4.close()     // Catch:{ Exception -> 0x017d }
        L_0x0177:
            if (r2 == 0) goto L_0x017c
            r2.close()     // Catch:{ Exception -> 0x0199 }
        L_0x017c:
            throw r0
        L_0x017d:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "ping insteam,e:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.d(r1)
            goto L_0x0177
        L_0x0199:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "bufferReader,e:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.d(r1)
            goto L_0x017c
        L_0x01b5:
            r0 = move-exception
            r2 = r3
            goto L_0x0172
        L_0x01b8:
            r0 = move-exception
            goto L_0x0172
        L_0x01ba:
            r1 = move-exception
            r2 = r3
            goto L_0x0110
        L_0x01be:
            r1 = move-exception
            goto L_0x0110
        L_0x01c1:
            r0 = r1
            goto L_0x00c8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.gsdk.utils.j.a(java.lang.String):int");
    }

    public static String b() {
        StringBuilder sb = new StringBuilder();
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
            String[] strArr = {"net.dns1", "net.dns2", "net.dns3", "net.dns4"};
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String str = (String) method.invoke((Object) null, new Object[]{strArr[i]});
                if (!TextUtils.isEmpty(str)) {
                    sb.append(str).append(",");
                }
            }
            sb.setLength(sb.length() - 1);
        } catch (Throwable th) {
        }
        return sb.toString();
    }
}
