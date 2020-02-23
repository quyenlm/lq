package com.google.android.gms.cast.framework;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzj extends zzee implements zzi {
    public zzj() {
        attachInterface(this, "com.google.android.gms.cast.framework.ICastConnectionController");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zzt(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                break;
            case 2:
                zza(parcel.readString(), (LaunchOptions) zzef.zza(parcel, LaunchOptions.CREATOR));
                parcel2.writeNoException();
                break;
            case 3:
                zzcc(parcel.readString());
                parcel2.writeNoException();
                break;
            case 4:
                zzY(parcel.readInt());
                parcel2.writeNoException();
                break;
            case 5:
                parcel2.writeNoException();
                parcel2.writeInt(11020208);
                break;
            default:
                return false;
        }
        return true;
    }
}
