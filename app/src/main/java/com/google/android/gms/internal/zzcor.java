package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public final class zzcor extends zza {
    public static final Parcelable.Creator<zzcor> CREATOR = new zzcos();
    @Nullable
    private final zzcni zzbwE;
    private final String zzbwG;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzcor(@android.support.annotation.Nullable android.os.IBinder r3, java.lang.String r4) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0007
            r0 = 0
        L_0x0003:
            r2.<init>((com.google.android.gms.internal.zzcni) r0, (java.lang.String) r4)
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcor.<init>(android.os.IBinder, java.lang.String):void");
    }

    private zzcor(@Nullable zzcni zzcni, String str) {
        this.zzbwE = zzcni;
        this.zzbwG = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcor)) {
            return false;
        }
        zzcor zzcor = (zzcor) obj;
        return zzbe.equal(this.zzbwE, zzcor.zzbwE) && zzbe.equal(this.zzbwG, zzcor.zzbwG);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbwE, this.zzbwG});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbwE == null ? null : this.zzbwE.asBinder(), false);
        zzd.zza(parcel, 2, this.zzbwG, false);
        zzd.zzI(parcel, zze);
    }
}
