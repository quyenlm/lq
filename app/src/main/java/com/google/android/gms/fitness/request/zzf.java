package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbvs;
import com.google.android.gms.internal.zzbvt;

public final class zzf extends zza {
    public static final Parcelable.Creator<zzf> CREATOR = new zzg();
    private final int versionCode;
    private DataType zzaSZ;
    private final zzbvs zzaWp;
    private final boolean zzaWq;

    zzf(int i, IBinder iBinder, DataType dataType, boolean z) {
        this.versionCode = i;
        this.zzaWp = zzbvt.zzO(iBinder);
        this.zzaSZ = dataType;
        this.zzaWq = z;
    }

    public zzf(zzbvs zzbvs, DataType dataType, boolean z) {
        this.versionCode = 3;
        this.zzaWp = zzbvs;
        this.zzaSZ = dataType;
        this.zzaWq = z;
    }

    public final String toString() {
        Object[] objArr = new Object[1];
        objArr[0] = this.zzaSZ == null ? Constants.NULL_VERSION_ID : this.zzaSZ.zztO();
        return String.format("DailyTotalRequest{%s}", objArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzaWp.asBinder(), false);
        zzd.zza(parcel, 2, (Parcelable) this.zzaSZ, i, false);
        zzd.zza(parcel, 4, this.zzaWq);
        zzd.zzc(parcel, 1000, this.versionCode);
        zzd.zzI(parcel, zze);
    }
}
