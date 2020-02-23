package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzu;

final class zzt extends zzu {
    private /* synthetic */ GoogleMap.OnCameraMoveStartedListener zzblS;

    zzt(GoogleMap googleMap, GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener) {
        this.zzblS = onCameraMoveStartedListener;
    }

    public final void onCameraMoveStarted(int i) {
        this.zzblS.onCameraMoveStarted(i);
    }
}
