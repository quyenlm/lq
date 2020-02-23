package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.zzt;
import com.google.android.gms.fitness.data.zzu;
import com.google.android.gms.internal.zzbxg;
import com.google.android.gms.internal.zzbxh;
import com.google.android.gms.internal.zzcbz;
import com.google.android.gms.location.LocationRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class zzan extends zza {
    public static final Parcelable.Creator<zzan> CREATOR = new zzao();
    private final PendingIntent mPendingIntent;
    private DataSource zzaUd;
    private DataType zzaUe;
    private final long zzaVj;
    private final int zzaVk;
    private zzt zzaWV;
    private final long zzaWW;
    private final long zzaWX;
    private final List<LocationRequest> zzaWY;
    private final long zzaWZ;
    private final zzbxg zzaWo;
    private final List<zzcbz> zzaXa;
    private final int zzaku;

    zzan(int i, DataSource dataSource, DataType dataType, IBinder iBinder, int i2, int i3, long j, long j2, PendingIntent pendingIntent, long j3, int i4, List<LocationRequest> list, long j4, IBinder iBinder2) {
        this.zzaku = i;
        this.zzaUd = dataSource;
        this.zzaUe = dataType;
        this.zzaWV = iBinder == null ? null : zzu.zzN(iBinder);
        this.zzaVj = j == 0 ? (long) i2 : j;
        this.zzaWX = j3;
        this.zzaWW = j2 == 0 ? (long) i3 : j2;
        this.zzaWY = list;
        this.mPendingIntent = pendingIntent;
        this.zzaVk = i4;
        this.zzaXa = Collections.emptyList();
        this.zzaWZ = j4;
        this.zzaWo = zzbxh.zzW(iBinder2);
    }

    private zzan(DataSource dataSource, DataType dataType, zzt zzt, PendingIntent pendingIntent, long j, long j2, long j3, int i, List<LocationRequest> list, List<zzcbz> list2, long j4, zzbxg zzbxg) {
        this.zzaku = 6;
        this.zzaUd = dataSource;
        this.zzaUe = dataType;
        this.zzaWV = zzt;
        this.mPendingIntent = pendingIntent;
        this.zzaVj = j;
        this.zzaWX = j2;
        this.zzaWW = j3;
        this.zzaVk = i;
        this.zzaWY = null;
        this.zzaXa = list2;
        this.zzaWZ = j4;
        this.zzaWo = zzbxg;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzan(com.google.android.gms.fitness.request.SensorRequest r21, com.google.android.gms.fitness.data.zzt r22, android.app.PendingIntent r23, com.google.android.gms.internal.zzbxg r24) {
        /*
            r20 = this;
            com.google.android.gms.fitness.data.DataSource r4 = r21.getDataSource()
            com.google.android.gms.fitness.data.DataType r5 = r21.getDataType()
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MICROSECONDS
            r0 = r21
            long r8 = r0.getSamplingRate(r2)
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MICROSECONDS
            r0 = r21
            long r10 = r0.getFastestRate(r2)
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MICROSECONDS
            r0 = r21
            long r12 = r0.getMaxDeliveryLatency(r2)
            int r14 = r21.getAccuracyMode()
            r15 = 0
            java.util.List r16 = java.util.Collections.emptyList()
            long r17 = r21.zztW()
            r3 = r20
            r6 = r22
            r7 = r23
            r19 = r24
            r3.<init>(r4, r5, r6, r7, r8, r10, r12, r14, r15, r16, r17, r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.request.zzan.<init>(com.google.android.gms.fitness.request.SensorRequest, com.google.android.gms.fitness.data.zzt, android.app.PendingIntent, com.google.android.gms.internal.zzbxg):void");
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof zzan)) {
                return false;
            }
            zzan zzan = (zzan) obj;
            if (!(zzbe.equal(this.zzaUd, zzan.zzaUd) && zzbe.equal(this.zzaUe, zzan.zzaUe) && this.zzaVj == zzan.zzaVj && this.zzaWX == zzan.zzaWX && this.zzaWW == zzan.zzaWW && this.zzaVk == zzan.zzaVk && zzbe.equal(this.zzaWY, zzan.zzaWY))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaUd, this.zzaUe, this.zzaWV, Long.valueOf(this.zzaVj), Long.valueOf(this.zzaWX), Long.valueOf(this.zzaWW), Integer.valueOf(this.zzaVk), this.zzaWY});
    }

    public final String toString() {
        return String.format("SensorRegistrationRequest{type %s source %s interval %s fastest %s latency %s}", new Object[]{this.zzaUe, this.zzaUd, Long.valueOf(this.zzaVj), Long.valueOf(this.zzaWX), Long.valueOf(this.zzaWW)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.zzaUd, i, false);
        zzd.zza(parcel, 2, (Parcelable) this.zzaUe, i, false);
        zzd.zza(parcel, 3, this.zzaWV == null ? null : this.zzaWV.asBinder(), false);
        zzd.zzc(parcel, 4, 0);
        zzd.zzc(parcel, 5, 0);
        zzd.zza(parcel, 6, this.zzaVj);
        zzd.zza(parcel, 7, this.zzaWW);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zza(parcel, 8, (Parcelable) this.mPendingIntent, i, false);
        zzd.zza(parcel, 9, this.zzaWX);
        zzd.zzc(parcel, 10, this.zzaVk);
        zzd.zzc(parcel, 11, this.zzaWY, false);
        zzd.zza(parcel, 12, this.zzaWZ);
        if (this.zzaWo != null) {
            iBinder = this.zzaWo.asBinder();
        }
        zzd.zza(parcel, 13, iBinder, false);
        zzd.zzI(parcel, zze);
    }
}
