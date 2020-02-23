package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.tagmanager.zzci;

final class zzcwr extends zzci {
    final /* synthetic */ zzcwn zzbJp;

    zzcwr(zzcwn zzcwn) {
        this.zzbJp = zzcwn;
    }

    public final void onEvent(String str, String str2, Bundle bundle, long j) {
        if (!str.endsWith("+gtm")) {
            this.zzbJp.zzbHL.execute(new zzcws(this, str2, bundle, new StringBuilder(String.valueOf(str).length() + 4).append(str).append("+gtm").toString(), j, str));
        }
    }
}
