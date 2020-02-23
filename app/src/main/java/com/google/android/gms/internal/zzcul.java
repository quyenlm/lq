package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;

final class zzcul implements ce, Runnable {
    private /* synthetic */ zzcuf zzbHS;

    private zzcul(zzcuf zzcuf) {
        this.zzbHS = zzcuf;
    }

    /* synthetic */ zzcul(zzcuf zzcuf, zzcug zzcug) {
        this(zzcuf);
    }

    @WorkerThread
    public final void run() {
        zzbo.zzae(this.zzbHS.mState == 2);
        if (!zzcvs.zzCw().zzfG(this.zzbHS.zzbDw)) {
            String zzd = this.zzbHS.zzbDw;
            zzcvk.v(new StringBuilder(String.valueOf(zzd).length() + 24).append("Refreshing container ").append(zzd).append("...").toString());
            ArrayList arrayList = new ArrayList();
            arrayList.add(0);
            this.zzbHS.zzbHK.zza(this.zzbHS.zzbDw, this.zzbHS.zzbHI, this.zzbHS.zzbHH, arrayList, this, this.zzbHS.zzbHO);
        }
    }

    public final void zza(cm cmVar) {
        if (cmVar.getStatus() == Status.zzaBm) {
            String zzd = this.zzbHS.zzbDw;
            zzcvk.v(new StringBuilder(String.valueOf(zzd).length() + 47).append("Refreshed container ").append(zzd).append(". Reinitializing runtime...").toString());
            this.zzbHS.zzbHL.execute(new zzcum(this.zzbHS, cmVar));
            return;
        }
        this.zzbHS.zzaj(this.zzbHS.zzbHO.zzAT());
    }
}
