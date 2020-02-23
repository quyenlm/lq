package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.SystemClock;
import com.tencent.component.debug.FileTracerConfig;

/* compiled from: BUGLY */
public final class aa implements Runnable {
    private final Handler a;
    private final String b;
    private long c;
    private final long d;
    private boolean e = true;
    private long f;

    aa(Handler handler, String str, long j) {
        this.a = handler;
        this.b = str;
        this.c = j;
        this.d = j;
    }

    public final void a() {
        if (!this.e) {
            x.d("scheduleCheckBlock fail as %s thread is blocked.", this.b);
            return;
        }
        this.e = false;
        this.f = SystemClock.uptimeMillis();
        this.a.postAtFrontOfQueue(this);
    }

    public final boolean b() {
        x.c("%s thread waitTime:%d", this.b, Long.valueOf(this.c));
        if (this.e || SystemClock.uptimeMillis() <= this.f + this.c) {
            return false;
        }
        return true;
    }

    public final int c() {
        if (this.e) {
            return 0;
        }
        if (SystemClock.uptimeMillis() - this.f < this.c) {
            return 1;
        }
        return 3;
    }

    public final Thread d() {
        return this.a.getLooper().getThread();
    }

    public final String e() {
        return this.b;
    }

    public final void run() {
        this.e = true;
        this.c = this.d;
    }

    public final void a(long j) {
        this.c = FileTracerConfig.FOREVER;
    }

    public final void f() {
        this.c = this.d;
    }
}
