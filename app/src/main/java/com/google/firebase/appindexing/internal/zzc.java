package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc implements Parcelable.Creator<zza> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        zzb zzb = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    zzb = (zzb) zzb.zza(parcel, readInt, zzb.CREATOR);
                    break;
                case 6:
                    str = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zza(str5, str4, str3, str2, zzb, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zza[i];
    }
}
