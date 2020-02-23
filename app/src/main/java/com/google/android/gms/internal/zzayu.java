package com.google.android.gms.internal;

import com.google.android.gms.common.util.zze;

public final class zzayu {
    private static final zzayo zzapq = new zzayo("RequestTracker");
    public static final Object zzrl = new Object();
    private long zzaxo = -1;
    private long zzayR;
    private long zzayS = 0;
    private zzayt zzayT;
    private final zze zzvw;

    public zzayu(zze zze, long j) {
        this.zzvw = zze;
        this.zzayR = j;
    }

    private final void zzoN() {
        this.zzaxo = -1;
        this.zzayT = null;
        this.zzayS = 0;
    }

    public final void clear() {
        synchronized (zzrl) {
            if (this.zzaxo != -1) {
                zzoN();
            }
        }
    }

    public final boolean test(long j) {
        boolean z;
        synchronized (zzrl) {
            z = this.zzaxo != -1 && this.zzaxo == j;
        }
        return z;
    }

    public final void zza(long j, zzayt zzayt) {
        zzayt zzayt2;
        long j2;
        synchronized (zzrl) {
            zzayt2 = this.zzayT;
            j2 = this.zzaxo;
            this.zzaxo = j;
            this.zzayT = zzayt;
            this.zzayS = this.zzvw.elapsedRealtime();
        }
        if (zzayt2 != null) {
            zzayt2.zzx(j2);
        }
    }

    public final boolean zzc(long j, int i, Object obj) {
        boolean z = true;
        zzayt zzayt = null;
        synchronized (zzrl) {
            if (this.zzaxo == -1 || this.zzaxo != j) {
                z = false;
            } else {
                zzapq.zzb("request %d completed", Long.valueOf(this.zzaxo));
                zzayt = this.zzayT;
                zzoN();
            }
        }
        if (zzayt != null) {
            zzayt.zza(j, i, obj);
        }
        return z;
    }

    public final boolean zzd(long j, int i) {
        zzayt zzayt;
        boolean z = true;
        long j2 = 0;
        synchronized (zzrl) {
            if (this.zzaxo == -1 || j - this.zzayS < this.zzayR) {
                zzayt = null;
                z = false;
            } else {
                zzapq.zzb("request %d timed out", Long.valueOf(this.zzaxo));
                j2 = this.zzaxo;
                zzayt = this.zzayT;
                zzoN();
            }
        }
        if (zzayt != null) {
            zzayt.zza(j2, i, (Object) null);
        }
        return z;
    }

    public final boolean zzoO() {
        boolean z;
        synchronized (zzrl) {
            z = this.zzaxo != -1;
        }
        return z;
    }
}
