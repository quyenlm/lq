package com.tencent.mna.b.a;

import com.beetalk.sdk.ShareConstants;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.c;
import com.tencent.mna.base.utils.h;
import com.tencent.smtt.sdk.TbsListener;

/* compiled from: TosManager */
public class i {
    private static volatile boolean a = false;

    public static int a(int i, boolean z) {
        if (!z) {
            return -2;
        }
        if (i > 7) {
            try {
                a = c.a();
                if (!a) {
                    return 10;
                }
            } catch (Exception e) {
                h.a("turnTos exception:" + e.getMessage());
                return -3;
            }
        }
        int b = b(i, true);
        if (b == 0) {
            return -1;
        }
        e.g(b);
        h.a("turnTos: 0x" + Integer.toHexString(b));
        return 0;
    }

    public static int a(int i) {
        return b(i, a);
    }

    private static int b(int i, boolean z) {
        int i2 = ShareConstants.ERROR_CODE.GG_RESULT_CANCELLED;
        switch (i) {
            case 5:
                return ShareConstants.ERROR_CODE.GG_RESULT_CANCELLED;
            case 6:
                return DownloaderService.STATUS_RUNNING;
            case 7:
                return TbsListener.ErrorCode.EXCEED_INCR_UPDATE;
            case 50:
                if (!z) {
                    i2 = 0;
                }
                return i2;
            case 60:
                if (z) {
                    return DownloaderService.STATUS_RUNNING;
                }
                return 0;
            case 70:
                if (z) {
                    return TbsListener.ErrorCode.EXCEED_INCR_UPDATE;
                }
                return 0;
            default:
                return 0;
        }
    }

    public static void a() {
        a = false;
    }
}
