package com.tencent.mna.base.jni;

import com.tencent.mna.base.jni.entity.CdnMasterRet;
import com.tencent.mna.base.jni.entity.CdnNegRet;

class CdnJni {
    public static native int endCdnSpeed();

    public static native long getCdnConnectPtr();

    public static native long getCdnRecvFromPtr();

    public static native long getCdnRecvMsgPtr();

    public static native long getCdnRecvPtr();

    public static native long getCdnSendMsgPtr();

    public static native long getCdnSendPtr();

    public static native long getCdnSendToPtr();

    public static native int getExportDelay(int i, int i2, int i3);

    public static native int getForwardDelay(int i, int i2, int i3, int i4, int i5);

    public static native int getMatchForwardDelay(int i, int i2, int i3, int i4, int i5, String str);

    public static native int getV6ExportDelay(int i, int i2, int i3);

    public static native CdnMasterRet reqMaster(String str, int i, String str2, int i2, String str3, boolean z);

    public static native CdnNegRet reqNeg(int i, int i2, String str, int i3, String str2, String str3);

    CdnJni() {
    }
}
