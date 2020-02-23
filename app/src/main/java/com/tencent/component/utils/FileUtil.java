package com.tencent.component.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.tencent.component.utils.log.LogUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    public static final FileComparator SIMPLE_COMPARATOR = new FileComparator() {
        public boolean equals(File lhs, File rhs) {
            return lhs.length() == rhs.length() && lhs.lastModified() == rhs.lastModified();
        }
    };
    public static final int ZIP_BUFFER_SIZE = 4096;
    /* access modifiers changed from: private */
    public static volatile String mSdcardState;
    private static BroadcastReceiver mSdcardStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String unused = FileUtil.mSdcardState = Environment.getExternalStorageState();
        }
    };
    private static final Object sCacheDirLock = new Object();
    private static volatile boolean sHasRegister = false;

    public interface FileComparator {
        boolean equals(File file, File file2);
    }

    public static boolean copyFiles(File src, File dst) {
        return copyFiles(src, dst, (FileFilter) null);
    }

    public static boolean copyFiles(File src, File dst, FileFilter filter) {
        return copyFiles(src, dst, filter, SIMPLE_COMPARATOR);
    }

    public static boolean copyFiles(File src, File dst, FileFilter filter, FileComparator comparator) {
        if (src == null || dst == null) {
            return false;
        }
        if (!src.exists()) {
            return false;
        }
        if (src.isFile()) {
            return performCopyFile(src, dst, filter, comparator);
        }
        File[] paths = src.listFiles();
        if (paths == null) {
            return false;
        }
        boolean result = true;
        for (File sub : paths) {
            if (!copyFiles(sub, new File(dst, sub.getName()), filter)) {
                result = false;
            }
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00dd A[SYNTHETIC, Splitter:B:44:0x00dd] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00e2 A[SYNTHETIC, Splitter:B:47:0x00e2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyFile(java.lang.String r11, java.lang.String r12, boolean r13) throws java.io.IOException {
        /*
            java.io.File r7 = new java.io.File
            r7.<init>(r11)
            boolean r8 = r7.exists()
            if (r8 != 0) goto L_0x0028
            java.io.FileNotFoundException r8 = new java.io.FileNotFoundException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Cannot find the source file: "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = r7.getAbsolutePath()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0028:
            boolean r8 = r7.canRead()
            if (r8 != 0) goto L_0x004b
            java.io.IOException r8 = new java.io.IOException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Cannot read the source file: "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = r7.getAbsolutePath()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x004b:
            java.io.File r1 = new java.io.File
            r1.<init>(r12)
            if (r13 != 0) goto L_0x0059
            boolean r8 = r1.exists()
            if (r8 == 0) goto L_0x00a5
        L_0x0058:
            return
        L_0x0059:
            boolean r8 = r1.exists()
            if (r8 == 0) goto L_0x0082
            boolean r8 = r1.canWrite()
            if (r8 != 0) goto L_0x00a5
            java.io.IOException r8 = new java.io.IOException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Cannot write the destination file: "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = r1.getAbsolutePath()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0082:
            boolean r8 = r1.createNewFile()
            if (r8 != 0) goto L_0x00a5
            java.io.IOException r8 = new java.io.IOException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Cannot write the destination file: "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = r1.getAbsolutePath()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x00a5:
            r2 = 0
            r4 = 0
            r8 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r8]
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ all -> 0x00ec }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ all -> 0x00ec }
            r8.<init>(r7)     // Catch:{ all -> 0x00ec }
            r3.<init>(r8)     // Catch:{ all -> 0x00ec }
            java.io.BufferedOutputStream r5 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x00ee }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ all -> 0x00ee }
            r8.<init>(r1)     // Catch:{ all -> 0x00ee }
            r5.<init>(r8)     // Catch:{ all -> 0x00ee }
        L_0x00bf:
            int r6 = r3.read(r0)     // Catch:{ all -> 0x00d8 }
            r8 = -1
            if (r6 != r8) goto L_0x00d3
            if (r3 == 0) goto L_0x00cb
            r3.close()     // Catch:{ IOException -> 0x00e6 }
        L_0x00cb:
            if (r5 == 0) goto L_0x0058
            r5.close()     // Catch:{ IOException -> 0x00d1 }
            goto L_0x0058
        L_0x00d1:
            r8 = move-exception
            goto L_0x0058
        L_0x00d3:
            r8 = 0
            r5.write(r0, r8, r6)     // Catch:{ all -> 0x00d8 }
            goto L_0x00bf
        L_0x00d8:
            r8 = move-exception
            r4 = r5
            r2 = r3
        L_0x00db:
            if (r2 == 0) goto L_0x00e0
            r2.close()     // Catch:{ IOException -> 0x00e8 }
        L_0x00e0:
            if (r4 == 0) goto L_0x00e5
            r4.close()     // Catch:{ IOException -> 0x00ea }
        L_0x00e5:
            throw r8
        L_0x00e6:
            r8 = move-exception
            goto L_0x00cb
        L_0x00e8:
            r9 = move-exception
            goto L_0x00e0
        L_0x00ea:
            r9 = move-exception
            goto L_0x00e5
        L_0x00ec:
            r8 = move-exception
            goto L_0x00db
        L_0x00ee:
            r8 = move-exception
            r2 = r3
            goto L_0x00db
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.FileUtil.copyFile(java.lang.String, java.lang.String, boolean):void");
    }

    private static boolean performCopyFile(File srcFile, File dstFile, FileFilter filter, FileComparator comparator) {
        if (srcFile == null || dstFile == null) {
            return false;
        }
        if (filter != null && !filter.accept(srcFile)) {
            return false;
        }
        FileChannel inc = null;
        FileChannel ouc = null;
        if (!srcFile.exists() || !srcFile.isFile()) {
            if (inc != null) {
                try {
                    inc.close();
                } catch (Throwable th) {
                }
            }
            if (ouc != null) {
                ouc.close();
            }
            return false;
        }
        if (dstFile.exists()) {
            if (comparator == null || !comparator.equals(srcFile, dstFile)) {
                try {
                    delete(dstFile);
                } catch (Throwable th2) {
                    return false;
                }
            } else {
                if (inc != null) {
                    try {
                        inc.close();
                    } catch (Throwable th3) {
                    }
                }
                if (ouc != null) {
                    ouc.close();
                }
                return true;
            }
        }
        File toParent = dstFile.getParentFile();
        if (toParent.isFile()) {
            delete(toParent);
        }
        if (toParent.exists() || toParent.mkdirs()) {
            inc = new FileInputStream(srcFile).getChannel();
            ouc = new FileOutputStream(dstFile).getChannel();
            ouc.transferFrom(inc, 0, inc.size());
            if (inc != null) {
                try {
                    inc.close();
                } catch (Throwable th4) {
                }
            }
            if (ouc != null) {
                ouc.close();
            }
            return true;
        }
        if (inc != null) {
            try {
                inc.close();
            } catch (Throwable th5) {
            }
        }
        if (ouc != null) {
            ouc.close();
        }
        return false;
    }

    public static void copyAssets(Context context, String assetName, String dst) {
        if (!isEmpty(dst)) {
            if (assetName == null) {
                assetName = "";
            }
            String[] files = null;
            try {
                files = context.getAssets().list(assetName);
            } catch (FileNotFoundException e) {
                if (assetName.length() > 0) {
                    performCopyAssetsFile(context, assetName, dst);
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            if (files != null) {
                if (files.length == 0 && assetName.length() > 0) {
                    performCopyAssetsFile(context, assetName, dst);
                }
                for (String file : files) {
                    if (!isEmpty(file)) {
                        copyAssets(context, assetName.length() == 0 ? file : assetName + File.separator + file, dst + File.separator + file);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:78:0x00cc A[SYNTHETIC, Splitter:B:78:0x00cc] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x00d1 A[Catch:{ Throwable -> 0x0108 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void performCopyAssetsFile(android.content.Context r18, java.lang.String r19, java.lang.String r20) {
        /*
            boolean r14 = isEmpty(r19)
            if (r14 != 0) goto L_0x000c
            boolean r14 = isEmpty(r20)
            if (r14 == 0) goto L_0x000d
        L_0x000c:
            return
        L_0x000d:
            android.content.res.AssetManager r2 = r18.getAssets()
            java.io.File r4 = new java.io.File
            r0 = r20
            r4.<init>(r0)
            r7 = 0
            r9 = 0
            boolean r14 = r4.exists()     // Catch:{ Throwable -> 0x00ae }
            if (r14 == 0) goto L_0x0081
            r13 = 0
            r0 = r19
            android.content.res.AssetFileDescriptor r6 = r2.openFd(r0)     // Catch:{ IOException -> 0x0072 }
            long r14 = r4.length()     // Catch:{ IOException -> 0x0072 }
            long r16 = r6.getLength()     // Catch:{ IOException -> 0x0072 }
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 != 0) goto L_0x0040
            if (r7 == 0) goto L_0x0038
            r7.close()     // Catch:{ Throwable -> 0x003e }
        L_0x0038:
            if (r9 == 0) goto L_0x000c
            r9.close()     // Catch:{ Throwable -> 0x003e }
            goto L_0x000c
        L_0x003e:
            r14 = move-exception
            goto L_0x000c
        L_0x0040:
            boolean r14 = r4.isDirectory()     // Catch:{ IOException -> 0x0072 }
            if (r14 == 0) goto L_0x0049
            delete(r4)     // Catch:{ IOException -> 0x0072 }
        L_0x0049:
            if (r13 == 0) goto L_0x0081
            r0 = r19
            java.io.InputStream r12 = r2.open(r0)     // Catch:{ Throwable -> 0x00ae }
            long r14 = r4.length()     // Catch:{ IOException -> 0x00a9, all -> 0x00c4 }
            int r16 = r12.available()     // Catch:{ IOException -> 0x00a9, all -> 0x00c4 }
            r0 = r16
            long r0 = (long) r0
            r16 = r0
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 != 0) goto L_0x0075
            r12.close()     // Catch:{ Throwable -> 0x00ae }
            if (r7 == 0) goto L_0x006a
            r7.close()     // Catch:{ Throwable -> 0x0070 }
        L_0x006a:
            if (r9 == 0) goto L_0x000c
            r9.close()     // Catch:{ Throwable -> 0x0070 }
            goto L_0x000c
        L_0x0070:
            r14 = move-exception
            goto L_0x000c
        L_0x0072:
            r5 = move-exception
            r13 = 1
            goto L_0x0049
        L_0x0075:
            boolean r14 = r4.isDirectory()     // Catch:{ IOException -> 0x00a9, all -> 0x00c4 }
            if (r14 == 0) goto L_0x007e
            delete(r4)     // Catch:{ IOException -> 0x00a9, all -> 0x00c4 }
        L_0x007e:
            r12.close()     // Catch:{ Throwable -> 0x00ae }
        L_0x0081:
            java.io.File r11 = r4.getParentFile()     // Catch:{ Throwable -> 0x00ae }
            boolean r14 = r11.isFile()     // Catch:{ Throwable -> 0x00ae }
            if (r14 == 0) goto L_0x008e
            delete(r11)     // Catch:{ Throwable -> 0x00ae }
        L_0x008e:
            boolean r14 = r11.exists()     // Catch:{ Throwable -> 0x00ae }
            if (r14 != 0) goto L_0x00d5
            boolean r14 = r11.mkdirs()     // Catch:{ Throwable -> 0x00ae }
            if (r14 != 0) goto L_0x00d5
            if (r7 == 0) goto L_0x009f
            r7.close()     // Catch:{ Throwable -> 0x00a6 }
        L_0x009f:
            if (r9 == 0) goto L_0x000c
            r9.close()     // Catch:{ Throwable -> 0x00a6 }
            goto L_0x000c
        L_0x00a6:
            r14 = move-exception
            goto L_0x000c
        L_0x00a9:
            r14 = move-exception
            r12.close()     // Catch:{ Throwable -> 0x00ae }
            goto L_0x0081
        L_0x00ae:
            r5 = move-exception
        L_0x00af:
            r5.printStackTrace()     // Catch:{ all -> 0x00c9 }
            delete(r4)     // Catch:{ all -> 0x00c9 }
            if (r7 == 0) goto L_0x00ba
            r7.close()     // Catch:{ Throwable -> 0x00c1 }
        L_0x00ba:
            if (r9 == 0) goto L_0x000c
            r9.close()     // Catch:{ Throwable -> 0x00c1 }
            goto L_0x000c
        L_0x00c1:
            r14 = move-exception
            goto L_0x000c
        L_0x00c4:
            r14 = move-exception
            r12.close()     // Catch:{ Throwable -> 0x00ae }
            throw r14     // Catch:{ Throwable -> 0x00ae }
        L_0x00c9:
            r14 = move-exception
        L_0x00ca:
            if (r7 == 0) goto L_0x00cf
            r7.close()     // Catch:{ Throwable -> 0x0108 }
        L_0x00cf:
            if (r9 == 0) goto L_0x00d4
            r9.close()     // Catch:{ Throwable -> 0x0108 }
        L_0x00d4:
            throw r14
        L_0x00d5:
            r0 = r19
            java.io.InputStream r7 = r2.open(r0)     // Catch:{ Throwable -> 0x00ae }
            java.io.BufferedOutputStream r10 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x00ae }
            java.io.FileOutputStream r14 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x00ae }
            r14.<init>(r4)     // Catch:{ Throwable -> 0x00ae }
            r10.<init>(r14)     // Catch:{ Throwable -> 0x00ae }
            r14 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r14]     // Catch:{ Throwable -> 0x00f4, all -> 0x010a }
        L_0x00e9:
            int r8 = r7.read(r3)     // Catch:{ Throwable -> 0x00f4, all -> 0x010a }
            if (r8 <= 0) goto L_0x00f7
            r14 = 0
            r10.write(r3, r14, r8)     // Catch:{ Throwable -> 0x00f4, all -> 0x010a }
            goto L_0x00e9
        L_0x00f4:
            r5 = move-exception
            r9 = r10
            goto L_0x00af
        L_0x00f7:
            if (r7 == 0) goto L_0x00fc
            r7.close()     // Catch:{ Throwable -> 0x0104 }
        L_0x00fc:
            if (r10 == 0) goto L_0x0101
            r10.close()     // Catch:{ Throwable -> 0x0104 }
        L_0x0101:
            r9 = r10
            goto L_0x000c
        L_0x0104:
            r14 = move-exception
            r9 = r10
            goto L_0x000c
        L_0x0108:
            r15 = move-exception
            goto L_0x00d4
        L_0x010a:
            r14 = move-exception
            r9 = r10
            goto L_0x00ca
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.FileUtil.performCopyAssetsFile(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static void delete(File file) {
        delete(file, false);
    }

    public static void delete(File file, boolean ignoreDir) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File f : fileList) {
                    delete(f, ignoreDir);
                }
                if (!ignoreDir) {
                    file.delete();
                }
            }
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean zip(File[] srcFiles, FileOutputStream dest) {
        if (srcFiles == null || srcFiles.length < 1 || dest == null) {
            return false;
        }
        ZipOutputStream zos = null;
        try {
            byte[] buffer = new byte[4096];
            ZipOutputStream zos2 = new ZipOutputStream(new BufferedOutputStream(dest));
            try {
                for (File src : srcFiles) {
                    doZip(zos2, src, (String) null, buffer);
                }
                zos2.flush();
                zos2.closeEntry();
                IOUtils.closeQuietly((Closeable) zos2);
                ZipOutputStream zipOutputStream = zos2;
                return true;
            } catch (IOException e) {
                zos = zos2;
                IOUtils.closeQuietly((Closeable) zos);
                return false;
            } catch (Throwable th) {
                th = th;
                zos = zos2;
                IOUtils.closeQuietly((Closeable) zos);
                throw th;
            }
        } catch (IOException e2) {
            IOUtils.closeQuietly((Closeable) zos);
            return false;
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly((Closeable) zos);
            throw th;
        }
    }

    public static boolean zip(File[] srcFiles, File dest) {
        try {
            return zip(srcFiles, new FileOutputStream(dest));
        } catch (FileNotFoundException e) {
            LogUtil.e("FileUtil", e.getMessage(), e);
            return false;
        }
    }

    public static boolean zip(File src, File dest) {
        return zip(new File[]{src}, dest);
    }

    public static boolean unzip(File src, File destFolder) {
        if (src == null || src.length() < 1 || !src.canRead()) {
            return false;
        }
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
        ZipInputStream zis = null;
        BufferedOutputStream bos = null;
        byte[] buffer = new byte[8192];
        try {
            ZipInputStream zis2 = new ZipInputStream(new FileInputStream(src));
            BufferedOutputStream bos2 = null;
            while (true) {
                try {
                    ZipEntry entry = zis2.getNextEntry();
                    if (entry != null) {
                        String entryName = entry.getName();
                        if (entryName.contains("../")) {
                            throw new Exception("unsecurity zipFile!");
                        }
                        if (entry.isDirectory()) {
                            new File(destFolder, entryName).mkdirs();
                            bos = bos2;
                        } else {
                            File entryFile = new File(destFolder, entryName);
                            entryFile.getParentFile().mkdirs();
                            bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                            while (true) {
                                try {
                                    int readLen = zis2.read(buffer, 0, buffer.length);
                                    if (-1 == readLen) {
                                        break;
                                    }
                                    bos.write(buffer, 0, readLen);
                                } catch (IOException e) {
                                    zis = zis2;
                                    IOUtils.closeQuietly((Closeable) bos);
                                    IOUtils.closeQuietly((Closeable) zis);
                                    return false;
                                } catch (Exception e2) {
                                    zis = zis2;
                                    IOUtils.closeQuietly((Closeable) bos);
                                    IOUtils.closeQuietly((Closeable) zis);
                                    return false;
                                } catch (Throwable th) {
                                    th = th;
                                    zis = zis2;
                                    IOUtils.closeQuietly((Closeable) bos);
                                    IOUtils.closeQuietly((Closeable) zis);
                                    throw th;
                                }
                            }
                            bos.flush();
                            bos.close();
                        }
                        bos2 = bos;
                    } else {
                        zis2.closeEntry();
                        zis2.close();
                        IOUtils.closeQuietly((Closeable) bos2);
                        IOUtils.closeQuietly((Closeable) zis2);
                        BufferedOutputStream bufferedOutputStream = bos2;
                        ZipInputStream zipInputStream = zis2;
                        return true;
                    }
                } catch (IOException e3) {
                    bos = bos2;
                    zis = zis2;
                    IOUtils.closeQuietly((Closeable) bos);
                    IOUtils.closeQuietly((Closeable) zis);
                    return false;
                } catch (Exception e4) {
                    bos = bos2;
                    zis = zis2;
                    IOUtils.closeQuietly((Closeable) bos);
                    IOUtils.closeQuietly((Closeable) zis);
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    bos = bos2;
                    zis = zis2;
                    IOUtils.closeQuietly((Closeable) bos);
                    IOUtils.closeQuietly((Closeable) zis);
                    throw th;
                }
            }
        } catch (IOException e5) {
            IOUtils.closeQuietly((Closeable) bos);
            IOUtils.closeQuietly((Closeable) zis);
            return false;
        } catch (Exception e6) {
            IOUtils.closeQuietly((Closeable) bos);
            IOUtils.closeQuietly((Closeable) zis);
            return false;
        } catch (Throwable th3) {
            th = th3;
            IOUtils.closeQuietly((Closeable) bos);
            IOUtils.closeQuietly((Closeable) zis);
            throw th;
        }
    }

    public static void doZip(ZipOutputStream zos, File file, String root, byte[] buffer) throws IOException {
        String rootName;
        if (zos == null || file == null) {
            throw new IOException("I/O Object got NullPointerException");
        } else if (!file.exists()) {
            throw new FileNotFoundException("Target File is missing");
        } else {
            BufferedInputStream bis = null;
            if (TextUtils.isEmpty(root)) {
                rootName = file.getName();
            } else {
                rootName = root + File.separator + file.getName();
            }
            if (file.isFile()) {
                try {
                    BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(file));
                    try {
                        zos.putNextEntry(new ZipEntry(rootName));
                        while (true) {
                            int readLen = bis2.read(buffer, 0, buffer.length);
                            if (-1 != readLen) {
                                zos.write(buffer, 0, readLen);
                            } else {
                                IOUtils.closeQuietly((Closeable) bis2);
                                BufferedInputStream bufferedInputStream = bis2;
                                return;
                            }
                        }
                    } catch (IOException e) {
                        e = e;
                        bis = bis2;
                        IOUtils.closeQuietly((Closeable) bis);
                        throw e;
                    }
                } catch (IOException e2) {
                    e = e2;
                    IOUtils.closeQuietly((Closeable) bis);
                    throw e;
                }
            } else if (file.isDirectory()) {
                for (File subFile : file.listFiles()) {
                    doZip(zos, subFile, rootName, buffer);
                }
            }
        }
    }

    public static boolean unjar(File src, File destFolder) {
        if (src == null || src.length() < 1 || !src.canRead()) {
            return false;
        }
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
        JarInputStream zis = null;
        BufferedOutputStream bos = null;
        byte[] buffer = new byte[8192];
        try {
            JarInputStream zis2 = new JarInputStream(new FileInputStream(src));
            BufferedOutputStream bos2 = null;
            while (true) {
                try {
                    JarEntry entry = zis2.getNextJarEntry();
                    if (entry != null) {
                        String entryName = entry.getName();
                        if (entryName.contains("../")) {
                            throw new Exception("unsecurity zipFile!");
                        }
                        if (entry.isDirectory()) {
                            new File(destFolder, entryName).mkdirs();
                            bos = bos2;
                        } else {
                            bos = new BufferedOutputStream(new FileOutputStream(new File(destFolder, entryName)));
                            while (true) {
                                try {
                                    int readLen = zis2.read(buffer, 0, buffer.length);
                                    if (-1 == readLen) {
                                        break;
                                    }
                                    bos.write(buffer, 0, readLen);
                                } catch (IOException e) {
                                    zis = zis2;
                                    IOUtils.closeQuietly((Closeable) bos);
                                    IOUtils.closeQuietly((Closeable) zis);
                                    return false;
                                } catch (Exception e2) {
                                    zis = zis2;
                                    IOUtils.closeQuietly((Closeable) bos);
                                    IOUtils.closeQuietly((Closeable) zis);
                                    return false;
                                } catch (Throwable th) {
                                    th = th;
                                    zis = zis2;
                                    IOUtils.closeQuietly((Closeable) bos);
                                    IOUtils.closeQuietly((Closeable) zis);
                                    throw th;
                                }
                            }
                            bos.flush();
                            bos.close();
                        }
                        bos2 = bos;
                    } else {
                        zis2.closeEntry();
                        zis2.close();
                        IOUtils.closeQuietly((Closeable) bos2);
                        IOUtils.closeQuietly((Closeable) zis2);
                        BufferedOutputStream bufferedOutputStream = bos2;
                        JarInputStream jarInputStream = zis2;
                        return true;
                    }
                } catch (IOException e3) {
                    bos = bos2;
                    zis = zis2;
                    IOUtils.closeQuietly((Closeable) bos);
                    IOUtils.closeQuietly((Closeable) zis);
                    return false;
                } catch (Exception e4) {
                    bos = bos2;
                    zis = zis2;
                    IOUtils.closeQuietly((Closeable) bos);
                    IOUtils.closeQuietly((Closeable) zis);
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    bos = bos2;
                    zis = zis2;
                    IOUtils.closeQuietly((Closeable) bos);
                    IOUtils.closeQuietly((Closeable) zis);
                    throw th;
                }
            }
        } catch (IOException e5) {
            IOUtils.closeQuietly((Closeable) bos);
            IOUtils.closeQuietly((Closeable) zis);
            return false;
        } catch (Exception e6) {
            IOUtils.closeQuietly((Closeable) bos);
            IOUtils.closeQuietly((Closeable) zis);
            return false;
        } catch (Throwable th3) {
            th = th3;
            IOUtils.closeQuietly((Closeable) bos);
            IOUtils.closeQuietly((Closeable) zis);
            throw th;
        }
    }

    public static boolean isExistFile(String uploadFilePath) {
        if (TextUtils.isEmpty(uploadFilePath)) {
            return false;
        }
        try {
            File file = new File(uploadFilePath);
            if (!file.exists() || !file.isFile() || file.length() == 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            LogUtil.e("UploadTask", e.getMessage(), e);
            return false;
        }
    }

    static class InnerEnvironment {
        private static final String EXTEND_SUFFIX = "-ext";
        private static final File EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY = new File(new File(Environment.getExternalStorageDirectory(), "Android"), ShareConstants.WEB_DIALOG_PARAM_DATA);
        private static final String TAG = "InnerEnvironment";

        InnerEnvironment() {
        }

        public static File getExternalStorageAndroidDataDir() {
            return EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY;
        }

        public static File getExternalStorageAppCacheDirectory(String packageName) {
            return new File(new File(EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY, packageName), "cache");
        }

        public static File getExternalStorageAppFilesDirectory(String packageName) {
            return new File(new File(EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY, packageName), "files");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            return r1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.io.File getExternalCacheDir(android.content.Context r6, boolean r7) {
            /*
                if (r7 != 0) goto L_0x000f
                int r2 = com.tencent.component.utils.PlatformUtil.version()
                r3 = 8
                if (r2 < r3) goto L_0x000f
                java.io.File r1 = r6.getExternalCacheDir()
            L_0x000e:
                return r1
            L_0x000f:
                java.lang.Class<com.tencent.component.utils.FileUtil$InnerEnvironment> r3 = com.tencent.component.utils.FileUtil.InnerEnvironment.class
                monitor-enter(r3)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0053 }
                r2.<init>()     // Catch:{ all -> 0x0053 }
                java.lang.String r4 = r6.getPackageName()     // Catch:{ all -> 0x0053 }
                java.lang.StringBuilder r4 = r2.append(r4)     // Catch:{ all -> 0x0053 }
                if (r7 == 0) goto L_0x0056
                java.lang.String r2 = "-ext"
            L_0x0023:
                java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x0053 }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0053 }
                java.io.File r1 = getExternalStorageAppCacheDirectory(r2)     // Catch:{ all -> 0x0053 }
                boolean r2 = r1.exists()     // Catch:{ all -> 0x0053 }
                if (r2 != 0) goto L_0x005e
                java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x0059 }
                java.io.File r4 = getExternalStorageAndroidDataDir()     // Catch:{ IOException -> 0x0059 }
                java.lang.String r5 = ".nomedia"
                r2.<init>(r4, r5)     // Catch:{ IOException -> 0x0059 }
                r2.createNewFile()     // Catch:{ IOException -> 0x0059 }
            L_0x0043:
                boolean r2 = r1.mkdirs()     // Catch:{ all -> 0x0053 }
                if (r2 != 0) goto L_0x005e
                java.lang.String r2 = "InnerEnvironment"
                java.lang.String r4 = "Unable to create external cache directory"
                android.util.Log.w(r2, r4)     // Catch:{ all -> 0x0053 }
                r1 = 0
                monitor-exit(r3)     // Catch:{ all -> 0x0053 }
                goto L_0x000e
            L_0x0053:
                r2 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0053 }
                throw r2
            L_0x0056:
                java.lang.String r2 = ""
                goto L_0x0023
            L_0x0059:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ all -> 0x0053 }
                goto L_0x0043
            L_0x005e:
                monitor-exit(r3)     // Catch:{ all -> 0x0053 }
                goto L_0x000e
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.FileUtil.InnerEnvironment.getExternalCacheDir(android.content.Context, boolean):java.io.File");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
            return r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.io.File getExternalFilesDir(android.content.Context r7, java.lang.String r8, boolean r9) {
            /*
                r2 = 0
                if (r9 != 0) goto L_0x0010
                int r3 = com.tencent.component.utils.PlatformUtil.version()
                r4 = 8
                if (r3 < r4) goto L_0x0010
                java.io.File r1 = r7.getExternalFilesDir(r8)
            L_0x000f:
                return r1
            L_0x0010:
                java.lang.Class<com.tencent.component.utils.FileUtil$InnerEnvironment> r4 = com.tencent.component.utils.FileUtil.InnerEnvironment.class
                monitor-enter(r4)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x005b }
                r3.<init>()     // Catch:{ all -> 0x005b }
                java.lang.String r5 = r7.getPackageName()     // Catch:{ all -> 0x005b }
                java.lang.StringBuilder r5 = r3.append(r5)     // Catch:{ all -> 0x005b }
                if (r9 == 0) goto L_0x0054
                java.lang.String r3 = "-ext"
            L_0x0024:
                java.lang.StringBuilder r3 = r5.append(r3)     // Catch:{ all -> 0x005b }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x005b }
                java.io.File r1 = getExternalStorageAppFilesDirectory(r3)     // Catch:{ all -> 0x005b }
                boolean r3 = r1.exists()     // Catch:{ all -> 0x005b }
                if (r3 != 0) goto L_0x0057
                java.io.File r3 = new java.io.File     // Catch:{ IOException -> 0x008d }
                java.io.File r5 = getExternalStorageAndroidDataDir()     // Catch:{ IOException -> 0x008d }
                java.lang.String r6 = ".nomedia"
                r3.<init>(r5, r6)     // Catch:{ IOException -> 0x008d }
                r3.createNewFile()     // Catch:{ IOException -> 0x008d }
            L_0x0044:
                boolean r3 = r1.mkdirs()     // Catch:{ all -> 0x005b }
                if (r3 != 0) goto L_0x0057
                java.lang.String r3 = "InnerEnvironment"
                java.lang.String r5 = "Unable to create external files directory"
                android.util.Log.w(r3, r5)     // Catch:{ all -> 0x005b }
                monitor-exit(r4)     // Catch:{ all -> 0x005b }
                r1 = r2
                goto L_0x000f
            L_0x0054:
                java.lang.String r3 = ""
                goto L_0x0024
            L_0x0057:
                if (r8 != 0) goto L_0x005e
                monitor-exit(r4)     // Catch:{ all -> 0x005b }
                goto L_0x000f
            L_0x005b:
                r2 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x005b }
                throw r2
            L_0x005e:
                java.io.File r0 = new java.io.File     // Catch:{ all -> 0x005b }
                r0.<init>(r1, r8)     // Catch:{ all -> 0x005b }
                boolean r3 = r0.exists()     // Catch:{ all -> 0x005b }
                if (r3 != 0) goto L_0x008a
                boolean r3 = r0.mkdirs()     // Catch:{ all -> 0x005b }
                if (r3 != 0) goto L_0x008a
                java.lang.String r3 = "InnerEnvironment"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005b }
                r5.<init>()     // Catch:{ all -> 0x005b }
                java.lang.String r6 = "Unable to create external media directory "
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x005b }
                java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ all -> 0x005b }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x005b }
                android.util.Log.w(r3, r5)     // Catch:{ all -> 0x005b }
                monitor-exit(r4)     // Catch:{ all -> 0x005b }
                r1 = r2
                goto L_0x000f
            L_0x008a:
                monitor-exit(r4)     // Catch:{ all -> 0x005b }
                r1 = r0
                goto L_0x000f
            L_0x008d:
                r3 = move-exception
                goto L_0x0044
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.FileUtil.InnerEnvironment.getExternalFilesDir(android.content.Context, java.lang.String, boolean):java.io.File");
        }
    }

    private static String getCacheDir(Context context, String name) {
        return getCacheDir(context, name, false);
    }

    public static String getCacheDir(Context context, String name, boolean persist) {
        String dir = getExternalCacheDir(context, name, persist);
        return dir != null ? dir : getInternalCacheDir(context, name, persist);
    }

    public static String getExternalCacheDir(Context context, String name) {
        return getExternalCacheDir(context, name, false);
    }

    public static String getExternalCacheDir(Context context, String name, boolean persist) {
        String dir = getExternalCacheDir(context, persist);
        if (dir == null) {
            return null;
        }
        if (isEmpty(name)) {
            return dir;
        }
        File file = new File(dir + File.separator + name);
        if (!file.exists() || !file.isDirectory()) {
            synchronized (sCacheDirLock) {
                if (!file.isDirectory()) {
                    delete(file);
                    file.mkdirs();
                } else if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
        return file.getAbsolutePath();
    }

    private static String getExternalCacheDir(Context context, boolean persist) {
        File externalDir;
        if (!isExternalAvailable()) {
            return null;
        }
        if (!persist) {
            externalDir = InnerEnvironment.getExternalCacheDir(context, false);
        } else {
            externalDir = InnerEnvironment.getExternalFilesDir(context, "cache", false);
        }
        if (externalDir != null) {
            return externalDir.getAbsolutePath();
        }
        return null;
    }

    public static String getExternalCacheDirExt(Context context, String name) {
        return getExternalCacheDirExt(context, name, false);
    }

    public static String getExternalCacheDirExt(Context context, String name, boolean persist) {
        String dir = getExternalCacheDirExt(context, persist);
        if (dir == null) {
            return null;
        }
        if (isEmpty(name)) {
            return dir;
        }
        File file = new File(dir + File.separator + name);
        if (!file.exists() || !file.isDirectory()) {
            synchronized (sCacheDirLock) {
                if (!file.isDirectory()) {
                    delete(file);
                    file.mkdirs();
                } else if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
        return file.getAbsolutePath();
    }

    private static String getExternalCacheDirExt(Context context, boolean persist) {
        File externalDir;
        if (!isExternalAvailable()) {
            return null;
        }
        if (!persist) {
            externalDir = InnerEnvironment.getExternalCacheDir(context, true);
        } else {
            externalDir = InnerEnvironment.getExternalFilesDir(context, "cache", true);
        }
        if (externalDir != null) {
            return externalDir.getAbsolutePath();
        }
        return null;
    }

    public static String getInternalCacheDir(Context context, String name) {
        return getInternalCacheDir(context, name, false);
    }

    public static String getInternalCacheDir(Context context, String name, boolean persist) {
        String dir = getInternalCacheDir(context, persist);
        if (isEmpty(name)) {
            return dir;
        }
        File file = new File(dir + File.separator + name);
        if (!file.exists() || !file.isDirectory()) {
            synchronized (sCacheDirLock) {
                if (!file.isDirectory()) {
                    delete(file);
                    file.mkdirs();
                } else if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
        return file.getAbsolutePath();
    }

    public static String getInternalCacheDir(Context context, boolean persist) {
        if (!persist) {
            return context.getCacheDir().getAbsolutePath();
        }
        return context.getFilesDir().getAbsolutePath() + File.separator + "cache";
    }

    public static String getInternalFileDir(Context context, boolean persist) {
        if (!persist) {
            return context.getCacheDir().getAbsolutePath();
        }
        return context.getFilesDir().getAbsolutePath() + File.separator;
    }

    public static boolean isExternal(String path) {
        return path != null && path.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    public static boolean isInternal(String path) {
        return path != null && path.startsWith(Environment.getDataDirectory().getAbsolutePath());
    }

    public static boolean isExternalAvailable() {
        String state = mSdcardState;
        if (state == null) {
            state = Environment.getExternalStorageState();
            mSdcardState = state;
        }
        return "mounted".equals(state);
    }

    private static void registerSdcardReceiver(Context context) {
        try {
            if (!sHasRegister) {
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
                filter.addAction("android.intent.action.MEDIA_EJECT");
                filter.addAction("android.intent.action.MEDIA_MOUNTED");
                filter.addAction("android.intent.action.MEDIA_REMOVED");
                filter.addAction("android.intent.action.MEDIA_UNMOUNTED");
                filter.addDataScheme(TransferTable.COLUMN_FILE);
                context.registerReceiver(mSdcardStateReceiver, filter);
                sHasRegister = true;
            }
        } catch (Throwable e) {
            LogUtil.e("FileUtil", "regist sdcard receiver failed. " + e.getMessage(), e);
        }
    }

    public static void init(Context context) {
        registerSdcardReceiver(context.getApplicationContext());
    }

    public static void renameFile(String from, String to) {
        if (from != null && to != null) {
            File fromFile = new File(from);
            if (fromFile.exists()) {
                fromFile.renameTo(new File(to));
            }
        }
    }

    public static String getParentFilePath(String filePath) {
        if (filePath == null) {
            LogUtil.e("FileUtil", "filePath is null");
            return null;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return file.getParent();
        }
        LogUtil.i("FileUtil", "file is not exist");
        return null;
    }
}
