package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzaw;

final class zzi extends zzaw {
    private /* synthetic */ GoogleMap.OnMyLocationButtonClickListener zzblH;

    zzi(GoogleMap googleMap, GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
        this.zzblH = onMyLocationButtonClickListener;
    }

    public final boolean onMyLocationButtonClick() throws RemoteException {
        return this.zzblH.onMyLocationButtonClick();
    }
}
