package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;

final class zzclh extends zzclf<PayloadCallback> {
    private /* synthetic */ zzcoc zzbwV;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclh(zzclg zzclg, zzcoc zzcoc) {
        super();
        this.zzbwV = zzcoc;
    }

    public final /* synthetic */ void zzq(Object obj) {
        PayloadCallback payloadCallback = (PayloadCallback) obj;
        Payload zza = zzcoq.zza(this.zzbwV.zzzK());
        if (zza == null) {
            Log.w("NearbyConnectionsClient", String.format("Failed to convert incoming ParcelablePayload %d to Payload.", new Object[]{Long.valueOf(this.zzbwV.zzzK().getId())}));
            return;
        }
        payloadCallback.onPayloadReceived(this.zzbwV.zzzF(), zza);
    }
}
