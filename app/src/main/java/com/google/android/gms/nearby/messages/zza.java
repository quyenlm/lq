package com.google.android.gms.nearby.messages;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.internal.zzcpj;

public final class zza implements Parcelable.Creator<Message> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        zzcpj[] zzcpjArr = null;
        String str = null;
        String str2 = null;
        byte[] bArr = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    zzcpjArr = (zzcpj[]) zzb.zzb(parcel, readInt, zzcpj.CREATOR);
                    break;
                case 5:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new Message(i, bArr, str2, str, zzcpjArr, j);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Message[i];
    }
}
