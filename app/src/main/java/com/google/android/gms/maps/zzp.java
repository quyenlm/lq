package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzbe;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;

final class zzp extends zzbe {
    private /* synthetic */ GoogleMap.OnPolylineClickListener zzblO;

    zzp(GoogleMap googleMap, GoogleMap.OnPolylineClickListener onPolylineClickListener) {
        this.zzblO = onPolylineClickListener;
    }

    public final void zza(IPolylineDelegate iPolylineDelegate) {
        this.zzblO.onPolylineClick(new Polyline(iPolylineDelegate));
    }
}
