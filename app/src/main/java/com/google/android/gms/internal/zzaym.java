package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.ApplicationMetadata;

public abstract class zzaym extends zzee implements zzayl {
    public zzaym() {
        attachInterface(this, "com.google.android.gms.cast.internal.ICastDeviceControllerListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zzae(parcel.readInt());
                break;
            case 2:
                zza((ApplicationMetadata) zzef.zza(parcel, ApplicationMetadata.CREATOR), parcel.readString(), parcel.readString(), zzef.zza(parcel));
                break;
            case 3:
                zzZ(parcel.readInt());
                break;
            case 4:
                zza(parcel.readString(), parcel.readDouble(), zzef.zza(parcel));
                break;
            case 5:
                zzu(parcel.readString(), parcel.readString());
                break;
            case 6:
                zza(parcel.readString(), parcel.createByteArray());
                break;
            case 7:
                zzag(parcel.readInt());
                break;
            case 8:
                zzaf(parcel.readInt());
                break;
            case 9:
                onApplicationDisconnected(parcel.readInt());
                break;
            case 10:
                zza(parcel.readString(), parcel.readLong(), parcel.readInt());
                break;
            case 11:
                zzb(parcel.readString(), parcel.readLong());
                break;
            case 12:
                zzb((zzaxq) zzef.zza(parcel, zzaxq.CREATOR));
                break;
            case 13:
                zzb((zzayf) zzef.zza(parcel, zzayf.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
