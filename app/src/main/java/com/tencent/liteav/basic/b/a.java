package com.tencent.liteav.basic.b;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.tencent.liteav.basic.f.b;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: TXCVideoJitterBuffer */
public class a {
    private long A;
    /* access modifiers changed from: private */
    public long B;
    /* access modifiers changed from: private */
    public long C;
    /* access modifiers changed from: private */
    public long D;
    /* access modifiers changed from: private */
    public long E;
    /* access modifiers changed from: private */
    public long F;
    private ReadWriteLock G;
    /* access modifiers changed from: private */
    public b a;
    /* access modifiers changed from: private */
    public LinkedList<b> b;
    /* access modifiers changed from: private */
    public LinkedList<b> c;
    /* access modifiers changed from: private */
    public long d;
    private long e;
    private volatile boolean f;
    private volatile float g;
    private long h;
    private long i;
    private long j;
    private long k;
    private long l;
    private long m;
    private long n;
    private long o;
    /* access modifiers changed from: private */
    public boolean p;
    private HandlerThread q;
    private Handler r;
    /* access modifiers changed from: private */
    public boolean s;
    private long t;
    private long u;
    private volatile long v;
    private volatile long w;
    private int x;
    private int y;
    /* access modifiers changed from: private */
    public long z;

    static /* synthetic */ long i(a aVar) {
        long j2 = aVar.A + 1;
        aVar.A = j2;
        return j2;
    }

    static /* synthetic */ long k(a aVar) {
        long j2 = aVar.E + 1;
        aVar.E = j2;
        return j2;
    }

    public a() {
        this.a = null;
        this.b = new LinkedList<>();
        this.c = new LinkedList<>();
        this.d = 0;
        this.e = 15;
        this.f = false;
        this.g = 1.0f;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.o = 0;
        this.p = false;
        this.q = null;
        this.r = null;
        this.s = false;
        this.t = 20;
        this.u = 0;
        this.v = 0;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.B = 0;
        this.C = 0;
        this.D = 0;
        this.E = 0;
        this.F = 0;
        this.G = new ReentrantReadWriteLock();
        this.q = new HandlerThread("VideoJitterBufferHandler");
        this.q.start();
        this.G.writeLock().lock();
        this.r = new Handler(this.q.getLooper());
        this.G.writeLock().unlock();
    }

    public void a(final b bVar) {
        this.G.readLock().lock();
        if (this.r != null) {
            this.r.post(new Runnable() {
                public void run() {
                    b unused = a.this.a = bVar;
                }
            });
        }
        this.G.readLock().unlock();
    }

    public void a() {
        this.G.readLock().lock();
        if (this.r != null) {
            this.r.post(new Runnable() {
                public void run() {
                    boolean unused = a.this.s = true;
                    long unused2 = a.this.B = TXCTimeUtil.getTimeTick();
                }
            });
        }
        this.G.readLock().unlock();
        m();
    }

    public void b() {
        this.G.writeLock().lock();
        if (this.r != null) {
            this.r.post(new Runnable() {
                public void run() {
                    boolean unused = a.this.s = false;
                    a.this.l();
                    try {
                        Looper.myLooper().quit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        this.r = null;
        this.G.writeLock().unlock();
    }

    /* access modifiers changed from: private */
    public void l() {
        this.b.clear();
        this.d = 0;
        this.c.clear();
        this.e = 15;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.v = 0;
        this.w = 0;
        this.y = 0;
        this.x = 0;
        this.f = false;
        this.g = 1.0f;
        this.k = 0;
        this.o = 0;
        this.l = 0;
        this.m = 0;
        this.C = 0;
        this.D = 0;
        this.E = 0;
        this.F = 0;
        this.p = false;
    }

    public void a(boolean z2) {
        this.p = z2;
    }

    /* access modifiers changed from: private */
    public void m() {
        this.G.readLock().lock();
        if (this.r != null) {
            this.r.postDelayed(new Runnable() {
                public void run() {
                    while (a.this.b != null && !a.this.b.isEmpty() && !a.this.p) {
                        a.this.c();
                        b d = a.this.n();
                        if (d == null) {
                            break;
                        } else if (d != null && a.this.a != null) {
                            a.this.a.b(d);
                            while (!a.this.c.isEmpty()) {
                                b bVar = (b) a.this.c.getFirst();
                                if (bVar.g > d.h) {
                                    break;
                                }
                                a.this.a.c(bVar);
                                a.this.c.removeFirst();
                            }
                        }
                    }
                    long timeTick = TXCTimeUtil.getTimeTick();
                    if (timeTick > a.this.B + 200) {
                        long unused = a.this.z = a.this.z + a.this.d();
                        a.i(a.this);
                        long e = a.this.e();
                        long unused2 = a.this.D = a.this.D + e;
                        a.k(a.this);
                        if (a.this.E > 0) {
                            long unused3 = a.this.F = a.this.D / a.this.E;
                        }
                        if (e > a.this.C) {
                            long unused4 = a.this.C = e;
                        }
                        long unused5 = a.this.B = timeTick;
                    }
                    if (a.this.s) {
                        a.this.m();
                    }
                }
            }, this.t);
        }
        this.G.readLock().unlock();
    }

    public void a(final b bVar) {
        if (bVar != null) {
            if (bVar.g > this.w || bVar.g + 500 < this.w) {
                this.w = bVar.g;
            }
            if (this.v > this.w) {
                this.v = this.w;
            }
            if (bVar.b == 0) {
                this.x = this.y;
                this.y = 1;
            } else if (bVar.b == 2 || bVar.b == 1) {
                this.y++;
            }
            this.G.readLock().lock();
            if (this.r != null) {
                this.r.post(new Runnable() {
                    public void run() {
                        if (bVar.b == 6) {
                            a.this.c.add(bVar);
                            return;
                        }
                        a.this.b.add(bVar);
                        long unused = a.this.d = (long) a.this.b.size();
                        if (a.this.a != null) {
                            long unused2 = a.this.d = a.this.d + ((long) a.this.a.o());
                        }
                        a.this.e(bVar.h);
                    }
                });
            }
            this.G.readLock().unlock();
        }
    }

    /* access modifiers changed from: private */
    public b n() {
        long j2;
        boolean z2;
        if (this.b == null || this.b.isEmpty()) {
            return null;
        }
        if (this.v > this.w) {
            this.v = this.w;
        }
        long timeTick = TXCTimeUtil.getTimeTick();
        if (this.k == 0) {
            z2 = true;
        } else {
            b first = this.b.getFirst();
            if (first.h > this.k) {
                j2 = b(first.h - this.k);
            } else {
                long b2 = b(0);
                TXCLog.w("TXCVideoJitterBuffer", "videojitter pull nal with invalid ts, current dts [" + first.h + "] < last dts[ " + this.k + "]!!! decInterval is " + b2);
                j2 = b2;
            }
            if (this.o + j2 <= this.u + timeTick) {
                this.u = (this.u + timeTick) - (this.o + j2);
                if (this.u > j2) {
                    this.u = j2;
                }
                z2 = true;
            } else {
                z2 = false;
            }
        }
        if (!z2) {
            return null;
        }
        b o2 = o();
        f(o2.h);
        this.o = timeTick;
        return o2;
    }

    private long b(long j2) {
        long j3;
        float c2;
        long j4 = 500;
        if (j2 > 500) {
            j2 = 500;
        }
        if (j2 > 0) {
            if (this.a != null) {
                j3 = this.a.n();
            } else {
                j3 = 0;
            }
            if (j3 > 0) {
                j4 = 50;
            } else if (this.f) {
                j4 = 200;
            }
            long a2 = a(j2, j4);
            if (this.f) {
                c2 = d(a2);
            } else {
                c2 = c(a2);
            }
            return (long) (((float) j2) / c2);
        } else if (this.e > 0) {
            return 1000 / this.e;
        } else {
            return 0;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000f, code lost:
        if (r6 < r0) goto L_0x0011;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long a(long r6, long r8) {
        /*
            r5 = this;
            long r0 = r5.e
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x0017
            r0 = 1000(0x3e8, double:4.94E-321)
            long r2 = r5.e
            long r0 = r0 / r2
            int r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x0017
        L_0x0011:
            int r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x0016
            r8 = r0
        L_0x0016:
            return r8
        L_0x0017:
            r0 = r6
            goto L_0x0011
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.b.a.a(long, long):long");
    }

    private float c(long j2) {
        long j3;
        long j4;
        float f2;
        long j5 = 0;
        int i2 = 0;
        if (this.a != null) {
            i2 = this.a.o();
        }
        if (i2 > 24) {
            TXCLog.e("TXCVideoJitterBuffer", "videojitter pull nal with speed : " + 0.1f);
            return 0.1f;
        }
        if (this.a != null) {
            j3 = this.a.n();
        } else {
            j3 = 0;
        }
        if (j3 <= 0) {
            if (this.w > this.v) {
                j4 = this.w - this.v;
            } else {
                j4 = 0;
            }
            long j6 = (long) (this.g * 1000.0f);
            if (this.a != null) {
                j5 = this.n * ((long) this.a.o());
            }
            if (j5 > j6) {
                j6 = j5;
            }
            if (j4 > j6 + j2) {
                f2 = 1.1f;
            } else {
                f2 = 1.0f;
            }
            if (j4 <= j6) {
                return 1.0f;
            }
            return f2;
        } else if (j3 >= this.v + j2) {
            if (j3 >= this.v + j2 + 200) {
                return 2.2f;
            }
            return 1.2f;
        } else if (this.v < j3 + j2) {
            return 1.0f;
        } else {
            if (this.v >= j3 + j2 + 200) {
                return 0.5f;
            }
            return 0.9f;
        }
    }

    private float d(long j2) {
        long j3;
        long j4;
        float f2;
        long j5 = 0;
        int i2 = 0;
        if (this.a != null) {
            i2 = this.a.o();
        }
        if (i2 > 24) {
            TXCLog.e("TXCVideoJitterBuffer", "videojitter pull nal with speed : " + 0.1f);
            return 0.1f;
        }
        if (this.a != null) {
            j3 = this.a.n();
        } else {
            j3 = 0;
        }
        if (j3 <= 0) {
            if (this.w > this.v) {
                j4 = this.w - this.v;
            } else {
                j4 = 0;
            }
            long j6 = (long) (this.g * 1000.0f);
            if (this.a != null) {
                j5 = this.n * ((long) this.a.o());
            }
            if (j5 > j6) {
                j6 = j5;
            }
            if (j4 > j6 + j2) {
                f2 = 1.2f;
            } else {
                f2 = 1.0f;
            }
            if (j4 <= j6) {
                return 1.0f;
            }
            return f2;
        } else if (j3 >= this.v + j2) {
            if (j3 >= this.v + j2 + 200) {
                return 2.2f;
            }
            return 1.5f;
        } else if (this.v < j3 + j2) {
            return 1.0f;
        } else {
            if (this.v >= j3 + j2 + 200) {
                return 0.5f;
            }
            return 0.7f;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c() {
        /*
            r8 = this;
            r1 = 0
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.b
            int r0 = r0.size()
            if (r0 != 0) goto L_0x000a
        L_0x0009:
            return
        L_0x000a:
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.b
            java.lang.Object r0 = r0.getFirst()
            com.tencent.liteav.basic.f.b r0 = (com.tencent.liteav.basic.f.b) r0
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.b
            java.lang.Object r0 = r0.getLast()
            com.tencent.liteav.basic.f.b r0 = (com.tencent.liteav.basic.f.b) r0
            com.tencent.liteav.basic.b.b r0 = r8.a
            if (r0 == 0) goto L_0x00b8
            com.tencent.liteav.basic.b.b r0 = r8.a
            int r0 = r0.o()
        L_0x0024:
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r2 = r8.b
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x0009
            r2 = 24
            if (r0 < r2) goto L_0x0009
            r2 = r1
            r3 = r1
        L_0x0032:
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.b
            int r0 = r0.size()
            if (r2 >= r0) goto L_0x004b
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.b
            java.lang.Object r0 = r0.get(r2)
            com.tencent.liteav.basic.f.b r0 = (com.tencent.liteav.basic.f.b) r0
            int r0 = r0.b
            if (r0 != 0) goto L_0x0047
            r3 = r2
        L_0x0047:
            int r0 = r2 + 1
            r2 = r0
            goto L_0x0032
        L_0x004b:
            r2 = r1
        L_0x004c:
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.b
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0096
            if (r2 >= r3) goto L_0x0096
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.b
            java.lang.Object r0 = r0.getFirst()
            com.tencent.liteav.basic.f.b r0 = (com.tencent.liteav.basic.f.b) r0
            long r0 = r0.h
            r8.k = r0
        L_0x0062:
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.c
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0082
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.c
            java.lang.Object r0 = r0.getFirst()
            com.tencent.liteav.basic.f.b r0 = (com.tencent.liteav.basic.f.b) r0
            long r4 = r0.g
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r1 = r8.b
            java.lang.Object r1 = r1.getFirst()
            com.tencent.liteav.basic.f.b r1 = (com.tencent.liteav.basic.f.b) r1
            long r6 = r1.h
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 <= 0) goto L_0x008b
        L_0x0082:
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.b
            r0.removeFirst()
            int r1 = r2 + 1
            r2 = r1
            goto L_0x004c
        L_0x008b:
            com.tencent.liteav.basic.b.b r1 = r8.a
            r1.c(r0)
            java.util.LinkedList<com.tencent.liteav.basic.f.b> r0 = r8.c
            r0.removeFirst()
            goto L_0x0062
        L_0x0096:
            if (r2 <= 0) goto L_0x0009
            java.lang.String r0 = "TXCVideoJitterBuffer"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "videojitter cache too maney ， so drop "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = " frames"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.tencent.liteav.basic.log.TXCLog.w(r0, r1)
            goto L_0x0009
        L_0x00b8:
            r0 = r1
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.b.a.c():void");
    }

    private b o() {
        b bVar = null;
        if (!this.b.isEmpty()) {
            bVar = this.b.getFirst();
            this.b.removeFirst();
            this.d = (long) this.b.size();
            if (this.a != null) {
                this.d += (long) this.a.o();
            }
        }
        return bVar;
    }

    /* access modifiers changed from: private */
    public void e(long j2) {
        if (this.j != 0) {
            if (this.i >= 5) {
                this.e = this.h / this.i;
                if (this.e > 200) {
                    this.e = 200;
                } else if (this.e < 1) {
                    this.e = 1;
                }
                if (this.e >= 30 && this.t != 5) {
                    this.t = 5;
                }
                this.h = 0;
                this.i = 0;
            } else {
                long j3 = j2 - this.j;
                if (j3 > 0) {
                    this.h = (1000 / j3) + this.h;
                    this.i++;
                }
            }
        }
        this.j = j2;
    }

    private void f(long j2) {
        long j3;
        if (this.k != 0) {
            if (j2 > this.k) {
                j3 = j2 - this.k;
                if (j3 > 500) {
                    j3 = 500;
                }
            } else if (this.e > 0) {
                j3 = 1000 / this.e;
            } else {
                j3 = 0;
            }
            this.l = j3 + this.l;
            this.m++;
            if (this.m >= 5) {
                this.n = this.l / this.m;
                if (this.n > 500) {
                    this.n = 500;
                } else if (this.n < 5) {
                    this.n = 5;
                }
                this.l = 0;
                this.m = 0;
            }
        }
        this.k = j2;
    }

    public void b(boolean z2) {
        this.f = z2;
    }

    public void a(float f2) {
        this.g = f2;
    }

    public void a(long j2) {
        this.v = j2;
        if (this.v > this.w) {
            this.v = this.w;
        }
    }

    public long d() {
        return this.w - this.v;
    }

    public long e() {
        return this.d;
    }

    public long f() {
        return this.v;
    }

    public long g() {
        return this.w;
    }

    public int h() {
        return this.x;
    }

    public long i() {
        return this.C;
    }

    public long j() {
        return this.F;
    }

    public long k() {
        long j2;
        if (this.A != 0) {
            j2 = this.z / this.A;
        } else {
            j2 = 0;
        }
        this.A = 0;
        this.z = 0;
        return j2;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        try {
            b();
        } catch (Exception e2) {
        }
    }
}
