package com.google.android.gms.identity.intents;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbay;
import com.google.android.gms.internal.zzcbe;

public final class Address {
    public static final Api<AddressOptions> API = new Api<>("Address.API", zzajS, zzajR);
    private static Api.zzf<zzcbe> zzajR = new Api.zzf<>();
    private static final Api.zza<zzcbe, AddressOptions> zzajS = new zza();

    public static final class AddressOptions implements Api.ApiOptions.HasOptions {
        public final int theme;

        public AddressOptions() {
            this.theme = 0;
        }

        public AddressOptions(int i) {
            this.theme = i;
        }
    }

    static abstract class zza extends zzbay<Status, zzcbe> {
        public zza(GoogleApiClient googleApiClient) {
            super((Api<?>) Address.API, googleApiClient);
        }

        public final /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Status) obj);
        }

        public final /* synthetic */ Result zzb(Status status) {
            return status;
        }
    }

    public static void requestUserAddress(GoogleApiClient googleApiClient, UserAddressRequest userAddressRequest, int i) {
        googleApiClient.zzd(new zzb(googleApiClient, userAddressRequest, i));
    }
}
