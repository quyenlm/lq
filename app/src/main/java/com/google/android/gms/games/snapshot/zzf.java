package com.google.android.gms.games.snapshot;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.PlayerEntity;

public final class zzf implements Parcelable.Creator<SnapshotMetadataEntity> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        GameEntity gameEntity = null;
        PlayerEntity playerEntity = null;
        String str = null;
        Uri uri = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        long j = 0;
        long j2 = 0;
        float f = 0.0f;
        String str5 = null;
        boolean z = false;
        long j3 = 0;
        String str6 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    gameEntity = (GameEntity) zzb.zza(parcel, readInt, GameEntity.CREATOR);
                    break;
                case 2:
                    playerEntity = (PlayerEntity) zzb.zza(parcel, readInt, PlayerEntity.CREATOR);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 10:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 11:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case 12:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 13:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 14:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 15:
                    str6 = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new SnapshotMetadataEntity(gameEntity, playerEntity, str, uri, str2, str3, str4, j, j2, f, str5, z, j3, str6);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SnapshotMetadataEntity[i];
    }
}
