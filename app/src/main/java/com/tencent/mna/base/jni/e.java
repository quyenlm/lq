package com.tencent.mna.base.jni;

import com.tencent.mna.base.jni.entity.CloudRet;

/* compiled from: MnaJniWrapper */
public class e {
    public static void a(int i, boolean z, String str) {
        try {
            MnaJni.init(i, z, str);
        } catch (Throwable th) {
        }
    }

    public static int a() {
        try {
            return MnaJni.getSoVersion();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static void a(String str, String str2) {
        try {
            MnaJni.setPkg(str, str2);
        } catch (Throwable th) {
        }
    }

    public static String a(String str) {
        try {
            return MnaJni.getInfo(str);
        } catch (Throwable th) {
            return "";
        }
    }

    public static CloudRet a(boolean z, int i, String str, int i2, String str2, int i3) {
        try {
            return MnaJni.requestCloud(z, i, str, i2, str2, i3);
        } catch (Throwable th) {
            return null;
        }
    }

    public static int a(int i) {
        try {
            return MnaJni.getFd(i);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int b(int i) {
        try {
            return MnaJni.getV6Fd(i);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int c(int i) {
        try {
            return MnaJni.getTcpFd(i);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static void d(int i) {
        try {
            MnaJni.closeFd(i);
        } catch (Throwable th) {
        }
    }

    public static int a(int i, byte[] bArr, int i2, int i3, String str, int i4) {
        try {
            return MnaJni.getV6DirectDelay(i, bArr, i2, i3, str, i4);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int b(int i, byte[] bArr, int i2, int i3, String str, int i4) {
        try {
            return MnaJni.getV6MatchDirectDelay(i, bArr, i2, i3, str, i4);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int a(int i, int i2, int i3, int i4, String str, int i5) {
        try {
            return MnaJni.uploadPingValue(i, i2, i3, i4, str, i5);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static void a(boolean z) {
        try {
            MnaJni.nHook(z);
        } catch (Throwable th) {
        }
    }

    public static int a(String str, long j, long j2) {
        try {
            return MnaJni.hookUdpSendTo(str, j, j2);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int b(String str, long j, long j2) {
        try {
            return MnaJni.hookUdpSendMsg(str, j, j2);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int a(String str, long j, long j2, long j3) {
        try {
            return MnaJni.hookUdpSend(str, j, j2, j3);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int b(String str, long j, long j2, long j3) {
        try {
            return MnaJni.hookUdpConnectSendTo(str, j, j2, j3);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int c(String str, long j, long j2, long j3) {
        try {
            return MnaJni.hookUdpConnectSendMsg(str, j, j2, j3);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int a(String str, long j) {
        try {
            return MnaJni.hookClose(str, j);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int b(String str) {
        try {
            return MnaJni.unhookUdpSendTo(str);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int c(String str) {
        try {
            return MnaJni.unhookUdpSendMsg(str);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int d(String str) {
        try {
            return MnaJni.unhookUdpSend(str);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int e(String str) {
        try {
            return MnaJni.unhookUdpConnectSendTo(str);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int f(String str) {
        try {
            return MnaJni.unhookUdpConnectSendMsg(str);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int g(String str) {
        try {
            return MnaJni.unhookClose(str);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static void a(boolean z, int i) {
        try {
            MnaJni.turnFilter(z, i);
        } catch (Throwable th) {
        }
    }

    public static void a(int i, int i2, int i3, int i4) {
        try {
            MnaJni.turnDoubleSend(i, i2, i3, i4);
        } catch (Throwable th) {
        }
    }

    public static boolean a(int i, byte[] bArr, int i2, String str) {
        try {
            return MnaJni.startV6DoubleNeg(i, bArr, i2, str);
        } catch (Throwable th) {
            return false;
        }
    }

    public static void a(int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6) {
        try {
            MnaJni.startV6UdpSendLoop(i, bArr, i2, i3, i4, i5, i6);
        } catch (Throwable th) {
        }
    }

    public static void a(int i, int i2) {
        try {
            MnaJni.startUdpRecvLoop(i, i2);
        } catch (Throwable th) {
        }
    }

    public static void a(String[] strArr) {
        try {
            MnaJni.setHookIps(strArr);
        } catch (Throwable th) {
        }
    }

    public static void e(int i) {
        try {
            MnaJni.setHookPort(i);
        } catch (Throwable th) {
        }
    }

    public static void h(String str) {
        try {
            MnaJni.setMobileVip(str);
        } catch (Throwable th) {
        }
    }

    public static void f(int i) {
        try {
            MnaJni.setLoadMapSwitch(i);
        } catch (Throwable th) {
        }
    }

    public static void b(boolean z) {
        try {
            MnaJni.setIsLoadMap(z);
        } catch (Throwable th) {
        }
    }

    public static void c(boolean z) {
        try {
            MnaJni.setIsShouldSpeed(z);
        } catch (Throwable th) {
        }
    }

    public static void b() {
        try {
            MnaJni.switchNetworkBindingIdle();
        } catch (Throwable th) {
        }
    }

    public static void c() {
        try {
            MnaJni.switchNetworkBindingToMobile();
        } catch (Throwable th) {
        }
    }

    public static void d() {
        try {
            MnaJni.switchNetworkBindingToWifi();
        } catch (Throwable th) {
        }
    }

    public static void e() {
        try {
            MnaJni.clear();
        } catch (Throwable th) {
        }
    }

    public static void a(long j, int i, int i2, String str) {
        try {
            MnaJni.notify(j, i, i2, str);
        } catch (Throwable th) {
        }
    }

    public static void a(long j, String str, int i, String str2, int i2, int i3, int i4, int i5, String str3, int i6, int i7, String str4, int i8, int i9, String str5, int i10, int i11, String str6, int i12, int i13, int i14, String str7, int i15, String str8, int i16) {
        try {
            MnaJni.kartinNotify(j, str, i, str2, i2, i3, i4, i5, str3, i6, i7, str4, i8, i9, str5, i10, i11, str6, i12, i13, i14, str7, i15, str8, i16);
        } catch (Throwable th) {
        }
    }

    public static int f() {
        try {
            return MnaJni.detectLocalIpStack();
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int i(String str) {
        try {
            return MnaJni.sendToUnity(str);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static void g(int i) {
        try {
            MnaJni.turnHookedFdTos(i);
        } catch (Throwable th) {
        }
    }

    public static int b(int i, int i2) {
        try {
            return MnaJni.setFdTos(i, i2);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int a(String str, int i) {
        try {
            return MnaJni.startFps(str, i);
        } catch (Throwable th) {
            return -4;
        }
    }

    public static int j(String str) {
        try {
            return MnaJni.endFps(str);
        } catch (Throwable th) {
            return -4;
        }
    }
}
