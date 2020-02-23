package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.search.SearchAuthApi;

public final class zzcsz implements SearchAuthApi {
    public final PendingResult<Status> clearToken(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zzd(new zzctb(googleApiClient, str));
    }

    public final PendingResult<SearchAuthApi.GoogleNowAuthResult> getGoogleNowAuth(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zzd(new zzctd(googleApiClient, str));
    }
}
