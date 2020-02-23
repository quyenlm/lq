package com.tencent.component.utils;

import android.app.ActivityManager;
import com.tencent.component.ComponentContext;
import com.tencent.component.utils.log.LogUtil;
import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class PerformanceUtil {
    public static final int MEMORY_LEVEL_HIGH = 0;
    public static final int MEMORY_LEVEL_LOW = 2;
    public static final int MEMORY_LEVEL_NORMAL = 1;
    private static final String TAG = "PerformanceUtil";
    private static int sCoreNum = 0;
    private static long sCpuFrequence = 0;
    private static long sTotalMemo = 0;

    public static int getNumCores() {
        if (sCoreNum == 0) {
            try {
                File[] files = new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                            return true;
                        }
                        return false;
                    }
                });
                sCoreNum = files == null ? 0 : files.length;
            } catch (Exception e) {
                LogUtil.e(TAG, "getNumCores exception occured,e=", e);
                sCoreNum = 1;
            }
        }
        return sCoreNum;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x004a A[SYNTHETIC, Splitter:B:23:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004f A[Catch:{ IOException -> 0x0053 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0066 A[SYNTHETIC, Splitter:B:34:0x0066] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x006b A[Catch:{ IOException -> 0x006f }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0082 A[SYNTHETIC, Splitter:B:45:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0087 A[Catch:{ IOException -> 0x008b }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0093 A[SYNTHETIC, Splitter:B:53:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0098 A[Catch:{ IOException -> 0x009c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long getCpuFrequence() {
        /*
            long r6 = sCpuFrequence
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 != 0) goto L_0x0034
            r1 = 0
            r4 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x003c, ClassCastException -> 0x0058, Exception -> 0x0074 }
            java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x003c, ClassCastException -> 0x0058, Exception -> 0x0074 }
            java.lang.String r7 = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"
            r6.<init>(r7)     // Catch:{ IOException -> 0x003c, ClassCastException -> 0x0058, Exception -> 0x0074 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x003c, ClassCastException -> 0x0058, Exception -> 0x0074 }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ IOException -> 0x00b6, ClassCastException -> 0x00af, Exception -> 0x00a8, all -> 0x00a1 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x00b6, ClassCastException -> 0x00af, Exception -> 0x00a8, all -> 0x00a1 }
            r6.<init>(r2)     // Catch:{ IOException -> 0x00b6, ClassCastException -> 0x00af, Exception -> 0x00a8, all -> 0x00a1 }
            r5.<init>(r6)     // Catch:{ IOException -> 0x00b6, ClassCastException -> 0x00af, Exception -> 0x00a8, all -> 0x00a1 }
            java.lang.String r3 = r5.readLine()     // Catch:{ IOException -> 0x00b9, ClassCastException -> 0x00b2, Exception -> 0x00ab, all -> 0x00a4 }
            long r6 = java.lang.Long.parseLong(r3)     // Catch:{ IOException -> 0x00b9, ClassCastException -> 0x00b2, Exception -> 0x00ab, all -> 0x00a4 }
            sCpuFrequence = r6     // Catch:{ IOException -> 0x00b9, ClassCastException -> 0x00b2, Exception -> 0x00ab, all -> 0x00a4 }
            if (r5 == 0) goto L_0x002f
            r5.close()     // Catch:{ IOException -> 0x0037 }
        L_0x002f:
            if (r2 == 0) goto L_0x0034
            r2.close()     // Catch:{ IOException -> 0x0037 }
        L_0x0034:
            long r6 = sCpuFrequence
            return r6
        L_0x0037:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0034
        L_0x003c:
            r0 = move-exception
        L_0x003d:
            java.lang.String r6 = "PerformanceUtil"
            java.lang.String r7 = "getCpuFrequence IOException occured,e="
            com.tencent.component.utils.log.LogUtil.e(r6, r7, r0)     // Catch:{ all -> 0x0090 }
            r6 = -1
            sCpuFrequence = r6     // Catch:{ all -> 0x0090 }
            if (r4 == 0) goto L_0x004d
            r4.close()     // Catch:{ IOException -> 0x0053 }
        L_0x004d:
            if (r1 == 0) goto L_0x0034
            r1.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x0034
        L_0x0053:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0034
        L_0x0058:
            r0 = move-exception
        L_0x0059:
            java.lang.String r6 = "PerformanceUtil"
            java.lang.String r7 = "getCpuFrequence ClassCastException occured,e="
            com.tencent.component.utils.log.LogUtil.e(r6, r7, r0)     // Catch:{ all -> 0x0090 }
            r6 = -1
            sCpuFrequence = r6     // Catch:{ all -> 0x0090 }
            if (r4 == 0) goto L_0x0069
            r4.close()     // Catch:{ IOException -> 0x006f }
        L_0x0069:
            if (r1 == 0) goto L_0x0034
            r1.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x0034
        L_0x006f:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0034
        L_0x0074:
            r0 = move-exception
        L_0x0075:
            java.lang.String r6 = "PerformanceUtil"
            java.lang.String r7 = "getCpuFrequence Exception occured,e="
            com.tencent.component.utils.log.LogUtil.e(r6, r7, r0)     // Catch:{ all -> 0x0090 }
            r6 = -1
            sCpuFrequence = r6     // Catch:{ all -> 0x0090 }
            if (r4 == 0) goto L_0x0085
            r4.close()     // Catch:{ IOException -> 0x008b }
        L_0x0085:
            if (r1 == 0) goto L_0x0034
            r1.close()     // Catch:{ IOException -> 0x008b }
            goto L_0x0034
        L_0x008b:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0034
        L_0x0090:
            r6 = move-exception
        L_0x0091:
            if (r4 == 0) goto L_0x0096
            r4.close()     // Catch:{ IOException -> 0x009c }
        L_0x0096:
            if (r1 == 0) goto L_0x009b
            r1.close()     // Catch:{ IOException -> 0x009c }
        L_0x009b:
            throw r6
        L_0x009c:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x009b
        L_0x00a1:
            r6 = move-exception
            r1 = r2
            goto L_0x0091
        L_0x00a4:
            r6 = move-exception
            r4 = r5
            r1 = r2
            goto L_0x0091
        L_0x00a8:
            r0 = move-exception
            r1 = r2
            goto L_0x0075
        L_0x00ab:
            r0 = move-exception
            r4 = r5
            r1 = r2
            goto L_0x0075
        L_0x00af:
            r0 = move-exception
            r1 = r2
            goto L_0x0059
        L_0x00b2:
            r0 = move-exception
            r4 = r5
            r1 = r2
            goto L_0x0059
        L_0x00b6:
            r0 = move-exception
            r1 = r2
            goto L_0x003d
        L_0x00b9:
            r0 = move-exception
            r4 = r5
            r1 = r2
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.PerformanceUtil.getCpuFrequence():long");
    }

    public static long getAvailMemory() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ((ActivityManager) ComponentContext.getContext().getSystemService("activity")).getMemoryInfo(mi);
        return mi.availMem;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0055 A[SYNTHETIC, Splitter:B:24:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0065 A[SYNTHETIC, Splitter:B:30:0x0065] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long getTotalMemory() {
        /*
            long r10 = sTotalMemo
            r12 = 0
            int r9 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r9 != 0) goto L_0x003e
            java.lang.String r7 = "/proc/meminfo"
            r2 = -1
            r5 = 0
            java.io.FileReader r6 = new java.io.FileReader     // Catch:{ IOException -> 0x004b }
            r6.<init>(r7)     // Catch:{ IOException -> 0x004b }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r9 = 8192(0x2000, float:1.14794E-41)
            r4.<init>(r6, r9)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            java.lang.String r8 = r4.readLine()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            if (r8 == 0) goto L_0x0033
            java.lang.String r9 = "\\s+"
            java.lang.String[] r0 = r8.split(r9)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r9 = 1
            r9 = r0[r9]     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            int r9 = r9.intValue()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            int r9 = r9 * 1024
            long r2 = (long) r9     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
        L_0x0033:
            r4.close()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            if (r6 == 0) goto L_0x0078
            r6.close()     // Catch:{ IOException -> 0x0041 }
            r5 = r6
        L_0x003c:
            sTotalMemo = r2
        L_0x003e:
            long r10 = sTotalMemo
            return r10
        L_0x0041:
            r1 = move-exception
            java.lang.String r9 = "PerformanceUtil"
            java.lang.String r10 = "close localFileReader Exception occured,e="
            android.util.Log.e(r9, r10, r1)
            r5 = r6
            goto L_0x003c
        L_0x004b:
            r1 = move-exception
        L_0x004c:
            java.lang.String r9 = "PerformanceUtil"
            java.lang.String r10 = "getTotalMemory Exception occured,e="
            com.tencent.component.utils.log.LogUtil.e(r9, r10, r1)     // Catch:{ all -> 0x0062 }
            if (r5 == 0) goto L_0x003c
            r5.close()     // Catch:{ IOException -> 0x0059 }
            goto L_0x003c
        L_0x0059:
            r1 = move-exception
            java.lang.String r9 = "PerformanceUtil"
            java.lang.String r10 = "close localFileReader Exception occured,e="
            android.util.Log.e(r9, r10, r1)
            goto L_0x003c
        L_0x0062:
            r9 = move-exception
        L_0x0063:
            if (r5 == 0) goto L_0x0068
            r5.close()     // Catch:{ IOException -> 0x0069 }
        L_0x0068:
            throw r9
        L_0x0069:
            r1 = move-exception
            java.lang.String r10 = "PerformanceUtil"
            java.lang.String r11 = "close localFileReader Exception occured,e="
            android.util.Log.e(r10, r11, r1)
            goto L_0x0068
        L_0x0072:
            r9 = move-exception
            r5 = r6
            goto L_0x0063
        L_0x0075:
            r1 = move-exception
            r5 = r6
            goto L_0x004c
        L_0x0078:
            r5 = r6
            goto L_0x003c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.PerformanceUtil.getTotalMemory():long");
    }

    public static int getMemoryClassLevel() {
        int memorgsize = ((ActivityManager) ComponentContext.getContext().getSystemService("activity")).getMemoryClass();
        if (memorgsize < 36) {
            return 2;
        }
        if (memorgsize < 42) {
            return 1;
        }
        return 0;
    }

    public static String getPerformanceDetail() {
        return "此设备性能信息：" + "Cpu频率 " + getCpuFrequence() + " Cpu核数 " + getNumCores() + " 总内存大小 " + getTotalMemory();
    }
}
