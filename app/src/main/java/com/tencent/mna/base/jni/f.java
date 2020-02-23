package com.tencent.mna.base.jni;

import com.tencent.mna.base.jni.entity.TCallExportInfo;
import com.tencent.mna.base.jni.entity.TCallTunnelRet;

/* compiled from: TCallJniWrapper */
public class f {
    public static int a(boolean z) {
        try {
            return TCallJni.tcallInit(z);
        } catch (Throwable th) {
            return 70000;
        }
    }

    public static TCallTunnelRet a(String str, String str2) {
        try {
            return TCallJni.createTunnel(str, str2);
        } catch (Throwable th) {
            return null;
        }
    }

    public static int a(String str, String str2, int i, String str3, String str4) {
        try {
            return TCallJni.connectNegotiate(str, str2, i, str3, str4);
        } catch (Throwable th) {
            return 70002;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5) {
        try {
            return TCallJni.getForwardDelay(i, i2, i3, i4, i5);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5, String str) {
        try {
            return TCallJni.getMatchForwardDelay(i, i2, i3, i4, i5, str);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(String str) {
        try {
            return TCallJni.getExportDelay(str);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static TCallExportInfo a() {
        try {
            return TCallJni.getExportInfo();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String b() {
        try {
            return TCallJni.getExportIp();
        } catch (Throwable th) {
            return "0.0.0.0";
        }
    }

    public static long c() {
        try {
            return TCallJni.getTCallSendToPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long d() {
        try {
            return TCallJni.getTCallRecvFromPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long e() {
        try {
            return TCallJni.getTCallSendMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long f() {
        try {
            return TCallJni.getTCallRecvMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long g() {
        try {
            return TCallJni.getTCallConnectPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long h() {
        try {
            return TCallJni.getTCallSendPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long i() {
        try {
            return TCallJni.getTCallRecvPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long j() {
        try {
            return TCallJni.getTCallClosePtr();
        } catch (Throwable th) {
            return 0;
        }
    }
}
