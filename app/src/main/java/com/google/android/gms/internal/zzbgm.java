package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbgm implements Parcelable.Creator<zzbgj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        zzbgc zzbgc = null;
        String str = null;
        int i = 0;
        String str2 = null;
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        int i4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    zzbgc = (zzbgc) zzb.zza(parcel, readInt, zzbgc.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbgj(i4, i3, z2, i2, z, str2, i, str, zzbgc);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbgj[i];
    }
}
