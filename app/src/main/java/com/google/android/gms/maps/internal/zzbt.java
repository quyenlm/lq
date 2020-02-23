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
import com.google.android.gms.maps.StreetViewPanoramaOptions;

public final class zzbt extends zzed implements IStreetViewPanoramaFragmentDelegate {
    zzbt(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate");
    }

    public final IStreetViewPanoramaDelegate getStreetViewPanorama() throws RemoteException {
        IStreetViewPanoramaDelegate zzbs;
        Parcel zza = zza(1, zzZ());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzbs = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            zzbs = queryLocalInterface instanceof IStreetViewPanoramaDelegate ? (IStreetViewPanoramaDelegate) queryLocalInterface : new zzbs(readStrongBinder);
        }
        zza.recycle();
        return zzbs;
    }

    public final void getStreetViewPanoramaAsync(zzbn zzbn) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) zzbn);
        zzb(12, zzZ);
    }

    public final boolean isReady() throws RemoteException {
        Parcel zza = zza(11, zzZ());
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (Parcelable) bundle);
        zzb(3, zzZ);
    }

    public final IObjectWrapper onCreateView(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, Bundle bundle) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        zzef.zza(zzZ, (IInterface) iObjectWrapper2);
        zzef.zza(zzZ, (Parcelable) bundle);
        Parcel zza = zza(4, zzZ);
        IObjectWrapper zzM = IObjectWrapper.zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }

    public final void onDestroy() throws RemoteException {
        zzb(8, zzZ());
    }

    public final void onDestroyView() throws RemoteException {
        zzb(7, zzZ());
    }

    public final void onInflate(IObjectWrapper iObjectWrapper, StreetViewPanoramaOptions streetViewPanoramaOptions, Bundle bundle) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        zzef.zza(zzZ, (Parcelable) streetViewPanoramaOptions);
        zzef.zza(zzZ, (Parcelable) bundle);
        zzb(2, zzZ);
    }

    public final void onLowMemory() throws RemoteException {
        zzb(9, zzZ());
    }

    public final void onPause() throws RemoteException {
        zzb(6, zzZ());
    }

    public final void onResume() throws RemoteException {
        zzb(5, zzZ());
    }

    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (Parcelable) bundle);
        Parcel zza = zza(10, zzZ);
        if (zza.readInt() != 0) {
            bundle.readFromParcel(zza);
        }
        zza.recycle();
    }
}
