package com.tencent.gsdk.utils.c;

/* compiled from: TcpSpeedTester */
final class g extends a {
    g() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002b A[SYNTHETIC, Splitter:B:13:0x002b] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0035 A[SYNTHETIC, Splitter:B:19:0x0035] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public short a(java.net.InetAddress r9, int r10, int r11) {
        /*
            r8 = this;
            r0 = 1000(0x3e8, float:1.401E-42)
            r2 = 0
            java.net.Socket r1 = new java.net.Socket     // Catch:{ Throwable -> 0x0027, all -> 0x0031 }
            r1.<init>()     // Catch:{ Throwable -> 0x0027, all -> 0x0031 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x003f, all -> 0x003d }
            java.net.InetSocketAddress r4 = new java.net.InetSocketAddress     // Catch:{ Throwable -> 0x003f, all -> 0x003d }
            r4.<init>(r9, r10)     // Catch:{ Throwable -> 0x003f, all -> 0x003d }
            r1.connect(r4, r11)     // Catch:{ Throwable -> 0x003f, all -> 0x003d }
            long r4 = (long) r11     // Catch:{ Throwable -> 0x003f, all -> 0x003d }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x003f, all -> 0x003d }
            long r2 = r6 - r2
            long r2 = java.lang.Math.min(r4, r2)     // Catch:{ Throwable -> 0x003f, all -> 0x003d }
            int r0 = (int) r2
            short r0 = (short) r0
            if (r1 == 0) goto L_0x0026
            r1.close()     // Catch:{ IOException -> 0x0039 }
        L_0x0026:
            return r0
        L_0x0027:
            r1 = move-exception
            r1 = r2
        L_0x0029:
            if (r1 == 0) goto L_0x0026
            r1.close()     // Catch:{ IOException -> 0x002f }
            goto L_0x0026
        L_0x002f:
            r1 = move-exception
            goto L_0x0026
        L_0x0031:
            r0 = move-exception
            r1 = r2
        L_0x0033:
            if (r1 == 0) goto L_0x0038
            r1.close()     // Catch:{ IOException -> 0x003b }
        L_0x0038:
            throw r0
        L_0x0039:
            r1 = move-exception
            goto L_0x0026
        L_0x003b:
            r1 = move-exception
            goto L_0x0038
        L_0x003d:
            r0 = move-exception
            goto L_0x0033
        L_0x003f:
            r2 = move-exception
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.gsdk.utils.c.g.a(java.net.InetAddress, int, int):short");
    }
}
