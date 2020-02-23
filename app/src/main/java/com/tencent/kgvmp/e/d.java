package com.tencent.kgvmp.e;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class d {
    private static String a(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                sb.append(readLine).append("\n");
            } else {
                bufferedReader.close();
                return sb.toString();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0055 A[SYNTHETIC, Splitter:B:31:0x0055] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r7) {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File
            r1.<init>(r7)
            boolean r2 = r1.exists()
            if (r2 == 0) goto L_0x0012
            boolean r2 = r1.canRead()
            if (r2 != 0) goto L_0x0013
        L_0x0012:
            return r0
        L_0x0013:
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0061, all -> 0x0051 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0061, all -> 0x0051 }
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0033 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0033 }
            java.lang.String r4 = ""
            r3.<init>(r4)     // Catch:{ Exception -> 0x0033 }
        L_0x0023:
            int r4 = r2.read(r1)     // Catch:{ Exception -> 0x0033 }
            if (r4 <= 0) goto L_0x0042
            java.lang.String r5 = new java.lang.String     // Catch:{ Exception -> 0x0033 }
            r6 = 0
            r5.<init>(r1, r6, r4)     // Catch:{ Exception -> 0x0033 }
            r3.append(r5)     // Catch:{ Exception -> 0x0033 }
            goto L_0x0023
        L_0x0033:
            r1 = move-exception
        L_0x0034:
            r1.printStackTrace()     // Catch:{ all -> 0x005e }
            if (r2 == 0) goto L_0x0012
            r2.close()     // Catch:{ Exception -> 0x003d }
            goto L_0x0012
        L_0x003d:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0012
        L_0x0042:
            java.lang.String r0 = r3.toString()     // Catch:{ Exception -> 0x0033 }
            if (r2 == 0) goto L_0x0012
            r2.close()     // Catch:{ Exception -> 0x004c }
            goto L_0x0012
        L_0x004c:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0012
        L_0x0051:
            r1 = move-exception
            r2 = r0
        L_0x0053:
            if (r2 == 0) goto L_0x0058
            r2.close()     // Catch:{ Exception -> 0x0059 }
        L_0x0058:
            throw r1
        L_0x0059:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0058
        L_0x005e:
            r0 = move-exception
            r1 = r0
            goto L_0x0053
        L_0x0061:
            r1 = move-exception
            r2 = r0
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.kgvmp.e.d.a(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0021 A[SYNTHETIC, Splitter:B:17:0x0021] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x002e A[SYNTHETIC, Splitter:B:24:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r3, java.lang.String r4) {
        /*
            r2 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0019, all -> 0x002a }
            r1.<init>(r3)     // Catch:{ Exception -> 0x0019, all -> 0x002a }
            byte[] r0 = r4.getBytes()     // Catch:{ Exception -> 0x0039 }
            r1.write(r0)     // Catch:{ Exception -> 0x0039 }
            if (r1 == 0) goto L_0x0012
            r1.close()     // Catch:{ Exception -> 0x0014 }
        L_0x0012:
            r0 = 1
        L_0x0013:
            return r0
        L_0x0014:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0012
        L_0x0019:
            r0 = move-exception
            r1 = r2
        L_0x001b:
            r0.printStackTrace()     // Catch:{ all -> 0x0037 }
            r0 = 0
            if (r1 == 0) goto L_0x0013
            r1.close()     // Catch:{ Exception -> 0x0025 }
            goto L_0x0013
        L_0x0025:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0013
        L_0x002a:
            r0 = move-exception
            r1 = r2
        L_0x002c:
            if (r1 == 0) goto L_0x0031
            r1.close()     // Catch:{ Exception -> 0x0032 }
        L_0x0031:
            throw r0
        L_0x0032:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0031
        L_0x0037:
            r0 = move-exception
            goto L_0x002c
        L_0x0039:
            r0 = move-exception
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.kgvmp.e.d.a(java.lang.String, java.lang.String):boolean");
    }

    public static String b(String str) {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        String str2 = "";
        try {
            str2 = a((InputStream) fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileInputStream.close();
        return str2;
    }

    public static boolean c(String str) {
        if (str == null) {
            return false;
        }
        return new File(str).exists();
    }
}
