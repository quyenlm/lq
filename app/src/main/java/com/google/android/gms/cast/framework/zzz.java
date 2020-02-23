package com.google.android.gms.cast.framework;

import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzz extends zzee implements zzy {
    public zzz() {
        attachInterface(this, "com.google.android.gms.cast.framework.ISessionManagerListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                IObjectWrapper zznn = zznn();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zznn);
                return true;
            case 2:
                zzz(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 3:
                zzc(IObjectWrapper.zza.zzM(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 4:
                zze(IObjectWrapper.zza.zzM(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 5:
                zzA(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 6:
                zzf(IObjectWrapper.zza.zzM(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 7:
                zzd(IObjectWrapper.zza.zzM(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 8:
                zza(IObjectWrapper.zza.zzM(parcel.readStrongBinder()), zzef.zza(parcel));
                parcel2.writeNoException();
                return true;
            case 9:
                zzg(IObjectWrapper.zza.zzM(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 10:
                zzh(IObjectWrapper.zza.zzM(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 11:
                parcel2.writeNoException();
                parcel2.writeInt(11020208);
                return true;
            default:
                return false;
        }
    }
}
