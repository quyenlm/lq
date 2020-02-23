package com.google.android.gms.maps;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.internal.zzah;
import com.google.android.gms.maps.model.RuntimeRemoteException;

final class zzm implements LocationSource.OnLocationChangedListener {
    private /* synthetic */ zzah zzblL;

    zzm(zzl zzl, zzah zzah) {
        this.zzblL = zzah;
    }

    public final void onLocationChanged(Location location) {
        try {
            this.zzblL.zzd(location);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
