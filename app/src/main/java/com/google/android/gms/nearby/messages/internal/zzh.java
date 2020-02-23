package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

@Deprecated
public final class zzh extends zza {
    public static final Parcelable.Creator<zzh> CREATOR = new zzi();
    private int zzaku;
    @Deprecated
    private String zzbye;
    private zzp zzbza;
    @Deprecated
    private ClientAppContext zzbzb;

    zzh(int i, IBinder iBinder, String str, ClientAppContext clientAppContext) {
        zzp zzr;
        this.zzaku = i;
        if (iBinder == null) {
            zzr = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesCallback");
            zzr = queryLocalInterface instanceof zzp ? (zzp) queryLocalInterface : new zzr(iBinder);
        }
        this.zzbza = zzr;
        this.zzbye = str;
        this.zzbzb = ClientAppContext.zza(clientAppContext, (String) null, str, false);
    }

    zzh(IBinder iBinder) {
        this(1, iBinder, (String) null, (ClientAppContext) null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzaku);
        zzd.zza(parcel, 2, this.zzbza.asBinder(), false);
        zzd.zza(parcel, 3, this.zzbye, false);
        zzd.zza(parcel, 4, (Parcelable) this.zzbzb, i, false);
        zzd.zzI(parcel, zze);
    }
}
