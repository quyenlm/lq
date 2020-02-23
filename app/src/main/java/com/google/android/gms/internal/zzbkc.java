package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbkc implements Parcelable.Creator<zzbkb> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        int i2 = 0;
        String str = null;
        String str2 = null;
        int i3 = 0;
        int i4 = 0;
        String str3 = null;
        int i5 = 0;
        String str4 = null;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    i5 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                case 7:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 10:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 11:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbkb(str5, str4, i5, str3, i4, i3, str2, str, i2, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbkb[i];
    }
}
