package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.PlayerEntity;

public class zzc implements Parcelable.Creator<ParticipantEntity> {
    public /* synthetic */ Object[] newArray(int i) {
        return new ParticipantEntity[i];
    }

    /* renamed from: zzi */
    public ParticipantEntity createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        Uri uri = null;
        Uri uri2 = null;
        int i = 0;
        String str3 = null;
        boolean z = false;
        PlayerEntity playerEntity = null;
        int i2 = 0;
        ParticipantResult participantResult = null;
        String str4 = null;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 4:
                    uri2 = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 5:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 8:
                    playerEntity = (PlayerEntity) zzb.zza(parcel, readInt, PlayerEntity.CREATOR);
                    break;
                case 9:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 10:
                    participantResult = (ParticipantResult) zzb.zza(parcel, readInt, ParticipantResult.CREATOR);
                    break;
                case 11:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 12:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new ParticipantEntity(str, str2, uri, uri2, i, str3, z, playerEntity, i2, participantResult, str4, str5);
    }
}
