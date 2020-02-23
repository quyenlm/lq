package com.tencent.tp;

public class s {
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0077 A[SYNTHETIC, Splitter:B:35:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x007c A[Catch:{ Throwable -> 0x00c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00ae A[SYNTHETIC, Splitter:B:59:0x00ae] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00b3 A[Catch:{ Throwable -> 0x00ba }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized byte[] a(java.lang.String r9) {
        /*
            r3 = 0
            java.lang.Class<com.tencent.tp.s> r5 = com.tencent.tp.s.class
            monitor-enter(r5)
            r4 = 0
            r6 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            r1.<init>(r9)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            java.net.URLConnection r1 = r1.openConnection()     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            if (r1 != 0) goto L_0x0020
            if (r3 == 0) goto L_0x0018
            r4.close()     // Catch:{ Throwable -> 0x00cb }
        L_0x0018:
            if (r3 == 0) goto L_0x001d
            r6.close()     // Catch:{ Throwable -> 0x00cb }
        L_0x001d:
            r1 = r3
        L_0x001e:
            monitor-exit(r5)
            return r1
        L_0x0020:
            r2 = 10000(0x2710, float:1.4013E-41)
            java.lang.String r7 = ".dat"
            boolean r7 = r9.endsWith(r7)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            if (r7 != 0) goto L_0x0032
            java.lang.String r7 = ".zip"
            boolean r7 = r9.endsWith(r7)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            if (r7 == 0) goto L_0x0034
        L_0x0032:
            r2 = 30000(0x7530, float:4.2039E-41)
        L_0x0034:
            java.lang.String r7 = "GET"
            r1.setRequestMethod(r7)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            r7 = 5000(0x1388, float:7.006E-42)
            r1.setConnectTimeout(r7)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            r1.setReadTimeout(r2)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            r2 = 1
            r1.setDoInput(r2)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            r2 = 0
            r1.setDoOutput(r2)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            r2 = 0
            r1.setUseCaches(r2)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            int r2 = r1.getResponseCode()     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            r7 = 200(0xc8, float:2.8E-43)
            if (r2 != r7) goto L_0x0099
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            java.io.InputStream r2 = r1.getInputStream()     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            r4.<init>(r2)     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x00c7, all -> 0x00bc }
            r2.<init>()     // Catch:{ Throwable -> 0x00c7, all -> 0x00bc }
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]     // Catch:{ Throwable -> 0x0073, all -> 0x00bf }
        L_0x0067:
            int r7 = r4.read(r6)     // Catch:{ Throwable -> 0x0073, all -> 0x00bf }
            r8 = -1
            if (r7 == r8) goto L_0x0081
            r8 = 0
            r2.write(r6, r8, r7)     // Catch:{ Throwable -> 0x0073, all -> 0x00bf }
            goto L_0x0067
        L_0x0073:
            r1 = move-exception
            r1 = r4
        L_0x0075:
            if (r1 == 0) goto L_0x007a
            r1.close()     // Catch:{ Throwable -> 0x00c1 }
        L_0x007a:
            if (r2 == 0) goto L_0x007f
            r2.close()     // Catch:{ Throwable -> 0x00c1 }
        L_0x007f:
            r1 = r3
            goto L_0x001e
        L_0x0081:
            r1.disconnect()     // Catch:{ Throwable -> 0x0073, all -> 0x00bf }
            r0 = r2
            java.io.ByteArrayOutputStream r0 = (java.io.ByteArrayOutputStream) r0     // Catch:{ Throwable -> 0x0073, all -> 0x00bf }
            r1 = r0
            byte[] r1 = r1.toByteArray()     // Catch:{ Throwable -> 0x0073, all -> 0x00bf }
            if (r4 == 0) goto L_0x0091
            r4.close()     // Catch:{ Throwable -> 0x0097 }
        L_0x0091:
            if (r2 == 0) goto L_0x001e
            r2.close()     // Catch:{ Throwable -> 0x0097 }
            goto L_0x001e
        L_0x0097:
            r2 = move-exception
            goto L_0x001e
        L_0x0099:
            r1.disconnect()     // Catch:{ Throwable -> 0x00c3, all -> 0x00a9 }
            if (r3 == 0) goto L_0x00a1
            r4.close()     // Catch:{ Throwable -> 0x00a7 }
        L_0x00a1:
            if (r3 == 0) goto L_0x007f
            r6.close()     // Catch:{ Throwable -> 0x00a7 }
            goto L_0x007f
        L_0x00a7:
            r1 = move-exception
            goto L_0x007f
        L_0x00a9:
            r1 = move-exception
            r2 = r3
            r4 = r3
        L_0x00ac:
            if (r4 == 0) goto L_0x00b1
            r4.close()     // Catch:{ Throwable -> 0x00ba }
        L_0x00b1:
            if (r2 == 0) goto L_0x00b6
            r2.close()     // Catch:{ Throwable -> 0x00ba }
        L_0x00b6:
            throw r1     // Catch:{ all -> 0x00b7 }
        L_0x00b7:
            r1 = move-exception
            monitor-exit(r5)
            throw r1
        L_0x00ba:
            r2 = move-exception
            goto L_0x00b6
        L_0x00bc:
            r1 = move-exception
            r2 = r3
            goto L_0x00ac
        L_0x00bf:
            r1 = move-exception
            goto L_0x00ac
        L_0x00c1:
            r1 = move-exception
            goto L_0x007f
        L_0x00c3:
            r1 = move-exception
            r2 = r3
            r1 = r3
            goto L_0x0075
        L_0x00c7:
            r1 = move-exception
            r2 = r3
            r1 = r4
            goto L_0x0075
        L_0x00cb:
            r1 = move-exception
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tp.s.a(java.lang.String):byte[]");
    }
}
