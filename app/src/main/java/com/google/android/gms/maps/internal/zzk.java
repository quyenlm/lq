package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

public final class zzk extends zzed implements IMapViewDelegate {
    zzk(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IMapViewDelegate");
    }

    public final IGoogleMapDelegate getMap() throws RemoteException {
        IGoogleMapDelegate zzg;
        Parcel zza = zza(1, zzZ());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzg = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            zzg = queryLocalInterface instanceof IGoogleMapDelegate ? (IGoogleMapDelegate) queryLocalInterface : new zzg(readStrongBinder);
        }
        zza.recycle();
        return zzg;
    }

    public final void getMapAsync(zzap zzap) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) zzap);
        zzb(9, zzZ);
    }

    public final IObjectWrapper getView() throws RemoteException {
        Parcel zza = zza(8, zzZ());
        IObjectWrapper zzM = IObjectWrapper.zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }

    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (Parcelable) bundle);
        zzb(2, zzZ);
    }

    public final void onDestroy() throws RemoteException {
        zzb(5, zzZ());
    }

    public final void onEnterAmbient(Bundle bundle) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (Parcelable) bundle);
        zzb(10, zzZ);
    }

    public final void onExitAmbient() throws RemoteException {
        zzb(11, zzZ());
    }

    public final void onLowMemory() throws RemoteException {
        zzb(6, zzZ());
    }

    public final void onPause() throws RemoteException {
        zzb(4, zzZ());
    }

    public final void onResume() throws RemoteException {
        zzb(3, zzZ());
    }

    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (Parcelable) bundle);
        Parcel zza = zza(7, zzZ);
        if (zza.readInt() != 0) {
            bundle.readFromParcel(zza);
        }
        zza.recycle();
    }

    public final void onStart() throws RemoteException {
        zzb(12, zzZ());
    }

    public final void onStop() throws RemoteException {
        zzb(13, zzZ());
    }
}
