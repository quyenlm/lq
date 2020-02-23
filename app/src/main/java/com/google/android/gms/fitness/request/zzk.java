package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataSet;

public final class zzk implements Parcelable.Creator<zzj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        IBinder iBinder = null;
        DataSet dataSet = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    dataSet = (DataSet) zzb.zza(parcel, readInt, DataSet.CREATOR);
                    break;
                case 2:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 4:
                    z = zzb.zzc(parcel, readInt);
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
        return new zzj(i, dataSet, iBinder, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzj[i];
    }
}
