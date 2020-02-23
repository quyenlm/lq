package com.tencent.qqgamemi.util;

import android.content.Context;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tencent.component.utils.PerformanceUtil;
import com.tencent.component.utils.log.LogUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceDetectUtil {
    private static final String ARRY_LIST = "list";
    private static final String BLACK_MODEL_LIST = "srp_black_list.json";
    public static final long FIT_MEMORY = 1500;
    public static String[] FREQ_BASE_PATH = {"/sys/class/kgsl", "/sys/devices/platform/galcore/gpu/gpu0/gpufreq", "/sys/class/devfreq"};
    public static String[] FREQ_MAX_PATH = {"/kgsl-3d0/max_gpuclk", "/kgsl-3d0/devfreq/max_freq", "/scaling_max_freq", "/e8600000.mali/max_freq"};
    private static final String LI_MAN = "man";
    private static final String LI_MODEL = "model";
    public static final long RESOLUTION_HEIGH = 540;
    public static final long RESOLUTION_WIDTH = 960;
    private static final String TAG = "DeviceDetectUtil";
    public static String cpu_info;
    public static String gpu_info;
    private static long sTotalMemo = 0;

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
            int r9 = r9 / 1024
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
            java.lang.String r9 = "DeviceDetectUtil"
            java.lang.String r10 = "close localFileReader Exception occured,e="
            com.tencent.component.utils.log.LogUtil.e(r9, r10, r1)
            r5 = r6
            goto L_0x003c
        L_0x004b:
            r1 = move-exception
        L_0x004c:
            java.lang.String r9 = "DeviceDetectUtil"
            java.lang.String r10 = "getTotalMemory Exception occured,e="
            com.tencent.component.utils.log.LogUtil.e(r9, r10, r1)     // Catch:{ all -> 0x0062 }
            if (r5 == 0) goto L_0x003c
            r5.close()     // Catch:{ IOException -> 0x0059 }
            goto L_0x003c
        L_0x0059:
            r1 = move-exception
            java.lang.String r9 = "DeviceDetectUtil"
            java.lang.String r10 = "close localFileReader Exception occured,e="
            com.tencent.component.utils.log.LogUtil.e(r9, r10, r1)
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
            java.lang.String r10 = "DeviceDetectUtil"
            java.lang.String r11 = "close localFileReader Exception occured,e="
            com.tencent.component.utils.log.LogUtil.e(r10, r11, r1)
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
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qqgamemi.util.DeviceDetectUtil.getTotalMemory():long");
    }

    public static boolean isNumCoresFit() {
        boolean isFitNumCores = PerformanceUtil.getNumCores() >= 4;
        LogUtil.i(TAG, "isFitNumCores:" + isFitNumCores);
        return isFitNumCores;
    }

    public static boolean isMemoryFit() {
        long totalMemory = getTotalMemory();
        boolean isFitTotalMemory = totalMemory >= FIT_MEMORY;
        LogUtil.i(TAG, "isFitTotalMemory:" + isFitTotalMemory + ",内存：" + totalMemory);
        return isFitTotalMemory;
    }

    public static boolean isResolutionFit(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
        boolean isFitResolution = ((long) (dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels)) - 960 >= 0 && ((long) (dm.widthPixels < dm.heightPixels ? dm.widthPixels : dm.heightPixels)) - 540 >= 0;
        LogUtil.i(TAG, "isFitResolution:" + isFitResolution);
        return isFitResolution;
    }

    public static boolean isDeviceEnable(Context context) {
        return isResolutionFit(context) && !isBlackModel(context);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x009c A[SYNTHETIC, Splitter:B:32:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getCpuInfoByRelativePath() {
        /*
            java.lang.String r0 = ""
            r4 = 0
            r7 = 0
            java.lang.Runtime r8 = java.lang.Runtime.getRuntime()     // Catch:{ IOException -> 0x005b }
            java.lang.String r9 = "cat proc/cpuinfo"
            java.lang.Process r7 = r8.exec(r9)     // Catch:{ IOException -> 0x005b }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x005b }
            java.io.InputStream r8 = r7.getInputStream()     // Catch:{ IOException -> 0x005b }
            r6.<init>(r8)     // Catch:{ IOException -> 0x005b }
            java.io.LineNumberReader r5 = new java.io.LineNumberReader     // Catch:{ IOException -> 0x005b }
            r5.<init>(r6)     // Catch:{ IOException -> 0x005b }
            java.lang.String r8 = "x86"
            boolean r3 = hasCpuAbi(r8)     // Catch:{ IOException -> 0x00c2, all -> 0x00bf }
            java.lang.String r8 = "arm"
            boolean r2 = hasCpuAbi(r8)     // Catch:{ IOException -> 0x00c2, all -> 0x00bf }
            if (r3 == 0) goto L_0x003a
            java.lang.String r0 = parseCpuInfoByX86(r5)     // Catch:{ IOException -> 0x00c2, all -> 0x00bf }
        L_0x002e:
            if (r5 == 0) goto L_0x0033
            r5.close()     // Catch:{ IOException -> 0x0041 }
        L_0x0033:
            if (r7 == 0) goto L_0x00c5
            r7.destroy()
            r4 = r5
        L_0x0039:
            return r0
        L_0x003a:
            if (r2 == 0) goto L_0x002e
            java.lang.String r0 = parseCpuInfoByArm(r5)     // Catch:{ IOException -> 0x00c2, all -> 0x00bf }
            goto L_0x002e
        L_0x0041:
            r1 = move-exception
            java.lang.String r8 = "DeviceDetectUtil"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "getCpuInfoByRelativePath file close IOException e:"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r1)
            java.lang.String r9 = r9.toString()
            com.tencent.component.utils.log.LogUtil.e(r8, r9)
            goto L_0x0033
        L_0x005b:
            r1 = move-exception
        L_0x005c:
            java.lang.String r8 = "DeviceDetectUtil"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0099 }
            r9.<init>()     // Catch:{ all -> 0x0099 }
            java.lang.String r10 = "getCpuInfoByRelativePath file  IOException e:"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x0099 }
            java.lang.StringBuilder r9 = r9.append(r1)     // Catch:{ all -> 0x0099 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0099 }
            com.tencent.component.utils.log.LogUtil.e(r8, r9)     // Catch:{ all -> 0x0099 }
            if (r4 == 0) goto L_0x0079
            r4.close()     // Catch:{ IOException -> 0x007f }
        L_0x0079:
            if (r7 == 0) goto L_0x0039
            r7.destroy()
            goto L_0x0039
        L_0x007f:
            r1 = move-exception
            java.lang.String r8 = "DeviceDetectUtil"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "getCpuInfoByRelativePath file close IOException e:"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r1)
            java.lang.String r9 = r9.toString()
            com.tencent.component.utils.log.LogUtil.e(r8, r9)
            goto L_0x0079
        L_0x0099:
            r8 = move-exception
        L_0x009a:
            if (r4 == 0) goto L_0x009f
            r4.close()     // Catch:{ IOException -> 0x00a5 }
        L_0x009f:
            if (r7 == 0) goto L_0x00a4
            r7.destroy()
        L_0x00a4:
            throw r8
        L_0x00a5:
            r1 = move-exception
            java.lang.String r9 = "DeviceDetectUtil"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "getCpuInfoByRelativePath file close IOException e:"
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r1)
            java.lang.String r10 = r10.toString()
            com.tencent.component.utils.log.LogUtil.e(r9, r10)
            goto L_0x009f
        L_0x00bf:
            r8 = move-exception
            r4 = r5
            goto L_0x009a
        L_0x00c2:
            r1 = move-exception
            r4 = r5
            goto L_0x005c
        L_0x00c5:
            r4 = r5
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qqgamemi.util.DeviceDetectUtil.getCpuInfoByRelativePath():java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x006d A[SYNTHETIC, Splitter:B:27:0x006d] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0072 A[Catch:{ IOException -> 0x0076 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ab A[SYNTHETIC, Splitter:B:38:0x00ab] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b0 A[Catch:{ IOException -> 0x00b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00d3 A[SYNTHETIC, Splitter:B:46:0x00d3] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00d8 A[Catch:{ IOException -> 0x00dc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getCpuInfoByAbsolutePath() {
        /*
            r5 = 0
            r0 = 0
            java.lang.String r3 = ""
            java.lang.String r2 = "/proc/cpuinfo"
            java.io.FileReader r6 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x0052, NullPointerException -> 0x0090 }
            r6.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0052, NullPointerException -> 0x0090 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x0104, NullPointerException -> 0x00fd, all -> 0x00f6 }
            r1.<init>(r6)     // Catch:{ FileNotFoundException -> 0x0104, NullPointerException -> 0x00fd, all -> 0x00f6 }
            java.lang.String r9 = "x86"
            boolean r8 = hasCpuAbi(r9)     // Catch:{ FileNotFoundException -> 0x0108, NullPointerException -> 0x0100, all -> 0x00f9 }
            java.lang.String r9 = "arm"
            boolean r7 = hasCpuAbi(r9)     // Catch:{ FileNotFoundException -> 0x0108, NullPointerException -> 0x0100, all -> 0x00f9 }
            if (r8 == 0) goto L_0x002f
            java.lang.String r3 = parseCpuInfoByX86(r1)     // Catch:{ FileNotFoundException -> 0x0108, NullPointerException -> 0x0100, all -> 0x00f9 }
        L_0x0022:
            if (r6 == 0) goto L_0x0027
            r6.close()     // Catch:{ IOException -> 0x0036 }
        L_0x0027:
            if (r1 == 0) goto L_0x002c
            r1.close()     // Catch:{ IOException -> 0x0036 }
        L_0x002c:
            r0 = r1
            r5 = r6
        L_0x002e:
            return r3
        L_0x002f:
            if (r7 == 0) goto L_0x0022
            java.lang.String r3 = parseCpuInfoByArm(r1)     // Catch:{ FileNotFoundException -> 0x0108, NullPointerException -> 0x0100, all -> 0x00f9 }
            goto L_0x0022
        L_0x0036:
            r4 = move-exception
            java.lang.String r9 = "DeviceDetectUtil"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "getCpuInfo file close IOException e:"
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r4)
            java.lang.String r10 = r10.toString()
            com.tencent.component.utils.log.LogUtil.e(r9, r10)
            r0 = r1
            r5 = r6
            goto L_0x002e
        L_0x0052:
            r4 = move-exception
        L_0x0053:
            java.lang.String r9 = "DeviceDetectUtil"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d0 }
            r10.<init>()     // Catch:{ all -> 0x00d0 }
            java.lang.String r11 = "getCpuInfo FileNotFoundException e:"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x00d0 }
            java.lang.StringBuilder r10 = r10.append(r4)     // Catch:{ all -> 0x00d0 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x00d0 }
            com.tencent.component.utils.log.LogUtil.e(r9, r10)     // Catch:{ all -> 0x00d0 }
            if (r5 == 0) goto L_0x0070
            r5.close()     // Catch:{ IOException -> 0x0076 }
        L_0x0070:
            if (r0 == 0) goto L_0x002e
            r0.close()     // Catch:{ IOException -> 0x0076 }
            goto L_0x002e
        L_0x0076:
            r4 = move-exception
            java.lang.String r9 = "DeviceDetectUtil"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "getCpuInfo file close IOException e:"
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r4)
            java.lang.String r10 = r10.toString()
            com.tencent.component.utils.log.LogUtil.e(r9, r10)
            goto L_0x002e
        L_0x0090:
            r4 = move-exception
        L_0x0091:
            java.lang.String r9 = "DeviceDetectUtil"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d0 }
            r10.<init>()     // Catch:{ all -> 0x00d0 }
            java.lang.String r11 = "getCpuInfo NullPointerException e:"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x00d0 }
            java.lang.StringBuilder r10 = r10.append(r4)     // Catch:{ all -> 0x00d0 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x00d0 }
            com.tencent.component.utils.log.LogUtil.e(r9, r10)     // Catch:{ all -> 0x00d0 }
            if (r5 == 0) goto L_0x00ae
            r5.close()     // Catch:{ IOException -> 0x00b5 }
        L_0x00ae:
            if (r0 == 0) goto L_0x002e
            r0.close()     // Catch:{ IOException -> 0x00b5 }
            goto L_0x002e
        L_0x00b5:
            r4 = move-exception
            java.lang.String r9 = "DeviceDetectUtil"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "getCpuInfo file close IOException e:"
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r4)
            java.lang.String r10 = r10.toString()
            com.tencent.component.utils.log.LogUtil.e(r9, r10)
            goto L_0x002e
        L_0x00d0:
            r9 = move-exception
        L_0x00d1:
            if (r5 == 0) goto L_0x00d6
            r5.close()     // Catch:{ IOException -> 0x00dc }
        L_0x00d6:
            if (r0 == 0) goto L_0x00db
            r0.close()     // Catch:{ IOException -> 0x00dc }
        L_0x00db:
            throw r9
        L_0x00dc:
            r4 = move-exception
            java.lang.String r10 = "DeviceDetectUtil"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "getCpuInfo file close IOException e:"
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r11 = r11.append(r4)
            java.lang.String r11 = r11.toString()
            com.tencent.component.utils.log.LogUtil.e(r10, r11)
            goto L_0x00db
        L_0x00f6:
            r9 = move-exception
            r5 = r6
            goto L_0x00d1
        L_0x00f9:
            r9 = move-exception
            r0 = r1
            r5 = r6
            goto L_0x00d1
        L_0x00fd:
            r4 = move-exception
            r5 = r6
            goto L_0x0091
        L_0x0100:
            r4 = move-exception
            r0 = r1
            r5 = r6
            goto L_0x0091
        L_0x0104:
            r4 = move-exception
            r5 = r6
            goto L_0x0053
        L_0x0108:
            r4 = move-exception
            r0 = r1
            r5 = r6
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qqgamemi.util.DeviceDetectUtil.getCpuInfoByAbsolutePath():java.lang.String");
    }

    private static String getPhoneBrand() {
        return SystemPropertiesUtil.get("ro.product.brand");
    }

    private static String getCpuinfoBySystemProperties() {
        String cpuinfo = SystemPropertiesUtil.get("ro.hardware.alter");
        if (TextUtils.isEmpty(cpuinfo)) {
            cpuinfo = SystemPropertiesUtil.get("ro.board.platform");
        }
        if (TextUtils.isEmpty(cpuinfo)) {
            return SystemPropertiesUtil.get("ro.product.board");
        }
        return cpuinfo;
    }

    public static String getCpuInfo() {
        if (!TextUtils.isEmpty(cpu_info)) {
            return cpu_info;
        }
        cpu_info = getCpuInfoByAbsolutePath();
        if (TextUtils.isEmpty(cpu_info)) {
            cpu_info = getCpuInfoByRelativePath();
        }
        if (TextUtils.isEmpty(cpu_info)) {
            cpu_info = getCpuinfoBySystemProperties();
        }
        return cpu_info;
    }

    private static String parseCpuInfoByX86(BufferedReader reader) {
        String cpuInfo = "";
        do {
            try {
                String cpuInfoStr = reader.readLine();
                if (cpuInfoStr == null) {
                    break;
                } else if (cpuInfoStr.indexOf("model name") != -1) {
                    int beginIndex = cpuInfoStr.indexOf(":") + 1;
                    int endIndex = cpuInfoStr.indexOf("@");
                    if (endIndex == -1 || endIndex >= cpuInfoStr.length()) {
                        cpuInfo = cpuInfoStr.substring(beginIndex);
                    } else {
                        cpuInfo = cpuInfoStr.substring(beginIndex, endIndex);
                    }
                    LogUtil.i(TAG, "get cpuInfo by x86:" + cpuInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (TextUtils.isEmpty(cpuInfo));
        if (!TextUtils.isEmpty(cpuInfo)) {
            return cpuInfo.trim();
        }
        return cpuInfo;
    }

    private static String parseCpuInfoByArm(BufferedReader reader) {
        String cpuInfo = "";
        do {
            try {
                String cpuInfoStr = reader.readLine();
                if (cpuInfoStr == null) {
                    break;
                } else if (cpuInfoStr.indexOf("Hardware") != -1) {
                    cpuInfo = cpuInfoStr.split(":")[1];
                    LogUtil.i(TAG, "get cpuInfo by arm:" + cpuInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (TextUtils.isEmpty(cpuInfo));
        if (!TextUtils.isEmpty(cpuInfo)) {
            return cpuInfo.trim();
        }
        return cpuInfo;
    }

    private static boolean hasCpuAbi(String abi) {
        return Build.CPU_ABI.toLowerCase().contains(abi);
    }

    public static String getGpuInfo() {
        if (!TextUtils.isEmpty(gpu_info)) {
            return gpu_info;
        }
        try {
            StringBuilder gpuInfo = new StringBuilder();
            if (Build.VERSION.SDK_INT >= 17) {
                EGLDisplay dpy = EGL14.eglGetDisplay(0);
                int[] vers = new int[2];
                EGL14.eglInitialize(dpy, vers, 0, vers, 1);
                EGLConfig[] configs = new EGLConfig[1];
                int[] numConfig = new int[1];
                EGL14.eglChooseConfig(dpy, new int[]{12351, 12430, 12329, 0, 12352, 4, 12339, 1, 12344}, 0, configs, 0, 1, numConfig, 0);
                if (numConfig[0] == 0) {
                    LogUtil.w("getOpenGLESInformation", "no config found! PANIC!");
                }
                EGLConfig config = configs[0];
                EGLSurface surf = EGL14.eglCreatePbufferSurface(dpy, config, new int[]{12375, 64, 12374, 64, 12344}, 0);
                EGLContext ctx = EGL14.eglCreateContext(dpy, config, EGL14.EGL_NO_CONTEXT, new int[]{12440, 2, 12344}, 0);
                EGL14.eglMakeCurrent(dpy, surf, surf, ctx);
                gpuInfo.append(GLES20.glGetString(7937));
                EGL14.eglMakeCurrent(dpy, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
                EGL14.eglDestroySurface(dpy, surf);
                EGL14.eglDestroyContext(dpy, ctx);
                EGL14.eglTerminate(dpy);
            }
            String gpu_freq = getGpuFreq();
            if (!TextUtils.isEmpty(gpu_freq)) {
                gpuInfo.append(" @");
                gpuInfo.append(gpu_freq);
            }
            gpu_info = gpuInfo.toString();
            return gpu_info;
        } catch (NullPointerException e) {
            LogUtil.e(TAG, "getGpuInfo NullPointerException :" + e);
            return null;
        } catch (Exception e2) {
            LogUtil.e(TAG, "getGpuInfo Exception:" + e2);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0078 A[SYNTHETIC, Splitter:B:29:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x007d A[Catch:{ IOException -> 0x00d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0111 A[SYNTHETIC, Splitter:B:51:0x0111] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0116 A[Catch:{ IOException -> 0x011b }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x013d A[SYNTHETIC, Splitter:B:59:0x013d] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0142 A[Catch:{ IOException -> 0x0146 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getGpuFreq() {
        /*
            r4 = 0
            r0 = 0
            java.lang.String r7 = ""
            java.lang.String r3 = getGpuFreqPath()     // Catch:{ IOException -> 0x0172, IllegalArgumentException -> 0x00f2 }
            boolean r11 = android.text.TextUtils.isEmpty(r3)     // Catch:{ IOException -> 0x0172, IllegalArgumentException -> 0x00f2 }
            if (r11 == 0) goto L_0x003a
            java.lang.String r11 = ""
            if (r4 == 0) goto L_0x0015
            r4.close()     // Catch:{ IOException -> 0x001c }
        L_0x0015:
            if (r0 == 0) goto L_0x001a
            r0.close()     // Catch:{ IOException -> 0x001c }
        L_0x001a:
            r8 = r7
        L_0x001b:
            return r11
        L_0x001c:
            r2 = move-exception
            java.lang.String r12 = "DeviceDetectUtil"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "getGpuFreq file close IOException e:"
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.String r14 = r2.toString()
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.String r13 = r13.toString()
            com.tencent.component.utils.log.LogUtil.e(r12, r13)
            goto L_0x001a
        L_0x003a:
            java.io.FileReader r5 = new java.io.FileReader     // Catch:{ IOException -> 0x0172, IllegalArgumentException -> 0x00f2 }
            r5.<init>(r3)     // Catch:{ IOException -> 0x0172, IllegalArgumentException -> 0x00f2 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0175, IllegalArgumentException -> 0x016b, all -> 0x0164 }
            r11 = 512(0x200, float:7.175E-43)
            r1.<init>(r5, r11)     // Catch:{ IOException -> 0x0175, IllegalArgumentException -> 0x016b, all -> 0x0164 }
            java.lang.String r9 = ""
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            r10.<init>()     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
        L_0x004d:
            java.lang.String r9 = r1.readLine()     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            if (r9 == 0) goto L_0x0083
            r10.append(r9)     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            goto L_0x004d
        L_0x0057:
            r2 = move-exception
            r0 = r1
            r4 = r5
        L_0x005a:
            java.lang.String r11 = "readLine"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x013a }
            r12.<init>()     // Catch:{ all -> 0x013a }
            java.lang.String r13 = "IOException:"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x013a }
            java.lang.String r13 = r2.getMessage()     // Catch:{ all -> 0x013a }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x013a }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x013a }
            com.tencent.component.utils.log.LogUtil.e(r11, r12)     // Catch:{ all -> 0x013a }
            if (r4 == 0) goto L_0x007b
            r4.close()     // Catch:{ IOException -> 0x00d4 }
        L_0x007b:
            if (r0 == 0) goto L_0x0080
            r0.close()     // Catch:{ IOException -> 0x00d4 }
        L_0x0080:
            r8 = r7
            r11 = r7
            goto L_0x001b
        L_0x0083:
            java.lang.String r11 = r10.toString()     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            int r11 = java.lang.Integer.parseInt(r11)     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            r12 = 1000000(0xf4240, float:1.401298E-39)
            int r6 = r11 / r12
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            r11.<init>()     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            java.lang.String r12 = java.lang.Integer.toString(r6)     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            java.lang.String r12 = "MHz"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            java.lang.String r7 = r11.toString()     // Catch:{ IOException -> 0x0057, IllegalArgumentException -> 0x016e, all -> 0x0167 }
            if (r5 == 0) goto L_0x00ac
            r5.close()     // Catch:{ IOException -> 0x00b4 }
        L_0x00ac:
            if (r1 == 0) goto L_0x00b1
            r1.close()     // Catch:{ IOException -> 0x00b4 }
        L_0x00b1:
            r0 = r1
            r4 = r5
            goto L_0x0080
        L_0x00b4:
            r2 = move-exception
            java.lang.String r11 = "DeviceDetectUtil"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "getGpuFreq file close IOException e:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = r2.toString()
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            com.tencent.component.utils.log.LogUtil.e(r11, r12)
            r0 = r1
            r4 = r5
            goto L_0x0080
        L_0x00d4:
            r2 = move-exception
            java.lang.String r11 = "DeviceDetectUtil"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "getGpuFreq file close IOException e:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = r2.toString()
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            com.tencent.component.utils.log.LogUtil.e(r11, r12)
            goto L_0x0080
        L_0x00f2:
            r2 = move-exception
        L_0x00f3:
            java.lang.String r11 = "BufferedReader"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x013a }
            r12.<init>()     // Catch:{ all -> 0x013a }
            java.lang.String r13 = "IllegalArgumentException:"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x013a }
            java.lang.String r13 = r2.getMessage()     // Catch:{ all -> 0x013a }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x013a }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x013a }
            com.tencent.component.utils.log.LogUtil.e(r11, r12)     // Catch:{ all -> 0x013a }
            if (r4 == 0) goto L_0x0114
            r4.close()     // Catch:{ IOException -> 0x011b }
        L_0x0114:
            if (r0 == 0) goto L_0x0080
            r0.close()     // Catch:{ IOException -> 0x011b }
            goto L_0x0080
        L_0x011b:
            r2 = move-exception
            java.lang.String r11 = "DeviceDetectUtil"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "getGpuFreq file close IOException e:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = r2.toString()
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            com.tencent.component.utils.log.LogUtil.e(r11, r12)
            goto L_0x0080
        L_0x013a:
            r11 = move-exception
        L_0x013b:
            if (r4 == 0) goto L_0x0140
            r4.close()     // Catch:{ IOException -> 0x0146 }
        L_0x0140:
            if (r0 == 0) goto L_0x0145
            r0.close()     // Catch:{ IOException -> 0x0146 }
        L_0x0145:
            throw r11
        L_0x0146:
            r2 = move-exception
            java.lang.String r12 = "DeviceDetectUtil"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "getGpuFreq file close IOException e:"
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.String r14 = r2.toString()
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.String r13 = r13.toString()
            com.tencent.component.utils.log.LogUtil.e(r12, r13)
            goto L_0x0145
        L_0x0164:
            r11 = move-exception
            r4 = r5
            goto L_0x013b
        L_0x0167:
            r11 = move-exception
            r0 = r1
            r4 = r5
            goto L_0x013b
        L_0x016b:
            r2 = move-exception
            r4 = r5
            goto L_0x00f3
        L_0x016e:
            r2 = move-exception
            r0 = r1
            r4 = r5
            goto L_0x00f3
        L_0x0172:
            r2 = move-exception
            goto L_0x005a
        L_0x0175:
            r2 = move-exception
            r4 = r5
            goto L_0x005a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qqgamemi.util.DeviceDetectUtil.getGpuFreq():java.lang.String");
    }

    public static String getGpuFreqPath() {
        String gpuBasePath = "";
        String gpuFreqMaxPath = "";
        for (String s : FREQ_BASE_PATH) {
            if (fileExists(s)) {
                gpuBasePath = s;
            }
        }
        for (String s2 : FREQ_MAX_PATH) {
            if (fileExists(gpuBasePath + s2)) {
                gpuFreqMaxPath = gpuBasePath + s2;
            }
        }
        return gpuFreqMaxPath;
    }

    public static boolean fileExists(String fileName) {
        if (TextUtils.isEmpty(fileName) || TextUtils.equals("_", fileName)) {
            return false;
        }
        return new File(fileName).exists();
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        if (context == null || fileName == null) {
            return null;
        }
        try {
            InputStream is = context.getAssets().open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            LogUtil.e(TAG, ex.getMessage());
            return null;
        }
    }

    public static List<HashMap<String, String>> getListFromJsonString(Context context) {
        try {
            String jsonToString = loadJSONFromAsset(context, BLACK_MODEL_LIST);
            if (jsonToString == null) {
                return null;
            }
            JSONArray m_jArry = new JSONObject(jsonToString).getJSONArray("list");
            List<HashMap<String, String>> arrayList = new ArrayList<>();
            int i = 0;
            while (i < m_jArry.length()) {
                try {
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    String man_value = null;
                    String model_value = null;
                    if (jo_inside.has(LI_MAN)) {
                        man_value = jo_inside.getString(LI_MAN);
                    }
                    if (jo_inside.has(LI_MODEL)) {
                        model_value = jo_inside.getString(LI_MODEL);
                    }
                    if (!(man_value == null || model_value == null)) {
                        HashMap<String, String> m_li = new HashMap<>();
                        m_li.put(LI_MAN, man_value);
                        m_li.put(LI_MODEL, model_value);
                        arrayList.add(m_li);
                    }
                    i++;
                } catch (JSONException e) {
                    e = e;
                    List<HashMap<String, String>> list = arrayList;
                    LogUtil.e(TAG, e.getMessage());
                    return null;
                }
            }
            List<HashMap<String, String>> formList = arrayList;
            return arrayList;
        } catch (JSONException e2) {
            e = e2;
            LogUtil.e(TAG, e.getMessage());
            return null;
        }
    }

    public static boolean isBlackModel(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put(LI_MAN, Build.MANUFACTURER);
        map.put(LI_MODEL, Build.MODEL);
        List<HashMap<String, String>> list = getListFromJsonString(context);
        if (list == null) {
            return false;
        }
        boolean isBlack = list.contains(map);
        LogUtil.i(TAG, "isBlack:" + isBlack);
        return isBlack;
    }

    public static class Space {
        public static final double SIZE_GB = 1.073741824E9d;
        public static final double SIZE_KB = 1024.0d;
        public static final double SIZE_MB = 1048576.0d;

        public static boolean isExternalAvailable() {
            return "mounted".equals(Environment.getExternalStorageState());
        }

        public static long getExternalAvailableSpaceInBytes() {
            try {
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                return ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        public static double getExternalAvailableSpaceInKB() {
            return new BigDecimal(((double) getExternalAvailableSpaceInBytes()) / 1024.0d).setScale(2, 4).doubleValue();
        }

        public static double getExternalAvailableSpaceInMB() {
            return getFileSize(getExternalAvailableSpaceInBytes());
        }

        public static double getExternalAvailableSpaceInGB() {
            return new BigDecimal(((double) getExternalAvailableSpaceInBytes()) / 1.073741824E9d).setScale(2, 4).doubleValue();
        }

        public static long getExternalStorageAvailableBlocks() {
            try {
                return (long) new StatFs(Environment.getExternalStorageDirectory().getPath()).getAvailableBlocks();
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        public static double getFileSize(long size) {
            return new BigDecimal(((double) size) / 1048576.0d).setScale(2, 4).doubleValue();
        }
    }
}
