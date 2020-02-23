package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.internal.zzbxd;
import com.google.android.gms.internal.zzbxe;
import java.util.Arrays;

public final class zzba extends zza {
    public static final Parcelable.Creator<zzba> CREATOR = new zzbb();
    private final String mName;
    private final String zzaVf;
    private final zzbxd zzaXo;
    private final int zzaku;

    zzba(int i, String str, String str2, IBinder iBinder) {
        this.zzaku = i;
        this.mName = str;
        this.zzaVf = str2;
        this.zzaXo = zzbxe.zzV(iBinder);
    }

    public zzba(String str, String str2, zzbxd zzbxd) {
        this.zzaku = 3;
        this.mName = str;
        this.zzaVf = str2;
        this.zzaXo = zzbxd;
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof zzba)) {
                return false;
            }
            zzba zzba = (zzba) obj;
            if (!(zzbe.equal(this.mName, zzba.mName) && zzbe.equal(this.zzaVf, zzba.zzaVf))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.mName, this.zzaVf});
    }

    public final String toString() {
        return zzbe.zzt(this).zzg("name", this.mName).zzg("identifier", this.zzaVf).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.mName, false);
        zzd.zza(parcel, 2, this.zzaVf, false);
        zzd.zza(parcel, 3, this.zzaXo == null ? null : this.zzaXo.asBinder(), false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
