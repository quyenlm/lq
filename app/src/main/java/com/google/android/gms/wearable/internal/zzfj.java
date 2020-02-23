package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.wearable.Channel;

final class zzfj extends zzfc<Channel.GetOutputStreamResult> {
    private final zzbd zzbTf;

    public zzfj(zzbaz<Channel.GetOutputStreamResult> zzbaz, zzbd zzbd) {
        super(zzbaz);
        this.zzbTf = (zzbd) zzbo.zzu(zzbd);
    }

    public final void zza(zzcm zzcm) {
        zzax zzax = null;
        if (zzcm.zzbSI != null) {
            zzax = new zzax(new ParcelFileDescriptor.AutoCloseOutputStream(zzcm.zzbSI));
            this.zzbTf.zza(new zzay(zzax));
        }
        zzR(new zzat(new Status(zzcm.statusCode), zzax));
    }
}
