package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.DriveId;

public final class zzbkq implements Parcelable.Creator<zzbkp> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        DriveId driveId = null;
        long j = 0;
        long j2 = 0;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    driveId = (DriveId) zzb.zza(parcel, readInt, DriveId.CREATOR);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 6:
                    j = zzb.zzi(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbkp(i2, driveId, i, j2, j);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbkp[i];
    }
}
