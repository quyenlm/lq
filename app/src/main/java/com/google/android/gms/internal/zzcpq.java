package com.google.android.gms.internal;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.messages.internal.zzq;

public final class zzcpq extends zzq implements zzcpn<zzbaz<Status>> {
    private final zzbdw<zzbaz<Status>> zzbzE;
    private boolean zzbzG = false;

    public zzcpq(zzbdw<zzbaz<Status>> zzbdw) {
        this.zzbzE = zzbdw;
    }

    public final synchronized void zzG(Status status) throws RemoteException {
        if (!this.zzbzG) {
            this.zzbzE.zza(new zzcpr(this, status));
            this.zzbzG = true;
        } else {
            String valueOf = String.valueOf(status);
            Log.wtf("NearbyMessagesCallbackWrapper", new StringBuilder(String.valueOf(valueOf).length() + 28).append("Received multiple statuses: ").append(valueOf).toString(), new Exception());
        }
    }

    public final zzbdw<zzbaz<Status>> zzzX() {
        return this.zzbzE;
    }
}
