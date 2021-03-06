package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;

abstract class zzcrg extends Plus.zza<People.LoadPeopleResult> {
    private zzcrg(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzcrg(GoogleApiClient googleApiClient, zzcrb zzcrb) {
        this(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzcrh(this, status);
    }
}
