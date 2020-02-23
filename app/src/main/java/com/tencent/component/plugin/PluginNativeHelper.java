package com.tencent.component.plugin;

import android.text.TextUtils;
import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.NativeLibraryHelper;
import com.tencent.component.utils.UniqueLock;
import java.io.File;
import java.util.concurrent.locks.Lock;

public class PluginNativeHelper {
    private static final String CHECKSUM = ".checksum";
    private static final int CHECKSUM_BUFFER_SIZE = 128;
    private static final int CRC_BUFFER_SIZE = 8192;
    private static final int CRC_FAST_BLOCK_NUM = 4;
    private static final long CRC_FAST_BLOCK_SIZE = 20480;
    private static final String TAG = "PluginNativeHelper";
    private static final UniqueLock<String> sUniqueLock = new UniqueLock<>();

    private PluginNativeHelper() {
    }

    public static boolean copyNativeBinariesIfNeeded(String apkPath, String sharedLibraryPath) {
        if (apkPath == null || sharedLibraryPath == null) {
            return false;
        }
        return copyNativeBinariesIfNeeded(new File(apkPath), new File(sharedLibraryPath));
    }

    public static boolean copyNativeBinariesIfNeeded(File apkFile, File sharedLibraryDir) {
        if (!isValidFile(apkFile) || sharedLibraryDir == null) {
            return false;
        }
        Lock lock = sUniqueLock.obtain(sharedLibraryDir.getAbsolutePath());
        lock.lock();
        try {
            String srcChecksum = computeChecksum(apkFile);
            String curChecksum = readChecksum(sharedLibraryDir);
            if (srcChecksum == null || !srcChecksum.equals(curChecksum)) {
                ensureDirectory(sharedLibraryDir);
                boolean result = NativeLibraryHelper.copyNativeBinariesIfNeeded(apkFile, sharedLibraryDir);
                if (result) {
                    writeChecksum(sharedLibraryDir, srcChecksum);
                }
                lock.unlock();
                return result;
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public static boolean removeNativeBinaries(String nativeLibraryPath) {
        return removeNativeBinaries(new File(nativeLibraryPath));
    }

    public static boolean removeNativeBinaries(File nativeLibraryDir) {
        return NativeLibraryHelper.removeNativeBinaries(nativeLibraryDir);
    }

    private static String readChecksum(File dir) {
        if (dir == null) {
            return null;
        }
        File file = new File(dir, CHECKSUM);
        if (!isValidChecksum(file)) {
            return null;
        }
        String checksum = readStringFromFile(file);
        if (TextUtils.isEmpty(checksum)) {
            checksum = null;
        }
        return checksum;
    }

    private static boolean writeChecksum(File dir, String checksum) {
        if (dir == null) {
            return false;
        }
        return writeStringToFile(new File(dir, CHECKSUM), checksum);
    }

    private static String computeChecksum(File file) {
        if (file == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(file.lastModified());
        sb.append(file.length());
        Long crc = fastCrc(file);
        if (crc != null) {
            sb.append(crc);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0042 A[SYNTHETIC, Splitter:B:31:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x004b A[SYNTHETIC, Splitter:B:36:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String readStringFromFile(java.io.File r9) {
        /*
            r5 = 0
            r3 = 0
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x0059, IOException -> 0x0038 }
            r4.<init>(r9)     // Catch:{ FileNotFoundException -> 0x0059, IOException -> 0x0038 }
            r7 = 128(0x80, float:1.794E-43)
            char[] r0 = new char[r7]     // Catch:{ FileNotFoundException -> 0x001b, IOException -> 0x0056, all -> 0x0053 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x001b, IOException -> 0x0056, all -> 0x0053 }
            r6.<init>()     // Catch:{ FileNotFoundException -> 0x001b, IOException -> 0x0056, all -> 0x0053 }
        L_0x0010:
            int r1 = r4.read(r0)     // Catch:{ FileNotFoundException -> 0x001b, IOException -> 0x0056, all -> 0x0053 }
            if (r1 <= 0) goto L_0x002a
            r7 = 0
            r6.append(r0, r7, r1)     // Catch:{ FileNotFoundException -> 0x001b, IOException -> 0x0056, all -> 0x0053 }
            goto L_0x0010
        L_0x001b:
            r2 = move-exception
            r3 = r4
        L_0x001d:
            java.lang.String r7 = "PluginNativeHelper"
            java.lang.String r8 = "cannot find file to read"
            com.tencent.component.utils.log.LogUtil.d(r7, r8, r2)     // Catch:{ all -> 0x0048 }
            if (r3 == 0) goto L_0x0029
            r3.close()     // Catch:{ IOException -> 0x004f }
        L_0x0029:
            return r5
        L_0x002a:
            java.lang.String r5 = r6.toString()     // Catch:{ FileNotFoundException -> 0x001b, IOException -> 0x0056, all -> 0x0053 }
            if (r4 == 0) goto L_0x005b
            r4.close()     // Catch:{ IOException -> 0x0035 }
            r3 = r4
            goto L_0x0029
        L_0x0035:
            r7 = move-exception
            r3 = r4
            goto L_0x0029
        L_0x0038:
            r2 = move-exception
        L_0x0039:
            java.lang.String r7 = "PluginNativeHelper"
            java.lang.String r8 = "error occurs while reading file"
            com.tencent.component.utils.log.LogUtil.d(r7, r8, r2)     // Catch:{ all -> 0x0048 }
            if (r3 == 0) goto L_0x0029
            r3.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x0029
        L_0x0046:
            r7 = move-exception
            goto L_0x0029
        L_0x0048:
            r7 = move-exception
        L_0x0049:
            if (r3 == 0) goto L_0x004e
            r3.close()     // Catch:{ IOException -> 0x0051 }
        L_0x004e:
            throw r7
        L_0x004f:
            r7 = move-exception
            goto L_0x0029
        L_0x0051:
            r8 = move-exception
            goto L_0x004e
        L_0x0053:
            r7 = move-exception
            r3 = r4
            goto L_0x0049
        L_0x0056:
            r2 = move-exception
            r3 = r4
            goto L_0x0039
        L_0x0059:
            r2 = move-exception
            goto L_0x001d
        L_0x005b:
            r3 = r4
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.PluginNativeHelper.readStringFromFile(java.io.File):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x002a A[SYNTHETIC, Splitter:B:20:0x002a] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0033 A[SYNTHETIC, Splitter:B:25:0x0033] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean writeStringToFile(java.io.File r6, java.lang.String r7) {
        /*
            if (r7 != 0) goto L_0x0004
            java.lang.String r7 = ""
        L_0x0004:
            r1 = 0
            r2 = 0
            java.io.File r4 = r6.getParentFile()     // Catch:{ IOException -> 0x0020 }
            ensureDirectory(r4)     // Catch:{ IOException -> 0x0020 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ IOException -> 0x0020 }
            r3.<init>(r6)     // Catch:{ IOException -> 0x0020 }
            r3.write(r7)     // Catch:{ IOException -> 0x003c, all -> 0x0039 }
            r1 = 1
            if (r3 == 0) goto L_0x003f
            r3.close()     // Catch:{ IOException -> 0x001d }
            r2 = r3
        L_0x001c:
            return r1
        L_0x001d:
            r4 = move-exception
            r2 = r3
            goto L_0x001c
        L_0x0020:
            r0 = move-exception
        L_0x0021:
            java.lang.String r4 = "PluginNativeHelper"
            java.lang.String r5 = "error occurs while writing file"
            com.tencent.component.utils.log.LogUtil.d(r4, r5, r0)     // Catch:{ all -> 0x0030 }
            if (r2 == 0) goto L_0x001c
            r2.close()     // Catch:{ IOException -> 0x002e }
            goto L_0x001c
        L_0x002e:
            r4 = move-exception
            goto L_0x001c
        L_0x0030:
            r4 = move-exception
        L_0x0031:
            if (r2 == 0) goto L_0x0036
            r2.close()     // Catch:{ IOException -> 0x0037 }
        L_0x0036:
            throw r4
        L_0x0037:
            r5 = move-exception
            goto L_0x0036
        L_0x0039:
            r4 = move-exception
            r2 = r3
            goto L_0x0031
        L_0x003c:
            r0 = move-exception
            r2 = r3
            goto L_0x0021
        L_0x003f:
            r2 = r3
            goto L_0x001c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.PluginNativeHelper.writeStringToFile(java.io.File, java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0081 A[SYNTHETIC, Splitter:B:32:0x0081] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a8 A[SYNTHETIC, Splitter:B:40:0x00a8] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b1 A[SYNTHETIC, Splitter:B:45:0x00b1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Long fastCrc(java.io.File r20) {
        /*
            r7 = 0
            r11 = 0
            long r2 = computeFastCrcBlockSize(r20)     // Catch:{ FileNotFoundException -> 0x0060, IOException -> 0x0087 }
            long r4 = computeFastCrcSkipSize(r20)     // Catch:{ FileNotFoundException -> 0x0060, IOException -> 0x0087 }
            r16 = 8192(0x2000, double:4.0474E-320)
            long r18 = r20.length()     // Catch:{ FileNotFoundException -> 0x0060, IOException -> 0x0087 }
            int r15 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r15 > 0) goto L_0x0055
            r6 = 8192(0x2000, float:1.14794E-41)
        L_0x0016:
            java.io.FileInputStream r12 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0060, IOException -> 0x0087 }
            r0 = r20
            r12.<init>(r0)     // Catch:{ FileNotFoundException -> 0x0060, IOException -> 0x0087 }
            java.util.zip.CRC32 r8 = new java.util.zip.CRC32     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00ba, all -> 0x00b7 }
            r8.<init>()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00ba, all -> 0x00b7 }
            byte[] r9 = new byte[r6]     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00ba, all -> 0x00b7 }
            r14 = 0
        L_0x0025:
            int r13 = r12.read(r9)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00ba, all -> 0x00b7 }
            if (r13 <= 0) goto L_0x0046
            r15 = 0
            r8.update(r9, r15, r13)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00ba, all -> 0x00b7 }
            int r14 = r14 + r13
            long r0 = (long) r14     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00ba, all -> 0x00b7 }
            r16 = r0
            int r15 = (r16 > r2 ? 1 : (r16 == r2 ? 0 : -1))
            if (r15 < 0) goto L_0x0025
            r14 = 0
            r16 = 0
            int r15 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r15 <= 0) goto L_0x0025
            long r16 = r12.skip(r4)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00ba, all -> 0x00b7 }
            int r15 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1))
            if (r15 == 0) goto L_0x0025
        L_0x0046:
            long r16 = r8.getValue()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00ba, all -> 0x00b7 }
            java.lang.Long r7 = java.lang.Long.valueOf(r16)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00ba, all -> 0x00b7 }
            if (r12 == 0) goto L_0x00c0
            r12.close()     // Catch:{ IOException -> 0x005d }
            r11 = r12
        L_0x0054:
            return r7
        L_0x0055:
            long r16 = r20.length()     // Catch:{ FileNotFoundException -> 0x0060, IOException -> 0x0087 }
            r0 = r16
            int r6 = (int) r0
            goto L_0x0016
        L_0x005d:
            r15 = move-exception
            r11 = r12
            goto L_0x0054
        L_0x0060:
            r10 = move-exception
        L_0x0061:
            java.lang.String r15 = "PluginNativeHelper"
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ae }
            r16.<init>()     // Catch:{ all -> 0x00ae }
            java.lang.String r17 = "Couldn't open file "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x00ae }
            r0 = r16
            r1 = r20
            java.lang.StringBuilder r16 = r0.append(r1)     // Catch:{ all -> 0x00ae }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x00ae }
            r0 = r16
            com.tencent.component.utils.log.LogUtil.i(r15, r0, r10)     // Catch:{ all -> 0x00ae }
            if (r11 == 0) goto L_0x0054
            r11.close()     // Catch:{ IOException -> 0x0085 }
            goto L_0x0054
        L_0x0085:
            r15 = move-exception
            goto L_0x0054
        L_0x0087:
            r10 = move-exception
        L_0x0088:
            java.lang.String r15 = "PluginNativeHelper"
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ae }
            r16.<init>()     // Catch:{ all -> 0x00ae }
            java.lang.String r17 = "Couldn't read file "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x00ae }
            r0 = r16
            r1 = r20
            java.lang.StringBuilder r16 = r0.append(r1)     // Catch:{ all -> 0x00ae }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x00ae }
            r0 = r16
            com.tencent.component.utils.log.LogUtil.i(r15, r0, r10)     // Catch:{ all -> 0x00ae }
            if (r11 == 0) goto L_0x0054
            r11.close()     // Catch:{ IOException -> 0x00ac }
            goto L_0x0054
        L_0x00ac:
            r15 = move-exception
            goto L_0x0054
        L_0x00ae:
            r15 = move-exception
        L_0x00af:
            if (r11 == 0) goto L_0x00b4
            r11.close()     // Catch:{ IOException -> 0x00b5 }
        L_0x00b4:
            throw r15
        L_0x00b5:
            r16 = move-exception
            goto L_0x00b4
        L_0x00b7:
            r15 = move-exception
            r11 = r12
            goto L_0x00af
        L_0x00ba:
            r10 = move-exception
            r11 = r12
            goto L_0x0088
        L_0x00bd:
            r10 = move-exception
            r11 = r12
            goto L_0x0061
        L_0x00c0:
            r11 = r12
            goto L_0x0054
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.PluginNativeHelper.fastCrc(java.io.File):java.lang.Long");
    }

    private static long computeFastCrcBlockSize(File file) {
        return CRC_FAST_BLOCK_SIZE;
    }

    private static long computeFastCrcSkipSize(File file) {
        long totalSkipSize = file.length() - (((long) 4) * CRC_FAST_BLOCK_SIZE);
        if (totalSkipSize <= 0) {
            return 0;
        }
        if (4 > 1) {
            return totalSkipSize / ((long) 3);
        }
        return totalSkipSize;
    }

    private static boolean ensureDirectory(File dir) {
        if (dir == null) {
            return false;
        }
        if (dir.exists() && dir.isDirectory()) {
            return true;
        }
        FileUtil.delete(dir);
        return dir.mkdirs();
    }

    private static boolean isValidChecksum(File checksum) {
        return isValidFile(checksum);
    }

    private static boolean isValidFile(File file) {
        return file != null && file.exists() && file.isFile() && file.length() > 0;
    }
}
