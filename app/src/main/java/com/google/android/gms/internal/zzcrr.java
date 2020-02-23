package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzcri;
import java.util.HashSet;

public final class zzcrr implements Parcelable.Creator<zzcri.zzf> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        HashSet hashSet = new HashSet();
        String str = null;
        boolean z = false;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    int zzg = zzb.zzg(parcel, readInt);
                    hashSet.add(1);
                    i = zzg;
                    break;
                case 2:
                    z = zzb.zzc(parcel, readInt);
                    hashSet.add(2);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    hashSet.add(3);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        if (parcel.dataPosition() == zzd) {
            return new zzcri.zzf(hashSet, i, z, str);
        }
        throw new zzc(new StringBuilder(37).append("Overread allowed size end=").append(zzd).toString(), parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcri.zzf[i];
    }
}
