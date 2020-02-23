package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.List;

public final class zzbtd extends zza {
    public static final Parcelable.Creator<zzbtd> CREATOR = new zzbte();
    private long zzayS;
    private List<zzbtl> zztH;

    public zzbtd(long j, List<zzbtl> list) {
        this.zzayS = j;
        this.zztH = list;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzayS);
        zzd.zzc(parcel, 3, this.zztH, false);
        zzd.zzI(parcel, zze);
    }
}
