package com.tencent.smtt.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import java.io.File;

public class a {
    public static int a(Context context, File file) {
        if (file == null || !file.exists()) {
            return 0;
        }
        try {
            PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 1);
            if (packageArchiveInfo != null) {
                return packageArchiveInfo.versionCode;
            }
            return 0;
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002e A[SYNTHETIC, Splitter:B:13:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0067 A[SYNTHETIC, Splitter:B:29:0x0067] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r11) {
        /*
            r1 = 0
            r10 = 16
            r3 = 0
            char[] r5 = new char[r10]
            r5 = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102} // fill-array
            r0 = 32
            char[] r6 = new char[r0]
            java.lang.String r0 = "MD5"
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r0)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
            r2.<init>(r11)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
            r4 = 8192(0x2000, float:1.14794E-41)
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x0028 }
        L_0x001c:
            int r7 = r2.read(r4)     // Catch:{ Exception -> 0x0028 }
            r8 = -1
            if (r7 == r8) goto L_0x0033
            r8 = 0
            r0.update(r4, r8, r7)     // Catch:{ Exception -> 0x0028 }
            goto L_0x001c
        L_0x0028:
            r0 = move-exception
        L_0x0029:
            r0.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r2 == 0) goto L_0x0031
            r2.close()     // Catch:{ IOException -> 0x0070 }
        L_0x0031:
            r0 = r1
        L_0x0032:
            return r0
        L_0x0033:
            byte[] r7 = r0.digest()     // Catch:{ Exception -> 0x0028 }
            r0 = r3
            r4 = r3
        L_0x0039:
            if (r0 >= r10) goto L_0x0053
            byte r8 = r7[r0]     // Catch:{ Exception -> 0x0028 }
            int r9 = r4 + 1
            int r3 = r8 >>> 4
            r3 = r3 & 15
            char r3 = r5[r3]     // Catch:{ Exception -> 0x0028 }
            r6[r4] = r3     // Catch:{ Exception -> 0x0028 }
            int r3 = r9 + 1
            r4 = r8 & 15
            char r4 = r5[r4]     // Catch:{ Exception -> 0x0028 }
            r6[r9] = r4     // Catch:{ Exception -> 0x0028 }
            int r0 = r0 + 1
            r4 = r3
            goto L_0x0039
        L_0x0053:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0028 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x0028 }
            if (r2 == 0) goto L_0x0032
            r2.close()     // Catch:{ IOException -> 0x005e }
            goto L_0x0032
        L_0x005e:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0032
        L_0x0063:
            r0 = move-exception
            r2 = r1
        L_0x0065:
            if (r2 == 0) goto L_0x006a
            r2.close()     // Catch:{ IOException -> 0x006b }
        L_0x006a:
            throw r0
        L_0x006b:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x006a
        L_0x0070:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0031
        L_0x0075:
            r0 = move-exception
            goto L_0x0065
        L_0x0077:
            r0 = move-exception
            r2 = r1
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.a.a(java.io.File):java.lang.String");
    }

    public static boolean a(Context context, File file, long j, int i) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (j > 0 && j != file.length()) {
            return false;
        }
        try {
            return i == a(context, file) && "3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a".equals(b.a(context, file));
        } catch (Exception e) {
            return false;
        }
    }
}
