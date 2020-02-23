package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;

public final class zzx extends zza {
    public static final Parcelable.Creator<zzx> CREATOR = new zzy();
    private int version;
    Account zzaHA;
    zzc[] zzaHB;
    private int zzaHu;
    private int zzaHv;
    String zzaHw;
    IBinder zzaHx;
    Scope[] zzaHy;
    Bundle zzaHz;

    public zzx(int i) {
        this.version = 3;
        this.zzaHv = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzaHu = i;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: android.accounts.Account} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v6, types: [com.google.android.gms.common.internal.zzal] */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    zzx(int r3, int r4, int r5, java.lang.String r6, android.os.IBinder r7, com.google.android.gms.common.api.Scope[] r8, android.os.Bundle r9, android.accounts.Account r10, com.google.android.gms.common.zzc[] r11) {
        /*
            r2 = this;
            r0 = 0
            r2.<init>()
            r2.version = r3
            r2.zzaHu = r4
            r2.zzaHv = r5
            java.lang.String r1 = "com.google.android.gms"
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L_0x002a
            java.lang.String r1 = "com.google.android.gms"
            r2.zzaHw = r1
        L_0x0016:
            r1 = 2
            if (r3 >= r1) goto L_0x0040
            if (r7 == 0) goto L_0x0021
            if (r7 != 0) goto L_0x002d
        L_0x001d:
            android.accounts.Account r0 = com.google.android.gms.common.internal.zza.zza(r0)
        L_0x0021:
            r2.zzaHA = r0
        L_0x0023:
            r2.zzaHy = r8
            r2.zzaHz = r9
            r2.zzaHB = r11
            return
        L_0x002a:
            r2.zzaHw = r6
            goto L_0x0016
        L_0x002d:
            java.lang.String r0 = "com.google.android.gms.common.internal.IAccountAccessor"
            android.os.IInterface r0 = r7.queryLocalInterface(r0)
            boolean r1 = r0 instanceof com.google.android.gms.common.internal.zzal
            if (r1 == 0) goto L_0x003a
            com.google.android.gms.common.internal.zzal r0 = (com.google.android.gms.common.internal.zzal) r0
            goto L_0x001d
        L_0x003a:
            com.google.android.gms.common.internal.zzan r0 = new com.google.android.gms.common.internal.zzan
            r0.<init>(r7)
            goto L_0x001d
        L_0x0040:
            r2.zzaHx = r7
            r2.zzaHA = r10
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzx.<init>(int, int, int, java.lang.String, android.os.IBinder, com.google.android.gms.common.api.Scope[], android.os.Bundle, android.accounts.Account, com.google.android.gms.common.zzc[]):void");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.version);
        zzd.zzc(parcel, 2, this.zzaHu);
        zzd.zzc(parcel, 3, this.zzaHv);
        zzd.zza(parcel, 4, this.zzaHw, false);
        zzd.zza(parcel, 5, this.zzaHx, false);
        zzd.zza(parcel, 6, (T[]) this.zzaHy, i, false);
        zzd.zza(parcel, 7, this.zzaHz, false);
        zzd.zza(parcel, 8, (Parcelable) this.zzaHA, i, false);
        zzd.zza(parcel, 10, (T[]) this.zzaHB, i, false);
        zzd.zzI(parcel, zze);
    }
}
