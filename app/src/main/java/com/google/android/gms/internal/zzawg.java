package com.google.android.gms.internal;

import com.google.android.gms.cast.Cast;

final class zzawg extends Cast.Listener {
    private /* synthetic */ zzawf zzavF;

    zzawg(zzawf zzawf) {
        this.zzavF = zzawf;
    }

    public final void onVolumeChanged() {
        this.zzavF.zzok();
    }
}
