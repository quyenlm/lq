package com.google.android.gms.games.snapshot;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc implements Parcelable.Creator<SnapshotEntity> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        zza zza = null;
        SnapshotMetadataEntity snapshotMetadataEntity = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    snapshotMetadataEntity = (SnapshotMetadataEntity) zzb.zza(parcel, readInt, SnapshotMetadataEntity.CREATOR);
                    break;
                case 3:
                    zza = (zza) zzb.zza(parcel, readInt, zza.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new SnapshotEntity(snapshotMetadataEntity, zza);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SnapshotEntity[i];
    }
}
