package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import java.util.Arrays;

public final class zzcoz extends zza {
    public static final Parcelable.Creator<zzcoz> CREATOR = new zzcpa();
    private final long durationMillis;
    @Nullable
    private final zzcni zzbwE;
    private final String zzbwr;
    @Nullable
    private final zzcmy zzbxO;
    private final DiscoveryOptions zzbxP;
    @Nullable
    private final zzcna zzbxQ;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzcoz(@android.support.annotation.Nullable android.os.IBinder r9, @android.support.annotation.Nullable android.os.IBinder r10, java.lang.String r11, long r12, com.google.android.gms.nearby.connection.DiscoveryOptions r14, @android.support.annotation.Nullable android.os.IBinder r15) {
        /*
            r8 = this;
            r7 = 0
            if (r9 != 0) goto L_0x0011
            r1 = r7
        L_0x0004:
            if (r10 != 0) goto L_0x0025
            r2 = r7
        L_0x0007:
            if (r15 != 0) goto L_0x0039
        L_0x0009:
            r0 = r8
            r3 = r11
            r4 = r12
            r6 = r14
            r0.<init>((com.google.android.gms.internal.zzcni) r1, (com.google.android.gms.internal.zzcmy) r2, (java.lang.String) r3, (long) r4, (com.google.android.gms.nearby.connection.DiscoveryOptions) r6, (com.google.android.gms.internal.zzcna) r7)
            return
        L_0x0011:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IResultListener"
            android.os.IInterface r0 = r9.queryLocalInterface(r0)
            boolean r1 = r0 instanceof com.google.android.gms.internal.zzcni
            if (r1 == 0) goto L_0x001f
            com.google.android.gms.internal.zzcni r0 = (com.google.android.gms.internal.zzcni) r0
            r1 = r0
            goto L_0x0004
        L_0x001f:
            com.google.android.gms.internal.zzcnk r1 = new com.google.android.gms.internal.zzcnk
            r1.<init>(r9)
            goto L_0x0004
        L_0x0025:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IDiscoveryCallback"
            android.os.IInterface r0 = r10.queryLocalInterface(r0)
            boolean r2 = r0 instanceof com.google.android.gms.internal.zzcmy
            if (r2 == 0) goto L_0x0033
            com.google.android.gms.internal.zzcmy r0 = (com.google.android.gms.internal.zzcmy) r0
            r2 = r0
            goto L_0x0007
        L_0x0033:
            com.google.android.gms.internal.zzcmz r2 = new com.google.android.gms.internal.zzcmz
            r2.<init>(r10)
            goto L_0x0007
        L_0x0039:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IDiscoveryListener"
            android.os.IInterface r0 = r15.queryLocalInterface(r0)
            boolean r3 = r0 instanceof com.google.android.gms.internal.zzcna
            if (r3 == 0) goto L_0x0047
            com.google.android.gms.internal.zzcna r0 = (com.google.android.gms.internal.zzcna) r0
            r7 = r0
            goto L_0x0009
        L_0x0047:
            com.google.android.gms.internal.zzcnc r7 = new com.google.android.gms.internal.zzcnc
            r7.<init>(r15)
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcoz.<init>(android.os.IBinder, android.os.IBinder, java.lang.String, long, com.google.android.gms.nearby.connection.DiscoveryOptions, android.os.IBinder):void");
    }

    private zzcoz(@Nullable zzcni zzcni, @Nullable zzcmy zzcmy, String str, long j, DiscoveryOptions discoveryOptions, @Nullable zzcna zzcna) {
        this.zzbwE = zzcni;
        this.zzbxO = zzcmy;
        this.zzbwr = str;
        this.durationMillis = j;
        this.zzbxP = discoveryOptions;
        this.zzbxQ = zzcna;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcoz)) {
            return false;
        }
        zzcoz zzcoz = (zzcoz) obj;
        return zzbe.equal(this.zzbwE, zzcoz.zzbwE) && zzbe.equal(this.zzbxO, zzcoz.zzbxO) && zzbe.equal(this.zzbwr, zzcoz.zzbwr) && zzbe.equal(Long.valueOf(this.durationMillis), Long.valueOf(zzcoz.durationMillis)) && zzbe.equal(this.zzbxP, zzcoz.zzbxP) && zzbe.equal(this.zzbxQ, zzcoz.zzbxQ);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbwE, this.zzbxO, this.zzbwr, Long.valueOf(this.durationMillis), this.zzbxP, this.zzbxQ});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbwE == null ? null : this.zzbwE.asBinder(), false);
        zzd.zza(parcel, 2, this.zzbxO == null ? null : this.zzbxO.asBinder(), false);
        zzd.zza(parcel, 3, this.zzbwr, false);
        zzd.zza(parcel, 4, this.durationMillis);
        zzd.zza(parcel, 5, (Parcelable) this.zzbxP, i, false);
        if (this.zzbxQ != null) {
            iBinder = this.zzbxQ.asBinder();
        }
        zzd.zza(parcel, 6, iBinder, false);
        zzd.zzI(parcel, zze);
    }
}
