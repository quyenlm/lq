package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzq;

final class zzv extends zzq {
    private /* synthetic */ GoogleMap.OnCameraMoveCanceledListener zzblU;

    zzv(GoogleMap googleMap, GoogleMap.OnCameraMoveCanceledListener onCameraMoveCanceledListener) {
        this.zzblU = onCameraMoveCanceledListener;
    }

    public final void onCameraMoveCanceled() {
        this.zzblU.onCameraMoveCanceled();
    }
}
