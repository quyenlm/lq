package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzcri;
import java.util.HashSet;

public final class zzcrs implements Parcelable.Creator<zzcri.zzg> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        String str = null;
        int i2 = 0;
        String str2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i3 = zzb.zzg(parcel, readInt);
                    hashSet.add(1);
                    break;
                case 3:
                    i = zzb.zzg(parcel, readInt);
                    hashSet.add(3);
                    break;
                case 4:
                    str = zzb.zzq(parcel, readInt);
                    hashSet.add(4);
                    break;
                case 5:
                    str2 = zzb.zzq(parcel, readInt);
                    hashSet.add(5);
                    break;
                case 6:
                    i2 = zzb.zzg(parcel, readInt);
                    hashSet.add(6);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        if (parcel.dataPosition() == zzd) {
            return new zzcri.zzg(hashSet, i3, str2, i2, str, i);
        }
        throw new zzc(new StringBuilder(37).append("Overread allowed size end=").append(zzd).toString(), parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcri.zzg[i];
    }
}
