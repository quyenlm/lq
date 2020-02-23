package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbpt implements Parcelable.Creator<zzbps> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        zzbqg zzbqg = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    zzbqg = (zzbqg) zzb.zza(parcel, readInt, zzbqg.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbps(zzbqg);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbps[i];
    }
}
