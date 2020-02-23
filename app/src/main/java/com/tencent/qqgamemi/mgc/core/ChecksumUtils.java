package com.tencent.qqgamemi.mgc.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumUtils {
    public static byte[] calculateFileMd5(String filePath) {
        return calculateFileHash(filePath, "MD5");
    }

    public static byte[] calculateFileSha1(String filePath) {
        return calculateFileHash(filePath, "SHA1");
    }

    public static byte[] md5(byte[] stream) {
        return calculateStreamHash(stream, "MD5");
    }

    public static byte[] calculateStreamSha1(byte[] steam) {
        return calculateStreamHash(steam, "SHA1");
    }

    public static byte[] calculateStreamHash(byte[] steam, String algorithm) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(steam);
            return m.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String MD5Str32(byte[] stream) {
        byte[] byteArray = md5(stream);
        if (byteArray == null) {
            return null;
        }
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(byteArray[i] & 255).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(byteArray[i] & 255));
            } else {
                md5StrBuff.append(Integer.toHexString(byteArray[i] & 255));
            }
        }
        return md5StrBuff.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0040 A[SYNTHETIC, Splitter:B:30:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x004c A[SYNTHETIC, Splitter:B:36:0x004c] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:10:0x001b=Splitter:B:10:0x001b, B:27:0x003b=Splitter:B:27:0x003b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] calculateFileHash(java.lang.String r8, java.lang.String r9) {
        /*
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x005b, NoSuchAlgorithmException -> 0x003a }
            r3.<init>(r8)     // Catch:{ IOException -> 0x005b, NoSuchAlgorithmException -> 0x003a }
            java.security.MessageDigest r5 = java.security.MessageDigest.getInstance(r9)     // Catch:{ IOException -> 0x0019, NoSuchAlgorithmException -> 0x0058, all -> 0x0055 }
            r7 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r7]     // Catch:{ IOException -> 0x0019, NoSuchAlgorithmException -> 0x0058, all -> 0x0055 }
        L_0x000e:
            int r6 = r3.read(r0)     // Catch:{ IOException -> 0x0019, NoSuchAlgorithmException -> 0x0058, all -> 0x0055 }
            if (r6 <= 0) goto L_0x0025
            r7 = 0
            r5.update(r0, r7, r6)     // Catch:{ IOException -> 0x0019, NoSuchAlgorithmException -> 0x0058, all -> 0x0055 }
            goto L_0x000e
        L_0x0019:
            r1 = move-exception
            r2 = r3
        L_0x001b:
            r1.printStackTrace()     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x0023
            r2.close()     // Catch:{ IOException -> 0x0035 }
        L_0x0023:
            r4 = 0
        L_0x0024:
            return r4
        L_0x0025:
            byte[] r4 = r5.digest()     // Catch:{ IOException -> 0x0019, NoSuchAlgorithmException -> 0x0058, all -> 0x0055 }
            if (r3 == 0) goto L_0x002e
            r3.close()     // Catch:{ IOException -> 0x0030 }
        L_0x002e:
            r2 = r3
            goto L_0x0024
        L_0x0030:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x002e
        L_0x0035:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0023
        L_0x003a:
            r1 = move-exception
        L_0x003b:
            r1.printStackTrace()     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x0023
            r2.close()     // Catch:{ IOException -> 0x0044 }
            goto L_0x0023
        L_0x0044:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0023
        L_0x0049:
            r7 = move-exception
        L_0x004a:
            if (r2 == 0) goto L_0x004f
            r2.close()     // Catch:{ IOException -> 0x0050 }
        L_0x004f:
            throw r7
        L_0x0050:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x004f
        L_0x0055:
            r7 = move-exception
            r2 = r3
            goto L_0x004a
        L_0x0058:
            r1 = move-exception
            r2 = r3
            goto L_0x003b
        L_0x005b:
            r1 = move-exception
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qqgamemi.mgc.core.ChecksumUtils.calculateFileHash(java.lang.String, java.lang.String):byte[]");
    }
}
