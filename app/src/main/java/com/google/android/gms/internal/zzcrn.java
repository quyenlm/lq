package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzcri;
import java.util.HashSet;

public final class zzcrn implements Parcelable.Creator<zzcri.zzb.C0036zzb> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        HashSet hashSet = new HashSet();
        String str = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i3 = zzb.zzg(parcel, readInt);
                    hashSet.add(1);
                    break;
                case 2:
                    i2 = zzb.zzg(parcel, readInt);
                    hashSet.add(2);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    hashSet.add(3);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    hashSet.add(4);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        if (parcel.dataPosition() == zzd) {
            return new zzcri.zzb.C0036zzb(hashSet, i3, i2, str, i);
        }
        throw new zzc(new StringBuilder(37).append("Overread allowed size end=").append(zzd).toString(), parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcri.zzb.C0036zzb[i];
    }
}
