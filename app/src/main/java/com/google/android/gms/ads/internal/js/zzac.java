package com.google.android.gms.ads.internal.js;

import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzahz;
import com.google.android.gms.internal.zzajr;
import com.google.android.gms.internal.zzajt;

public final class zzac extends zzajt<zza> {
    private final Object mLock = new Object();
    /* access modifiers changed from: private */
    public zzahz<zza> zzLc;
    private boolean zzLu;
    private int zzLv;

    public zzac(zzahz<zza> zzahz) {
        this.zzLc = zzahz;
        this.zzLu = false;
        this.zzLv = 0;
    }

    private final void zzfd() {
        synchronized (this.mLock) {
            zzbo.zzae(this.zzLv >= 0);
            if (!this.zzLu || this.zzLv != 0) {
                zzafr.v("There are still references to the engine. Not destroying.");
            } else {
                zzafr.v("No reference is left (including root). Cleaning up engine.");
                zza(new zzaf(this), new zzajr());
            }
        }
    }

    public final zzy zzfa() {
        zzy zzy = new zzy(this);
        synchronized (this.mLock) {
            zza(new zzad(this, zzy), new zzae(this, zzy));
            zzbo.zzae(this.zzLv >= 0);
            this.zzLv++;
        }
        return zzy;
    }

    /* access modifiers changed from: protected */
    public final void zzfb() {
        synchronized (this.mLock) {
            zzbo.zzae(this.zzLv > 0);
            zzafr.v("Releasing 1 reference for JS Engine");
            this.zzLv--;
            zzfd();
        }
    }

    public final void zzfc() {
        boolean z = true;
        synchronized (this.mLock) {
            if (this.zzLv < 0) {
                z = false;
            }
            zzbo.zzae(z);
            zzafr.v("Releasing root reference. JS Engine will be destroyed once other references are released.");
            this.zzLu = true;
            zzfd();
        }
    }
}
