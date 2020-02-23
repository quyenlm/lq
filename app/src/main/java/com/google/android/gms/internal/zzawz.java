package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerClient;
import com.tencent.midas.oversea.network.http.APErrorCode;
import java.io.IOException;
import org.json.JSONObject;

final class zzawz extends zzaxh {
    final /* synthetic */ zzawy zzaxd;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzawz(zzawy zzawy, GameManagerClient gameManagerClient) {
        super(zzawy, gameManagerClient);
        this.zzaxd = zzawy;
    }

    public final void execute() {
        try {
            this.zzaxd.zzasb.setMessageReceivedCallbacks(this.zzaxd.zzXj, this.zzaxd.getNamespace(), new zzaxa(this));
            this.zzaxd.zzov();
            this.zzaxd.zzou();
            this.zzaxd.zza((String) null, (int) APErrorCode.ERROR_NETWORK_HTTPSTIMES, (JSONObject) null, this.zzarw);
        } catch (IOException | IllegalStateException e) {
            this.zzarw.zza(-1, 8, (Object) null);
        }
    }
}
