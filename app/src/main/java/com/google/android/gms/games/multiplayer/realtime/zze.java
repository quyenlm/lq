package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

public class zze implements Parcelable.Creator<RoomEntity> {
    public /* synthetic */ Object[] newArray(int i) {
        return new RoomEntity[i];
    }

    /* renamed from: zzj */
    public RoomEntity createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        long j = 0;
        int i = 0;
        ArrayList<ParticipantEntity> arrayList = null;
        Bundle bundle = null;
        int i2 = 0;
        String str = null;
        int i3 = 0;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 7:
                    bundle = zzb.zzs(parcel, readInt);
                    break;
                case 8:
                    arrayList = zzb.zzc(parcel, readInt, ParticipantEntity.CREATOR);
                    break;
                case 9:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new RoomEntity(str3, str2, j, i3, str, i2, bundle, arrayList, i);
    }
}
