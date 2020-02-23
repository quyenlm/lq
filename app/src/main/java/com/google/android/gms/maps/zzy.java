package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzao;
import com.google.android.gms.maps.model.LatLng;

final class zzy extends zzao {
    private /* synthetic */ GoogleMap.OnMapLongClickListener zzblX;

    zzy(GoogleMap googleMap, GoogleMap.OnMapLongClickListener onMapLongClickListener) {
        this.zzblX = onMapLongClickListener;
    }

    public final void onMapLongClick(LatLng latLng) {
        this.zzblX.onMapLongClick(latLng);
    }
}
