package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public final class zzcot extends zza {
    public static final Parcelable.Creator<zzcot> CREATOR = new zzcou();
    @Nullable
    private final String name;
    @Nullable
    private final zzcni zzbwE;
    @Nullable
    private final zzcmp zzbwF;
    private final String zzbwG;
    @Nullable
    private final byte[] zzbwH;
    @Nullable
    private final zzcmv zzbxH;
    @Nullable
    private final zzcms zzbxI;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzcot(@android.support.annotation.Nullable android.os.IBinder r9, @android.support.annotation.Nullable android.os.IBinder r10, @android.support.annotation.Nullable android.os.IBinder r11, @android.support.annotation.Nullable java.lang.String r12, java.lang.String r13, @android.support.annotation.Nullable byte[] r14, @android.support.annotation.Nullable android.os.IBinder r15) {
        /*
            r8 = this;
            r7 = 0
            if (r9 != 0) goto L_0x0014
            r1 = r7
        L_0x0004:
            if (r10 != 0) goto L_0x0028
            r2 = r7
        L_0x0007:
            if (r11 != 0) goto L_0x003c
            r3 = r7
        L_0x000a:
            if (r15 != 0) goto L_0x0050
        L_0x000c:
            r0 = r8
            r4 = r12
            r5 = r13
            r6 = r14
            r0.<init>((com.google.android.gms.internal.zzcni) r1, (com.google.android.gms.internal.zzcmp) r2, (com.google.android.gms.internal.zzcmv) r3, (java.lang.String) r4, (java.lang.String) r5, (byte[]) r6, (com.google.android.gms.internal.zzcms) r7)
            return
        L_0x0014:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IResultListener"
            android.os.IInterface r0 = r9.queryLocalInterface(r0)
            boolean r1 = r0 instanceof com.google.android.gms.internal.zzcni
            if (r1 == 0) goto L_0x0022
            com.google.android.gms.internal.zzcni r0 = (com.google.android.gms.internal.zzcni) r0
            r1 = r0
            goto L_0x0004
        L_0x0022:
            com.google.android.gms.internal.zzcnk r1 = new com.google.android.gms.internal.zzcnk
            r1.<init>(r9)
            goto L_0x0004
        L_0x0028:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IConnectionEventListener"
            android.os.IInterface r0 = r10.queryLocalInterface(r0)
            boolean r2 = r0 instanceof com.google.android.gms.internal.zzcmp
            if (r2 == 0) goto L_0x0036
            com.google.android.gms.internal.zzcmp r0 = (com.google.android.gms.internal.zzcmp) r0
            r2 = r0
            goto L_0x0007
        L_0x0036:
            com.google.android.gms.internal.zzcmr r2 = new com.google.android.gms.internal.zzcmr
            r2.<init>(r10)
            goto L_0x0007
        L_0x003c:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IConnectionResponseListener"
            android.os.IInterface r0 = r11.queryLocalInterface(r0)
            boolean r3 = r0 instanceof com.google.android.gms.internal.zzcmv
            if (r3 == 0) goto L_0x004a
            com.google.android.gms.internal.zzcmv r0 = (com.google.android.gms.internal.zzcmv) r0
            r3 = r0
            goto L_0x000a
        L_0x004a:
            com.google.android.gms.internal.zzcmx r3 = new com.google.android.gms.internal.zzcmx
            r3.<init>(r11)
            goto L_0x000a
        L_0x0050:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IConnectionLifecycleListener"
            android.os.IInterface r0 = r15.queryLocalInterface(r0)
            boolean r4 = r0 instanceof com.google.android.gms.internal.zzcms
            if (r4 == 0) goto L_0x005e
            com.google.android.gms.internal.zzcms r0 = (com.google.android.gms.internal.zzcms) r0
            r7 = r0
            goto L_0x000c
        L_0x005e:
            com.google.android.gms.internal.zzcmu r7 = new com.google.android.gms.internal.zzcmu
            r7.<init>(r15)
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcot.<init>(android.os.IBinder, android.os.IBinder, android.os.IBinder, java.lang.String, java.lang.String, byte[], android.os.IBinder):void");
    }

    private zzcot(@Nullable zzcni zzcni, @Nullable zzcmp zzcmp, @Nullable zzcmv zzcmv, @Nullable String str, String str2, @Nullable byte[] bArr, @Nullable zzcms zzcms) {
        this.zzbwE = zzcni;
        this.zzbwF = zzcmp;
        this.zzbxH = zzcmv;
        this.name = str;
        this.zzbwG = str2;
        this.zzbwH = bArr;
        this.zzbxI = zzcms;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcot)) {
            return false;
        }
        zzcot zzcot = (zzcot) obj;
        return zzbe.equal(this.zzbwE, zzcot.zzbwE) && zzbe.equal(this.zzbwF, zzcot.zzbwF) && zzbe.equal(this.zzbxH, zzcot.zzbxH) && zzbe.equal(this.name, zzcot.name) && zzbe.equal(this.zzbwG, zzcot.zzbwG) && zzbe.equal(this.zzbwH, zzcot.zzbwH) && zzbe.equal(this.zzbxI, zzcot.zzbxI);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbwE, this.zzbwF, this.zzbxH, this.name, this.zzbwG, this.zzbwH, this.zzbxI});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbwE == null ? null : this.zzbwE.asBinder(), false);
        zzd.zza(parcel, 2, this.zzbwF == null ? null : this.zzbwF.asBinder(), false);
        zzd.zza(parcel, 3, this.zzbxH == null ? null : this.zzbxH.asBinder(), false);
        zzd.zza(parcel, 4, this.name, false);
        zzd.zza(parcel, 5, this.zzbwG, false);
        zzd.zza(parcel, 6, this.zzbwH, false);
        if (this.zzbxI != null) {
            iBinder = this.zzbxI.asBinder();
        }
        zzd.zza(parcel, 7, iBinder, false);
        zzd.zzI(parcel, zze);
    }
}
