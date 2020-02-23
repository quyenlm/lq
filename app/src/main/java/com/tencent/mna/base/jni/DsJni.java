package com.tencent.mna.base.jni;

class DsJni {
    public static native int endDsSpeed();

    public static native long getDsConnectPtr();

    public static native long getDsRecvFromPtr();

    public static native long getDsRecvMsgPtr();

    public static native long getDsRecvPtr();

    public static native long getDsSendMsgPtr();

    public static native long getDsSendPtr();

    public static native long getDsSendToPtr();

    public static native int getExportDelay(int i, int i2, int i3);

    public static native int getForwardDelay(int i, int i2, int i3, int i4, int i5);

    public static native int getMatchForwardDelay(int i, int i2, int i3, int i4, int i5, String str);

    public static native int getV6ExportDelay(int i, int i2, int i3);

    public static native int prepare(String str, int i, String str2, int i2, int i3, int i4, int i5, int i6, boolean z);

    public static native int prepareExport(String str, int i, boolean z);

    public static native void setIsFilterOn(boolean z);

    public static native void setIsShouldMobile(boolean z);

    DsJni() {
    }
}
