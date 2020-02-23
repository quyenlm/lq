package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.ads.internal.zzbs;

final class zzabr implements Runnable {
    private /* synthetic */ zzaae zzUG;
    private /* synthetic */ zzaap zzUH;
    private /* synthetic */ zzabm zzUI;

    zzabr(zzabm zzabm, zzaae zzaae, zzaap zzaap) {
        this.zzUI = zzabm;
        this.zzUG = zzaae;
        this.zzUH = zzaap;
    }

    public final void run() {
        zzaai zzaai;
        try {
            zzaai = this.zzUI.zzc(this.zzUG);
        } catch (Exception e) {
            zzbs.zzbD().zza((Throwable) e, "AdRequestServiceImpl.loadAdAsync");
            zzafr.zzc("Could not fetch ad response due to an Exception.", e);
            zzaai = null;
        }
        if (zzaai == null) {
            zzaai = new zzaai(0);
        }
        try {
            this.zzUH.zza(zzaai);
        } catch (RemoteException e2) {
            zzafr.zzc("Fail to forward ad response.", e2);
        }
    }
}
