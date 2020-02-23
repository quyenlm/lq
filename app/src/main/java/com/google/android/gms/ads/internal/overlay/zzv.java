package com.google.android.gms.ads.internal.overlay;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.ads.internal.zzap;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.internal.zzaje;

public final class zzv implements Parcelable.Creator<AdOverlayInfoParcel> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        zzc zzc = null;
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        IBinder iBinder4 = null;
        String str = null;
        boolean z = false;
        String str2 = null;
        IBinder iBinder5 = null;
        int i = 0;
        int i2 = 0;
        String str3 = null;
        zzaje zzaje = null;
        String str4 = null;
        zzap zzap = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    zzc = (zzc) zzb.zza(parcel, readInt, zzc.CREATOR);
                    break;
                case 3:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 4:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case 5:
                    iBinder3 = zzb.zzr(parcel, readInt);
                    break;
                case 6:
                    iBinder4 = zzb.zzr(parcel, readInt);
                    break;
                case 7:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 9:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 10:
                    iBinder5 = zzb.zzr(parcel, readInt);
                    break;
                case 11:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 12:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 13:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 14:
                    zzaje = (zzaje) zzb.zza(parcel, readInt, zzaje.CREATOR);
                    break;
                case 16:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 17:
                    zzap = (zzap) zzb.zza(parcel, readInt, zzap.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new AdOverlayInfoParcel(zzc, iBinder, iBinder2, iBinder3, iBinder4, str, z, str2, iBinder5, i, i2, str3, zzaje, str4, zzap);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new AdOverlayInfoParcel[i];
    }
}
