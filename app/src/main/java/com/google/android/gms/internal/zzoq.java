package com.google.android.gms.internal;

final class zzoq implements Runnable {
    private /* synthetic */ zzoc zzIA;
    private /* synthetic */ zzop zzIB;

    zzoq(zzop zzop, zzoc zzoc) {
        this.zzIB = zzop;
        this.zzIA = zzoc;
    }

    public final void run() {
        zzaka zzaka;
        try {
            zzaka = this.zzIA.zzes();
        } catch (Exception e) {
            zzafr.zzb("Error obtaining overlay.", e);
            zzaka = null;
        }
        if (!(zzaka == null || this.zzIB.zzss == null)) {
            this.zzIB.zzss.addView(zzaka.getView());
        }
        if (!(this.zzIA instanceof zznx)) {
            this.zzIB.zza(this.zzIA);
        }
    }
}
