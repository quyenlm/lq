package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzcel implements Parcelable.Creator<zzcek> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        zzcji zzcji = null;
        long j = 0;
        boolean z = false;
        String str3 = null;
        zzcez zzcez = null;
        long j2 = 0;
        zzcez zzcez2 = null;
        long j3 = 0;
        zzcez zzcez3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    zzcji = (zzcji) zzb.zza(parcel, readInt, zzcji.CREATOR);
                    break;
                case 5:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 6:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 7:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    zzcez = (zzcez) zzb.zza(parcel, readInt, zzcez.CREATOR);
                    break;
                case 9:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 10:
                    zzcez2 = (zzcez) zzb.zza(parcel, readInt, zzcez.CREATOR);
                    break;
                case 11:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 12:
                    zzcez3 = (zzcez) zzb.zza(parcel, readInt, zzcez.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcek(i, str, str2, zzcji, j, z, str3, zzcez, j2, zzcez2, j3, zzcez3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcek[i];
    }
}
