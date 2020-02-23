package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.internal.zzg;

final class zzk extends zzy {
    private /* synthetic */ GoogleMap.OnGroundOverlayClickListener zzblJ;

    zzk(GoogleMap googleMap, GoogleMap.OnGroundOverlayClickListener onGroundOverlayClickListener) {
        this.zzblJ = onGroundOverlayClickListener;
    }

    public final void zza(zzg zzg) {
        this.zzblJ.onGroundOverlayClick(new GroundOverlay(zzg));
    }
}
