package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.internal.zzcpj;
import com.google.android.gms.nearby.messages.Message;

public final class zzbg implements Parcelable.Creator<Update> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        zzcpj zzcpj = null;
        zza zza = null;
        zze zze = null;
        Message message = null;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    message = (Message) zzb.zza(parcel, readInt, Message.CREATOR);
                    break;
                case 4:
                    zze = (zze) zzb.zza(parcel, readInt, zze.CREATOR);
                    break;
                case 5:
                    zza = (zza) zzb.zza(parcel, readInt, zza.CREATOR);
                    break;
                case 6:
                    zzcpj = (zzcpj) zzb.zza(parcel, readInt, zzcpj.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new Update(i2, i, message, zze, zza, zzcpj);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Update[i];
    }
}
