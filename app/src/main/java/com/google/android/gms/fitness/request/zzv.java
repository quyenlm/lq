package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.internal.zzbxg;
import com.google.android.gms.internal.zzbxh;
import java.util.Arrays;

public final class zzv extends zza {
    public static final Parcelable.Creator<zzv> CREATOR = new zzw();
    private final PendingIntent mPendingIntent;
    private final zzbxg zzaWo;
    private final int zzaku;

    zzv(int i, PendingIntent pendingIntent, IBinder iBinder) {
        this.zzaku = i;
        this.mPendingIntent = pendingIntent;
        this.zzaWo = zzbxh.zzW(iBinder);
    }

    public zzv(PendingIntent pendingIntent, IBinder iBinder) {
        this.zzaku = 1;
        this.mPendingIntent = pendingIntent;
        this.zzaWo = zzbxh.zzW(iBinder);
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzv) && zzbe.equal(this.mPendingIntent, ((zzv) obj).mPendingIntent));
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.mPendingIntent});
    }

    public final String toString() {
        return "DataUpdateListenerUnregistrationRequest";
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.mPendingIntent, i, false);
        zzd.zza(parcel, 2, this.zzaWo == null ? null : this.zzaWo.asBinder(), false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
