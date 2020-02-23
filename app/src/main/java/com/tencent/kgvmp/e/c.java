package com.tencent.kgvmp.e;

import android.content.Context;
import android.os.Build;
import android.support.v4.media.session.PlaybackStateCompat;
import java.io.File;

public class c {
    private static final String a = c.class.getSimpleName();
    private static final String[] b = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};

    public static double a(long j, long j2, long j3, long j4) {
        return ((((double) j2) - ((double) j4)) * 100.0d) / (((double) j) - ((double) j3));
    }

    public static String a() {
        return Build.MODEL;
    }

    public static boolean a(String str, Context context) {
        boolean z = true;
        if (context == null) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(str, 1);
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        return z;
    }

    public static String b() {
        return Build.MANUFACTURER;
    }

    public static int c() {
        return Build.VERSION.SDK_INT;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0075 A[SYNTHETIC, Splitter:B:19:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0082 A[SYNTHETIC, Splitter:B:26:0x0082] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long d() {
        /*
            r4 = 0
            r0 = -1
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0067, all -> 0x007e }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0067, all -> 0x007e }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0067, all -> 0x007e }
            java.lang.String r6 = "/proc/stat"
            r5.<init>(r6)     // Catch:{ Exception -> 0x0067, all -> 0x007e }
            java.lang.String r6 = "utf-8"
            r2.<init>(r5, r6)     // Catch:{ Exception -> 0x0067, all -> 0x007e }
            r5 = 1000(0x3e8, float:1.401E-42)
            r3.<init>(r2, r5)     // Catch:{ Exception -> 0x0067, all -> 0x007e }
            java.lang.String r2 = r3.readLine()     // Catch:{ Exception -> 0x008d }
            if (r2 == 0) goto L_0x005c
            java.lang.String r4 = " "
            java.lang.String[] r2 = r2.split(r4)     // Catch:{ Exception -> 0x008d }
            r4 = 2
            r4 = r2[r4]     // Catch:{ Exception -> 0x008d }
            long r4 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x008d }
            r6 = 3
            r6 = r2[r6]     // Catch:{ Exception -> 0x008d }
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ Exception -> 0x008d }
            long r4 = r4 + r6
            r6 = 4
            r6 = r2[r6]     // Catch:{ Exception -> 0x008d }
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ Exception -> 0x008d }
            long r4 = r4 + r6
            r6 = 5
            r6 = r2[r6]     // Catch:{ Exception -> 0x008d }
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ Exception -> 0x008d }
            long r4 = r4 + r6
            r6 = 6
            r6 = r2[r6]     // Catch:{ Exception -> 0x008d }
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ Exception -> 0x008d }
            long r4 = r4 + r6
            r6 = 7
            r6 = r2[r6]     // Catch:{ Exception -> 0x008d }
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ Exception -> 0x008d }
            long r4 = r4 + r6
            r6 = 8
            r2 = r2[r6]     // Catch:{ Exception -> 0x008d }
            long r0 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x008d }
            long r0 = r0 + r4
        L_0x005c:
            if (r3 == 0) goto L_0x0061
            r3.close()     // Catch:{ IOException -> 0x0062 }
        L_0x0061:
            return r0
        L_0x0062:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0061
        L_0x0067:
            r2 = move-exception
            r3 = r4
        L_0x0069:
            r2.printStackTrace()     // Catch:{ all -> 0x008b }
            java.lang.String r2 = a     // Catch:{ all -> 0x008b }
            java.lang.String r4 = "getTotalCpu: exception."
            com.tencent.kgvmp.e.f.a(r2, r4)     // Catch:{ all -> 0x008b }
            if (r3 == 0) goto L_0x0061
            r3.close()     // Catch:{ IOException -> 0x0079 }
            goto L_0x0061
        L_0x0079:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0061
        L_0x007e:
            r0 = move-exception
            r3 = r4
        L_0x0080:
            if (r3 == 0) goto L_0x0085
            r3.close()     // Catch:{ IOException -> 0x0086 }
        L_0x0085:
            throw r0
        L_0x0086:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0085
        L_0x008b:
            r0 = move-exception
            goto L_0x0080
        L_0x008d:
            r2 = move-exception
            goto L_0x0069
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.kgvmp.e.c.d():long");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x008c A[SYNTHETIC, Splitter:B:21:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0098 A[SYNTHETIC, Splitter:B:27:0x0098] */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long e() {
        /*
            r2 = 0
            int r0 = android.os.Process.myPid()     // Catch:{ Exception -> 0x006b }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x006b }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x006b }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x006b }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006b }
            r5.<init>()     // Catch:{ Exception -> 0x006b }
            java.lang.String r6 = "/proc/"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x006b }
            java.lang.StringBuilder r0 = r5.append(r0)     // Catch:{ Exception -> 0x006b }
            java.lang.String r5 = "/stat"
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Exception -> 0x006b }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x006b }
            r4.<init>(r0)     // Catch:{ Exception -> 0x006b }
            java.lang.String r0 = "utf-8"
            r3.<init>(r4, r0)     // Catch:{ Exception -> 0x006b }
            r0 = 1000(0x3e8, float:1.401E-42)
            r1.<init>(r3, r0)     // Catch:{ Exception -> 0x006b }
            java.lang.String r0 = r1.readLine()     // Catch:{ Exception -> 0x00c4, all -> 0x00c1 }
            if (r0 == 0) goto L_0x003d
            java.lang.String r2 = " "
            java.lang.String[] r2 = r0.split(r2)     // Catch:{ Exception -> 0x00c4, all -> 0x00c1 }
        L_0x003d:
            if (r1 == 0) goto L_0x0042
            r1.close()     // Catch:{ Exception -> 0x0066 }
        L_0x0042:
            r0 = 13
            r0 = r2[r0]     // Catch:{ Exception -> 0x00a1 }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x00a1 }
            r3 = 14
            r3 = r2[r3]     // Catch:{ Exception -> 0x00a1 }
            long r4 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x00a1 }
            long r0 = r0 + r4
            r3 = 15
            r3 = r2[r3]     // Catch:{ Exception -> 0x00a1 }
            long r4 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x00a1 }
            long r0 = r0 + r4
            r3 = 16
            r2 = r2[r3]     // Catch:{ Exception -> 0x00a1 }
            long r2 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x00a1 }
            long r0 = r0 + r2
        L_0x0065:
            return r0
        L_0x0066:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0042
        L_0x006b:
            r0 = move-exception
        L_0x006c:
            java.lang.String r1 = a     // Catch:{ all -> 0x0095 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0095 }
            r3.<init>()     // Catch:{ all -> 0x0095 }
            java.lang.String r4 = "IOException"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0095 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0095 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x0095 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0095 }
            com.tencent.kgvmp.e.f.c(r1, r0)     // Catch:{ all -> 0x0095 }
            r0 = 0
            if (r2 == 0) goto L_0x0065
            r2.close()     // Catch:{ Exception -> 0x0090 }
            goto L_0x0065
        L_0x0090:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0065
        L_0x0095:
            r0 = move-exception
        L_0x0096:
            if (r2 == 0) goto L_0x009b
            r2.close()     // Catch:{ Exception -> 0x009c }
        L_0x009b:
            throw r0
        L_0x009c:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x009b
        L_0x00a1:
            r0 = move-exception
            java.lang.String r1 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "ArrayIndexOutOfBoundsException"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.kgvmp.e.f.c(r1, r0)
            r0 = -1
            goto L_0x0065
        L_0x00c1:
            r0 = move-exception
            r2 = r1
            goto L_0x0096
        L_0x00c4:
            r0 = move-exception
            r2 = r1
            goto L_0x006c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.kgvmp.e.c.e():long");
    }

    public static int f() {
        try {
            Runtime runtime = Runtime.getRuntime();
            return (int) ((runtime.totalMemory() - runtime.freeMemory()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String g() {
        try {
            return d.b("/proc/cpuinfo");
        } catch (Exception e) {
            f.a(a, "DeviceUtil:getCPU: Could not open file /proc/cpuinfo");
            return null;
        }
    }

    public static String h() {
        String g = g();
        if (g != null) {
            return g.substring(g.indexOf(58, g.indexOf("Hardware")) + 1).trim();
        }
        return null;
    }

    public static boolean i() {
        String[] strArr = b;
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            File file = new File(strArr[i] + "su");
            if (file.isFile() && file.exists()) {
                return true;
            }
        }
        return false;
    }
}
