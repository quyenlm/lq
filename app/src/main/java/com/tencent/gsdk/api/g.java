package com.tencent.gsdk.api;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.gsdk.utils.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: SystemInfo */
public class g {
    public static int a() {
        if (Build.VERSION.SDK_INT >= 17) {
            return Runtime.getRuntime().availableProcessors();
        }
        return m();
    }

    /* compiled from: SystemInfo */
    class a implements FileFilter {
        a() {
        }

        public boolean accept(File file) {
            if (Pattern.matches("cpu[0-9]+", file.getName())) {
                return true;
            }
            return false;
        }
    }

    private static int m() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new a()).length;
        } catch (Exception e) {
            Logger.w("getNumCoresOldPhones error:" + e.getMessage());
            return 1;
        }
    }

    public static String b() {
        List<Double> a2 = a(a());
        double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        if (a2 != null && a2.size() != 0) {
            double doubleValue = a2.get(0).doubleValue();
            Iterator<Double> it = a2.iterator();
            while (true) {
                d = doubleValue;
                if (!it.hasNext()) {
                    break;
                }
                Double next = it.next();
                if (next.doubleValue() > d) {
                    doubleValue = next.doubleValue();
                } else {
                    doubleValue = d;
                }
            }
        }
        return String.valueOf(d);
    }

    private static List<Double> a(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            String str = "0";
            InputStream inputStream = null;
            try {
                InputStream inputStream2 = new ProcessBuilder(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu" + i2 + "/cpufreq/cpuinfo_max_freq"}).start().getInputStream();
                byte[] bArr = new byte[24];
                while (inputStream2.read(bArr) != -1) {
                    str = str + new String(bArr);
                }
                inputStream2.close();
                if (!(str == null || str.trim() == null)) {
                    arrayList.add(Double.valueOf(Double.parseDouble(str.trim())));
                }
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (IOException e) {
                        Logger.w("getCPUFreq close input stream error:" + e.getMessage());
                    }
                }
            } catch (IOException e2) {
                Logger.w("getCPUFreq error:" + e2.getMessage());
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        Logger.w("getCPUFreq close input stream error:" + e3.getMessage());
                    }
                }
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        Logger.w("getCPUFreq close input stream error:" + e4.getMessage());
                    }
                }
                throw th;
            }
        }
        return arrayList;
    }

    public static Map<String, String> c() {
        HashMap hashMap = new HashMap();
        try {
            Scanner scanner = new Scanner(new File("/proc/cpuinfo"));
            while (scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(": ");
                if (split.length > 1) {
                    hashMap.put(split[0].trim(), split[1].trim());
                }
            }
        } catch (Exception e) {
            Log.e("getCpuInfo", Log.getStackTraceString(e));
        }
        return hashMap;
    }

    public static float d() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/proc/stat", "r");
            String[] split = randomAccessFile.readLine().split(" ");
            String str = split[2];
            String str2 = split[3];
            String str3 = split[4];
            String str4 = split[5];
            String str5 = split[6];
            String str6 = split[7];
            String str7 = split[8];
            if (str == null || str.equals("")) {
                str = "0";
            } else if (str2 == null || str2.equals("")) {
                str2 = "0";
            } else if (str3 == null || str3.equals("")) {
                str3 = "0";
            } else if (str4 == null || str4.equals("")) {
                str4 = "0";
            } else if (str5 == null || str5.equals("")) {
                str5 = "0";
            } else if (str6 == null || str6.equals("")) {
                str6 = "0";
            } else if (str7 == null || str7.equals("")) {
                str7 = "0";
            }
            long parseLong = Long.parseLong(str4);
            long parseLong2 = Long.parseLong(str);
            long parseLong3 = Long.parseLong(str3);
            long parseLong4 = Long.parseLong(str6) + parseLong3 + Long.parseLong(str2) + parseLong2 + Long.parseLong(str5) + Long.parseLong(str7);
            try {
                Thread.sleep(300);
            } catch (Exception e) {
            }
            randomAccessFile.seek(0);
            String readLine = randomAccessFile.readLine();
            randomAccessFile.close();
            String[] split2 = readLine.split(" ");
            String str8 = split2[2];
            String str9 = split2[3];
            String str10 = split2[4];
            String str11 = split2[5];
            String str12 = split2[6];
            String str13 = split2[7];
            String str14 = split2[8];
            if (str8 == null || str8.equals("")) {
                str8 = "0";
            } else if (str9 == null || str9.equals("")) {
                str9 = "0";
            } else if (str10 == null || str10.equals("")) {
                str10 = "0";
            } else if (str11 == null || str11.equals("")) {
                str11 = "0";
            } else if (str12 == null || str12.equals("")) {
                str12 = "0";
            } else if (str13 == null || str13.equals("")) {
                str13 = "0";
            } else if (str14 == null || str14.equals("")) {
                str14 = "0";
            }
            long parseLong5 = Long.parseLong(str11);
            long parseLong6 = Long.parseLong(str8);
            long parseLong7 = Long.parseLong(str10);
            long parseLong8 = Long.parseLong(str13) + parseLong7 + Long.parseLong(str9) + parseLong6 + Long.parseLong(str12) + Long.parseLong(str14);
            if (0 == (parseLong8 + parseLong5) - (parseLong4 + parseLong)) {
                return 0.0f;
            }
            return (100.0f * ((float) (parseLong8 - parseLong4))) / ((float) ((parseLong8 + parseLong5) - (parseLong4 + parseLong)));
        } catch (Exception e2) {
            Logger.w("getTotalCPURate error:" + e2.getMessage());
            return 0.0f;
        }
    }

    public static float e() {
        float f = (float) f();
        float g = (float) g();
        try {
            Thread.sleep(300);
        } catch (Exception e) {
        }
        float f2 = (float) f();
        float g2 = (float) g();
        if (0.0f == f2 - f) {
            return 0.0f;
        }
        return (100.0f * (g2 - g)) / (f2 - f);
    }

    public static long f() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            String[] split = readLine.split(" ");
            if (split == null) {
                return 0;
            }
            String str = split[2];
            String str2 = split[3];
            String str3 = split[4];
            String str4 = split[5];
            String str5 = split[6];
            String str6 = split[7];
            String str7 = split[8];
            if (str == null || str.equals("")) {
                str = "0";
            } else if (str2 == null || str2.equals("")) {
                str2 = "0";
            } else if (str3 == null || str3.equals("")) {
                str3 = "0";
            } else if (str4 == null || str4.equals("")) {
                str4 = "0";
            } else if (str5 == null || str5.equals("")) {
                str5 = "0";
            } else if (str6 == null || str6.equals("")) {
                str6 = "0";
            } else if (str7 == null || str7.equals("")) {
                str7 = "0";
            }
            long parseLong = Long.parseLong(str3);
            long parseLong2 = Long.parseLong(str5);
            return Long.parseLong(str7) + parseLong2 + parseLong + Long.parseLong(str) + Long.parseLong(str2) + Long.parseLong(str4) + Long.parseLong(str6);
        } catch (Exception e) {
            Logger.w("getTotalCpuTime error:" + e.getMessage());
            return 0;
        }
    }

    public static long g() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + Process.myPid() + "/stat")), 1000);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            String[] split = readLine.split(" ");
            if (split == null) {
                return 0;
            }
            String str = split[13];
            String str2 = split[14];
            String str3 = split[15];
            String str4 = split[16];
            if (str == null || str.equals("")) {
                str = "0";
            } else if (str2 == null || str2.equals("")) {
                str2 = "0";
            } else if (str3 == null || str3.equals("")) {
                str3 = "0";
            } else if (str4 == null || str4.equals("")) {
                str4 = "0";
            }
            long parseLong = Long.parseLong(str);
            return Long.parseLong(str4) + Long.parseLong(str2) + parseLong + Long.parseLong(str3);
        } catch (IOException e) {
            Logger.w("getAppCpuTime error:" + e.getMessage());
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b2 A[SYNTHETIC, Splitter:B:41:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00d6 A[SYNTHETIC, Splitter:B:48:0x00d6] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long h() {
        /*
            r12 = 100
            r4 = 0
            r10 = 128(0x80, float:1.794E-43)
            r2 = 0
            r8 = 1000(0x3e8, double:4.94E-321)
            r0 = -1
            byte[] r5 = new byte[r10]
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0094, all -> 0x00d2 }
            java.lang.String r6 = "/sys/devices/virtual/thermal/thermal_zone0/temp"
            r3.<init>(r6)     // Catch:{ Exception -> 0x0094, all -> 0x00d2 }
            int r6 = r3.read(r5)     // Catch:{ Exception -> 0x00f8 }
            if (r6 <= 0) goto L_0x0029
        L_0x0019:
            if (r2 >= r6) goto L_0x0023
            if (r2 >= r10) goto L_0x0023
            byte r4 = r5[r2]     // Catch:{ Exception -> 0x00f8 }
            r7 = 10
            if (r4 != r7) goto L_0x0056
        L_0x0023:
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x00f8 }
            r6 = 0
            r4.<init>(r5, r6, r2)     // Catch:{ Exception -> 0x00f8 }
        L_0x0029:
            if (r4 == 0) goto L_0x0050
            long r0 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x00f8 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f8 }
            r2.<init>()     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r4 = "temp a is:"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x00f8 }
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00f8 }
            com.tencent.gsdk.utils.Logger.i(r2)     // Catch:{ Exception -> 0x00f8 }
            int r2 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1))
            if (r2 < 0) goto L_0x0059
            int r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x0059
            r4 = 10
            long r0 = r0 / r4
        L_0x0050:
            if (r3 == 0) goto L_0x0055
            r3.close()     // Catch:{ IOException -> 0x0078 }
        L_0x0055:
            return r0
        L_0x0056:
            int r2 = r2 + 1
            goto L_0x0019
        L_0x0059:
            int r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r2 < 0) goto L_0x0067
            r4 = 10000(0x2710, double:4.9407E-320)
            int r2 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x0067
            r4 = 100
            long r0 = r0 / r4
            goto L_0x0050
        L_0x0067:
            r4 = 10000(0x2710, double:4.9407E-320)
            int r2 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x0050
            r4 = 100000(0x186a0, double:4.94066E-319)
            int r2 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x0050
            r4 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 / r4
            goto L_0x0050
        L_0x0078:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "close stream error:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r2 = r2.getMessage()
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            com.tencent.gsdk.utils.Logger.e(r2)
            goto L_0x0055
        L_0x0094:
            r2 = move-exception
            r3 = r4
        L_0x0096:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f6 }
            r4.<init>()     // Catch:{ all -> 0x00f6 }
            java.lang.String r5 = "getCPUTemperature read file error:"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00f6 }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x00f6 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x00f6 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00f6 }
            com.tencent.gsdk.utils.Logger.e(r2)     // Catch:{ all -> 0x00f6 }
            if (r3 == 0) goto L_0x0055
            r3.close()     // Catch:{ IOException -> 0x00b6 }
            goto L_0x0055
        L_0x00b6:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "close stream error:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r2 = r2.getMessage()
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            com.tencent.gsdk.utils.Logger.e(r2)
            goto L_0x0055
        L_0x00d2:
            r0 = move-exception
            r3 = r4
        L_0x00d4:
            if (r3 == 0) goto L_0x00d9
            r3.close()     // Catch:{ IOException -> 0x00da }
        L_0x00d9:
            throw r0
        L_0x00da:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "close stream error:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.e(r1)
            goto L_0x00d9
        L_0x00f6:
            r0 = move-exception
            goto L_0x00d4
        L_0x00f8:
            r2 = move-exception
            goto L_0x0096
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.gsdk.api.g.h():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x009e A[SYNTHETIC, Splitter:B:41:0x009e] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00c2 A[SYNTHETIC, Splitter:B:48:0x00c2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long i() {
        /*
            r12 = 100
            r4 = 0
            r10 = 128(0x80, float:1.794E-43)
            r2 = 0
            r8 = 1000(0x3e8, double:4.94E-321)
            r0 = -1
            r3 = 128(0x80, float:1.794E-43)
            byte[] r5 = new byte[r3]     // Catch:{ Exception -> 0x0080, all -> 0x00be }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0080, all -> 0x00be }
            java.lang.String r6 = "/sys/class/thermal/thermal_zone10/temp"
            r3.<init>(r6)     // Catch:{ Exception -> 0x0080, all -> 0x00be }
            int r6 = r3.read(r5)     // Catch:{ Exception -> 0x00e4 }
            if (r6 <= 0) goto L_0x002b
        L_0x001b:
            if (r2 >= r6) goto L_0x0025
            if (r2 >= r10) goto L_0x0025
            byte r4 = r5[r2]     // Catch:{ Exception -> 0x00e4 }
            r7 = 10
            if (r4 != r7) goto L_0x0042
        L_0x0025:
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x00e4 }
            r6 = 0
            r4.<init>(r5, r6, r2)     // Catch:{ Exception -> 0x00e4 }
        L_0x002b:
            if (r4 == 0) goto L_0x003c
            long r0 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x00e4 }
            int r2 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1))
            if (r2 < 0) goto L_0x0045
            int r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x0045
            r4 = 10
            long r0 = r0 / r4
        L_0x003c:
            if (r3 == 0) goto L_0x0041
            r3.close()     // Catch:{ IOException -> 0x0064 }
        L_0x0041:
            return r0
        L_0x0042:
            int r2 = r2 + 1
            goto L_0x001b
        L_0x0045:
            int r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r2 < 0) goto L_0x0053
            r4 = 10000(0x2710, double:4.9407E-320)
            int r2 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x0053
            r4 = 100
            long r0 = r0 / r4
            goto L_0x003c
        L_0x0053:
            r4 = 10000(0x2710, double:4.9407E-320)
            int r2 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x003c
            r4 = 100000(0x186a0, double:4.94066E-319)
            int r2 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x003c
            r4 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 / r4
            goto L_0x003c
        L_0x0064:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "close stream error:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r2 = r2.getMessage()
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            com.tencent.gsdk.utils.Logger.e(r2)
            goto L_0x0041
        L_0x0080:
            r2 = move-exception
            r3 = r4
        L_0x0082:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e2 }
            r4.<init>()     // Catch:{ all -> 0x00e2 }
            java.lang.String r5 = "getGPUTemperature read file error:"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00e2 }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x00e2 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x00e2 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00e2 }
            com.tencent.gsdk.utils.Logger.e(r2)     // Catch:{ all -> 0x00e2 }
            if (r3 == 0) goto L_0x0041
            r3.close()     // Catch:{ IOException -> 0x00a2 }
            goto L_0x0041
        L_0x00a2:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "close stream error:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r2 = r2.getMessage()
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            com.tencent.gsdk.utils.Logger.e(r2)
            goto L_0x0041
        L_0x00be:
            r0 = move-exception
            r3 = r4
        L_0x00c0:
            if (r3 == 0) goto L_0x00c5
            r3.close()     // Catch:{ IOException -> 0x00c6 }
        L_0x00c5:
            throw r0
        L_0x00c6:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "close stream error:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.e(r1)
            goto L_0x00c5
        L_0x00e2:
            r0 = move-exception
            goto L_0x00c0
        L_0x00e4:
            r2 = move-exception
            goto L_0x0082
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.gsdk.api.g.i():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b5 A[SYNTHETIC, Splitter:B:30:0x00b5] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00c1 A[SYNTHETIC, Splitter:B:38:0x00c1] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00cd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int j() {
        /*
            r1 = -1
            r0 = 0
            r3 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            java.lang.String r4 = "/sys/class/kgsl/kgsl-3d0"
            r2.<init>(r4)     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            if (r2 == 0) goto L_0x0050
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            java.lang.String r4 = "/sys/class/kgsl/kgsl-3d0/gpubusy"
            java.lang.String r5 = "r"
            r2.<init>(r4, r5)     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            java.lang.String r3 = r2.readLine()     // Catch:{ Exception -> 0x00cb }
            if (r3 == 0) goto L_0x004a
            java.lang.String r3 = r3.trim()     // Catch:{ Exception -> 0x00cb }
            java.lang.String r4 = "\\s+"
            java.lang.String[] r3 = r3.split(r4)     // Catch:{ Exception -> 0x00cb }
            r4 = 0
            r4 = r3[r4]     // Catch:{ Exception -> 0x00cb }
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x00cb }
            long r4 = (long) r4     // Catch:{ Exception -> 0x00cb }
            r6 = 1
            r3 = r3[r6]     // Catch:{ Exception -> 0x00cb }
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ Exception -> 0x00cb }
            long r6 = (long) r3     // Catch:{ Exception -> 0x00cb }
            r8 = 0
            int r3 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r3 == 0) goto L_0x004a
            double r4 = (double) r4     // Catch:{ Exception -> 0x00cb }
            double r6 = (double) r6     // Catch:{ Exception -> 0x00cb }
            double r4 = r4 / r6
            r6 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r4 = r4 * r6
            long r0 = java.lang.Math.round(r4)     // Catch:{ Exception -> 0x00cb }
            int r0 = (int) r0
        L_0x004a:
            if (r2 == 0) goto L_0x004f
            r2.close()     // Catch:{ IOException -> 0x00c5 }
        L_0x004f:
            return r0
        L_0x0050:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            java.lang.String r4 = "/proc/mali/utilization"
            r2.<init>(r4)     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            if (r2 == 0) goto L_0x00cf
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            java.lang.String r0 = "/proc/mali/utilization"
            java.lang.String r4 = "r"
            r2.<init>(r0, r4)     // Catch:{ Exception -> 0x0097, all -> 0x00bd }
            java.lang.String r0 = r2.readLine()     // Catch:{ Exception -> 0x00cb }
            if (r0 == 0) goto L_0x0090
            java.lang.String r3 = "="
            int r3 = r0.indexOf(r3)     // Catch:{ Exception -> 0x00cb }
            int r3 = r3 + 1
            int r4 = r0.length()     // Catch:{ Exception -> 0x00cb }
            java.lang.String r0 = r0.substring(r3, r4)     // Catch:{ Exception -> 0x00cb }
            r3 = 0
            java.lang.String r4 = "/"
            int r4 = r0.indexOf(r4)     // Catch:{ Exception -> 0x00cb }
            java.lang.String r0 = r0.substring(r3, r4)     // Catch:{ Exception -> 0x00cb }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x00cb }
            int r0 = r0.intValue()     // Catch:{ Exception -> 0x00cb }
            goto L_0x004a
        L_0x0090:
            java.lang.String r0 = "get gpu usage failed"
            com.tencent.gsdk.utils.Logger.w(r0)     // Catch:{ Exception -> 0x00cb }
            r0 = r1
            goto L_0x004a
        L_0x0097:
            r0 = move-exception
            r2 = r3
        L_0x0099:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
            r3.<init>()     // Catch:{ all -> 0x00c9 }
            java.lang.String r4 = "get gpu usage error: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00c9 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00c9 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x00c9 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00c9 }
            com.tencent.gsdk.utils.Logger.e(r0)     // Catch:{ all -> 0x00c9 }
            if (r2 == 0) goto L_0x00cd
            r2.close()     // Catch:{ IOException -> 0x00ba }
            r0 = r1
            goto L_0x004f
        L_0x00ba:
            r0 = move-exception
            r0 = r1
            goto L_0x004f
        L_0x00bd:
            r0 = move-exception
            r2 = r3
        L_0x00bf:
            if (r2 == 0) goto L_0x00c4
            r2.close()     // Catch:{ IOException -> 0x00c7 }
        L_0x00c4:
            throw r0
        L_0x00c5:
            r1 = move-exception
            goto L_0x004f
        L_0x00c7:
            r1 = move-exception
            goto L_0x00c4
        L_0x00c9:
            r0 = move-exception
            goto L_0x00bf
        L_0x00cb:
            r0 = move-exception
            goto L_0x0099
        L_0x00cd:
            r0 = r1
            goto L_0x004f
        L_0x00cf:
            r2 = r3
            goto L_0x004a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.gsdk.api.g.j():int");
    }

    @SuppressLint({"NewApi"})
    public static long a(Context context) {
        if (context == null) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
            return memoryInfo.totalMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/proc/meminfo", "r");
            Matcher matcher = Pattern.compile("(\\d+)").matcher(randomAccessFile.readLine());
            String str = "";
            while (matcher.find()) {
                str = matcher.group(1);
                Logger.d("Ram : " + str);
            }
            randomAccessFile.close();
            if (str == null) {
                str = "0";
            }
            return Long.parseLong(str) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        } catch (IOException e) {
            Logger.w("getTotalMemory error:" + e.getMessage());
            return -1;
        }
    }

    public static long b(Context context) {
        long j;
        if (context == null) {
            return -1;
        }
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            if (activityManager == null || memoryInfo == null) {
                return -1;
            }
            activityManager.getMemoryInfo(memoryInfo);
            j = memoryInfo.availMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
            return j;
        } catch (Exception e) {
            Logger.e("Get available memory error:" + e.getMessage());
            j = -1;
        }
    }

    public static long c(Context context) {
        long j;
        Debug.MemoryInfo[] processMemoryInfo;
        if (context == null) {
            return -1;
        }
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int[] iArr = {Process.myPid()};
            if (activityManager == null || (processMemoryInfo = activityManager.getProcessMemoryInfo(iArr)) == null || processMemoryInfo[0] == null) {
                return -1;
            }
            j = ((long) processMemoryInfo[0].getTotalPss()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            return j;
        } catch (Exception e) {
            Logger.e("Get process Memory error:" + e.getMessage());
            j = -1;
        }
    }

    public static long k() {
        int myUid = Process.myUid();
        return TrafficStats.getUidTxBytes(myUid) + TrafficStats.getUidRxBytes(myUid);
    }

    public static float d(Context context) {
        if (context == null) {
            return -1.0f;
        }
        try {
            int intExtra = context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("temperature", 0);
            if (intExtra != 0) {
                return (float) (intExtra / 10);
            }
            return -1.0f;
        } catch (Exception e) {
            Logger.w("getBatteryTemperature error:" + e.getMessage());
            return -1.0f;
        }
    }

    public static String l() {
        int i;
        int i2;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) GSDKSystem.getInstance().a().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            i = displayMetrics.heightPixels;
            try {
                i2 = displayMetrics.widthPixels;
            } catch (Exception e) {
                e = e;
                Logger.e("getScreenResolution error:" + e.getMessage());
                i2 = -1;
                return i + "x" + i2;
            }
        } catch (Exception e2) {
            e = e2;
            i = -1;
            Logger.e("getScreenResolution error:" + e.getMessage());
            i2 = -1;
            return i + "x" + i2;
        }
        return i + "x" + i2;
    }

    public static long e(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            return new File(context.getFilesDir().getAbsoluteFile().toString()).getTotalSpace() / 1000000;
        } catch (Exception e) {
            Logger.w("getTotalExternalStorage error:" + e.getMessage());
            return -1;
        }
    }

    public static long f(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            return new File(context.getFilesDir().getAbsoluteFile().toString()).getFreeSpace() / 1000000;
        } catch (Exception e) {
            Logger.w("getFreeExternalStorage error:" + e.getMessage());
            return -1;
        }
    }
}
