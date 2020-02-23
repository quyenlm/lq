package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.zze;
import com.google.android.gms.drive.events.zzp;
import com.google.android.gms.drive.events.zzt;

public final class zzbks implements Parcelable.Creator<zzbkr> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        zzp zzp = null;
        zzt zzt = null;
        zze zze = null;
        DriveId driveId = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    driveId = (DriveId) zzb.zza(parcel, readInt, DriveId.CREATOR);
                    break;
                case 3:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    zze = (zze) zzb.zza(parcel, readInt, zze.CREATOR);
                    break;
                case 5:
                    zzt = (zzt) zzb.zza(parcel, readInt, zzt.CREATOR);
                    break;
                case 6:
                    zzp = (zzp) zzb.zza(parcel, readInt, zzp.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbkr(driveId, i, zze, zzt, zzp);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbkr[i];
    }
}
