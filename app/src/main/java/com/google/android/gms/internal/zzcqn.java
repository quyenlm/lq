package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.tp.a.h;
import com.vk.sdk.VKScope;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class zzcqn extends zza {
    public static final Parcelable.Creator<zzcqn> CREATOR = new zzcqt();
    private static byte[][] zzazi = new byte[0][];
    private static zzcqn zzbAc = new zzcqn("", (byte[]) null, zzazi, zzazi, zzazi, zzazi, (int[]) null, (byte[][]) null);
    private static final zzcqs zzbAl = new zzcqo();
    private static final zzcqs zzbAm = new zzcqp();
    private static final zzcqs zzbAn = new zzcqq();
    private static final zzcqs zzbAo = new zzcqr();
    private String zzbAd;
    private byte[] zzbAe;
    private byte[][] zzbAf;
    private byte[][] zzbAg;
    private byte[][] zzbAh;
    private byte[][] zzbAi;
    private int[] zzbAj;
    private byte[][] zzbAk;

    public zzcqn(String str, byte[] bArr, byte[][] bArr2, byte[][] bArr3, byte[][] bArr4, byte[][] bArr5, int[] iArr, byte[][] bArr6) {
        this.zzbAd = str;
        this.zzbAe = bArr;
        this.zzbAf = bArr2;
        this.zzbAg = bArr3;
        this.zzbAh = bArr4;
        this.zzbAi = bArr5;
        this.zzbAj = iArr;
        this.zzbAk = bArr6;
    }

    private static void zza(StringBuilder sb, String str, int[] iArr) {
        sb.append(str);
        sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
        if (iArr == null) {
            sb.append(Constants.NULL_VERSION_ID);
            return;
        }
        boolean z = true;
        sb.append(h.a);
        int length = iArr.length;
        int i = 0;
        while (i < length) {
            int i2 = iArr[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append(i2);
            i++;
            z = false;
        }
        sb.append(h.b);
    }

    private static void zza(StringBuilder sb, String str, byte[][] bArr) {
        sb.append(str);
        sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
        if (bArr == null) {
            sb.append(Constants.NULL_VERSION_ID);
            return;
        }
        boolean z = true;
        sb.append(h.a);
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            byte[] bArr2 = bArr[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append("'");
            sb.append(Base64.encodeToString(bArr2, 3));
            sb.append("'");
            i++;
            z = false;
        }
        sb.append(h.b);
    }

    private static List<String> zzb(byte[][] bArr) {
        if (bArr == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(bArr.length);
        for (byte[] encodeToString : bArr) {
            arrayList.add(Base64.encodeToString(encodeToString, 3));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static List<Integer> zzc(int[] iArr) {
        if (iArr == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int valueOf : iArr) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzcqn)) {
            return false;
        }
        zzcqn zzcqn = (zzcqn) obj;
        return zzcqu.equals(this.zzbAd, zzcqn.zzbAd) && Arrays.equals(this.zzbAe, zzcqn.zzbAe) && zzcqu.equals(zzb(this.zzbAf), zzb(zzcqn.zzbAf)) && zzcqu.equals(zzb(this.zzbAg), zzb(zzcqn.zzbAg)) && zzcqu.equals(zzb(this.zzbAh), zzb(zzcqn.zzbAh)) && zzcqu.equals(zzb(this.zzbAi), zzb(zzcqn.zzbAi)) && zzcqu.equals(zzc(this.zzbAj), zzc(zzcqn.zzbAj)) && zzcqu.equals(zzb(this.zzbAk), zzb(zzcqn.zzbAk));
    }

    public final String toString() {
        String sb;
        StringBuilder sb2 = new StringBuilder("ExperimentTokens");
        sb2.append(h.a);
        if (this.zzbAd == null) {
            sb = Constants.NULL_VERSION_ID;
        } else {
            String valueOf = String.valueOf("'");
            String str = this.zzbAd;
            String valueOf2 = String.valueOf("'");
            sb = new StringBuilder(String.valueOf(valueOf).length() + String.valueOf(str).length() + String.valueOf(valueOf2).length()).append(valueOf).append(str).append(valueOf2).toString();
        }
        sb2.append(sb);
        sb2.append(", ");
        byte[] bArr = this.zzbAe;
        sb2.append(VKScope.DIRECT);
        sb2.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
        if (bArr == null) {
            sb2.append(Constants.NULL_VERSION_ID);
        } else {
            sb2.append("'");
            sb2.append(Base64.encodeToString(bArr, 3));
            sb2.append("'");
        }
        sb2.append(", ");
        zza(sb2, "GAIA", this.zzbAf);
        sb2.append(", ");
        zza(sb2, "PSEUDO", this.zzbAg);
        sb2.append(", ");
        zza(sb2, "ALWAYS", this.zzbAh);
        sb2.append(", ");
        zza(sb2, "OTHER", this.zzbAi);
        sb2.append(", ");
        zza(sb2, "weak", this.zzbAj);
        sb2.append(", ");
        zza(sb2, "directs", this.zzbAk);
        sb2.append(h.b);
        return sb2.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzbAd, false);
        zzd.zza(parcel, 3, this.zzbAe, false);
        zzd.zza(parcel, 4, this.zzbAf, false);
        zzd.zza(parcel, 5, this.zzbAg, false);
        zzd.zza(parcel, 6, this.zzbAh, false);
        zzd.zza(parcel, 7, this.zzbAi, false);
        zzd.zza(parcel, 8, this.zzbAj, false);
        zzd.zza(parcel, 9, this.zzbAk, false);
        zzd.zzI(parcel, zze);
    }
}
