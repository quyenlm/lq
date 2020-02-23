package com.google.android.gms.games.internal.experience;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;

public final class zza implements Parcelable.Creator<ExperienceEventEntity> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        GameEntity gameEntity = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        Uri uri = null;
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    gameEntity = (GameEntity) zzb.zza(parcel, readInt, GameEntity.CREATOR);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 7:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 8:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 9:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 10:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 11:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new ExperienceEventEntity(str, gameEntity, str2, str3, str4, uri, j, j2, j3, i, i2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new ExperienceEventEntity[i];
    }
}
