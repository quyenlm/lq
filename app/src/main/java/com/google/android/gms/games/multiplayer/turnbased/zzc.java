package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

public final class zzc implements Parcelable.Creator<TurnBasedMatchEntity> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        GameEntity gameEntity = null;
        String str = null;
        String str2 = null;
        long j = 0;
        String str3 = null;
        long j2 = 0;
        String str4 = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        byte[] bArr = null;
        ArrayList<ParticipantEntity> arrayList = null;
        String str5 = null;
        byte[] bArr2 = null;
        int i4 = 0;
        Bundle bundle = null;
        int i5 = 0;
        boolean z = false;
        String str6 = null;
        String str7 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    gameEntity = (GameEntity) zzb.zza(parcel, readInt, GameEntity.CREATOR);
                    break;
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 5:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 7:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 10:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 11:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 12:
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                case 13:
                    arrayList = zzb.zzc(parcel, readInt, ParticipantEntity.CREATOR);
                    break;
                case 14:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 15:
                    bArr2 = zzb.zzt(parcel, readInt);
                    break;
                case 16:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                case 17:
                    bundle = zzb.zzs(parcel, readInt);
                    break;
                case 18:
                    i5 = zzb.zzg(parcel, readInt);
                    break;
                case 19:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 20:
                    str6 = zzb.zzq(parcel, readInt);
                    break;
                case 21:
                    str7 = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new TurnBasedMatchEntity(gameEntity, str, str2, j, str3, j2, str4, i, i2, i3, bArr, arrayList, str5, bArr2, i4, bundle, i5, z, str6, str7);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new TurnBasedMatchEntity[i];
    }
}
