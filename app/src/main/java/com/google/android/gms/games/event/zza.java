package com.google.android.gms.games.event;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.PlayerEntity;

public final class zza implements Parcelable.Creator<EventEntity> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        long j = 0;
        boolean z = false;
        String str = null;
        PlayerEntity playerEntity = null;
        String str2 = null;
        Uri uri = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 5:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    playerEntity = (PlayerEntity) zzb.zza(parcel, readInt, PlayerEntity.CREATOR);
                    break;
                case 7:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 8:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new EventEntity(str5, str4, str3, uri, str2, playerEntity, j, str, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new EventEntity[i];
    }
}
