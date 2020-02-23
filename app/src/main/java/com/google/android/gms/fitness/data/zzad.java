package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzad implements Parcelable.Creator<Session> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        long j2 = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        int i2 = 0;
        zzb zzb = null;
        Long l = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 2:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    zzb = (zzb) zzb.zza(parcel, readInt, zzb.CREATOR);
                    break;
                case 9:
                    l = zzb.zzj(parcel, readInt);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new Session(i, j, j2, str, str2, str3, i2, zzb, l);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Session[i];
    }
}
