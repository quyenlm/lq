package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

@zzzn
public final class zzaff {
    public final int errorCode;
    public final int orientation;
    @Nullable
    public final zzua zzMG;
    @Nullable
    public final zzut zzMH;
    @Nullable
    public final String zzMI;
    @Nullable
    public final zzud zzMJ;
    public final List<String> zzMa;
    public final List<String> zzMb;
    @Nullable
    public final List<String> zzMd;
    public final long zzMg;
    @Nullable
    public final zzaka zzPg;
    public final String zzSC;
    public final zzir zzSz;
    @Nullable
    public final zzaee zzTD;
    @Nullable
    public final List<String> zzTF;
    public final boolean zzTG;
    private zzaak zzTH;
    public final String zzTK;
    private long zzTn;
    public final boolean zzTo;
    private long zzTp;
    public final List<String> zzTq;
    public final String zzTt;
    public final JSONObject zzXL;
    public boolean zzXM;
    public final zzub zzXN;
    @Nullable
    public final String zzXO;
    public final zziv zzXP;
    @Nullable
    public final List<String> zzXQ;
    public final long zzXR;
    public final long zzXS;
    @Nullable
    public final zzoa zzXT;
    public boolean zzXU;
    public boolean zzXV;
    public boolean zzXW;
    private zzig zzXX;

    public zzaff(zzafg zzafg, @Nullable zzaka zzaka, @Nullable zzua zzua, @Nullable zzut zzut, @Nullable String str, @Nullable zzud zzud, @Nullable zzoa zzoa, @Nullable String str2) {
        this(zzafg.zzUj.zzSz, (zzaka) null, zzafg.zzXY.zzMa, zzafg.errorCode, zzafg.zzXY.zzMb, zzafg.zzXY.zzTq, zzafg.zzXY.orientation, zzafg.zzXY.zzMg, zzafg.zzUj.zzSC, zzafg.zzXY.zzTo, (zzua) null, (zzut) null, (String) null, zzafg.zzXN, (zzud) null, zzafg.zzXY.zzTp, zzafg.zzvX, zzafg.zzXY.zzTn, zzafg.zzXR, zzafg.zzXS, zzafg.zzXY.zzTt, zzafg.zzXL, (zzoa) null, zzafg.zzXY.zzTD, zzafg.zzXY.zzTE, zzafg.zzXY.zzTE, zzafg.zzXY.zzTG, zzafg.zzXY.zzTH, (String) null, zzafg.zzXY.zzMd, zzafg.zzXY.zzTK, zzafg.zzXX);
    }

    public zzaff(zzir zzir, @Nullable zzaka zzaka, List<String> list, int i, List<String> list2, List<String> list3, int i2, long j, String str, boolean z, @Nullable zzua zzua, @Nullable zzut zzut, @Nullable String str2, zzub zzub, @Nullable zzud zzud, long j2, zziv zziv, long j3, long j4, long j5, String str3, JSONObject jSONObject, @Nullable zzoa zzoa, zzaee zzaee, List<String> list4, List<String> list5, boolean z2, zzaak zzaak, @Nullable String str4, List<String> list6, String str5, zzig zzig) {
        this.zzXU = false;
        this.zzXV = false;
        this.zzXW = false;
        this.zzSz = zzir;
        this.zzPg = zzaka;
        this.zzMa = zzn(list);
        this.errorCode = i;
        this.zzMb = zzn(list2);
        this.zzTq = zzn(list3);
        this.orientation = i2;
        this.zzMg = j;
        this.zzSC = str;
        this.zzTo = z;
        this.zzMG = zzua;
        this.zzMH = zzut;
        this.zzMI = str2;
        this.zzXN = zzub;
        this.zzMJ = zzud;
        this.zzTp = j2;
        this.zzXP = zziv;
        this.zzTn = j3;
        this.zzXR = j4;
        this.zzXS = j5;
        this.zzTt = str3;
        this.zzXL = jSONObject;
        this.zzXT = zzoa;
        this.zzTD = zzaee;
        this.zzXQ = zzn(list4);
        this.zzTF = zzn(list5);
        this.zzTG = z2;
        this.zzTH = zzaak;
        this.zzXO = str4;
        this.zzMd = zzn(list6);
        this.zzTK = str5;
        this.zzXX = zzig;
    }

    @Nullable
    private static <T> List<T> zzn(@Nullable List<T> list) {
        if (list == null) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    public final boolean zzcn() {
        if (this.zzPg == null || this.zzPg.zziw() == null) {
            return false;
        }
        return this.zzPg.zziw().zzcn();
    }
}
