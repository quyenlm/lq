package com.google.android.gms.games;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzi implements Parcelable.Creator<PlayerLevelInfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        PlayerLevel playerLevel = null;
        PlayerLevel playerLevel2 = null;
        long j = 0;
        long j2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 2:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    playerLevel2 = (PlayerLevel) zzb.zza(parcel, readInt, PlayerLevel.CREATOR);
                    break;
                case 4:
                    playerLevel = (PlayerLevel) zzb.zza(parcel, readInt, PlayerLevel.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PlayerLevelInfo(j2, j, playerLevel2, playerLevel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PlayerLevelInfo[i];
    }
}
