package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzbr;

public final class zzcty implements Parcelable.Creator<zzctx> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        zzbr zzbr = null;
        ConnectionResult connectionResult = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    connectionResult = (ConnectionResult) zzb.zza(parcel, readInt, ConnectionResult.CREATOR);
                    break;
                case 3:
                    zzbr = (zzbr) zzb.zza(parcel, readInt, zzbr.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzctx(i, connectionResult, zzbr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzctx[i];
    }
}
