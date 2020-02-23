package com.tencent.smtt.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import com.appsflyer.share.Constants;
import com.facebook.share.internal.ShareConstants;
import com.tencent.component.debug.FileTracerConfig;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsDownloader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@SuppressLint({"NewApi"})
public class k {
    public static String a = null;
    public static final a b = new m();
    private static final int c = "lib/".length();
    private static RandomAccessFile d = null;

    public interface a {
        boolean a(File file, File file2);
    }

    public interface b {
        boolean a(InputStream inputStream, ZipEntry zipEntry, String str);
    }

    public static long a(InputStream inputStream, OutputStream outputStream) {
        if (inputStream == null) {
            return -1;
        }
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    public static ByteArrayOutputStream a(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b(inputStream, (OutputStream) byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    public static File a() {
        try {
            return Environment.getExternalStorageDirectory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File a(Context context, boolean z, String str) {
        String d2 = z ? d(context) : c(context);
        if (d2 == null) {
            return null;
        }
        File file = new File(d2);
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        if (!file.canWrite()) {
            return null;
        }
        File file2 = new File(file, str);
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file2;
    }

    public static String a(Context context, int i) {
        return a(context, context.getApplicationInfo().packageName, i, true);
    }

    private static String a(Context context, String str) {
        if (context == null) {
            return "";
        }
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        try {
            return context.getExternalFilesDir(str).getAbsolutePath();
        } catch (Throwable th) {
            th.printStackTrace();
            try {
                return Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + ShareConstants.WEB_DIALOG_PARAM_DATA + File.separator + context.getApplicationInfo().packageName + File.separator + "files" + File.separator + str;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    public static String a(Context context, String str, int i, boolean z) {
        String str2;
        if (context == null) {
            return "";
        }
        try {
            str2 = Environment.getExternalStorageDirectory() + File.separator;
        } catch (Exception e) {
            e.printStackTrace();
            str2 = "";
        }
        switch (i) {
            case 1:
                return !str2.equals("") ? str2 + "tencent" + File.separator + "tbs" + File.separator + str : str2;
            case 2:
                return !str2.equals("") ? str2 + "tbs" + File.separator + "backup" + File.separator + str : str2;
            case 3:
                return !str2.equals("") ? str2 + "tencent" + File.separator + "tbs" + File.separator + "backup" + File.separator + str : str2;
            case 4:
                if (str2.equals("")) {
                    return a(context, "backup");
                }
                String str3 = str2 + "tencent" + File.separator + "tbs" + File.separator + "backup" + File.separator + str;
                if (!z) {
                    return str3;
                }
                File file = new File(str3);
                if (file.exists() && file.canWrite()) {
                    return str3;
                }
                if (file.exists()) {
                    return a(context, "backup");
                }
                file.mkdirs();
                return !file.canWrite() ? a(context, "backup") : str3;
            case 5:
                return !str2.equals("") ? str2 + "tencent" + File.separator + "tbs" + File.separator + str : str2;
            case 6:
                if (a != null) {
                    return a;
                }
                a = a(context, "tbslog");
                return a;
            default:
                return "";
        }
    }

    public static FileLock a(Context context, FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) {
            return null;
        }
        try {
            FileLock tryLock = fileOutputStream.getChannel().tryLock();
            if (tryLock.isValid()) {
                return tryLock;
            }
            return null;
        } catch (OverlappingFileLockException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static synchronized void a(Context context, FileLock fileLock) {
        synchronized (k.class) {
            FileChannel channel = fileLock.channel();
            if (channel != null && channel.isOpen()) {
                try {
                    fileLock.release();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return;
    }

    public static void a(File file, boolean z) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File a2 : listFiles) {
                    a(a2, z);
                }
                if (!z) {
                    file.delete();
                }
            }
        }
    }

    public static void a(File file, boolean z, String str) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (!file2.getName().equals(str)) {
                        a(file2, z);
                    }
                }
                if (!z) {
                    file.delete();
                }
            }
        }
    }

    public static void a(FileLock fileLock, FileOutputStream fileOutputStream) {
        if (fileLock != null) {
            try {
                FileChannel channel = fileLock.channel();
                if (channel != null && channel.isOpen()) {
                    fileLock.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static boolean a(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (context != null) {
            return context.getApplicationContext().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        }
        return false;
    }

    public static boolean a(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        b(file);
        return file.mkdirs();
    }

    public static boolean a(File file, File file2) {
        return a(file.getPath(), file2.getPath());
    }

    public static boolean a(File file, File file2, FileFilter fileFilter) {
        return a(file, file2, fileFilter, b);
    }

    public static boolean a(File file, File file2, FileFilter fileFilter, a aVar) {
        if (file == null || file2 == null || !file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return b(file, file2, fileFilter, aVar);
        }
        File[] listFiles = file.listFiles(fileFilter);
        if (listFiles == null) {
            return false;
        }
        boolean z = true;
        for (File file3 : listFiles) {
            if (!a(file3, new File(file2, file3.getName()), fileFilter)) {
                z = false;
            }
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0054  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r9, long r10, long r12, long r14) {
        /*
            r0 = 1
            r1 = 0
            java.io.File r4 = new java.io.File
            r4.<init>(r9)
            long r2 = r4.length()
            int r2 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r2 == 0) goto L_0x0036
            java.lang.String r1 = "FileHelper"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "file size doesn't match: "
            java.lang.StringBuilder r2 = r2.append(r3)
            long r4 = r4.length()
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r3 = " vs "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r10)
            java.lang.String r2 = r2.toString()
            com.tencent.smtt.utils.TbsLog.e(r1, r2)
        L_0x0035:
            return r0
        L_0x0036:
            r3 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x009d }
            r2.<init>(r4)     // Catch:{ all -> 0x009d }
            java.util.zip.CRC32 r3 = new java.util.zip.CRC32     // Catch:{ all -> 0x0050 }
            r3.<init>()     // Catch:{ all -> 0x0050 }
            r5 = 8192(0x2000, float:1.14794E-41)
            byte[] r5 = new byte[r5]     // Catch:{ all -> 0x0050 }
        L_0x0045:
            int r6 = r2.read(r5)     // Catch:{ all -> 0x0050 }
            if (r6 <= 0) goto L_0x0058
            r7 = 0
            r3.update(r5, r7, r6)     // Catch:{ all -> 0x0050 }
            goto L_0x0045
        L_0x0050:
            r0 = move-exception
            r1 = r2
        L_0x0052:
            if (r1 == 0) goto L_0x0057
            r1.close()
        L_0x0057:
            throw r0
        L_0x0058:
            long r6 = r3.getValue()     // Catch:{ all -> 0x0050 }
            java.lang.String r3 = "FileHelper"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0050 }
            r5.<init>()     // Catch:{ all -> 0x0050 }
            java.lang.String r8 = ""
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ all -> 0x0050 }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x0050 }
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ all -> 0x0050 }
            java.lang.String r5 = ": crc = "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0050 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ all -> 0x0050 }
            java.lang.String r5 = ", zipCrc = "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0050 }
            java.lang.StringBuilder r4 = r4.append(r14)     // Catch:{ all -> 0x0050 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0050 }
            com.tencent.smtt.utils.TbsLog.i(r3, r4)     // Catch:{ all -> 0x0050 }
            int r3 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r3 == 0) goto L_0x0096
            if (r2 == 0) goto L_0x0035
            r2.close()
            goto L_0x0035
        L_0x0096:
            if (r2 == 0) goto L_0x009b
            r2.close()
        L_0x009b:
            r0 = r1
            goto L_0x0035
        L_0x009d:
            r0 = move-exception
            r1 = r3
            goto L_0x0052
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.k.a(java.lang.String, long, long, long):boolean");
    }

    @SuppressLint({"InlinedApi"})
    public static boolean a(String str, String str2) {
        return a(str, str2, Build.CPU_ABI, Build.VERSION.SDK_INT >= 8 ? Build.CPU_ABI2 : null, r.a("ro.product.cpu.upgradeabi", "armeabi"));
    }

    private static boolean a(String str, String str2, String str3, String str4, b bVar) {
        ZipFile zipFile;
        InputStream inputStream;
        try {
            zipFile = new ZipFile(str);
            boolean z = false;
            boolean z2 = false;
            try {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                    String name = zipEntry.getName();
                    if (name != null && (name.startsWith("lib/") || name.startsWith("assets/"))) {
                        String substring = name.substring(name.lastIndexOf(47));
                        if (substring.endsWith(".so")) {
                            if (!name.regionMatches(c, str2, 0, str2.length()) || name.charAt(c + str2.length()) != '/') {
                                if (str3 != null) {
                                    if (name.regionMatches(c, str3, 0, str3.length()) && name.charAt(c + str3.length()) == '/') {
                                        z2 = true;
                                        if (z) {
                                        }
                                    }
                                }
                                if (str4 != null && name.regionMatches(c, str4, 0, str4.length()) && name.charAt(c + str4.length()) == '/' && !z) {
                                    if (z2) {
                                    }
                                }
                            } else {
                                z = true;
                            }
                        }
                        inputStream = zipFile.getInputStream(zipEntry);
                        if (!bVar.a(inputStream, zipEntry, substring.substring(1))) {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (zipFile == null) {
                                return false;
                            }
                            zipFile.close();
                            return false;
                        } else if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                }
                if (zipFile != null) {
                    zipFile.close();
                }
                return true;
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            zipFile = null;
            if (zipFile != null) {
                zipFile.close();
            }
            throw th;
        }
    }

    private static boolean a(String str, String str2, String str3, String str4, String str5) {
        return a(str, str3, str4, str5, (b) new l(str2));
    }

    public static int b(InputStream inputStream, OutputStream outputStream) {
        long a2 = a(inputStream, outputStream);
        if (a2 > 2147483647L) {
            return -1;
        }
        return (int) a2;
    }

    public static FileOutputStream b(Context context, boolean z, String str) {
        File a2 = a(context, z, str);
        if (a2 != null) {
            try {
                return new FileOutputStream(a2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void b(File file) {
        a(file, false);
    }

    public static boolean b() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean b(Context context) {
        long a2 = aa.a();
        boolean z = a2 >= TbsDownloadConfig.getInstance(context).getDownloadMinFreeSpace();
        if (!z) {
            TbsLog.e(TbsDownloader.LOGTAG, "[TbsApkDwonloader.hasEnoughFreeSpace] freeSpace too small,  freeSpace = " + a2);
        }
        return z;
    }

    public static boolean b(File file, File file2) {
        return a(file, file2, (FileFilter) null);
    }

    private static boolean b(File file, File file2, FileFilter fileFilter, a aVar) {
        FileChannel fileChannel;
        Throwable th;
        FileChannel fileChannel2;
        FileChannel fileChannel3 = null;
        if (file == null || file2 == null) {
            return false;
        }
        if (fileFilter != null && !fileFilter.accept(file)) {
            return false;
        }
        try {
            if (!file.exists() || !file.isFile()) {
                if (fileChannel3 != null) {
                    fileChannel3.close();
                }
                if (fileChannel3 != null) {
                    fileChannel3.close();
                }
                return false;
            }
            if (file2.exists()) {
                if (aVar == null || !aVar.a(file, file2)) {
                    b(file2);
                } else {
                    if (fileChannel3 != null) {
                        fileChannel3.close();
                    }
                    if (fileChannel3 != null) {
                        fileChannel3.close();
                    }
                    return true;
                }
            }
            File parentFile = file2.getParentFile();
            if (parentFile.isFile()) {
                b(parentFile);
            }
            if (parentFile.exists() || parentFile.mkdirs()) {
                fileChannel2 = new FileInputStream(file).getChannel();
                try {
                    FileChannel channel = new FileOutputStream(file2).getChannel();
                    try {
                        long size = fileChannel2.size();
                        if (channel.transferFrom(fileChannel2, 0, size) != size) {
                            b(file2);
                            if (fileChannel2 != null) {
                                fileChannel2.close();
                            }
                            if (channel != null) {
                                channel.close();
                            }
                            return false;
                        }
                        if (fileChannel2 != null) {
                            fileChannel2.close();
                        }
                        if (channel != null) {
                            channel.close();
                        }
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                        fileChannel = channel;
                        if (fileChannel2 != null) {
                            fileChannel2.close();
                        }
                        if (fileChannel != null) {
                            fileChannel.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileChannel = fileChannel3;
                }
            } else {
                if (fileChannel3 != null) {
                    fileChannel3.close();
                }
                if (fileChannel3 != null) {
                    fileChannel3.close();
                }
                return false;
            }
        } catch (Throwable th4) {
            th = th4;
            fileChannel = fileChannel3;
            fileChannel2 = fileChannel3;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005b  */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(java.io.InputStream r9, java.util.zip.ZipEntry r10, java.lang.String r11, java.lang.String r12) {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File
            r1.<init>(r11)
            a((java.io.File) r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r2 = java.io.File.separator
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            java.io.File r8 = new java.io.File
            r8.<init>(r1)
            r3 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00b9, all -> 0x00b3 }
            r2.<init>(r8)     // Catch:{ IOException -> 0x00b9, all -> 0x00b3 }
            r3 = 8192(0x2000, float:1.14794E-41)
            byte[] r3 = new byte[r3]     // Catch:{ IOException -> 0x003a, all -> 0x00b6 }
        L_0x002f:
            int r4 = r9.read(r3)     // Catch:{ IOException -> 0x003a, all -> 0x00b6 }
            if (r4 <= 0) goto L_0x005f
            r5 = 0
            r2.write(r3, r5, r4)     // Catch:{ IOException -> 0x003a, all -> 0x00b6 }
            goto L_0x002f
        L_0x003a:
            r0 = move-exception
            r1 = r2
        L_0x003c:
            b((java.io.File) r8)     // Catch:{ all -> 0x0058 }
            java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0058 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            r3.<init>()     // Catch:{ all -> 0x0058 }
            java.lang.String r4 = "Couldn't write dst file "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0058 }
            java.lang.StringBuilder r3 = r3.append(r8)     // Catch:{ all -> 0x0058 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0058 }
            r2.<init>(r3, r0)     // Catch:{ all -> 0x0058 }
            throw r2     // Catch:{ all -> 0x0058 }
        L_0x0058:
            r0 = move-exception
        L_0x0059:
            if (r1 == 0) goto L_0x005e
            r1.close()
        L_0x005e:
            throw r0
        L_0x005f:
            if (r2 == 0) goto L_0x0064
            r2.close()
        L_0x0064:
            long r2 = r10.getSize()
            long r4 = r10.getTime()
            long r6 = r10.getCrc()
            boolean r2 = a((java.lang.String) r1, (long) r2, (long) r4, (long) r6)
            if (r2 == 0) goto L_0x008f
            java.lang.String r2 = "FileHelper"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "file is different: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.e(r2, r1)
        L_0x008e:
            return r0
        L_0x008f:
            long r0 = r10.getTime()
            boolean r0 = r8.setLastModified(r0)
            if (r0 != 0) goto L_0x00b1
            java.lang.String r0 = "FileHelper"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Couldn't set time for dst file "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r8)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.e(r0, r1)
        L_0x00b1:
            r0 = 1
            goto L_0x008e
        L_0x00b3:
            r0 = move-exception
            r1 = r3
            goto L_0x0059
        L_0x00b6:
            r0 = move-exception
            r1 = r2
            goto L_0x0059
        L_0x00b9:
            r0 = move-exception
            r1 = r3
            goto L_0x003c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.k.b(java.io.InputStream, java.util.zip.ZipEntry, java.lang.String, java.lang.String):boolean");
    }

    public static byte[] b(InputStream inputStream) {
        return a(inputStream).toByteArray();
    }

    public static File c() {
        File file = new File(a().getAbsolutePath() + Constants.URL_PATH_DELIMITER + ".tbs");
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static String c(Context context) {
        return Environment.getExternalStorageDirectory() + File.separator + "tbs" + File.separator + "file_locks";
    }

    public static boolean c(File file) {
        return file != null && file.exists() && file.isFile() && file.length() > 0;
    }

    public static FileOutputStream d(File file) {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                throw new IOException("File '" + file + "' could not be created");
            }
        } else if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        } else if (!file.canWrite()) {
            throw new IOException("File '" + file + "' cannot be written to");
        }
        return new FileOutputStream(file);
    }

    static String d(Context context) {
        File file = new File(context.getDir("tbs", 0), "core_private");
        if (file == null) {
            return null;
        }
        if (file.isDirectory() || file.mkdir()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static FileLock e(Context context) {
        boolean z;
        FileLock fileLock = null;
        try {
            z = TbsDownloadConfig.getInstance().getTbsCoreLoadRenameFileLockEnable();
        } catch (Throwable th) {
            z = true;
        }
        if (!z) {
            FileOutputStream b2 = b(context, true, "tbs_rename_lock");
            if (b2 != null && (fileLock = a(context, b2)) == null) {
            }
        } else {
            try {
                d = new RandomAccessFile(a(context, true, "tbs_rename_lock").getAbsolutePath(), "r");
                fileLock = d.getChannel().tryLock(0, FileTracerConfig.FOREVER, true);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
            if (fileLock == null) {
            }
        }
        return fileLock;
    }

    public static FileLock f(Context context) {
        FileLock fileLock = null;
        try {
            d = new RandomAccessFile(a(context, true, "tbs_rename_lock").getAbsolutePath(), "rw");
            fileLock = d.getChannel().tryLock(0, FileTracerConfig.FOREVER, false);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (fileLock == null) {
        }
        return fileLock;
    }
}
