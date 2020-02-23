package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.wearable.Channel;

final class zzfi extends zzfc<Channel.GetInputStreamResult> {
    private final zzbd zzbTf;

    public zzfi(zzbaz<Channel.GetInputStreamResult> zzbaz, zzbd zzbd) {
        super(zzbaz);
        this.zzbTf = (zzbd) zzbo.zzu(zzbd);
    }

    public final void zza(zzck zzck) {
        zzav zzav = null;
        if (zzck.zzbSI != null) {
            zzav = new zzav(new ParcelFileDescriptor.AutoCloseInputStream(zzck.zzbSI));
            this.zzbTf.zza(new zzaw(zzav));
        }
        zzR(new zzas(new Status(zzck.statusCode), zzav));
    }
}
