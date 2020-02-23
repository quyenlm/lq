package com.tencent.mna.base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Process;
import android.support.v4.media.session.PlaybackStateCompat;

/* compiled from: PerfUtil */
public final class l {
    public static long[] a() {
        long[] jArr = {-1, 0, 0};
        try {
            long[] d = d();
            long j = d[0];
            long j2 = d[1];
            try {
                Thread.sleep(360);
            } catch (Exception e) {
            }
            long[] d2 = d();
            long j3 = d2[0];
            long j4 = d2[1];
            long j5 = j3 - j;
            long j6 = j4 - j2;
            if (j5 > 0 && j6 >= 0) {
                jArr[0] = (j6 * 100) / j5;
                jArr[1] = j3;
                jArr[2] = j4;
            }
        } catch (Exception e2) {
            h.d("getSystemCpuUsage() exception:" + e2.getMessage());
        }
        return jArr;
    }

    public static long[] a(long j, long j2) {
        long[] jArr = {-1, 0, 0};
        try {
            long[] d = d();
            long j3 = d[0];
            long j4 = d[1];
            long j5 = j3 - j;
            long j6 = j4 - j2;
            if (j5 > 0 && j6 >= 0) {
                jArr[0] = (j6 * 100) / j5;
                jArr[1] = j3;
                jArr[2] = j4;
            }
        } catch (Exception e) {
            h.d("getSystemCpuUsage exception:" + e.getMessage());
        }
        return jArr;
    }

    public static long[] b() {
        long[] jArr = {-1, 0, 0};
        try {
            long e = e();
            long f = f();
            try {
                Thread.sleep(360);
            } catch (Exception e2) {
            }
            long e3 = e();
            long f2 = f();
            long j = e3 - e;
            long j2 = f2 - f;
            if (j > 0 && j2 >= 0) {
                jArr[0] = (j2 * 100) / j;
                jArr[1] = e3;
                jArr[2] = f2;
            }
        } catch (Exception e4) {
            h.d("getProcessCpuUsage() exception:" + e4.getMessage());
        }
        return jArr;
    }

    public static long[] b(long j, long j2) {
        long[] jArr = {-1, 0, 0};
        try {
            long e = e();
            long f = f();
            long j3 = e - j;
            long j4 = f - j2;
            if (j3 > 0 && j4 >= 0) {
                jArr[0] = (j4 * 100) / j3;
                jArr[1] = e;
                jArr[2] = f;
            }
        } catch (Exception e2) {
            h.d("getProcessCpuUsage exception:" + e2.getMessage());
        }
        return jArr;
    }

    public static int a(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            long[] d = d(context);
            if (d == null || d.length < 2 || d[0] <= 0 || d[1] < 0) {
                return -1;
            }
            return (int) ((100 * d[1]) / d[0]);
        } catch (Exception e) {
            h.d("getSystemMemUsage exception:" + e.getMessage());
            return -1;
        }
    }

    public static int b(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            long c = c(context);
            if (c <= 0) {
                return -1;
            }
            long e = e(context);
            if (e >= 0) {
                return (int) ((e * 100) / c);
            }
            return -1;
        } catch (Exception e2) {
            h.d("getProcessMemUsage exception:" + e2.getMessage());
            return -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00cd A[SYNTHETIC, Splitter:B:32:0x00cd] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d9 A[SYNTHETIC, Splitter:B:40:0x00d9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int c() {
        /*
            r8 = 0
            r1 = -1
            r0 = 0
            r3 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.lang.String r4 = "/sys/class/kgsl/kgsl-3d0"
            r2.<init>(r4)     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            if (r2 == 0) goto L_0x0060
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.lang.String r6 = "/sys/class/kgsl/kgsl-3d0/gpubusy"
            r5.<init>(r6)     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.lang.String r6 = "UTF-8"
            r4.<init>(r5, r6)     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            r5 = 1024(0x400, float:1.435E-42)
            r2.<init>(r4, r5)     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.lang.String r3 = r2.readLine()     // Catch:{ Exception -> 0x00e3 }
            if (r3 == 0) goto L_0x005a
            java.lang.String r3 = r3.trim()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r4 = "\\s+"
            java.lang.String[] r3 = r3.split(r4)     // Catch:{ Exception -> 0x00e3 }
            r4 = 0
            r4 = r3[r4]     // Catch:{ Exception -> 0x00e3 }
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x00e3 }
            long r4 = (long) r4     // Catch:{ Exception -> 0x00e3 }
            r6 = 1
            r3 = r3[r6]     // Catch:{ Exception -> 0x00e3 }
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ Exception -> 0x00e3 }
            long r6 = (long) r3     // Catch:{ Exception -> 0x00e3 }
            int r3 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r3 <= 0) goto L_0x005a
            int r3 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r3 < 0) goto L_0x005a
            double r4 = (double) r4     // Catch:{ Exception -> 0x00e3 }
            double r6 = (double) r6     // Catch:{ Exception -> 0x00e3 }
            double r4 = r4 / r6
            r6 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r4 = r4 * r6
            long r0 = java.lang.Math.round(r4)     // Catch:{ Exception -> 0x00e3 }
            int r0 = (int) r0
        L_0x005a:
            if (r2 == 0) goto L_0x005f
            r2.close()     // Catch:{ Exception -> 0x00dd }
        L_0x005f:
            return r0
        L_0x0060:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.lang.String r4 = "/proc/mali/utilization"
            r2.<init>(r4)     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            if (r2 == 0) goto L_0x00e5
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.lang.String r5 = "/proc/mali/utilization"
            r4.<init>(r5)     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.lang.String r5 = "UTF-8"
            r0.<init>(r4, r5)     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            r4 = 1024(0x400, float:1.435E-42)
            r2.<init>(r0, r4)     // Catch:{ Exception -> 0x00af, all -> 0x00d5 }
            java.lang.String r0 = r2.readLine()     // Catch:{ Exception -> 0x00e3 }
            if (r0 == 0) goto L_0x00a8
            java.lang.String r3 = "="
            int r3 = r0.indexOf(r3)     // Catch:{ Exception -> 0x00e3 }
            int r3 = r3 + 1
            int r4 = r0.length()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r0 = r0.substring(r3, r4)     // Catch:{ Exception -> 0x00e3 }
            r3 = 0
            java.lang.String r4 = "/"
            int r4 = r0.indexOf(r4)     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r0 = r0.substring(r3, r4)     // Catch:{ Exception -> 0x00e3 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x00e3 }
            goto L_0x005a
        L_0x00a8:
            java.lang.String r0 = "get gpu usage failed"
            com.tencent.mna.base.utils.h.c(r0)     // Catch:{ Exception -> 0x00e3 }
            r0 = r1
            goto L_0x005a
        L_0x00af:
            r0 = move-exception
            r2 = r3
        L_0x00b1:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e1 }
            r3.<init>()     // Catch:{ all -> 0x00e1 }
            java.lang.String r4 = "get gpu usage error: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00e1 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00e1 }
            com.tencent.mna.base.utils.h.d(r0)     // Catch:{ all -> 0x00e1 }
            if (r2 == 0) goto L_0x00d0
            r2.close()     // Catch:{ Exception -> 0x00d2 }
        L_0x00d0:
            r0 = r1
            goto L_0x005f
        L_0x00d2:
            r0 = move-exception
            r0 = r1
            goto L_0x005f
        L_0x00d5:
            r0 = move-exception
            r2 = r3
        L_0x00d7:
            if (r2 == 0) goto L_0x00dc
            r2.close()     // Catch:{ Exception -> 0x00df }
        L_0x00dc:
            throw r0
        L_0x00dd:
            r1 = move-exception
            goto L_0x005f
        L_0x00df:
            r1 = move-exception
            goto L_0x00dc
        L_0x00e1:
            r0 = move-exception
            goto L_0x00d7
        L_0x00e3:
            r0 = move-exception
            goto L_0x00b1
        L_0x00e5:
            r2 = r3
            goto L_0x005a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.l.c():int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x00d7 A[SYNTHETIC, Splitter:B:62:0x00d7] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00e3 A[SYNTHETIC, Splitter:B:68:0x00e3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long[] d() {
        /*
            r0 = 2
            long[] r0 = new long[r0]
            r0 = {0, 0} // fill-array
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00d3, all -> 0x00df }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00d3, all -> 0x00df }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00d3, all -> 0x00df }
            java.lang.String r5 = "/proc/stat"
            r4.<init>(r5)     // Catch:{ Exception -> 0x00d3, all -> 0x00df }
            java.lang.String r5 = "UTF-8"
            r3.<init>(r4, r5)     // Catch:{ Exception -> 0x00d3, all -> 0x00df }
            r4 = 1024(0x400, float:1.435E-42)
            r1.<init>(r3, r4)     // Catch:{ Exception -> 0x00d3, all -> 0x00df }
            java.lang.String r2 = r1.readLine()     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r2 == 0) goto L_0x0028
            int r3 = r2.length()     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r3 > 0) goto L_0x002e
        L_0x0028:
            if (r1 == 0) goto L_0x002d
            r1.close()     // Catch:{ Exception -> 0x00e7 }
        L_0x002d:
            return r0
        L_0x002e:
            java.lang.String r3 = "\\s+"
            java.lang.String[] r2 = r2.split(r3)     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r2 == 0) goto L_0x003b
            int r3 = r2.length     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            r4 = 8
            if (r3 >= r4) goto L_0x0043
        L_0x003b:
            if (r1 == 0) goto L_0x002d
            r1.close()     // Catch:{ Exception -> 0x0041 }
            goto L_0x002d
        L_0x0041:
            r1 = move-exception
            goto L_0x002d
        L_0x0043:
            r3 = 1
            r8 = r2[r3]     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            r3 = 2
            r7 = r2[r3]     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            r3 = 3
            r6 = r2[r3]     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            r3 = 4
            r5 = r2[r3]     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            r3 = 5
            r4 = r2[r3]     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            r3 = 6
            r3 = r2[r3]     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            r9 = 7
            r2 = r2[r9]     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r8 == 0) goto L_0x0060
            int r9 = r8.length()     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r9 > 0) goto L_0x0062
        L_0x0060:
            java.lang.String r8 = "0"
        L_0x0062:
            if (r7 == 0) goto L_0x006a
            int r9 = r7.length()     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r9 > 0) goto L_0x006c
        L_0x006a:
            java.lang.String r7 = "0"
        L_0x006c:
            if (r6 == 0) goto L_0x0074
            int r9 = r6.length()     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r9 > 0) goto L_0x0076
        L_0x0074:
            java.lang.String r6 = "0"
        L_0x0076:
            if (r5 == 0) goto L_0x007e
            int r9 = r5.length()     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r9 > 0) goto L_0x0080
        L_0x007e:
            java.lang.String r5 = "0"
        L_0x0080:
            if (r4 == 0) goto L_0x0088
            int r9 = r4.length()     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r9 > 0) goto L_0x008a
        L_0x0088:
            java.lang.String r4 = "0"
        L_0x008a:
            if (r3 == 0) goto L_0x0092
            int r9 = r3.length()     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r9 > 0) goto L_0x0094
        L_0x0092:
            java.lang.String r3 = "0"
        L_0x0094:
            if (r2 == 0) goto L_0x009c
            int r9 = r2.length()     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r9 > 0) goto L_0x009e
        L_0x009c:
            java.lang.String r2 = "0"
        L_0x009e:
            r9 = 1
            long r10 = java.lang.Long.parseLong(r8)     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            long r12 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            long r10 = r10 + r12
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            long r6 = r6 + r10
            long r10 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            long r6 = r6 + r10
            long r10 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            long r6 = r6 + r10
            long r2 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            long r2 = r2 + r6
            r0[r9] = r2     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            r2 = 0
            r3 = 1
            r6 = r0[r3]     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            long r4 = java.lang.Long.parseLong(r5)     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            long r4 = r4 + r6
            r0[r2] = r4     // Catch:{ Exception -> 0x00ee, all -> 0x00ec }
            if (r1 == 0) goto L_0x002d
            r1.close()     // Catch:{ Exception -> 0x00d0 }
            goto L_0x002d
        L_0x00d0:
            r1 = move-exception
            goto L_0x002d
        L_0x00d3:
            r1 = move-exception
            r1 = r2
        L_0x00d5:
            if (r1 == 0) goto L_0x002d
            r1.close()     // Catch:{ Exception -> 0x00dc }
            goto L_0x002d
        L_0x00dc:
            r1 = move-exception
            goto L_0x002d
        L_0x00df:
            r0 = move-exception
            r1 = r2
        L_0x00e1:
            if (r1 == 0) goto L_0x00e6
            r1.close()     // Catch:{ Exception -> 0x00ea }
        L_0x00e6:
            throw r0
        L_0x00e7:
            r1 = move-exception
            goto L_0x002d
        L_0x00ea:
            r1 = move-exception
            goto L_0x00e6
        L_0x00ec:
            r0 = move-exception
            goto L_0x00e1
        L_0x00ee:
            r2 = move-exception
            goto L_0x00d5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.l.d():long[]");
    }

    private static long e() {
        long[] d = d();
        if (d == null || d.length <= 0) {
            return 0;
        }
        return d[0];
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d2 A[SYNTHETIC, Splitter:B:56:0x00d2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long f() {
        /*
            r0 = 0
            r4 = 0
            int r2 = android.os.Process.myPid()     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            r7.<init>()     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.lang.String r8 = "/proc/"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.lang.StringBuilder r2 = r7.append(r2)     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.lang.String r7 = "/stat"
            java.lang.StringBuilder r2 = r2.append(r7)     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            r6.<init>(r2)     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.lang.String r2 = "UTF-8"
            r5.<init>(r6, r2)     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            r2 = 1024(0x400, float:1.435E-42)
            r3.<init>(r5, r2)     // Catch:{ Exception -> 0x00a8, all -> 0x00ce }
            java.lang.String r2 = r3.readLine()     // Catch:{ Exception -> 0x00dd }
            if (r2 == 0) goto L_0x003f
            int r4 = r2.length()     // Catch:{ Exception -> 0x00dd }
            if (r4 > 0) goto L_0x0045
        L_0x003f:
            if (r3 == 0) goto L_0x0044
            r3.close()     // Catch:{ Exception -> 0x00d6 }
        L_0x0044:
            return r0
        L_0x0045:
            java.lang.String r4 = "\\s+"
            java.lang.String[] r2 = r2.split(r4)     // Catch:{ Exception -> 0x00dd }
            if (r2 != 0) goto L_0x0055
            if (r3 == 0) goto L_0x0044
            r3.close()     // Catch:{ Exception -> 0x0053 }
            goto L_0x0044
        L_0x0053:
            r2 = move-exception
            goto L_0x0044
        L_0x0055:
            r4 = 13
            r6 = r2[r4]     // Catch:{ Exception -> 0x00dd }
            r4 = 14
            r5 = r2[r4]     // Catch:{ Exception -> 0x00dd }
            r4 = 15
            r4 = r2[r4]     // Catch:{ Exception -> 0x00dd }
            r7 = 16
            r2 = r2[r7]     // Catch:{ Exception -> 0x00dd }
            if (r6 == 0) goto L_0x006d
            int r7 = r6.length()     // Catch:{ Exception -> 0x00dd }
            if (r7 > 0) goto L_0x006f
        L_0x006d:
            java.lang.String r6 = "0"
        L_0x006f:
            if (r5 == 0) goto L_0x0077
            int r7 = r5.length()     // Catch:{ Exception -> 0x00dd }
            if (r7 > 0) goto L_0x0079
        L_0x0077:
            java.lang.String r5 = "0"
        L_0x0079:
            if (r4 == 0) goto L_0x0081
            int r7 = r4.length()     // Catch:{ Exception -> 0x00dd }
            if (r7 > 0) goto L_0x0083
        L_0x0081:
            java.lang.String r4 = "0"
        L_0x0083:
            if (r2 == 0) goto L_0x008b
            int r7 = r2.length()     // Catch:{ Exception -> 0x00dd }
            if (r7 > 0) goto L_0x008d
        L_0x008b:
            java.lang.String r2 = "0"
        L_0x008d:
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ Exception -> 0x00dd }
            long r8 = java.lang.Long.parseLong(r5)     // Catch:{ Exception -> 0x00dd }
            long r6 = r6 + r8
            long r4 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x00dd }
            long r4 = r4 + r6
            long r0 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x00dd }
            long r0 = r0 + r4
            if (r3 == 0) goto L_0x0044
            r3.close()     // Catch:{ Exception -> 0x00a6 }
            goto L_0x0044
        L_0x00a6:
            r2 = move-exception
            goto L_0x0044
        L_0x00a8:
            r2 = move-exception
            r3 = r4
        L_0x00aa:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00db }
            r4.<init>()     // Catch:{ all -> 0x00db }
            java.lang.String r5 = "getProcessCpuTime exception:"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00db }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x00db }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x00db }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00db }
            com.tencent.mna.base.utils.h.d(r2)     // Catch:{ all -> 0x00db }
            if (r3 == 0) goto L_0x0044
            r3.close()     // Catch:{ Exception -> 0x00cb }
            goto L_0x0044
        L_0x00cb:
            r2 = move-exception
            goto L_0x0044
        L_0x00ce:
            r0 = move-exception
            r3 = r4
        L_0x00d0:
            if (r3 == 0) goto L_0x00d5
            r3.close()     // Catch:{ Exception -> 0x00d9 }
        L_0x00d5:
            throw r0
        L_0x00d6:
            r2 = move-exception
            goto L_0x0044
        L_0x00d9:
            r1 = move-exception
            goto L_0x00d5
        L_0x00db:
            r0 = move-exception
            goto L_0x00d0
        L_0x00dd:
            r2 = move-exception
            goto L_0x00aa
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.l.f():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00e3 A[SYNTHETIC, Splitter:B:53:0x00e3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long[] d(android.content.Context r10) {
        /*
            r0 = 2
            long[] r1 = new long[r0]
            r1 = {-1, -1} // fill-array
            if (r10 != 0) goto L_0x000a
            r0 = r1
        L_0x0009:
            return r0
        L_0x000a:
            r2 = 0
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x00bb }
            r3 = 16
            if (r0 < r3) goto L_0x0047
            java.lang.String r0 = "activity"
            java.lang.Object r0 = r10.getSystemService(r0)     // Catch:{ Exception -> 0x00bb }
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0     // Catch:{ Exception -> 0x00bb }
            if (r0 != 0) goto L_0x0022
            if (r2 == 0) goto L_0x0020
            r2.close()     // Catch:{ Exception -> 0x00e7 }
        L_0x0020:
            r0 = r1
            goto L_0x0009
        L_0x0022:
            android.app.ActivityManager$MemoryInfo r3 = new android.app.ActivityManager$MemoryInfo     // Catch:{ Exception -> 0x00bb }
            r3.<init>()     // Catch:{ Exception -> 0x00bb }
            r0.getMemoryInfo(r3)     // Catch:{ Exception -> 0x00bb }
            r0 = 0
            long r4 = r3.totalMem     // Catch:{ Exception -> 0x00bb }
            r6 = 1048576(0x100000, double:5.180654E-318)
            long r4 = r4 / r6
            r1[r0] = r4     // Catch:{ Exception -> 0x00bb }
            r0 = 1
            r4 = 0
            r4 = r1[r4]     // Catch:{ Exception -> 0x00bb }
            long r6 = r3.availMem     // Catch:{ Exception -> 0x00bb }
            r8 = 1048576(0x100000, double:5.180654E-318)
            long r6 = r6 / r8
            long r4 = r4 - r6
            r1[r0] = r4     // Catch:{ Exception -> 0x00bb }
        L_0x0040:
            if (r2 == 0) goto L_0x0045
            r2.close()     // Catch:{ Exception -> 0x00ec }
        L_0x0045:
            r0 = r1
            goto L_0x0009
        L_0x0047:
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00bb }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00bb }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00bb }
            java.lang.String r5 = "/proc/meminfo"
            r4.<init>(r5)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r5 = "UTF-8"
            r0.<init>(r4, r5)     // Catch:{ Exception -> 0x00bb }
            r4 = 1024(0x400, float:1.435E-42)
            r3.<init>(r0, r4)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r0 = r3.readLine()     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            java.lang.String r2 = r3.readLine()     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            if (r0 == 0) goto L_0x0068
            if (r2 != 0) goto L_0x006f
        L_0x0068:
            if (r3 == 0) goto L_0x006d
            r3.close()     // Catch:{ Exception -> 0x00ea }
        L_0x006d:
            r0 = r1
            goto L_0x0009
        L_0x006f:
            java.lang.String r4 = "(\\d+)"
            java.util.regex.Pattern r4 = java.util.regex.Pattern.compile(r4)     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            java.util.regex.Matcher r5 = r4.matcher(r0)     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            java.util.regex.Matcher r4 = r4.matcher(r2)     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            java.lang.String r2 = "0"
            java.lang.String r0 = "0"
        L_0x0081:
            boolean r6 = r5.find()     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            if (r6 == 0) goto L_0x008d
            r2 = 1
            java.lang.String r2 = r5.group(r2)     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            goto L_0x0081
        L_0x008d:
            if (r2 != 0) goto L_0x0091
            java.lang.String r2 = "0"
        L_0x0091:
            boolean r5 = r4.find()     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            if (r5 == 0) goto L_0x009d
            r0 = 1
            java.lang.String r0 = r4.group(r0)     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            goto L_0x0091
        L_0x009d:
            if (r0 != 0) goto L_0x00a1
            java.lang.String r0 = "0"
        L_0x00a1:
            r4 = 0
            long r6 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            r8 = 1024(0x400, double:5.06E-321)
            long r6 = r6 / r8
            r1[r4] = r6     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            r2 = 1
            r4 = 0
            r4 = r1[r4]     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            long r6 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            r8 = 1024(0x400, double:5.06E-321)
            long r6 = r6 / r8
            long r4 = r4 - r6
            r1[r2] = r4     // Catch:{ Exception -> 0x00f4, all -> 0x00f1 }
            r2 = r3
            goto L_0x0040
        L_0x00bb:
            r0 = move-exception
        L_0x00bc:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e0 }
            r3.<init>()     // Catch:{ all -> 0x00e0 }
            java.lang.String r4 = "getTotalAndUsedMemoryInMB exception:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00e0 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00e0 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x00e0 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00e0 }
            com.tencent.mna.base.utils.h.d(r0)     // Catch:{ all -> 0x00e0 }
            if (r2 == 0) goto L_0x0045
            r2.close()     // Catch:{ Exception -> 0x00dd }
            goto L_0x0045
        L_0x00dd:
            r0 = move-exception
            goto L_0x0045
        L_0x00e0:
            r0 = move-exception
        L_0x00e1:
            if (r2 == 0) goto L_0x00e6
            r2.close()     // Catch:{ Exception -> 0x00ef }
        L_0x00e6:
            throw r0
        L_0x00e7:
            r0 = move-exception
            goto L_0x0020
        L_0x00ea:
            r0 = move-exception
            goto L_0x006d
        L_0x00ec:
            r0 = move-exception
            goto L_0x0045
        L_0x00ef:
            r1 = move-exception
            goto L_0x00e6
        L_0x00f1:
            r0 = move-exception
            r2 = r3
            goto L_0x00e1
        L_0x00f4:
            r0 = move-exception
            r2 = r3
            goto L_0x00bc
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.l.d(android.content.Context):long[]");
    }

    public static long c(Context context) {
        long[] d = d(context);
        if (d == null || d.length <= 0) {
            return -1;
        }
        return d[0];
    }

    private static long e(Context context) {
        long j;
        Debug.MemoryInfo[] processMemoryInfo;
        if (context == null) {
            return -1;
        }
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int[] iArr = {Process.myPid()};
            if (activityManager == null || (processMemoryInfo = activityManager.getProcessMemoryInfo(iArr)) == null || processMemoryInfo.length < 1 || processMemoryInfo[0] == null) {
                return -1;
            }
            j = ((long) processMemoryInfo[0].getTotalPss()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            return j;
        } catch (Exception e) {
            h.d("Get process Memory error:" + e.getMessage());
            j = -1;
        }
    }
}
