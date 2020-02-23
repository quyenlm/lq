package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.utils.TbsLog;
import java.util.Arrays;

public class TbsCoreLoadStat {
    private static TbsCoreLoadStat d = null;
    public static volatile int mLoadErrorCode = -1;
    private TbsSequenceQueue a = null;
    private boolean b = false;
    private final int c = 3;

    public class TbsSequenceQueue {
        private int b;
        private int c;
        private int[] d;
        private int e;
        private int f;

        public TbsSequenceQueue() {
            this.b = 10;
            this.e = 0;
            this.f = 0;
            this.c = this.b;
            this.d = new int[this.c];
        }

        public TbsSequenceQueue(int i, int i2) {
            this.b = 10;
            this.e = 0;
            this.f = 0;
            this.c = i2;
            this.d = new int[this.c];
            this.d[0] = i;
            this.f++;
        }

        public void add(int i) {
            if (this.f > this.c - 1) {
                throw new IndexOutOfBoundsException("sequeue is full");
            }
            int[] iArr = this.d;
            int i2 = this.f;
            this.f = i2 + 1;
            iArr[i2] = i;
        }

        public void clear() {
            Arrays.fill(this.d, 0);
            this.e = 0;
            this.f = 0;
        }

        public int element() {
            if (!empty()) {
                return this.d[this.e];
            }
            throw new IndexOutOfBoundsException("sequeue is null");
        }

        public boolean empty() {
            return this.f == this.e;
        }

        public int length() {
            return this.f - this.e;
        }

        public int remove() {
            if (empty()) {
                throw new IndexOutOfBoundsException("sequeue is null");
            }
            int i = this.d[this.e];
            int[] iArr = this.d;
            int i2 = this.e;
            this.e = i2 + 1;
            iArr[i2] = 0;
            return i;
        }

        public String toString() {
            if (empty()) {
                return "";
            }
            StringBuilder sb = new StringBuilder("[");
            for (int i = this.e; i < this.f; i++) {
                sb.append(String.valueOf(this.d[i]) + ",");
            }
            int length = sb.length();
            return sb.delete(length - 1, length).append("]").toString();
        }
    }

    private TbsCoreLoadStat() {
    }

    public static TbsCoreLoadStat getInstance() {
        if (d == null) {
            d = new TbsCoreLoadStat();
        }
        return d;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (this.a != null) {
            this.a.clear();
        }
        this.b = false;
    }

    /* access modifiers changed from: package-private */
    public void a(Context context, int i) {
        a(context, i, (Throwable) null);
        TbsLog.e(TbsListener.tag_load_error, "" + i);
    }

    /* access modifiers changed from: package-private */
    public synchronized void a(Context context, int i, Throwable th) {
        if (mLoadErrorCode == -1) {
            mLoadErrorCode = i;
            TbsLog.addLog(TbsLog.TBSLOG_CODE_SDK_LOAD_ERROR, "code=%d,desc=%s", Integer.valueOf(i), String.valueOf(th));
            if (th != null) {
                TbsLogReport.a(context).b(i, th);
            } else {
                TbsLog.e("TbsCoreLoadStat", "setLoadErrorCode :: error is null with errorCode: " + i + "; Check & correct it!");
            }
        } else {
            TbsLog.w("TbsCoreLoadStat", "setLoadErrorCode :: error(" + mLoadErrorCode + ") was already reported; " + i + " is duplicated. Try to remove it!");
        }
    }
}
