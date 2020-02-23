package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzapn implements Parcelable.Creator<zzapl> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        long j = 0;
        int i = -1;
        int i2 = 0;
        boolean z = false;
        zzaow zzaow = null;
        String str = null;
        int i3 = 0;
        zzaoz zzaoz = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    zzaoz = (zzaoz) zzb.zza(parcel, readInt, zzaoz.CREATOR);
                    break;
                case 2:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    zzaow = (zzaow) zzb.zza(parcel, readInt, zzaow.CREATOR);
                    break;
                case 6:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 7:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzapl(zzaoz, j, i3, str, zzaow, z, i, i2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzapl[i];
    }
}
