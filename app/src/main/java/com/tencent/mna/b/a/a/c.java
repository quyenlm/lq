package com.tencent.mna.b.a.a;

import android.content.Context;
import com.tencent.imsdk.framework.consts.InnerErrorCode;
import com.tencent.mna.b.a.c.b;
import com.tencent.mna.b.a.c.f;
import com.tencent.mna.b.a.d;
import com.tencent.mna.b.a.g;
import com.tencent.mna.base.c.a;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.i;
import com.tencent.mna.base.utils.k;
import com.tencent.mna.base.utils.l;
import com.tencent.mna.base.utils.q;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: ScheduledWorkerTask */
public class c implements Runnable {
    private static final ReentrantLock a = new ReentrantLock();
    private d b;
    private a c;
    private a d;
    private f e;
    private b f;
    private int g = 0;
    private int h = InnerErrorCode.DEFAULT_ERROR;
    private int i = -1;

    public c(d dVar, a aVar) {
        this.b = dVar;
        this.c = aVar;
        this.d = new b(com.tencent.mna.base.a.a.h(), com.tencent.mna.base.a.a.g(), com.tencent.mna.base.a.a.i());
        this.e = this.c.b();
        this.f = this.c.c();
    }

    public void run() {
        boolean z;
        int i2;
        int i3;
        int i4;
        int a2;
        int au;
        int at;
        int av;
        int e2;
        int c2;
        int i5;
        int i6;
        int i7 = 0;
        a.lock();
        try {
            Thread.currentThread().setName("mna-scheduled-worker");
            if (com.tencent.mna.b.a.b.i()) {
                if (this.b == null) {
                    h.d("Scheduled Work failed, testerFacade is null");
                    return;
                }
                boolean z2 = com.tencent.mna.base.a.a.x() == 1 || com.tencent.mna.base.a.a.x() == 2;
                if (com.tencent.mna.base.a.a.x() == 1 || com.tencent.mna.base.a.a.x() == 3) {
                    z = true;
                } else {
                    z = false;
                }
                boolean d2 = com.tencent.mna.b.a.b.d();
                if (d2) {
                    if (z2) {
                        if (com.tencent.mna.a.b.i) {
                            i5 = this.b.c(this.h);
                        } else {
                            i5 = this.b.b();
                        }
                        String str = "[N]转发测速: " + i5;
                        h.b(str);
                        h.e(str);
                    } else {
                        i5 = 0;
                    }
                    if (!z || com.tencent.mna.a.b.i) {
                        i6 = 0;
                    } else {
                        i6 = this.b.a();
                        String str2 = "[N]直连测速: " + i6;
                        h.b(str2);
                        h.e(str2);
                    }
                    if (this.e != null) {
                        this.e.a(i5, i6);
                    }
                    i3 = i5;
                } else {
                    if (z) {
                        if (com.tencent.mna.a.b.i) {
                            i2 = this.b.b(this.h);
                        } else {
                            i2 = this.b.a();
                        }
                        String str3 = "[N]直连测速: " + i2;
                        h.b(str3);
                        h.e(str3);
                    } else {
                        i2 = 0;
                    }
                    if (this.e != null) {
                        this.e.a(i2);
                    }
                    i3 = i2;
                }
                Context g2 = com.tencent.mna.b.g();
                int a3 = k.a(g2);
                if (this.h == -10000 || this.d == null || !this.d.a(i3, this.h)) {
                    i4 = 0;
                } else {
                    i4 = a(d2, i3, a3);
                }
                this.h = i3;
                this.i = a3;
                com.tencent.mna.b.b.b m = com.tencent.mna.b.a.b.m();
                if (com.tencent.mna.base.a.a.aD() && m != null && com.tencent.mna.base.a.a.as() && a3 == 4 && this.g < com.tencent.mna.base.a.a.aw()) {
                    int j = com.tencent.mna.b.a.b.j();
                    if (j == 1) {
                        a2 = q.a(g2);
                        au = com.tencent.mna.base.a.a.ay();
                        at = com.tencent.mna.base.a.a.ax();
                        av = com.tencent.mna.base.a.a.az();
                    } else {
                        a2 = i.a();
                        au = com.tencent.mna.base.a.a.au();
                        at = com.tencent.mna.base.a.a.at();
                        av = com.tencent.mna.base.a.a.av();
                    }
                    if (i3 > at && a2 > au) {
                        if (i4 <= 0) {
                            if (d2) {
                                i4 = z2 ? this.b.b() : 0;
                            } else {
                                i4 = z ? this.b.a() : 0;
                            }
                        }
                        if (i4 > at) {
                            if (d2) {
                                e2 = z2 ? this.b.f() : 0;
                            } else {
                                e2 = z ? this.b.e() : 0;
                            }
                            if (e2 > 0 && e2 <= av) {
                                long currentTimeMillis = System.currentTimeMillis();
                                if (j == 1) {
                                    c2 = com.tencent.mna.b.a.b.c(false);
                                    h.b("[N]网络绑定到Wifi, 结果为: " + c2);
                                } else {
                                    this.g++;
                                    c2 = com.tencent.mna.b.a.b.c(true);
                                    h.b("[N]网络绑定到4G, 结果为: " + c2);
                                }
                                StringBuilder sb = new StringBuilder();
                                sb.append(currentTimeMillis).append('_').append(-1).append('_').append(-1).append('_').append(c2);
                                this.c.a(a.C0030a.W2M_FLAG, sb.toString());
                            }
                        }
                    }
                }
                if (d2 && com.tencent.mna.base.a.a.s() && i3 > com.tencent.mna.base.a.a.t() && z2) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(i3);
                    while (i7 < 2) {
                        int b2 = this.b.b();
                        sb2.append(", ").append(b2);
                        if (b2 <= com.tencent.mna.base.a.a.t()) {
                            break;
                        }
                        i7++;
                    }
                    if (i7 == 2) {
                        h.b("[N]转发中断，原因：" + 3 + "次转发时延均大于" + com.tencent.mna.base.a.a.t() + "，即(" + sb2.toString() + com.tencent.tp.a.h.b);
                        com.tencent.mna.b.a.b.a(false);
                    }
                }
                a();
            }
            a.unlock();
        } catch (Exception e3) {
            h.a("ScheduledWorker exception:" + e3.getMessage());
        } catch (Throwable th) {
            h.a("ScheduledWorker throwable:" + th.getMessage());
        } finally {
            a.unlock();
        }
    }

    private int a(boolean z, int i2, int i3) {
        int i4;
        int i5;
        d.b bVar = d.b.STAGE_DIRECT_CHOSEN;
        if (z) {
            bVar = d.b.STAGE_FORWARD_CHOSEN;
            i4 = i2;
            i5 = 0;
        } else {
            i4 = 0;
            i5 = i2;
        }
        int a2 = this.b.a(this.c, bVar, i4, i5, i3, this.h, this.i);
        g.a(a2, i.a(), bVar);
        return a2;
    }

    private void a() {
        long[] b2;
        long[] jArr;
        if (this.f == null) {
            h.d("recordCpuMemGpu: sCpuMemGpusInfo is null");
            return;
        }
        if (com.tencent.mna.base.a.a.E() != 0) {
            if (this.f.a()) {
                long[] a2 = l.a();
                b2 = l.b();
                jArr = a2;
            } else {
                long[] a3 = l.a(this.f.a, this.f.b);
                b2 = l.b(this.f.c, this.f.d);
                jArr = a3;
            }
            if (jArr == null || jArr.length < 3 || b2 == null || b2.length < 3) {
                h.d("recordCpuMemGpu: get cpu usage error");
            } else {
                this.f.a((int) jArr[0], jArr[1], jArr[2], (int) b2[0], b2[1], b2[2]);
            }
        }
        if (com.tencent.mna.base.a.a.F() != 0) {
            Context g2 = com.tencent.mna.b.g();
            this.f.a(l.a(g2), l.b(g2));
        }
        if (com.tencent.mna.base.a.a.G() != 0) {
            this.f.a(l.c());
        }
    }
}
