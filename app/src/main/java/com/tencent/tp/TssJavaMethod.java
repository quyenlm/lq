package com.tencent.tp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TssJavaMethod {
    private static ITssJavaMethod2 a = null;
    public static String runtime_sdk_version = TssSdk.TSS_SDK_VERSION;

    static {
        b();
    }

    private static String a(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            stringBuffer.append(cArr[(bArr[i] & 240) >> 4]);
            stringBuffer.append(cArr[bArr[i] & 15]);
        }
        return stringBuffer.toString();
    }

    private static boolean a() {
        boolean z = true;
        if (!TssJavaMethod.class.getName().equals("com.tencent.tp.TssJavaMethod") || TssSdkRuntime.getPackageInfo() == null) {
            return false;
        }
        File file = new File(TssSdkRuntime.getPackageInfo().applicationInfo.dataDir + "/files/tersafeupdate2.jar");
        if (!file.exists()) {
            return false;
        }
        try {
            JarFile jarFile = new JarFile(file.getAbsoluteFile(), true);
            if (!a(jarFile)) {
                m.a("*#06#:!jar.ver");
                jarFile.close();
                return false;
            }
            byte[] bArr = new byte[128];
            String str = new String(bArr, 0, jarFile.getInputStream(jarFile.getJarEntry("version.txt")).read(bArr));
            jarFile.close();
            if (TssSdk.TSS_SDK_VERSION.compareTo(str) < 0) {
                runtime_sdk_version = str;
                c.a(file.getAbsolutePath(), (String) null);
            } else {
                z = false;
            }
            return z;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean a(JarFile jarFile) {
        boolean z;
        boolean z2;
        Certificate[] certificateArr = null;
        try {
            byte[] bArr = new byte[4096];
            Enumeration<JarEntry> entries = jarFile.entries();
            boolean z3 = false;
            while (entries.hasMoreElements()) {
                try {
                    JarEntry nextElement = entries.nextElement();
                    if (!nextElement.isDirectory() && !nextElement.getName().startsWith("META-INF/")) {
                        Certificate[] a2 = a(jarFile, nextElement, bArr);
                        if (a2 == null) {
                            return false;
                        }
                        if (certificateArr == null) {
                            MessageDigest instance = MessageDigest.getInstance("MD5");
                            instance.update(a2[0].getEncoded());
                            z = "2BD6F965D7704D957A7AF1462EC17E5F".compareToIgnoreCase(a(instance.digest())) == 0 ? true : z3;
                        } else {
                            for (int i = 0; i < certificateArr.length; i++) {
                                int i2 = 0;
                                while (true) {
                                    if (i2 < a2.length) {
                                        if (certificateArr[i] != null && certificateArr[i].equals(a2[i2])) {
                                            z2 = true;
                                            break;
                                        }
                                        i2++;
                                    } else {
                                        z2 = false;
                                        break;
                                    }
                                }
                                if (!z2 || certificateArr.length != a2.length) {
                                    return false;
                                }
                            }
                            a2 = certificateArr;
                            z = z3;
                        }
                        certificateArr = a2;
                        z3 = z;
                    }
                } catch (NoSuchAlgorithmException | CertificateEncodingException e) {
                    return z3;
                }
            }
            return z3;
        } catch (NoSuchAlgorithmException e2) {
            return false;
        } catch (CertificateEncodingException e3) {
            return false;
        }
    }

    private static Certificate[] a(JarFile jarFile, JarEntry jarEntry, byte[] bArr) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(jarFile.getInputStream(jarEntry));
            do {
            } while (bufferedInputStream.read(bArr, 0, bArr.length) != -1);
            bufferedInputStream.close();
            if (jarEntry != null) {
                return jarEntry.getCertificates();
            }
            return null;
        } catch (IOException | RuntimeException e) {
            return null;
        }
    }

    private static void b() {
        if (a == null) {
            try {
                if (!a()) {
                    a = null;
                } else {
                    Class a2 = c.a("com.tencent.up_tp.TssJavaMethodImp2");
                    if (a2 != null) {
                        a = (ITssJavaMethod2) a2.newInstance();
                    } else {
                        throw new Exception("com.tencent.up_tp.TssJavaMethodImp2 not found");
                    }
                }
            } catch (Exception e) {
                if (m.c() == 1) {
                    try {
                        m.b("*#06#:" + e.toString());
                    } catch (Exception e2) {
                    }
                }
            }
            if (a == null) {
                a = new k();
            }
        }
    }

    public static void initialize() {
        if (a != null) {
            a.initialize();
        }
    }

    public static void invokeForceUpdateRootkitAppRequest() {
        if (a != null) {
            a.invokeForceUpdateRootkitAppRequest();
        }
    }

    public static void invokeRootkitAppRequest() {
        if (a != null) {
            a.invokeRootkitAppRequest();
        }
    }

    public static void invokeRootkitIsRunningTip() {
        if (a != null) {
            a.invokeRootkitIsRunningTip();
        }
    }

    public static void scan() {
        if (a != null) {
            a.scan();
        }
    }

    public static void sendCmd(String str) {
        if (a != null) {
            a.sendCmd(str);
        }
    }

    public static int sendCmdEx(String str) {
        if (a != null) {
            return a.sendCmd(str);
        }
        return 0;
    }

    public static void showMsgBoxEx() {
        if (a != null) {
            a.showMsgBoxEx();
        }
    }
}
