package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzeg;

final class zzn implements Runnable {
    private /* synthetic */ WearableListenerService.zzc zzbRA;
    private /* synthetic */ zzeg zzbRC;

    zzn(WearableListenerService.zzc zzc, zzeg zzeg) {
        this.zzbRA = zzc;
        this.zzbRC = zzeg;
    }

    public final void run() {
        WearableListenerService.this.onPeerConnected(this.zzbRC);
    }
}
