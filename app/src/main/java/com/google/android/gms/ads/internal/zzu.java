package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzpj;

final class zzu implements Runnable {
    private /* synthetic */ zzq zzti;
    private /* synthetic */ zzpj zztl;

    zzu(zzq zzq, zzpj zzpj) {
        this.zzti = zzq;
        this.zztl = zzpj;
    }

    public final void run() {
        try {
            this.zzti.zzsP.zzwi.get(this.zztl.getCustomTemplateId()).zza(this.zztl);
        } catch (RemoteException e) {
            zzafr.zzc("Could not call onCustomTemplateAdLoadedListener.onCustomTemplateAdLoaded().", e);
        }
    }
}
