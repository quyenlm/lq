package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzaup extends zzee implements zzauo {
    public zzaup() {
        attachInterface(this, "com.google.android.gms.cast.framework.internal.IMediaRouter");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzauq zzaur;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                Bundle bundle = (Bundle) zzef.zza(parcel, Bundle.CREATOR);
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    zzaur = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.cast.framework.internal.IMediaRouterCallback");
                    zzaur = queryLocalInterface instanceof zzauq ? (zzauq) queryLocalInterface : new zzaur(readStrongBinder);
                }
                zza(bundle, zzaur);
                parcel2.writeNoException();
                break;
            case 2:
                zza((Bundle) zzef.zza(parcel, Bundle.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                break;
            case 3:
                zzk((Bundle) zzef.zza(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                break;
            case 4:
                boolean zzb = zzb((Bundle) zzef.zza(parcel, Bundle.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                zzef.zza(parcel2, zzb);
                break;
            case 5:
                zzce(parcel.readString());
                parcel2.writeNoException();
                break;
            case 6:
                zznI();
                parcel2.writeNoException();
                break;
            case 7:
                boolean zznJ = zznJ();
                parcel2.writeNoException();
                zzef.zza(parcel2, zznJ);
                break;
            case 8:
                Bundle zzcf = zzcf(parcel.readString());
                parcel2.writeNoException();
                zzef.zzb(parcel2, zzcf);
                break;
            case 9:
                String zznK = zznK();
                parcel2.writeNoException();
                parcel2.writeString(zznK);
                break;
            case 10:
                parcel2.writeNoException();
                parcel2.writeInt(11020208);
                break;
            default:
                return false;
        }
        return true;
    }
}
