package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzba implements Parcelable.Creator<zzaz> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ClientAppContext clientAppContext = null;
        String str = null;
        boolean z = false;
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case 3:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 4:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    clientAppContext = (ClientAppContext) zzb.zza(parcel, readInt, ClientAppContext.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzaz(i, iBinder2, iBinder, z, str, clientAppContext);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzaz[i];
    }
}
