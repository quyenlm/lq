package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public abstract class zzaps extends zzee implements zzapr {
    public zzaps() {
        attachInterface(this, "com.google.android.gms.appdatasearch.internal.ILightweightAppDataSearchCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza((Status) zzef.zza(parcel, Status.CREATOR));
                break;
            case 2:
                zzef.zza(parcel, Status.CREATOR);
                zzef.zza(parcel, ParcelFileDescriptor.CREATOR);
                break;
            case 3:
                zzef.zza(parcel, Status.CREATOR);
                zzef.zza(parcel);
                break;
            case 4:
                zzef.zza(parcel, zzapf.CREATOR);
                break;
            default:
                return false;
        }
        return true;
    }
}
