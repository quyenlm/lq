package com.tencent.tp.a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;
import com.tencent.smtt.sdk.TbsListener;

public class ad {
    public static int a(int i, int i2, int i3, int i4) {
        return ((i4 & 255) << 24) | ((i & 255) << 16) | ((i2 & 255) << 8) | (i3 & 255);
    }

    public static Drawable a() {
        return n.a(p.a);
    }

    public static Drawable a(Context context) {
        return n.a(n.a(r.a), n.a(u.a));
    }

    public static Drawable b() {
        return n.a(q.a);
    }

    public static Drawable b(Context context) {
        return n.a(n.a(s.a), n.a(t.a));
    }

    public static Drawable c() {
        return n.a(v.a);
    }

    public static Drawable c(Context context) {
        return n.a(w.a);
    }

    public static int d() {
        return a(124, 124, 124, 255);
    }

    public static Drawable d(Context context) {
        return n.a(x.a);
    }

    public static int e() {
        return a(255, 255, 255, 255);
    }

    public static Drawable e(Context context) {
        return n.a(y.a);
    }

    public static int f() {
        return a(49, 148, TbsListener.ErrorCode.INCR_UPDATE_ERROR, 255);
    }

    public static int g() {
        return a(64, DownloaderService.STATUS_PAUSED_BY_APP, 68, 255);
    }

    public static int h() {
        return a(20, 146, TbsListener.ErrorCode.EXCEED_LZMA_RETRY_NUM, 255);
    }
}
