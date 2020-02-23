package com.tencent.mna.base.jni;

import com.tencent.mna.base.jni.entity.TCallExportInfo;
import com.tencent.mna.base.jni.entity.TCallTunnelRet;

class TCallJni {
    public static native int connectNegotiate(String str, String str2, int i, String str3, String str4);

    public static native TCallTunnelRet createTunnel(String str, String str2);

    public static native int getExportDelay(String str);

    public static native TCallExportInfo getExportInfo();

    public static native String getExportIp();

    public static native int getForwardDelay(int i, int i2, int i3, int i4, int i5);

    public static native int getMatchForwardDelay(int i, int i2, int i3, int i4, int i5, String str);

    public static native long getTCallClosePtr();

    public static native long getTCallConnectPtr();

    public static native long getTCallRecvFromPtr();

    public static native long getTCallRecvMsgPtr();

    public static native long getTCallRecvPtr();

    public static native long getTCallSendMsgPtr();

    public static native long getTCallSendPtr();

    public static native long getTCallSendToPtr();

    public static native int tcallInit(boolean z);

    TCallJni() {
    }
}
