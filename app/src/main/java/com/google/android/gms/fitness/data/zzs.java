package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.Goal;
import java.util.ArrayList;
import java.util.List;

public final class zzs implements Parcelable.Creator<Goal> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        long j2 = 0;
        ArrayList arrayList = new ArrayList();
        Goal.Recurrence recurrence = null;
        int i2 = 0;
        Goal.MetricObjective metricObjective = null;
        Goal.DurationObjective durationObjective = null;
        Goal.FrequencyObjective frequencyObjective = null;
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
                    zzb.zza(parcel, readInt, (List) arrayList, getClass().getClassLoader());
                    break;
                case 4:
                    recurrence = (Goal.Recurrence) zzb.zza(parcel, readInt, Goal.Recurrence.CREATOR);
                    break;
                case 5:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    metricObjective = (Goal.MetricObjective) zzb.zza(parcel, readInt, Goal.MetricObjective.CREATOR);
                    break;
                case 7:
                    durationObjective = (Goal.DurationObjective) zzb.zza(parcel, readInt, Goal.DurationObjective.CREATOR);
                    break;
                case 8:
                    frequencyObjective = (Goal.FrequencyObjective) zzb.zza(parcel, readInt, Goal.FrequencyObjective.CREATOR);
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
        return new Goal(i, j, j2, arrayList, recurrence, i2, metricObjective, durationObjective, frequencyObjective);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Goal[i];
    }
}
