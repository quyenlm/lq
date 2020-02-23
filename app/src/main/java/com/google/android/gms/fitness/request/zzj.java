package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.internal.zzbxg;
import com.google.android.gms.internal.zzbxh;
import java.util.Arrays;

public final class zzj extends zza {
    public static final Parcelable.Creator<zzj> CREATOR = new zzk();
    private final DataSet zzaVi;
    private final zzbxg zzaWo;
    private final boolean zzaWv;
    private final int zzaku;

    zzj(int i, DataSet dataSet, IBinder iBinder, boolean z) {
        this.zzaku = i;
        this.zzaVi = dataSet;
        this.zzaWo = zzbxh.zzW(iBinder);
        this.zzaWv = z;
    }

    public zzj(DataSet dataSet, zzbxg zzbxg, boolean z) {
        this.zzaku = 4;
        this.zzaVi = dataSet;
        this.zzaWo = zzbxg;
        this.zzaWv = z;
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzj) && zzbe.equal(this.zzaVi, ((zzj) obj).zzaVi));
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaVi});
    }

    public final String toString() {
        return zzbe.zzt(this).zzg("dataSet", this.zzaVi).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.zzaVi, i, false);
        zzd.zza(parcel, 2, this.zzaWo == null ? null : this.zzaWo.asBinder(), false);
        zzd.zza(parcel, 4, this.zzaWv);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
