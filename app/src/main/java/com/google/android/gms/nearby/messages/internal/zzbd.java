package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbd implements Parcelable.Creator<zzbc> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ClientAppContext clientAppContext = null;
        boolean z = false;
        String str = null;
        String str2 = null;
        IBinder iBinder = null;
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
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 4:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 7:
                    clientAppContext = (ClientAppContext) zzb.zza(parcel, readInt, ClientAppContext.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbc(i, zzaf, iBinder, str2, str, z, clientAppContext);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbc[i];
    }
}
