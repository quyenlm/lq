package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.nearby.messages.internal.zzg;
import com.google.android.gms.nearby.messages.internal.zzl;
import java.util.UUID;

public final class zzcpl extends zza {
    public static final Parcelable.Creator<zzcpl> CREATOR = new zzcpm();
    private int zzaku;
    private int zzbyP;
    private byte[] zzbyQ;
    private boolean zzbyR;

    zzcpl(int i, int i2, byte[] bArr, boolean z) {
        this.zzaku = i;
        this.zzbyP = i2;
        this.zzbyQ = bArr;
        this.zzbyR = z;
    }

    private zzcpl(int i, byte[] bArr) {
        this(1, i, bArr, false);
    }

    public static zzcpl zzT(String str, @Nullable String str2) {
        String valueOf = String.valueOf(str);
        if (str2 == null) {
            str2 = "";
        }
        String valueOf2 = String.valueOf(str2);
        return new zzcpl(2, new zzg(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)).getBytes());
    }

    public static zzcpl zza(UUID uuid, @Nullable Short sh, @Nullable Short sh2) {
        return new zzcpl(3, new zzl(uuid, sh, sh2).getBytes());
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzbyP);
        zzd.zza(parcel, 2, this.zzbyQ, false);
        zzd.zza(parcel, 3, this.zzbyR);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
