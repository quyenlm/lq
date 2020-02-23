package com.google.android.gms.location.places.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.location.places.zzd;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.List;
import java.util.Locale;

public final class zzm extends zzz<zzr> {
    private final zzat zzbko;

    private zzm(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String str, PlacesOptions placesOptions) {
        super(context, looper, 65, zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzbko = new zzat(str, Locale.getDefault(), zzq.getAccount() != null ? zzq.getAccount().name : null, (String) null, 0);
    }

    public final void zza(zzd zzd, String str) throws RemoteException {
        zzbo.zzb(str, (Object) "placeId cannot be null");
        ((zzr) zzrf()).zza(str, this.zzbko, (zzt) zzd);
    }

    public final void zza(zzd zzd, String str, int i, int i2, int i3) throws RemoteException {
        boolean z = true;
        zzbo.zzb(str, (Object) "fifeUrl cannot be null");
        zzbo.zzb(i > 0, (Object) "width should be > 0");
        if (i <= 0) {
            z = false;
        }
        zzbo.zzb(z, (Object) "height should be > 0");
        ((zzr) zzrf()).zza(str, i, i2, i3, this.zzbko, zzd);
    }

    public final void zza(com.google.android.gms.location.places.zzm zzm, AddPlaceRequest addPlaceRequest) throws RemoteException {
        zzbo.zzb(addPlaceRequest, (Object) "userAddedPlace == null");
        ((zzr) zzrf()).zza(addPlaceRequest, this.zzbko, (zzv) zzm);
    }

    public final void zza(com.google.android.gms.location.places.zzm zzm, String str, @Nullable LatLngBounds latLngBounds, @Nullable AutocompleteFilter autocompleteFilter) throws RemoteException {
        zzbo.zzb(zzm, (Object) "callback == null");
        ((zzr) zzrf()).zza(str == null ? "" : str, latLngBounds, autocompleteFilter == null ? new AutocompleteFilter.Builder().build() : autocompleteFilter, this.zzbko, zzm);
    }

    public final void zza(com.google.android.gms.location.places.zzm zzm, List<String> list) throws RemoteException {
        ((zzr) zzrf()).zza(list, this.zzbko, (zzv) zzm);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        return queryLocalInterface instanceof zzr ? (zzr) queryLocalInterface : new zzs(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.location.places.GeoDataApi";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.location.places.internal.IGooglePlacesService";
    }
}
