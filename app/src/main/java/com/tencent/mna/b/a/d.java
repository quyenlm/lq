package com.tencent.mna.b.a;

import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.json.JSONObject;

/* compiled from: AccelerateTesterFacade */
public class d {
    private final AtomicInteger a = new AtomicInteger(200000);
    private final AtomicInteger b = new AtomicInteger(0);
    private int c = 0;
    private final ReentrantReadWriteLock d = new ReentrantReadWriteLock();
    private com.tencent.mna.b.b.b e = null;
    private com.tencent.mna.base.d.a f;
    private com.tencent.mna.base.d.a g;
    private h h;
    private boolean i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private a t;

    /* compiled from: AccelerateTesterFacade */
    public enum b {
        STAGE_CONTINUOUS_SPEED_TEST(0, "启动阶段已连续测速"),
        STAGE_FORWARD_CHOSEN(1, "选择转发"),
        STAGE_DIRECT_CHOSEN(2, "选择直连"),
        STAGE_NOT_CONTINUOUS_SPEED_TEST(3, "启动阶段未连续测速");
        
        private String desc;
        private int flag;

        private b(int i, String str) {
            this.desc = "";
            this.flag = 0;
            this.flag = i;
            this.desc = str;
        }

        public int getFlag() {
            return this.flag;
        }

        public String getDesc() {
            return this.desc;
        }

        public boolean isInPvp() {
            return this == STAGE_FORWARD_CHOSEN || this == STAGE_DIRECT_CHOSEN;
        }
    }

    d(int i2, String str, int i3, int i4) {
        this.n = i2 <= 0 ? 300 : i2;
        this.o = i4 <= 0 ? 500 : i4;
        int b2 = e.b(this.n);
        this.l = f.i(str);
        this.m = i3;
        this.h = null;
        this.f = new com.tencent.mna.base.d.a(b2, str, this.m);
        this.i = false;
        this.c = 0;
        this.t = new a();
        h.a("AccFacade:::speedIp = [" + str + ":" + i3 + "], fdTimeout = [" + this.n + "], speedTestTimeout = [" + this.o + "], directFd = [" + b2 + "]");
    }

    public void a(h hVar) {
        this.d.writeLock().lock();
        if (hVar == null) {
            try {
                h.c("AccFacade:::setSpeedTester failed, speedTester is null");
            } finally {
                this.d.writeLock().unlock();
            }
        } else {
            this.j = e.a(this.n);
            this.k = e.a(this.n);
            this.h = hVar;
            h.a("AccFacade:::setSpeedTester succeed, mForwardFd = [" + this.j + "], mEdgeFd = [" + this.k + "]");
            this.d.writeLock().unlock();
        }
    }

    public void a(int i2) {
        this.d.writeLock().lock();
        try {
            if (this.f != null) {
                this.f.b(i2);
            }
            if (this.g != null) {
                this.g.b(i2);
            }
            e.b(this.j, i2);
            e.b(this.k, i2);
            e.b(this.p, i2);
            e.b(this.q, i2);
        } finally {
            this.d.writeLock().unlock();
        }
    }

    public void a(com.tencent.mna.b.b.b bVar, boolean z) {
        this.d.writeLock().lock();
        if (bVar == null) {
            try {
                h.c("AccFacade:::setMobileSpeedTest failed, networkBinding is null");
            } catch (Exception e2) {
                h.a("AccFacade:::setMobileSpeedTest fail, exception:" + e2.getMessage());
            } finally {
                this.d.writeLock().unlock();
            }
        } else {
            this.e = bVar;
            this.p = e.a(this.n);
            this.q = e.a(this.n);
            bVar.a(this.p);
            bVar.a(this.q);
            this.r = z ? f.i(bVar.a()) : this.l;
            this.s = z ? bVar.b() : this.m;
            int b2 = e.b(this.n);
            this.g = new com.tencent.mna.base.d.a(b2, bVar.a(), this.s, this.e);
            h.a("AccFacade:::setMobileSpeedTest succeed, mOtherNetIp = [" + bVar.a() + ":" + this.s + "], otherNetFd = [" + b2 + "], mOtherNetForwardFd = [" + this.p + "], mOtherNetEdgeFd = [" + this.q + "]");
            this.d.writeLock().unlock();
        }
    }

    public int a() {
        this.d.readLock().lock();
        try {
            if (this.f == null) {
                h.d("directSpeedTester is null");
                return -3;
            }
            int a2 = this.f.a(this.o);
            this.d.readLock().unlock();
            return a2;
        } finally {
            this.d.readLock().unlock();
        }
    }

    public int b(int i2) {
        this.d.readLock().lock();
        try {
            if (this.f == null) {
                h.d("AccFacade:::directSpeedTester is null");
                return -3;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("lastdelay", i2).put("appid", com.tencent.mna.a.b.d).put("openid", com.tencent.mna.a.b.f).put("pvpid", com.tencent.mna.a.b.a);
                int a2 = this.f.a(this.o, jSONObject.toString());
                this.d.readLock().unlock();
                return a2;
            } catch (Exception e2) {
                this.d.readLock().unlock();
                return -1;
            }
        } finally {
            this.d.readLock().unlock();
        }
    }

    public int b() {
        this.d.readLock().lock();
        try {
            if (this.h == null) {
                h.d("AccFacade:::speedTester is null");
                return -3;
            }
            int a2 = this.h.a(this.j, this.l, this.m, this.a.incrementAndGet(), this.o);
            this.d.readLock().unlock();
            return a2;
        } finally {
            this.d.readLock().unlock();
        }
    }

    public int c(int i2) {
        this.d.readLock().lock();
        try {
            if (this.h == null) {
                h.d("AccFacade:::speedTester is null");
                return -3;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("lastdelay", i2).put("appid", com.tencent.mna.a.b.d).put("openid", com.tencent.mna.a.b.f).put("pvpid", com.tencent.mna.a.b.a);
                int a2 = this.h.a(this.j, this.l, this.m, this.b.incrementAndGet(), this.o, jSONObject.toString());
                this.d.readLock().unlock();
                return a2;
            } catch (Exception e2) {
                this.d.readLock().unlock();
                return -1;
            }
        } finally {
            this.d.readLock().unlock();
        }
    }

    public String c() {
        String a2;
        this.d.readLock().lock();
        try {
            if (this.h == null) {
                h.d("AccFacade:::speedTester is null");
                a2 = "0.0.0.0";
            } else {
                a2 = this.h.a();
                this.d.readLock().unlock();
            }
            return a2;
        } finally {
            this.d.readLock().unlock();
        }
    }

    public int d() {
        this.d.readLock().lock();
        try {
            if (this.h == null) {
                h.d("AccFacade:::speedTester is null");
                return -3;
            }
            int a2 = this.h.a(this.k, this.a.incrementAndGet(), this.o);
            this.d.readLock().unlock();
            return a2;
        } finally {
            this.d.readLock().unlock();
        }
    }

    public int e() {
        this.d.readLock().lock();
        try {
            if (this.g == null) {
                h.d("AccFacade:::otherNetDirectSpeedTester is null");
                return -3;
            }
            int a2 = this.g.a(this.o);
            this.d.readLock().unlock();
            return a2;
        } finally {
            this.d.readLock().unlock();
        }
    }

    public int f() {
        this.d.readLock().lock();
        try {
            if (this.h == null) {
                h.d("AccFacade:::speedTester is null");
                return -3;
            }
            int a2 = this.h.a(this.p, this.l, this.m, this.a.incrementAndGet(), this.o);
            this.d.readLock().unlock();
            return a2;
        } finally {
            this.d.readLock().unlock();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0022, code lost:
        if (r4.i == false) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(boolean r5) {
        /*
            r4 = this;
            r1 = 1
            r0 = 0
            java.util.concurrent.locks.ReentrantReadWriteLock r2 = r4.d
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r2 = r2.writeLock()
            r2.lock()
            com.tencent.mna.base.d.a r2 = r4.g     // Catch:{ Exception -> 0x0071 }
            if (r2 != 0) goto L_0x001e
            java.lang.String r1 = "AccFacade:::switchNetworkBinding failed, not setMobileSpeedTest"
            com.tencent.mna.base.utils.h.d(r1)     // Catch:{ Exception -> 0x0071 }
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r4.d
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
            r1.unlock()
        L_0x001d:
            return r0
        L_0x001e:
            if (r5 == 0) goto L_0x0024
            boolean r2 = r4.i     // Catch:{ Exception -> 0x0071 }
            if (r2 != 0) goto L_0x002a
        L_0x0024:
            if (r5 != 0) goto L_0x0035
            boolean r2 = r4.i     // Catch:{ Exception -> 0x0071 }
            if (r2 != 0) goto L_0x0035
        L_0x002a:
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = r4.d
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.unlock()
            r0 = r1
            goto L_0x001d
        L_0x0035:
            boolean r2 = r4.i     // Catch:{ Exception -> 0x0071 }
            if (r2 != 0) goto L_0x006f
            r2 = r1
        L_0x003a:
            r4.i = r2     // Catch:{ Exception -> 0x0071 }
            com.tencent.mna.base.d.a r2 = r4.f     // Catch:{ Exception -> 0x0071 }
            com.tencent.mna.base.d.a r3 = r4.g     // Catch:{ Exception -> 0x0071 }
            r4.f = r3     // Catch:{ Exception -> 0x0071 }
            r4.g = r2     // Catch:{ Exception -> 0x0071 }
            int r2 = r4.j     // Catch:{ Exception -> 0x0071 }
            int r3 = r4.p     // Catch:{ Exception -> 0x0071 }
            r4.j = r3     // Catch:{ Exception -> 0x0071 }
            r4.p = r2     // Catch:{ Exception -> 0x0071 }
            int r2 = r4.l     // Catch:{ Exception -> 0x0071 }
            int r3 = r4.r     // Catch:{ Exception -> 0x0071 }
            r4.l = r3     // Catch:{ Exception -> 0x0071 }
            r4.r = r2     // Catch:{ Exception -> 0x0071 }
            int r2 = r4.m     // Catch:{ Exception -> 0x0071 }
            int r3 = r4.s     // Catch:{ Exception -> 0x0071 }
            r4.m = r3     // Catch:{ Exception -> 0x0071 }
            r4.s = r2     // Catch:{ Exception -> 0x0071 }
            int r2 = r4.k     // Catch:{ Exception -> 0x0071 }
            int r3 = r4.q     // Catch:{ Exception -> 0x0071 }
            r4.k = r3     // Catch:{ Exception -> 0x0071 }
            r4.q = r2     // Catch:{ Exception -> 0x0071 }
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = r4.d
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.unlock()
            r0 = r1
            goto L_0x001d
        L_0x006f:
            r2 = r0
            goto L_0x003a
        L_0x0071:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0096 }
            r2.<init>()     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = "AccFacade:::switchNetworkBinding exception: "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x0096 }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0096 }
            com.tencent.mna.base.utils.h.d(r1)     // Catch:{ all -> 0x0096 }
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r4.d
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
            r1.unlock()
            goto L_0x001d
        L_0x0096:
            r0 = move-exception
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r4.d
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.b.a.d.a(boolean):boolean");
    }

    public void d(int i2) {
        this.d.writeLock().lock();
        if (i2 <= 0) {
            i2 = 500;
        }
        try {
            this.o = i2;
        } finally {
            this.d.writeLock().unlock();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x010f  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x013b  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x014c  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0158  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x02c6  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x02d7  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0433  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x044f  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0461  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0465  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int a(com.tencent.mna.base.c.a r34, com.tencent.mna.b.a.d.b r35, int r36, int r37, int r38, int r39, int r40) {
        /*
            r33 = this;
            int r4 = com.tencent.mna.base.a.a.x()
            if (r4 != 0) goto L_0x000e
            java.lang.String r4 = "AccFacade:::check all delay switch not open"
            com.tencent.mna.base.utils.h.c(r4)
            r4 = -10
        L_0x000d:
            return r4
        L_0x000e:
            r0 = r33
            int r4 = r0.c
            int r5 = r4 + 1
            r0 = r33
            r0.c = r5
            int r5 = com.tencent.mna.base.a.a.aP()
            if (r4 < r5) goto L_0x0049
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "AccFacade:::check all delay times limit, jumpCount:"
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r33
            int r5 = r0.c
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ", max:"
            java.lang.StringBuilder r4 = r4.append(r5)
            int r5 = com.tencent.mna.base.a.a.aP()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.mna.base.utils.h.c(r4)
            r4 = -11
            goto L_0x000d
        L_0x0049:
            int r4 = com.tencent.mna.base.a.a.x()
            r5 = 1
            if (r4 == r5) goto L_0x0057
            int r4 = com.tencent.mna.base.a.a.x()
            r5 = 2
            if (r4 != r5) goto L_0x0386
        L_0x0057:
            r4 = 1
        L_0x0058:
            int r5 = com.tencent.mna.base.a.a.x()
            r6 = 1
            if (r5 == r6) goto L_0x0066
            int r5 = com.tencent.mna.base.a.a.x()
            r6 = 3
            if (r5 != r6) goto L_0x0389
        L_0x0066:
            r5 = 1
        L_0x0067:
            long r24 = java.lang.System.currentTimeMillis()
            int r26 = r35.getFlag()
            if (r37 > 0) goto L_0x0469
            if (r5 == 0) goto L_0x0469
            int r37 = r33.a()
            r10 = r37
        L_0x0079:
            if (r36 > 0) goto L_0x007f
            if (r4 == 0) goto L_0x007f
            r36 = -1
        L_0x007f:
            android.content.Context r27 = com.tencent.mna.b.g()
            r6 = -1
            boolean r7 = com.tencent.mna.base.a.a.J()
            if (r7 == 0) goto L_0x0091
            r6 = 1
            r0 = r27
            int r6 = com.tencent.mna.base.utils.q.a(r0, r6)
        L_0x0091:
            r28 = -1
            boolean r20 = r35.isInPvp()
            boolean r29 = com.tencent.mna.base.a.a.aQ()
            int r9 = r33.d()
            r8 = 0
            java.lang.String r7 = "-1"
            r11 = -2
            if (r9 != r11) goto L_0x00c3
            int r11 = com.tencent.mna.base.a.a.aO()
            if (r11 == 0) goto L_0x00c3
            if (r20 == 0) goto L_0x038c
            r0 = r33
            com.tencent.mna.b.a.d$a r7 = r0.t
            java.lang.String r7 = r7.i
        L_0x00b3:
            r8 = 1
            int r9 = com.tencent.mna.base.utils.n.a(r7, r8)
            r8 = 1
            if (r7 == 0) goto L_0x00c1
            int r11 = r7.length()
            if (r11 > 0) goto L_0x00c3
        L_0x00c1:
            java.lang.String r7 = "-3"
        L_0x00c3:
            r11 = -10
            int[] r12 = com.tencent.mna.b.a.d.AnonymousClass1.a
            int r13 = r35.ordinal()
            r12 = r12[r13]
            switch(r12) {
                case 1: goto L_0x039a;
                case 2: goto L_0x039d;
                case 3: goto L_0x03a8;
                case 4: goto L_0x03a8;
                default: goto L_0x00d0;
            }
        L_0x00d0:
            r4 = r11
        L_0x00d1:
            r0 = r27
            r1 = r38
            int r30 = com.tencent.mna.base.utils.k.a(r0, r1)
            r5 = -1
            r14 = -1
            r13 = -1
            r12 = -1
            r11 = -1
            boolean r15 = com.tencent.mna.base.utils.k.d((int) r38)
            if (r15 == 0) goto L_0x0430
            if (r20 == 0) goto L_0x03b3
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            int r15 = r5.a
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            int r14 = r5.b
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            int r13 = r5.c
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            int r12 = r5.d
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            int r5 = r5.e
            r11 = r5
        L_0x0105:
            int r31 = com.tencent.mna.base.utils.q.e(r27)
            r18 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r16 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            if (r20 == 0) goto L_0x0433
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            double r0 = r5.f
            r18 = r0
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            double r0 = r5.g
            r16 = r0
        L_0x011f:
            r0 = r27
            r1 = r38
            int r32 = com.tencent.mna.base.utils.k.b(r0, r1)
            r5 = -10
            int r21 = com.tencent.mna.base.a.a.l()
            r22 = 1
            r0 = r21
            r1 = r22
            if (r0 != r1) goto L_0x0465
            boolean r21 = com.tencent.mna.base.a.a.J()
            if (r21 == 0) goto L_0x0465
            java.lang.String r5 = "www.qq.com"
            r21 = 1
            r0 = r21
            int r5 = com.tencent.mna.base.utils.n.b(r5, r0)
            r23 = r5
        L_0x0147:
            r5 = 65535(0xffff, float:9.1834E-41)
            if (r20 == 0) goto L_0x044f
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            int r5 = r5.h
        L_0x0152:
            java.lang.String r20 = com.tencent.mna.base.utils.i.c((android.content.Context) r27)
            if (r20 == 0) goto L_0x0461
            r21 = 95
            r22 = 44
            java.lang.String r20 = r20.replace(r21, r22)
            r22 = r20
        L_0x0162:
            r21 = -1
            r20 = -1
            if (r29 == 0) goto L_0x0174
            android.telephony.CellInfo r20 = com.tencent.mna.base.utils.i.d(r27)
            int r21 = com.tencent.mna.base.utils.i.a((android.telephony.CellInfo) r20)
            int r20 = com.tencent.mna.base.utils.i.b((android.telephony.CellInfo) r20)
        L_0x0174:
            java.lang.StringBuilder r27 = new java.lang.StringBuilder
            r27.<init>()
            r0 = r27
            r1 = r24
            java.lang.StringBuilder r24 = r0.append(r1)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            r1 = r38
            java.lang.StringBuilder r24 = r0.append(r1)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            r1 = r26
            java.lang.StringBuilder r24 = r0.append(r1)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            java.lang.StringBuilder r24 = r0.append(r10)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            r1 = r36
            java.lang.StringBuilder r24 = r0.append(r1)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            java.lang.StringBuilder r24 = r0.append(r6)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            java.lang.StringBuilder r24 = r0.append(r9)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            java.lang.StringBuilder r24 = r0.append(r4)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            r1 = r39
            java.lang.StringBuilder r24 = r0.append(r1)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            r1 = r30
            java.lang.StringBuilder r24 = r0.append(r1)
            r25 = 95
            java.lang.StringBuilder r24 = r24.append(r25)
            r0 = r24
            java.lang.StringBuilder r15 = r0.append(r15)
            r24 = 95
            r0 = r24
            java.lang.StringBuilder r15 = r15.append(r0)
            java.lang.StringBuilder r14 = r15.append(r14)
            r15 = 95
            java.lang.StringBuilder r14 = r14.append(r15)
            java.lang.StringBuilder r13 = r14.append(r13)
            r14 = 95
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r12 = r13.append(r12)
            r13 = 95
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.StringBuilder r11 = r12.append(r11)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            r0 = r31
            java.lang.StringBuilder r11 = r11.append(r0)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            r0 = r40
            java.lang.StringBuilder r11 = r11.append(r0)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            r0 = r18
            java.lang.StringBuilder r11 = r11.append(r0)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            r0 = r16
            java.lang.StringBuilder r11 = r11.append(r0)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            r0 = r32
            java.lang.StringBuilder r11 = r11.append(r0)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            r0 = r23
            java.lang.StringBuilder r11 = r11.append(r0)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r11 = r11.append(r5)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            r0 = r22
            java.lang.StringBuilder r11 = r11.append(r0)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            r0 = r21
            java.lang.StringBuilder r11 = r11.append(r0)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            r0 = r20
            java.lang.StringBuilder r11 = r11.append(r0)
            r12 = 95
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r8 = r11.append(r8)
            r11 = 95
            java.lang.StringBuilder r8 = r8.append(r11)
            r8.append(r7)
            if (r34 == 0) goto L_0x02d1
            com.tencent.mna.base.c.a$a r8 = com.tencent.mna.base.c.a.C0030a.JUMPVALUE
            java.lang.String r11 = r27.toString()
            r0 = r34
            r0.a(r8, r11)
        L_0x02d1:
            boolean r8 = com.tencent.mna.base.a.a.aU()
            if (r8 == 0) goto L_0x02de
            java.lang.String r8 = java.lang.String.valueOf(r4)
            com.tencent.mna.b.a.j.a((java.lang.String) r8)
        L_0x02de:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r11 = "[N]跳变诊断("
            java.lang.StringBuilder r8 = r8.append(r11)
            r0 = r33
            int r11 = r0.c
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.String r11 = "): stage:["
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.String r11 = r35.getDesc()
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.String r11 = "], 直连:["
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.StringBuilder r8 = r8.append(r10)
            java.lang.String r10 = "], 转发:["
            java.lang.StringBuilder r8 = r8.append(r10)
            r0 = r36
            java.lang.StringBuilder r8 = r8.append(r0)
            java.lang.String r10 = "], 路由:["
            java.lang.StringBuilder r8 = r8.append(r10)
            java.lang.StringBuilder r6 = r8.append(r6)
            java.lang.String r8 = "], 出口:["
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.String r8 = "], 重测:["
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.StringBuilder r6 = r6.append(r4)
            java.lang.String r8 = "], 下一跳:["
            java.lang.StringBuilder r6 = r6.append(r8)
            r0 = r23
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r8 = "], 信噪比:["
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.String r6 = "], 基站ID:["
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r22
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = "], RSRP/RSRQ:["
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r21
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = "/"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r20
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = "], pingIp:["
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r6 = "]"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.tencent.mna.base.utils.h.b(r5)
            goto L_0x000d
        L_0x0386:
            r4 = 0
            goto L_0x0058
        L_0x0389:
            r5 = 0
            goto L_0x0067
        L_0x038c:
            java.lang.String r7 = "www.qq.com"
            java.lang.String r7 = com.tencent.mna.base.utils.n.a(r7)
            r0 = r33
            com.tencent.mna.b.a.d$a r8 = r0.t
            r8.i = r7
            goto L_0x00b3
        L_0x039a:
            r4 = r10
            goto L_0x00d1
        L_0x039d:
            if (r4 == 0) goto L_0x03a5
            int r4 = r33.b()
            goto L_0x00d1
        L_0x03a5:
            r4 = 0
            goto L_0x00d1
        L_0x03a8:
            if (r5 == 0) goto L_0x03b0
            int r4 = r33.a()
            goto L_0x00d1
        L_0x03b0:
            r4 = 0
            goto L_0x00d1
        L_0x03b3:
            if (r29 == 0) goto L_0x0430
            com.tencent.mna.base.utils.q$a r15 = com.tencent.mna.base.utils.q.b(r27)
            if (r15 == 0) goto L_0x03bd
            int r5 = r15.a
        L_0x03bd:
            r0 = r33
            com.tencent.mna.b.a.d$a r15 = r0.t
            r15.a = r5
            java.lang.String r15 = com.tencent.mna.base.utils.q.h(r27)
            java.lang.String r16 = "_"
            java.lang.String[] r15 = r15.split(r16)
            int r0 = r15.length
            r16 = r0
            r17 = 3
            r0 = r16
            r1 = r17
            if (r0 <= r1) goto L_0x0430
            r16 = 0
            r16 = r15[r16]     // Catch:{ Exception -> 0x0413 }
            int r14 = java.lang.Integer.parseInt(r16)     // Catch:{ Exception -> 0x0413 }
            r16 = 1
            r16 = r15[r16]     // Catch:{ Exception -> 0x0413 }
            int r13 = java.lang.Integer.parseInt(r16)     // Catch:{ Exception -> 0x0413 }
            r16 = 2
            r16 = r15[r16]     // Catch:{ Exception -> 0x0413 }
            int r12 = java.lang.Integer.parseInt(r16)     // Catch:{ Exception -> 0x0413 }
            r16 = 3
            r15 = r15[r16]     // Catch:{ Exception -> 0x0413 }
            int r11 = java.lang.Integer.parseInt(r15)     // Catch:{ Exception -> 0x0413 }
            r0 = r33
            com.tencent.mna.b.a.d$a r15 = r0.t     // Catch:{ Exception -> 0x0413 }
            r15.b = r14     // Catch:{ Exception -> 0x0413 }
            r0 = r33
            com.tencent.mna.b.a.d$a r15 = r0.t     // Catch:{ Exception -> 0x0413 }
            r15.c = r13     // Catch:{ Exception -> 0x0413 }
            r0 = r33
            com.tencent.mna.b.a.d$a r15 = r0.t     // Catch:{ Exception -> 0x0413 }
            r15.d = r12     // Catch:{ Exception -> 0x0413 }
            r0 = r33
            com.tencent.mna.b.a.d$a r15 = r0.t     // Catch:{ Exception -> 0x0413 }
            r15.e = r11     // Catch:{ Exception -> 0x0413 }
            r15 = r5
            goto L_0x0105
        L_0x0413:
            r15 = move-exception
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "AccFacade:::checkAllDelay wifiSignal parse exception:"
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r15 = r15.getMessage()
            r0 = r16
            java.lang.StringBuilder r15 = r0.append(r15)
            java.lang.String r15 = r15.toString()
            com.tencent.mna.base.utils.h.d(r15)
        L_0x0430:
            r15 = r5
            goto L_0x0105
        L_0x0433:
            if (r29 == 0) goto L_0x011f
            double r18 = com.tencent.mna.base.utils.g.a()
            double r16 = com.tencent.mna.base.utils.g.b()
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            r0 = r18
            r5.f = r0
            r0 = r33
            com.tencent.mna.b.a.d$a r5 = r0.t
            r0 = r16
            r5.g = r0
            goto L_0x011f
        L_0x044f:
            if (r29 == 0) goto L_0x0152
            int r5 = com.tencent.mna.base.utils.i.c()
            r0 = r33
            com.tencent.mna.b.a.d$a r0 = r0.t
            r20 = r0
            r0 = r20
            r0.h = r5
            goto L_0x0152
        L_0x0461:
            r22 = r20
            goto L_0x0162
        L_0x0465:
            r23 = r5
            goto L_0x0147
        L_0x0469:
            r10 = r37
            goto L_0x0079
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.b.a.d.a(com.tencent.mna.base.c.a, com.tencent.mna.b.a.d$b, int, int, int, int, int):int");
    }

    public boolean g() {
        this.d.writeLock().lock();
        try {
            if (this.f != null) {
                this.f.a();
                this.f = null;
            }
            if (this.g != null) {
                this.g.a();
                this.g = null;
            }
            this.h = null;
            h.a("AccFacade:::release isCurFdMobile:" + this.i);
            if (this.j != 0) {
                if (this.i && this.e != null) {
                    this.e.b(this.j);
                }
                e.d(this.j);
                this.j = 0;
            }
            if (this.k != 0) {
                if (this.i && this.e != null) {
                    this.e.b(this.k);
                }
                e.d(this.k);
                this.k = 0;
            }
            if (this.p != 0) {
                if (!this.i && this.e != null) {
                    this.e.b(this.p);
                }
                e.d(this.p);
                this.p = 0;
            }
            if (this.q != 0) {
                if (!this.i && this.e != null) {
                    this.e.b(this.q);
                }
                e.d(this.q);
                this.q = 0;
            }
            this.l = 0;
            this.m = 0;
            this.r = 0;
            this.s = 0;
            this.o = 0;
            return true;
        } catch (Exception e2) {
            h.d("AccFacade:::release exception: " + e2.getMessage());
            return false;
        } finally {
            this.d.writeLock().unlock();
        }
    }

    public String toString() {
        return "AccelerateTesterFacade{mIsCurFdMobile=" + this.i + ", mForwardFd=" + this.j + ", mEdgeFd=" + this.k + ", mSpeedIp=" + this.l + ", mSpeedPort=" + this.m + ", mfdTimeout=" + this.n + ", mSpeedTestTimeout=" + this.o + ", mOtherNetForwardFd=" + this.p + ", mOtherNetEdgeFd=" + this.q + ", mOtherNetIp=" + this.r + ", mOtherNetPort=" + this.s + '}';
    }

    /* compiled from: AccelerateTesterFacade */
    private static class a {
        int a;
        int b;
        int c;
        int d;
        int e;
        double f;
        double g;
        int h;
        String i;

        private a() {
            this.a = -1;
            this.b = -1;
            this.c = -1;
            this.d = -1;
            this.e = -1;
            this.f = -1.0d;
            this.g = -1.0d;
            this.h = 65535;
            this.i = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        }
    }
}
