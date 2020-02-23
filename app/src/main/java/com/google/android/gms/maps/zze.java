package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzag;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.internal.zzp;

final class zze extends zzag {
    private /* synthetic */ GoogleMap.OnInfoWindowLongClickListener zzblD;

    zze(GoogleMap googleMap, GoogleMap.OnInfoWindowLongClickListener onInfoWindowLongClickListener) {
        this.zzblD = onInfoWindowLongClickListener;
    }

    public final void zzf(zzp zzp) {
        this.zzblD.onInfoWindowLongClick(new Marker(zzp));
    }
}
