package com.tencent.bugly.crashreport.common.info;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import com.tencent.tp.a.h;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: BUGLY */
public class b {
    private static final String[] a = {"/su", "/su/bin/su", "/sbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su", "/system/xbin/su", "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr", "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd", "/system/bin/conbb", "/system/xbin/conbb"};
    private static final String[] b = {"com.ami.duosupdater.ui", "com.ami.launchmetro", "com.ami.syncduosservices", "com.bluestacks.home", "com.bluestacks.windowsfilemanager", "com.bluestacks.settings", "com.bluestacks.bluestackslocationprovider", "com.bluestacks.appsettings", "com.bluestacks.bstfolder", "com.bluestacks.BstCommandProcessor", "com.bluestacks.s2p", "com.bluestacks.setup", "com.kaopu001.tiantianserver", "com.kpzs.helpercenter", "com.kaopu001.tiantianime", "com.android.development_settings", "com.android.development", "com.android.customlocale2", "com.genymotion.superuser", "com.genymotion.clipboardproxy", "com.uc.xxzs.keyboard", "com.uc.xxzs", "com.blue.huang17.agent", "com.blue.huang17.launcher", "com.blue.huang17.ime", "com.microvirt.guide", "com.microvirt.market", "com.microvirt.memuime", "cn.itools.vm.launcher", "cn.itools.vm.proxy", "cn.itools.vm.softkeyboard", "cn.itools.avdmarket", "com.syd.IME", "com.bignox.app.store.hd", "com.bignox.launcher", "com.bignox.app.phone", "com.bignox.app.noxservice", "com.android.noxpush", "com.haimawan.push", "me.haima.helpcenter", "com.windroy.launcher", "com.windroy.superuser", "com.windroy.launcher", "com.windroy.ime", "com.android.flysilkworm", "com.android.emu.inputservice", "com.tiantian.ime", "com.microvirt.launcher", "me.le8.androidassist", "com.vphone.helper", "com.vphone.launcher", "com.duoyi.giftcenter.giftcenter"};
    private static final String[] c = {"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/qemud", "/dev/qemu_pipe", "/dev/socket/baseband_genyd", "/dev/socket/genyd"};
    private static String d = null;
    private static String e = null;

    public static String a() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static String b() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static int c() {
        try {
            return Build.VERSION.SDK_INT;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    public static String d() {
        return Constants.NULL_VERSION_ID;
    }

    public static String e() {
        return Constants.NULL_VERSION_ID;
    }

    public static String a(Context context) {
        if (context == null) {
            return "fail";
        }
        try {
            String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            if (string == null) {
                return Constants.NULL_VERSION_ID;
            }
            return string.toLowerCase();
        } catch (Throwable th) {
            if (x.a(th)) {
                return "fail";
            }
            x.a("Failed to get Android ID.", new Object[0]);
            return "fail";
        }
    }

    public static String f() {
        return Constants.NULL_VERSION_ID;
    }

    public static String b(Context context) {
        String str;
        if (context == null) {
            return "fail";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                str = telephonyManager.getSimSerialNumber();
                if (str == null) {
                    str = Constants.NULL_VERSION_ID;
                }
            } else {
                str = "fail";
            }
        } catch (Throwable th) {
            str = "fail";
            x.a("Failed to get SIM serial number.", new Object[0]);
        }
        return str;
    }

    public static String g() {
        try {
            return Build.SERIAL;
        } catch (Throwable th) {
            x.a("Failed to get hardware serial number.", new Object[0]);
            return "fail";
        }
    }

    private static boolean t() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    public static String a(Context context, boolean z) {
        String str = null;
        if (z) {
            try {
                String a2 = z.a(context, "ro.product.cpu.abilist");
                if (z.a(a2) || a2.equals("fail")) {
                    a2 = z.a(context, "ro.product.cpu.abi");
                }
                if (!z.a(a2) && !a2.equals("fail")) {
                    x.b(b.class, "ABI list: " + a2, new Object[0]);
                    str = a2.split(",")[0];
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                return "fail";
            }
        }
        if (str == null) {
            str = System.getProperty("os.arch");
        }
        return str;
    }

    public static long h() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (x.a(th)) {
                return -1;
            }
            th.printStackTrace();
            return -1;
        }
    }

    public static long i() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (x.a(th)) {
                return -1;
            }
            th.printStackTrace();
            return -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x007b A[Catch:{ all -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0080 A[SYNTHETIC, Splitter:B:44:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0085 A[SYNTHETIC, Splitter:B:47:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00a6 A[SYNTHETIC, Splitter:B:61:0x00a6] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00ab A[SYNTHETIC, Splitter:B:64:0x00ab] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long j() {
        /*
            r3 = 0
            java.lang.String r0 = "/proc/meminfo"
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x0072, all -> 0x00a1 }
            r4.<init>(r0)     // Catch:{ Throwable -> 0x0072, all -> 0x00a1 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00cd, all -> 0x00c5 }
            r0 = 2048(0x800, float:2.87E-42)
            r2.<init>(r4, r0)     // Catch:{ Throwable -> 0x00cd, all -> 0x00c5 }
            java.lang.String r0 = r2.readLine()     // Catch:{ Throwable -> 0x00d0, all -> 0x00c8 }
            if (r0 != 0) goto L_0x0034
            r2.close()     // Catch:{ IOException -> 0x001e }
        L_0x0018:
            r4.close()     // Catch:{ IOException -> 0x0029 }
        L_0x001b:
            r0 = -1
        L_0x001d:
            return r0
        L_0x001e:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0018
            r0.printStackTrace()
            goto L_0x0018
        L_0x0029:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x001b
            r0.printStackTrace()
            goto L_0x001b
        L_0x0034:
            java.lang.String r1 = ":\\s+"
            r3 = 2
            java.lang.String[] r0 = r0.split(r1, r3)     // Catch:{ Throwable -> 0x00d0, all -> 0x00c8 }
            r1 = 1
            r0 = r0[r1]     // Catch:{ Throwable -> 0x00d0, all -> 0x00c8 }
            java.lang.String r0 = r0.toLowerCase()     // Catch:{ Throwable -> 0x00d0, all -> 0x00c8 }
            java.lang.String r1 = "kb"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.replace(r1, r3)     // Catch:{ Throwable -> 0x00d0, all -> 0x00c8 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x00d0, all -> 0x00c8 }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ Throwable -> 0x00d0, all -> 0x00c8 }
            r3 = 10
            long r0 = r0 << r3
            r2.close()     // Catch:{ IOException -> 0x0067 }
        L_0x0058:
            r4.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x001d
        L_0x005c:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x001d
            r2.printStackTrace()
            goto L_0x001d
        L_0x0067:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x0058
            r2.printStackTrace()
            goto L_0x0058
        L_0x0072:
            r0 = move-exception
            r1 = r3
            r4 = r3
        L_0x0075:
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x00ca }
            if (r2 != 0) goto L_0x007e
            r0.printStackTrace()     // Catch:{ all -> 0x00ca }
        L_0x007e:
            if (r1 == 0) goto L_0x0083
            r1.close()     // Catch:{ IOException -> 0x008b }
        L_0x0083:
            if (r4 == 0) goto L_0x0088
            r4.close()     // Catch:{ IOException -> 0x0096 }
        L_0x0088:
            r0 = -2
            goto L_0x001d
        L_0x008b:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0083
            r0.printStackTrace()
            goto L_0x0083
        L_0x0096:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0088
            r0.printStackTrace()
            goto L_0x0088
        L_0x00a1:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x00a4:
            if (r2 == 0) goto L_0x00a9
            r2.close()     // Catch:{ IOException -> 0x00af }
        L_0x00a9:
            if (r4 == 0) goto L_0x00ae
            r4.close()     // Catch:{ IOException -> 0x00ba }
        L_0x00ae:
            throw r0
        L_0x00af:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)
            if (r2 != 0) goto L_0x00a9
            r1.printStackTrace()
            goto L_0x00a9
        L_0x00ba:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)
            if (r2 != 0) goto L_0x00ae
            r1.printStackTrace()
            goto L_0x00ae
        L_0x00c5:
            r0 = move-exception
            r2 = r3
            goto L_0x00a4
        L_0x00c8:
            r0 = move-exception
            goto L_0x00a4
        L_0x00ca:
            r0 = move-exception
            r2 = r1
            goto L_0x00a4
        L_0x00cd:
            r0 = move-exception
            r1 = r3
            goto L_0x0075
        L_0x00d0:
            r0 = move-exception
            r1 = r2
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.j():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:73:0x010b A[Catch:{ all -> 0x015b }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0110 A[SYNTHETIC, Splitter:B:75:0x0110] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0115 A[SYNTHETIC, Splitter:B:78:0x0115] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0137 A[SYNTHETIC, Splitter:B:92:0x0137] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x013c A[SYNTHETIC, Splitter:B:95:0x013c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long k() {
        /*
            r3 = 0
            r0 = -1
            r10 = 10
            java.lang.String r2 = "/proc/meminfo"
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x0102, all -> 0x0132 }
            r4.<init>(r2)     // Catch:{ Throwable -> 0x0102, all -> 0x0132 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x015e, all -> 0x0156 }
            r5 = 2048(0x800, float:2.87E-42)
            r2.<init>(r4, r5)     // Catch:{ Throwable -> 0x015e, all -> 0x0156 }
            r2.readLine()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r3 = r2.readLine()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            if (r3 != 0) goto L_0x0039
            r2.close()     // Catch:{ IOException -> 0x0023 }
        L_0x001f:
            r4.close()     // Catch:{ IOException -> 0x002e }
        L_0x0022:
            return r0
        L_0x0023:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x001f
            r2.printStackTrace()
            goto L_0x001f
        L_0x002e:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x0022
            r2.printStackTrace()
            goto L_0x0022
        L_0x0039:
            java.lang.String r5 = ":\\s+"
            r6 = 2
            java.lang.String[] r3 = r3.split(r5, r6)     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            r5 = 1
            r3 = r3[r5]     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r3 = r3.toLowerCase()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r5 = "kb"
            java.lang.String r6 = ""
            java.lang.String r3 = r3.replace(r5, r6)     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r3 = r3.trim()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            r6 = 0
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            long r8 = r8 << r10
            long r6 = r6 + r8
            java.lang.String r3 = r2.readLine()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            if (r3 != 0) goto L_0x007e
            r2.close()     // Catch:{ IOException -> 0x0073 }
        L_0x0064:
            r4.close()     // Catch:{ IOException -> 0x0068 }
            goto L_0x0022
        L_0x0068:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x0022
            r2.printStackTrace()
            goto L_0x0022
        L_0x0073:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x0064
            r2.printStackTrace()
            goto L_0x0064
        L_0x007e:
            java.lang.String r5 = ":\\s+"
            r8 = 2
            java.lang.String[] r3 = r3.split(r5, r8)     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            r5 = 1
            r3 = r3[r5]     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r3 = r3.toLowerCase()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r5 = "kb"
            java.lang.String r8 = ""
            java.lang.String r3 = r3.replace(r5, r8)     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r3 = r3.trim()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            long r8 = r8 << r10
            long r6 = r6 + r8
            java.lang.String r3 = r2.readLine()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            if (r3 != 0) goto L_0x00c3
            r2.close()     // Catch:{ IOException -> 0x00b8 }
        L_0x00a7:
            r4.close()     // Catch:{ IOException -> 0x00ac }
            goto L_0x0022
        L_0x00ac:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x0022
            r2.printStackTrace()
            goto L_0x0022
        L_0x00b8:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x00a7
            r2.printStackTrace()
            goto L_0x00a7
        L_0x00c3:
            java.lang.String r0 = ":\\s+"
            r1 = 2
            java.lang.String[] r0 = r3.split(r0, r1)     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            r1 = 1
            r0 = r0[r1]     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r0 = r0.toLowerCase()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r1 = "kb"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.replace(r1, r3)     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ Throwable -> 0x0161, all -> 0x0159 }
            long r0 = r0 << r10
            long r0 = r0 + r6
            r2.close()     // Catch:{ IOException -> 0x00f7 }
        L_0x00e6:
            r4.close()     // Catch:{ IOException -> 0x00eb }
            goto L_0x0022
        L_0x00eb:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x0022
            r2.printStackTrace()
            goto L_0x0022
        L_0x00f7:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r2)
            if (r3 != 0) goto L_0x00e6
            r2.printStackTrace()
            goto L_0x00e6
        L_0x0102:
            r0 = move-exception
            r1 = r3
            r4 = r3
        L_0x0105:
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x015b }
            if (r2 != 0) goto L_0x010e
            r0.printStackTrace()     // Catch:{ all -> 0x015b }
        L_0x010e:
            if (r1 == 0) goto L_0x0113
            r1.close()     // Catch:{ IOException -> 0x011c }
        L_0x0113:
            if (r4 == 0) goto L_0x0118
            r4.close()     // Catch:{ IOException -> 0x0127 }
        L_0x0118:
            r0 = -2
            goto L_0x0022
        L_0x011c:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0113
            r0.printStackTrace()
            goto L_0x0113
        L_0x0127:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0118
            r0.printStackTrace()
            goto L_0x0118
        L_0x0132:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x0135:
            if (r2 == 0) goto L_0x013a
            r2.close()     // Catch:{ IOException -> 0x0140 }
        L_0x013a:
            if (r4 == 0) goto L_0x013f
            r4.close()     // Catch:{ IOException -> 0x014b }
        L_0x013f:
            throw r0
        L_0x0140:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)
            if (r2 != 0) goto L_0x013a
            r1.printStackTrace()
            goto L_0x013a
        L_0x014b:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)
            if (r2 != 0) goto L_0x013f
            r1.printStackTrace()
            goto L_0x013f
        L_0x0156:
            r0 = move-exception
            r2 = r3
            goto L_0x0135
        L_0x0159:
            r0 = move-exception
            goto L_0x0135
        L_0x015b:
            r0 = move-exception
            r2 = r1
            goto L_0x0135
        L_0x015e:
            r0 = move-exception
            r1 = r3
            goto L_0x0105
        L_0x0161:
            r0 = move-exception
            r1 = r2
            goto L_0x0105
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.k():long");
    }

    public static long l() {
        if (!t()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            int blockSize = statFs.getBlockSize();
            return ((long) blockSize) * ((long) statFs.getBlockCount());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -2;
        }
    }

    public static long m() {
        if (!t()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            int blockSize = statFs.getBlockSize();
            return ((long) blockSize) * ((long) statFs.getAvailableBlocks());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -2;
        }
    }

    public static String n() {
        try {
            return Locale.getDefault().getCountry();
        } catch (Throwable th) {
            if (x.a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    public static String o() {
        try {
            return Build.BRAND;
        } catch (Throwable th) {
            if (x.a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    public static String c(Context context) {
        String str;
        TelephonyManager telephonyManager;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getType() == 1) {
                return "WIFI";
            }
            if (activeNetworkInfo.getType() == 0 && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null) {
                int networkType = telephonyManager.getNetworkType();
                switch (networkType) {
                    case 1:
                        return "GPRS";
                    case 2:
                        return "EDGE";
                    case 3:
                        return "UMTS";
                    case 4:
                        return "CDMA";
                    case 5:
                        return "EVDO_0";
                    case 6:
                        return "EVDO_A";
                    case 7:
                        return "1xRTT";
                    case 8:
                        return "HSDPA";
                    case 9:
                        return "HSUPA";
                    case 10:
                        return "HSPA";
                    case 11:
                        return "iDen";
                    case 12:
                        return "EVDO_B";
                    case 13:
                        return "LTE";
                    case 14:
                        return "eHRPD";
                    case 15:
                        return "HSPA+";
                    default:
                        str = "MOBILE(" + networkType + h.b;
                        break;
                }
            } else {
                str = "unknown";
            }
            return str;
        } catch (Exception e2) {
            if (x.a(e2)) {
                return "unknown";
            }
            e2.printStackTrace();
            return "unknown";
        }
    }

    public static String d(Context context) {
        String a2 = z.a(context, "ro.miui.ui.version.name");
        if (!z.a(a2) && !a2.equals("fail")) {
            return "XiaoMi/MIUI/" + a2;
        }
        String a3 = z.a(context, "ro.build.version.emui");
        if (!z.a(a3) && !a3.equals("fail")) {
            return "HuaWei/EMOTION/" + a3;
        }
        String a4 = z.a(context, "ro.lenovo.series");
        if (z.a(a4) || a4.equals("fail")) {
            String a5 = z.a(context, "ro.build.nubia.rom.name");
            if (!z.a(a5) && !a5.equals("fail")) {
                return "Zte/NUBIA/" + a5 + "_" + z.a(context, "ro.build.nubia.rom.code");
            }
            String a6 = z.a(context, "ro.meizu.product.model");
            if (!z.a(a6) && !a6.equals("fail")) {
                return "Meizu/FLYME/" + z.a(context, "ro.build.display.id");
            }
            String a7 = z.a(context, "ro.build.version.opporom");
            if (!z.a(a7) && !a7.equals("fail")) {
                return "Oppo/COLOROS/" + a7;
            }
            String a8 = z.a(context, "ro.vivo.os.build.display.id");
            if (!z.a(a8) && !a8.equals("fail")) {
                return "vivo/FUNTOUCH/" + a8;
            }
            String a9 = z.a(context, "ro.aa.romver");
            if (!z.a(a9) && !a9.equals("fail")) {
                return "htc/" + a9 + com.appsflyer.share.Constants.URL_PATH_DELIMITER + z.a(context, "ro.build.description");
            }
            String a10 = z.a(context, "ro.lewa.version");
            if (!z.a(a10) && !a10.equals("fail")) {
                return "tcl/" + a10 + com.appsflyer.share.Constants.URL_PATH_DELIMITER + z.a(context, "ro.build.display.id");
            }
            String a11 = z.a(context, "ro.gn.gnromvernumber");
            if (!z.a(a11) && !a11.equals("fail")) {
                return "amigo/" + a11 + com.appsflyer.share.Constants.URL_PATH_DELIMITER + z.a(context, "ro.build.display.id");
            }
            String a12 = z.a(context, "ro.build.tyd.kbstyle_version");
            if (z.a(a12) || a12.equals("fail")) {
                return z.a(context, "ro.build.fingerprint") + com.appsflyer.share.Constants.URL_PATH_DELIMITER + z.a(context, "ro.build.rom.id");
            }
            return "dido/" + a12;
        }
        return "Lenovo/VIBE/" + z.a(context, "ro.build.version.incremental");
    }

    public static String e(Context context) {
        return z.a(context, "ro.board.platform");
    }

    public static boolean p() {
        boolean z;
        boolean z2;
        String[] strArr = a;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (new File(strArr[i]).exists()) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (Build.TAGS == null || !Build.TAGS.contains("test-keys")) {
            z2 = false;
        } else {
            z2 = true;
        }
        return z2 || z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00a8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a9, code lost:
        r3 = r0;
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ac, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ad, code lost:
        r3 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00af, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00b0, code lost:
        r3 = r0;
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00b7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00b8, code lost:
        r3 = r0;
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00c1, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0091 A[SYNTHETIC, Splitter:B:42:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x009f A[SYNTHETIC, Splitter:B:49:0x009f] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ac A[ExcHandler: all (r0v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r2 
  PHI: (r2v6 java.io.BufferedReader) = (r2v5 java.io.BufferedReader), (r2v7 java.io.BufferedReader) binds: [B:12:0x002e, B:23:0x0059] A[DONT_GENERATE, DONT_INLINE], Splitter:B:12:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00bb A[ExcHandler: Throwable (th java.lang.Throwable), PHI: r1 
  PHI: (r1v7 java.io.BufferedReader) = (r1v19 java.io.BufferedReader), (r1v19 java.io.BufferedReader), (r1v21 java.io.BufferedReader), (r1v22 java.io.BufferedReader), (r1v23 java.io.BufferedReader) binds: [B:31:0x007e, B:32:?, B:27:0x0075, B:16:0x004a, B:5:0x001f] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x001f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String q() {
        /*
            r0 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008d, all -> 0x009a }
            r3.<init>()     // Catch:{ Throwable -> 0x008d, all -> 0x009a }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x008d, all -> 0x009a }
            java.lang.String r2 = "/sys/block/mmcblk0/device/type"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x008d, all -> 0x009a }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x008d, all -> 0x009a }
            if (r1 == 0) goto L_0x00c5
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x008d, all -> 0x009a }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x008d, all -> 0x009a }
            java.lang.String r4 = "/sys/block/mmcblk0/device/type"
            r2.<init>(r4)     // Catch:{ Throwable -> 0x008d, all -> 0x009a }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x008d, all -> 0x009a }
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x00bb, all -> 0x00a8 }
            if (r2 == 0) goto L_0x0028
            r3.append(r2)     // Catch:{ Throwable -> 0x00bb, all -> 0x00a8 }
        L_0x0028:
            r1.close()     // Catch:{ Throwable -> 0x00bb, all -> 0x00a8 }
            r2 = r1
        L_0x002c:
            java.lang.String r1 = ","
            r3.append(r1)     // Catch:{ Throwable -> 0x00bd, all -> 0x00ac }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x00bd, all -> 0x00ac }
            java.lang.String r4 = "/sys/block/mmcblk0/device/name"
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00bd, all -> 0x00ac }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x00bd, all -> 0x00ac }
            if (r1 == 0) goto L_0x0057
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00bd, all -> 0x00ac }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x00bd, all -> 0x00ac }
            java.lang.String r5 = "/sys/block/mmcblk0/device/name"
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00bd, all -> 0x00ac }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00bd, all -> 0x00ac }
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x00bb, all -> 0x00af }
            if (r2 == 0) goto L_0x0053
            r3.append(r2)     // Catch:{ Throwable -> 0x00bb, all -> 0x00af }
        L_0x0053:
            r1.close()     // Catch:{ Throwable -> 0x00bb, all -> 0x00af }
            r2 = r1
        L_0x0057:
            java.lang.String r1 = ","
            r3.append(r1)     // Catch:{ Throwable -> 0x00c0, all -> 0x00ac }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x00c0, all -> 0x00ac }
            java.lang.String r4 = "/sys/block/mmcblk0/device/cid"
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00c0, all -> 0x00ac }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x00c0, all -> 0x00ac }
            if (r1 == 0) goto L_0x00c3
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00c0, all -> 0x00ac }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x00c0, all -> 0x00ac }
            java.lang.String r5 = "/sys/block/mmcblk0/device/cid"
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00c0, all -> 0x00ac }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00c0, all -> 0x00ac }
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x00bb, all -> 0x00b3 }
            if (r2 == 0) goto L_0x007e
            r3.append(r2)     // Catch:{ Throwable -> 0x00bb, all -> 0x00b3 }
        L_0x007e:
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x00bb, all -> 0x00b7 }
            if (r1 == 0) goto L_0x0087
            r1.close()     // Catch:{ IOException -> 0x0088 }
        L_0x0087:
            return r0
        L_0x0088:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
            goto L_0x0087
        L_0x008d:
            r1 = move-exception
            r1 = r0
        L_0x008f:
            if (r1 == 0) goto L_0x0087
            r1.close()     // Catch:{ IOException -> 0x0095 }
            goto L_0x0087
        L_0x0095:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
            goto L_0x0087
        L_0x009a:
            r1 = move-exception
            r3 = r1
            r2 = r0
        L_0x009d:
            if (r2 == 0) goto L_0x00a2
            r2.close()     // Catch:{ IOException -> 0x00a3 }
        L_0x00a2:
            throw r3
        L_0x00a3:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
            goto L_0x00a2
        L_0x00a8:
            r0 = move-exception
            r3 = r0
            r2 = r1
            goto L_0x009d
        L_0x00ac:
            r0 = move-exception
            r3 = r0
            goto L_0x009d
        L_0x00af:
            r0 = move-exception
            r3 = r0
            r2 = r1
            goto L_0x009d
        L_0x00b3:
            r0 = move-exception
            r3 = r0
            r2 = r1
            goto L_0x009d
        L_0x00b7:
            r0 = move-exception
            r3 = r0
            r2 = r1
            goto L_0x009d
        L_0x00bb:
            r2 = move-exception
            goto L_0x008f
        L_0x00bd:
            r1 = move-exception
            r1 = r2
            goto L_0x008f
        L_0x00c0:
            r1 = move-exception
            r1 = r2
            goto L_0x008f
        L_0x00c3:
            r1 = r2
            goto L_0x007e
        L_0x00c5:
            r2 = r0
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.q():java.lang.String");
    }

    public static String f(Context context) {
        StringBuilder sb = new StringBuilder();
        String a2 = z.a(context, "ro.genymotion.version");
        if (a2 != null) {
            sb.append("ro.genymotion.version");
            sb.append("|");
            sb.append(a2);
            sb.append("\n");
        }
        String a3 = z.a(context, "androVM.vbox_dpi");
        if (a3 != null) {
            sb.append("androVM.vbox_dpi");
            sb.append("|");
            sb.append(a3);
            sb.append("\n");
        }
        String a4 = z.a(context, "qemu.sf.fake_camera");
        if (a4 != null) {
            sb.append("qemu.sf.fake_camera");
            sb.append("|");
            sb.append(a4);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x006a A[Catch:{ Throwable -> 0x00b1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0095 A[SYNTHETIC, Splitter:B:32:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a6 A[SYNTHETIC, Splitter:B:40:0x00a6] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String g(android.content.Context r5) {
        /*
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r0 = d
            if (r0 != 0) goto L_0x0011
            java.lang.String r0 = "ro.secure"
            java.lang.String r0 = com.tencent.bugly.proguard.z.a((android.content.Context) r5, (java.lang.String) r0)
            d = r0
        L_0x0011:
            java.lang.String r0 = d
            if (r0 == 0) goto L_0x0029
            java.lang.String r0 = "ro.secure"
            r3.append(r0)
            java.lang.String r0 = "|"
            r3.append(r0)
            java.lang.String r0 = d
            r3.append(r0)
            java.lang.String r0 = "\n"
            r3.append(r0)
        L_0x0029:
            java.lang.String r0 = e
            if (r0 != 0) goto L_0x0035
            java.lang.String r0 = "ro.debuggable"
            java.lang.String r0 = com.tencent.bugly.proguard.z.a((android.content.Context) r5, (java.lang.String) r0)
            e = r0
        L_0x0035:
            java.lang.String r0 = e
            if (r0 == 0) goto L_0x004d
            java.lang.String r0 = "ro.debuggable"
            r3.append(r0)
            java.lang.String r0 = "|"
            r3.append(r0)
            java.lang.String r0 = e
            r3.append(r0)
            java.lang.String r0 = "\n"
            r3.append(r0)
        L_0x004d:
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x008e, all -> 0x00a2 }
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ Throwable -> 0x008e, all -> 0x00a2 }
            java.lang.String r4 = "/proc/self/status"
            r0.<init>(r4)     // Catch:{ Throwable -> 0x008e, all -> 0x00a2 }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x008e, all -> 0x00a2 }
        L_0x005a:
            java.lang.String r0 = r1.readLine()     // Catch:{ Throwable -> 0x00b1 }
            if (r0 == 0) goto L_0x0068
            java.lang.String r2 = "TracerPid:"
            boolean r2 = r0.startsWith(r2)     // Catch:{ Throwable -> 0x00b1 }
            if (r2 == 0) goto L_0x005a
        L_0x0068:
            if (r0 == 0) goto L_0x0081
            r2 = 10
            java.lang.String r0 = r0.substring(r2)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r2 = "tracer_pid"
            r3.append(r2)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r2 = "|"
            r3.append(r2)     // Catch:{ Throwable -> 0x00b1 }
            r3.append(r0)     // Catch:{ Throwable -> 0x00b1 }
        L_0x0081:
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x00b1 }
            r1.close()     // Catch:{ IOException -> 0x0089 }
        L_0x0088:
            return r0
        L_0x0089:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
            goto L_0x0088
        L_0x008e:
            r0 = move-exception
            r1 = r2
        L_0x0090:
            com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x00af }
            if (r1 == 0) goto L_0x0098
            r1.close()     // Catch:{ IOException -> 0x009d }
        L_0x0098:
            java.lang.String r0 = r3.toString()
            goto L_0x0088
        L_0x009d:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
            goto L_0x0098
        L_0x00a2:
            r0 = move-exception
            r1 = r2
        L_0x00a4:
            if (r1 == 0) goto L_0x00a9
            r1.close()     // Catch:{ IOException -> 0x00aa }
        L_0x00a9:
            throw r0
        L_0x00aa:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
            goto L_0x00a9
        L_0x00af:
            r0 = move-exception
            goto L_0x00a4
        L_0x00b1:
            r0 = move-exception
            goto L_0x0090
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.g(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00c9, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ca, code lost:
        r2 = r1;
        r3 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00d9, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00da, code lost:
        r2 = r1;
        r3 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00b2 A[SYNTHETIC, Splitter:B:42:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c0 A[SYNTHETIC, Splitter:B:49:0x00c0] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00dd A[ExcHandler: Throwable (th java.lang.Throwable), PHI: r0 
  PHI: (r0v9 java.io.BufferedReader) = (r0v25 java.io.BufferedReader), (r0v26 java.io.BufferedReader), (r0v27 java.io.BufferedReader) binds: [B:27:0x0089, B:16:0x0054, B:5:0x001f] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x001f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String r() {
        /*
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r1 = 0
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00ae, all -> 0x00bb }
            java.lang.String r3 = "/sys/class/power_supply/ac/online"
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00ae, all -> 0x00bb }
            boolean r0 = r0.exists()     // Catch:{ Throwable -> 0x00ae, all -> 0x00bb }
            if (r0 == 0) goto L_0x0036
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00ae, all -> 0x00bb }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x00ae, all -> 0x00bb }
            java.lang.String r4 = "/sys/class/power_supply/ac/online"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00ae, all -> 0x00bb }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00ae, all -> 0x00bb }
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x00dd, all -> 0x00c9 }
            if (r1 == 0) goto L_0x0032
            java.lang.String r3 = "ac_online"
            r2.append(r3)     // Catch:{ Throwable -> 0x00dd, all -> 0x00c9 }
            java.lang.String r3 = "|"
            r2.append(r3)     // Catch:{ Throwable -> 0x00dd, all -> 0x00c9 }
            r2.append(r1)     // Catch:{ Throwable -> 0x00dd, all -> 0x00c9 }
        L_0x0032:
            r0.close()     // Catch:{ Throwable -> 0x00dd, all -> 0x00c9 }
            r1 = r0
        L_0x0036:
            java.lang.String r0 = "\n"
            r2.append(r0)     // Catch:{ Throwable -> 0x00df, all -> 0x00cd }
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00df, all -> 0x00cd }
            java.lang.String r3 = "/sys/class/power_supply/usb/online"
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00df, all -> 0x00cd }
            boolean r0 = r0.exists()     // Catch:{ Throwable -> 0x00df, all -> 0x00cd }
            if (r0 == 0) goto L_0x006b
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00df, all -> 0x00cd }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x00df, all -> 0x00cd }
            java.lang.String r4 = "/sys/class/power_supply/usb/online"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00df, all -> 0x00cd }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00df, all -> 0x00cd }
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x00dd, all -> 0x00d1 }
            if (r1 == 0) goto L_0x0067
            java.lang.String r3 = "usb_online"
            r2.append(r3)     // Catch:{ Throwable -> 0x00dd, all -> 0x00d1 }
            java.lang.String r3 = "|"
            r2.append(r3)     // Catch:{ Throwable -> 0x00dd, all -> 0x00d1 }
            r2.append(r1)     // Catch:{ Throwable -> 0x00dd, all -> 0x00d1 }
        L_0x0067:
            r0.close()     // Catch:{ Throwable -> 0x00dd, all -> 0x00d1 }
            r1 = r0
        L_0x006b:
            java.lang.String r0 = "\n"
            r2.append(r0)     // Catch:{ Throwable -> 0x00e2, all -> 0x00d5 }
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00e2, all -> 0x00d5 }
            java.lang.String r3 = "/sys/class/power_supply/battery/capacity"
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00e2, all -> 0x00d5 }
            boolean r0 = r0.exists()     // Catch:{ Throwable -> 0x00e2, all -> 0x00d5 }
            if (r0 == 0) goto L_0x00e5
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00e2, all -> 0x00d5 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x00e2, all -> 0x00d5 }
            java.lang.String r4 = "/sys/class/power_supply/battery/capacity"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00e2, all -> 0x00d5 }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00e2, all -> 0x00d5 }
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x00dd, all -> 0x00d9 }
            if (r1 == 0) goto L_0x009c
            java.lang.String r3 = "battery_capacity"
            r2.append(r3)     // Catch:{ Throwable -> 0x00dd, all -> 0x00d9 }
            java.lang.String r3 = "|"
            r2.append(r3)     // Catch:{ Throwable -> 0x00dd, all -> 0x00d9 }
            r2.append(r1)     // Catch:{ Throwable -> 0x00dd, all -> 0x00d9 }
        L_0x009c:
            r0.close()     // Catch:{ Throwable -> 0x00dd, all -> 0x00d9 }
        L_0x009f:
            if (r0 == 0) goto L_0x00a4
            r0.close()     // Catch:{ IOException -> 0x00a9 }
        L_0x00a4:
            java.lang.String r0 = r2.toString()
            return r0
        L_0x00a9:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
            goto L_0x00a4
        L_0x00ae:
            r0 = move-exception
            r0 = r1
        L_0x00b0:
            if (r0 == 0) goto L_0x00a4
            r0.close()     // Catch:{ IOException -> 0x00b6 }
            goto L_0x00a4
        L_0x00b6:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
            goto L_0x00a4
        L_0x00bb:
            r0 = move-exception
            r2 = r0
            r3 = r1
        L_0x00be:
            if (r3 == 0) goto L_0x00c3
            r3.close()     // Catch:{ IOException -> 0x00c4 }
        L_0x00c3:
            throw r2
        L_0x00c4:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
            goto L_0x00c3
        L_0x00c9:
            r1 = move-exception
            r2 = r1
            r3 = r0
            goto L_0x00be
        L_0x00cd:
            r0 = move-exception
            r2 = r0
            r3 = r1
            goto L_0x00be
        L_0x00d1:
            r1 = move-exception
            r2 = r1
            r3 = r0
            goto L_0x00be
        L_0x00d5:
            r0 = move-exception
            r2 = r0
            r3 = r1
            goto L_0x00be
        L_0x00d9:
            r1 = move-exception
            r2 = r1
            r3 = r0
            goto L_0x00be
        L_0x00dd:
            r1 = move-exception
            goto L_0x00b0
        L_0x00df:
            r0 = move-exception
            r0 = r1
            goto L_0x00b0
        L_0x00e2:
            r0 = move-exception
            r0 = r1
            goto L_0x00b0
        L_0x00e5:
            r0 = r1
            goto L_0x009f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.r():java.lang.String");
    }

    public static String h(Context context) {
        StringBuilder sb = new StringBuilder();
        String a2 = z.a(context, "gsm.sim.state");
        if (a2 != null) {
            sb.append("gsm.sim.state");
            sb.append("|");
            sb.append(a2);
        }
        sb.append("\n");
        String a3 = z.a(context, "gsm.sim.state2");
        if (a3 != null) {
            sb.append("gsm.sim.state2");
            sb.append("|");
            sb.append(a3);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0041 A[SYNTHETIC, Splitter:B:20:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004e A[SYNTHETIC, Splitter:B:27:0x004e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long s() {
        /*
            r0 = 0
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0035, all -> 0x004a }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x0035, all -> 0x004a }
            java.lang.String r4 = "/proc/uptime"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0035, all -> 0x004a }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x0035, all -> 0x004a }
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x0059 }
            if (r2 == 0) goto L_0x002b
            java.lang.String r3 = " "
            java.lang.String[] r2 = r2.split(r3)     // Catch:{ Throwable -> 0x0059 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Throwable -> 0x0059 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0059 }
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r6
            float r3 = (float) r4     // Catch:{ Throwable -> 0x0059 }
            float r0 = java.lang.Float.parseFloat(r2)     // Catch:{ Throwable -> 0x0059 }
            float r0 = r3 - r0
        L_0x002b:
            r1.close()     // Catch:{ IOException -> 0x0030 }
        L_0x002e:
            long r0 = (long) r0
            return r0
        L_0x0030:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
            goto L_0x002e
        L_0x0035:
            r1 = move-exception
            r1 = r2
        L_0x0037:
            java.lang.String r2 = "Failed to get boot time of device."
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0057 }
            com.tencent.bugly.proguard.x.a(r2, r3)     // Catch:{ all -> 0x0057 }
            if (r1 == 0) goto L_0x002e
            r1.close()     // Catch:{ IOException -> 0x0045 }
            goto L_0x002e
        L_0x0045:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
            goto L_0x002e
        L_0x004a:
            r0 = move-exception
            r1 = r2
        L_0x004c:
            if (r1 == 0) goto L_0x0051
            r1.close()     // Catch:{ IOException -> 0x0052 }
        L_0x0051:
            throw r0
        L_0x0052:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
            goto L_0x0051
        L_0x0057:
            r0 = move-exception
            goto L_0x004c
        L_0x0059:
            r2 = move-exception
            goto L_0x0037
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.s():long");
    }

    public static boolean i(Context context) {
        if (k(context) == null) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < c.length; i++) {
                if (i == 0) {
                    if (!new File(c[i]).exists()) {
                        arrayList.add(Integer.valueOf(i));
                    }
                } else if (new File(c[i]).exists()) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
            return (arrayList.isEmpty() ? null : arrayList.toString()) != null;
        }
    }

    private static String k(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < b.length; i++) {
            try {
                packageManager.getPackageInfo(b[i], 1);
                arrayList.add(Integer.valueOf(i));
            } catch (PackageManager.NameNotFoundException e2) {
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList.toString();
    }

    public static boolean j(Context context) {
        return (((l(context) | v()) | w()) | u()) > 0;
    }

    private static int u() {
        try {
            Method method = Class.forName("android.app.ActivityManagerNative").getMethod("getDefault", new Class[0]);
            method.setAccessible(true);
            if (method.invoke((Object) null, new Object[0]).getClass().getName().startsWith("$Proxy")) {
                return 256;
            }
            return 0;
        } catch (Exception e2) {
            return 256;
        }
    }

    private static int l(Context context) {
        int i = 0;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getInstallerPackageName("de.robv.android.xposed.installer");
            i = 1;
        } catch (Exception e2) {
        }
        try {
            packageManager.getInstallerPackageName("com.saurik.substrate");
            return i | 2;
        } catch (Exception e3) {
            return i;
        }
    }

    private static int v() {
        try {
            throw new Exception("detect hook");
        } catch (Exception e2) {
            int i = 0;
            int i2 = 0;
            for (StackTraceElement stackTraceElement : e2.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("main")) {
                    i |= 4;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    i |= 8;
                }
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") && stackTraceElement.getMethodName().equals("invoked")) {
                    i |= 16;
                }
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit") && (i2 = i2 + 1) == 2) {
                    i |= 32;
                }
            }
            return i;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x005e A[SYNTHETIC, Splitter:B:16:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a9 A[SYNTHETIC, Splitter:B:43:0x00a9] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ba A[SYNTHETIC, Splitter:B:52:0x00ba] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c7 A[SYNTHETIC, Splitter:B:59:0x00c7] */
    /* JADX WARNING: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:40:0x00a4=Splitter:B:40:0x00a4, B:49:0x00b5=Splitter:B:49:0x00b5, B:13:0x0059=Splitter:B:13:0x0059} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int w() {
        /*
            r1 = 0
            r3 = 0
            java.util.HashSet r2 = new java.util.HashSet     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            r2.<init>()     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            java.lang.String r8 = "/proc/"
            r7.<init>(r8)     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            int r8 = android.os.Process.myPid()     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            java.lang.String r8 = "/maps"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            java.lang.String r7 = r7.toString()     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            r6.<init>(r7)     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            java.lang.String r7 = "utf-8"
            r4.<init>(r6, r7)     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
            r5.<init>(r4)     // Catch:{ UnsupportedEncodingException -> 0x00e8, FileNotFoundException -> 0x00a1, IOException -> 0x00b2, all -> 0x00c3 }
        L_0x0031:
            java.lang.String r3 = r5.readLine()     // Catch:{ UnsupportedEncodingException -> 0x0057, FileNotFoundException -> 0x00dd, IOException -> 0x00d2 }
            if (r3 == 0) goto L_0x0062
            java.lang.String r4 = ".so"
            boolean r4 = r3.endsWith(r4)     // Catch:{ UnsupportedEncodingException -> 0x0057, FileNotFoundException -> 0x00dd, IOException -> 0x00d2 }
            if (r4 != 0) goto L_0x0047
            java.lang.String r4 = ".jar"
            boolean r4 = r3.endsWith(r4)     // Catch:{ UnsupportedEncodingException -> 0x0057, FileNotFoundException -> 0x00dd, IOException -> 0x00d2 }
            if (r4 == 0) goto L_0x0031
        L_0x0047:
            java.lang.String r4 = " "
            int r4 = r3.lastIndexOf(r4)     // Catch:{ UnsupportedEncodingException -> 0x0057, FileNotFoundException -> 0x00dd, IOException -> 0x00d2 }
            int r4 = r4 + 1
            java.lang.String r3 = r3.substring(r4)     // Catch:{ UnsupportedEncodingException -> 0x0057, FileNotFoundException -> 0x00dd, IOException -> 0x00d2 }
            r2.add(r3)     // Catch:{ UnsupportedEncodingException -> 0x0057, FileNotFoundException -> 0x00dd, IOException -> 0x00d2 }
            goto L_0x0031
        L_0x0057:
            r2 = move-exception
            r4 = r2
        L_0x0059:
            r4.printStackTrace()     // Catch:{ all -> 0x00d0 }
            if (r5 == 0) goto L_0x0061
            r5.close()     // Catch:{ IOException -> 0x009c }
        L_0x0061:
            return r1
        L_0x0062:
            java.util.Iterator r4 = r2.iterator()     // Catch:{ UnsupportedEncodingException -> 0x0057, FileNotFoundException -> 0x00dd, IOException -> 0x00d2 }
            r3 = r1
        L_0x0067:
            boolean r1 = r4.hasNext()     // Catch:{ UnsupportedEncodingException -> 0x00ed, FileNotFoundException -> 0x00e0, IOException -> 0x00d5 }
            if (r1 == 0) goto L_0x0091
            java.lang.Object r1 = r4.next()     // Catch:{ UnsupportedEncodingException -> 0x00ed, FileNotFoundException -> 0x00e0, IOException -> 0x00d5 }
            r0 = r1
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ UnsupportedEncodingException -> 0x00ed, FileNotFoundException -> 0x00e0, IOException -> 0x00d5 }
            r2 = r0
            java.lang.String r2 = r2.toLowerCase()     // Catch:{ UnsupportedEncodingException -> 0x00ed, FileNotFoundException -> 0x00e0, IOException -> 0x00d5 }
            java.lang.String r6 = "xposed"
            boolean r2 = r2.contains(r6)     // Catch:{ UnsupportedEncodingException -> 0x00ed, FileNotFoundException -> 0x00e0, IOException -> 0x00d5 }
            if (r2 == 0) goto L_0x00f9
            r2 = r3 | 64
        L_0x0083:
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00e4, IOException -> 0x00d9 }
            java.lang.String r3 = "com.saurik.substrate"
            boolean r1 = r1.contains(r3)     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00e4, IOException -> 0x00d9 }
            if (r1 == 0) goto L_0x00f7
            r1 = r2 | 128(0x80, float:1.794E-43)
        L_0x008f:
            r3 = r1
            goto L_0x0067
        L_0x0091:
            r5.close()     // Catch:{ IOException -> 0x0096 }
            r1 = r3
            goto L_0x0061
        L_0x0096:
            r1 = move-exception
            r1.printStackTrace()
            r1 = r3
            goto L_0x0061
        L_0x009c:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0061
        L_0x00a1:
            r2 = move-exception
            r4 = r2
            r5 = r3
        L_0x00a4:
            r4.printStackTrace()     // Catch:{ all -> 0x00d0 }
            if (r5 == 0) goto L_0x0061
            r5.close()     // Catch:{ IOException -> 0x00ad }
            goto L_0x0061
        L_0x00ad:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0061
        L_0x00b2:
            r2 = move-exception
            r4 = r2
            r5 = r3
        L_0x00b5:
            r4.printStackTrace()     // Catch:{ all -> 0x00d0 }
            if (r5 == 0) goto L_0x0061
            r5.close()     // Catch:{ IOException -> 0x00be }
            goto L_0x0061
        L_0x00be:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0061
        L_0x00c3:
            r1 = move-exception
            r5 = r3
        L_0x00c5:
            if (r5 == 0) goto L_0x00ca
            r5.close()     // Catch:{ IOException -> 0x00cb }
        L_0x00ca:
            throw r1
        L_0x00cb:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00ca
        L_0x00d0:
            r1 = move-exception
            goto L_0x00c5
        L_0x00d2:
            r2 = move-exception
            r4 = r2
            goto L_0x00b5
        L_0x00d5:
            r2 = move-exception
            r4 = r2
            r1 = r3
            goto L_0x00b5
        L_0x00d9:
            r3 = move-exception
            r4 = r3
            r1 = r2
            goto L_0x00b5
        L_0x00dd:
            r2 = move-exception
            r4 = r2
            goto L_0x00a4
        L_0x00e0:
            r2 = move-exception
            r4 = r2
            r1 = r3
            goto L_0x00a4
        L_0x00e4:
            r3 = move-exception
            r4 = r3
            r1 = r2
            goto L_0x00a4
        L_0x00e8:
            r2 = move-exception
            r4 = r2
            r5 = r3
            goto L_0x0059
        L_0x00ed:
            r2 = move-exception
            r4 = r2
            r1 = r3
            goto L_0x0059
        L_0x00f2:
            r3 = move-exception
            r4 = r3
            r1 = r2
            goto L_0x0059
        L_0x00f7:
            r1 = r2
            goto L_0x008f
        L_0x00f9:
            r2 = r3
            goto L_0x0083
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.w():int");
    }
}
