package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzdx;

final class zzm implements Runnable {
    private /* synthetic */ WearableListenerService.zzc zzbRA;
    private /* synthetic */ zzdx zzbRB;

    zzm(WearableListenerService.zzc zzc, zzdx zzdx) {
        this.zzbRA = zzc;
        this.zzbRB = zzdx;
    }

    public final void run() {
        WearableListenerService.this.onMessageReceived(this.zzbRB);
    }
}
