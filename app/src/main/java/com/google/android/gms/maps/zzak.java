package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.zzbo;

final class zzak extends zzbo {
    private /* synthetic */ OnStreetViewPanoramaReadyCallback zzbmJ;

    zzak(SupportStreetViewPanoramaFragment.zza zza, OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        this.zzbmJ = onStreetViewPanoramaReadyCallback;
    }

    public final void zza(IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) throws RemoteException {
        this.zzbmJ.onStreetViewPanoramaReady(new StreetViewPanorama(iStreetViewPanoramaDelegate));
    }
}
