package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.internal.zzbi;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

final class zzac extends zzbi {
    private /* synthetic */ StreetViewPanorama.OnStreetViewPanoramaChangeListener zzbmD;

    zzac(StreetViewPanorama streetViewPanorama, StreetViewPanorama.OnStreetViewPanoramaChangeListener onStreetViewPanoramaChangeListener) {
        this.zzbmD = onStreetViewPanoramaChangeListener;
    }

    public final void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
        this.zzbmD.onStreetViewPanoramaChange(streetViewPanoramaLocation);
    }
}
