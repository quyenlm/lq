package com.tencent.imsdk.tool.etc;

import android.content.Context;
import android.support.annotation.NonNull;
import java.io.File;

public class InstallationInfoUtil {
    private static final String INSTALLATION = "IMSDK_INSTALLATION";
    private static String sID = null;

    public static synchronized String getInstallationID(@NonNull Context context) {
        String str;
        synchronized (InstallationInfoUtil.class) {
            if (sID == null) {
                File installation = new File(context.getFilesDir(), INSTALLATION);
                try {
                    if (!installation.exists()) {
                        writeInstallationFile(installation);
                    }
                    sID = readInstallationFile(installation);
                } catch (Exception e) {
                    IMLogger.d("catch exception : " + e.getMessage());
                }
            }
            str = sID;
        }
        return str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0060 A[SYNTHETIC, Splitter:B:15:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0085 A[SYNTHETIC, Splitter:B:22:0x0085] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String readInstallationFile(java.io.File r7) throws java.io.IOException {
        /*
            r2 = 0
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0043 }
            java.lang.String r4 = "r"
            r3.<init>(r7, r4)     // Catch:{ Exception -> 0x0043 }
            long r4 = r3.length()     // Catch:{ Exception -> 0x00a8, all -> 0x00a5 }
            int r4 = (int) r4     // Catch:{ Exception -> 0x00a8, all -> 0x00a5 }
            byte[] r0 = new byte[r4]     // Catch:{ Exception -> 0x00a8, all -> 0x00a5 }
            r3.readFully(r0)     // Catch:{ Exception -> 0x00a8, all -> 0x00a5 }
            r3.close()     // Catch:{ Exception -> 0x00a8, all -> 0x00a5 }
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x00a8, all -> 0x00a5 }
            java.lang.String r5 = "UTF-8"
            java.nio.charset.Charset r5 = java.nio.charset.Charset.forName(r5)     // Catch:{ Exception -> 0x00a8, all -> 0x00a5 }
            r4.<init>(r0, r5)     // Catch:{ Exception -> 0x00a8, all -> 0x00a5 }
            if (r3 == 0) goto L_0x0025
            r3.close()     // Catch:{ Exception -> 0x0027 }
        L_0x0025:
            r2 = r3
        L_0x0026:
            return r4
        L_0x0027:
            r1 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "close file failed : "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = r1.getMessage()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r5)
            goto L_0x0025
        L_0x0043:
            r1 = move-exception
        L_0x0044:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0082 }
            r4.<init>()     // Catch:{ all -> 0x0082 }
            java.lang.String r5 = "read install file failed : "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0082 }
            java.lang.String r5 = r1.getMessage()     // Catch:{ all -> 0x0082 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0082 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0082 }
            com.tencent.imsdk.tool.etc.IMLogger.w(r4)     // Catch:{ all -> 0x0082 }
            if (r2 == 0) goto L_0x0063
            r2.close()     // Catch:{ Exception -> 0x0066 }
        L_0x0063:
            java.lang.String r4 = ""
            goto L_0x0026
        L_0x0066:
            r1 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "close file failed : "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r1.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r4)
            goto L_0x0063
        L_0x0082:
            r4 = move-exception
        L_0x0083:
            if (r2 == 0) goto L_0x0088
            r2.close()     // Catch:{ Exception -> 0x0089 }
        L_0x0088:
            throw r4
        L_0x0089:
            r1 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "close file failed : "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = r1.getMessage()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r5)
            goto L_0x0088
        L_0x00a5:
            r4 = move-exception
            r2 = r3
            goto L_0x0083
        L_0x00a8:
            r1 = move-exception
            r2 = r3
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.etc.InstallationInfoUtil.readInstallationFile(java.io.File):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0058 A[SYNTHETIC, Splitter:B:16:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x007b A[SYNTHETIC, Splitter:B:22:0x007b] */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void writeInstallationFile(java.io.File r7) throws java.io.IOException {
        /*
            r2 = 0
            java.util.UUID r4 = java.util.UUID.randomUUID()
            java.lang.String r1 = r4.toString()
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x003b }
            r3.<init>(r7)     // Catch:{ Exception -> 0x003b }
            java.lang.String r4 = "UTF-8"
            byte[] r4 = r1.getBytes(r4)     // Catch:{ Exception -> 0x009e, all -> 0x009b }
            r3.write(r4)     // Catch:{ Exception -> 0x009e, all -> 0x009b }
            if (r3 == 0) goto L_0x001c
            r3.close()     // Catch:{ Exception -> 0x001e }
        L_0x001c:
            r2 = r3
        L_0x001d:
            return
        L_0x001e:
            r0 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "close file failed : "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r0.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r4)
            r2 = r3
            goto L_0x001d
        L_0x003b:
            r0 = move-exception
        L_0x003c:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0078 }
            r4.<init>()     // Catch:{ all -> 0x0078 }
            java.lang.String r5 = "write install id failed : "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0078 }
            java.lang.String r5 = r0.getMessage()     // Catch:{ all -> 0x0078 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0078 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0078 }
            com.tencent.imsdk.tool.etc.IMLogger.w(r4)     // Catch:{ all -> 0x0078 }
            if (r2 == 0) goto L_0x001d
            r2.close()     // Catch:{ Exception -> 0x005c }
            goto L_0x001d
        L_0x005c:
            r0 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "close file failed : "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r0.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r4)
            goto L_0x001d
        L_0x0078:
            r4 = move-exception
        L_0x0079:
            if (r2 == 0) goto L_0x007e
            r2.close()     // Catch:{ Exception -> 0x007f }
        L_0x007e:
            throw r4
        L_0x007f:
            r0 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "close file failed : "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = r0.getMessage()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r5)
            goto L_0x007e
        L_0x009b:
            r4 = move-exception
            r2 = r3
            goto L_0x0079
        L_0x009e:
            r0 = move-exception
            r2 = r3
            goto L_0x003c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.etc.InstallationInfoUtil.writeInstallationFile(java.io.File):void");
    }
}
