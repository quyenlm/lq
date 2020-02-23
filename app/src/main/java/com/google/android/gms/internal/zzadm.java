package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.zzbs;

@zzzn
public final class zzadm extends zzafp implements zzads, zzadv {
    /* access modifiers changed from: private */
    public final Context mContext;
    private int mErrorCode = 3;
    private final Object mLock;
    /* access modifiers changed from: private */
    public final String zzMs;
    private final zzafg zzQQ;
    /* access modifiers changed from: private */
    public final String zzWA;
    private final zzua zzWB;
    private final long zzWC;
    private int zzWD = 0;
    private zzadp zzWE;
    private final zzadz zzWy;
    private final zzadv zzWz;

    public zzadm(Context context, String str, String str2, zzua zzua, zzafg zzafg, zzadz zzadz, zzadv zzadv, long j) {
        this.mContext = context;
        this.zzMs = str;
        this.zzWA = str2;
        this.zzWB = zzua;
        this.zzQQ = zzafg;
        this.zzWy = zzadz;
        this.mLock = new Object();
        this.zzWz = zzadv;
        this.zzWC = j;
    }

    /* access modifiers changed from: private */
    public final void zza(zzir zzir, zzut zzut) {
        this.zzWy.zzgX().zza((zzadv) this);
        try {
            if ("com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzMs)) {
                zzut.zza(zzir, this.zzWA, this.zzWB.zzLH);
            } else {
                zzut.zzc(zzir, this.zzWA);
            }
        } catch (RemoteException e) {
            zzafr.zzc("Fail to load ad from adapter.", e);
            zza(this.zzMs, 0);
        }
    }

    private final boolean zzf(long j) {
        long elapsedRealtime = this.zzWC - (zzbs.zzbF().elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            this.mErrorCode = 4;
            return false;
        }
        try {
            this.mLock.wait(elapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            this.mErrorCode = 5;
            return false;
        }
    }

    public final void onStop() {
    }

    public final void zza(String str, int i) {
        synchronized (this.mLock) {
            this.zzWD = 2;
            this.mErrorCode = i;
            this.mLock.notify();
        }
    }

    public final void zzaw(String str) {
        synchronized (this.mLock) {
            this.zzWD = 1;
            this.mLock.notify();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004e, code lost:
        r2 = new com.google.android.gms.internal.zzadr().zzg(com.google.android.gms.ads.internal.zzbs.zzbF().elapsedRealtime() - r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0063, code lost:
        if (1 != r10.zzWD) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0065, code lost:
        r0 = 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0066, code lost:
        r10.zzWE = r2.zzw(r0).zzax(r10.zzMs).zzay(r10.zzWB.zzLK).zzgU();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r0 = r10.mErrorCode;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzbd() {
        /*
            r10 = this;
            r9 = 1
            r8 = 0
            com.google.android.gms.internal.zzadz r0 = r10.zzWy
            if (r0 == 0) goto L_0x0016
            com.google.android.gms.internal.zzadz r0 = r10.zzWy
            com.google.android.gms.internal.zzadu r0 = r0.zzgX()
            if (r0 == 0) goto L_0x0016
            com.google.android.gms.internal.zzadz r0 = r10.zzWy
            com.google.android.gms.internal.zzut r0 = r0.zzgW()
            if (r0 != 0) goto L_0x0017
        L_0x0016:
            return
        L_0x0017:
            com.google.android.gms.internal.zzadz r0 = r10.zzWy
            com.google.android.gms.internal.zzadu r1 = r0.zzgX()
            r1.zza((com.google.android.gms.internal.zzadv) r8)
            r1.zza((com.google.android.gms.internal.zzads) r10)
            com.google.android.gms.internal.zzafg r0 = r10.zzQQ
            com.google.android.gms.internal.zzaae r0 = r0.zzUj
            com.google.android.gms.internal.zzir r0 = r0.zzSz
            com.google.android.gms.internal.zzadz r2 = r10.zzWy
            com.google.android.gms.internal.zzut r2 = r2.zzgW()
            boolean r3 = r2.isInitialized()     // Catch:{ RemoteException -> 0x009c }
            if (r3 == 0) goto L_0x0091
            android.os.Handler r3 = com.google.android.gms.internal.zzaiy.zzaaH     // Catch:{ RemoteException -> 0x009c }
            com.google.android.gms.internal.zzadn r4 = new com.google.android.gms.internal.zzadn     // Catch:{ RemoteException -> 0x009c }
            r4.<init>(r10, r0, r2)     // Catch:{ RemoteException -> 0x009c }
            r3.post(r4)     // Catch:{ RemoteException -> 0x009c }
        L_0x003f:
            com.google.android.gms.common.util.zze r0 = com.google.android.gms.ads.internal.zzbs.zzbF()
            long r2 = r0.elapsedRealtime()
        L_0x0047:
            java.lang.Object r4 = r10.mLock
            monitor-enter(r4)
            int r0 = r10.zzWD     // Catch:{ all -> 0x00e1 }
            if (r0 == 0) goto L_0x00ac
            com.google.android.gms.internal.zzadr r0 = new com.google.android.gms.internal.zzadr     // Catch:{ all -> 0x00e1 }
            r0.<init>()     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.common.util.zze r5 = com.google.android.gms.ads.internal.zzbs.zzbF()     // Catch:{ all -> 0x00e1 }
            long r6 = r5.elapsedRealtime()     // Catch:{ all -> 0x00e1 }
            long r2 = r6 - r2
            com.google.android.gms.internal.zzadr r2 = r0.zzg(r2)     // Catch:{ all -> 0x00e1 }
            int r0 = r10.zzWD     // Catch:{ all -> 0x00e1 }
            if (r9 != r0) goto L_0x00a9
            r0 = 6
        L_0x0066:
            com.google.android.gms.internal.zzadr r0 = r2.zzw(r0)     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = r10.zzMs     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzadr r0 = r0.zzax(r2)     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzua r2 = r10.zzWB     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = r2.zzLK     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzadr r0 = r0.zzay(r2)     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzadp r0 = r0.zzgU()     // Catch:{ all -> 0x00e1 }
            r10.zzWE = r0     // Catch:{ all -> 0x00e1 }
            monitor-exit(r4)     // Catch:{ all -> 0x00e1 }
        L_0x007f:
            r1.zza((com.google.android.gms.internal.zzadv) r8)
            r1.zza((com.google.android.gms.internal.zzads) r8)
            int r0 = r10.zzWD
            if (r0 != r9) goto L_0x00e7
            com.google.android.gms.internal.zzadv r0 = r10.zzWz
            java.lang.String r1 = r10.zzMs
            r0.zzaw(r1)
            goto L_0x0016
        L_0x0091:
            android.os.Handler r3 = com.google.android.gms.internal.zzaiy.zzaaH     // Catch:{ RemoteException -> 0x009c }
            com.google.android.gms.internal.zzado r4 = new com.google.android.gms.internal.zzado     // Catch:{ RemoteException -> 0x009c }
            r4.<init>(r10, r2, r0, r1)     // Catch:{ RemoteException -> 0x009c }
            r3.post(r4)     // Catch:{ RemoteException -> 0x009c }
            goto L_0x003f
        L_0x009c:
            r0 = move-exception
            java.lang.String r2 = "Fail to check if adapter is initialized."
            com.google.android.gms.internal.zzafr.zzc(r2, r0)
            java.lang.String r0 = r10.zzMs
            r2 = 0
            r10.zza((java.lang.String) r0, (int) r2)
            goto L_0x003f
        L_0x00a9:
            int r0 = r10.mErrorCode     // Catch:{ all -> 0x00e1 }
            goto L_0x0066
        L_0x00ac:
            boolean r0 = r10.zzf(r2)     // Catch:{ all -> 0x00e1 }
            if (r0 != 0) goto L_0x00e4
            com.google.android.gms.internal.zzadr r0 = new com.google.android.gms.internal.zzadr     // Catch:{ all -> 0x00e1 }
            r0.<init>()     // Catch:{ all -> 0x00e1 }
            int r5 = r10.mErrorCode     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzadr r0 = r0.zzw(r5)     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.common.util.zze r5 = com.google.android.gms.ads.internal.zzbs.zzbF()     // Catch:{ all -> 0x00e1 }
            long r6 = r5.elapsedRealtime()     // Catch:{ all -> 0x00e1 }
            long r2 = r6 - r2
            com.google.android.gms.internal.zzadr r0 = r0.zzg(r2)     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = r10.zzMs     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzadr r0 = r0.zzax(r2)     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzua r2 = r10.zzWB     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = r2.zzLK     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzadr r0 = r0.zzay(r2)     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzadp r0 = r0.zzgU()     // Catch:{ all -> 0x00e1 }
            r10.zzWE = r0     // Catch:{ all -> 0x00e1 }
            monitor-exit(r4)     // Catch:{ all -> 0x00e1 }
            goto L_0x007f
        L_0x00e1:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00e1 }
            throw r0
        L_0x00e4:
            monitor-exit(r4)     // Catch:{ all -> 0x00e1 }
            goto L_0x0047
        L_0x00e7:
            com.google.android.gms.internal.zzadv r0 = r10.zzWz
            java.lang.String r1 = r10.zzMs
            int r2 = r10.mErrorCode
            r0.zza(r1, r2)
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzadm.zzbd():void");
    }

    public final zzadp zzgR() {
        zzadp zzadp;
        synchronized (this.mLock) {
            zzadp = this.zzWE;
        }
        return zzadp;
    }

    public final zzua zzgS() {
        return this.zzWB;
    }

    public final void zzgT() {
        zza(this.zzQQ.zzUj.zzSz, this.zzWy.zzgW());
    }

    public final void zzv(int i) {
        zza(this.zzMs, 0);
    }
}
