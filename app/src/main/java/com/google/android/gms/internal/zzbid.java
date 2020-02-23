package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import java.util.Map;

public abstract class zzbid extends zzee implements zzbic {
    public zzbid() {
        attachInterface(this, "com.google.android.gms.config.internal.IConfigCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza((Status) zzef.zza(parcel, Status.CREATOR), parcel.createByteArray());
                break;
            case 2:
                zza((Status) zzef.zza(parcel, Status.CREATOR), (Map) zzef.zzc(parcel));
                break;
            case 3:
                zzz((Status) zzef.zza(parcel, Status.CREATOR));
                break;
            case 4:
                zza((Status) zzef.zza(parcel, Status.CREATOR), (zzbia) zzef.zza(parcel, zzbia.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
