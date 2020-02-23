package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzl;

final class zzr implements Runnable {
    private /* synthetic */ WearableListenerService.zzc zzbRA;
    private /* synthetic */ zzl zzbRF;

    zzr(WearableListenerService.zzc zzc, zzl zzl) {
        this.zzbRA = zzc;
        this.zzbRF = zzl;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.google.android.gms.wearable.internal.zzl, com.google.android.gms.wearable.zzd] */
    public final void run() {
        WearableListenerService.this.onNotificationReceived(this.zzbRF);
    }
}
