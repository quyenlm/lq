package com.google.android.gms.tagmanager;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.ef;
import com.google.android.gms.internal.ek;
import com.google.android.gms.internal.zzbbe;
import com.google.android.gms.internal.zzbn;
import com.google.android.gms.internal.zzbq;
import com.google.android.gms.tagmanager.zzei;

public final class zzy extends zzbbe<ContainerHolder> {
    private final Context mContext;
    /* access modifiers changed from: private */
    public long zzbDB;
    private final TagManager zzbDI;
    private final zzaf zzbDL;
    /* access modifiers changed from: private */
    public final zzek zzbDM;
    private final int zzbDN;
    /* access modifiers changed from: private */
    public final zzai zzbDO;
    private zzah zzbDP;
    private ef zzbDQ;
    /* access modifiers changed from: private */
    public volatile zzv zzbDR;
    /* access modifiers changed from: private */
    public volatile boolean zzbDS;
    /* access modifiers changed from: private */
    public zzbq zzbDT;
    private String zzbDU;
    private zzag zzbDV;
    private zzac zzbDW;
    private final String zzbDw;
    private final Looper zzrM;
    /* access modifiers changed from: private */
    public final zze zzvw;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private zzy(Context context, TagManager tagManager, Looper looper, String str, int i, zzah zzah, zzag zzag, ef efVar, zze zze, zzek zzek, zzai zzai) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.mContext = context;
        this.zzbDI = tagManager;
        this.zzrM = looper == null ? Looper.getMainLooper() : looper;
        this.zzbDw = str;
        this.zzbDN = i;
        this.zzbDP = zzah;
        this.zzbDV = zzag;
        this.zzbDQ = efVar;
        this.zzbDL = new zzaf(this, (zzz) null);
        this.zzbDT = new zzbq();
        this.zzvw = zze;
        this.zzbDM = zzek;
        this.zzbDO = zzai;
        if (zzAQ()) {
            zzfa(zzei.zzBD().zzBF());
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzy(android.content.Context r19, com.google.android.gms.tagmanager.TagManager r20, android.os.Looper r21, java.lang.String r22, int r23, com.google.android.gms.tagmanager.zzal r24) {
        /*
            r18 = this;
            com.google.android.gms.tagmanager.zzey r4 = new com.google.android.gms.tagmanager.zzey
            r0 = r19
            r1 = r22
            r4.<init>(r0, r1)
            com.google.android.gms.tagmanager.zzet r16 = new com.google.android.gms.tagmanager.zzet
            r0 = r16
            r1 = r19
            r2 = r22
            r3 = r24
            r0.<init>(r1, r2, r3)
            com.google.android.gms.internal.ef r14 = new com.google.android.gms.internal.ef
            r0 = r19
            r14.<init>(r0)
            com.google.android.gms.common.util.zze r15 = com.google.android.gms.common.util.zzi.zzrY()
            com.google.android.gms.tagmanager.zzdh r5 = new com.google.android.gms.tagmanager.zzdh
            r6 = 1
            r7 = 5
            r8 = 900000(0xdbba0, double:4.44659E-318)
            r10 = 5000(0x1388, double:2.4703E-320)
            java.lang.String r12 = "refreshing"
            com.google.android.gms.common.util.zze r13 = com.google.android.gms.common.util.zzi.zzrY()
            r5.<init>(r6, r7, r8, r10, r12, r13)
            com.google.android.gms.tagmanager.zzai r17 = new com.google.android.gms.tagmanager.zzai
            r0 = r17
            r1 = r19
            r2 = r22
            r0.<init>(r1, r2)
            r6 = r18
            r7 = r19
            r8 = r20
            r9 = r21
            r10 = r22
            r11 = r23
            r12 = r4
            r13 = r16
            r16 = r5
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            r0 = r18
            com.google.android.gms.internal.ef r4 = r0.zzbDQ
            java.lang.String r5 = r24.zzAX()
            r4.zzgc(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzy.<init>(android.content.Context, com.google.android.gms.tagmanager.TagManager, android.os.Looper, java.lang.String, int, com.google.android.gms.tagmanager.zzal):void");
    }

    /* access modifiers changed from: private */
    public final boolean zzAQ() {
        zzei zzBD = zzei.zzBD();
        return (zzBD.zzBE() == zzei.zza.CONTAINER || zzBD.zzBE() == zzei.zza.CONTAINER_DEBUG) && this.zzbDw.equals(zzBD.getContainerId());
    }

    /* access modifiers changed from: private */
    public final synchronized void zza(zzbq zzbq) {
        if (this.zzbDP != null) {
            ee eeVar = new ee();
            eeVar.zzbLG = this.zzbDB;
            eeVar.zzlB = new zzbn();
            eeVar.zzbLH = zzbq;
            this.zzbDP.zza(eeVar);
        }
    }

    /* access modifiers changed from: private */
    public final synchronized void zza(zzbq zzbq, long j, boolean z) {
        if (z) {
            boolean z2 = this.zzbDS;
        }
        if (!isReady() || this.zzbDR != null) {
            this.zzbDT = zzbq;
            this.zzbDB = j;
            long zzAS = this.zzbDO.zzAS();
            zzag(Math.max(0, Math.min(zzAS, (this.zzbDB + zzAS) - this.zzvw.currentTimeMillis())));
            Container container = new Container(this.mContext, this.zzbDI.getDataLayer(), this.zzbDw, j, zzbq);
            if (this.zzbDR == null) {
                this.zzbDR = new zzv(this.zzbDI, this.zzrM, container, this.zzbDL);
            } else {
                this.zzbDR.zza(container);
            }
            if (!isReady() && this.zzbDW.zzb(container)) {
                setResult(this.zzbDR);
            }
        }
    }

    /* access modifiers changed from: private */
    public final synchronized void zzag(long j) {
        if (this.zzbDV == null) {
            zzdj.zzaT("Refresh requested, but no network load scheduler.");
        } else {
            this.zzbDV.zza(j, this.zzbDT.zzlC);
        }
    }

    private final void zzaq(boolean z) {
        this.zzbDP.zza((zzdi<ee>) new zzad(this, (zzz) null));
        this.zzbDV.zza(new zzae(this, (zzz) null));
        ek zzbx = this.zzbDP.zzbx(this.zzbDN);
        if (zzbx != null) {
            this.zzbDR = new zzv(this.zzbDI, this.zzrM, new Container(this.mContext, this.zzbDI.getDataLayer(), this.zzbDw, 0, zzbx), this.zzbDL);
        }
        this.zzbDW = new zzab(this, z);
        if (zzAQ()) {
            this.zzbDV.zza(0, "");
        } else {
            this.zzbDP.zzAR();
        }
    }

    /* access modifiers changed from: package-private */
    public final synchronized String zzAK() {
        return this.zzbDU;
    }

    public final void zzAN() {
        ek zzbx = this.zzbDP.zzbx(this.zzbDN);
        if (zzbx != null) {
            setResult(new zzv(this.zzbDI, this.zzrM, new Container(this.mContext, this.zzbDI.getDataLayer(), this.zzbDw, 0, zzbx), new zzaa(this)));
        } else {
            zzdj.e("Default was requested, but no default container was found");
            setResult(zzb(new Status(10, "Default was requested, but no default container was found", (PendingIntent) null)));
        }
        this.zzbDV = null;
        this.zzbDP = null;
    }

    public final void zzAO() {
        zzaq(false);
    }

    public final void zzAP() {
        zzaq(true);
    }

    /* access modifiers changed from: protected */
    /* renamed from: zzI */
    public final ContainerHolder zzb(Status status) {
        if (this.zzbDR != null) {
            return this.zzbDR;
        }
        if (status == Status.zzaBp) {
            zzdj.e("timer expired: setting result to failure");
        }
        return new zzv(status);
    }

    /* access modifiers changed from: package-private */
    public final synchronized void zzfa(String str) {
        this.zzbDU = str;
        if (this.zzbDV != null) {
            this.zzbDV.zzfb(str);
        }
    }
}
