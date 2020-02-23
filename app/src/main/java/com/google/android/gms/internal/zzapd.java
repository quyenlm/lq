package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public final class zzapd extends zza {
    public static final Parcelable.Creator<zzapd> CREATOR = new zzape();
    private int id;
    private Bundle zzajk;

    zzapd(int i, Bundle bundle) {
        this.id = i;
        this.zzajk = bundle;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzapd)) {
            return false;
        }
        zzapd zzapd = (zzapd) obj;
        return zzbe.equal(Integer.valueOf(zzapd.id), Integer.valueOf(this.id)) && zzbe.equal(zzapd.zzajk, this.zzajk);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.id), this.zzajk});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.id);
        zzd.zza(parcel, 2, this.zzajk, false);
        zzd.zzI(parcel, zze);
    }
}
