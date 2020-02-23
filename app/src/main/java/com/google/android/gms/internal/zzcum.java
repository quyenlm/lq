package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import java.util.List;

final class zzcum implements Runnable {
    private /* synthetic */ zzcuf zzbHS;
    private final cm zzbHU;

    zzcum(zzcuf zzcuf, cm cmVar) {
        this.zzbHS = zzcuf;
        this.zzbHU = cmVar;
    }

    @WorkerThread
    public final void run() {
        db zzCV = this.zzbHU.zzCQ().zzCV();
        dj zzCR = this.zzbHU.zzCR();
        boolean z = this.zzbHS.zzbHP == null;
        zzcvu unused = this.zzbHS.zzbHP = this.zzbHS.zzbHJ.zza(zzCV, zzCR);
        int unused2 = this.zzbHS.mState = 2;
        String zzd = this.zzbHS.zzbDw;
        zzcvk.v(new StringBuilder(String.valueOf(zzd).length() + 48).append("Container ").append(zzd).append(" loaded during runtime initialization.").toString());
        if (this.zzbHS.zzbHQ != null) {
            for (zzcut zzcut : this.zzbHS.zzbHQ) {
                String valueOf = String.valueOf(zzcut.zzCk());
                zzcvk.v(valueOf.length() != 0 ? "Evaluating tags for pending event ".concat(valueOf) : new String("Evaluating tags for pending event "));
                this.zzbHS.zzbHP.zzb(zzcut);
            }
            List unused3 = this.zzbHS.zzbHQ = null;
        }
        this.zzbHS.zzbHP.dispatch();
        String valueOf2 = String.valueOf(this.zzbHS.zzbDw);
        zzcvk.v(valueOf2.length() != 0 ? "Runtime initialized successfully for container ".concat(valueOf2) : new String("Runtime initialized successfully for container "));
        long zzCW = this.zzbHU.zzCQ().zzCW() + this.zzbHS.zzbHO.zzAS();
        if (!z || !this.zzbHS.zzbHR || this.zzbHU.getSource() != 1 || zzCW >= this.zzbHS.zzvw.currentTimeMillis()) {
            this.zzbHS.zzaj(Math.max(900000, zzCW - this.zzbHS.zzvw.currentTimeMillis()));
        } else {
            this.zzbHS.zzaj(this.zzbHS.zzbHO.zzCi());
        }
    }
}
