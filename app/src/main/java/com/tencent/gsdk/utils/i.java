package com.tencent.gsdk.utils;

/* compiled from: NetStatHelper */
public class i {
    /* JADX WARNING: Removed duplicated region for block: B:31:0x011d A[SYNTHETIC, Splitter:B:31:0x011d] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0127 A[SYNTHETIC, Splitter:B:37:0x0127] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.gsdk.utils.i.a a() {
        /*
            r4 = 0
            r1 = 1
            com.tencent.gsdk.utils.i$a r6 = new com.tencent.gsdk.utils.i$a
            long r2 = java.lang.System.currentTimeMillis()
            r6.<init>(r2)
            r3 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00ff, all -> 0x0123 }
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ Exception -> 0x00ff, all -> 0x0123 }
            java.lang.String r5 = "/proc/net/dev"
            r0.<init>(r5)     // Catch:{ Exception -> 0x00ff, all -> 0x0123 }
            r5 = 1024(0x400, float:1.435E-42)
            r2.<init>(r0, r5)     // Catch:{ Exception -> 0x00ff, all -> 0x0123 }
            java.lang.String r5 = r2.readLine()     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r3 = r4
            r0 = r4
        L_0x0020:
            if (r5 == 0) goto L_0x00f9
            if (r0 == 0) goto L_0x0026
            if (r3 != 0) goto L_0x00f9
        L_0x0026:
            java.lang.String r4 = r5.trim()     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            java.lang.String r5 = "rmnet0:"
            boolean r5 = r4.startsWith(r5)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            if (r5 != 0) goto L_0x003a
            java.lang.String r5 = "rmnet_ipa0:"
            boolean r5 = r4.startsWith(r5)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            if (r5 == 0) goto L_0x0098
        L_0x003a:
            java.lang.String r5 = "\\s+"
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            int r5 = r4.length     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r7 = 17
            if (r5 < r7) goto L_0x0092
            r0 = 1
            r0 = r4[r0]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.k = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r0 = 2
            r0 = r4[r0]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.l = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r0 = 3
            r0 = r4[r0]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.m = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r0 = 4
            r0 = r4[r0]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.n = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r0 = 9
            r0 = r4[r0]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.o = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r0 = 10
            r0 = r4[r0]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.p = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r0 = 11
            r0 = r4[r0]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.q = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r0 = 12
            r0 = r4[r0]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r4 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.r = r4     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r0 = r1
        L_0x0092:
            java.lang.String r4 = r2.readLine()     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r5 = r4
            goto L_0x0020
        L_0x0098:
            java.lang.String r5 = "wlan0:"
            boolean r5 = r4.startsWith(r5)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            if (r5 == 0) goto L_0x0092
            java.lang.String r5 = "\\s+"
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            int r5 = r4.length     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r7 = 17
            if (r5 < r7) goto L_0x0092
            r3 = 1
            r3 = r4[r3]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.c = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r3 = 2
            r3 = r4[r3]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.d = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r3 = 3
            r3 = r4[r3]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.e = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r3 = 4
            r3 = r4[r3]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.f = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r3 = 9
            r3 = r4[r3]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.g = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r3 = 10
            r3 = r4[r3]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.h = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r3 = 11
            r3 = r4[r3]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.i = r8     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r3 = 12
            r3 = r4[r3]     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            long r4 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r6.j = r4     // Catch:{ Exception -> 0x0134, all -> 0x012f }
            r3 = r1
            goto L_0x0092
        L_0x00f9:
            if (r2 == 0) goto L_0x00fe
            r2.close()     // Catch:{ Exception -> 0x012b }
        L_0x00fe:
            return r6
        L_0x00ff:
            r0 = move-exception
            r1 = r3
        L_0x0101:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0131 }
            r2.<init>()     // Catch:{ all -> 0x0131 }
            java.lang.String r3 = "getNetStat exception:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x0131 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0131 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0131 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0131 }
            com.tencent.gsdk.utils.Logger.d(r0)     // Catch:{ all -> 0x0131 }
            if (r1 == 0) goto L_0x00fe
            r1.close()     // Catch:{ Exception -> 0x0121 }
            goto L_0x00fe
        L_0x0121:
            r0 = move-exception
            goto L_0x00fe
        L_0x0123:
            r0 = move-exception
            r2 = r3
        L_0x0125:
            if (r2 == 0) goto L_0x012a
            r2.close()     // Catch:{ Exception -> 0x012d }
        L_0x012a:
            throw r0
        L_0x012b:
            r0 = move-exception
            goto L_0x00fe
        L_0x012d:
            r1 = move-exception
            goto L_0x012a
        L_0x012f:
            r0 = move-exception
            goto L_0x0125
        L_0x0131:
            r0 = move-exception
            r2 = r1
            goto L_0x0125
        L_0x0134:
            r0 = move-exception
            r1 = r2
            goto L_0x0101
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.gsdk.utils.i.a():com.tencent.gsdk.utils.i$a");
    }

    public static a a(a aVar, a aVar2) {
        return new a(aVar, aVar2);
    }

    /* compiled from: NetStatHelper */
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
        }

        public String toString() {
            return "DevNetStat{isDif=" + this.a + ", timestamp=" + this.b + ", wlan_rcv_bytes=" + this.c + ", wlan_rcv_packets=" + this.d + ", wlan_rcv_errs=" + this.e + ", wlan_rcv_drops=" + this.f + ", wlan_snd_bytes=" + this.g + ", wlan_snd_packets=" + this.h + ", wlan_snd_errs=" + this.i + ", wlan_snd_drops=" + this.j + ", rmnet_rcv_bytes=" + this.k + ", rmnet_rcv_packets=" + this.l + ", rmnet_rcv_errs=" + this.m + ", rmnet_rcv_drops=" + this.n + ", rmnet_snd_bytes=" + this.o + ", rmnet_snd_packets=" + this.p + ", rmnet_snd_errs=" + this.q + ", rmnet_snd_drops=" + this.r + '}';
        }
    }
}
