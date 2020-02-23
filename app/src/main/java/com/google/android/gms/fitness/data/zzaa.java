package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzaa implements Parcelable.Creator<RawDataSet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<RawDataPoint> arrayList = null;
        boolean z = false;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, readInt, RawDataPoint.CREATOR);
                    break;
                case 4:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 1000:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new RawDataSet(i3, i2, i, arrayList, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new RawDataSet[i];
    }
}
