package com.tencent.smtt.sdk;

public class a {
    public static int a = 600;
    private static int b = 0;

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0072 A[SYNTHETIC, Splitter:B:29:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0082 A[SYNTHETIC, Splitter:B:38:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x008f A[SYNTHETIC, Splitter:B:45:0x008f] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:26:0x006d=Splitter:B:26:0x006d, B:35:0x007d=Splitter:B:35:0x007d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a() {
        /*
            r0 = 0
            int r1 = b
            if (r1 <= 0) goto L_0x0008
            int r0 = b
        L_0x0007:
            return r0
        L_0x0008:
            java.lang.String r1 = "/proc/meminfo"
            java.lang.String r2 = ""
            r3 = 0
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ IOException -> 0x006b, Throwable -> 0x007b, all -> 0x008b }
            r4.<init>(r1)     // Catch:{ IOException -> 0x006b, Throwable -> 0x007b, all -> 0x008b }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x006b, Throwable -> 0x007b, all -> 0x008b }
            r1 = 8192(0x2000, float:1.14794E-41)
            r2.<init>(r4, r1)     // Catch:{ IOException -> 0x006b, Throwable -> 0x007b, all -> 0x008b }
        L_0x0019:
            java.lang.String r1 = r2.readLine()     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            if (r1 == 0) goto L_0x005c
            java.lang.String r3 = "MemTotal:"
            int r3 = r1.indexOf(r3)     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            r4 = -1
            if (r4 == r3) goto L_0x0019
            java.lang.String r4 = "MemTotal:"
            int r4 = r4.length()     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            int r3 = r3 + r4
            java.lang.String r1 = r1.substring(r3)     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            java.lang.String r1 = r1.trim()     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            if (r1 == 0) goto L_0x005c
            int r3 = r1.length()     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            if (r3 == 0) goto L_0x005c
            java.lang.String r3 = "k"
            boolean r3 = r1.contains(r3)     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            if (r3 == 0) goto L_0x005c
            r3 = 0
            java.lang.String r4 = "k"
            int r4 = r1.indexOf(r4)     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            java.lang.String r1 = r1.substring(r3, r4)     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            java.lang.String r1 = r1.trim()     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ IOException -> 0x009c, Throwable -> 0x009a }
            int r0 = r1 / 1024
        L_0x005c:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0061:
            b = r0
            int r0 = b
            goto L_0x0007
        L_0x0066:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0061
        L_0x006b:
            r1 = move-exception
            r2 = r3
        L_0x006d:
            r1.printStackTrace()     // Catch:{ all -> 0x0098 }
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x0076 }
            goto L_0x0061
        L_0x0076:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0061
        L_0x007b:
            r1 = move-exception
            r2 = r3
        L_0x007d:
            r1.printStackTrace()     // Catch:{ all -> 0x0098 }
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x0086 }
            goto L_0x0061
        L_0x0086:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0061
        L_0x008b:
            r0 = move-exception
            r2 = r3
        L_0x008d:
            if (r2 == 0) goto L_0x0092
            r2.close()     // Catch:{ IOException -> 0x0093 }
        L_0x0092:
            throw r0
        L_0x0093:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0092
        L_0x0098:
            r0 = move-exception
            goto L_0x008d
        L_0x009a:
            r1 = move-exception
            goto L_0x007d
        L_0x009c:
            r1 = move-exception
            goto L_0x006d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.a.a():int");
    }
}
