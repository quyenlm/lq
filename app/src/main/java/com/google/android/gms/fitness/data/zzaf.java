package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzaf implements Parcelable.Creator<zzae> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        DataSet dataSet = null;
        Session session = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    session = (Session) zzb.zza(parcel, readInt, Session.CREATOR);
                    break;
                case 2:
                    dataSet = (DataSet) zzb.zza(parcel, readInt, DataSet.CREATOR);
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
        return new zzae(i, session, dataSet);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzae[i];
    }
}
