package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.zza;
import java.util.ArrayList;

public final class zzboy implements Parcelable.Creator<zzbox> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        zza zza = null;
        ArrayList<DriveId> arrayList = null;
        DataHolder dataHolder = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    dataHolder = (DataHolder) zzb.zza(parcel, readInt, DataHolder.CREATOR);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, readInt, DriveId.CREATOR);
                    break;
                case 4:
                    zza = (zza) zzb.zza(parcel, readInt, zza.CREATOR);
                    break;
                case 5:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbox(dataHolder, arrayList, zza, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbox[i];
    }
}
