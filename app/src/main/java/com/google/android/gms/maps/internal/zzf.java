package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.model.internal.zza;
import com.google.android.gms.maps.model.internal.zzb;

public final class zzf extends zzed implements zze {
    zzf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.ICreator");
    }

    public final IMapFragmentDelegate zzH(IObjectWrapper iObjectWrapper) throws RemoteException {
        IMapFragmentDelegate zzj;
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        Parcel zza = zza(2, zzZ);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzj = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
            zzj = queryLocalInterface instanceof IMapFragmentDelegate ? (IMapFragmentDelegate) queryLocalInterface : new zzj(readStrongBinder);
        }
        zza.recycle();
        return zzj;
    }

    public final IStreetViewPanoramaFragmentDelegate zzI(IObjectWrapper iObjectWrapper) throws RemoteException {
        IStreetViewPanoramaFragmentDelegate zzbt;
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        Parcel zza = zza(8, zzZ);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzbt = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate");
            zzbt = queryLocalInterface instanceof IStreetViewPanoramaFragmentDelegate ? (IStreetViewPanoramaFragmentDelegate) queryLocalInterface : new zzbt(readStrongBinder);
        }
        zza.recycle();
        return zzbt;
    }

    public final IMapViewDelegate zza(IObjectWrapper iObjectWrapper, GoogleMapOptions googleMapOptions) throws RemoteException {
        IMapViewDelegate zzk;
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        zzef.zza(zzZ, (Parcelable) googleMapOptions);
        Parcel zza = zza(3, zzZ);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzk = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
            zzk = queryLocalInterface instanceof IMapViewDelegate ? (IMapViewDelegate) queryLocalInterface : new zzk(readStrongBinder);
        }
        zza.recycle();
        return zzk;
    }

    public final IStreetViewPanoramaViewDelegate zza(IObjectWrapper iObjectWrapper, StreetViewPanoramaOptions streetViewPanoramaOptions) throws RemoteException {
        IStreetViewPanoramaViewDelegate zzbu;
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        zzef.zza(zzZ, (Parcelable) streetViewPanoramaOptions);
        Parcel zza = zza(7, zzZ);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzbu = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
            zzbu = queryLocalInterface instanceof IStreetViewPanoramaViewDelegate ? (IStreetViewPanoramaViewDelegate) queryLocalInterface : new zzbu(readStrongBinder);
        }
        zza.recycle();
        return zzbu;
    }

    public final void zzi(IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        zzZ.writeInt(i);
        zzb(6, zzZ);
    }

    public final ICameraUpdateFactoryDelegate zzwh() throws RemoteException {
        ICameraUpdateFactoryDelegate zzb;
        Parcel zza = zza(4, zzZ());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzb = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
            zzb = queryLocalInterface instanceof ICameraUpdateFactoryDelegate ? (ICameraUpdateFactoryDelegate) queryLocalInterface : new zzb(readStrongBinder);
        }
        zza.recycle();
        return zzb;
    }

    public final zza zzwi() throws RemoteException {
        Parcel zza = zza(5, zzZ());
        zza zzaa = zzb.zzaa(zza.readStrongBinder());
        zza.recycle();
        return zzaa;
    }
}
