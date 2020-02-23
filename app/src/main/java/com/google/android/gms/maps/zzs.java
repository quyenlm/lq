package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzm;
import com.google.android.gms.maps.model.CameraPosition;

final class zzs extends zzm {
    private /* synthetic */ GoogleMap.OnCameraChangeListener zzblR;

    zzs(GoogleMap googleMap, GoogleMap.OnCameraChangeListener onCameraChangeListener) {
        this.zzblR = onCameraChangeListener;
    }

    public final void onCameraChange(CameraPosition cameraPosition) {
        this.zzblR.onCameraChange(cameraPosition);
    }
}
