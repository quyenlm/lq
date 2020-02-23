package com.tencent.smtt.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class b {
    public static String a = "";
    public static String b = "";
    public static String c = "";
    public static String d = "";
    public static String e = "";

    private static PackageInfo a(String str, int i) {
        Class cls;
        try {
            Class<?> cls2 = Class.forName("android.content.pm.PackageParser");
            Class[] declaredClasses = cls2.getDeclaredClasses();
            int length = declaredClasses.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    cls = null;
                    break;
                }
                cls = declaredClasses[i2];
                if (cls.getName().compareTo("android.content.pm.PackageParser$Package") == 0) {
                    break;
                }
                i2++;
            }
            Constructor<?> constructor = cls2.getConstructor(new Class[]{String.class});
            Method declaredMethod = cls2.getDeclaredMethod("parsePackage", new Class[]{File.class, String.class, DisplayMetrics.class, Integer.TYPE});
            Method declaredMethod2 = cls2.getDeclaredMethod("collectCertificates", new Class[]{cls, Integer.TYPE});
            Method declaredMethod3 = cls2.getDeclaredMethod("generatePackageInfo", new Class[]{cls, int[].class, Integer.TYPE, Long.TYPE, Long.TYPE});
            constructor.setAccessible(true);
            declaredMethod.setAccessible(true);
            declaredMethod2.setAccessible(true);
            declaredMethod3.setAccessible(true);
            Object newInstance = constructor.newInstance(new Object[]{str});
            DisplayMetrics displayMetrics = new DisplayMetrics();
            displayMetrics.setToDefaults();
            Object invoke = declaredMethod.invoke(newInstance, new Object[]{new File(str), str, displayMetrics, 0});
            if (invoke == null) {
                return null;
            }
            if ((i & 64) != 0) {
                declaredMethod2.invoke(newInstance, new Object[]{invoke, 0});
            }
            return (PackageInfo) declaredMethod3.invoke((Object) null, new Object[]{invoke, null, Integer.valueOf(i), 0, 0});
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0061 A[SYNTHETIC, Splitter:B:27:0x0061] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0066 A[SYNTHETIC, Splitter:B:30:0x0066] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006f A[SYNTHETIC, Splitter:B:35:0x006f] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0074 A[SYNTHETIC, Splitter:B:38:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a() {
        /*
            r3 = 0
            java.lang.String r0 = c
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x000c
            java.lang.String r0 = c
        L_0x000b:
            return r0
        L_0x000c:
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x004e, all -> 0x006a }
            java.lang.String r1 = "getprop ro.product.cpu.abi"
            java.lang.Process r0 = r0.exec(r1)     // Catch:{ Throwable -> 0x004e, all -> 0x006a }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x004e, all -> 0x006a }
            java.io.InputStream r0 = r0.getInputStream()     // Catch:{ Throwable -> 0x004e, all -> 0x006a }
            r4.<init>(r0)     // Catch:{ Throwable -> 0x004e, all -> 0x006a }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0085, all -> 0x0080 }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0085, all -> 0x0080 }
            java.lang.String r0 = r2.readLine()     // Catch:{ Throwable -> 0x0089 }
            java.lang.String r1 = "x86"
            boolean r0 = r0.contains(r1)     // Catch:{ Throwable -> 0x0089 }
            if (r0 == 0) goto L_0x0043
            java.lang.String r0 = "i686"
            java.lang.String r0 = a((java.lang.String) r0)     // Catch:{ Throwable -> 0x0089 }
        L_0x0036:
            if (r2 == 0) goto L_0x003b
            r2.close()     // Catch:{ IOException -> 0x007e }
        L_0x003b:
            if (r4 == 0) goto L_0x000b
            r4.close()     // Catch:{ IOException -> 0x0041 }
            goto L_0x000b
        L_0x0041:
            r1 = move-exception
            goto L_0x000b
        L_0x0043:
            java.lang.String r0 = "os.arch"
            java.lang.String r0 = java.lang.System.getProperty(r0)     // Catch:{ Throwable -> 0x0089 }
            java.lang.String r0 = a((java.lang.String) r0)     // Catch:{ Throwable -> 0x0089 }
            goto L_0x0036
        L_0x004e:
            r0 = move-exception
            r1 = r0
            r2 = r3
            r4 = r3
        L_0x0052:
            java.lang.String r0 = "os.arch"
            java.lang.String r0 = java.lang.System.getProperty(r0)     // Catch:{ all -> 0x0083 }
            java.lang.String r0 = a((java.lang.String) r0)     // Catch:{ all -> 0x0083 }
            r1.printStackTrace()     // Catch:{ all -> 0x0083 }
            if (r2 == 0) goto L_0x0064
            r2.close()     // Catch:{ IOException -> 0x007c }
        L_0x0064:
            if (r4 == 0) goto L_0x000b
            r4.close()     // Catch:{ IOException -> 0x0041 }
            goto L_0x000b
        L_0x006a:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x006d:
            if (r2 == 0) goto L_0x0072
            r2.close()     // Catch:{ IOException -> 0x0078 }
        L_0x0072:
            if (r4 == 0) goto L_0x0077
            r4.close()     // Catch:{ IOException -> 0x007a }
        L_0x0077:
            throw r0
        L_0x0078:
            r1 = move-exception
            goto L_0x0072
        L_0x007a:
            r1 = move-exception
            goto L_0x0077
        L_0x007c:
            r1 = move-exception
            goto L_0x0064
        L_0x007e:
            r1 = move-exception
            goto L_0x003b
        L_0x0080:
            r0 = move-exception
            r2 = r3
            goto L_0x006d
        L_0x0083:
            r0 = move-exception
            goto L_0x006d
        L_0x0085:
            r0 = move-exception
            r1 = r0
            r2 = r3
            goto L_0x0052
        L_0x0089:
            r0 = move-exception
            r1 = r0
            goto L_0x0052
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.b.a():java.lang.String");
    }

    public static String a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            return null;
        }
    }

    public static String a(Context context, File file) {
        try {
            if (context.getApplicationContext().getPackageName().contains("com.jd.jrapp")) {
                TbsLog.i("AppUtil", "[AppUtil.getSignatureFromApk]  #1");
                String a2 = a(file);
                if (a2 != null) {
                    TbsLog.i("AppUtil", "[AppUtil.getSignatureFromApk]  #2");
                    return a2;
                }
            }
        } catch (Throwable th) {
            TbsLog.i("AppUtil", "[AppUtil.getSignatureFromApk]  #3");
        }
        TbsLog.i("AppUtil", "[AppUtil.getSignatureFromApk]  #4");
        String a3 = a(context, file, false);
        TbsLog.i("AppUtil", "[AppUtil.getSignatureFromApk]  android api signature=" + a3);
        if (a3 == null) {
            a3 = a(file);
            TbsLog.i("AppUtil", "[AppUtil.getSignatureFromApk]  java get signature=" + a3);
        }
        if (a3 != null) {
            return a3;
        }
        String a4 = a(context, file, true);
        TbsLog.i("AppUtil", "[AppUtil.getSignatureFromApk]  android reflection signature=" + a4);
        return a4;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(android.content.Context r4, java.io.File r5, boolean r6) {
        /*
            r0 = 0
            r3 = 65
            if (r6 == 0) goto L_0x0024
            java.lang.String r1 = r5.getAbsolutePath()
            android.content.pm.PackageInfo r1 = a((java.lang.String) r1, (int) r3)
        L_0x000d:
            if (r1 == 0) goto L_0x0038
            android.content.pm.Signature[] r2 = r1.signatures
            if (r2 == 0) goto L_0x0031
            android.content.pm.Signature[] r2 = r1.signatures
            int r2 = r2.length
            if (r2 <= 0) goto L_0x0031
            android.content.pm.Signature[] r1 = r1.signatures
            r2 = 0
            r1 = r1[r2]
        L_0x001d:
            if (r1 == 0) goto L_0x0023
            java.lang.String r0 = r1.toCharsString()
        L_0x0023:
            return r0
        L_0x0024:
            android.content.pm.PackageManager r1 = r4.getPackageManager()
            java.lang.String r2 = r5.getAbsolutePath()
            android.content.pm.PackageInfo r1 = r1.getPackageArchiveInfo(r2, r3)
            goto L_0x000d
        L_0x0031:
            java.lang.String r1 = "AppUtil"
            java.lang.String r2 = "[getSignatureFromApk] pkgInfo is not null BUT signatures is null!"
            com.tencent.smtt.utils.TbsLog.w(r1, r2)
        L_0x0038:
            r1 = r0
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.b.a(android.content.Context, java.io.File, boolean):java.lang.String");
    }

    public static String a(Context context, String str) {
        String str2;
        try {
            try {
                return String.valueOf(Integer.toHexString(Integer.parseInt(String.valueOf(context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get(str)))));
            } catch (Exception e2) {
                return str2;
            }
        } catch (Exception e3) {
            return null;
        }
    }

    private static String a(File file) {
        try {
            JarFile jarFile = new JarFile(file);
            byte[] bArr = new byte[8192];
            String a2 = a(a(jarFile, jarFile.getJarEntry("AndroidManifest.xml"), bArr)[0].getEncoded());
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry nextElement = entries.nextElement();
                String name = nextElement.getName();
                if (name != null) {
                    Certificate[] a3 = a(jarFile, nextElement, bArr);
                    String a4 = a3 != null ? a(a3[0].getEncoded()) : null;
                    if (a4 == null) {
                        if (!name.startsWith("META-INF/")) {
                            return null;
                        }
                    } else if (!a4.equals(a2)) {
                        return null;
                    }
                }
            }
            return a2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String a(String str) {
        return str == null ? "" : str;
    }

    private static String a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[(length * 2)];
        for (int i = 0; i < length; i++) {
            byte b2 = bArr[i];
            int i2 = (b2 >> 4) & 15;
            cArr[i * 2] = (char) (i2 >= 10 ? (i2 + 97) - 10 : i2 + 48);
            byte b3 = b2 & 15;
            cArr[(i * 2) + 1] = (char) (b3 >= 10 ? (b3 + 97) - 10 : b3 + 48);
        }
        return new String(cArr);
    }

    private static Certificate[] a(JarFile jarFile, JarEntry jarEntry, byte[] bArr) {
        InputStream inputStream = jarFile.getInputStream(jarEntry);
        do {
        } while (inputStream.read(bArr, 0, bArr.length) != -1);
        inputStream.close();
        if (jarEntry != null) {
            return jarEntry.getCertificates();
        }
        return null;
    }

    public static int b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e2) {
            return 0;
        }
    }

    public static String c(Context context) {
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String d(Context context) {
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String e(Context context) {
        if (!TextUtils.isEmpty(d)) {
            return d;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            WifiInfo connectionInfo = wifiManager == null ? null : wifiManager.getConnectionInfo();
            return connectionInfo == null ? "" : connectionInfo.getMacAddress();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String f(Context context) {
        if (!TextUtils.isEmpty(e)) {
            return e;
        }
        try {
            e = Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return e;
    }
}
