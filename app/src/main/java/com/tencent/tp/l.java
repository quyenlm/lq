package com.tencent.tp;

import android.content.Context;
import android.os.Looper;
import com.tencent.tp.a.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.CodeSigner;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class l {
    private static final String a = "is_enabled2:";

    l() {
    }

    public static int a(String str) {
        if (str == null) {
            return -1;
        }
        if (str.compareTo("initialize") == 0) {
            TssSdkRuntime.initialize2();
            return 0;
        } else if (str.startsWith("mt:")) {
            MainThreadDispatcher2.SendCmd(str.substring(3));
            return 0;
        } else if (str.startsWith("tpgbox_ld:")) {
            g(str.substring(10));
            return 0;
        } else if (str.startsWith("inf_cl:")) {
            c(str.substring(7));
            return 0;
        } else if (str.startsWith("getopenid")) {
            b();
            return 0;
        } else if (str.startsWith("getapk")) {
            c();
            return 0;
        } else if (str.compareTo("get_network") == 0) {
            m.a("net_type:" + TssSdkRuntime.getNetWorkType());
            return 0;
        } else if (str.startsWith(a)) {
            return 0;
        } else {
            if (str.startsWith("get_device_id")) {
                m.a("imei:" + h.a().a(TssSdkRuntime.getAppContext()));
                return 0;
            } else if (str.startsWith("is_main_looper")) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    return 0;
                }
                m.a("IsMainThread");
                return 0;
            } else if (str.startsWith("msgbox:") || str.startsWith("hide_msgbox:")) {
                e.a().a(str);
                return 0;
            } else if (str.startsWith("dl_file:")) {
                d(str.substring(8));
                return 0;
            } else if (str.startsWith("update_adb_enabled_over_usb:")) {
                h(str);
                return 0;
            } else {
                m.a("*#07#:" + str);
                return 0;
            }
        }
    }

    private static int a(String str, String str2) {
        if (!e(str)) {
            return -1;
        }
        if (e(str2)) {
            f(str2);
        }
        new File(str).renameTo(new File(str2));
        return 0;
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02x", new Object[]{Integer.valueOf(bArr[i] & 255)}));
        }
        return sb.toString();
    }

    private static void a(byte[] bArr, String str) throws Exception {
        String str2 = str + ".tmp";
        FileOutputStream fileOutputStream = new FileOutputStream(str2);
        try {
            fileOutputStream.write(bArr);
        } catch (IOException e) {
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        a(str2, str);
    }

    private static boolean a() {
        try {
            Object a2 = g.a("com.tencent.msdk.login.LoginManager", "getInstance", new Class[0], new Object[0]);
            if (a2 == null) {
                return false;
            }
            String str = (String) g.a("com.tencent.msdk.login.LoginManager", "getCurrentOpenid", a2, new Class[0], new Object[0]);
            if (str == null) {
                return false;
            }
            m.a("open_id_msdk:" + str);
            return true;
        } catch (Exception e) {
            m.a("open_id_msdk:" + e.toString());
            return false;
        }
    }

    private static void b() {
        if (!a()) {
            m.a("open_id_msdk:openId == null");
        }
    }

    private static boolean b(String str) {
        if (m.c() == 1) {
            return true;
        }
        try {
            JarFile jarFile = new JarFile(str, true);
            byte[] bArr = new byte[16384];
            InputStream inputStream = null;
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry nextElement = entries.nextElement();
                try {
                    inputStream = jarFile.getInputStream(nextElement);
                    do {
                    } while (inputStream.read(bArr) != -1);
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th) {
                        }
                    }
                    CodeSigner[] codeSigners = nextElement.getCodeSigners();
                    if (codeSigners != null) {
                        for (CodeSigner signerCertPath : codeSigners) {
                            for (Certificate encoded : signerCertPath.getSignerCertPath().getCertificates()) {
                                byte[] bArr2 = new byte[0];
                                try {
                                    if (!Arrays.equals(MessageDigest.getInstance("md5").digest(encoded.getEncoded()), new byte[]{18, -105, -96, 30, 41, -69, 36, -13, -6, -22, 34, 7, -101, 15, 109, 72})) {
                                        if (jarFile != null) {
                                            try {
                                                jarFile.close();
                                            } catch (Throwable th2) {
                                            }
                                        }
                                        return false;
                                    }
                                } catch (Throwable th3) {
                                }
                            }
                        }
                        continue;
                    } else if (!nextElement.getName().toUpperCase().equals("META-INF/CERT.RSA") && !nextElement.getName().toUpperCase().equals("META-INF/CERT.SF") && !nextElement.getName().toUpperCase().equals("META-INF/MANIFEST.MF")) {
                        if (jarFile != null) {
                            try {
                                jarFile.close();
                            } catch (Throwable th4) {
                            }
                        }
                        return false;
                    }
                } catch (Throwable th5) {
                    if (jarFile != null) {
                        try {
                            jarFile.close();
                        } catch (Throwable th6) {
                        }
                    }
                    throw th5;
                }
            }
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (Throwable th7) {
                }
            }
            return true;
        } catch (Throwable th8) {
            return false;
        }
        return false;
        return false;
        return false;
    }

    private static void c() {
        Context appContext = TssSdkRuntime.getAppContext();
        if (appContext != null) {
            new i(appContext).execute(new Void[0]);
        }
    }

    private static void c(String str) {
        boolean z = true;
        String[] split = str.split("\\|");
        if (split.length == 3) {
            boolean z2 = split[0].compareTo("1") == 0;
            boolean z3 = split[1].compareTo("1") == 0;
            if (split[2].compareTo("1") != 0) {
                z = false;
            }
            TssSdkSafeScan.scan(z2, z3, z);
        }
    }

    private static void d(String str) {
        String[] split = str.split("\\|");
        if (split.length == 2) {
            String str2 = split[0];
            String str3 = split[1];
            byte[] a2 = s.a(str2);
            if (a2 != null && a2.length != 0) {
                try {
                    a(a2, str3);
                } catch (Exception e) {
                }
            }
        }
    }

    private static boolean e(String str) {
        return new File(str).exists();
    }

    private static void f(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
    }

    private static void g(String str) {
        String[] split = str.split("\\|");
        if (split.length >= 3 && split[0].endsWith(".jar")) {
            String str2 = split[0];
            String str3 = split[1];
            String str4 = split[2];
            File file = new File(TssSdkRuntime.getPackageInfo().applicationInfo.dataDir + "/files/" + str2);
            if (file.exists() && b(file.getAbsolutePath())) {
                c.a(file.getAbsolutePath(), (String) null);
                if (str3.length() > 1) {
                    try {
                        Class a2 = c.a(str3);
                        g.a(a2, "setPluginInfos", a2.newInstance(), new Class[]{String.class}, new Object[]{str4});
                    } catch (Exception e) {
                        m.a("*#06#:" + e.toString());
                    }
                }
            } else {
                return;
            }
        }
        m.a("*#06#:" + str);
    }

    private static void h(String str) {
        boolean z;
        boolean z2;
        Matcher matcher = Pattern.compile("update_adb_enabled_over_usb:([\\d]+):([\\d]+)").matcher(str);
        if (matcher.matches()) {
            o.a(Integer.parseInt(matcher.group(1), 10), Integer.parseInt(matcher.group(2), 10));
            z2 = o.a();
            z = o.b();
        } else {
            z = false;
            z2 = false;
        }
        Context appContext = TssSdkRuntime.getAppContext();
        Object[] objArr = new Object[3];
        objArr[0] = appContext != null ? TssSdkRuntime.getAdbEnabledOverUsb(appContext) : false ? "1" : "0";
        objArr[1] = z2 ? "1" : "0";
        objArr[2] = z ? "1" : "0";
        m.a(String.format("update_adb_enabled_over_usb:%s:%s:%s", objArr));
    }
}
