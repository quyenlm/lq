package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzn implements Parcelable.Creator<DataUpdateNotification> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        DataType dataType = null;
        DataSource dataSource = null;
        int i = 0;
        long j = 0;
        long j2 = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 2:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    dataSource = (DataSource) zzb.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 5:
                    dataType = (DataType) zzb.zza(parcel, readInt, DataType.CREATOR);
                    break;
                case 1000:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new DataUpdateNotification(i2, j2, j, i, dataSource, dataType);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataUpdateNotification[i];
    }
}
