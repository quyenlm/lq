package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzcow implements Parcelable.Creator<zzcov> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        zzcoo zzcoo = null;
        String[] strArr = null;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 2:
                    strArr = zzb.zzA(parcel, readInt);
                    break;
                case 3:
                    zzcoo = (zzcoo) zzb.zza(parcel, readInt, zzcoo.CREATOR);
                    break;
                case 4:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcov(iBinder, strArr, zzcoo, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcov[i];
    }
}
