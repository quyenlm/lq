package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.drive.query.Filter;

public class FilterHolder extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<FilterHolder> CREATOR = new zzh();
    private final Filter zzaMD;
    private zzb<?> zzaRh;
    private zzd zzaRi;
    private zzr zzaRj;
    private zzv zzaRk;
    private zzp<?> zzaRl;
    private zzt zzaRm;
    private zzn zzaRn;
    private zzl zzaRo;
    private zzz zzaRp;

    public FilterHolder(Filter filter) {
        zzbo.zzb(filter, (Object) "Null filter.");
        this.zzaRh = filter instanceof zzb ? (zzb) filter : null;
        this.zzaRi = filter instanceof zzd ? (zzd) filter : null;
        this.zzaRj = filter instanceof zzr ? (zzr) filter : null;
        this.zzaRk = filter instanceof zzv ? (zzv) filter : null;
        this.zzaRl = filter instanceof zzp ? (zzp) filter : null;
        this.zzaRm = filter instanceof zzt ? (zzt) filter : null;
        this.zzaRn = filter instanceof zzn ? (zzn) filter : null;
        this.zzaRo = filter instanceof zzl ? (zzl) filter : null;
        this.zzaRp = filter instanceof zzz ? (zzz) filter : null;
        if (this.zzaRh == null && this.zzaRi == null && this.zzaRj == null && this.zzaRk == null && this.zzaRl == null && this.zzaRm == null && this.zzaRn == null && this.zzaRo == null && this.zzaRp == null) {
            throw new IllegalArgumentException("Invalid filter type.");
        }
        this.zzaMD = filter;
    }

    FilterHolder(zzb<?> zzb, zzd zzd, zzr zzr, zzv zzv, zzp<?> zzp, zzt zzt, zzn<?> zzn, zzl zzl, zzz zzz) {
        this.zzaRh = zzb;
        this.zzaRi = zzd;
        this.zzaRj = zzr;
        this.zzaRk = zzv;
        this.zzaRl = zzp;
        this.zzaRm = zzt;
        this.zzaRn = zzn;
        this.zzaRo = zzl;
        this.zzaRp = zzz;
        if (this.zzaRh != null) {
            this.zzaMD = this.zzaRh;
        } else if (this.zzaRi != null) {
            this.zzaMD = this.zzaRi;
        } else if (this.zzaRj != null) {
            this.zzaMD = this.zzaRj;
        } else if (this.zzaRk != null) {
            this.zzaMD = this.zzaRk;
        } else if (this.zzaRl != null) {
            this.zzaMD = this.zzaRl;
        } else if (this.zzaRm != null) {
            this.zzaMD = this.zzaRm;
        } else if (this.zzaRn != null) {
            this.zzaMD = this.zzaRn;
        } else if (this.zzaRo != null) {
            this.zzaMD = this.zzaRo;
        } else if (this.zzaRp != null) {
            this.zzaMD = this.zzaRp;
        } else {
            throw new IllegalArgumentException("At least one filter must be set.");
        }
    }

    public final Filter getFilter() {
        return this.zzaMD;
    }

    public String toString() {
        return String.format("FilterHolder[%s]", new Object[]{this.zzaMD});
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.zzaRh, i, false);
        zzd.zza(parcel, 2, (Parcelable) this.zzaRi, i, false);
        zzd.zza(parcel, 3, (Parcelable) this.zzaRj, i, false);
        zzd.zza(parcel, 4, (Parcelable) this.zzaRk, i, false);
        zzd.zza(parcel, 5, (Parcelable) this.zzaRl, i, false);
        zzd.zza(parcel, 6, (Parcelable) this.zzaRm, i, false);
        zzd.zza(parcel, 7, (Parcelable) this.zzaRn, i, false);
        zzd.zza(parcel, 8, (Parcelable) this.zzaRo, i, false);
        zzd.zza(parcel, 9, (Parcelable) this.zzaRp, i, false);
        zzd.zzI(parcel, zze);
    }
}
