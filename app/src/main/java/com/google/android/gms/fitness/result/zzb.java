package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataSet;

public final class zzb implements Parcelable.Creator<DailyTotalResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel);
        int i = 0;
        DataSet dataSet = null;
        Status status = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    status = (Status) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Status.CREATOR);
                    break;
                case 2:
                    dataSet = (DataSet) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, DataSet.CREATOR);
                    break;
                case 1000:
                    i = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, readInt);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzF(parcel, zzd);
        return new DailyTotalResult(i, status, dataSet);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DailyTotalResult[i];
    }
}
