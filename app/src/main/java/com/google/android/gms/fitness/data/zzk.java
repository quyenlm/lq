package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzk implements Parcelable.Creator<DataSource> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int[] iArr = null;
        String str = null;
        zzb zzb = null;
        Device device = null;
        int i = 0;
        String str2 = null;
        DataType dataType = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    dataType = (DataType) zzb.zza(parcel, readInt, DataType.CREATOR);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    device = (Device) zzb.zza(parcel, readInt, Device.CREATOR);
                    break;
                case 5:
                    zzb = (zzb) zzb.zza(parcel, readInt, zzb.CREATOR);
                    break;
                case 6:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    iArr = zzb.zzw(parcel, readInt);
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
        return new DataSource(i2, dataType, str2, i, device, zzb, str, iArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataSource[i];
    }
}
