package com.tencent.component.utils;

import android.text.TextUtils;
import java.lang.reflect.Method;

public class PropertyUtils {
    private static final String CMD_GET_PROP = "getprop";
    public static final String PROPERTY_DNS_PRIMARY = "net.dns1";
    public static final String PROPERTY_DNS_SECNDARY = "net.dns2";
    private static Class sClassSystemProperties;
    private static Method sMethodGetString;

    static {
        try {
            sClassSystemProperties = Class.forName("android.os.SystemProperties");
            sMethodGetString = sClassSystemProperties.getDeclaredMethod("get", new Class[]{String.class, String.class});
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static String get(String key, String defValue) {
        if (TextUtils.isEmpty(key)) {
            return defValue;
        }
        String value = getWithReflect(key, (String) null);
        if (TextUtils.isEmpty(value)) {
            value = getWithCmd(key, (String) null);
        }
        if (TextUtils.isEmpty(value)) {
            value = defValue;
        }
        return value;
    }

    public static String getQuickly(String key, String defValue) {
        return TextUtils.isEmpty(key) ? defValue : getWithReflect(key, defValue);
    }

    private static String getWithReflect(String key, String defValue) {
        if (sClassSystemProperties == null || sMethodGetString == null) {
            return defValue;
        }
        String str = defValue;
        try {
            return (String) sMethodGetString.invoke(sClassSystemProperties, new Object[]{key, defValue});
        } catch (Throwable e) {
            e.printStackTrace();
            return str;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x005a A[Catch:{ IOException -> 0x0066, all -> 0x0057, Throwable -> 0x005e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getWithCmd(java.lang.String r11, java.lang.String r12) {
        /*
            r7 = r12
            java.lang.Runtime r8 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x005e }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x005e }
            r9.<init>()     // Catch:{ Throwable -> 0x005e }
            java.lang.String r10 = "getprop "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x005e }
            java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ Throwable -> 0x005e }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x005e }
            java.lang.Process r3 = r8.exec(r9)     // Catch:{ Throwable -> 0x005e }
            r5 = 0
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0066, all -> 0x0057 }
            java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0066, all -> 0x0057 }
            java.io.InputStream r9 = r3.getInputStream()     // Catch:{ IOException -> 0x0066, all -> 0x0057 }
            r8.<init>(r9)     // Catch:{ IOException -> 0x0066, all -> 0x0057 }
            r6.<init>(r8)     // Catch:{ IOException -> 0x0066, all -> 0x0057 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x003a, all -> 0x0063 }
            r0.<init>()     // Catch:{ IOException -> 0x003a, all -> 0x0063 }
        L_0x0030:
            java.lang.String r2 = r6.readLine()     // Catch:{ IOException -> 0x003a, all -> 0x0063 }
            if (r2 == 0) goto L_0x0045
            r0.append(r2)     // Catch:{ IOException -> 0x003a, all -> 0x0063 }
            goto L_0x0030
        L_0x003a:
            r8 = move-exception
            r5 = r6
        L_0x003c:
            if (r5 == 0) goto L_0x0041
            r5.close()     // Catch:{ Throwable -> 0x005e }
        L_0x0041:
            r3.destroy()     // Catch:{ Throwable -> 0x005e }
        L_0x0044:
            return r7
        L_0x0045:
            java.lang.String r4 = r0.toString()     // Catch:{ IOException -> 0x003a, all -> 0x0063 }
            boolean r8 = android.text.TextUtils.isEmpty(r4)     // Catch:{ IOException -> 0x003a, all -> 0x0063 }
            if (r8 != 0) goto L_0x0050
            r7 = r4
        L_0x0050:
            if (r6 == 0) goto L_0x0068
            r6.close()     // Catch:{ Throwable -> 0x005e }
            r5 = r6
            goto L_0x0041
        L_0x0057:
            r8 = move-exception
        L_0x0058:
            if (r5 == 0) goto L_0x005d
            r5.close()     // Catch:{ Throwable -> 0x005e }
        L_0x005d:
            throw r8     // Catch:{ Throwable -> 0x005e }
        L_0x005e:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0044
        L_0x0063:
            r8 = move-exception
            r5 = r6
            goto L_0x0058
        L_0x0066:
            r8 = move-exception
            goto L_0x003c
        L_0x0068:
            r5 = r6
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.PropertyUtils.getWithCmd(java.lang.String, java.lang.String):java.lang.String");
    }
}
