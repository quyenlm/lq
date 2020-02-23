package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.internal.zzbk;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

final class zzae extends zzbk {
    private /* synthetic */ StreetViewPanorama.OnStreetViewPanoramaClickListener zzbmF;

    zzae(StreetViewPanorama streetViewPanorama, StreetViewPanorama.OnStreetViewPanoramaClickListener onStreetViewPanoramaClickListener) {
        this.zzbmF = onStreetViewPanoramaClickListener;
    }

    public final void onStreetViewPanoramaClick(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
        this.zzbmF.onStreetViewPanoramaClick(streetViewPanoramaOrientation);
    }
}
