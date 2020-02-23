package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzb extends zzee implements zza {
    public static zza zzaa(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        return queryLocalInterface instanceof zza ? (zza) queryLocalInterface : new zzc(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                IObjectWrapper zzbo = zzbo(parcel.readInt());
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzbo);
                break;
            case 2:
                IObjectWrapper zzdC = zzdC(parcel.readString());
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzdC);
                break;
            case 3:
                IObjectWrapper zzdD = zzdD(parcel.readString());
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzdD);
                break;
            case 4:
                IObjectWrapper zzwl = zzwl();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzwl);
                break;
            case 5:
                IObjectWrapper zze = zze(parcel.readFloat());
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zze);
                break;
            case 6:
                IObjectWrapper zzd = zzd((Bitmap) zzef.zza(parcel, Bitmap.CREATOR));
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzd);
                break;
            case 7:
                IObjectWrapper zzdE = zzdE(parcel.readString());
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzdE);
                break;
            default:
                return false;
        }
        return true;
    }
}
