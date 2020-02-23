package com.google.android.gms.location.places.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.location.places.zzm;
import java.util.Locale;

public final class zzaa extends zzz<zzp> {
    private final Locale zzbjV;
    private final zzat zzbko;

    private zzaa(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String str, PlacesOptions placesOptions) {
        super(context, looper, 67, zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzbjV = Locale.getDefault();
        this.zzbko = new zzat(str, this.zzbjV, zzq.getAccount() != null ? zzq.getAccount().name : null, (String) null, 0);
    }

    public final void zza(zzm zzm, PlaceFilter placeFilter) throws RemoteException {
        if (placeFilter == null) {
            placeFilter = PlaceFilter.zzvS();
        }
        ((zzp) zzrf()).zza(placeFilter, this.zzbko, (zzv) zzm);
    }

    public final void zza(zzm zzm, PlaceReport placeReport) throws RemoteException {
        zzbo.zzu(placeReport);
        ((zzp) zzrf()).zza(placeReport, this.zzbko, (zzv) zzm);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
        return queryLocalInterface instanceof zzp ? (zzp) queryLocalInterface : new zzq(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.location.places.PlaceDetectionApi";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.location.places.internal.IGooglePlaceDetectionService";
    }
}
