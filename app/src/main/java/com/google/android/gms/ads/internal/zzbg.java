package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzpj;

final class zzbg implements Runnable {
    private /* synthetic */ zzpj zztl;
    private /* synthetic */ zzbb zzuQ;

    zzbg(zzbb zzbb, zzpj zzpj) {
        this.zzuQ = zzbb;
        this.zztl = zzpj;
    }

    public final void run() {
        try {
            this.zzuQ.zzsP.zzwi.get(this.zztl.getCustomTemplateId()).zza(this.zztl);
        } catch (RemoteException e) {
            zzafr.zzc("Could not call onCustomTemplateAdLoadedListener.onCustomTemplateAdLoaded().", e);
        }
    }
}
