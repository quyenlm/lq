package com.google.android.gms.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import java.util.ArrayList;
import java.util.TimeZone;

public final class zzazp {
    private boolean zzazA;
    private final aej zzazB;
    private boolean zzazC;
    private /* synthetic */ zzazn zzazD;
    private String zzazk;
    private int zzazl;
    private String zzazm;
    private String zzazn;
    private int zzazp;
    private final zzazr zzazu;
    private ArrayList<Integer> zzazv;
    private ArrayList<String> zzazw;
    private ArrayList<Integer> zzazx;
    private ArrayList<zzcqn> zzazy;
    private ArrayList<byte[]> zzazz;

    private zzazp(zzazn zzazn2, byte[] bArr) {
        this(zzazn2, bArr, (zzazr) null);
    }

    /* synthetic */ zzazp(zzazn zzazn2, byte[] bArr, zzazo zzazo) {
        this(zzazn2, bArr);
    }

    private zzazp(zzazn zzazn2, byte[] bArr, zzazr zzazr) {
        this.zzazD = zzazn2;
        this.zzazl = this.zzazD.zzazl;
        this.zzazk = this.zzazD.zzazk;
        this.zzazm = this.zzazD.zzazm;
        zzazn zzazn3 = this.zzazD;
        this.zzazn = null;
        this.zzazp = 0;
        this.zzazv = null;
        this.zzazw = null;
        this.zzazx = null;
        this.zzazy = null;
        this.zzazz = null;
        this.zzazA = true;
        this.zzazB = new aej();
        this.zzazC = false;
        this.zzazm = zzazn2.zzazm;
        this.zzazn = null;
        this.zzazB.zzctQ = zzazn2.zzazr.currentTimeMillis();
        this.zzazB.zzctR = zzazn2.zzazr.elapsedRealtime();
        aej aej = this.zzazB;
        zzazs unused = zzazn2.zzazs;
        aej.zzcuc = (long) (TimeZone.getDefault().getOffset(this.zzazB.zzctQ) / 1000);
        if (bArr != null) {
            this.zzazB.zzctX = bArr;
        }
        this.zzazu = null;
    }

    public final zzazp zzai(int i) {
        this.zzazB.zzctT = i;
        return this;
    }

    public final zzazp zzaj(int i) {
        this.zzazB.zzrB = i;
        return this;
    }

    public final void zzoS() {
        zzoT();
    }

    @Deprecated
    public final PendingResult<Status> zzoT() {
        if (this.zzazC) {
            throw new IllegalStateException("do not reuse LogEventBuilder");
        }
        this.zzazC = true;
        zzazu zzazu2 = new zzazu(new zzbak(this.zzazD.packageName, this.zzazD.zzazj, this.zzazl, this.zzazk, this.zzazm, this.zzazn, this.zzazD.zzazo, 0), this.zzazB, (zzazr) null, (zzazr) null, zzazn.zzb((ArrayList<Integer>) null), (String[]) null, zzazn.zzb((ArrayList<Integer>) null), (byte[][]) null, (zzcqn[]) null, this.zzazA);
        zzbak zzbak = zzazu2.zzazE;
        return this.zzazD.zzazt.zzg(zzbak.zzazk, zzbak.zzazl) ? this.zzazD.zzazq.zza(zzazu2) : PendingResults.immediatePendingResult(Status.zzaBm);
    }
}
