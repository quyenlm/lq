package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.NodeApi;

public final class zzdz implements NodeApi {
    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, NodeApi.NodeListener nodeListener) {
        return zzb.zza(googleApiClient, new zzec(new IntentFilter[]{zzez.zzgl("com.google.android.gms.wearable.NODE_CHANGED")}), nodeListener);
    }

    public final PendingResult<NodeApi.GetConnectedNodesResult> getConnectedNodes(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzeb(this, googleApiClient));
    }

    public final PendingResult<NodeApi.GetLocalNodeResult> getLocalNode(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzea(this, googleApiClient));
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, NodeApi.NodeListener nodeListener) {
        return googleApiClient.zzd(new zzed(this, googleApiClient, nodeListener));
    }
}
