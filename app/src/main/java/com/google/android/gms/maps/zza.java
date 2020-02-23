package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzaa;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.internal.zzj;

final class zza extends zzaa {
    private /* synthetic */ GoogleMap.OnIndoorStateChangeListener zzblz;

    zza(GoogleMap googleMap, GoogleMap.OnIndoorStateChangeListener onIndoorStateChangeListener) {
        this.zzblz = onIndoorStateChangeListener;
    }

    public final void onIndoorBuildingFocused() {
        this.zzblz.onIndoorBuildingFocused();
    }

    public final void zza(zzj zzj) {
        this.zzblz.onIndoorLevelActivated(new IndoorBuilding(zzj));
    }
}
