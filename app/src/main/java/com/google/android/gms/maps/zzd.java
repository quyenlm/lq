package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzac;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.internal.zzp;

final class zzd extends zzac {
    private /* synthetic */ GoogleMap.OnInfoWindowClickListener zzblC;

    zzd(GoogleMap googleMap, GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener) {
        this.zzblC = onInfoWindowClickListener;
    }

    public final void zze(zzp zzp) {
        this.zzblC.onInfoWindowClick(new Marker(zzp));
    }
}
