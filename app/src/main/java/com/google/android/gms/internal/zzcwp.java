package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.tagmanager.zzcl;

final class zzcwp extends zzcl {
    final /* synthetic */ zzcwn zzbJp;

    zzcwp(zzcwn zzcwn) {
        this.zzbJp = zzcwn;
    }

    public final void interceptEvent(String str, String str2, Bundle bundle, long j) throws RemoteException {
        this.zzbJp.zzbHL.execute(new zzcwq(this, str2, bundle, new StringBuilder(String.valueOf(str).length() + 4).append(str).append("+gtm").toString(), j, str));
    }
}
