package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzy implements Parcelable.Creator<RawBucket> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        long j2 = 0;
        Session session = null;
        int i2 = 0;
        ArrayList<RawDataSet> arrayList = null;
        int i3 = 0;
        boolean z = false;
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
                    session = (Session) zzb.zza(parcel, readInt, Session.CREATOR);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    arrayList = zzb.zzc(parcel, readInt, RawDataSet.CREATOR);
                    break;
                case 6:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 7:
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
        return new RawBucket(i, j, j2, session, i2, arrayList, i3, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new RawBucket[i];
    }
}
