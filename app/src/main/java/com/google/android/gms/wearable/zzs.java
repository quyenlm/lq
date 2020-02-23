package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzi;

final class zzs implements Runnable {
    private /* synthetic */ WearableListenerService.zzc zzbRA;
    private /* synthetic */ zzi zzbRG;

    zzs(WearableListenerService.zzc zzc, zzi zzi) {
        this.zzbRA = zzc;
        this.zzbRG = zzi;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.google.android.gms.wearable.internal.zzi, com.google.android.gms.wearable.zzb] */
    public final void run() {
        WearableListenerService.this.onEntityUpdate(this.zzbRG);
    }
}
