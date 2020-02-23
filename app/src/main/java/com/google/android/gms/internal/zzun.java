package com.google.android.gms.internal;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

@zzzn
public final class zzun implements zztz {
    private final Context mContext;
    private final Object mLock = new Object();
    private final long mStartTime;
    private final zzaae zzMM;
    private final long zzMN;
    private boolean zzMP = false;
    private final String zzMR;
    private List<zzuh> zzMS = new ArrayList();
    private zzue zzMW;
    private final zzub zzMu;
    private final boolean zzMy;
    private final zznb zzsK;
    private final zzuq zzsX;
    private final boolean zzwJ;

    public zzun(Context context, zzaae zzaae, zzuq zzuq, zzub zzub, boolean z, boolean z2, String str, long j, long j2, zznb zznb) {
        this.mContext = context;
        this.zzMM = zzaae;
        this.zzsX = zzuq;
        this.zzMu = zzub;
        this.zzwJ = z;
        this.zzMy = z2;
        this.zzMR = str;
        this.mStartTime = j;
        this.zzMN = j2;
        this.zzsK = zznb;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzMP = true;
            if (this.zzMW != null) {
                this.zzMW.cancel();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00e7, code lost:
        r2 = r23.zzMW.zza(r23.mStartTime, r23.zzMN);
        r23.zzMS.add(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0100, code lost:
        if (r2.zzMF != 0) goto L_0x014e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0102, code lost:
        com.google.android.gms.internal.zzafr.zzaC("Adapter succeeded.");
        r23.zzsK.zzh("mediation_network_succeed", r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0114, code lost:
        if (r17.isEmpty() != false) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0116, code lost:
        r23.zzsK.zzh("mediation_networks_fail", android.text.TextUtils.join(",", r17));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0127, code lost:
        r23.zzsK.zza(r21, "mls");
        r23.zzsK.zza(r18, "ttm");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x014e, code lost:
        r17.add(r4);
        r23.zzsK.zza(r21, "mlf");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0166, code lost:
        if (r2.zzMH == null) goto L_0x006d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0168, code lost:
        com.google.android.gms.internal.zzagz.zzZr.post(new com.google.android.gms.internal.zzuo(r23, r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzuh zzf(java.util.List<com.google.android.gms.internal.zzua> r24) {
        /*
            r23 = this;
            java.lang.String r2 = "Starting mediation."
            com.google.android.gms.internal.zzafr.zzaC(r2)
            java.util.ArrayList r17 = new java.util.ArrayList
            r17.<init>()
            r0 = r23
            com.google.android.gms.internal.zznb r2 = r0.zzsK
            com.google.android.gms.internal.zzmz r18 = r2.zzdS()
            r0 = r23
            com.google.android.gms.internal.zzaae r2 = r0.zzMM
            com.google.android.gms.internal.zziv r2 = r2.zzvX
            r3 = 2
            int[] r3 = new int[r3]
            com.google.android.gms.internal.zziv[] r4 = r2.zzAu
            if (r4 == 0) goto L_0x0195
            com.google.android.gms.ads.internal.zzbs.zzbS()
            r0 = r23
            java.lang.String r4 = r0.zzMR
            boolean r4 = com.google.android.gms.internal.zzuj.zza((java.lang.String) r4, (int[]) r3)
            if (r4 == 0) goto L_0x0195
            r4 = 0
            r4 = r3[r4]
            r5 = 1
            r5 = r3[r5]
            com.google.android.gms.internal.zziv[] r6 = r2.zzAu
            int r7 = r6.length
            r3 = 0
        L_0x0036:
            if (r3 >= r7) goto L_0x0195
            r9 = r6[r3]
            int r8 = r9.width
            if (r4 != r8) goto L_0x0096
            int r8 = r9.height
            if (r5 != r8) goto L_0x0096
        L_0x0042:
            java.util.Iterator r19 = r24.iterator()
        L_0x0046:
            boolean r2 = r19.hasNext()
            if (r2 == 0) goto L_0x0176
            java.lang.Object r7 = r19.next()
            com.google.android.gms.internal.zzua r7 = (com.google.android.gms.internal.zzua) r7
            java.lang.String r3 = "Trying mediation network: "
            java.lang.String r2 = r7.zzLI
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r4 = r2.length()
            if (r4 == 0) goto L_0x0099
            java.lang.String r2 = r3.concat(r2)
        L_0x0064:
            com.google.android.gms.internal.zzafr.zzaS(r2)
            java.util.List<java.lang.String> r2 = r7.zzLJ
            java.util.Iterator r20 = r2.iterator()
        L_0x006d:
            boolean r2 = r20.hasNext()
            if (r2 == 0) goto L_0x0046
            java.lang.Object r4 = r20.next()
            java.lang.String r4 = (java.lang.String) r4
            r0 = r23
            com.google.android.gms.internal.zznb r2 = r0.zzsK
            com.google.android.gms.internal.zzmz r21 = r2.zzdS()
            r0 = r23
            java.lang.Object r0 = r0.mLock
            r22 = r0
            monitor-enter(r22)
            r0 = r23
            boolean r2 = r0.zzMP     // Catch:{ all -> 0x014b }
            if (r2 == 0) goto L_0x009f
            com.google.android.gms.internal.zzuh r2 = new com.google.android.gms.internal.zzuh     // Catch:{ all -> 0x014b }
            r3 = -1
            r2.<init>(r3)     // Catch:{ all -> 0x014b }
            monitor-exit(r22)     // Catch:{ all -> 0x014b }
        L_0x0095:
            return r2
        L_0x0096:
            int r3 = r3 + 1
            goto L_0x0036
        L_0x0099:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r3)
            goto L_0x0064
        L_0x009f:
            com.google.android.gms.internal.zzue r2 = new com.google.android.gms.internal.zzue     // Catch:{ all -> 0x014b }
            r0 = r23
            android.content.Context r3 = r0.mContext     // Catch:{ all -> 0x014b }
            r0 = r23
            com.google.android.gms.internal.zzuq r5 = r0.zzsX     // Catch:{ all -> 0x014b }
            r0 = r23
            com.google.android.gms.internal.zzub r6 = r0.zzMu     // Catch:{ all -> 0x014b }
            r0 = r23
            com.google.android.gms.internal.zzaae r8 = r0.zzMM     // Catch:{ all -> 0x014b }
            com.google.android.gms.internal.zzir r8 = r8.zzSz     // Catch:{ all -> 0x014b }
            r0 = r23
            com.google.android.gms.internal.zzaae r10 = r0.zzMM     // Catch:{ all -> 0x014b }
            com.google.android.gms.internal.zzaje r10 = r10.zzvT     // Catch:{ all -> 0x014b }
            r0 = r23
            boolean r11 = r0.zzwJ     // Catch:{ all -> 0x014b }
            r0 = r23
            boolean r12 = r0.zzMy     // Catch:{ all -> 0x014b }
            r0 = r23
            com.google.android.gms.internal.zzaae r13 = r0.zzMM     // Catch:{ all -> 0x014b }
            com.google.android.gms.internal.zzon r13 = r13.zzwj     // Catch:{ all -> 0x014b }
            r0 = r23
            com.google.android.gms.internal.zzaae r14 = r0.zzMM     // Catch:{ all -> 0x014b }
            java.util.List<java.lang.String> r14 = r14.zzwq     // Catch:{ all -> 0x014b }
            r0 = r23
            com.google.android.gms.internal.zzaae r15 = r0.zzMM     // Catch:{ all -> 0x014b }
            java.util.List<java.lang.String> r15 = r15.zzSO     // Catch:{ all -> 0x014b }
            r0 = r23
            com.google.android.gms.internal.zzaae r0 = r0.zzMM     // Catch:{ all -> 0x014b }
            r16 = r0
            r0 = r16
            java.util.List<java.lang.String> r0 = r0.zzTj     // Catch:{ all -> 0x014b }
            r16 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x014b }
            r0 = r23
            r0.zzMW = r2     // Catch:{ all -> 0x014b }
            monitor-exit(r22)     // Catch:{ all -> 0x014b }
            r0 = r23
            com.google.android.gms.internal.zzue r2 = r0.zzMW
            r0 = r23
            long r10 = r0.mStartTime
            r0 = r23
            long r12 = r0.zzMN
            com.google.android.gms.internal.zzuh r2 = r2.zza((long) r10, (long) r12)
            r0 = r23
            java.util.List<com.google.android.gms.internal.zzuh> r3 = r0.zzMS
            r3.add(r2)
            int r3 = r2.zzMF
            if (r3 != 0) goto L_0x014e
            java.lang.String r3 = "Adapter succeeded."
            com.google.android.gms.internal.zzafr.zzaC(r3)
            r0 = r23
            com.google.android.gms.internal.zznb r3 = r0.zzsK
            java.lang.String r5 = "mediation_network_succeed"
            r3.zzh(r5, r4)
            boolean r3 = r17.isEmpty()
            if (r3 != 0) goto L_0x0127
            r0 = r23
            com.google.android.gms.internal.zznb r3 = r0.zzsK
            java.lang.String r4 = "mediation_networks_fail"
            java.lang.String r5 = ","
            r0 = r17
            java.lang.String r5 = android.text.TextUtils.join(r5, r0)
            r3.zzh(r4, r5)
        L_0x0127:
            r0 = r23
            com.google.android.gms.internal.zznb r3 = r0.zzsK
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]
            r5 = 0
            java.lang.String r6 = "mls"
            r4[r5] = r6
            r0 = r21
            r3.zza(r0, r4)
            r0 = r23
            com.google.android.gms.internal.zznb r3 = r0.zzsK
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]
            r5 = 0
            java.lang.String r6 = "ttm"
            r4[r5] = r6
            r0 = r18
            r3.zza(r0, r4)
            goto L_0x0095
        L_0x014b:
            r2 = move-exception
            monitor-exit(r22)     // Catch:{ all -> 0x014b }
            throw r2
        L_0x014e:
            r0 = r17
            r0.add(r4)
            r0 = r23
            com.google.android.gms.internal.zznb r3 = r0.zzsK
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]
            r5 = 0
            java.lang.String r6 = "mlf"
            r4[r5] = r6
            r0 = r21
            r3.zza(r0, r4)
            com.google.android.gms.internal.zzut r3 = r2.zzMH
            if (r3 == 0) goto L_0x006d
            android.os.Handler r3 = com.google.android.gms.internal.zzagz.zzZr
            com.google.android.gms.internal.zzuo r4 = new com.google.android.gms.internal.zzuo
            r0 = r23
            r4.<init>(r0, r2)
            r3.post(r4)
            goto L_0x006d
        L_0x0176:
            boolean r2 = r17.isEmpty()
            if (r2 != 0) goto L_0x018d
            r0 = r23
            com.google.android.gms.internal.zznb r2 = r0.zzsK
            java.lang.String r3 = "mediation_networks_fail"
            java.lang.String r4 = ","
            r0 = r17
            java.lang.String r4 = android.text.TextUtils.join(r4, r0)
            r2.zzh(r3, r4)
        L_0x018d:
            com.google.android.gms.internal.zzuh r2 = new com.google.android.gms.internal.zzuh
            r3 = 1
            r2.<init>(r3)
            goto L_0x0095
        L_0x0195:
            r9 = r2
            goto L_0x0042
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzun.zzf(java.util.List):com.google.android.gms.internal.zzuh");
    }

    public final List<zzuh> zzfg() {
        return this.zzMS;
    }
}
