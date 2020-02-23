package com.tencent.ieg.ntv.utils;

import android.os.Build;
import java.util.Properties;

public class AndroidRomUtil {
    private static final String KEY_EMUI_ROM_CODE = "huawei";
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String TAG = AndroidRomUtil.class.getSimpleName();
    private static Properties properties = new Properties();

    public static boolean autoKillMultiProcess() {
        return isEMUI() || isSamsung();
    }

    private static boolean isSamsung() {
        return Build.MANUFACTURER != null && Build.MANUFACTURER.toLowerCase().contains("samsung");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b3 A[SYNTHETIC, Splitter:B:30:0x00b3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isEMUI() {
        /*
            r4 = 0
            r1 = 0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0095 }
            r6.<init>()     // Catch:{ Exception -> 0x0095 }
            java.lang.String r7 = android.os.Build.FINGERPRINT     // Catch:{ Exception -> 0x0095 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0095 }
            java.lang.String r7 = android.os.Build.MANUFACTURER     // Catch:{ Exception -> 0x0095 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0095 }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x0095 }
            java.lang.String r6 = TAG     // Catch:{ Exception -> 0x0095 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0095 }
            r7.<init>()     // Catch:{ Exception -> 0x0095 }
            java.lang.String r8 = "romInfo:"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x0095 }
            java.lang.StringBuilder r7 = r7.append(r5)     // Catch:{ Exception -> 0x0095 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0095 }
            com.tencent.ieg.ntv.utils.Logger.d(r6, r7)     // Catch:{ Exception -> 0x0095 }
            java.util.Locale r6 = java.util.Locale.UK     // Catch:{ Exception -> 0x0095 }
            java.lang.String r6 = r5.toLowerCase(r6)     // Catch:{ Exception -> 0x0095 }
            java.lang.String r7 = "huawei"
            boolean r6 = r6.contains(r7)     // Catch:{ Exception -> 0x0095 }
            r4 = r4 | r6
            java.lang.String r6 = TAG     // Catch:{ Exception -> 0x0095 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0095 }
            r7.<init>()     // Catch:{ Exception -> 0x0095 }
            java.lang.String r8 = "after fingerprint , current isEMUI is "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x0095 }
            java.lang.StringBuilder r7 = r7.append(r4)     // Catch:{ Exception -> 0x0095 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0095 }
            com.tencent.ieg.ntv.utils.Logger.d(r6, r7)     // Catch:{ Exception -> 0x0095 }
            java.util.Properties r6 = properties     // Catch:{ Exception -> 0x0095 }
            boolean r6 = r6.isEmpty()     // Catch:{ Exception -> 0x0095 }
            if (r6 == 0) goto L_0x0075
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0095 }
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x0095 }
            java.io.File r7 = android.os.Environment.getRootDirectory()     // Catch:{ Exception -> 0x0095 }
            java.lang.String r8 = "build.prop"
            r6.<init>(r7, r8)     // Catch:{ Exception -> 0x0095 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x0095 }
            java.util.Properties r6 = properties     // Catch:{ Exception -> 0x00c5, all -> 0x00c2 }
            r6.load(r2)     // Catch:{ Exception -> 0x00c5, all -> 0x00c2 }
            r2.close()     // Catch:{ Exception -> 0x00c5, all -> 0x00c2 }
            r1 = r2
        L_0x0075:
            java.util.Properties r6 = properties     // Catch:{ Exception -> 0x0095 }
            java.lang.String r7 = "ro.build.version.emui"
            r8 = 0
            java.lang.String r6 = r6.getProperty(r7, r8)     // Catch:{ Exception -> 0x0095 }
            if (r6 == 0) goto L_0x0088
            r6 = 1
        L_0x0081:
            r4 = r4 | r6
            if (r1 == 0) goto L_0x0087
            r1.close()     // Catch:{ IOException -> 0x008a }
        L_0x0087:
            return r4
        L_0x0088:
            r6 = 0
            goto L_0x0081
        L_0x008a:
            r3 = move-exception
            java.lang.String r6 = TAG
            java.lang.String r7 = r3.getMessage()
            com.tencent.ieg.ntv.utils.Logger.e((java.lang.String) r6, (java.lang.String) r7)
            goto L_0x0087
        L_0x0095:
            r0 = move-exception
        L_0x0096:
            java.lang.String r6 = TAG     // Catch:{ all -> 0x00b0 }
            java.lang.String r7 = r0.getMessage()     // Catch:{ all -> 0x00b0 }
            com.tencent.ieg.ntv.utils.Logger.w((java.lang.String) r6, (java.lang.String) r7)     // Catch:{ all -> 0x00b0 }
            if (r1 == 0) goto L_0x0087
            r1.close()     // Catch:{ IOException -> 0x00a5 }
            goto L_0x0087
        L_0x00a5:
            r3 = move-exception
            java.lang.String r6 = TAG
            java.lang.String r7 = r3.getMessage()
            com.tencent.ieg.ntv.utils.Logger.e((java.lang.String) r6, (java.lang.String) r7)
            goto L_0x0087
        L_0x00b0:
            r6 = move-exception
        L_0x00b1:
            if (r1 == 0) goto L_0x00b6
            r1.close()     // Catch:{ IOException -> 0x00b7 }
        L_0x00b6:
            throw r6
        L_0x00b7:
            r3 = move-exception
            java.lang.String r7 = TAG
            java.lang.String r8 = r3.getMessage()
            com.tencent.ieg.ntv.utils.Logger.e((java.lang.String) r7, (java.lang.String) r8)
            goto L_0x00b6
        L_0x00c2:
            r6 = move-exception
            r1 = r2
            goto L_0x00b1
        L_0x00c5:
            r0 = move-exception
            r1 = r2
            goto L_0x0096
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.ieg.ntv.utils.AndroidRomUtil.isEMUI():boolean");
    }
}
