package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import com.unity3d.player.n;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class o {
    /* access modifiers changed from: private */
    public UnityPlayer a = null;
    /* access modifiers changed from: private */
    public Context b = null;
    private a c;
    /* access modifiers changed from: private */
    public final Semaphore d = new Semaphore(0);
    /* access modifiers changed from: private */
    public final Lock e = new ReentrantLock();
    /* access modifiers changed from: private */
    public n f = null;
    /* access modifiers changed from: private */
    public int g = 2;
    private boolean h = false;
    /* access modifiers changed from: private */
    public boolean i = false;

    public interface a {
        void a();
    }

    o(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    /* access modifiers changed from: private */
    public void d() {
        if (this.f != null) {
            this.a.removeViewFromPlayer(this.f);
            this.i = false;
            this.f.destroyPlayer();
            this.f = null;
            if (this.c != null) {
                this.c.a();
            }
        }
    }

    public final void a() {
        this.e.lock();
        if (this.f != null) {
            if (this.g == 0) {
                this.f.CancelOnPrepare();
            } else if (this.i) {
                this.h = this.f.a();
                if (!this.h) {
                    this.f.pause();
                }
            }
        }
        this.e.unlock();
    }

    public final boolean a(Context context, String str, int i2, int i3, int i4, boolean z, long j, long j2, a aVar) {
        this.e.lock();
        this.c = aVar;
        this.b = context;
        this.d.drainPermits();
        this.g = 2;
        final String str2 = str;
        final int i5 = i2;
        final int i6 = i3;
        final int i7 = i4;
        final boolean z2 = z;
        final long j3 = j;
        final long j4 = j2;
        runOnUiThread(new Runnable() {
            public final void run() {
                if (o.this.f != null) {
                    e.Log(5, "Video already playing");
                    int unused = o.this.g = 2;
                    o.this.d.release();
                    return;
                }
                n unused2 = o.this.f = new n(o.this.b, str2, i5, i6, i7, z2, j3, j4, new n.a() {
                    public final void a(int i) {
                        o.this.e.lock();
                        int unused = o.this.g = i;
                        if (i == 3 && o.this.i) {
                            o.this.runOnUiThread(new Runnable() {
                                public final void run() {
                                    o.this.d();
                                    o.this.a.resume();
                                }
                            });
                        }
                        if (i != 0) {
                            o.this.d.release();
                        }
                        o.this.e.unlock();
                    }
                });
                if (o.this.f != null) {
                    o.this.a.addView(o.this.f);
                }
            }
        });
        boolean z3 = false;
        try {
            this.e.unlock();
            this.d.acquire();
            this.e.lock();
            z3 = this.g != 2;
        } catch (InterruptedException e2) {
        }
        runOnUiThread(new Runnable() {
            public final void run() {
                o.this.a.pause();
            }
        });
        if (!z3 || this.g == 3) {
            runOnUiThread(new Runnable() {
                public final void run() {
                    o.this.d();
                    o.this.a.resume();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                public final void run() {
                    if (o.this.f != null) {
                        o.this.a.addViewToPlayer(o.this.f, true);
                        boolean unused = o.this.i = true;
                        o.this.f.requestFocus();
                    }
                }
            });
        }
        this.e.unlock();
        return z3;
    }

    public final void b() {
        this.e.lock();
        if (this.f != null && this.i && !this.h) {
            this.f.start();
        }
        this.e.unlock();
    }

    public final void c() {
        this.e.lock();
        if (this.f != null) {
            this.f.updateVideoLayout();
        }
        this.e.unlock();
    }

    /* access modifiers changed from: protected */
    public final void runOnUiThread(Runnable runnable) {
        if (this.b instanceof Activity) {
            ((Activity) this.b).runOnUiThread(runnable);
        } else {
            e.Log(5, "Not running from an Activity; Ignoring execution request...");
        }
    }
}
