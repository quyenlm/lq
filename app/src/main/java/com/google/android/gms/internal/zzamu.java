package com.google.android.gms.internal;

public final class zzamu extends zzamh {
    private final zzalk zzaeh = new zzalk();

    zzamu(zzamj zzamj) {
        super(zzamj);
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
        zzkt().zzjA().zzb(this.zzaeh);
        zzaot zzkx = zzkx();
        String zzjG = zzkx.zzjG();
        if (zzjG != null) {
            this.zzaeh.setAppName(zzjG);
        }
        String zzjH = zzkx.zzjH();
        if (zzjH != null) {
            this.zzaeh.setAppVersion(zzjH);
        }
    }

    public final zzalk zzkW() {
        zzkD();
        return this.zzaeh;
    }
}
