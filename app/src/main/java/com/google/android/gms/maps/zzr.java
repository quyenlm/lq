package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzba;
import com.google.android.gms.maps.model.PointOfInterest;

final class zzr extends zzba {
    private /* synthetic */ GoogleMap.OnPoiClickListener zzblQ;

    zzr(GoogleMap googleMap, GoogleMap.OnPoiClickListener onPoiClickListener) {
        this.zzblQ = onPoiClickListener;
    }

    public final void zza(PointOfInterest pointOfInterest) throws RemoteException {
        this.zzblQ.onPoiClick(pointOfInterest);
    }
}
