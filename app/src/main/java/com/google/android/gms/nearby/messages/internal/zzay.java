package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.nearby.messages.Strategy;

public final class zzay implements Parcelable.Creator<zzax> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ClientAppContext clientAppContext = null;
        boolean z = false;
        IBinder iBinder = null;
        boolean z2 = false;
        String str = null;
        String str2 = null;
        IBinder iBinder2 = null;
        Strategy strategy = null;
        zzaf zzaf = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    zzaf = (zzaf) zzb.zza(parcel, readInt, zzaf.CREATOR);
                    break;
                case 3:
                    strategy = (Strategy) zzb.zza(parcel, readInt, Strategy.CREATOR);
                    break;
                case 4:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case 5:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 8:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 9:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 10:
                    clientAppContext = (ClientAppContext) zzb.zza(parcel, readInt, ClientAppContext.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzax(i, zzaf, strategy, iBinder2, str2, str, z2, iBinder, z, clientAppContext);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzax[i];
    }
}
