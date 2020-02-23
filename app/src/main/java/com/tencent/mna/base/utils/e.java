package com.tencent.mna.base.utils;

import com.appsflyer.share.Constants;
import com.tencent.mna.a.a;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.zip.GZIPOutputStream;

/* compiled from: FileWriter */
public final class e {
    static String a = "";

    public static void a(String str, String str2, long j, String str3, int i, String str4) {
        if (i != 0) {
            final String str5 = str3;
            final String str6 = str;
            final String str7 = str2;
            final long j2 = j;
            final int i2 = i;
            final String str8 = str4;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        int unused = e.c(str6, str7, j2, e.b(str5), i2, str8);
                    } catch (Throwable th) {
                    }
                }
            }).start();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0139, code lost:
        r4 = -6;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized int c(java.lang.String r18, java.lang.String r19, long r20, java.lang.String r22, int r23, java.lang.String r24) {
        /*
            java.lang.Class<com.tencent.mna.base.utils.e> r5 = com.tencent.mna.base.utils.e.class
            monitor-enter(r5)
            if (r23 != 0) goto L_0x0008
            r4 = 0
        L_0x0006:
            monitor-exit(r5)
            return r4
        L_0x0008:
            if (r22 == 0) goto L_0x0010
            int r4 = r22.length()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r4 > 0) goto L_0x0012
        L_0x0010:
            r4 = -1
            goto L_0x0006
        L_0x0012:
            java.lang.String r4 = android.os.Environment.getExternalStorageState()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r6 = "mounted"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r4 != 0) goto L_0x0025
            java.lang.String r4 = "[writeSync] sdcard不存在"
            com.tencent.mna.base.utils.h.b(r4)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r4 = -2
            goto L_0x0006
        L_0x0025:
            java.lang.String r4 = a     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r4 == 0) goto L_0x0031
            java.lang.String r4 = a     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            int r4 = r4.length()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r4 > 0) goto L_0x004e
        L_0x0031:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r4.<init>()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.io.File r6 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r6 = r6.getAbsolutePath()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r6 = "/"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            a = r4     // Catch:{ Exception -> 0x0138, all -> 0x013c }
        L_0x004e:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r4.<init>()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r6 = a     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r0 = r18
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r6 = a((long) r20)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r6 = r4.toString()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r7.<init>(r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            boolean r4 = r7.exists()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r4 != 0) goto L_0x00cc
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r8.<init>()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r9 = a     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r0 = r18
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r4.<init>(r8)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.io.File[] r8 = r4.listFiles()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r8 == 0) goto L_0x00be
            int r4 = r8.length     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r4 <= 0) goto L_0x00be
            int r9 = r8.length     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r4 = 0
        L_0x009b:
            if (r4 >= r9) goto L_0x00be
            r10 = r8[r4]     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            long r12 = r10.lastModified()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            long r12 = r20 - r12
            r0 = r23
            long r14 = (long) r0     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r16 = 24
            long r14 = r14 * r16
            r16 = 3600(0xe10, double:1.7786E-320)
            long r14 = r14 * r16
            r16 = 1000(0x3e8, double:4.94E-321)
            long r14 = r14 * r16
            int r11 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r11 <= 0) goto L_0x00bb
            a((java.io.File) r10)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
        L_0x00bb:
            int r4 = r4 + 1
            goto L_0x009b
        L_0x00be:
            boolean r4 = r7.mkdirs()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r4 != 0) goto L_0x00cc
            java.lang.String r4 = "[writeSync] 文件夹不存在且创建失败"
            com.tencent.mna.base.utils.h.b(r4)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r4 = -3
            goto L_0x0006
        L_0x00cc:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r4.<init>()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r0 = r19
            r1 = r20
            r3 = r24
            java.lang.String r6 = a((java.lang.String) r0, (long) r1, (java.lang.String) r3)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r6.<init>()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r7 = "[writeSync] filePath "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.StringBuilder r6 = r6.append(r4)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            com.tencent.mna.base.utils.h.b(r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r6.<init>(r4)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            boolean r4 = r6.exists()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r4 != 0) goto L_0x011f
            r6.createNewFile()     // Catch:{ IOException -> 0x011b }
        L_0x010b:
            java.lang.String r4 = r6.getAbsolutePath()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r6 = 1
            r0 = r22
            boolean r4 = a((java.lang.String) r4, (java.lang.String) r0, (boolean) r6)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            if (r4 == 0) goto L_0x0135
            r4 = 0
            goto L_0x0006
        L_0x011b:
            r4 = move-exception
            r4 = -4
            goto L_0x0006
        L_0x011f:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r4.<init>()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r7 = "#####"
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            r0 = r22
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            java.lang.String r22 = r4.toString()     // Catch:{ Exception -> 0x0138, all -> 0x013c }
            goto L_0x010b
        L_0x0135:
            r4 = -5
            goto L_0x0006
        L_0x0138:
            r4 = move-exception
            r4 = -6
            goto L_0x0006
        L_0x013c:
            r4 = move-exception
            monitor-exit(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.e.c(java.lang.String, java.lang.String, long, java.lang.String, int, java.lang.String):int");
    }

    public static boolean a(String str, String str2, boolean z) {
        boolean z2 = false;
        FileChannel fileChannel = null;
        try {
            fileChannel = new FileOutputStream(str, z).getChannel();
            byte[] bytes = str2.getBytes("ISO-8859-1");
            ByteBuffer allocate = ByteBuffer.allocate(bytes.length);
            allocate.put(bytes);
            allocate.flip();
            fileChannel.write(allocate);
            fileChannel.close();
            z2 = true;
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                }
            }
        } catch (IOException e2) {
            h.a("[writeFileByChannel] exception:" + e2.getMessage());
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e3) {
                }
            }
        } catch (Throwable th) {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e4) {
                }
            }
            throw th;
        }
        return z2;
    }

    private static String a(long j) {
        Date date = new Date(j);
        return new SimpleDateFormat("yyyyMMdd", a.b).format(date) + Constants.URL_PATH_DELIMITER;
    }

    private static String a(String str, long j, String str2) {
        return str.replace('|', '_') + "_" + j;
    }

    private static void a(File file) {
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                file.delete();
                return;
            }
            for (File a2 : listFiles) {
                a(a2);
            }
            file.delete();
        }
    }

    /* access modifiers changed from: private */
    public static String b(String str) {
        if (str == null || str.length() <= 0) {
            return str;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(str.getBytes("ISO-8859-1"));
        gZIPOutputStream.close();
        return byteArrayOutputStream.toString("ISO-8859-1");
    }
}
