package com.google.android.gms.internal;

import android.os.Handler;
import java.util.LinkedList;
import java.util.List;

@zzzn
final class zzsj {
    /* access modifiers changed from: private */
    public final List<zzth> zztH = new LinkedList();

    zzsj() {
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzti zzti) {
        Handler handler = zzagz.zzZr;
        for (zzth zztg : this.zztH) {
            handler.post(new zztg(this, zztg, zzti));
        }
        this.zztH.clear();
    }
}
