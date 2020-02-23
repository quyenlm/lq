package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class zzcds implements Parcelable.Creator<zzcdr> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        int i = 0;
        short s = 0;
        double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        double d2 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        float f = 0.0f;
        long j = 0;
        int i2 = 0;
        int i3 = -1;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    s = zzb.zzf(parcel, readInt);
                    break;
                case 4:
                    d = zzb.zzn(parcel, readInt);
                    break;
                case 5:
                    d2 = zzb.zzn(parcel, readInt);
                    break;
                case 6:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case 7:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 9:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcdr(str, i, s, d, d2, f, j, i2, i3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcdr[i];
    }
}
