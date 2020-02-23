package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzam;

final class zzj extends zzam {
    private /* synthetic */ GoogleMap.OnMapLoadedCallback zzblI;

    zzj(GoogleMap googleMap, GoogleMap.OnMapLoadedCallback onMapLoadedCallback) {
        this.zzblI = onMapLoadedCallback;
    }

    public final void onMapLoaded() throws RemoteException {
        this.zzblI.onMapLoaded();
    }
}
