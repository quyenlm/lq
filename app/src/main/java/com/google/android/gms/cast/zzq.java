package com.google.android.gms.cast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzq implements GoogleApiClient.OnConnectionFailedListener {
    private /* synthetic */ CastRemoteDisplayLocalService zzapJ;

    zzq(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zzapJ = castRemoteDisplayLocalService;
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        CastRemoteDisplayLocalService castRemoteDisplayLocalService = this.zzapJ;
        String valueOf = String.valueOf(connectionResult);
        castRemoteDisplayLocalService.zzbs(new StringBuilder(String.valueOf(valueOf).length() + 19).append("Connection failed: ").append(valueOf).toString());
        this.zzapJ.zzne();
    }
}
