package com.tencent.mna.base.jni;

import com.tencent.mna.base.jni.entity.CdnMasterRet;
import com.tencent.mna.base.jni.entity.CdnNegRet;

/* compiled from: CdnJniWrapper */
public class a {
    public static CdnMasterRet a(String str, int i, String str2, int i2, String str3, boolean z) {
        try {
            return CdnJni.reqMaster(str, i, str2, i2, str3, z);
        } catch (Throwable th) {
            return null;
        }
    }

    public static CdnNegRet a(int i, int i2, String str, int i3, String str2, String str3) {
        try {
            return CdnJni.reqNeg(i, i2, str, i3, str2, str3);
        } catch (Throwable th) {
            return null;
        }
    }

    public static long a() {
        try {
            return CdnJni.getCdnSendToPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long b() {
        try {
            return CdnJni.getCdnRecvFromPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long c() {
        try {
            return CdnJni.getCdnSendMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long d() {
        try {
            return CdnJni.getCdnRecvMsgPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long e() {
        try {
            return CdnJni.getCdnConnectPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long f() {
        try {
            return CdnJni.getCdnSendPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static long g() {
        try {
            return CdnJni.getCdnRecvPtr();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int h() {
        try {
            return CdnJni.endCdnSpeed();
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5) {
        try {
            return CdnJni.getForwardDelay(i, i2, i3, i4, i5);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(int i, int i2, int i3, int i4, int i5, String str) {
        try {
            return CdnJni.getMatchForwardDelay(i, i2, i3, i4, i5, str);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int a(int i, int i2, int i3) {
        try {
            return CdnJni.getExportDelay(i, i2, i3);
        } catch (Throwable th) {
            return 501;
        }
    }

    public static int b(int i, int i2, int i3) {
        try {
            return CdnJni.getV6ExportDelay(i, i2, i3);
        } catch (Throwable th) {
            return 501;
        }
    }
}
