package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzd implements Parcelable.Creator<zzc> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        boolean z = false;
        DriveId driveId = null;
        int i = 0;
        int i2 = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    parcelFileDescriptor = (ParcelFileDescriptor) zzb.zza(parcel, readInt, ParcelFileDescriptor.CREATOR);
                    break;
                case 3:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    driveId = (DriveId) zzb.zza(parcel, readInt, DriveId.CREATOR);
                    break;
                case 7:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 8:
                    str = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzc(parcelFileDescriptor, i2, i, driveId, z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzc[i];
    }
}
