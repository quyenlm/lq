package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import java.util.ArrayList;

public final class zzm implements Parcelable.Creator<DataReadRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        ArrayList<DataType> arrayList = null;
        ArrayList<DataSource> arrayList2 = null;
        long j = 0;
        long j2 = 0;
        ArrayList<DataType> arrayList3 = null;
        ArrayList<DataSource> arrayList4 = null;
        int i2 = 0;
        long j3 = 0;
        DataSource dataSource = null;
        int i3 = 0;
        boolean z = false;
        boolean z2 = false;
        IBinder iBinder = null;
        ArrayList<Device> arrayList5 = null;
        ArrayList<Integer> arrayList6 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    arrayList = zzb.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 2:
                    arrayList2 = zzb.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 3:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 5:
                    arrayList3 = zzb.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 6:
                    arrayList4 = zzb.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 7:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 9:
                    dataSource = (DataSource) zzb.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 10:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 12:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 13:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 14:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 16:
                    arrayList5 = zzb.zzc(parcel, readInt, Device.CREATOR);
                    break;
                case 17:
                    arrayList6 = zzb.zzB(parcel, readInt);
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
        return new DataReadRequest(i, arrayList, arrayList2, j, j2, arrayList3, arrayList4, i2, j3, dataSource, i3, z, z2, iBinder, arrayList5, arrayList6);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataReadRequest[i];
    }
}
