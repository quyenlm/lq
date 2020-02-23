package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;

public final class zzbf implements Parcelable.Creator<StartBleScanRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        IBinder iBinder = null;
        int i = 0;
        IBinder iBinder2 = null;
        ArrayList<DataType> arrayList = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    arrayList = zzb.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 2:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case 3:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    iBinder = zzb.zzr(parcel, readInt);
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
        return new StartBleScanRequest(i2, arrayList, iBinder2, i, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new StartBleScanRequest[i];
    }
}
