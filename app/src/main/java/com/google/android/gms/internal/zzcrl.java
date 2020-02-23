package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzcri;
import java.util.HashSet;

public final class zzcrl implements Parcelable.Creator<zzcri.zzb> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        zzcri.zzb.C0036zzb zzb = null;
        zzcri.zzb.zza zza = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i2 = zzb.zzg(parcel, readInt);
                    hashSet.add(1);
                    break;
                case 2:
                    hashSet.add(2);
                    zza = (zzcri.zzb.zza) zzb.zza(parcel, readInt, zzcri.zzb.zza.CREATOR);
                    break;
                case 3:
                    hashSet.add(3);
                    zzb = (zzcri.zzb.C0036zzb) zzb.zza(parcel, readInt, zzcri.zzb.C0036zzb.CREATOR);
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
            return new zzcri.zzb(hashSet, i2, zza, zzb, i);
        }
        throw new zzc(new StringBuilder(37).append("Overread allowed size end=").append(zzd).toString(), parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcri.zzb[i];
    }
}
