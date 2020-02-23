package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;
import java.util.List;

public final class zzc implements Parcelable.Creator<DataReadResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList<DataType> arrayList3 = null;
        ArrayList<DataSource> arrayList4 = null;
        int i = 0;
        Status status = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    zzb.zza(parcel, readInt, (List) arrayList, getClass().getClassLoader());
                    break;
                case 2:
                    status = (Status) zzb.zza(parcel, readInt, Status.CREATOR);
                    break;
                case 3:
                    zzb.zza(parcel, readInt, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 5:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    arrayList4 = zzb.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 7:
                    arrayList3 = zzb.zzc(parcel, readInt, DataType.CREATOR);
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
        return new DataReadResult(i2, arrayList, status, arrayList2, i, arrayList4, arrayList3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataReadResult[i];
    }
}
