package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;
import com.google.android.gms.maps.model.internal.zzq;

public abstract class zzi extends zzee implements zzh {
    public zzi() {
        attachInterface(this, "com.google.android.gms.maps.internal.IInfoWindowAdapter");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                IObjectWrapper zzh = zzh(zzq.zzaf(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzh);
                return true;
            case 2:
                IObjectWrapper zzi = zzi(zzq.zzaf(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzi);
                return true;
            default:
                return false;
        }
    }
}
