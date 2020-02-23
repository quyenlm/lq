package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzcod implements Parcelable.Creator<zzcoc> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        zzcoo zzcoo = null;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    zzcoo = (zzcoo) zzb.zza(parcel, readInt, zzcoo.CREATOR);
                    break;
                case 3:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcoc(str, zzcoo, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcoc[i];
    }
}
