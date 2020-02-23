package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;

final class zzcia implements Runnable {
    private /* synthetic */ boolean zzbtN;
    private /* synthetic */ AppMeasurement.zzb zzbtO;
    private /* synthetic */ zzcic zzbtP;
    private /* synthetic */ zzchz zzbtQ;

    zzcia(zzchz zzchz, boolean z, AppMeasurement.zzb zzb, zzcic zzcic) {
        this.zzbtQ = zzchz;
        this.zzbtN = z;
        this.zzbtO = zzb;
        this.zzbtP = zzcic;
    }

    public final void run() {
        if (this.zzbtN && this.zzbtQ.zzbtE != null) {
            this.zzbtQ.zza(this.zzbtQ.zzbtE);
        }
        if (this.zzbtO == null || this.zzbtO.zzbol != this.zzbtP.zzbol || !zzcjl.zzR(this.zzbtO.zzbok, this.zzbtP.zzbok) || !zzcjl.zzR(this.zzbtO.zzboj, this.zzbtP.zzboj)) {
            Bundle bundle = new Bundle();
            zzchz.zza((AppMeasurement.zzb) this.zzbtP, bundle);
            if (this.zzbtO != null) {
                if (this.zzbtO.zzboj != null) {
                    bundle.putString("_pn", this.zzbtO.zzboj);
                }
                bundle.putString("_pc", this.zzbtO.zzbok);
                bundle.putLong("_pi", this.zzbtO.zzbol);
            }
            this.zzbtQ.zzwt().zzd("auto", "_vs", bundle);
        }
        this.zzbtQ.zzbtE = this.zzbtP;
        this.zzbtQ.zzww().zza((AppMeasurement.zzb) this.zzbtP);
    }
}
