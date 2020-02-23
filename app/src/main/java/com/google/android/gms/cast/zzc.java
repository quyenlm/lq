package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc implements Parcelable.Creator<AdBreakStatus> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        long j = 0;
        String str = null;
        String str2 = null;
        long j2 = 0;
        long j3 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    j = zzb.zzi(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new AdBreakStatus(j3, j2, str2, str, j);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new AdBreakStatus[i];
    }
}
