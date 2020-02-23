package com.google.android.gms.ads.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zzzn;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.lang.ref.WeakReference;

@zzzn
public final class zzbi {
    private final zzbk zzuS;
    /* access modifiers changed from: private */
    @Nullable
    public zzir zzuT;
    /* access modifiers changed from: private */
    public boolean zzuU;
    private boolean zzuV;
    private long zzuW;
    private final Runnable zzv;

    public zzbi(zza zza) {
        this(zza, new zzbk(zzagz.zzZr));
    }

    private zzbi(zza zza, zzbk zzbk) {
        this.zzuU = false;
        this.zzuV = false;
        this.zzuW = 0;
        this.zzuS = zzbk;
        this.zzv = new zzbj(this, new WeakReference(zza));
    }

    public final void cancel() {
        this.zzuU = false;
        this.zzuS.removeCallbacks(this.zzv);
    }

    public final void pause() {
        this.zzuV = true;
        if (this.zzuU) {
            this.zzuS.removeCallbacks(this.zzv);
        }
    }

    public final void resume() {
        this.zzuV = false;
        if (this.zzuU) {
            this.zzuU = false;
            zza(this.zzuT, this.zzuW);
        }
    }

    public final void zza(zzir zzir, long j) {
        if (this.zzuU) {
            zzafr.zzaT("An ad refresh is already scheduled.");
            return;
        }
        this.zzuT = zzir;
        this.zzuU = true;
        this.zzuW = j;
        if (!this.zzuV) {
            zzafr.zzaS(new StringBuilder(65).append("Scheduling ad refresh ").append(j).append(" milliseconds from now.").toString());
            this.zzuS.postDelayed(this.zzv, j);
        }
    }

    public final boolean zzbo() {
        return this.zzuU;
    }

    public final void zzf(zzir zzir) {
        this.zzuT = zzir;
    }

    public final void zzg(zzir zzir) {
        zza(zzir, (long) Constants.WATCHDOG_WAKE_TIMER);
    }
}
