package com.tencent.mna.base.jni;

/* compiled from: InoJniWrapper */
public class c {
    public static int a(String str, int i, String str2, int i2, String str3, String str4, int i3, boolean z) {
        try {
            return InoJni.prepare(str, i, str2, i2, str3, str4, i3, z);
        } catch (Throwable th) {
            return 65000;
        }
    }

    public static int a(String str, int i, boolean z) {
        try {
            return InoJni.prepareExport(str, i, z);
        } catch (Throwable th) {
            return 66000;
        }
    }

    public static long a() {
        try {
            return InoJni.getInoSendToPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long b() {
        try {
            return InoJni.getInoRecvFromPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long c() {
        try {
            return InoJni.getInoSendMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long d() {
        try {
            return InoJni.getInoRecvMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long e() {
        try {
            return InoJni.getInoConnectPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long f() {
        try {
            return InoJni.getInoSendPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long g() {
        try {
            return InoJni.getInoRecvPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int h() {
        try {
            return InoJni.endInoSpeed();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5) {
        try {
            return InoJni.getForwardDelay(i, i2, i3, i4, i5);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5, String str) {
        try {
            return InoJni.getMatchForwardDelay(i, i2, i3, i4, i5, str);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(int i, int i2, int i3) {
        try {
            return InoJni.getExportDelay(i, i2, i3);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int b(int i, int i2, int i3) {
        try {
            return InoJni.getV6ExportDelay(i, i2, i3);
        } catch (Throwable th) {
            return 501;
        }
    }
}
