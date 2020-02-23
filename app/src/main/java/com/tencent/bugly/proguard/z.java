package com.tencent.bugly.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.qqgamemi.util.TimeUtils;
import com.tencent.tp.a.h;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import org.apache.http.HttpHost;

/* compiled from: BUGLY */
public class z {
    private static Map<String, String> a = null;
    private static boolean b = false;

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        try {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.getBuffer().toString();
        } catch (Throwable th2) {
            if (!x.a(th2)) {
                th2.printStackTrace();
            }
            return "fail";
        }
    }

    public static String a() {
        return a(System.currentTimeMillis());
    }

    public static String a(long j) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(j));
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    public static String a(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    private static byte[] a(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        x.c("rqdp{  enD:} %d %d", Integer.valueOf(bArr.length), Integer.valueOf(i));
        try {
            aj a2 = a.a(i);
            if (a2 == null) {
                return null;
            }
            a2.a(str);
            return a2.b(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static byte[] b(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        try {
            aj a2 = a.a(i);
            if (a2 == null) {
                return null;
            }
            a2.a(str);
            return a2.a(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            x.d("encrytype %d %s", Integer.valueOf(i), str);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0080 A[SYNTHETIC, Splitter:B:35:0x0080] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.io.File r8, java.lang.String r9, java.lang.String r10) {
        /*
            r0 = 0
            r7 = 0
            if (r9 == 0) goto L_0x000a
            int r1 = r9.length()
            if (r1 != 0) goto L_0x000b
        L_0x000a:
            return r0
        L_0x000b:
            java.lang.String r1 = "rqdp{  ZF start}"
            java.lang.Object[] r2 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.c(r1, r2)
            java.lang.String r1 = "UTF-8"
            byte[] r1 = r9.getBytes(r1)     // Catch:{ Throwable -> 0x0093, all -> 0x007c }
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ Throwable -> 0x0093, all -> 0x007c }
            r3.<init>(r1)     // Catch:{ Throwable -> 0x0093, all -> 0x007c }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0093, all -> 0x007c }
            r1.<init>()     // Catch:{ Throwable -> 0x0093, all -> 0x007c }
            java.util.zip.ZipOutputStream r2 = new java.util.zip.ZipOutputStream     // Catch:{ Throwable -> 0x0093, all -> 0x007c }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0093, all -> 0x007c }
            r4 = 8
            r2.setMethod(r4)     // Catch:{ Throwable -> 0x0043 }
            java.util.zip.ZipEntry r4 = new java.util.zip.ZipEntry     // Catch:{ Throwable -> 0x0043 }
            r4.<init>(r10)     // Catch:{ Throwable -> 0x0043 }
            r2.putNextEntry(r4)     // Catch:{ Throwable -> 0x0043 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ Throwable -> 0x0043 }
        L_0x0038:
            int r5 = r3.read(r4)     // Catch:{ Throwable -> 0x0043 }
            if (r5 <= 0) goto L_0x005a
            r6 = 0
            r2.write(r4, r6, r5)     // Catch:{ Throwable -> 0x0043 }
            goto L_0x0038
        L_0x0043:
            r1 = move-exception
        L_0x0044:
            boolean r3 = com.tencent.bugly.proguard.x.a(r1)     // Catch:{ all -> 0x0090 }
            if (r3 != 0) goto L_0x004d
            r1.printStackTrace()     // Catch:{ all -> 0x0090 }
        L_0x004d:
            if (r2 == 0) goto L_0x0052
            r2.close()     // Catch:{ IOException -> 0x0077 }
        L_0x0052:
            java.lang.String r1 = "rqdp{  ZF end}"
            java.lang.Object[] r2 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.c(r1, r2)
            goto L_0x000a
        L_0x005a:
            r2.closeEntry()     // Catch:{ Throwable -> 0x0043 }
            r2.flush()     // Catch:{ Throwable -> 0x0043 }
            r2.finish()     // Catch:{ Throwable -> 0x0043 }
            byte[] r0 = r1.toByteArray()     // Catch:{ Throwable -> 0x0043 }
            r2.close()     // Catch:{ IOException -> 0x0072 }
        L_0x006a:
            java.lang.String r1 = "rqdp{  ZF end}"
            java.lang.Object[] r2 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.c(r1, r2)
            goto L_0x000a
        L_0x0072:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x006a
        L_0x0077:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0052
        L_0x007c:
            r1 = move-exception
            r2 = r0
        L_0x007e:
            if (r2 == 0) goto L_0x0083
            r2.close()     // Catch:{ IOException -> 0x008b }
        L_0x0083:
            java.lang.String r0 = "rqdp{  ZF end}"
            java.lang.Object[] r2 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.c(r0, r2)
            throw r1
        L_0x008b:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0083
        L_0x0090:
            r0 = move-exception
            r1 = r0
            goto L_0x007e
        L_0x0093:
            r1 = move-exception
            r2 = r0
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(java.io.File, java.lang.String, java.lang.String):byte[]");
    }

    public static byte[] a(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        x.c("[Util] Zip %d bytes data with type %s", objArr);
        try {
            ae a2 = ad.a(i);
            if (a2 == null) {
                return null;
            }
            return a2.a(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] b(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        x.c("[Util] Unzip %d bytes data with type %s", objArr);
        try {
            ae a2 = ad.a(i);
            if (a2 == null) {
                return null;
            }
            return a2.b(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] a(byte[] bArr, int i, int i2, String str) {
        if (bArr == null) {
            return null;
        }
        try {
            return a(a(bArr, 2), 1, str);
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static byte[] b(byte[] bArr, int i, int i2, String str) {
        try {
            return b(b(bArr, 1, str), 2);
        } catch (Exception e) {
            if (!x.a(e)) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static long b() {
        try {
            return (((System.currentTimeMillis() + ((long) TimeZone.getDefault().getRawOffset())) / TimeUtils.MILLIS_IN_DAY) * TimeUtils.MILLIS_IN_DAY) - ((long) TimeZone.getDefault().getRawOffset());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static String b(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "NULL";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(bArr);
            return a(instance.digest());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0096 A[Catch:{ all -> 0x0103 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009b A[SYNTHETIC, Splitter:B:40:0x009b] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a0 A[SYNTHETIC, Splitter:B:43:0x00a0] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00e6 A[SYNTHETIC, Splitter:B:68:0x00e6] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00eb A[SYNTHETIC, Splitter:B:71:0x00eb] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.io.File r6, java.io.File r7, int r8) {
        /*
            r3 = 0
            r0 = 0
            java.lang.String r1 = "rqdp{  ZF start}"
            java.lang.Object[] r2 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.c(r1, r2)
            if (r6 == 0) goto L_0x0013
            if (r7 == 0) goto L_0x0013
            boolean r1 = r6.equals(r7)
            if (r1 == 0) goto L_0x001b
        L_0x0013:
            java.lang.String r1 = "rqdp{  err ZF 1R!}"
            java.lang.Object[] r2 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.d(r1, r2)
        L_0x001a:
            return r0
        L_0x001b:
            boolean r1 = r6.exists()
            if (r1 == 0) goto L_0x0027
            boolean r1 = r6.canRead()
            if (r1 != 0) goto L_0x002f
        L_0x0027:
            java.lang.String r1 = "rqdp{  !sFile.exists() || !sFile.canRead(),pls check ,return!}"
            java.lang.Object[] r2 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.d(r1, r2)
            goto L_0x001a
        L_0x002f:
            java.io.File r1 = r7.getParentFile()     // Catch:{ Throwable -> 0x00ac }
            if (r1 == 0) goto L_0x0046
            java.io.File r1 = r7.getParentFile()     // Catch:{ Throwable -> 0x00ac }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x00ac }
            if (r1 != 0) goto L_0x0046
            java.io.File r1 = r7.getParentFile()     // Catch:{ Throwable -> 0x00ac }
            r1.mkdirs()     // Catch:{ Throwable -> 0x00ac }
        L_0x0046:
            boolean r1 = r7.exists()     // Catch:{ Throwable -> 0x00ac }
            if (r1 != 0) goto L_0x004f
            r7.createNewFile()     // Catch:{ Throwable -> 0x00ac }
        L_0x004f:
            boolean r1 = r7.exists()
            if (r1 == 0) goto L_0x001a
            boolean r1 = r7.canRead()
            if (r1 == 0) goto L_0x001a
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0105, all -> 0x00e1 }
            r4.<init>(r6)     // Catch:{ Throwable -> 0x0105, all -> 0x00e1 }
            java.util.zip.ZipOutputStream r2 = new java.util.zip.ZipOutputStream     // Catch:{ Throwable -> 0x0109, all -> 0x0100 }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x0109, all -> 0x0100 }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0109, all -> 0x0100 }
            r5.<init>(r7)     // Catch:{ Throwable -> 0x0109, all -> 0x0100 }
            r1.<init>(r5)     // Catch:{ Throwable -> 0x0109, all -> 0x0100 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0109, all -> 0x0100 }
            r1 = 8
            r2.setMethod(r1)     // Catch:{ Throwable -> 0x008f }
            java.util.zip.ZipEntry r1 = new java.util.zip.ZipEntry     // Catch:{ Throwable -> 0x008f }
            java.lang.String r3 = r6.getName()     // Catch:{ Throwable -> 0x008f }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x008f }
            r2.putNextEntry(r1)     // Catch:{ Throwable -> 0x008f }
            r1 = 5000(0x1388, float:7.006E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Throwable -> 0x008f }
        L_0x0084:
            int r3 = r4.read(r1)     // Catch:{ Throwable -> 0x008f }
            if (r3 <= 0) goto L_0x00b7
            r5 = 0
            r2.write(r1, r5, r3)     // Catch:{ Throwable -> 0x008f }
            goto L_0x0084
        L_0x008f:
            r1 = move-exception
        L_0x0090:
            boolean r3 = com.tencent.bugly.proguard.x.a(r1)     // Catch:{ all -> 0x0103 }
            if (r3 != 0) goto L_0x0099
            r1.printStackTrace()     // Catch:{ all -> 0x0103 }
        L_0x0099:
            if (r4 == 0) goto L_0x009e
            r4.close()     // Catch:{ IOException -> 0x00d7 }
        L_0x009e:
            if (r2 == 0) goto L_0x00a3
            r2.close()     // Catch:{ IOException -> 0x00dc }
        L_0x00a3:
            java.lang.String r1 = "rqdp{  ZF end}"
            java.lang.Object[] r2 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.c(r1, r2)
            goto L_0x001a
        L_0x00ac:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)
            if (r2 != 0) goto L_0x004f
            r1.printStackTrace()
            goto L_0x004f
        L_0x00b7:
            r2.flush()     // Catch:{ Throwable -> 0x008f }
            r2.closeEntry()     // Catch:{ Throwable -> 0x008f }
            r4.close()     // Catch:{ IOException -> 0x00cd }
        L_0x00c0:
            r2.close()     // Catch:{ IOException -> 0x00d2 }
        L_0x00c3:
            java.lang.String r1 = "rqdp{  ZF end}"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.c(r1, r0)
            r0 = 1
            goto L_0x001a
        L_0x00cd:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00c0
        L_0x00d2:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00c3
        L_0x00d7:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x009e
        L_0x00dc:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00a3
        L_0x00e1:
            r1 = move-exception
            r2 = r3
            r4 = r3
        L_0x00e4:
            if (r4 == 0) goto L_0x00e9
            r4.close()     // Catch:{ IOException -> 0x00f6 }
        L_0x00e9:
            if (r2 == 0) goto L_0x00ee
            r2.close()     // Catch:{ IOException -> 0x00fb }
        L_0x00ee:
            java.lang.String r2 = "rqdp{  ZF end}"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.c(r2, r0)
            throw r1
        L_0x00f6:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x00e9
        L_0x00fb:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00ee
        L_0x0100:
            r1 = move-exception
            r2 = r3
            goto L_0x00e4
        L_0x0103:
            r1 = move-exception
            goto L_0x00e4
        L_0x0105:
            r1 = move-exception
            r2 = r3
            r4 = r3
            goto L_0x0090
        L_0x0109:
            r1 = move-exception
            r2 = r3
            goto L_0x0090
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(java.io.File, java.io.File, int):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0080 A[Catch:{ all -> 0x00e4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0085 A[SYNTHETIC, Splitter:B:22:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x008a A[SYNTHETIC, Splitter:B:25:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00cc A[SYNTHETIC, Splitter:B:52:0x00cc] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00d1 A[SYNTHETIC, Splitter:B:55:0x00d1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList<java.lang.String> c(android.content.Context r6, java.lang.String r7) {
        /*
            r1 = 1
            r3 = 0
            r2 = 0
            boolean r0 = com.tencent.bugly.crashreport.common.info.AppInfo.f(r6)
            if (r0 == 0) goto L_0x0019
            java.util.ArrayList r0 = new java.util.ArrayList
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.String r2 = "unknown(low memory)"
            r1[r3] = r2
            java.util.List r1 = java.util.Arrays.asList(r1)
            r0.<init>(r1)
        L_0x0018:
            return r0
        L_0x0019:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.lang.String r0 = "/system/bin/sh"
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            boolean r3 = r3.exists()     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            if (r3 == 0) goto L_0x0036
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            boolean r3 = r3.canExecute()     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            if (r3 != 0) goto L_0x0038
        L_0x0036:
            java.lang.String r0 = "sh"
        L_0x0038:
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r4 = 2
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r5 = 0
            r4[r5] = r0     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r0 = 1
            java.lang.String r5 = "-c"
            r4[r0] = r5     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            java.util.List r0 = java.util.Arrays.asList(r4)     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r3.add(r7)     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            java.lang.Runtime r4 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r0 = 3
            java.lang.String[] r0 = new java.lang.String[r0]     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            java.lang.Object[] r0 = r3.toArray(r0)     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            java.lang.Process r0 = r4.exec(r0)     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            java.io.InputStream r5 = r0.getInputStream()     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r3.<init>(r5)     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x00e7, all -> 0x00c7 }
        L_0x006e:
            java.lang.String r3 = r4.readLine()     // Catch:{ Throwable -> 0x0078, all -> 0x00df }
            if (r3 == 0) goto L_0x008f
            r1.add(r3)     // Catch:{ Throwable -> 0x0078, all -> 0x00df }
            goto L_0x006e
        L_0x0078:
            r0 = move-exception
            r1 = r2
        L_0x007a:
            boolean r3 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x00e4 }
            if (r3 != 0) goto L_0x0083
            r0.printStackTrace()     // Catch:{ all -> 0x00e4 }
        L_0x0083:
            if (r4 == 0) goto L_0x0088
            r4.close()     // Catch:{ IOException -> 0x00bd }
        L_0x0088:
            if (r1 == 0) goto L_0x008d
            r1.close()     // Catch:{ IOException -> 0x00c2 }
        L_0x008d:
            r0 = r2
            goto L_0x0018
        L_0x008f:
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0078, all -> 0x00df }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0078, all -> 0x00df }
            java.io.InputStream r0 = r0.getErrorStream()     // Catch:{ Throwable -> 0x0078, all -> 0x00df }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x0078, all -> 0x00df }
            r3.<init>(r5)     // Catch:{ Throwable -> 0x0078, all -> 0x00df }
        L_0x009d:
            java.lang.String r0 = r3.readLine()     // Catch:{ Throwable -> 0x00a7, all -> 0x00e2 }
            if (r0 == 0) goto L_0x00aa
            r1.add(r0)     // Catch:{ Throwable -> 0x00a7, all -> 0x00e2 }
            goto L_0x009d
        L_0x00a7:
            r0 = move-exception
            r1 = r3
            goto L_0x007a
        L_0x00aa:
            r4.close()     // Catch:{ IOException -> 0x00b3 }
        L_0x00ad:
            r3.close()     // Catch:{ IOException -> 0x00b8 }
        L_0x00b0:
            r0 = r1
            goto L_0x0018
        L_0x00b3:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00ad
        L_0x00b8:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00b0
        L_0x00bd:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0088
        L_0x00c2:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x008d
        L_0x00c7:
            r0 = move-exception
            r3 = r2
            r4 = r2
        L_0x00ca:
            if (r4 == 0) goto L_0x00cf
            r4.close()     // Catch:{ IOException -> 0x00d5 }
        L_0x00cf:
            if (r3 == 0) goto L_0x00d4
            r3.close()     // Catch:{ IOException -> 0x00da }
        L_0x00d4:
            throw r0
        L_0x00d5:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00cf
        L_0x00da:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00d4
        L_0x00df:
            r0 = move-exception
            r3 = r2
            goto L_0x00ca
        L_0x00e2:
            r0 = move-exception
            goto L_0x00ca
        L_0x00e4:
            r0 = move-exception
            r3 = r1
            goto L_0x00ca
        L_0x00e7:
            r0 = move-exception
            r1 = r2
            r4 = r2
            goto L_0x007a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.c(android.content.Context, java.lang.String):java.util.ArrayList");
    }

    public static String a(Context context, String str) {
        if (str == null || str.trim().equals("")) {
            return "";
        }
        if (a == null) {
            a = new HashMap();
            ArrayList<String> c = c(context, "getprop");
            if (c != null && c.size() > 0) {
                x.b(z.class, "Successfully get 'getprop' list.", new Object[0]);
                Pattern compile = Pattern.compile("\\[(.+)\\]: \\[(.*)\\]");
                for (String matcher : c) {
                    Matcher matcher2 = compile.matcher(matcher);
                    if (matcher2.find()) {
                        a.put(matcher2.group(1), matcher2.group(2));
                    }
                }
                x.b(z.class, "System properties number: %d.", Integer.valueOf(a.size()));
            }
        }
        if (a.containsKey(str)) {
            return a.get(str);
        }
        return "fail";
    }

    public static void b(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean a(String str) {
        if (str == null || str.trim().length() <= 0) {
            return true;
        }
        return false;
    }

    public static void b(String str) {
        if (str != null) {
            File file = new File(str);
            if (file.isFile() && file.exists() && file.canWrite()) {
                file.delete();
            }
        }
    }

    public static byte[] c(long j) {
        try {
            return new StringBuilder().append(j).toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long c(byte[] bArr) {
        if (bArr == null) {
            return -1;
        }
        try {
            return Long.parseLong(new String(bArr, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0003, code lost:
        r0 = r1.getApplicationContext();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.Context a(android.content.Context r1) {
        /*
            if (r1 != 0) goto L_0x0003
        L_0x0002:
            return r1
        L_0x0003:
            android.content.Context r0 = r1.getApplicationContext()
            if (r0 == 0) goto L_0x0002
            r1 = r0
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(android.content.Context):android.content.Context");
    }

    public static String b(Throwable th) {
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static void a(Class<?> cls, String str, Object obj, Object obj2) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set((Object) null, obj);
        } catch (Exception e) {
        }
    }

    public static Object a(String str, String str2, Object obj, Class<?>[] clsArr, Object[] objArr) {
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke((Object) null, objArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static void a(Parcel parcel, Map<String, PlugInBean> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle((Bundle) null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Map.Entry next : map.entrySet()) {
            arrayList.add(next.getKey());
            arrayList2.add(next.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putInt("pluginNum", arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            bundle.putString("pluginKey" + i, (String) arrayList.get(i));
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            bundle.putString("pluginVal" + i2 + "plugInId", ((PlugInBean) arrayList2.get(i2)).a);
            bundle.putString("pluginVal" + i2 + "plugInUUID", ((PlugInBean) arrayList2.get(i2)).c);
            bundle.putString("pluginVal" + i2 + "plugInVersion", ((PlugInBean) arrayList2.get(i2)).b);
        }
        parcel.writeBundle(bundle);
    }

    public static Map<String, PlugInBean> a(Parcel parcel) {
        HashMap hashMap;
        Bundle readBundle = parcel.readBundle();
        if (readBundle == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int intValue = ((Integer) readBundle.get("pluginNum")).intValue();
        for (int i = 0; i < intValue; i++) {
            arrayList.add(readBundle.getString("pluginKey" + i));
        }
        for (int i2 = 0; i2 < intValue; i2++) {
            arrayList2.add(new PlugInBean(readBundle.getString("pluginVal" + i2 + "plugInId"), readBundle.getString("pluginVal" + i2 + "plugInVersion"), readBundle.getString("pluginVal" + i2 + "plugInUUID")));
        }
        if (arrayList.size() == arrayList2.size()) {
            HashMap hashMap2 = new HashMap(arrayList.size());
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                hashMap2.put(arrayList.get(i3), PlugInBean.class.cast(arrayList2.get(i3)));
            }
            hashMap = hashMap2;
        } else {
            x.e("map plugin parcel error!", new Object[0]);
            hashMap = null;
        }
        return hashMap;
    }

    public static void b(Parcel parcel, Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle((Bundle) null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Map.Entry next : map.entrySet()) {
            arrayList.add(next.getKey());
            arrayList2.add(next.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("keys", arrayList);
        bundle.putStringArrayList("values", arrayList2);
        parcel.writeBundle(bundle);
    }

    public static Map<String, String> b(Parcel parcel) {
        HashMap hashMap;
        Bundle readBundle = parcel.readBundle();
        if (readBundle == null) {
            return null;
        }
        ArrayList<String> stringArrayList = readBundle.getStringArrayList("keys");
        ArrayList<String> stringArrayList2 = readBundle.getStringArrayList("values");
        if (stringArrayList == null || stringArrayList2 == null || stringArrayList.size() != stringArrayList2.size()) {
            x.e("map parcel error!", new Object[0]);
            hashMap = null;
        } else {
            HashMap hashMap2 = new HashMap(stringArrayList.size());
            for (int i = 0; i < stringArrayList.size(); i++) {
                hashMap2.put(stringArrayList.get(i), stringArrayList2.get(i));
            }
            hashMap = hashMap2;
        }
        return hashMap;
    }

    public static byte[] a(Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    public static <T> T a(byte[] bArr, Parcelable.Creator<T> creator) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        try {
            T createFromParcel = creator.createFromParcel(obtain);
            if (obtain == null) {
                return createFromParcel;
            }
            obtain.recycle();
            return createFromParcel;
        } catch (Throwable th) {
            if (obtain != null) {
                obtain.recycle();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x0108 A[SYNTHETIC, Splitter:B:55:0x0108] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r7, int r8, java.lang.String r9) {
        /*
            r6 = 4
            r5 = 3
            r4 = 2
            r3 = 1
            r2 = 0
            java.lang.String r0 = "android.permission.READ_LOGS"
            boolean r0 = com.tencent.bugly.crashreport.common.info.AppInfo.a(r7, r0)
            if (r0 != 0) goto L_0x0016
            java.lang.String r0 = "no read_log permission!"
            java.lang.Object[] r1 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.x.d(r0, r1)
            r0 = 0
        L_0x0015:
            return r0
        L_0x0016:
            if (r9 != 0) goto L_0x00b2
            java.lang.String[] r0 = new java.lang.String[r6]
            java.lang.String r1 = "logcat"
            r0[r2] = r1
            java.lang.String r1 = "-d"
            r0[r3] = r1
            java.lang.String r1 = "-v"
            r0[r4] = r1
            java.lang.String r1 = "threadtime"
            r0[r5] = r1
        L_0x002a:
            r1 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0130 }
            java.lang.Process r2 = r2.exec(r0)     // Catch:{ Throwable -> 0x0130 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            java.io.InputStream r4 = r2.getInputStream()     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
        L_0x0046:
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            if (r1 == 0) goto L_0x00ce
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            java.lang.String r4 = "\n"
            r1.append(r4)     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            if (r8 <= 0) goto L_0x0046
            int r1 = r3.length()     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            if (r1 <= r8) goto L_0x0046
            r1 = 0
            int r4 = r3.length()     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            int r4 = r4 - r8
            r3.delete(r1, r4)     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            goto L_0x0046
        L_0x0067:
            r0 = move-exception
            r1 = r2
        L_0x0069:
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x0105 }
            if (r2 != 0) goto L_0x0072
            r0.printStackTrace()     // Catch:{ all -> 0x0105 }
        L_0x0072:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = "\n[error:"
            r2.<init>(r4)     // Catch:{ all -> 0x0105 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0105 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0105 }
            java.lang.String r2 = "]"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0105 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0105 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x0105 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0105 }
            if (r1 == 0) goto L_0x0015
            java.io.OutputStream r2 = r1.getOutputStream()     // Catch:{ IOException -> 0x00fb }
            r2.close()     // Catch:{ IOException -> 0x00fb }
        L_0x009c:
            java.io.InputStream r2 = r1.getInputStream()     // Catch:{ IOException -> 0x0100 }
            r2.close()     // Catch:{ IOException -> 0x0100 }
        L_0x00a3:
            java.io.InputStream r1 = r1.getErrorStream()     // Catch:{ IOException -> 0x00ac }
            r1.close()     // Catch:{ IOException -> 0x00ac }
            goto L_0x0015
        L_0x00ac:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0015
        L_0x00b2:
            r0 = 6
            java.lang.String[] r0 = new java.lang.String[r0]
            java.lang.String r1 = "logcat"
            r0[r2] = r1
            java.lang.String r1 = "-d"
            r0[r3] = r1
            java.lang.String r1 = "-v"
            r0[r4] = r1
            java.lang.String r1 = "threadtime"
            r0[r5] = r1
            java.lang.String r1 = "-s"
            r0[r6] = r1
            r1 = 5
            r0[r1] = r9
            goto L_0x002a
        L_0x00ce:
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x0067, all -> 0x012d }
            if (r2 == 0) goto L_0x0015
            java.io.OutputStream r1 = r2.getOutputStream()     // Catch:{ IOException -> 0x00f1 }
            r1.close()     // Catch:{ IOException -> 0x00f1 }
        L_0x00db:
            java.io.InputStream r1 = r2.getInputStream()     // Catch:{ IOException -> 0x00f6 }
            r1.close()     // Catch:{ IOException -> 0x00f6 }
        L_0x00e2:
            java.io.InputStream r1 = r2.getErrorStream()     // Catch:{ IOException -> 0x00eb }
            r1.close()     // Catch:{ IOException -> 0x00eb }
            goto L_0x0015
        L_0x00eb:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0015
        L_0x00f1:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00db
        L_0x00f6:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00e2
        L_0x00fb:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x009c
        L_0x0100:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00a3
        L_0x0105:
            r0 = move-exception
        L_0x0106:
            if (r1 == 0) goto L_0x011d
            java.io.OutputStream r2 = r1.getOutputStream()     // Catch:{ IOException -> 0x011e }
            r2.close()     // Catch:{ IOException -> 0x011e }
        L_0x010f:
            java.io.InputStream r2 = r1.getInputStream()     // Catch:{ IOException -> 0x0123 }
            r2.close()     // Catch:{ IOException -> 0x0123 }
        L_0x0116:
            java.io.InputStream r1 = r1.getErrorStream()     // Catch:{ IOException -> 0x0128 }
            r1.close()     // Catch:{ IOException -> 0x0128 }
        L_0x011d:
            throw r0
        L_0x011e:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x010f
        L_0x0123:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0116
        L_0x0128:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x011d
        L_0x012d:
            r0 = move-exception
            r1 = r2
            goto L_0x0106
        L_0x0130:
            r0 = move-exception
            goto L_0x0069
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(android.content.Context, int, java.lang.String):java.lang.String");
    }

    public static Map<String, String> a(int i, boolean z) {
        HashMap hashMap = new HashMap(12);
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        if (allStackTraces == null) {
            return null;
        }
        Thread thread = Looper.getMainLooper().getThread();
        if (!allStackTraces.containsKey(thread)) {
            allStackTraces.put(thread, thread.getStackTrace());
        }
        Thread.currentThread().getId();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry next : allStackTraces.entrySet()) {
            sb.setLength(0);
            if (!(next.getValue() == null || ((StackTraceElement[]) next.getValue()).length == 0)) {
                StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) next.getValue();
                int length = stackTraceElementArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    StackTraceElement stackTraceElement = stackTraceElementArr[i2];
                    if (i > 0 && sb.length() >= i) {
                        sb.append("\n[Stack over limit size :" + i + " , has been cut!]");
                        break;
                    }
                    sb.append(stackTraceElement.toString()).append("\n");
                    i2++;
                }
                hashMap.put(((Thread) next.getKey()).getName() + h.a + ((Thread) next.getKey()).getId() + h.b, sb.toString());
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0030 A[SYNTHETIC, Splitter:B:17:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0050 A[Catch:{ Exception -> 0x0054 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized byte[] a(int r7) {
        /*
            r1 = 0
            java.lang.Class<com.tencent.bugly.proguard.z> r3 = com.tencent.bugly.proguard.z.class
            monitor-enter(r3)
            r0 = 16
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0021, all -> 0x004c }
            java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ Exception -> 0x0021, all -> 0x004c }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0021, all -> 0x004c }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0021, all -> 0x004c }
            java.lang.String r6 = "/dev/urandom"
            r5.<init>(r6)     // Catch:{ Exception -> 0x0021, all -> 0x004c }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0021, all -> 0x004c }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0021, all -> 0x004c }
            r2.readFully(r0)     // Catch:{ Exception -> 0x0065 }
            r2.close()     // Catch:{ Exception -> 0x0054 }
        L_0x001f:
            monitor-exit(r3)
            return r0
        L_0x0021:
            r0 = move-exception
            r2 = r1
        L_0x0023:
            java.lang.String r4 = "Failed to read from /dev/urandom : %s"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0063 }
            r6 = 0
            r5[r6] = r0     // Catch:{ all -> 0x0063 }
            com.tencent.bugly.proguard.x.e(r4, r5)     // Catch:{ all -> 0x0063 }
            if (r2 == 0) goto L_0x0033
            r2.close()     // Catch:{ Exception -> 0x0054 }
        L_0x0033:
            java.lang.String r0 = "AES"
            javax.crypto.KeyGenerator r0 = javax.crypto.KeyGenerator.getInstance(r0)     // Catch:{ Exception -> 0x0054 }
            r2 = 128(0x80, float:1.794E-43)
            java.security.SecureRandom r4 = new java.security.SecureRandom     // Catch:{ Exception -> 0x0054 }
            r4.<init>()     // Catch:{ Exception -> 0x0054 }
            r0.init(r2, r4)     // Catch:{ Exception -> 0x0054 }
            javax.crypto.SecretKey r0 = r0.generateKey()     // Catch:{ Exception -> 0x0054 }
            byte[] r0 = r0.getEncoded()     // Catch:{ Exception -> 0x0054 }
            goto L_0x001f
        L_0x004c:
            r0 = move-exception
            r2 = r1
        L_0x004e:
            if (r2 == 0) goto L_0x0053
            r2.close()     // Catch:{ Exception -> 0x0054 }
        L_0x0053:
            throw r0     // Catch:{ Exception -> 0x0054 }
        L_0x0054:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.b(r0)     // Catch:{ all -> 0x0060 }
            if (r2 != 0) goto L_0x005e
            r0.printStackTrace()     // Catch:{ all -> 0x0060 }
        L_0x005e:
            r0 = r1
            goto L_0x001f
        L_0x0060:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x0063:
            r0 = move-exception
            goto L_0x004e
        L_0x0065:
            r0 = move-exception
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(int):byte[]");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    @android.annotation.TargetApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(int r4, byte[] r5, byte[] r6) {
        /*
            javax.crypto.spec.SecretKeySpec r0 = new javax.crypto.spec.SecretKeySpec     // Catch:{ Exception -> 0x0038 }
            java.lang.String r1 = "AES"
            r0.<init>(r6, r1)     // Catch:{ Exception -> 0x0038 }
            java.lang.String r1 = "AES/GCM/NoPadding"
            javax.crypto.Cipher r1 = javax.crypto.Cipher.getInstance(r1)     // Catch:{ Exception -> 0x0038 }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0038 }
            r3 = 21
            if (r2 < r3) goto L_0x0017
            boolean r2 = b     // Catch:{ Exception -> 0x0038 }
            if (r2 == 0) goto L_0x0024
        L_0x0017:
            javax.crypto.spec.IvParameterSpec r2 = new javax.crypto.spec.IvParameterSpec     // Catch:{ Exception -> 0x0038 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x0038 }
            r1.init(r4, r0, r2)     // Catch:{ Exception -> 0x0038 }
        L_0x001f:
            byte[] r0 = r1.doFinal(r5)     // Catch:{ Exception -> 0x0038 }
        L_0x0023:
            return r0
        L_0x0024:
            int r2 = r1.getBlockSize()     // Catch:{ Exception -> 0x0038 }
            javax.crypto.spec.GCMParameterSpec r3 = new javax.crypto.spec.GCMParameterSpec     // Catch:{ Exception -> 0x0038 }
            int r2 = r2 << 3
            r3.<init>(r2, r6)     // Catch:{ Exception -> 0x0038 }
            r1.init(r4, r0, r3)     // Catch:{ InvalidAlgorithmParameterException -> 0x0033 }
            goto L_0x001f
        L_0x0033:
            r0 = move-exception
            r1 = 1
            b = r1     // Catch:{ Exception -> 0x0038 }
            throw r0     // Catch:{ Exception -> 0x0038 }
        L_0x0038:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.b(r0)
            if (r1 != 0) goto L_0x0042
            r0.printStackTrace()
        L_0x0042:
            r0 = 0
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(int, byte[], byte[]):byte[]");
    }

    public static byte[] b(int i, byte[] bArr, byte[] bArr2) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr2));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, generatePublic);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            if (!x.b(e)) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static boolean a(Context context, String str, long j) {
        x.c("[Util] Try to lock file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (file.exists()) {
                if (System.currentTimeMillis() - file.lastModified() < j) {
                    return false;
                }
                x.c("[Util] Lock file (%s) is expired, unlock it.", str);
                b(context, str);
            }
            if (file.createNewFile()) {
                x.c("[Util] Successfully locked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                return true;
            }
            x.c("[Util] Failed to locked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return false;
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    public static boolean b(Context context, String str) {
        x.c("[Util] Try to unlock file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (!file.exists()) {
                return true;
            }
            if (!file.delete()) {
                return false;
            }
            x.c("[Util] Successfully unlocked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return true;
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0070 A[SYNTHETIC, Splitter:B:37:0x0070] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r6, int r7, boolean r8) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x000f
            boolean r1 = r6.exists()
            if (r1 == 0) goto L_0x000f
            boolean r1 = r6.canRead()
            if (r1 != 0) goto L_0x0010
        L_0x000f:
            return r0
        L_0x0010:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x007c, all -> 0x006c }
            r1.<init>()     // Catch:{ Throwable -> 0x007c, all -> 0x006c }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x007c, all -> 0x006c }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x007c, all -> 0x006c }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x007c, all -> 0x006c }
            r4.<init>(r6)     // Catch:{ Throwable -> 0x007c, all -> 0x006c }
            java.lang.String r5 = "utf-8"
            r3.<init>(r4, r5)     // Catch:{ Throwable -> 0x007c, all -> 0x006c }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x007c, all -> 0x006c }
        L_0x0026:
            java.lang.String r3 = r2.readLine()     // Catch:{ Throwable -> 0x005d }
            if (r3 == 0) goto L_0x0046
            java.lang.StringBuilder r3 = r1.append(r3)     // Catch:{ Throwable -> 0x005d }
            java.lang.String r4 = "\n"
            r3.append(r4)     // Catch:{ Throwable -> 0x005d }
            if (r7 <= 0) goto L_0x0026
            int r3 = r1.length()     // Catch:{ Throwable -> 0x005d }
            if (r3 <= r7) goto L_0x0026
            if (r8 == 0) goto L_0x0053
            int r3 = r1.length()     // Catch:{ Throwable -> 0x005d }
            r1.delete(r7, r3)     // Catch:{ Throwable -> 0x005d }
        L_0x0046:
            java.lang.String r0 = r1.toString()     // Catch:{ Throwable -> 0x005d }
            r2.close()     // Catch:{ Exception -> 0x004e }
            goto L_0x000f
        L_0x004e:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
            goto L_0x000f
        L_0x0053:
            r3 = 0
            int r4 = r1.length()     // Catch:{ Throwable -> 0x005d }
            int r4 = r4 - r7
            r1.delete(r3, r4)     // Catch:{ Throwable -> 0x005d }
            goto L_0x0026
        L_0x005d:
            r1 = move-exception
        L_0x005e:
            com.tencent.bugly.proguard.x.a(r1)     // Catch:{ all -> 0x0079 }
            if (r2 == 0) goto L_0x000f
            r2.close()     // Catch:{ Exception -> 0x0067 }
            goto L_0x000f
        L_0x0067:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
            goto L_0x000f
        L_0x006c:
            r1 = move-exception
            r2 = r0
        L_0x006e:
            if (r2 == 0) goto L_0x0073
            r2.close()     // Catch:{ Exception -> 0x0074 }
        L_0x0073:
            throw r1
        L_0x0074:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
            goto L_0x0073
        L_0x0079:
            r0 = move-exception
            r1 = r0
            goto L_0x006e
        L_0x007c:
            r1 = move-exception
            r2 = r0
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(java.io.File, int, boolean):java.lang.String");
    }

    private static BufferedReader a(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return null;
        }
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (Throwable th) {
            x.a(th);
            return null;
        }
    }

    public static BufferedReader a(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str, str2);
            if (!file.exists() || !file.canRead()) {
                return null;
            }
            return a(file);
        } catch (NullPointerException e) {
            x.a(e);
            return null;
        }
    }

    public static Thread a(Runnable runnable, String str) {
        try {
            Thread thread = new Thread(runnable);
            thread.setName(str);
            thread.start();
            return thread;
        } catch (Throwable th) {
            x.e("[Util] Failed to start a thread to execute task with message: %s", th.getMessage());
            return null;
        }
    }

    public static boolean a(Runnable runnable) {
        if (runnable != null) {
            w a2 = w.a();
            if (a2 != null) {
                return a2.a(runnable);
            }
            String[] split = runnable.getClass().getName().split("\\.");
            if (a(runnable, split[split.length - 1]) != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean c(String str) {
        boolean z;
        if (str == null || str.trim().length() <= 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        if (str.length() > 255) {
            x.a("URL(%s)'s length is larger than 255.", str);
            return false;
        } else if (str.toLowerCase().startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            return true;
        } else {
            x.a("URL(%s) is not start with \"http\".", str);
            return false;
        }
    }

    public static SharedPreferences a(String str, Context context) {
        if (context != null) {
            return context.getSharedPreferences(str, 0);
        }
        return null;
    }

    public static String b(String str, String str2) {
        if (a.b() == null || a.b().E == null) {
            return "";
        }
        return a.b().E.getString(str, str2);
    }
}
