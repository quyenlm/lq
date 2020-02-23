package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class zzf implements Parcelable.Creator<zze> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    d = zzb.zzn(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zze(i2, i, d);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zze[i];
    }
}
