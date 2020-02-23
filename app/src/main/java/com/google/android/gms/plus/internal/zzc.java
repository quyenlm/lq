package com.google.android.gms.plus.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzbgt;
import com.google.android.gms.internal.zzcri;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzc extends zzee implements zzb {
    public zzc() {
        attachInterface(this, "com.google.android.gms.plus.internal.IPlusCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                parcel.readInt();
                zzef.zza(parcel, Bundle.CREATOR);
                zzef.zza(parcel, Bundle.CREATOR);
                break;
            case 2:
                parcel.readInt();
                zzef.zza(parcel, Bundle.CREATOR);
                zzef.zza(parcel, ParcelFileDescriptor.CREATOR);
                break;
            case 3:
                parcel.readString();
                break;
            case 4:
                zza((DataHolder) zzef.zza(parcel, DataHolder.CREATOR), parcel.readString());
                break;
            case 5:
                parcel.readInt();
                zzef.zza(parcel, Bundle.CREATOR);
                zzef.zza(parcel, zzbgt.CREATOR);
                break;
            case 6:
                zzef.zza(parcel, DataHolder.CREATOR);
                parcel.readString();
                parcel.readString();
                break;
            case 7:
                zzf(parcel.readInt(), (Bundle) zzef.zza(parcel, Bundle.CREATOR));
                break;
            case 8:
                parcel.readString();
                break;
            case 9:
                parcel.readInt();
                zzef.zza(parcel, zzcri.CREATOR);
                break;
            case 10:
                zzef.zza(parcel, Status.CREATOR);
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
