package com.tencent.component.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.component.utils.log.LogUtil;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.ZipEntry;

public class NativeLibraryHelper {
    private static final String APK_LIB = "lib/";
    private static final int APK_LIB_LEN = APK_LIB.length();
    private static final String LIB_PREFIX = "/lib";
    private static final int LIB_PREFIX_LEN = LIB_PREFIX.length();
    private static final String LIB_SUFFIX = ".so";
    private static final int LIB_SUFFIX_LEN = LIB_SUFFIX.length();
    private static final String TAG = "NativeLibraryHelper";

    private interface IterateHandler {
        boolean handleEntry(InputStream inputStream, ZipEntry zipEntry, String str);
    }

    public static boolean copyNativeBinariesIfNeeded(File apkFile, File sharedLibraryDir) {
        return copyNativeBinariesIfNeeded(apkFile.getPath(), sharedLibraryDir.getPath());
    }

    @SuppressLint({"InlinedApi"})
    public static boolean copyNativeBinariesIfNeeded(String apkPath, String sharedLibraryDir) {
        String cpuAbi = Build.CPU_ABI;
        String cpuAbi2 = Build.VERSION.SDK_INT >= 8 ? Build.CPU_ABI2 : null;
        String cpuAbi3 = PropertyUtils.getQuickly("ro.product.cpu.upgradeabi", "armeabi");
        String[] cpuAbiList = Build.SUPPORTED_ABIS;
        if (cpuAbiList != null) {
            LogUtil.i(TAG, "cpuabilist: " + Arrays.toString(cpuAbiList));
            if (TextUtils.isEmpty(cpuAbi) && cpuAbiList.length > 0) {
                cpuAbi = cpuAbiList[0];
            }
            if (TextUtils.isEmpty(cpuAbi2) && cpuAbiList.length > 1) {
                cpuAbi2 = cpuAbiList[1];
            }
            if (TextUtils.isEmpty(cpuAbi3) && cpuAbiList.length > 2) {
                cpuAbi3 = cpuAbiList[2];
            }
        }
        return copyNativeBinariesIfNeeded(apkPath, sharedLibraryDir, cpuAbi, cpuAbi2, cpuAbi3);
    }

    private static boolean copyNativeBinariesIfNeeded(String filePath, String sharedLibraryPath, String cpuAbi, String cpuAbi2, String cpuAbi3) {
        final String dstDir = sharedLibraryPath;
        return iterateOverNativeBinaries(filePath, cpuAbi, cpuAbi2, cpuAbi3, new IterateHandler() {
            public boolean handleEntry(InputStream is, ZipEntry ze, String name) {
                return NativeLibraryHelper.copyFileIfChanged(is, ze, dstDir, name);
            }
        });
    }

    public static boolean removeNativeBinaries(String nativeLibraryPath) {
        return removeNativeBinaries(new File(nativeLibraryPath));
    }

    public static boolean removeNativeBinaries(File nativeLibraryDir) {
        File[] binaries;
        boolean deletedFiles = false;
        if (nativeLibraryDir.exists() && (binaries = nativeLibraryDir.listFiles()) != null) {
            for (int nn = 0; nn < binaries.length; nn++) {
                if (!binaries[nn].delete()) {
                    LogUtil.w(TAG, "Could not delete native binary: " + binaries[nn].getPath());
                } else {
                    deletedFiles = true;
                }
            }
        }
        return deletedFiles;
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x0127 A[SYNTHETIC, Splitter:B:62:0x0127] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean iterateOverNativeBinaries(java.lang.String r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, com.tencent.component.utils.NativeLibraryHelper.IterateHandler r18) {
        /*
            r9 = 0
            java.util.zip.ZipFile r10 = new java.util.zip.ZipFile     // Catch:{ IOException -> 0x01e5 }
            r10.<init>(r14)     // Catch:{ IOException -> 0x01e5 }
            r4 = 0
            r5 = 0
            java.util.Enumeration r2 = r10.entries()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
        L_0x000c:
            boolean r11 = r2.hasMoreElements()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            if (r11 == 0) goto L_0x01d1
            java.lang.Object r8 = r2.nextElement()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.util.zip.ZipEntry r8 = (java.util.zip.ZipEntry) r8     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r3 = r8.getName()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            if (r3 == 0) goto L_0x000c
            java.lang.String r11 = "lib/"
            boolean r11 = r3.startsWith(r11)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            if (r11 == 0) goto L_0x000c
            int r11 = r3.length()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r12 = APK_LIB_LEN     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r12 = r12 + 2
            int r13 = LIB_PREFIX_LEN     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r12 = r12 + r13
            int r12 = r12 + 1
            int r13 = LIB_SUFFIX_LEN     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r12 = r12 + r13
            if (r11 < r12) goto L_0x000c
            r11 = 47
            int r11 = r3.lastIndexOf(r11)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r7 = r3.substring(r11)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r11 = ".so"
            boolean r11 = r7.endsWith(r11)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            if (r11 == 0) goto L_0x000c
            java.lang.String r11 = "/lib"
            boolean r11 = r7.startsWith(r11)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            if (r11 == 0) goto L_0x000c
            int r11 = APK_LIB_LEN     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12 = 0
            int r13 = r15.length()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            boolean r11 = r3.regionMatches(r11, r15, r12, r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            if (r11 == 0) goto L_0x00a5
            int r11 = APK_LIB_LEN     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r12 = r15.length()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r11 = r11 + r12
            char r11 = r3.charAt(r11)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12 = 47
            if (r11 != r12) goto L_0x00a5
            r4 = 1
        L_0x006f:
            java.io.InputStream r6 = r10.getInputStream(r8)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r11 = 1
            java.lang.String r11 = r7.substring(r11)     // Catch:{ all -> 0x01ca }
            r0 = r18
            boolean r11 = r0.handleEntry(r6, r8, r11)     // Catch:{ all -> 0x01ca }
            if (r11 != 0) goto L_0x01c3
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x01ca }
            r12.<init>()     // Catch:{ all -> 0x01ca }
            java.lang.String r13 = "Failure for handle match entry "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x01ca }
            java.lang.StringBuilder r12 = r12.append(r3)     // Catch:{ all -> 0x01ca }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x01ca }
            com.tencent.component.utils.log.LogUtil.w((java.lang.String) r11, (java.lang.String) r12)     // Catch:{ all -> 0x01ca }
            r11 = 0
            if (r6 == 0) goto L_0x009e
            r6.close()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
        L_0x009e:
            if (r10 == 0) goto L_0x00a3
            r10.close()     // Catch:{ IOException -> 0x01da }
        L_0x00a3:
            r9 = r10
        L_0x00a4:
            return r11
        L_0x00a5:
            if (r16 == 0) goto L_0x012b
            int r11 = APK_LIB_LEN     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12 = 0
            int r13 = r16.length()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r0 = r16
            boolean r11 = r3.regionMatches(r11, r0, r12, r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            if (r11 == 0) goto L_0x012b
            int r11 = APK_LIB_LEN     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r12 = r16.length()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r11 = r11 + r12
            char r11 = r3.charAt(r11)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12 = 47
            if (r11 != r12) goto L_0x012b
            r5 = 1
            if (r4 == 0) goto L_0x0107
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12.<init>()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r13 = "Already saw primary ABI, skipping secondary ABI "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r0 = r16
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r12 = r12.toString()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            com.tencent.component.utils.log.LogUtil.i(r11, r12)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            goto L_0x000c
        L_0x00e4:
            r1 = move-exception
            r9 = r10
        L_0x00e6:
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e2 }
            r12.<init>()     // Catch:{ all -> 0x01e2 }
            java.lang.String r13 = "Couldn't open APK "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x01e2 }
            java.lang.StringBuilder r12 = r12.append(r14)     // Catch:{ all -> 0x01e2 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x01e2 }
            com.tencent.component.utils.log.LogUtil.w((java.lang.String) r11, (java.lang.String) r12)     // Catch:{ all -> 0x01e2 }
            r11 = 0
            if (r9 == 0) goto L_0x00a4
            r9.close()     // Catch:{ IOException -> 0x0105 }
            goto L_0x00a4
        L_0x0105:
            r12 = move-exception
            goto L_0x00a4
        L_0x0107:
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12.<init>()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r13 = "Using secondary ABI "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r0 = r16
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r12 = r12.toString()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            com.tencent.component.utils.log.LogUtil.i(r11, r12)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            goto L_0x006f
        L_0x0123:
            r11 = move-exception
            r9 = r10
        L_0x0125:
            if (r9 == 0) goto L_0x012a
            r9.close()     // Catch:{ IOException -> 0x01df }
        L_0x012a:
            throw r11
        L_0x012b:
            if (r17 == 0) goto L_0x0187
            int r11 = APK_LIB_LEN     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12 = 0
            int r13 = r17.length()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r0 = r17
            boolean r11 = r3.regionMatches(r11, r0, r12, r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            if (r11 == 0) goto L_0x0187
            int r11 = APK_LIB_LEN     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r12 = r17.length()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            int r11 = r11 + r12
            char r11 = r3.charAt(r11)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12 = 47
            if (r11 != r12) goto L_0x0187
            if (r4 != 0) goto L_0x014f
            if (r5 == 0) goto L_0x016b
        L_0x014f:
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12.<init>()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r13 = "Already saw primary or secondary ABI, skipping third ABI "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r0 = r17
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r12 = r12.toString()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            com.tencent.component.utils.log.LogUtil.i(r11, r12)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            goto L_0x000c
        L_0x016b:
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12.<init>()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r13 = "Using third ABI "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r0 = r17
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r12 = r12.toString()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            com.tencent.component.utils.log.LogUtil.i(r11, r12)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            goto L_0x006f
        L_0x0187:
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r12.<init>()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r13 = "abi didn't match anything entry "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.StringBuilder r12 = r12.append(r3)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r13 = ", ABI is "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.StringBuilder r12 = r12.append(r15)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r13 = " ABI2 is "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r0 = r16
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r13 = " ABI3 is "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            r0 = r17
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            java.lang.String r12 = r12.toString()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            com.tencent.component.utils.log.LogUtil.i(r11, r12)     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            goto L_0x000c
        L_0x01c3:
            if (r6 == 0) goto L_0x000c
            r6.close()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
            goto L_0x000c
        L_0x01ca:
            r11 = move-exception
            if (r6 == 0) goto L_0x01d0
            r6.close()     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
        L_0x01d0:
            throw r11     // Catch:{ IOException -> 0x00e4, all -> 0x0123 }
        L_0x01d1:
            if (r10 == 0) goto L_0x01d6
            r10.close()     // Catch:{ IOException -> 0x01dd }
        L_0x01d6:
            r11 = 1
            r9 = r10
            goto L_0x00a4
        L_0x01da:
            r12 = move-exception
            goto L_0x00a3
        L_0x01dd:
            r11 = move-exception
            goto L_0x01d6
        L_0x01df:
            r12 = move-exception
            goto L_0x012a
        L_0x01e2:
            r11 = move-exception
            goto L_0x0125
        L_0x01e5:
            r1 = move-exception
            goto L_0x00e6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.NativeLibraryHelper.iterateOverNativeBinaries(java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.tencent.component.utils.NativeLibraryHelper$IterateHandler):boolean");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0073 A[SYNTHETIC, Splitter:B:19:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00a5 A[SYNTHETIC, Splitter:B:31:0x00a5] */
    /* JADX WARNING: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyFileIfChanged(java.io.InputStream r15, java.util.zip.ZipEntry r16, java.lang.String r17, java.lang.String r18) {
        /*
            java.io.File r4 = new java.io.File
            r0 = r17
            r4.<init>(r0)
            ensureDirectory(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r0 = r17
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r5 = java.io.File.separator
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r18
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r3 = r4.toString()
            long r4 = r16.getSize()
            long r6 = r16.getTime()
            long r8 = r16.getCrc()
            boolean r4 = isFileDifferent(r3, r4, r6, r8)
            if (r4 != 0) goto L_0x0039
            r4 = 1
        L_0x0038:
            return r4
        L_0x0039:
            java.io.File r10 = new java.io.File
            r10.<init>(r3)
            r12 = 0
            java.io.FileOutputStream r13 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00b0 }
            r13.<init>(r10)     // Catch:{ IOException -> 0x00b0 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r4]     // Catch:{ IOException -> 0x0053, all -> 0x00ad }
        L_0x0048:
            int r14 = r15.read(r2)     // Catch:{ IOException -> 0x0053, all -> 0x00ad }
            if (r14 <= 0) goto L_0x0079
            r4 = 0
            r13.write(r2, r4, r14)     // Catch:{ IOException -> 0x0053, all -> 0x00ad }
            goto L_0x0048
        L_0x0053:
            r11 = move-exception
            r12 = r13
        L_0x0055:
            java.lang.String r4 = "NativeLibraryHelper"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a2 }
            r5.<init>()     // Catch:{ all -> 0x00a2 }
            java.lang.String r6 = "Couldn't write dst file "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x00a2 }
            java.lang.StringBuilder r5 = r5.append(r10)     // Catch:{ all -> 0x00a2 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00a2 }
            com.tencent.component.utils.log.LogUtil.w(r4, r5, r11)     // Catch:{ all -> 0x00a2 }
            com.tencent.component.utils.FileUtil.delete(r10)     // Catch:{ all -> 0x00a2 }
            r4 = 0
            if (r12 == 0) goto L_0x0038
            r12.close()     // Catch:{ IOException -> 0x0077 }
            goto L_0x0038
        L_0x0077:
            r5 = move-exception
            goto L_0x0038
        L_0x0079:
            if (r13 == 0) goto L_0x007e
            r13.close()     // Catch:{ IOException -> 0x00a9 }
        L_0x007e:
            long r4 = r16.getTime()
            boolean r4 = r10.setLastModified(r4)
            if (r4 != 0) goto L_0x00a0
            java.lang.String r4 = "NativeLibraryHelper"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Couldn't set time for dst file "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r10)
            java.lang.String r5 = r5.toString()
            com.tencent.component.utils.log.LogUtil.w((java.lang.String) r4, (java.lang.String) r5)
        L_0x00a0:
            r4 = 1
            goto L_0x0038
        L_0x00a2:
            r4 = move-exception
        L_0x00a3:
            if (r12 == 0) goto L_0x00a8
            r12.close()     // Catch:{ IOException -> 0x00ab }
        L_0x00a8:
            throw r4
        L_0x00a9:
            r4 = move-exception
            goto L_0x007e
        L_0x00ab:
            r5 = move-exception
            goto L_0x00a8
        L_0x00ad:
            r4 = move-exception
            r12 = r13
            goto L_0x00a3
        L_0x00b0:
            r11 = move-exception
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.NativeLibraryHelper.copyFileIfChanged(java.io.InputStream, java.util.zip.ZipEntry, java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a4 A[SYNTHETIC, Splitter:B:21:0x00a4] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x010d A[SYNTHETIC, Splitter:B:43:0x010d] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0118 A[SYNTHETIC, Splitter:B:48:0x0118] */
    /* JADX WARNING: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isFileDifferent(java.lang.String r17, long r18, long r20, long r22) {
        /*
            java.io.File r7 = new java.io.File
            r0 = r17
            r7.<init>(r0)
            long r12 = r7.length()
            int r11 = (r12 > r18 ? 1 : (r12 == r18 ? 0 : -1))
            if (r11 == 0) goto L_0x0039
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "file size doesn't match: "
            java.lang.StringBuilder r12 = r12.append(r13)
            long r14 = r7.length()
            java.lang.StringBuilder r12 = r12.append(r14)
            java.lang.String r13 = " vs "
            java.lang.StringBuilder r12 = r12.append(r13)
            r0 = r18
            java.lang.StringBuilder r12 = r12.append(r0)
            java.lang.String r12 = r12.toString()
            com.tencent.component.utils.log.LogUtil.i(r11, r12)
            r11 = 1
        L_0x0038:
            return r11
        L_0x0039:
            long r12 = r7.lastModified()
            int r11 = (r12 > r20 ? 1 : (r12 == r20 ? 0 : -1))
            if (r11 == 0) goto L_0x006b
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "mod time doesn't match: "
            java.lang.StringBuilder r12 = r12.append(r13)
            long r14 = r7.lastModified()
            java.lang.StringBuilder r12 = r12.append(r14)
            java.lang.String r13 = " vs "
            java.lang.StringBuilder r12 = r12.append(r13)
            r0 = r20
            java.lang.StringBuilder r12 = r12.append(r0)
            java.lang.String r12 = r12.toString()
            com.tencent.component.utils.log.LogUtil.i(r11, r12)
            r11 = 1
            goto L_0x0038
        L_0x006b:
            r8 = 0
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0126, IOException -> 0x00ef }
            r9.<init>(r7)     // Catch:{ FileNotFoundException -> 0x0126, IOException -> 0x00ef }
            java.util.zip.CRC32 r4 = new java.util.zip.CRC32     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            r4.<init>()     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            r11 = 8192(0x2000, float:1.14794E-41)
            byte[] r5 = new byte[r11]     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
        L_0x007a:
            int r10 = r9.read(r5)     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            if (r10 <= 0) goto L_0x00aa
            r11 = 0
            r4.update(r5, r11, r10)     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            goto L_0x007a
        L_0x0085:
            r6 = move-exception
            r8 = r9
        L_0x0087:
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r12.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r13 = "Couldn't open file "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0115 }
            r0 = r17
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ all -> 0x0115 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0115 }
            com.tencent.component.utils.log.LogUtil.w(r11, r12, r6)     // Catch:{ all -> 0x0115 }
            r11 = 1
            if (r8 == 0) goto L_0x0038
            r8.close()     // Catch:{ IOException -> 0x00a8 }
            goto L_0x0038
        L_0x00a8:
            r12 = move-exception
            goto L_0x0038
        L_0x00aa:
            long r2 = r4.getValue()     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            r12.<init>()     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            r0 = r17
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            java.lang.String r13 = ": crc = "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            java.lang.StringBuilder r12 = r12.append(r2)     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            java.lang.String r13 = ", zipCrc = "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            r0 = r22
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            java.lang.String r12 = r12.toString()     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            com.tencent.component.utils.log.LogUtil.i(r11, r12)     // Catch:{ FileNotFoundException -> 0x0085, IOException -> 0x0123, all -> 0x0120 }
            int r11 = (r2 > r22 ? 1 : (r2 == r22 ? 0 : -1))
            if (r11 == 0) goto L_0x00e7
            r11 = 1
            if (r9 == 0) goto L_0x0038
            r9.close()     // Catch:{ IOException -> 0x00e4 }
            goto L_0x0038
        L_0x00e4:
            r12 = move-exception
            goto L_0x0038
        L_0x00e7:
            if (r9 == 0) goto L_0x00ec
            r9.close()     // Catch:{ IOException -> 0x011c }
        L_0x00ec:
            r11 = 0
            goto L_0x0038
        L_0x00ef:
            r6 = move-exception
        L_0x00f0:
            java.lang.String r11 = "NativeLibraryHelper"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r12.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r13 = "Couldn't read file "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0115 }
            r0 = r17
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ all -> 0x0115 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0115 }
            com.tencent.component.utils.log.LogUtil.w(r11, r12, r6)     // Catch:{ all -> 0x0115 }
            r11 = 1
            if (r8 == 0) goto L_0x0038
            r8.close()     // Catch:{ IOException -> 0x0112 }
            goto L_0x0038
        L_0x0112:
            r12 = move-exception
            goto L_0x0038
        L_0x0115:
            r11 = move-exception
        L_0x0116:
            if (r8 == 0) goto L_0x011b
            r8.close()     // Catch:{ IOException -> 0x011e }
        L_0x011b:
            throw r11
        L_0x011c:
            r11 = move-exception
            goto L_0x00ec
        L_0x011e:
            r12 = move-exception
            goto L_0x011b
        L_0x0120:
            r11 = move-exception
            r8 = r9
            goto L_0x0116
        L_0x0123:
            r6 = move-exception
            r8 = r9
            goto L_0x00f0
        L_0x0126:
            r6 = move-exception
            goto L_0x0087
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.NativeLibraryHelper.isFileDifferent(java.lang.String, long, long, long):boolean");
    }

    private static boolean ensureDirectory(File directory) {
        if (!directory.exists()) {
            return directory.mkdirs();
        }
        if (directory.isDirectory()) {
            return true;
        }
        FileUtil.delete(directory);
        return directory.mkdirs();
    }
}
