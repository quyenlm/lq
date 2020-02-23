package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import java.util.ArrayList;

public final class zzi implements Parcelable.Creator<DataDeleteRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        long j2 = 0;
        ArrayList<DataSource> arrayList = null;
        ArrayList<DataType> arrayList2 = null;
        ArrayList<Session> arrayList3 = null;
        boolean z = false;
        boolean z2 = false;
        IBinder iBinder = null;
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
                    arrayList = zzb.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 4:
                    arrayList2 = zzb.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 5:
                    arrayList3 = zzb.zzc(parcel, readInt, Session.CREATOR);
                    break;
                case 6:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 7:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 8:
                    iBinder = zzb.zzr(parcel, readInt);
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
        return new DataDeleteRequest(i, j, j2, arrayList, arrayList2, arrayList3, z, z2, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataDeleteRequest[i];
    }
}
