package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzcri;
import java.util.HashSet;

public final class zzcrq implements Parcelable.Creator<zzcri.zze> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        String str = null;
        String str2 = null;
        boolean z = false;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i2 = zzb.zzg(parcel, readInt);
                    hashSet.add(1);
                    break;
                case 2:
                    str7 = zzb.zzq(parcel, readInt);
                    hashSet.add(2);
                    break;
                case 3:
                    str6 = zzb.zzq(parcel, readInt);
                    hashSet.add(3);
                    break;
                case 4:
                    str5 = zzb.zzq(parcel, readInt);
                    hashSet.add(4);
                    break;
                case 5:
                    str4 = zzb.zzq(parcel, readInt);
                    hashSet.add(5);
                    break;
                case 6:
                    str3 = zzb.zzq(parcel, readInt);
                    hashSet.add(6);
                    break;
                case 7:
                    z = zzb.zzc(parcel, readInt);
                    hashSet.add(7);
                    break;
                case 8:
                    str2 = zzb.zzq(parcel, readInt);
                    hashSet.add(8);
                    break;
                case 9:
                    str = zzb.zzq(parcel, readInt);
                    hashSet.add(9);
                    break;
                case 10:
                    i = zzb.zzg(parcel, readInt);
                    hashSet.add(10);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        if (parcel.dataPosition() == zzd) {
            return new zzcri.zze(hashSet, i2, str7, str6, str5, str4, str3, z, str2, str, i);
        }
        throw new zzc(new StringBuilder(37).append("Overread allowed size end=").append(zzd).toString(), parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcri.zze[i];
    }
}
