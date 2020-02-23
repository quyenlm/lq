package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzz implements Parcelable.Creator<RawDataPoint> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        long j2 = 0;
        Value[] valueArr = null;
        int i2 = 0;
        int i3 = 0;
        long j3 = 0;
        long j4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 2:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    valueArr = (Value[]) zzb.zzb(parcel, readInt, Value.CREATOR);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 7:
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
        return new RawDataPoint(i, j, j2, valueArr, i2, i3, j3, j4);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new RawDataPoint[i];
    }
}
