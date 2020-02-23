package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public final class zzazu extends zza {
    public static final Parcelable.Creator<zzazu> CREATOR = new zzazv();
    private boolean zzazA;
    public final aej zzazB;
    public zzbak zzazE;
    public byte[] zzazF;
    private int[] zzazG;
    private String[] zzazH;
    private int[] zzazI;
    private byte[][] zzazJ;
    private zzcqn[] zzazK;
    public final zzazr zzazL;
    public final zzazr zzazu;

    public zzazu(zzbak zzbak, aej aej, zzazr zzazr, zzazr zzazr2, int[] iArr, String[] strArr, int[] iArr2, byte[][] bArr, zzcqn[] zzcqnArr, boolean z) {
        this.zzazE = zzbak;
        this.zzazB = aej;
        this.zzazu = zzazr;
        this.zzazL = null;
        this.zzazG = iArr;
        this.zzazH = null;
        this.zzazI = iArr2;
        this.zzazJ = null;
        this.zzazK = null;
        this.zzazA = z;
    }

    zzazu(zzbak zzbak, byte[] bArr, int[] iArr, String[] strArr, int[] iArr2, byte[][] bArr2, boolean z, zzcqn[] zzcqnArr) {
        this.zzazE = zzbak;
        this.zzazF = bArr;
        this.zzazG = iArr;
        this.zzazH = strArr;
        this.zzazB = null;
        this.zzazu = null;
        this.zzazL = null;
        this.zzazI = iArr2;
        this.zzazJ = bArr2;
        this.zzazK = zzcqnArr;
        this.zzazA = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzazu)) {
            return false;
        }
        zzazu zzazu2 = (zzazu) obj;
        return zzbe.equal(this.zzazE, zzazu2.zzazE) && Arrays.equals(this.zzazF, zzazu2.zzazF) && Arrays.equals(this.zzazG, zzazu2.zzazG) && Arrays.equals(this.zzazH, zzazu2.zzazH) && zzbe.equal(this.zzazB, zzazu2.zzazB) && zzbe.equal(this.zzazu, zzazu2.zzazu) && zzbe.equal(this.zzazL, zzazu2.zzazL) && Arrays.equals(this.zzazI, zzazu2.zzazI) && Arrays.deepEquals(this.zzazJ, zzazu2.zzazJ) && Arrays.equals(this.zzazK, zzazu2.zzazK) && this.zzazA == zzazu2.zzazA;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzazE, this.zzazF, this.zzazG, this.zzazH, this.zzazB, this.zzazu, this.zzazL, this.zzazI, this.zzazJ, this.zzazK, Boolean.valueOf(this.zzazA)});
    }

    public final String toString() {
        return "LogEventParcelable[" + this.zzazE + ", LogEventBytes: " + (this.zzazF == null ? null : new String(this.zzazF)) + ", TestCodes: " + Arrays.toString(this.zzazG) + ", MendelPackages: " + Arrays.toString(this.zzazH) + ", LogEvent: " + this.zzazB + ", ExtensionProducer: " + this.zzazu + ", VeProducer: " + this.zzazL + ", ExperimentIDs: " + Arrays.toString(this.zzazI) + ", ExperimentTokens: " + Arrays.toString(this.zzazJ) + ", ExperimentTokensParcelables: " + Arrays.toString(this.zzazK) + ", AddPhenotypeExperimentTokens: " + this.zzazA + "]";
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) this.zzazE, i, false);
        zzd.zza(parcel, 3, this.zzazF, false);
        zzd.zza(parcel, 4, this.zzazG, false);
        zzd.zza(parcel, 5, this.zzazH, false);
        zzd.zza(parcel, 6, this.zzazI, false);
        zzd.zza(parcel, 7, this.zzazJ, false);
        zzd.zza(parcel, 8, this.zzazA);
        zzd.zza(parcel, 9, (T[]) this.zzazK, i, false);
        zzd.zzI(parcel, zze);
    }
}
