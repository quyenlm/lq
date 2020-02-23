package com.tencent.mna.base.jni;

import com.tencent.mna.base.jni.entity.CloudRet;

class MnaJni {
    public static native void clear();

    public static native void closeFd(int i);

    public static native int detectLocalIpStack();

    public static native String dns(String str);

    public static native int endFps(String str);

    public static native int getDirectDelay(int i, int i2, int i3, int i4, String str, int i5);

    public static native int getFd(int i);

    public static native String getInfo(String str);

    public static native int getMatchDirectDelay(int i, int i2, int i3, int i4, String str, int i5);

    public static native int getSoVersion();

    public static native int getTcpFd(int i);

    public static native int getTcpV6Fd(int i);

    public static native int getV6DirectDelay(int i, byte[] bArr, int i2, int i3, String str, int i4);

    public static native int getV6Fd(int i);

    public static native int getV6MatchDirectDelay(int i, byte[] bArr, int i2, int i3, String str, int i4);

    public static native int hookClose(String str, long j);

    public static native int hookUdpConnectSendMsg(String str, long j, long j2, long j3);

    public static native int hookUdpConnectSendTo(String str, long j, long j2, long j3);

    public static native int hookUdpSend(String str, long j, long j2, long j3);

    public static native int hookUdpSendMsg(String str, long j, long j2);

    public static native int hookUdpSendTo(String str, long j, long j2);

    public static native void init(int i, boolean z, String str);

    public static native void kartinNotify(long j, String str, int i, String str2, int i2, int i3, int i4, int i5, String str3, int i6, int i7, String str4, int i8, int i9, String str5, int i10, int i11, String str6, int i12, int i13, int i14, String str7, int i15, String str8, int i16);

    public static native void nHook(boolean z);

    public static native void notify(long j, int i, int i2, String str);

    public static native CloudRet requestCloud(boolean z, int i, String str, int i2, String str2, int i3);

    public static native int sendToUnity(String str);

    public static native int setFdTos(int i, int i2);

    public static native void setHookIps(String[] strArr);

    public static native void setHookPort(int i);

    public static native void setIsLoadMap(boolean z);

    public static native void setIsShouldSpeed(boolean z);

    public static native void setLoadMapSwitch(int i);

    public static native void setMobileVip(String str);

    public static native void setPkg(String str, String str2);

    public static native boolean startDoubleNeg(int i, String str, int i2, String str2);

    public static native int startFps(String str, int i);

    public static native void startUdpRecvLoop(int i, int i2);

    public static native void startUdpSendLoop(int i, String str, int i2, int i3, int i4, int i5, int i6);

    public static native boolean startV6DoubleNeg(int i, byte[] bArr, int i2, String str);

    public static native void startV6UdpSendLoop(int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6);

    public static native void switchNetworkBindingIdle();

    public static native void switchNetworkBindingToMobile();

    public static native void switchNetworkBindingToWifi();

    public static native void turnDoubleSend(int i, int i2, int i3, int i4);

    public static native void turnFilter(boolean z, int i);

    public static native void turnHookedFdTos(int i);

    public static native int unhookClose(String str);

    public static native int unhookUdpConnectSendMsg(String str);

    public static native int unhookUdpConnectSendTo(String str);

    public static native int unhookUdpSend(String str);

    public static native int unhookUdpSendMsg(String str);

    public static native int unhookUdpSendTo(String str);

    static native int uploadPingValue(int i, int i2, int i3, int i4, String str, int i5);

    MnaJni() {
    }
}
