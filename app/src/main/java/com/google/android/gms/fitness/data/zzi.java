package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

public final class zzi implements Parcelable.Creator<DataSet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        ArrayList<DataSource> arrayList2 = null;
        DataType dataType = null;
        DataSource dataSource = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    dataSource = (DataSource) zzb.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 2:
                    dataType = (DataType) zzb.zza(parcel, readInt, DataType.CREATOR);
                    break;
                case 3:
                    zzb.zza(parcel, readInt, (List) arrayList, getClass().getClassLoader());
                    break;
                case 4:
                    arrayList2 = zzb.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 5:
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
        return new DataSet(i, dataSource, dataType, arrayList, arrayList2, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataSet[i];
    }
}
