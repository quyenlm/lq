package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzai;

final class zzt implements Runnable {
    private /* synthetic */ WearableListenerService.zzc zzbRA;
    private /* synthetic */ zzai zzbRH;

    zzt(WearableListenerService.zzc zzc, zzai zzai) {
        this.zzbRA = zzc;
        this.zzbRH = zzai;
    }

    public final void run() {
        this.zzbRH.zza(WearableListenerService.this);
    }
}
