package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.zzm;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzaz extends zzee implements zzay {
    public static zzay zzJ(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGoogleCertificatesApi");
        return queryLocalInterface instanceof zzay ? (zzay) queryLocalInterface : new zzba(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                IObjectWrapper zzrF = zzrF();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzrF);
                break;
            case 2:
                IObjectWrapper zzrG = zzrG();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzrG);
                break;
            case 3:
                boolean zze = zze(parcel.readString(), IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzef.zza(parcel2, zze);
                break;
            case 4:
                boolean zzf = zzf(parcel.readString(), IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzef.zza(parcel2, zzf);
                break;
            case 5:
                boolean zza = zza((zzm) zzef.zza(parcel, zzm.CREATOR), IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzef.zza(parcel2, zza);
                break;
            default:
                return false;
        }
        return true;
    }
}
