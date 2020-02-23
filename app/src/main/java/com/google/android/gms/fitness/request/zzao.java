package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;

public final class zzao implements Parcelable.Creator<zzan> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        DataSource dataSource = null;
        DataType dataType = null;
        IBinder iBinder = null;
        int i2 = 0;
        int i3 = 0;
        long j = 0;
        long j2 = 0;
        PendingIntent pendingIntent = null;
        long j3 = 0;
        int i4 = 0;
        ArrayList<LocationRequest> arrayList = null;
        long j4 = 0;
        IBinder iBinder2 = null;
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
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 7:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 8:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, readInt, PendingIntent.CREATOR);
                    break;
                case 9:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 10:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                case 11:
                    arrayList = zzb.zzc(parcel, readInt, LocationRequest.CREATOR);
                    break;
                case 12:
                    j4 = zzb.zzi(parcel, readInt);
                    break;
                case 13:
                    iBinder2 = zzb.zzr(parcel, readInt);
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
        return new zzan(i, dataSource, dataType, iBinder, i2, i3, j, j2, pendingIntent, j3, i4, arrayList, j4, iBinder2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzan[i];
    }
}
