package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@zzzn
public final class zzaae extends zza {
    public static final Parcelable.Creator<zzaae> CREATOR = new zzaag();
    public final ApplicationInfo applicationInfo;
    public final int versionCode;
    public final boolean zzMe;
    @Nullable
    public final PackageInfo zzSA;
    public final String zzSB;
    public final String zzSC;
    public final String zzSD;
    public final Bundle zzSE;
    public final int zzSF;
    public final Bundle zzSG;
    public final boolean zzSH;
    public final int zzSI;
    public final int zzSJ;
    public final String zzSK;
    public final long zzSL;
    public final String zzSM;
    @Nullable
    public final List<String> zzSN;
    public final List<String> zzSO;
    public final long zzSP;
    public final String zzSQ;
    public final float zzSR;
    public final int zzSS;
    public final int zzST;
    public final boolean zzSU;
    public final boolean zzSV;
    public final String zzSW;
    public final boolean zzSX;
    public final String zzSY;
    public final int zzSZ;
    @Nullable
    public final Bundle zzSy;
    public final zzir zzSz;
    public final Bundle zzTa;
    public final String zzTb;
    public final boolean zzTc;
    public final Bundle zzTd;
    @Nullable
    public final String zzTe;
    @Nullable
    public final String zzTf;
    @Nullable
    public final String zzTg;
    public final boolean zzTh;
    public final String zzTi;
    public final List<String> zzTj;
    private String zzvQ;
    public final String zzvR;
    public final zzaje zzvT;
    public final zziv zzvX;
    public final zzon zzwj;
    @Nullable
    public final zzky zzwl;
    public final List<Integer> zzwn;
    public final List<String> zzwq;
    public final float zzxR;

    zzaae(int i, Bundle bundle, zzir zzir, zziv zziv, String str, ApplicationInfo applicationInfo2, PackageInfo packageInfo, String str2, String str3, String str4, zzaje zzaje, Bundle bundle2, int i2, List<String> list, Bundle bundle3, boolean z, int i3, int i4, float f, String str5, long j, String str6, List<String> list2, String str7, zzon zzon, List<String> list3, long j2, String str8, float f2, boolean z2, int i5, int i6, boolean z3, boolean z4, String str9, String str10, boolean z5, int i7, Bundle bundle4, String str11, zzky zzky, boolean z6, Bundle bundle5, String str12, String str13, String str14, boolean z7, List<Integer> list4, String str15, List<String> list5) {
        this.versionCode = i;
        this.zzSy = bundle;
        this.zzSz = zzir;
        this.zzvX = zziv;
        this.zzvR = str;
        this.applicationInfo = applicationInfo2;
        this.zzSA = packageInfo;
        this.zzSB = str2;
        this.zzSC = str3;
        this.zzSD = str4;
        this.zzvT = zzaje;
        this.zzSE = bundle2;
        this.zzSF = i2;
        this.zzwq = list;
        this.zzSO = list3 == null ? Collections.emptyList() : Collections.unmodifiableList(list3);
        this.zzSG = bundle3;
        this.zzSH = z;
        this.zzSI = i3;
        this.zzSJ = i4;
        this.zzxR = f;
        this.zzSK = str5;
        this.zzSL = j;
        this.zzSM = str6;
        this.zzSN = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
        this.zzvQ = str7;
        this.zzwj = zzon;
        this.zzSP = j2;
        this.zzSQ = str8;
        this.zzSR = f2;
        this.zzSX = z2;
        this.zzSS = i5;
        this.zzST = i6;
        this.zzSU = z3;
        this.zzSV = z4;
        this.zzSW = str9;
        this.zzSY = str10;
        this.zzMe = z5;
        this.zzSZ = i7;
        this.zzTa = bundle4;
        this.zzTb = str11;
        this.zzwl = zzky;
        this.zzTc = z6;
        this.zzTd = bundle5;
        this.zzTe = str12;
        this.zzTf = str13;
        this.zzTg = str14;
        this.zzTh = z7;
        this.zzwn = list4;
        this.zzTi = str15;
        this.zzTj = list5;
    }

    private zzaae(@Nullable Bundle bundle, zzir zzir, zziv zziv, String str, ApplicationInfo applicationInfo2, @Nullable PackageInfo packageInfo, String str2, String str3, String str4, zzaje zzaje, Bundle bundle2, int i, List<String> list, List<String> list2, Bundle bundle3, boolean z, int i2, int i3, float f, String str5, long j, String str6, @Nullable List<String> list3, String str7, zzon zzon, long j2, String str8, float f2, boolean z2, int i4, int i5, boolean z3, boolean z4, String str9, String str10, boolean z5, int i6, Bundle bundle4, String str11, @Nullable zzky zzky, boolean z6, Bundle bundle5, String str12, String str13, String str14, boolean z7, List<Integer> list4, String str15, List<String> list5) {
        this(23, bundle, zzir, zziv, str, applicationInfo2, packageInfo, str2, str3, str4, zzaje, bundle2, i, list, bundle3, z, i2, i3, f, str5, j, str6, list3, str7, zzon, list2, j2, str8, f2, z2, i4, i5, z3, z4, str9, str10, z5, i6, bundle4, str11, zzky, z6, bundle5, str12, str13, str14, z7, list4, str15, list5);
    }

    public zzaae(zzaaf zzaaf, long j, String str, String str2, String str3) {
        this(zzaaf.zzSy, zzaaf.zzSz, zzaaf.zzvX, zzaaf.zzvR, zzaaf.applicationInfo, zzaaf.zzSA, (String) zzaji.zza(zzaaf.zzTl, ""), zzaaf.zzSC, zzaaf.zzSD, zzaaf.zzvT, zzaaf.zzSE, zzaaf.zzSF, zzaaf.zzwq, zzaaf.zzSO, zzaaf.zzSG, zzaaf.zzSH, zzaaf.zzSI, zzaaf.zzSJ, zzaaf.zzxR, zzaaf.zzSK, zzaaf.zzSL, zzaaf.zzSM, zzaaf.zzSN, zzaaf.zzvQ, zzaaf.zzwj, j, zzaaf.zzSQ, zzaaf.zzSR, zzaaf.zzSX, zzaaf.zzSS, zzaaf.zzST, zzaaf.zzSU, zzaaf.zzSV, (String) zzaji.zza(zzaaf.zzTk, "", 1, TimeUnit.SECONDS), zzaaf.zzSY, zzaaf.zzMe, zzaaf.zzSZ, zzaaf.zzTa, zzaaf.zzTb, zzaaf.zzwl, zzaaf.zzTc, zzaaf.zzTd, str, str2, str3, zzaaf.zzTh, zzaaf.zzwn, zzaaf.zzTi, zzaaf.zzTj);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, 2, this.zzSy, false);
        zzd.zza(parcel, 3, (Parcelable) this.zzSz, i, false);
        zzd.zza(parcel, 4, (Parcelable) this.zzvX, i, false);
        zzd.zza(parcel, 5, this.zzvR, false);
        zzd.zza(parcel, 6, (Parcelable) this.applicationInfo, i, false);
        zzd.zza(parcel, 7, (Parcelable) this.zzSA, i, false);
        zzd.zza(parcel, 8, this.zzSB, false);
        zzd.zza(parcel, 9, this.zzSC, false);
        zzd.zza(parcel, 10, this.zzSD, false);
        zzd.zza(parcel, 11, (Parcelable) this.zzvT, i, false);
        zzd.zza(parcel, 12, this.zzSE, false);
        zzd.zzc(parcel, 13, this.zzSF);
        zzd.zzb(parcel, 14, this.zzwq, false);
        zzd.zza(parcel, 15, this.zzSG, false);
        zzd.zza(parcel, 16, this.zzSH);
        zzd.zzc(parcel, 18, this.zzSI);
        zzd.zzc(parcel, 19, this.zzSJ);
        zzd.zza(parcel, 20, this.zzxR);
        zzd.zza(parcel, 21, this.zzSK, false);
        zzd.zza(parcel, 25, this.zzSL);
        zzd.zza(parcel, 26, this.zzSM, false);
        zzd.zzb(parcel, 27, this.zzSN, false);
        zzd.zza(parcel, 28, this.zzvQ, false);
        zzd.zza(parcel, 29, (Parcelable) this.zzwj, i, false);
        zzd.zzb(parcel, 30, this.zzSO, false);
        zzd.zza(parcel, 31, this.zzSP);
        zzd.zza(parcel, 33, this.zzSQ, false);
        zzd.zza(parcel, 34, this.zzSR);
        zzd.zzc(parcel, 35, this.zzSS);
        zzd.zzc(parcel, 36, this.zzST);
        zzd.zza(parcel, 37, this.zzSU);
        zzd.zza(parcel, 38, this.zzSV);
        zzd.zza(parcel, 39, this.zzSW, false);
        zzd.zza(parcel, 40, this.zzSX);
        zzd.zza(parcel, 41, this.zzSY, false);
        zzd.zza(parcel, 42, this.zzMe);
        zzd.zzc(parcel, 43, this.zzSZ);
        zzd.zza(parcel, 44, this.zzTa, false);
        zzd.zza(parcel, 45, this.zzTb, false);
        zzd.zza(parcel, 46, (Parcelable) this.zzwl, i, false);
        zzd.zza(parcel, 47, this.zzTc);
        zzd.zza(parcel, 48, this.zzTd, false);
        zzd.zza(parcel, 49, this.zzTe, false);
        zzd.zza(parcel, 50, this.zzTf, false);
        zzd.zza(parcel, 51, this.zzTg, false);
        zzd.zza(parcel, 52, this.zzTh);
        zzd.zza(parcel, 53, this.zzwn, false);
        zzd.zza(parcel, 54, this.zzTi, false);
        zzd.zzb(parcel, 55, this.zzTj, false);
        zzd.zzI(parcel, zze);
    }
}
