package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.IntentFilter;

final class zzfo extends zzfn {
    /* access modifiers changed from: private */
    public static final Object zzbGC = new Object();
    private static zzfo zzbGO;
    /* access modifiers changed from: private */
    public boolean connected = true;
    /* access modifiers changed from: private */
    public Context zzbGD;
    /* access modifiers changed from: private */
    public zzcc zzbGE;
    private volatile zzbz zzbGF;
    /* access modifiers changed from: private */
    public int zzbGG = 1800000;
    private boolean zzbGH = true;
    private boolean zzbGI = false;
    private boolean zzbGJ = true;
    private zzcd zzbGK = new zzfp(this);
    private zzfr zzbGL;
    private zzdo zzbGM;
    private boolean zzbGN = false;

    private zzfo() {
    }

    /* access modifiers changed from: private */
    public final boolean isPowerSaveMode() {
        return this.zzbGN || !this.connected || this.zzbGG <= 0;
    }

    public static zzfo zzBV() {
        if (zzbGO == null) {
            zzbGO = new zzfo();
        }
        return zzbGO;
    }

    public final synchronized void dispatch() {
        if (!this.zzbGI) {
            zzdj.v("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.zzbGH = true;
        } else {
            this.zzbGF.zzn(new zzfq(this));
        }
    }

    public final synchronized void zzBU() {
        if (!isPowerSaveMode()) {
            this.zzbGL.zzBY();
        }
    }

    /* access modifiers changed from: package-private */
    public final synchronized zzcc zzBW() {
        if (this.zzbGE == null) {
            if (this.zzbGD == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.zzbGE = new zzec(this.zzbGK, this.zzbGD);
        }
        if (this.zzbGL == null) {
            this.zzbGL = new zzfs(this, (zzfp) null);
            if (this.zzbGG > 0) {
                this.zzbGL.zzs((long) this.zzbGG);
            }
        }
        this.zzbGI = true;
        if (this.zzbGH) {
            dispatch();
            this.zzbGH = false;
        }
        if (this.zzbGM == null && this.zzbGJ) {
            this.zzbGM = new zzdo(this);
            zzdo zzdo = this.zzbGM;
            Context context = this.zzbGD;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(zzdo, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("com.google.analytics.RADIO_POWERED");
            intentFilter2.addCategory(context.getPackageName());
            context.registerReceiver(zzdo, intentFilter2);
        }
        return this.zzbGE;
    }

    /* access modifiers changed from: package-private */
    public final synchronized void zza(Context context, zzbz zzbz) {
        if (this.zzbGD == null) {
            this.zzbGD = context.getApplicationContext();
            if (this.zzbGF == null) {
                this.zzbGF = zzbz;
            }
        }
    }

    public final synchronized void zzas(boolean z) {
        zzd(this.zzbGN, z);
    }

    /* access modifiers changed from: package-private */
    public final synchronized void zzd(boolean z, boolean z2) {
        boolean isPowerSaveMode = isPowerSaveMode();
        this.zzbGN = z;
        this.connected = z2;
        if (isPowerSaveMode() != isPowerSaveMode) {
            if (isPowerSaveMode()) {
                this.zzbGL.cancel();
                zzdj.v("PowerSaveMode initiated.");
            } else {
                this.zzbGL.zzs((long) this.zzbGG);
                zzdj.v("PowerSaveMode terminated.");
            }
        }
    }
}
