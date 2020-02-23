package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;

final class zzcuj implements ce, Runnable {
    private /* synthetic */ zzcuf zzbHS;

    private zzcuj(zzcuf zzcuf) {
        this.zzbHS = zzcuf;
    }

    /* synthetic */ zzcuj(zzcuf zzcuf, zzcug zzcug) {
        this(zzcuf);
    }

    @WorkerThread
    public final void run() {
        zzbo.zzae(this.zzbHS.mState == 1);
        ArrayList arrayList = new ArrayList();
        boolean unused = this.zzbHS.zzbHR = false;
        if (zzcvs.zzCw().zzfG(this.zzbHS.zzbDw)) {
            arrayList.add(0);
        } else {
            boolean unused2 = this.zzbHS.zzbHR = this.zzbHS.zzbHO.zzCj();
            if (!this.zzbHS.zzbHR) {
                arrayList.add(0);
                arrayList.add(1);
            } else {
                arrayList.add(1);
                arrayList.add(0);
            }
            arrayList.add(2);
        }
        this.zzbHS.zzbHK.zza(this.zzbHS.zzbDw, this.zzbHS.zzbHI, this.zzbHS.zzbHH, arrayList, this, this.zzbHS.zzbHO);
    }

    public final void zza(cm cmVar) {
        if (cmVar.getStatus() == Status.zzaBm) {
            this.zzbHS.zzbHL.execute(new zzcum(this.zzbHS, cmVar));
        } else {
            this.zzbHS.zzbHL.execute(new zzcui(this.zzbHS, (zzcug) null));
        }
    }
}
