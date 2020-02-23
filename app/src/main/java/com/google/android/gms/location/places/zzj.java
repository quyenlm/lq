package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzj implements Parcelable.Creator<PlacePhotoMetadataResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        DataHolder dataHolder = null;
        Status status = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    status = (Status) zzb.zza(parcel, readInt, Status.CREATOR);
                    break;
                case 2:
                    dataHolder = (DataHolder) zzb.zza(parcel, readInt, DataHolder.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PlacePhotoMetadataResult(status, dataHolder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PlacePhotoMetadataResult[i];
    }
}
