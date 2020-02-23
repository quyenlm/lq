package com.tencent.imsdk.webview.qq;

import android.os.Build;
import java.util.Properties;

public class AndroidRomUtil {
    private static final String KEY_EMUI_ROM_CODE = "huawei";
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static Properties properties = new Properties();

    public static boolean autoKillMultiProcess() {
        return isEMUI() || isSamsung();
    }

    private static boolean isSamsung() {
        return Build.MANUFACTURER != null && Build.MANUFACTURER.toLowerCase().contains("samsung");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a9 A[SYNTHETIC, Splitter:B:30:0x00a9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isEMUI() {
        /*
            r4 = 0
            r1 = 0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008f }
            r6.<init>()     // Catch:{ Exception -> 0x008f }
            java.lang.String r7 = android.os.Build.FINGERPRINT     // Catch:{ Exception -> 0x008f }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008f }
            java.lang.String r7 = android.os.Build.MANUFACTURER     // Catch:{ Exception -> 0x008f }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008f }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x008f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008f }
            r6.<init>()     // Catch:{ Exception -> 0x008f }
            java.lang.String r7 = "romInfo:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008f }
            java.lang.StringBuilder r6 = r6.append(r5)     // Catch:{ Exception -> 0x008f }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x008f }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r6)     // Catch:{ Exception -> 0x008f }
            java.util.Locale r6 = java.util.Locale.UK     // Catch:{ Exception -> 0x008f }
            java.lang.String r6 = r5.toLowerCase(r6)     // Catch:{ Exception -> 0x008f }
            java.lang.String r7 = "huawei"
            boolean r6 = r6.contains(r7)     // Catch:{ Exception -> 0x008f }
            r4 = r4 | r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008f }
            r6.<init>()     // Catch:{ Exception -> 0x008f }
            java.lang.String r7 = "after fingerprint , current isEMUI is "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008f }
            java.lang.StringBuilder r6 = r6.append(r4)     // Catch:{ Exception -> 0x008f }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x008f }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r6)     // Catch:{ Exception -> 0x008f }
            java.util.Properties r6 = properties     // Catch:{ Exception -> 0x008f }
            boolean r6 = r6.isEmpty()     // Catch:{ Exception -> 0x008f }
            if (r6 == 0) goto L_0x0071
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x008f }
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x008f }
            java.io.File r7 = android.os.Environment.getRootDirectory()     // Catch:{ Exception -> 0x008f }
            java.lang.String r8 = "build.prop"
            r6.<init>(r7, r8)     // Catch:{ Exception -> 0x008f }
            r2.<init>(r6)     // Catch:{ Exception -> 0x008f }
            java.util.Properties r6 = properties     // Catch:{ Exception -> 0x00b9, all -> 0x00b6 }
            r6.load(r2)     // Catch:{ Exception -> 0x00b9, all -> 0x00b6 }
            r2.close()     // Catch:{ Exception -> 0x00b9, all -> 0x00b6 }
            r1 = r2
        L_0x0071:
            java.util.Properties r6 = properties     // Catch:{ Exception -> 0x008f }
            java.lang.String r7 = "ro.build.version.emui"
            r8 = 0
            java.lang.String r6 = r6.getProperty(r7, r8)     // Catch:{ Exception -> 0x008f }
            if (r6 == 0) goto L_0x0084
            r6 = 1
        L_0x007d:
            r4 = r4 | r6
            if (r1 == 0) goto L_0x0083
            r1.close()     // Catch:{ IOException -> 0x0086 }
        L_0x0083:
            return r4
        L_0x0084:
            r6 = 0
            goto L_0x007d
        L_0x0086:
            r3 = move-exception
            java.lang.String r6 = r3.getMessage()
            com.tencent.imsdk.tool.etc.IMLogger.e(r6)
            goto L_0x0083
        L_0x008f:
            r0 = move-exception
        L_0x0090:
            java.lang.String r6 = r0.getMessage()     // Catch:{ all -> 0x00a6 }
            com.tencent.imsdk.tool.etc.IMLogger.w(r6)     // Catch:{ all -> 0x00a6 }
            if (r1 == 0) goto L_0x0083
            r1.close()     // Catch:{ IOException -> 0x009d }
            goto L_0x0083
        L_0x009d:
            r3 = move-exception
            java.lang.String r6 = r3.getMessage()
            com.tencent.imsdk.tool.etc.IMLogger.e(r6)
            goto L_0x0083
        L_0x00a6:
            r6 = move-exception
        L_0x00a7:
            if (r1 == 0) goto L_0x00ac
            r1.close()     // Catch:{ IOException -> 0x00ad }
        L_0x00ac:
            throw r6
        L_0x00ad:
            r3 = move-exception
            java.lang.String r7 = r3.getMessage()
            com.tencent.imsdk.tool.etc.IMLogger.e(r7)
            goto L_0x00ac
        L_0x00b6:
            r6 = move-exception
            r1 = r2
            goto L_0x00a7
        L_0x00b9:
            r0 = move-exception
            r1 = r2
            goto L_0x0090
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.webview.qq.AndroidRomUtil.isEMUI():boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0057 A[SYNTHETIC, Splitter:B:24:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0067 A[SYNTHETIC, Splitter:B:30:0x0067] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isMIUI() {
        /*
            r4 = 0
            r1 = 0
            java.util.Properties r5 = properties     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            boolean r5 = r5.isEmpty()     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            if (r5 == 0) goto L_0x0023
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            java.io.File r5 = new java.io.File     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            java.io.File r6 = android.os.Environment.getRootDirectory()     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            java.lang.String r7 = "build.prop"
            r5.<init>(r6, r7)     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            r2.<init>(r5)     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            java.util.Properties r5 = properties     // Catch:{ IOException -> 0x0077, all -> 0x0074 }
            r5.load(r2)     // Catch:{ IOException -> 0x0077, all -> 0x0074 }
            r2.close()     // Catch:{ IOException -> 0x0077, all -> 0x0074 }
            r1 = r2
        L_0x0023:
            java.util.Properties r5 = properties     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            java.lang.String r6 = "ro.miui.ui.version.code"
            r7 = 0
            java.lang.String r5 = r5.getProperty(r6, r7)     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            if (r5 != 0) goto L_0x0044
            java.util.Properties r5 = properties     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            java.lang.String r6 = "ro.miui.ui.version.name"
            r7 = 0
            java.lang.String r5 = r5.getProperty(r6, r7)     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            if (r5 != 0) goto L_0x0044
            java.util.Properties r5 = properties     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            java.lang.String r6 = "ro.miui.internal.storage"
            r7 = 0
            java.lang.String r5 = r5.getProperty(r6, r7)     // Catch:{ IOException -> 0x0054, all -> 0x0064 }
            if (r5 == 0) goto L_0x0045
        L_0x0044:
            r4 = 1
        L_0x0045:
            if (r1 == 0) goto L_0x004a
            r1.close()     // Catch:{ IOException -> 0x004b }
        L_0x004a:
            return r4
        L_0x004b:
            r3 = move-exception
            java.lang.String r5 = r3.getMessage()
            com.tencent.imsdk.tool.etc.IMLogger.e(r5)
            goto L_0x004a
        L_0x0054:
            r0 = move-exception
        L_0x0055:
            if (r1 == 0) goto L_0x004a
            r1.close()     // Catch:{ IOException -> 0x005b }
            goto L_0x004a
        L_0x005b:
            r3 = move-exception
            java.lang.String r5 = r3.getMessage()
            com.tencent.imsdk.tool.etc.IMLogger.e(r5)
            goto L_0x004a
        L_0x0064:
            r4 = move-exception
        L_0x0065:
            if (r1 == 0) goto L_0x006a
            r1.close()     // Catch:{ IOException -> 0x006b }
        L_0x006a:
            throw r4
        L_0x006b:
            r3 = move-exception
            java.lang.String r5 = r3.getMessage()
            com.tencent.imsdk.tool.etc.IMLogger.e(r5)
            goto L_0x006a
        L_0x0074:
            r4 = move-exception
            r1 = r2
            goto L_0x0065
        L_0x0077:
            r0 = move-exception
            r1 = r2
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.webview.qq.AndroidRomUtil.isMIUI():boolean");
    }

    public static boolean isFlyme() {
        try {
            if (Build.class.getMethod("hasSmartBar", new Class[0]) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
