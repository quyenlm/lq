package com.google.android.gms.internal;

import android.content.Context;
import android.content.IntentFilter;

final class zzcwd extends zzcwc {
    /* access modifiers changed from: private */
    public static final Object zzbGC = new Object();
    private static zzcwd zzbIZ;
    /* access modifiers changed from: private */
    public boolean connected = true;
    /* access modifiers changed from: private */
    public Context zzbGD;
    /* access modifiers changed from: private */
    public int zzbGG = 1800000;
    private boolean zzbGH = true;
    private boolean zzbGI = false;
    private boolean zzbGJ = true;
    private boolean zzbGN = false;
    /* access modifiers changed from: private */
    public zzcvb zzbIT;
    private volatile zzcuy zzbIU;
    /* access modifiers changed from: private */
    public boolean zzbIV = false;
    private zzcvc zzbIW = new zzcwe(this);
    private zzcwg zzbIX;
    private zzcvm zzbIY;

    private zzcwd() {
    }

    /* access modifiers changed from: private */
    public final boolean isPowerSaveMode() {
        return this.zzbGN || !this.connected || this.zzbGG <= 0;
    }

    public static zzcwd zzCA() {
        if (zzbIZ == null) {
            zzbIZ = new zzcwd();
        }
        return zzbIZ;
    }

    public final synchronized void dispatch() {
        if (!this.zzbGI) {
            zzcvk.v("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.zzbGH = true;
        } else if (!this.zzbIV) {
            this.zzbIV = true;
            this.zzbIU.zzn(new zzcwf(this));
        }
    }

    public final synchronized void zzBU() {
        if (!isPowerSaveMode()) {
            this.zzbIX.zzBY();
        }
    }

    /* access modifiers changed from: package-private */
    public final synchronized zzcvb zzCB() {
        if (this.zzbIT == null) {
            if (this.zzbGD == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.zzbIT = new zzcvn(this.zzbIW, this.zzbGD);
        }
        if (this.zzbIX == null) {
            this.zzbIX = new zzcwh(this, (zzcwe) null);
            if (this.zzbGG > 0) {
                this.zzbIX.zzs((long) this.zzbGG);
            }
        }
        this.zzbGI = true;
        if (this.zzbGH) {
            dispatch();
            this.zzbGH = false;
        }
        if (this.zzbIY == null && this.zzbGJ) {
            this.zzbIY = new zzcvm(this);
            zzcvm zzcvm = this.zzbIY;
            Context context = this.zzbGD;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(zzcvm, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("com.google.analytics.RADIO_POWERED");
            intentFilter2.addCategory(context.getPackageName());
            context.registerReceiver(zzcvm, intentFilter2);
        }
        return this.zzbIT;
    }

    /* access modifiers changed from: package-private */
    public final synchronized void zza(Context context, zzcuy zzcuy) {
        if (this.zzbGD == null) {
            this.zzbGD = context.getApplicationContext();
            if (this.zzbIU == null) {
                this.zzbIU = zzcuy;
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
                this.zzbIX.cancel();
                zzcvk.v("PowerSaveMode initiated.");
            } else {
                this.zzbIX.zzs((long) this.zzbGG);
                zzcvk.v("PowerSaveMode terminated.");
            }
        }
    }
}
