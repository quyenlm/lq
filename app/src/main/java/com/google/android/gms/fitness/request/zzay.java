package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbxg;
import com.google.android.gms.internal.zzbxh;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public final class zzay extends zza {
    public static final Parcelable.Creator<zzay> CREATOR = new zzaz();
    private final Session zzaTe;
    private final zzbxg zzaWo;
    private final int zzaku;

    zzay(int i, Session session, IBinder iBinder) {
        this.zzaku = i;
        this.zzaTe = session;
        this.zzaWo = zzbxh.zzW(iBinder);
    }

    public zzay(Session session, zzbxg zzbxg) {
        zzbo.zzb(session.getStartTime(TimeUnit.MILLISECONDS) < System.currentTimeMillis(), (Object) "Cannot start a session in the future");
        zzbo.zzb(session.isOngoing(), (Object) "Cannot start a session which has already ended");
        this.zzaku = 3;
        this.zzaTe = session;
        this.zzaWo = zzbxg;
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzay) && zzbe.equal(this.zzaTe, ((zzay) obj).zzaTe));
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaTe});
    }

    public final String toString() {
        return zzbe.zzt(this).zzg("session", this.zzaTe).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.zzaTe, i, false);
        zzd.zza(parcel, 2, this.zzaWo == null ? null : this.zzaWo.asBinder(), false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
