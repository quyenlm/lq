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

public final class zzaw extends zza {
    public static final Parcelable.Creator<zzaw> CREATOR = new zzax();
    private final PendingIntent mPendingIntent;
    private final zzbxg zzaWo;
    private final int zzaXn;
    private final int zzaku;

    zzaw(int i, PendingIntent pendingIntent, IBinder iBinder, int i2) {
        this.zzaku = i;
        this.mPendingIntent = pendingIntent;
        this.zzaWo = iBinder == null ? null : zzbxh.zzW(iBinder);
        this.zzaXn = i2;
    }

    public zzaw(PendingIntent pendingIntent, zzbxg zzbxg, int i) {
        this.zzaku = 6;
        this.mPendingIntent = pendingIntent;
        this.zzaWo = zzbxg;
        this.zzaXn = i;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof zzaw)) {
                return false;
            }
            zzaw zzaw = (zzaw) obj;
            if (!(this.zzaXn == zzaw.zzaXn && zzbe.equal(this.mPendingIntent, zzaw.mPendingIntent))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.mPendingIntent, Integer.valueOf(this.zzaXn)});
    }

    public final String toString() {
        return zzbe.zzt(this).zzg("pendingIntent", this.mPendingIntent).zzg("sessionRegistrationOption", Integer.valueOf(this.zzaXn)).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.mPendingIntent, i, false);
        zzd.zza(parcel, 2, this.zzaWo == null ? null : this.zzaWo.asBinder(), false);
        zzd.zzc(parcel, 4, this.zzaXn);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
