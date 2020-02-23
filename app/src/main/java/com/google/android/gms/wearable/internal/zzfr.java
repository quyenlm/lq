package com.google.android.gms.wearable.internal;

import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.wearable.DataApi;
import java.util.List;
import java.util.concurrent.FutureTask;

final class zzfr extends zzfc<DataApi.DataItemResult> {
    private final List<FutureTask<Boolean>> zzJO;

    zzfr(zzbaz<DataApi.DataItemResult> zzbaz, List<FutureTask<Boolean>> list) {
        super(zzbaz);
        this.zzJO = list;
    }

    public final void zza(zzem zzem) {
        zzR(new zzbs(zzev.zzaY(zzem.statusCode), zzem.zzbSP));
        if (zzem.statusCode != 0) {
            for (FutureTask<Boolean> cancel : this.zzJO) {
                cancel.cancel(true);
            }
        }
    }
}
