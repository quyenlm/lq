package com.tencent.mna.b.a.b;

import com.facebook.internal.NativeProtocol;
import com.tencent.mna.a.b;
import com.tencent.mna.b.a.b.f;
import com.tencent.mna.base.c.a;
import com.tencent.mna.base.c.c;

/* compiled from: LRSpeedComparator */
public class d implements b {
    private com.tencent.mna.b.a.d a;
    private a b;
    private String c;
    private String d;
    private String e;
    private String f;
    private boolean g;

    public d(com.tencent.mna.b.a.d dVar, a aVar, String str, String str2, String str3, String str4, boolean z) {
        this.a = dVar;
        this.b = aVar;
        this.f = str;
        this.c = str2;
        this.e = str3;
        this.d = str4;
        this.g = z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x019a, code lost:
        r4 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x019b, code lost:
        r5 = null;
        r6 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01c6, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01c7, code lost:
        r26 = r5;
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01d1, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01d2, code lost:
        r7 = -2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01df, code lost:
        r4 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01e0, code lost:
        r6 = r27;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x019a A[ExcHandler: Exception (e java.lang.Exception), Splitter:B:4:0x0013] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01df A[ExcHandler: Exception (e java.lang.Exception), Splitter:B:13:0x00ce] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compare(com.tencent.mna.b.a.c.c r30, com.tencent.mna.b.a.c.c r31) {
        /*
            r29 = this;
            if (r30 == 0) goto L_0x0004
            if (r31 != 0) goto L_0x000b
        L_0x0004:
            java.lang.String r4 = "compare input is null"
            com.tencent.mna.base.utils.h.d(r4)
            r4 = 0
        L_0x000a:
            return r4
        L_0x000b:
            r28 = 0
            java.lang.String r27 = ""
            r26 = 0
            r0 = r29
            java.lang.String r4 = r0.f     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r5 = com.tencent.mna.a.b.f     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r0 = r29
            java.lang.String r6 = r0.f     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r5 = com.tencent.mna.base.a.e.a(r5, r6)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r4 = com.tencent.mna.base.a.e.b(r4, r5)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            if (r4 != 0) goto L_0x0170
            android.content.Context r6 = com.tencent.mna.b.g()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r20 = com.tencent.mna.base.utils.m.a()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r7.<init>()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r8 = "Android "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r8 = com.tencent.mna.base.utils.m.b()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r21 = r7.toString()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r7.<init>()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r8 = "level "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r8 = com.tencent.mna.base.utils.m.c()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r22 = r7.toString()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r9 = 0
            r10 = 0
            r0 = r29
            com.tencent.mna.b.a.d r7 = r0.a     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            if (r7 == 0) goto L_0x006f
            r0 = r29
            com.tencent.mna.b.a.d r7 = r0.a     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r10 = r7.d()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
        L_0x006f:
            r0 = r29
            boolean r7 = r0.g     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            if (r7 == 0) goto L_0x0105
            r8 = 4
            java.lang.String r19 = com.tencent.mna.base.utils.q.d(r6)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r7 = com.tencent.mna.base.utils.q.e(r6)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            float r12 = (float) r7     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r13 = com.tencent.mna.base.utils.q.a((android.content.Context) r6)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r7 = com.tencent.mna.base.utils.q.h(r6)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.lang.String r11 = "_"
            java.lang.String[] r7 = r7.split(r11)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r11 = 0
            r11 = r7[r11]     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r14 = java.lang.Integer.parseInt(r11)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r11 = 1
            r11 = r7[r11]     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r15 = java.lang.Integer.parseInt(r11)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r11 = 2
            r11 = r7[r11]     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r16 = java.lang.Integer.parseInt(r11)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r11 = 3
            r7 = r7[r11]     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r17 = java.lang.Integer.parseInt(r7)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r18 = 0
            r7 = 1
            int r11 = com.tencent.mna.base.utils.q.a(r6, r7)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.util.Vector r6 = r30.g()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.util.Vector r7 = r31.g()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r0 = r29
            java.lang.String r0 = r0.c     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r23 = r0
            r0 = r29
            java.lang.String r0 = r0.e     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r24 = r0
            r0 = r29
            java.lang.String r0 = r0.d     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r25 = r0
            com.tencent.mna.b.a.b.f$a r5 = com.tencent.mna.b.a.b.f.a(r4, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
        L_0x00ce:
            double r8 = r5.a     // Catch:{ Exception -> 0x01df, all -> 0x01c6 }
            r6 = 0
            int r4 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r4 < 0) goto L_0x00df
            r6 = 4607182423303617035(0x3ff000010c6f7a0b, double:1.000001)
            int r4 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x0134
        L_0x00df:
            r6 = -3
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01df, all -> 0x01cc }
            r4.<init>()     // Catch:{ Exception -> 0x01df, all -> 0x01cc }
            java.lang.String r7 = "predict "
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ Exception -> 0x01df, all -> 0x01cc }
            java.lang.StringBuilder r4 = r4.append(r8)     // Catch:{ Exception -> 0x01df, all -> 0x01cc }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01df, all -> 0x01cc }
            r0 = r29
            r0.a(r6, r4, r5)
            r0 = r29
            r0.b(r6, r4, r5)
        L_0x00fd:
            java.lang.String r4 = "LRSpeedComparator return 0"
            com.tencent.mna.base.utils.h.b(r4)
            r4 = 0
            goto L_0x000a
        L_0x0105:
            r8 = 3
            java.lang.String r13 = com.tencent.mna.base.utils.i.c((android.content.Context) r6)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            int r12 = com.tencent.mna.base.utils.i.a()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r11 = 0
            java.util.Vector r6 = r30.g()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            java.util.Vector r7 = r31.g()     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r0 = r29
            java.lang.String r0 = r0.c     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r17 = r0
            r0 = r29
            java.lang.String r0 = r0.e     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r18 = r0
            r0 = r29
            java.lang.String r0 = r0.d     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            r19 = r0
            r14 = r20
            r15 = r21
            r16 = r22
            com.tencent.mna.b.a.b.f$a r5 = com.tencent.mna.b.a.b.f.a(r4, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)     // Catch:{ Exception -> 0x019a, all -> 0x01b0 }
            goto L_0x00ce
        L_0x0134:
            double r6 = com.tencent.mna.base.a.a.P()     // Catch:{ Exception -> 0x01df, all -> 0x01c6 }
            int r4 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x0156
            java.lang.String r4 = "LRSpeedComparator return 1, forward is better"
            com.tencent.mna.base.utils.h.a((java.lang.String) r4)     // Catch:{ Exception -> 0x01df, all -> 0x01c6 }
            r4 = 1
            r0 = r29
            r1 = r28
            r2 = r27
            r0.a(r1, r2, r5)
            r0 = r29
            r1 = r28
            r2 = r27
            r0.b(r1, r2, r5)
            goto L_0x000a
        L_0x0156:
            java.lang.String r4 = "LRSpeedComparator return -1, direct is better"
            com.tencent.mna.base.utils.h.a((java.lang.String) r4)     // Catch:{ Exception -> 0x01df, all -> 0x01c6 }
            r4 = -1
            r0 = r29
            r1 = r28
            r2 = r27
            r0.a(r1, r2, r5)
            r0 = r29
            r1 = r28
            r2 = r27
            r0.b(r1, r2, r5)
            goto L_0x000a
        L_0x0170:
            r5 = -2
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019a, all -> 0x01d1 }
            r6.<init>()     // Catch:{ Exception -> 0x019a, all -> 0x01d1 }
            java.lang.String r7 = "updateConfig "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x019a, all -> 0x01d1 }
            java.lang.StringBuilder r4 = r6.append(r4)     // Catch:{ Exception -> 0x019a, all -> 0x01d1 }
            java.lang.String r6 = r4.toString()     // Catch:{ Exception -> 0x019a, all -> 0x01d1 }
            java.lang.String r4 = "LRSpeedComparator return 0, direct is better"
            com.tencent.mna.base.utils.h.a((java.lang.String) r4)     // Catch:{ Exception -> 0x01e3, all -> 0x01d4 }
            r4 = 0
            r0 = r29
            r1 = r26
            r0.a(r5, r6, r1)
            r0 = r29
            r1 = r26
            r0.b(r5, r6, r1)
            goto L_0x000a
        L_0x019a:
            r4 = move-exception
            r5 = r26
            r6 = r27
        L_0x019f:
            r7 = -4
            java.lang.String r4 = android.util.Log.getStackTraceString(r4)     // Catch:{ all -> 0x01d9 }
            r0 = r29
            r0.a(r7, r4, r5)
            r0 = r29
            r0.b(r7, r4, r5)
            goto L_0x00fd
        L_0x01b0:
            r4 = move-exception
            r7 = r28
        L_0x01b3:
            r0 = r29
            r1 = r27
            r2 = r26
            r0.a(r7, r1, r2)
            r0 = r29
            r1 = r27
            r2 = r26
            r0.b(r7, r1, r2)
            throw r4
        L_0x01c6:
            r4 = move-exception
            r26 = r5
            r7 = r28
            goto L_0x01b3
        L_0x01cc:
            r4 = move-exception
            r26 = r5
            r7 = r6
            goto L_0x01b3
        L_0x01d1:
            r4 = move-exception
            r7 = r5
            goto L_0x01b3
        L_0x01d4:
            r4 = move-exception
            r27 = r6
            r7 = r5
            goto L_0x01b3
        L_0x01d9:
            r4 = move-exception
            r26 = r5
            r27 = r6
            goto L_0x01b3
        L_0x01df:
            r4 = move-exception
            r6 = r27
            goto L_0x019f
        L_0x01e3:
            r4 = move-exception
            r5 = r26
            goto L_0x019f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.b.a.b.d.compare(com.tencent.mna.b.a.c.c, com.tencent.mna.b.a.c.c):int");
    }

    private void a(int i, String str, f.a aVar) {
        com.tencent.mna.base.c.d a2 = com.tencent.mna.base.c.f.a(c.PREDICT);
        a2.a("errno", "" + i).a("errmsg", str).a("xmlver", this.f).a("openid", b.f).a("pvpid", b.a).a("origtime", "" + com.tencent.mna.b.a);
        if (aVar != null) {
            a2.a(NativeProtocol.WEB_DIALOG_PARAMS, aVar.b).a("iadirect", aVar.c).a("iaforward", aVar.d).a("sProbMin", aVar.e).a("prob", aVar.f);
        }
        a2.g();
    }

    private void b(int i, String str, f.a aVar) {
        if (this.b != null) {
            this.b.a(a.C0030a.PRE_XMLVER, String.valueOf(this.f));
            this.b.a(a.C0030a.PRE_ERRNO, String.valueOf(i));
            this.b.a(a.C0030a.PRE_ERRMSG, str);
            this.b.a(a.C0030a.PRE_PROB, aVar != null ? aVar.f : "");
        }
    }
}
