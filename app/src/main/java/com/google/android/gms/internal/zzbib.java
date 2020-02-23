package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbib implements Parcelable.Creator<zzbia> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        DataHolder dataHolder = null;
        DataHolder dataHolder2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    dataHolder2 = (DataHolder) zzb.zza(parcel, readInt, DataHolder.CREATOR);
                    break;
                case 4:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 5:
                    dataHolder = (DataHolder) zzb.zza(parcel, readInt, DataHolder.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbia(i, dataHolder2, j, dataHolder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbia[i];
    }
}
