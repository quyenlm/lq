package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;

public final class zzav implements Parcelable.Creator<SessionReadRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        long j = 0;
        long j2 = 0;
        ArrayList<DataType> arrayList = null;
        ArrayList<DataSource> arrayList2 = null;
        boolean z = false;
        boolean z2 = false;
        ArrayList<String> arrayList3 = null;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 5:
                    arrayList = zzb.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 6:
                    arrayList2 = zzb.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 7:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 8:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 9:
                    arrayList3 = zzb.zzC(parcel, readInt);
                    break;
                case 10:
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
        return new SessionReadRequest(i, str, str2, j, j2, arrayList, arrayList2, z, z2, arrayList3, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SessionReadRequest[i];
    }
}
