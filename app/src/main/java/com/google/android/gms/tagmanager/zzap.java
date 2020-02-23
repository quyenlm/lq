package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.DataLayer;
import java.util.List;

final class zzap implements zzaq {
    private /* synthetic */ DataLayer zzbEl;

    zzap(DataLayer dataLayer) {
        this.zzbEl = dataLayer;
    }

    public final void zzJ(List<DataLayer.zza> list) {
        for (DataLayer.zza next : list) {
            this.zzbEl.zzr(DataLayer.zzn(next.zzBN, next.mValue));
        }
        this.zzbEl.zzbEk.countDown();
    }
}
