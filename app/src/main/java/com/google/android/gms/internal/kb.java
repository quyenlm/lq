package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.PhoneAuthCredential;

public abstract class kb extends zzee implements ka {
    public kb() {
        attachInterface(this, "com.google.firebase.auth.api.internal.IFirebaseAuthCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zzb((kx) zzef.zza(parcel, kx.CREATOR));
                break;
            case 2:
                zza((kx) zzef.zza(parcel, kx.CREATOR), (kv) zzef.zza(parcel, kv.CREATOR));
                break;
            case 3:
                zza((kt) zzef.zza(parcel, kt.CREATOR));
                break;
            case 4:
                zza((ld) zzef.zza(parcel, ld.CREATOR));
                break;
            case 5:
                onFailure((Status) zzef.zza(parcel, Status.CREATOR));
                break;
            case 6:
                zzEN();
                break;
            case 7:
                zzEO();
                break;
            case 8:
                zzgq(parcel.readString());
                break;
            case 9:
                zzgr(parcel.readString());
                break;
            case 10:
                onVerificationCompleted((PhoneAuthCredential) zzef.zza(parcel, PhoneAuthCredential.CREATOR));
                break;
            case 11:
                zzgs(parcel.readString());
                break;
            default:
                return false;
        }
        return true;
    }
}
