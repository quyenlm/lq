package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.nearby.connection.Connections;
import com.google.android.gms.nearby.connection.Payload;

final class zzcld extends zzclf<Connections.MessageListener> {
    private /* synthetic */ zzcoc zzbwV;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcld(zzclc zzclc, zzcoc zzcoc) {
        super();
        this.zzbwV = zzcoc;
    }

    public final /* synthetic */ void zzq(Object obj) {
        Connections.MessageListener messageListener = (Connections.MessageListener) obj;
        Payload zza = zzcoq.zza(this.zzbwV.zzzK());
        if (zza == null) {
            Log.w("NearbyConnectionsClient", String.format("Failed to convert incoming ParcelablePayload %d to Payload.", new Object[]{Long.valueOf(this.zzbwV.zzzK().getId())}));
        } else if (zza.getType() == 1) {
            messageListener.onMessageReceived(this.zzbwV.zzzF(), zza.asBytes(), this.zzbwV.zzzL());
        }
    }
}
