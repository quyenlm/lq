package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.wearable.internal.zzac;
import com.google.android.gms.wearable.internal.zzbh;
import com.google.android.gms.wearable.internal.zzbi;
import com.google.android.gms.wearable.internal.zzds;
import com.google.android.gms.wearable.internal.zzdz;
import com.google.android.gms.wearable.internal.zzey;
import com.google.android.gms.wearable.internal.zzfw;
import com.google.android.gms.wearable.internal.zzgi;
import com.google.android.gms.wearable.internal.zzh;
import com.google.android.gms.wearable.internal.zzk;
import com.google.android.gms.wearable.internal.zzo;

public class Wearable {
    public static final Api<WearableOptions> API = new Api<>("Wearable.API", zzajS, zzajR);
    public static final CapabilityApi CapabilityApi = new zzo();
    public static final ChannelApi ChannelApi = new zzac();
    public static final DataApi DataApi = new zzbi();
    public static final MessageApi MessageApi = new zzds();
    public static final NodeApi NodeApi = new zzdz();
    private static Api.zzf<zzfw> zzajR = new Api.zzf<>();
    private static final Api.zza<zzfw, WearableOptions> zzajS = new zzj();
    private static zzc zzbRl = new zzk();
    private static zza zzbRm = new zzh();
    private static zzf zzbRn = new zzbh();
    private static zzi zzbRo = new zzey();
    private static zzu zzbRp = new zzgi();

    public static final class WearableOptions implements Api.ApiOptions.Optional {

        public static class Builder {
            public WearableOptions build() {
                return new WearableOptions(this, (zzj) null);
            }
        }

        private WearableOptions(Builder builder) {
        }

        /* synthetic */ WearableOptions(Builder builder, zzj zzj) {
            this(builder);
        }
    }

    private Wearable() {
    }
}
