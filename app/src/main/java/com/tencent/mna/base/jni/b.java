package com.tencent.mna.base.jni;

/* compiled from: DsJniWrapper */
public class b {
    public static int a(String str, int i, String str2, int i2, int i3, int i4, int i5, int i6, boolean z) {
        try {
            return DsJni.prepare(str, i, str2, i2, i3, i4, i5, i6, z);
        } catch (Throwable th) {
            return 80404;
        }
    }

    public static int a(String str, int i, boolean z) {
        try {
            return DsJni.prepareExport(str, i, z);
        } catch (Throwable th) {
            return 66404;
        }
    }

    public static long a() {
        try {
            return DsJni.getDsSendToPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long b() {
        try {
            return DsJni.getDsRecvFromPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long c() {
        try {
            return DsJni.getDsSendMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long d() {
        try {
            return DsJni.getDsRecvMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long e() {
        try {
            return DsJni.getDsConnectPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long f() {
        try {
            return DsJni.getDsSendPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long g() {
        try {
            return DsJni.getDsRecvPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int h() {
        try {
            return DsJni.endDsSpeed();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5) {
        try {
            return DsJni.getForwardDelay(i, i2, i3, i4, i5);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5, String str) {
        try {
            return DsJni.getMatchForwardDelay(i, i2, i3, i4, i5, str);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(int i, int i2, int i3) {
        try {
            return DsJni.getExportDelay(i, i2, i3);
        } catch (Throwable th) {
            return 501;
        }
    }
}
