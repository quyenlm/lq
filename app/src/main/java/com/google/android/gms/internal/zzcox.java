package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import java.util.Arrays;

public final class zzcox extends zza {
    public static final Parcelable.Creator<zzcox> CREATOR = new zzcoy();
    private final long durationMillis;
    private final String name;
    private final String zzbwr;
    @Nullable
    private final zzcms zzbxI;
    @Nullable
    private final zzcnl zzbxL;
    @Nullable
    private final zzcmm zzbxM;
    private final AdvertisingOptions zzbxN;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzcox(@android.support.annotation.Nullable android.os.IBinder r14, @android.support.annotation.Nullable android.os.IBinder r15, java.lang.String r16, java.lang.String r17, long r18, com.google.android.gms.nearby.connection.AdvertisingOptions r20, @android.support.annotation.Nullable android.os.IBinder r21) {
        /*
            r13 = this;
            if (r14 != 0) goto L_0x0016
            r4 = 0
        L_0x0003:
            if (r15 != 0) goto L_0x002a
            r5 = 0
        L_0x0006:
            if (r21 != 0) goto L_0x003e
            r11 = 0
        L_0x0009:
            r3 = r13
            r6 = r16
            r7 = r17
            r8 = r18
            r10 = r20
            r3.<init>((com.google.android.gms.internal.zzcnl) r4, (com.google.android.gms.internal.zzcmm) r5, (java.lang.String) r6, (java.lang.String) r7, (long) r8, (com.google.android.gms.nearby.connection.AdvertisingOptions) r10, (com.google.android.gms.internal.zzcms) r11)
            return
        L_0x0016:
            java.lang.String r2 = "com.google.android.gms.nearby.internal.connection.IStartAdvertisingResultListener"
            android.os.IInterface r2 = r14.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.zzcnl
            if (r3 == 0) goto L_0x0024
            com.google.android.gms.internal.zzcnl r2 = (com.google.android.gms.internal.zzcnl) r2
            r4 = r2
            goto L_0x0003
        L_0x0024:
            com.google.android.gms.internal.zzcnn r4 = new com.google.android.gms.internal.zzcnn
            r4.<init>(r14)
            goto L_0x0003
        L_0x002a:
            java.lang.String r2 = "com.google.android.gms.nearby.internal.connection.IAdvertisingCallback"
            android.os.IInterface r2 = r15.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.zzcmm
            if (r3 == 0) goto L_0x0038
            com.google.android.gms.internal.zzcmm r2 = (com.google.android.gms.internal.zzcmm) r2
            r5 = r2
            goto L_0x0006
        L_0x0038:
            com.google.android.gms.internal.zzcmo r5 = new com.google.android.gms.internal.zzcmo
            r5.<init>(r15)
            goto L_0x0006
        L_0x003e:
            java.lang.String r2 = "com.google.android.gms.nearby.internal.connection.IConnectionLifecycleListener"
            r0 = r21
            android.os.IInterface r2 = r0.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.zzcms
            if (r3 == 0) goto L_0x004e
            com.google.android.gms.internal.zzcms r2 = (com.google.android.gms.internal.zzcms) r2
            r11 = r2
            goto L_0x0009
        L_0x004e:
            com.google.android.gms.internal.zzcmu r11 = new com.google.android.gms.internal.zzcmu
            r0 = r21
            r11.<init>(r0)
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcox.<init>(android.os.IBinder, android.os.IBinder, java.lang.String, java.lang.String, long, com.google.android.gms.nearby.connection.AdvertisingOptions, android.os.IBinder):void");
    }

    private zzcox(@Nullable zzcnl zzcnl, @Nullable zzcmm zzcmm, String str, String str2, long j, AdvertisingOptions advertisingOptions, @Nullable zzcms zzcms) {
        this.zzbxL = zzcnl;
        this.zzbxM = zzcmm;
        this.name = str;
        this.zzbwr = str2;
        this.durationMillis = j;
        this.zzbxN = advertisingOptions;
        this.zzbxI = zzcms;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcox)) {
            return false;
        }
        zzcox zzcox = (zzcox) obj;
        return zzbe.equal(this.zzbxL, zzcox.zzbxL) && zzbe.equal(this.zzbxM, zzcox.zzbxM) && zzbe.equal(this.name, zzcox.name) && zzbe.equal(this.zzbwr, zzcox.zzbwr) && zzbe.equal(Long.valueOf(this.durationMillis), Long.valueOf(zzcox.durationMillis)) && zzbe.equal(this.zzbxN, zzcox.zzbxN) && zzbe.equal(this.zzbxI, zzcox.zzbxI);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbxL, this.zzbxM, this.name, this.zzbwr, Long.valueOf(this.durationMillis), this.zzbxN, this.zzbxI});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbxL == null ? null : this.zzbxL.asBinder(), false);
        zzd.zza(parcel, 2, this.zzbxM == null ? null : this.zzbxM.asBinder(), false);
        zzd.zza(parcel, 3, this.name, false);
        zzd.zza(parcel, 4, this.zzbwr, false);
        zzd.zza(parcel, 5, this.durationMillis);
        zzd.zza(parcel, 6, (Parcelable) this.zzbxN, i, false);
        if (this.zzbxI != null) {
            iBinder = this.zzbxI.asBinder();
        }
        zzd.zza(parcel, 7, iBinder, false);
        zzd.zzI(parcel, zze);
    }
}
