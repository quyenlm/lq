package com.tencent.mna.base.jni;

/* compiled from: McJniWrapper */
public class d {
    public static int a(int i, int i2, String str, String str2, int i3, int i4, String str3, int i5, int i6, int i7, int i8, int i9, boolean z) {
        try {
            return McJni.prepare(i, i2, str, str2, i3, i4, str3, i5, i6, i7, i8, i9, z);
        } catch (Throwable th) {
            return 80404;
        }
    }

    public static int a(String str, int i, boolean z) {
        try {
            return McJni.prepareExport(str, i, z);
        } catch (Throwable th) {
            return 66404;
        }
    }

    public static void a(boolean z) {
        try {
            McJni.setIsShouldMobile(z);
        } catch (Throwable th) {
        }
    }

    public static long a() {
        try {
            return McJni.getMcSendToPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long b() {
        try {
            return McJni.getMcRecvFromPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long c() {
        try {
            return McJni.getMcSendMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long d() {
        try {
            return McJni.getMcRecvMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long e() {
        try {
            return McJni.getMcConnectPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long f() {
        try {
            return McJni.getMcSendPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long g() {
        try {
            return McJni.getMcRecvPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int h() {
        try {
            return McJni.endMcSpeed();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5) {
        try {
            return McJni.getForwardDelay(i, i2, i3, i4, i5);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5, String str) {
        try {
            return McJni.getMatchForwardDelay(i, i2, i3, i4, i5, str);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(int i, int i2, int i3) {
        try {
            return McJni.getExportDelay(i, i2, i3);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int b(int i, int i2, int i3) {
        try {
            return McJni.getV6ExportDelay(i, i2, i3);
        } catch (Throwable th) {
            return 501;
        }
    }
}
