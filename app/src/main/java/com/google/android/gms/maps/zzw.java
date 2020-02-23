package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzo;

final class zzw extends zzo {
    private /* synthetic */ GoogleMap.OnCameraIdleListener zzblV;

    zzw(GoogleMap googleMap, GoogleMap.OnCameraIdleListener onCameraIdleListener) {
        this.zzblV = onCameraIdleListener;
    }

    public final void onCameraIdle() {
        this.zzblV.onCameraIdle();
    }
}
