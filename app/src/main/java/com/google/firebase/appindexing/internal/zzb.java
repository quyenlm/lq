package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzb extends zza {
    public static final Parcelable.Creator<zzb> CREATOR = new zzy();
    private int zzajK = 0;
    private final String zzakh;
    private final boolean zzbVA;
    private final boolean zzbVB;
    private final String zzbVI;
    private final byte[] zzbVJ;

    zzb(int i, boolean z, String str, String str2, byte[] bArr, boolean z2) {
        this.zzajK = i;
        this.zzbVA = z;
        this.zzbVI = str;
        this.zzakh = str2;
        this.zzbVJ = bArr;
        this.zzbVB = z2;
    }

    public zzb(boolean z, String str, String str2, byte[] bArr, boolean z2) {
        this.zzbVA = z;
        this.zzbVI = null;
        this.zzakh = null;
        this.zzbVJ = null;
        this.zzbVB = false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MetadataImpl { ");
        sb.append("{ eventStatus: '").append(this.zzajK).append("' } ");
        sb.append("{ uploadable: '").append(this.zzbVA).append("' } ");
        if (this.zzbVI != null) {
            sb.append("{ completionToken: '").append(this.zzbVI).append("' } ");
        }
        if (this.zzakh != null) {
            sb.append("{ accountName: '").append(this.zzakh).append("' } ");
        }
        if (this.zzbVJ != null) {
            sb.append("{ ssbContext: [ ");
            for (byte hexString : this.zzbVJ) {
                sb.append("0x").append(Integer.toHexString(hexString)).append(" ");
            }
            sb.append("] } ");
        }
        sb.append("{ contextOnly: '").append(this.zzbVB).append("' } ");
        sb.append("}");
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzajK);
        zzd.zza(parcel, 2, this.zzbVA);
        zzd.zza(parcel, 3, this.zzbVI, false);
        zzd.zza(parcel, 4, this.zzakh, false);
        zzd.zza(parcel, 5, this.zzbVJ, false);
        zzd.zza(parcel, 6, this.zzbVB);
        zzd.zzI(parcel, zze);
    }

    public final void zzbS(int i) {
        this.zzajK = i;
    }
}
