package com.tencent.mna.base.utils;

/* compiled from: NetErrHelper */
public final class j {
    private static final b a = new b();
    private static final b b = new b();

    public static b a() {
        try {
            b bVar = new b();
            bVar.a(b());
            bVar.a(c());
            return bVar;
        } catch (Exception e) {
            return null;
        }
    }

    public static String a(b bVar) {
        if (bVar == null) {
            return "0:0:0:0_0:0:0:0_0:0";
        }
        try {
            a a2 = bVar.a();
            c b2 = bVar.b();
            return a2.e + ":" + a2.f + ":" + a2.i + ":" + a2.j + "_" + b2.c + ":" + b2.d + ":" + b2.e + ":" + b2.f + "_" + a2.d + ":" + a2.h;
        } catch (Exception e) {
            return "0:0:0:0_0:0:0:0_0:0";
        }
    }

    public static String b(b bVar) {
        if (bVar == null) {
            return "0:0:0:0_0:0:0:0_0:0_0:0:0:0_0:0";
        }
        try {
            a a2 = bVar.a();
            c b2 = bVar.b();
            return a2.e + ":" + a2.f + ":" + a2.i + ":" + a2.j + "_" + b2.c + ":" + b2.d + ":" + b2.e + ":" + b2.f + "_" + a2.d + ":" + a2.h + "_" + a2.m + ":" + a2.n + ":" + a2.q + ":" + a2.r + "_" + a2.c + ":" + a2.g;
        } catch (Exception e) {
            return "0:0:0:0_0:0:0:0_0:0_0:0:0:0_0:0";
        }
    }

    public static b a(b bVar, b bVar2) {
        if (!(bVar == null || bVar2 == null)) {
            try {
                b bVar3 = new b();
                a a2 = bVar.a();
                c b2 = bVar.b();
                a a3 = bVar2.a();
                c b3 = bVar2.b();
                a aVar = new a(a3, a2);
                c cVar = new c(b3, b2);
                bVar3.a(aVar);
                bVar3.a(cVar);
                return bVar3;
            } catch (Exception e) {
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0189 A[SYNTHETIC, Splitter:B:37:0x0189] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0193 A[SYNTHETIC, Splitter:B:43:0x0193] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.mna.base.utils.j.a b() {
        /*
            r5 = 0
            r1 = 1
            com.tencent.mna.base.utils.j$a r7 = new com.tencent.mna.base.utils.j$a
            long r2 = java.lang.System.currentTimeMillis()
            r7.<init>(r2)
            r2 = 0
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x016b, all -> 0x018f }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x016b, all -> 0x018f }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x016b, all -> 0x018f }
            java.lang.String r6 = "/proc/net/dev"
            r4.<init>(r6)     // Catch:{ Exception -> 0x016b, all -> 0x018f }
            java.lang.String r6 = "UTF-8"
            r0.<init>(r4, r6)     // Catch:{ Exception -> 0x016b, all -> 0x018f }
            r4 = 1024(0x400, float:1.435E-42)
            r3.<init>(r0, r4)     // Catch:{ Exception -> 0x016b, all -> 0x018f }
            java.lang.String r6 = r3.readLine()     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r4 = r5
            r2 = r5
            r0 = r5
        L_0x0028:
            if (r6 == 0) goto L_0x0165
            if (r0 == 0) goto L_0x0030
            if (r2 == 0) goto L_0x0030
            if (r4 != 0) goto L_0x0165
        L_0x0030:
            java.lang.String r5 = r6.trim()     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            java.lang.String r6 = "rmnet0:"
            boolean r6 = r5.startsWith(r6)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            if (r6 != 0) goto L_0x0044
            java.lang.String r6 = "rmnet_ipa0:"
            boolean r6 = r5.startsWith(r6)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            if (r6 == 0) goto L_0x00a2
        L_0x0044:
            java.lang.String r6 = "\\s+"
            java.lang.String[] r5 = r5.split(r6)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            int r6 = r5.length     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r8 = 17
            if (r6 < r8) goto L_0x009c
            r0 = 1
            r0 = r5[r0]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.k = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r0 = 2
            r0 = r5[r0]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.l = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r0 = 3
            r0 = r5[r0]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.m = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r0 = 4
            r0 = r5[r0]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.n = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r0 = 9
            r0 = r5[r0]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.o = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r0 = 10
            r0 = r5[r0]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.p = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r0 = 11
            r0 = r5[r0]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.q = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r0 = 12
            r0 = r5[r0]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.r = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r0 = r1
        L_0x009c:
            java.lang.String r5 = r3.readLine()     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r6 = r5
            goto L_0x0028
        L_0x00a2:
            java.lang.String r6 = "wlan0:"
            boolean r6 = r5.startsWith(r6)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            if (r6 == 0) goto L_0x0103
            java.lang.String r6 = "\\s+"
            java.lang.String[] r5 = r5.split(r6)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            int r6 = r5.length     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r8 = 17
            if (r6 < r8) goto L_0x009c
            r2 = 1
            r2 = r5[r2]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.c = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r2 = 2
            r2 = r5[r2]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.d = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r2 = 3
            r2 = r5[r2]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.e = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r2 = 4
            r2 = r5[r2]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.f = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r2 = 9
            r2 = r5[r2]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.g = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r2 = 10
            r2 = r5[r2]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.h = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r2 = 11
            r2 = r5[r2]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.i = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r2 = 12
            r2 = r5[r2]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.j = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r2 = r1
            goto L_0x009c
        L_0x0103:
            java.lang.String r6 = "eth0:"
            boolean r6 = r5.startsWith(r6)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            if (r6 == 0) goto L_0x009c
            java.lang.String r6 = "\\s+"
            java.lang.String[] r5 = r5.split(r6)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            int r6 = r5.length     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r8 = 17
            if (r6 < r8) goto L_0x009c
            r4 = 1
            r4 = r5[r4]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.s = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r4 = 2
            r4 = r5[r4]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.t = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r4 = 3
            r4 = r5[r4]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.u = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r4 = 4
            r4 = r5[r4]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.v = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r4 = 9
            r4 = r5[r4]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.w = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r4 = 10
            r4 = r5[r4]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.x = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r4 = 11
            r4 = r5[r4]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r8 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.y = r8     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r4 = 12
            r4 = r5[r4]     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            long r4 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r7.z = r4     // Catch:{ Exception -> 0x01a0, all -> 0x019b }
            r4 = r1
            goto L_0x009c
        L_0x0165:
            if (r3 == 0) goto L_0x016a
            r3.close()     // Catch:{ Exception -> 0x0197 }
        L_0x016a:
            return r7
        L_0x016b:
            r0 = move-exception
            r1 = r2
        L_0x016d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x019d }
            r2.<init>()     // Catch:{ all -> 0x019d }
            java.lang.String r3 = "getDevNetStat exception:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x019d }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x019d }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x019d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x019d }
            com.tencent.mna.base.utils.h.a((java.lang.String) r0)     // Catch:{ all -> 0x019d }
            if (r1 == 0) goto L_0x016a
            r1.close()     // Catch:{ Exception -> 0x018d }
            goto L_0x016a
        L_0x018d:
            r0 = move-exception
            goto L_0x016a
        L_0x018f:
            r0 = move-exception
            r3 = r2
        L_0x0191:
            if (r3 == 0) goto L_0x0196
            r3.close()     // Catch:{ Exception -> 0x0199 }
        L_0x0196:
            throw r0
        L_0x0197:
            r0 = move-exception
            goto L_0x016a
        L_0x0199:
            r1 = move-exception
            goto L_0x0196
        L_0x019b:
            r0 = move-exception
            goto L_0x0191
        L_0x019d:
            r0 = move-exception
            r3 = r1
            goto L_0x0191
        L_0x01a0:
            r0 = move-exception
            r1 = r3
            goto L_0x016d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.j.b():com.tencent.mna.base.utils.j$a");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0039, code lost:
        r1 = r1.split("\\s+");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0041, code lost:
        if (r1.length >= 7) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r2.g = java.lang.Long.parseLong(r1[1]);
        r2.c = java.lang.Long.parseLong(r1[2]);
        r2.d = java.lang.Long.parseLong(r1[3]);
        r2.h = java.lang.Long.parseLong(r1[4]);
        r2.e = java.lang.Long.parseLong(r1[5]);
        r2.f = java.lang.Long.parseLong(r1[6]);
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a3 A[SYNTHETIC, Splitter:B:33:0x00a3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.mna.base.utils.j.c c() {
        /*
            com.tencent.mna.base.utils.j$c r2 = new com.tencent.mna.base.utils.j$c
            long r0 = java.lang.System.currentTimeMillis()
            r2.<init>(r0)
            r1 = 0
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00af, all -> 0x009e }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00af, all -> 0x009e }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00af, all -> 0x009e }
            java.lang.String r5 = "/proc/net/snmp"
            r4.<init>(r5)     // Catch:{ Exception -> 0x00af, all -> 0x009e }
            java.lang.String r5 = "UTF-8"
            r3.<init>(r4, r5)     // Catch:{ Exception -> 0x00af, all -> 0x009e }
            r4 = 1024(0x400, float:1.435E-42)
            r0.<init>(r3, r4)     // Catch:{ Exception -> 0x00af, all -> 0x009e }
            java.lang.String r1 = r0.readLine()     // Catch:{ Exception -> 0x0080 }
        L_0x0023:
            if (r1 == 0) goto L_0x0043
            java.lang.String r1 = r1.trim()     // Catch:{ Exception -> 0x0080 }
            java.lang.String r3 = "Udp"
            boolean r3 = r1.startsWith(r3)     // Catch:{ Exception -> 0x0080 }
            if (r3 == 0) goto L_0x0099
            java.lang.String r3 = "InDatagrams"
            boolean r3 = r1.contains(r3)     // Catch:{ Exception -> 0x0080 }
            if (r3 != 0) goto L_0x0099
            java.lang.String r3 = "\\s+"
            java.lang.String[] r1 = r1.split(r3)     // Catch:{ Exception -> 0x0080 }
            int r3 = r1.length     // Catch:{ Exception -> 0x0080 }
            r4 = 7
            if (r3 >= r4) goto L_0x0049
        L_0x0043:
            if (r0 == 0) goto L_0x0048
            r0.close()     // Catch:{ Exception -> 0x00a7 }
        L_0x0048:
            return r2
        L_0x0049:
            r3 = 1
            r3 = r1[r3]     // Catch:{ Exception -> 0x0080 }
            long r4 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0080 }
            r2.g = r4     // Catch:{ Exception -> 0x0080 }
            r3 = 2
            r3 = r1[r3]     // Catch:{ Exception -> 0x0080 }
            long r4 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0080 }
            r2.c = r4     // Catch:{ Exception -> 0x0080 }
            r3 = 3
            r3 = r1[r3]     // Catch:{ Exception -> 0x0080 }
            long r4 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0080 }
            r2.d = r4     // Catch:{ Exception -> 0x0080 }
            r3 = 4
            r3 = r1[r3]     // Catch:{ Exception -> 0x0080 }
            long r4 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0080 }
            r2.h = r4     // Catch:{ Exception -> 0x0080 }
            r3 = 5
            r3 = r1[r3]     // Catch:{ Exception -> 0x0080 }
            long r4 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0080 }
            r2.e = r4     // Catch:{ Exception -> 0x0080 }
            r3 = 6
            r1 = r1[r3]     // Catch:{ Exception -> 0x0080 }
            long r4 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0080 }
            r2.f = r4     // Catch:{ Exception -> 0x0080 }
            goto L_0x0043
        L_0x0080:
            r1 = move-exception
        L_0x0081:
            r4 = -2
            r2.c = r4     // Catch:{ all -> 0x00ab }
            r4 = -2
            r2.d = r4     // Catch:{ all -> 0x00ab }
            r4 = -2
            r2.e = r4     // Catch:{ all -> 0x00ab }
            r4 = -2
            r2.f = r4     // Catch:{ all -> 0x00ab }
            if (r0 == 0) goto L_0x0048
            r0.close()     // Catch:{ Exception -> 0x0097 }
            goto L_0x0048
        L_0x0097:
            r0 = move-exception
            goto L_0x0048
        L_0x0099:
            java.lang.String r1 = r0.readLine()     // Catch:{ Exception -> 0x0080 }
            goto L_0x0023
        L_0x009e:
            r0 = move-exception
            r2 = r0
            r3 = r1
        L_0x00a1:
            if (r3 == 0) goto L_0x00a6
            r3.close()     // Catch:{ Exception -> 0x00a9 }
        L_0x00a6:
            throw r2
        L_0x00a7:
            r0 = move-exception
            goto L_0x0048
        L_0x00a9:
            r0 = move-exception
            goto L_0x00a6
        L_0x00ab:
            r1 = move-exception
            r2 = r1
            r3 = r0
            goto L_0x00a1
        L_0x00af:
            r0 = move-exception
            r0 = r1
            goto L_0x0081
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.j.c():com.tencent.mna.base.utils.j$c");
    }

    /* compiled from: NetErrHelper */
    public static class b {
        a a;
        c b;
        boolean c = true;

        public synchronized a a() {
            return this.a;
        }

        public synchronized c b() {
            return this.b;
        }

        public synchronized void a(a aVar) {
            this.a = aVar;
            this.c = false;
        }

        public synchronized void a(c cVar) {
            this.b = cVar;
            this.c = false;
        }
    }

    /* compiled from: NetErrHelper */
    public static class a {
        public boolean a;
        public long b;
        public long c;
        public long d;
        public long e;
        public long f;
        public long g;
        public long h;
        public long i;
        public long j;
        public long k;
        public long l;
        public long m;
        public long n;
        public long o;
        public long p;
        public long q;
        public long r;
        public long s;
        public long t;
        public long u;
        public long v;
        public long w;
        public long x;
        public long y;
        public long z;

        public a(long j2) {
            this.a = false;
            this.c = -1;
            this.d = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            this.h = -1;
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.m = -1;
            this.n = -1;
            this.o = -1;
            this.p = -1;
            this.q = -1;
            this.r = -1;
            this.s = -1;
            this.t = -1;
            this.u = -1;
            this.v = -1;
            this.w = -1;
            this.x = -1;
            this.y = -1;
            this.z = -1;
            this.a = false;
            this.b = j2;
        }

        public a(a aVar, a aVar2) {
            this.a = false;
            this.c = -1;
            this.d = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            this.h = -1;
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.m = -1;
            this.n = -1;
            this.o = -1;
            this.p = -1;
            this.q = -1;
            this.r = -1;
            this.s = -1;
            this.t = -1;
            this.u = -1;
            this.v = -1;
            this.w = -1;
            this.x = -1;
            this.y = -1;
            this.z = -1;
            this.a = true;
            this.b = aVar.b - aVar2.b;
            this.c = aVar.c - aVar2.c;
            this.d = aVar.d - aVar2.d;
            this.e = aVar.e - aVar2.e;
            this.f = aVar.f - aVar2.f;
            this.g = aVar.g - aVar2.g;
            this.h = aVar.h - aVar2.h;
            this.i = aVar.i - aVar2.i;
            this.j = aVar.j - aVar2.j;
            this.k = aVar.k - aVar2.k;
            this.l = aVar.l - aVar2.l;
            this.m = aVar.m - aVar2.m;
            this.n = aVar.n - aVar2.n;
            this.o = aVar.o - aVar2.o;
            this.p = aVar.p - aVar2.p;
            this.q = aVar.q - aVar2.q;
            this.r = aVar.r - aVar2.r;
            this.s = aVar.s - aVar2.s;
            this.t = aVar.t - aVar2.t;
            this.u = aVar.u - aVar2.u;
            this.v = aVar.v - aVar2.v;
            this.w = aVar.w - aVar2.w;
            this.x = aVar.x - aVar2.x;
            this.y = aVar.y - aVar2.y;
            this.z = aVar.z - aVar2.z;
        }

        public String toString() {
            return "DevNetStat{isDif=" + this.a + ", timestamp=" + this.b + ", wlan_rcv_bytes=" + this.c + ", wlan_rcv_packets=" + this.d + ", wlan_rcv_errs=" + this.e + ", wlan_rcv_drops=" + this.f + ", wlan_snd_bytes=" + this.g + ", wlan_snd_packets=" + this.h + ", wlan_snd_errs=" + this.i + ", wlan_snd_drops=" + this.j + ", rmnet_rcv_bytes=" + this.k + ", rmnet_rcv_packets=" + this.l + ", rmnet_rcv_errs=" + this.m + ", rmnet_rcv_drops=" + this.n + ", rmnet_snd_bytes=" + this.o + ", rmnet_snd_packets=" + this.p + ", rmnet_snd_errs=" + this.q + ", rmnet_snd_drops=" + this.r + ", eth_rcv_bytes=" + this.s + ", eth_rcv_packets=" + this.t + ", eth_rcv_errs=" + this.u + ", eth_rcv_drops=" + this.v + ", eth_snd_bytes=" + this.w + ", eth_snd_packets=" + this.x + ", eth_snd_errs=" + this.y + ", eth_snd_drops=" + this.z + '}';
        }
    }

    /* compiled from: NetErrHelper */
    public static class c {
        boolean a;
        public long b;
        public long c;
        public long d;
        public long e;
        public long f;
        public long g;
        public long h;

        public c(long j) {
            this.a = false;
            this.c = -1;
            this.d = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            this.h = -1;
            this.b = j;
            this.a = false;
        }

        public c(c cVar, c cVar2) {
            this.a = false;
            this.c = -1;
            this.d = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            this.h = -1;
            this.a = true;
            this.b = cVar.b - cVar2.b;
            this.c = cVar.c - cVar2.c;
            this.d = cVar.d - cVar2.d;
            this.e = cVar.e - cVar2.e;
            this.f = cVar.f - cVar2.f;
            this.g = cVar.g - cVar2.g;
            this.h = cVar.h - cVar2.h;
        }

        public String toString() {
            return "SnmpNetStat{isDif=" + this.a + ", timestamp=" + this.b + ", noPorts=" + this.c + ", inErrors=" + this.d + ", rcvbufErrors=" + this.e + ", sndbufErrors=" + this.f + ", inDatagrams=" + this.g + ", outDatagrams=" + this.h + '}';
        }
    }
}
