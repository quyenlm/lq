package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public final class zzcov extends zza {
    public static final Parcelable.Creator<zzcov> CREATOR = new zzcow();
    @Nullable
    private final zzcni zzbwE;
    private final String[] zzbxJ;
    private final boolean zzbxK;
    @Nullable
    private final zzcoo zzbxr;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzcov(@android.support.annotation.Nullable android.os.IBinder r3, java.lang.String[] r4, @android.support.annotation.Nullable com.google.android.gms.internal.zzcoo r5, boolean r6) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0007
            r0 = 0
        L_0x0003:
            r2.<init>((com.google.android.gms.internal.zzcni) r0, (java.lang.String[]) r4, (com.google.android.gms.internal.zzcoo) r5, (boolean) r6)
            return
        L_0x0007:
            java.lang.String r0 = "com.google.android.gms.nearby.internal.connection.IResultListener"
            android.os.IInterface r0 = r3.queryLocalInterface(r0)
            boolean r1 = r0 instanceof com.google.android.gms.internal.zzcni
            if (r1 == 0) goto L_0x0014
            com.google.android.gms.internal.zzcni r0 = (com.google.android.gms.internal.zzcni) r0
            goto L_0x0003
        L_0x0014:
            com.google.android.gms.internal.zzcnk r0 = new com.google.android.gms.internal.zzcnk
            r0.<init>(r3)
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcov.<init>(android.os.IBinder, java.lang.String[], com.google.android.gms.internal.zzcoo, boolean):void");
    }

    private zzcov(@Nullable zzcni zzcni, String[] strArr, @Nullable zzcoo zzcoo, boolean z) {
        this.zzbwE = zzcni;
        this.zzbxJ = strArr;
        this.zzbxr = zzcoo;
        this.zzbxK = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcov)) {
            return false;
        }
        zzcov zzcov = (zzcov) obj;
        return zzbe.equal(this.zzbwE, zzcov.zzbwE) && Arrays.equals(this.zzbxJ, zzcov.zzbxJ) && zzbe.equal(this.zzbxr, zzcov.zzbxr) && zzbe.equal(Boolean.valueOf(this.zzbxK), Boolean.valueOf(zzcov.zzbxK));
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbwE, this.zzbxJ, this.zzbxr, Boolean.valueOf(this.zzbxK)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbwE == null ? null : this.zzbwE.asBinder(), false);
        zzd.zza(parcel, 2, this.zzbxJ, false);
        zzd.zza(parcel, 3, (Parcelable) this.zzbxr, i, false);
        zzd.zza(parcel, 4, this.zzbxK);
        zzd.zzI(parcel, zze);
    }
}
