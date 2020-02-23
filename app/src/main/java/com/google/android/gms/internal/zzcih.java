package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.measurement.AppMeasurement;

final class zzcih implements Runnable {
    private /* synthetic */ zzcid zzbua;
    private /* synthetic */ AppMeasurement.zzb zzbuc;

    zzcih(zzcid zzcid, AppMeasurement.zzb zzb) {
        this.zzbua = zzcid;
        this.zzbuc = zzb;
    }

    public final void run() {
        zzcfd zzd = this.zzbua.zzbtU;
        if (zzd == null) {
            this.zzbua.zzwF().zzyx().log("Failed to send current screen to service");
            return;
        }
        try {
            if (this.zzbuc == null) {
                zzd.zza(0, (String) null, (String) null, this.zzbua.getContext().getPackageName());
            } else {
                zzd.zza(this.zzbuc.zzbol, this.zzbuc.zzboj, this.zzbuc.zzbok, this.zzbua.getContext().getPackageName());
            }
            this.zzbua.zzkP();
        } catch (RemoteException e) {
            this.zzbua.zzwF().zzyx().zzj("Failed to send current screen to the service", e);
        }
    }
}
