package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public final class zzcki extends zza {
    public static final Parcelable.Creator<zzcki> CREATOR = new zzckj();
    @Nullable
    private final zzcni zzbwE;
    @Nullable
    private final zzcmp zzbwF;
    private final String zzbwG;
    @Nullable
    private final byte[] zzbwH;
    @Nullable
    private final zzcnf zzbwI;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzcki(@android.support.annotation.Nullable android.os.IBinder r7, @android.support.annotation.Nullable android.os.IBinder r8, java.lang.String r9, @android.support.annotation.Nullable byte[] r10, @android.support.annotation.Nullable android.os.IBinder r11) {
        /*
            r6 = this;
            r5 = 0
            if (r7 != 0) goto L_0x0010
            r1 = r5
        L_0x0004:
            if (r8 != 0) goto L_0x0024
            r2 = r5
        L_0x0007:
            if (r11 != 0) goto L_0x0038
        L_0x0009:
            r0 = r6
            r3 = r9
            r4 = r10
            r0.<init>((com.google.android.gms.internal.zzcni) r1, (com.google.android.gms.internal.zzcmp) r2, (java.lang.String) r3, (byte[]) r4, (com.google.android.gms.internal.zzcnf) r5)
            return
        L_0x0010:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IResultListener"
            android.os.IInterface r0 = r7.queryLocalInterface(r0)
            boolean r1 = r0 instanceof com.google.android.gms.internal.zzcni
            if (r1 == 0) goto L_0x001e
            com.google.android.gms.internal.zzcni r0 = (com.google.android.gms.internal.zzcni) r0
            r1 = r0
            goto L_0x0004
        L_0x001e:
            com.google.android.gms.internal.zzcnk r1 = new com.google.android.gms.internal.zzcnk
            r1.<init>(r7)
            goto L_0x0004
        L_0x0024:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IConnectionEventListener"
            android.os.IInterface r0 = r8.queryLocalInterface(r0)
            boolean r2 = r0 instanceof com.google.android.gms.internal.zzcmp
            if (r2 == 0) goto L_0x0032
            com.google.android.gms.internal.zzcmp r0 = (com.google.android.gms.internal.zzcmp) r0
            r2 = r0
            goto L_0x0007
        L_0x0032:
            com.google.android.gms.internal.zzcmr r2 = new com.google.android.gms.internal.zzcmr
            r2.<init>(r8)
            goto L_0x0007
        L_0x0038:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IPayloadListener"
            android.os.IInterface r0 = r11.queryLocalInterface(r0)
            boolean r3 = r0 instanceof com.google.android.gms.internal.zzcnf
            if (r3 == 0) goto L_0x0046
            com.google.android.gms.internal.zzcnf r0 = (com.google.android.gms.internal.zzcnf) r0
            r5 = r0
            goto L_0x0009
        L_0x0046:
            com.google.android.gms.internal.zzcnh r5 = new com.google.android.gms.internal.zzcnh
            r5.<init>(r11)
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcki.<init>(android.os.IBinder, android.os.IBinder, java.lang.String, byte[], android.os.IBinder):void");
    }

    private zzcki(@Nullable zzcni zzcni, @Nullable zzcmp zzcmp, String str, @Nullable byte[] bArr, @Nullable zzcnf zzcnf) {
        this.zzbwE = zzcni;
        this.zzbwF = zzcmp;
        this.zzbwG = str;
        this.zzbwH = bArr;
        this.zzbwI = zzcnf;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcki)) {
            return false;
        }
        zzcki zzcki = (zzcki) obj;
        return zzbe.equal(this.zzbwE, zzcki.zzbwE) && zzbe.equal(this.zzbwF, zzcki.zzbwF) && zzbe.equal(this.zzbwG, zzcki.zzbwG) && zzbe.equal(this.zzbwH, zzcki.zzbwH) && zzbe.equal(this.zzbwI, zzcki.zzbwI);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbwE, this.zzbwF, this.zzbwG, this.zzbwH, this.zzbwI});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbwE == null ? null : this.zzbwE.asBinder(), false);
        zzd.zza(parcel, 2, this.zzbwF == null ? null : this.zzbwF.asBinder(), false);
        zzd.zza(parcel, 3, this.zzbwG, false);
        zzd.zza(parcel, 4, this.zzbwH, false);
        if (this.zzbwI != null) {
            iBinder = this.zzbwI.asBinder();
        }
        zzd.zza(parcel, 5, iBinder, false);
        zzd.zzI(parcel, zze);
    }
}
