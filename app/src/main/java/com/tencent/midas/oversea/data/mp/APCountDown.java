package com.tencent.midas.oversea.data.mp;

import android.os.CountDownTimer;
import com.tencent.smtt.sdk.TbsDownloadConfig;

public class APCountDown {
    private static APCountDown a;
    private static CountDownTimer b;
    /* access modifiers changed from: private */
    public long c = -1;
    /* access modifiers changed from: private */
    public CallBack d;

    public interface CallBack {
        void finished();

        void update();
    }

    private APCountDown() {
    }

    static /* synthetic */ long b(APCountDown aPCountDown) {
        long j = aPCountDown.c;
        aPCountDown.c = j - 1;
        return j;
    }

    public static APCountDown getInstance() {
        if (a == null) {
            a = new APCountDown();
        }
        return a;
    }

    public static void release() {
        if (b != null) {
            b.cancel();
            b = null;
        }
        a = null;
    }

    public String getCount() {
        return this.c + "";
    }

    public String getCountDownText() {
        long j = this.c;
        long j2 = j / TbsDownloadConfig.DEFAULT_RETRY_INTERVAL_SEC;
        long j3 = (j % TbsDownloadConfig.DEFAULT_RETRY_INTERVAL_SEC) / 3600;
        long j4 = (j % 3600) / 60;
        long j5 = j % 60;
        if (((double) j2) >= 1.0d) {
            return "还剩" + j2 + "天" + String.format("%02d", new Object[]{Long.valueOf(j3)}) + "小时";
        } else if (this.c <= 120) {
            return "即将结束";
        } else {
            return "还剩" + String.format("%02d", new Object[]{Long.valueOf(j3)}) + ":" + String.format("%02d", new Object[]{Long.valueOf(j4)}) + ":" + String.format("%02d", new Object[]{Long.valueOf(j5)});
        }
    }

    public void init(long j, long j2) {
        if (this.c == -1) {
            this.c = j / 1000;
            b = new a(this, j, j2);
            b.start();
        }
    }

    public void setCallback(CallBack callBack) {
        this.d = callBack;
    }
}
