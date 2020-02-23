package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.drive.zzv;

public final class zzbpn extends zzv {
    public static final Parcelable.Creator<zzbpn> CREATOR = new zzbpo();
    final DataHolder zzaPj;

    public zzbpn(DataHolder dataHolder) {
        this.zzaPj = dataHolder;
    }

    /* access modifiers changed from: protected */
    public final void zzJ(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) this.zzaPj, i, false);
        zzd.zzI(parcel, zze);
    }
}
