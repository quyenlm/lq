package com.google.android.gms.internal;

import java.util.Map;

final class sb implements Runnable {
    private /* synthetic */ qd zzcet;
    private /* synthetic */ ry zzceu;

    sb(ry ryVar, qd qdVar) {
        this.zzceu = ryVar;
        this.zzcet = qdVar;
    }

    public final void run() {
        synchronized (this.zzceu.zzces) {
            if (this.zzceu.zzces.containsKey(this.zzcet)) {
                boolean z = true;
                for (qu quVar : ((Map) this.zzceu.zzces.get(this.zzcet)).values()) {
                    quVar.interrupt();
                    z = z && !quVar.zzHj();
                }
                if (z) {
                    this.zzcet.stop();
                }
            }
        }
    }
}
