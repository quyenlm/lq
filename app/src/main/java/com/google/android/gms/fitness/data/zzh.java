package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzh implements Parcelable.Creator<DataPoint> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        DataSource dataSource = null;
        long j = 0;
        long j2 = 0;
        Value[] valueArr = null;
        DataSource dataSource2 = null;
        long j3 = 0;
        long j4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    dataSource = (DataSource) zzb.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 3:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 5:
                    valueArr = (Value[]) zzb.zzb(parcel, readInt, Value.CREATOR);
                    break;
                case 6:
                    dataSource2 = (DataSource) zzb.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 7:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 8:
                    j4 = zzb.zzi(parcel, readInt);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new DataPoint(i, dataSource, j, j2, valueArr, dataSource2, j3, j4);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataPoint[i];
    }
}
