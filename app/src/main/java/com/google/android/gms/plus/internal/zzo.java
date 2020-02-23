package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzo implements Parcelable.Creator<zzn> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        PlusCommonExtras plusCommonExtras = null;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String[] strArr = null;
        String[] strArr2 = null;
        String[] strArr3 = null;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    strArr3 = zzb.zzA(parcel, readInt);
                    break;
                case 3:
                    strArr2 = zzb.zzA(parcel, readInt);
                    break;
                case 4:
                    strArr = zzb.zzA(parcel, readInt);
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
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    plusCommonExtras = (PlusCommonExtras) zzb.zza(parcel, readInt, PlusCommonExtras.CREATOR);
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
        return new zzn(i, str5, strArr3, strArr2, strArr, str4, str3, str2, str, plusCommonExtras);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzn[i];
    }
}
