package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzs;

final class zzu extends zzs {
    private /* synthetic */ GoogleMap.OnCameraMoveListener zzblT;

    zzu(GoogleMap googleMap, GoogleMap.OnCameraMoveListener onCameraMoveListener) {
        this.zzblT = onCameraMoveListener;
    }

    public final void onCameraMove() {
        this.zzblT.onCameraMove();
    }
}
