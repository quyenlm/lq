package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.DriveId;
import java.util.ArrayList;

public final class zzbqp implements Parcelable.Creator<zzbqo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<DriveId> arrayList = null;
        DriveId driveId = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    driveId = (DriveId) zzb.zza(parcel, readInt, DriveId.CREATOR);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, readInt, DriveId.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbqo(driveId, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbqo[i];
    }
}
