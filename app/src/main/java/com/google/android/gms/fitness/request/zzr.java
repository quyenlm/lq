package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.internal.zzbwb;
import com.google.android.gms.internal.zzbwc;
import java.util.Arrays;

public final class zzr extends zza {
    public static final Parcelable.Creator<zzr> CREATOR = new zzs();
    private final String mName;
    private final zzbwb zzaWM;
    private final int zzaku;

    zzr(int i, String str, IBinder iBinder) {
        this.zzaku = i;
        this.mName = str;
        this.zzaWM = zzbwc.zzR(iBinder);
    }

    public zzr(String str, zzbwb zzbwb) {
        this.zzaku = 3;
        this.mName = str;
        this.zzaWM = zzbwb;
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzr) && zzbe.equal(this.mName, ((zzr) obj).mName));
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.mName});
    }

    public final String toString() {
        return zzbe.zzt(this).zzg("name", this.mName).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.mName, false);
        zzd.zza(parcel, 3, this.zzaWM.asBinder(), false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
