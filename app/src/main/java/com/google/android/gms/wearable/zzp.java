package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import java.util.List;

final class zzp implements Runnable {
    private /* synthetic */ WearableListenerService.zzc zzbRA;
    private /* synthetic */ List zzbRD;

    zzp(WearableListenerService.zzc zzc, List list) {
        this.zzbRA = zzc;
        this.zzbRD = list;
    }

    public final void run() {
        WearableListenerService.this.onConnectedNodes(this.zzbRD);
    }
}
