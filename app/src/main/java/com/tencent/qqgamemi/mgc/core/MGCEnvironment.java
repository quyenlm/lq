package com.tencent.qqgamemi.mgc.core;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;

public class MGCEnvironment {
    private static final String CONFIG_RELATIVE_PATH = ("configs" + File.separator + "recordsdk");
    private static final String SDK_DIRNAME = "VRecorder";

    public static File getMgcExternalStorageDirectory(Context context) {
        return getMgcExternalStorageDirectory(context, "mgc");
    }

    public static File getConfigExternalStorageDirectory(Context context) {
        return getMgcExternalStorageDirectory(context, CONFIG_RELATIVE_PATH);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0031 A[SYNTHETIC, Splitter:B:16:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File getMgcExternalStorageDirectory(android.content.Context r6, java.lang.String r7) {
        /*
            if (r6 != 0) goto L_0x000a
            boolean r4 = android.text.TextUtils.isEmpty(r7)
            if (r4 == 0) goto L_0x000a
            r0 = 0
        L_0x0009:
            return r0
        L_0x000a:
            boolean r3 = com.tencent.component.utils.FileUtil.isExternalAvailable()
            r0 = 0
            if (r3 == 0) goto L_0x0050
            r2 = 0
            int r4 = android.os.Build.VERSION.SDK_INT
            r5 = 23
            if (r4 < r5) goto L_0x003f
            java.lang.String r4 = "VRecorder"
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x003f
            java.lang.String r2 = getSdkExternalDirApi23(r6, r7)
        L_0x0024:
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x0050
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0048 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0048 }
        L_0x002f:
            if (r1 != 0) goto L_0x004e
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x004b }
            android.content.Context r4 = com.tencent.component.ComponentContext.getContext()     // Catch:{ Exception -> 0x004b }
            java.io.File r4 = r4.getFilesDir()     // Catch:{ Exception -> 0x004b }
            r0.<init>(r4, r7)     // Catch:{ Exception -> 0x004b }
            goto L_0x0009
        L_0x003f:
            android.content.Context r4 = com.tencent.component.ComponentContext.getContext()
            java.lang.String r2 = com.tencent.component.utils.FileUtil.getExternalCacheDirExt((android.content.Context) r4, (java.lang.String) r7)
            goto L_0x0024
        L_0x0048:
            r4 = move-exception
            r1 = r0
            goto L_0x002f
        L_0x004b:
            r4 = move-exception
            r0 = r1
            goto L_0x0009
        L_0x004e:
            r0 = r1
            goto L_0x0009
        L_0x0050:
            r1 = r0
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qqgamemi.mgc.core.MGCEnvironment.getMgcExternalStorageDirectory(android.content.Context, java.lang.String):java.io.File");
    }

    public static String getSdkExternalDirApi23(Context context, String dirName) {
        String sdkDir = getExternalCacheDirForSdk(context);
        if (TextUtils.isEmpty(sdkDir)) {
            return null;
        }
        if (TextUtils.isEmpty(dirName)) {
            return sdkDir;
        }
        File dir = new File(sdkDir, dirName);
        if (dir != null) {
            return dir.getAbsolutePath();
        }
        return null;
    }

    public static String getExternalCacheDirForSdk(Context context) {
        File sdkDir;
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir == null || (sdkDir = new File(externalCacheDir, "VRecorder")) == null) {
            return null;
        }
        return sdkDir.getAbsolutePath();
    }
}
