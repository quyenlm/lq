package com.tencent.imsdk.tool.etc;

import android.os.Environment;
import android.os.StatFs;

public class IMHardwareInfo {
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0094 A[SYNTHETIC, Splitter:B:24:0x0094] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b7 A[SYNTHETIC, Splitter:B:30:0x00b7] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:21:0x0078=Splitter:B:21:0x0078, B:12:0x003a=Splitter:B:12:0x003a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String readFile(java.lang.String r6) {
        /*
            r0 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x0039, Exception -> 0x0077 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x0039, Exception -> 0x0077 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0039, Exception -> 0x0077 }
            r4.<init>(r6)     // Catch:{ FileNotFoundException -> 0x0039, Exception -> 0x0077 }
            java.lang.String r5 = "UTF-8"
            r3.<init>(r4, r5)     // Catch:{ FileNotFoundException -> 0x0039, Exception -> 0x0077 }
            r1.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0039, Exception -> 0x0077 }
            java.lang.String r3 = r1.readLine()     // Catch:{ FileNotFoundException -> 0x00dd, Exception -> 0x00da, all -> 0x00d7 }
            if (r1 == 0) goto L_0x001b
            r1.close()     // Catch:{ Exception -> 0x001d }
        L_0x001b:
            r0 = r1
        L_0x001c:
            return r3
        L_0x001d:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "close buffer failed : "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r2.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r4)
            goto L_0x001b
        L_0x0039:
            r2 = move-exception
        L_0x003a:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b4 }
            r3.<init>()     // Catch:{ all -> 0x00b4 }
            java.lang.String r4 = "read file error : "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00b4 }
            java.lang.String r4 = r2.getMessage()     // Catch:{ all -> 0x00b4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00b4 }
            com.tencent.imsdk.tool.etc.IMLogger.e(r3)     // Catch:{ all -> 0x00b4 }
            if (r0 == 0) goto L_0x0059
            r0.close()     // Catch:{ Exception -> 0x005b }
        L_0x0059:
            r3 = 0
            goto L_0x001c
        L_0x005b:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "close buffer failed : "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r3)
            goto L_0x0059
        L_0x0077:
            r2 = move-exception
        L_0x0078:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b4 }
            r3.<init>()     // Catch:{ all -> 0x00b4 }
            java.lang.String r4 = "read file error : "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00b4 }
            java.lang.String r4 = r2.getMessage()     // Catch:{ all -> 0x00b4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00b4 }
            com.tencent.imsdk.tool.etc.IMLogger.e(r3)     // Catch:{ all -> 0x00b4 }
            if (r0 == 0) goto L_0x0059
            r0.close()     // Catch:{ Exception -> 0x0098 }
            goto L_0x0059
        L_0x0098:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "close buffer failed : "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r3)
            goto L_0x0059
        L_0x00b4:
            r3 = move-exception
        L_0x00b5:
            if (r0 == 0) goto L_0x00ba
            r0.close()     // Catch:{ Exception -> 0x00bb }
        L_0x00ba:
            throw r3
        L_0x00bb:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "close buffer failed : "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r2.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r4)
            goto L_0x00ba
        L_0x00d7:
            r3 = move-exception
            r0 = r1
            goto L_0x00b5
        L_0x00da:
            r2 = move-exception
            r0 = r1
            goto L_0x0078
        L_0x00dd:
            r2 = move-exception
            r0 = r1
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.etc.IMHardwareInfo.readFile(java.lang.String):java.lang.String");
    }

    public static String getCpuName() {
        String[] array;
        try {
            String text = readFile("/proc/cpuinfo");
            if (!(text == null || (array = text.split(":\\s+", 2)) == null || array.length < 2)) {
                return array[1];
            }
        } catch (Exception e) {
            IMLogger.e("get cpu info error : " + e.getMessage());
        }
        return null;
    }

    public static String getTotalMemory() {
        String[] array;
        try {
            String text = readFile("/proc/meminfo");
            if (!(text == null || (array = text.split(":\\s+", 2)) == null || array.length < 2)) {
                return array[1];
            }
        } catch (Exception e) {
            IMLogger.e("get mem info error : " + e.getMessage());
        }
        return null;
    }

    public static long getTotalInternalMemorySize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) stat.getBlockCount()) * ((long) stat.getBlockSize());
    }
}
