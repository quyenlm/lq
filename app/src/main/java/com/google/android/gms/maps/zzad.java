package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.internal.zzbg;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

final class zzad extends zzbg {
    private /* synthetic */ StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener zzbmE;

    zzad(StreetViewPanorama streetViewPanorama, StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener onStreetViewPanoramaCameraChangeListener) {
        this.zzbmE = onStreetViewPanoramaCameraChangeListener;
    }

    public final void onStreetViewPanoramaCameraChange(StreetViewPanoramaCamera streetViewPanoramaCamera) {
        this.zzbmE.onStreetViewPanoramaCameraChange(streetViewPanoramaCamera);
    }
}
