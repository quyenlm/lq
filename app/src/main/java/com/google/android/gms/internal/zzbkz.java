package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.zzc;

public final class zzbkz implements Parcelable.Creator<zzbky> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        Boolean bool = null;
        zzc zzc = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    zzc = (zzc) zzb.zza(parcel, readInt, zzc.CREATOR);
                    break;
                case 3:
                    bool = zzb.zzd(parcel, readInt);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbky(zzc, bool, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbky[i];
    }
}
