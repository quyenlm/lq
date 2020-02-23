package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.Map;

/* compiled from: BUGLY */
public final class d {
    /* access modifiers changed from: private */
    public static d a = null;
    private a b;
    private com.tencent.bugly.crashreport.common.info.a c;
    private b d;
    private Context e;

    static /* synthetic */ void a(d dVar) {
        x.c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class<?> cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            String str = "com.tencent.bugly";
            dVar.c.getClass();
            if (!"".equals("")) {
                str = str + "." + "";
            }
            z.a(cls, "sdkPackageName", (Object) str, (Object) null);
            x.c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable th) {
            x.a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x025c A[Catch:{ Throwable -> 0x0255, all -> 0x0268 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.tencent.bugly.crashreport.crash.d r8, java.lang.Thread r9, int r10, java.lang.String r11, java.lang.String r12, java.lang.String r13, java.util.Map r14) {
        /*
            r2 = 1
            r7 = 0
            if (r9 != 0) goto L_0x0008
            java.lang.Thread r9 = java.lang.Thread.currentThread()
        L_0x0008:
            switch(r10) {
                case 4: goto L_0x0087;
                case 5: goto L_0x0019;
                case 6: goto L_0x0019;
                case 7: goto L_0x000b;
                case 8: goto L_0x008a;
                default: goto L_0x000b;
            }
        L_0x000b:
            java.lang.String r0 = "[ExtraCrashManager] Unknown extra crash type: %d"
            java.lang.Object[] r1 = new java.lang.Object[r2]
            java.lang.Integer r2 = java.lang.Integer.valueOf(r10)
            r1[r7] = r2
            com.tencent.bugly.proguard.x.d(r0, r1)
        L_0x0018:
            return
        L_0x0019:
            java.lang.String r0 = "Cocos"
        L_0x001b:
            java.lang.String r1 = "[ExtraCrashManager] %s Crash Happen"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r7] = r0
            com.tencent.bugly.proguard.x.e(r1, r2)
            com.tencent.bugly.crashreport.common.strategy.a r1 = r8.b     // Catch:{ Throwable -> 0x0255 }
            boolean r1 = r1.b()     // Catch:{ Throwable -> 0x0255 }
            if (r1 != 0) goto L_0x0034
            java.lang.String r1 = "[ExtraCrashManager] There is no remote strategy, but still store it."
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.proguard.x.d(r1, r2)     // Catch:{ Throwable -> 0x0255 }
        L_0x0034:
            com.tencent.bugly.crashreport.common.strategy.a r1 = r8.b     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r1 = r1.c()     // Catch:{ Throwable -> 0x0255 }
            boolean r2 = r1.g     // Catch:{ Throwable -> 0x0255 }
            if (r2 != 0) goto L_0x008d
            com.tencent.bugly.crashreport.common.strategy.a r2 = r8.b     // Catch:{ Throwable -> 0x0255 }
            boolean r2 = r2.b()     // Catch:{ Throwable -> 0x0255 }
            if (r2 == 0) goto L_0x008d
            java.lang.String r1 = "[ExtraCrashManager] Crash report was closed by remote , will not upload to Bugly , print local for helpful!"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.proguard.x.e(r1, r2)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = com.tencent.bugly.proguard.z.a()     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r2 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r2 = r2.d     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r3 = r9.getName()     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0255 }
            r4.<init>()     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r4 = r4.append(r11)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r5 = "\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r4 = r4.append(r12)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r5 = "\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r4 = r4.append(r13)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0255 }
            r5 = 0
            com.tencent.bugly.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.e(r0, r1)
            goto L_0x0018
        L_0x0087:
            java.lang.String r0 = "Unity"
            goto L_0x001b
        L_0x008a:
            java.lang.String r0 = "H5"
            goto L_0x001b
        L_0x008d:
            switch(r10) {
                case 4: goto L_0x0090;
                case 5: goto L_0x01d8;
                case 6: goto L_0x01d8;
                case 7: goto L_0x0090;
                case 8: goto L_0x01f0;
                default: goto L_0x0090;
            }
        L_0x0090:
            r1 = 8
            if (r10 != r1) goto L_0x0095
            r10 = 5
        L_0x0095:
            com.tencent.bugly.crashreport.crash.CrashDetailBean r5 = new com.tencent.bugly.crashreport.crash.CrashDetailBean     // Catch:{ Throwable -> 0x0255 }
            r5.<init>()     // Catch:{ Throwable -> 0x0255 }
            long r2 = com.tencent.bugly.crashreport.common.info.b.k()     // Catch:{ Throwable -> 0x0255 }
            r5.C = r2     // Catch:{ Throwable -> 0x0255 }
            long r2 = com.tencent.bugly.crashreport.common.info.b.i()     // Catch:{ Throwable -> 0x0255 }
            r5.D = r2     // Catch:{ Throwable -> 0x0255 }
            long r2 = com.tencent.bugly.crashreport.common.info.b.m()     // Catch:{ Throwable -> 0x0255 }
            r5.E = r2     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            long r2 = r1.p()     // Catch:{ Throwable -> 0x0255 }
            r5.F = r2     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            long r2 = r1.o()     // Catch:{ Throwable -> 0x0255 }
            r5.G = r2     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            long r2 = r1.q()     // Catch:{ Throwable -> 0x0255 }
            r5.H = r2     // Catch:{ Throwable -> 0x0255 }
            android.content.Context r1 = r8.e     // Catch:{ Throwable -> 0x0255 }
            int r2 = com.tencent.bugly.crashreport.crash.c.e     // Catch:{ Throwable -> 0x0255 }
            r3 = 0
            java.lang.String r1 = com.tencent.bugly.proguard.z.a((android.content.Context) r1, (int) r2, (java.lang.String) r3)     // Catch:{ Throwable -> 0x0255 }
            r5.w = r1     // Catch:{ Throwable -> 0x0255 }
            r5.b = r10     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r1.h()     // Catch:{ Throwable -> 0x0255 }
            r5.e = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r1.j     // Catch:{ Throwable -> 0x0255 }
            r5.f = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r1.w()     // Catch:{ Throwable -> 0x0255 }
            r5.g = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r1.g()     // Catch:{ Throwable -> 0x0255 }
            r5.m = r1     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0255 }
            r1.<init>()     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r1 = r1.append(r11)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0255 }
            r5.n = r1     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0255 }
            r1.<init>()     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r1 = r1.append(r12)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0255 }
            r5.o = r1     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = ""
            if (r13 == 0) goto L_0x0208
            java.lang.String r2 = "\n"
            java.lang.String[] r2 = r13.split(r2)     // Catch:{ Throwable -> 0x0255 }
            int r3 = r2.length     // Catch:{ Throwable -> 0x0255 }
            if (r3 <= 0) goto L_0x011d
            r1 = 0
            r1 = r2[r1]     // Catch:{ Throwable -> 0x0255 }
        L_0x011d:
            r2 = r13
        L_0x011e:
            r5.p = r1     // Catch:{ Throwable -> 0x0255 }
            r5.q = r2     // Catch:{ Throwable -> 0x0255 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0255 }
            r5.r = r2     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r5.q     // Catch:{ Throwable -> 0x0255 }
            byte[] r1 = r1.getBytes()     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = com.tencent.bugly.proguard.z.b((byte[]) r1)     // Catch:{ Throwable -> 0x0255 }
            r5.u = r1     // Catch:{ Throwable -> 0x0255 }
            int r1 = com.tencent.bugly.crashreport.crash.c.f     // Catch:{ Throwable -> 0x0255 }
            r2 = 0
            java.util.Map r1 = com.tencent.bugly.proguard.z.a((int) r1, (boolean) r2)     // Catch:{ Throwable -> 0x0255 }
            r5.z = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r1.d     // Catch:{ Throwable -> 0x0255 }
            r5.A = r1     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0255 }
            r1.<init>()     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r2 = r9.getName()     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r2 = "("
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0255 }
            long r2 = r9.getId()     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r2 = ")"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0255 }
            r5.B = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r1 = r1.y()     // Catch:{ Throwable -> 0x0255 }
            r5.I = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.util.Map r1 = r1.v()     // Catch:{ Throwable -> 0x0255 }
            r5.h = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            long r2 = r1.a     // Catch:{ Throwable -> 0x0255 }
            r5.M = r2     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            boolean r1 = r1.a()     // Catch:{ Throwable -> 0x0255 }
            r5.N = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            int r1 = r1.H()     // Catch:{ Throwable -> 0x0255 }
            r5.P = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            int r1 = r1.I()     // Catch:{ Throwable -> 0x0255 }
            r5.Q = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.util.Map r1 = r1.B()     // Catch:{ Throwable -> 0x0255 }
            r5.R = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r1 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.util.Map r1 = r1.G()     // Catch:{ Throwable -> 0x0255 }
            r5.S = r1     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.crash.b r1 = r8.d     // Catch:{ Throwable -> 0x0255 }
            r1.c((com.tencent.bugly.crashreport.crash.CrashDetailBean) r5)     // Catch:{ Throwable -> 0x0255 }
            byte[] r1 = com.tencent.bugly.proguard.y.a()     // Catch:{ Throwable -> 0x0255 }
            r5.y = r1     // Catch:{ Throwable -> 0x0255 }
            java.util.Map<java.lang.String, java.lang.String> r1 = r5.O     // Catch:{ Throwable -> 0x0255 }
            if (r1 != 0) goto L_0x01be
            java.util.LinkedHashMap r1 = new java.util.LinkedHashMap     // Catch:{ Throwable -> 0x0255 }
            r1.<init>()     // Catch:{ Throwable -> 0x0255 }
            r5.O = r1     // Catch:{ Throwable -> 0x0255 }
        L_0x01be:
            if (r14 == 0) goto L_0x01c5
            java.util.Map<java.lang.String, java.lang.String> r1 = r5.O     // Catch:{ Throwable -> 0x0255 }
            r1.putAll(r14)     // Catch:{ Throwable -> 0x0255 }
        L_0x01c5:
            if (r5 != 0) goto L_0x020c
            java.lang.String r0 = "[ExtraCrashManager] Failed to package crash data."
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.proguard.x.e(r0, r1)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.e(r0, r1)
            goto L_0x0018
        L_0x01d8:
            boolean r1 = r1.l     // Catch:{ Throwable -> 0x0255 }
            if (r1 != 0) goto L_0x0090
            java.lang.String r1 = "[ExtraCrashManager] %s report is disabled."
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0255 }
            r3 = 0
            r2[r3] = r0     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.proguard.x.e(r1, r2)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.e(r0, r1)
            goto L_0x0018
        L_0x01f0:
            boolean r1 = r1.m     // Catch:{ Throwable -> 0x0255 }
            if (r1 != 0) goto L_0x0090
            java.lang.String r1 = "[ExtraCrashManager] %s report is disabled."
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0255 }
            r3 = 0
            r2[r3] = r0     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.proguard.x.e(r1, r2)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.e(r0, r1)
            goto L_0x0018
        L_0x0208:
            java.lang.String r2 = ""
            goto L_0x011e
        L_0x020c:
            java.lang.String r1 = com.tencent.bugly.proguard.z.a()     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.common.info.a r2 = r8.c     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r2 = r2.d     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r3 = r9.getName()     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0255 }
            r4.<init>()     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r4 = r4.append(r11)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r6 = "\n"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r4 = r4.append(r12)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r6 = "\n"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x0255 }
            java.lang.StringBuilder r4 = r4.append(r13)     // Catch:{ Throwable -> 0x0255 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0255 }
            com.tencent.bugly.crashreport.crash.b r0 = r8.d     // Catch:{ Throwable -> 0x0255 }
            boolean r0 = r0.a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r5)     // Catch:{ Throwable -> 0x0255 }
            if (r0 != 0) goto L_0x024c
            com.tencent.bugly.crashreport.crash.b r0 = r8.d     // Catch:{ Throwable -> 0x0255 }
            r2 = 3000(0xbb8, double:1.482E-320)
            r1 = 0
            r0.a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r5, (long) r2, (boolean) r1)     // Catch:{ Throwable -> 0x0255 }
        L_0x024c:
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.e(r0, r1)
            goto L_0x0018
        L_0x0255:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x0268 }
            if (r1 != 0) goto L_0x025f
            r0.printStackTrace()     // Catch:{ all -> 0x0268 }
        L_0x025f:
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.e(r0, r1)
            goto L_0x0018
        L_0x0268:
            r0 = move-exception
            java.lang.String r1 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r2 = new java.lang.Object[r7]
            com.tencent.bugly.proguard.x.e(r1, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.d.a(com.tencent.bugly.crashreport.crash.d, java.lang.Thread, int, java.lang.String, java.lang.String, java.lang.String, java.util.Map):void");
    }

    private d(Context context) {
        c a2 = c.a();
        if (a2 != null) {
            this.b = a.a();
            this.c = com.tencent.bugly.crashreport.common.info.a.a(context);
            this.d = a2.p;
            this.e = context;
            w.a().a(new Runnable() {
                public final void run() {
                    d.a(d.this);
                }
            });
        }
    }

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    public static void a(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        final Thread thread2 = thread;
        final int i2 = i;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Map<String, String> map2 = map;
        w.a().a(new Runnable() {
            public final void run() {
                try {
                    if (d.a == null) {
                        x.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    } else {
                        d.a(d.a, thread2, i2, str4, str5, str6, map2);
                    }
                } catch (Throwable th) {
                    if (!x.b(th)) {
                        th.printStackTrace();
                    }
                    x.e("[ExtraCrashManager] Crash error %s %s %s", str4, str5, str6);
                }
            }
        });
    }
}
