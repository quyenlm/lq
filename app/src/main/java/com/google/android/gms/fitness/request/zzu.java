package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;

public final class zzu implements Parcelable.Creator<DataUpdateListenerRegistrationRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        IBinder iBinder = null;
        PendingIntent pendingIntent = null;
        DataType dataType = null;
        DataSource dataSource = null;
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
                    pendingIntent = (PendingIntent) zzb.zza(parcel, readInt, PendingIntent.CREATOR);
                    break;
                case 4:
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
        return new DataUpdateListenerRegistrationRequest(i, dataSource, dataType, pendingIntent, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataUpdateListenerRegistrationRequest[i];
    }
}
