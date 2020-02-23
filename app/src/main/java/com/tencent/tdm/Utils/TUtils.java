package com.tencent.tdm.Utils;

import android.content.Context;
import android.text.TextUtils;

public class TUtils {
    private static String TAG = "TUtils";

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0071 A[SYNTHETIC, Splitter:B:21:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0076 A[Catch:{ IOException -> 0x007a }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00fb A[SYNTHETIC, Splitter:B:41:0x00fb] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0100 A[Catch:{ IOException -> 0x0105 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x012e A[SYNTHETIC, Splitter:B:49:0x012e] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0133 A[Catch:{ IOException -> 0x0137 }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:66:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:38:0x00d6=Splitter:B:38:0x00d6, B:18:0x0068=Splitter:B:18:0x0068} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyAssertsToDest(android.content.Context r13, java.lang.String r14, java.lang.String r15) {
        /*
            java.io.File r8 = new java.io.File
            r8.<init>(r15)
            java.lang.String r9 = TAG
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "copy dstPath:"
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r8.getAbsolutePath()
            java.lang.String r11 = r11.toString()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.tdm.system.TXLog.d(r9, r10)
            java.lang.String r4 = calculateFileMD5(r15)
            java.lang.String r0 = calculateAssetsFileMD5(r13, r14)
            boolean r9 = android.text.TextUtils.isEmpty(r4)
            if (r9 != 0) goto L_0x0047
            boolean r9 = android.text.TextUtils.isEmpty(r0)
            if (r9 != 0) goto L_0x0047
            boolean r9 = r4.equals(r0)
            if (r9 == 0) goto L_0x0047
            java.lang.String r9 = TAG
            java.lang.String r10 = "json file already exits and md5 is right"
            com.tencent.tdm.system.TXLog.d(r9, r10)
        L_0x0046:
            return
        L_0x0047:
            r7 = 0
            r5 = 0
            android.content.res.AssetManager r9 = r13.getAssets()     // Catch:{ FileNotFoundException -> 0x0163, IOException -> 0x00d5 }
            java.io.InputStream r7 = r9.open(r14)     // Catch:{ FileNotFoundException -> 0x0163, IOException -> 0x00d5 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0163, IOException -> 0x00d5 }
            r6.<init>(r8)     // Catch:{ FileNotFoundException -> 0x0163, IOException -> 0x00d5 }
            r9 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r9]     // Catch:{ FileNotFoundException -> 0x0066, IOException -> 0x015f, all -> 0x015c }
        L_0x005a:
            int r2 = r7.read(r1)     // Catch:{ FileNotFoundException -> 0x0066, IOException -> 0x015f, all -> 0x015c }
            r9 = -1
            if (r2 == r9) goto L_0x009f
            r9 = 0
            r6.write(r1, r9, r2)     // Catch:{ FileNotFoundException -> 0x0066, IOException -> 0x015f, all -> 0x015c }
            goto L_0x005a
        L_0x0066:
            r3 = move-exception
            r5 = r6
        L_0x0068:
            java.lang.String r9 = TAG     // Catch:{ all -> 0x012b }
            java.lang.String r10 = "FileNotFoundException,may not be noticed if not using the encrypt function"
            com.tencent.tdm.system.TXLog.i(r9, r10)     // Catch:{ all -> 0x012b }
            if (r7 == 0) goto L_0x0074
            r7.close()     // Catch:{ IOException -> 0x007a }
        L_0x0074:
            if (r5 == 0) goto L_0x0046
            r5.close()     // Catch:{ IOException -> 0x007a }
            goto L_0x0046
        L_0x007a:
            r3 = move-exception
            java.lang.String r9 = TAG
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "IOEXCeption: "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r3.getMessage()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.tdm.system.TXLog.i(r9, r10)
            java.lang.String r9 = TAG
            java.lang.String r10 = "IOEXCeption"
            com.tencent.tdm.system.TXLog.e(r9, r10)
            goto L_0x0046
        L_0x009f:
            r6.flush()     // Catch:{ FileNotFoundException -> 0x0066, IOException -> 0x015f, all -> 0x015c }
            if (r7 == 0) goto L_0x00a7
            r7.close()     // Catch:{ IOException -> 0x00ae }
        L_0x00a7:
            if (r6 == 0) goto L_0x00ac
            r6.close()     // Catch:{ IOException -> 0x00ae }
        L_0x00ac:
            r5 = r6
            goto L_0x0046
        L_0x00ae:
            r3 = move-exception
            java.lang.String r9 = TAG
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "IOEXCeption: "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r3.getMessage()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.tdm.system.TXLog.i(r9, r10)
            java.lang.String r9 = TAG
            java.lang.String r10 = "IOEXCeption"
            com.tencent.tdm.system.TXLog.e(r9, r10)
            r5 = r6
            goto L_0x0046
        L_0x00d5:
            r3 = move-exception
        L_0x00d6:
            java.lang.String r9 = TAG     // Catch:{ all -> 0x012b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x012b }
            r10.<init>()     // Catch:{ all -> 0x012b }
            java.lang.String r11 = "IOEXCeption: "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x012b }
            java.lang.String r11 = r3.getMessage()     // Catch:{ all -> 0x012b }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x012b }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x012b }
            com.tencent.tdm.system.TXLog.i(r9, r10)     // Catch:{ all -> 0x012b }
            java.lang.String r9 = TAG     // Catch:{ all -> 0x012b }
            java.lang.String r10 = "IOEXCeption"
            com.tencent.tdm.system.TXLog.e(r9, r10)     // Catch:{ all -> 0x012b }
            if (r7 == 0) goto L_0x00fe
            r7.close()     // Catch:{ IOException -> 0x0105 }
        L_0x00fe:
            if (r5 == 0) goto L_0x0046
            r5.close()     // Catch:{ IOException -> 0x0105 }
            goto L_0x0046
        L_0x0105:
            r3 = move-exception
            java.lang.String r9 = TAG
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "IOEXCeption: "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r3.getMessage()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.tdm.system.TXLog.i(r9, r10)
            java.lang.String r9 = TAG
            java.lang.String r10 = "IOEXCeption"
            com.tencent.tdm.system.TXLog.e(r9, r10)
            goto L_0x0046
        L_0x012b:
            r9 = move-exception
        L_0x012c:
            if (r7 == 0) goto L_0x0131
            r7.close()     // Catch:{ IOException -> 0x0137 }
        L_0x0131:
            if (r5 == 0) goto L_0x0136
            r5.close()     // Catch:{ IOException -> 0x0137 }
        L_0x0136:
            throw r9
        L_0x0137:
            r3 = move-exception
            java.lang.String r10 = TAG
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "IOEXCeption: "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = r3.getMessage()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.tencent.tdm.system.TXLog.i(r10, r11)
            java.lang.String r10 = TAG
            java.lang.String r11 = "IOEXCeption"
            com.tencent.tdm.system.TXLog.e(r10, r11)
            goto L_0x0136
        L_0x015c:
            r9 = move-exception
            r5 = r6
            goto L_0x012c
        L_0x015f:
            r3 = move-exception
            r5 = r6
            goto L_0x00d6
        L_0x0163:
            r3 = move-exception
            goto L_0x0068
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tdm.Utils.TUtils.copyAssertsToDest(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static String calculateFileMD5(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        String tmpMd5 = MD5Util.encodeFile2HexStr(filePath);
        String result = TextUtils.isEmpty(tmpMd5) ? tmpMd5 : tmpMd5.toUpperCase();
        String str = result;
        return result;
    }

    public static String calculateAssetsFileMD5(Context context, String assetsFilePath) {
        if (TextUtils.isEmpty(assetsFilePath)) {
            return null;
        }
        String tmpMd5 = MD5Util.encodeAssets2HexStr(context, assetsFilePath);
        String result = TextUtils.isEmpty(tmpMd5) ? tmpMd5 : tmpMd5.toUpperCase();
        String str = result;
        return result;
    }
}
