package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class zzayg implements Parcelable.Creator<zzayf> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        ApplicationMetadata applicationMetadata = null;
        int i = 0;
        int i2 = 0;
        boolean z = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    d = zzb.zzn(parcel, readInt);
                    break;
                case 3:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    applicationMetadata = (ApplicationMetadata) zzb.zza(parcel, readInt, ApplicationMetadata.CREATOR);
                    break;
                case 6:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzayf(d, z, i2, applicationMetadata, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzayf[i];
    }
}
