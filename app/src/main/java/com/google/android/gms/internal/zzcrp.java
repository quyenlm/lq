package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzcri;
import java.util.HashSet;

public final class zzcrp implements Parcelable.Creator<zzcri.zzd> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    hashSet.add(1);
                    break;
                case 2:
                    str6 = zzb.zzq(parcel, readInt);
                    hashSet.add(2);
                    break;
                case 3:
                    str5 = zzb.zzq(parcel, readInt);
                    hashSet.add(3);
                    break;
                case 4:
                    str4 = zzb.zzq(parcel, readInt);
                    hashSet.add(4);
                    break;
                case 5:
                    str3 = zzb.zzq(parcel, readInt);
                    hashSet.add(5);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, readInt);
                    hashSet.add(6);
                    break;
                case 7:
                    str = zzb.zzq(parcel, readInt);
                    hashSet.add(7);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        if (parcel.dataPosition() == zzd) {
            return new zzcri.zzd(hashSet, i, str6, str5, str4, str3, str2, str);
        }
        throw new zzc(new StringBuilder(37).append("Overread allowed size end=").append(zzd).toString(), parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcri.zzd[i];
    }
}
