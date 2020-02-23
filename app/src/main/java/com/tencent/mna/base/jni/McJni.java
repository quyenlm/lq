package com.tencent.mna.base.jni;

class McJni {
    public static native int endMcSpeed();

    public static native int getExportDelay(int i, int i2, int i3);

    public static native int getForwardDelay(int i, int i2, int i3, int i4, int i5);

    public static native int getMatchForwardDelay(int i, int i2, int i3, int i4, int i5, String str);

    public static native long getMcConnectPtr();

    public static native long getMcRecvFromPtr();

    public static native long getMcRecvMsgPtr();

    public static native long getMcRecvPtr();

    public static native long getMcSendMsgPtr();

    public static native long getMcSendPtr();

    public static native long getMcSendToPtr();

    public static native int getV6ExportDelay(int i, int i2, int i3);

    public static native int prepare(int i, int i2, String str, String str2, int i3, int i4, String str3, int i5, int i6, int i7, int i8, int i9, boolean z);

    public static native int prepareExport(String str, int i, boolean z);

    public static native void setIsShouldMobile(boolean z);

    McJni() {
    }
}
