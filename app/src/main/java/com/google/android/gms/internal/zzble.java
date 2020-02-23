package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzble implements Parcelable.Creator<zzbld> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        Integer num = null;
        DriveId driveId = null;
        String str = null;
        MetadataBundle metadataBundle = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    metadataBundle = (MetadataBundle) zzb.zza(parcel, readInt, MetadataBundle.CREATOR);
                    break;
                case 3:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    driveId = (DriveId) zzb.zza(parcel, readInt, DriveId.CREATOR);
                    break;
                case 6:
                    num = zzb.zzh(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbld(metadataBundle, i, str, driveId, num);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbld[i];
    }
}
