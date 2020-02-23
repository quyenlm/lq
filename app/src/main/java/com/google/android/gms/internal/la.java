package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class la implements Parcelable.Creator<kz> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str6 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    str = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new kz(str6, str5, str4, str3, str2, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new kz[i];
    }
}
