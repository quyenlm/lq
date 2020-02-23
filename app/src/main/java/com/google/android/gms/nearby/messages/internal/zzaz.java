package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzaz extends zza {
    public static final Parcelable.Creator<zzaz> CREATOR = new zzba();
    private int versionCode;
    @Deprecated
    private String zzbye;
    private zzp zzbza;
    @Deprecated
    private ClientAppContext zzbzb;
    private zzx zzbzt;
    public boolean zzbzu;

    zzaz(int i, IBinder iBinder, IBinder iBinder2, boolean z, String str, ClientAppContext clientAppContext) {
        zzp zzr;
        zzx zzz;
        this.versionCode = i;
        if (iBinder == null) {
            zzr = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesCallback");
            zzr = queryLocalInterface instanceof zzp ? (zzp) queryLocalInterface : new zzr(iBinder);
        }
        this.zzbza = zzr;
        if (iBinder2 == null) {
            zzz = null;
        } else {
            IInterface queryLocalInterface2 = iBinder2.queryLocalInterface("com.google.android.gms.nearby.messages.internal.IStatusCallback");
            zzz = queryLocalInterface2 instanceof zzx ? (zzx) queryLocalInterface2 : new zzz(iBinder2);
        }
        this.zzbzt = zzz;
        this.zzbzu = z;
        this.zzbye = str;
        this.zzbzb = ClientAppContext.zza(clientAppContext, (String) null, str, false);
    }

    public zzaz(IBinder iBinder, IBinder iBinder2) {
        this(1, iBinder, iBinder2, false, (String) null, (ClientAppContext) null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, 2, this.zzbza.asBinder(), false);
        zzd.zza(parcel, 3, this.zzbzt.asBinder(), false);
        zzd.zza(parcel, 4, this.zzbzu);
        zzd.zza(parcel, 5, this.zzbye, false);
        zzd.zza(parcel, 6, (Parcelable) this.zzbzb, i, false);
        zzd.zzI(parcel, zze);
    }
}
