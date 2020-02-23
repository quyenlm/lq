package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.PlayerEntity;
import java.util.ArrayList;

public final class zza implements Parcelable.Creator<GameRequestEntity> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        GameEntity gameEntity = null;
        PlayerEntity playerEntity = null;
        byte[] bArr = null;
        String str = null;
        ArrayList<PlayerEntity> arrayList = null;
        int i = 0;
        long j = 0;
        long j2 = 0;
        Bundle bundle = null;
        int i2 = 0;
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
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                case 4:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    arrayList = zzb.zzc(parcel, readInt, PlayerEntity.CREATOR);
                    break;
                case 7:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 9:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 10:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 11:
                    bundle = zzb.zzs(parcel, readInt);
                    break;
                case 12:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new GameRequestEntity(gameEntity, playerEntity, bArr, str, arrayList, i, j, j2, bundle, i2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GameRequestEntity[i];
    }
}
