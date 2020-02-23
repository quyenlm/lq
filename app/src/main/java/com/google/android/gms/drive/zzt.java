package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzt implements Parcelable.Creator<zzs> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        int i = 0;
        String str = null;
        String str2 = null;
        int i2 = 0;
        String str3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 7:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzs(str3, i2, str2, str, i, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzs[i];
    }
}
