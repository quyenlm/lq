package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

public final class zzac implements Parcelable.Creator<GoalsReadRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        IBinder iBinder = null;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 2:
                    zzb.zza(parcel, readInt, (List) arrayList, getClass().getClassLoader());
                    break;
                case 3:
                    zzb.zza(parcel, readInt, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 4:
                    zzb.zza(parcel, readInt, (List) arrayList3, getClass().getClassLoader());
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
        return new GoalsReadRequest(i, iBinder, arrayList, arrayList2, arrayList3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GoalsReadRequest[i];
    }
}
