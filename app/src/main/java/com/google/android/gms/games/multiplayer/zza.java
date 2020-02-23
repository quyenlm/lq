package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

public class zza implements Parcelable.Creator<InvitationEntity> {
    public /* synthetic */ Object[] newArray(int i) {
        return new InvitationEntity[i];
    }

    /* renamed from: zzh */
    public InvitationEntity createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        long j = 0;
        int i = 0;
        int i2 = 0;
        ArrayList<ParticipantEntity> arrayList = null;
        ParticipantEntity participantEntity = null;
        int i3 = 0;
        String str = null;
        GameEntity gameEntity = null;
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
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    participantEntity = (ParticipantEntity) zzb.zza(parcel, readInt, ParticipantEntity.CREATOR);
                    break;
                case 6:
                    arrayList = zzb.zzc(parcel, readInt, ParticipantEntity.CREATOR);
                    break;
                case 7:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new InvitationEntity(gameEntity, str, j, i3, participantEntity, arrayList, i2, i);
    }
}
