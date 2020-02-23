package com.google.android.gms.games.quest;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

public final class zzc implements Parcelable.Creator<QuestEntity> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        GameEntity gameEntity = null;
        String str = null;
        long j = 0;
        Uri uri = null;
        String str2 = null;
        String str3 = null;
        long j2 = 0;
        long j3 = 0;
        Uri uri2 = null;
        String str4 = null;
        String str5 = null;
        long j4 = 0;
        long j5 = 0;
        int i = 0;
        int i2 = 0;
        ArrayList<MilestoneEntity> arrayList = null;
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
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 5:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 8:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 9:
                    uri2 = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 10:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 12:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 13:
                    j4 = zzb.zzi(parcel, readInt);
                    break;
                case 14:
                    j5 = zzb.zzi(parcel, readInt);
                    break;
                case 15:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 16:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 17:
                    arrayList = zzb.zzc(parcel, readInt, MilestoneEntity.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new QuestEntity(gameEntity, str, j, uri, str2, str3, j2, j3, uri2, str4, str5, j4, j5, i, i2, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new QuestEntity[i];
    }
}
