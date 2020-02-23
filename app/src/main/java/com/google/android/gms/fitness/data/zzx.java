package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.Goal;

public final class zzx implements Parcelable.Creator<Goal.MetricObjective> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        String str = null;
        double d = 0.0d;
        double d2 = 0.0d;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    d2 = zzb.zzn(parcel, readInt);
                    break;
                case 3:
                    d = zzb.zzn(parcel, readInt);
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
        return new Goal.MetricObjective(i, str, d2, d);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Goal.MetricObjective[i];
    }
}
