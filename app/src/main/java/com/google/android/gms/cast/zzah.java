package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzah implements Parcelable.Creator<MediaTrack> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        long j = 0;
        String str = null;
        int i = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 9:
                    str = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new MediaTrack(j, i2, str5, str4, str3, str2, i, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MediaTrack[i];
    }
}
