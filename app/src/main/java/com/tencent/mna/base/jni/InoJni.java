package com.tencent.mna.base.jni;

class InoJni {
    public static native int endInoSpeed();

    public static native int getExportDelay(int i, int i2, int i3);

    public static native int getForwardDelay(int i, int i2, int i3, int i4, int i5);

    public static native long getInoConnectPtr();

    public static native long getInoRecvFromPtr();

    public static native long getInoRecvMsgPtr();

    public static native long getInoRecvPtr();

    public static native long getInoSendMsgPtr();

    public static native long getInoSendPtr();

    public static native long getInoSendToPtr();

    public static native int getMatchForwardDelay(int i, int i2, int i3, int i4, int i5, String str);

    public static native int getV6ExportDelay(int i, int i2, int i3);

    public static native int prepare(String str, int i, String str2, int i2, String str3, String str4, int i3, boolean z);

    public static native int prepareExport(String str, int i, boolean z);

    InoJni() {
    }
}
