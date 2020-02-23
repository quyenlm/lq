package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.safetynet.SafetyNet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

@zzzn
public final class zzael {
    private static final List<Integer> zzXh = Arrays.asList(new Integer[]{4, 5});
    /* access modifiers changed from: private */
    public Timer zzXi = new Timer();
    /* access modifiers changed from: private */
    public GoogleApiClient zzXj;

    public final zzajm<Map<String, String>> zza(Context context, Set<String> set) {
        zzajg zzajg = new zzajg();
        this.zzXj = new GoogleApiClient.Builder(context).addApi(SafetyNet.API).addConnectionCallbacks(new zzaen(this, set, zzajg)).addOnConnectionFailedListener(new zzaem(this, zzajg)).build();
        this.zzXj.connect();
        return zzajg;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final void zza(GoogleApiClient googleApiClient, Set<String> set, zzajg<Map<String, String>> zzajg) {
        HashMap hashMap = new HashMap();
        AtomicInteger atomicInteger = new AtomicInteger(set.size());
        for (String next : set) {
            ((zzcsa) SafetyNet.SafetyNetApi).zza(this.zzXj, zzXh, next, (String) zzbs.zzbL().zzd(zzmo.zzGc)).setResultCallback(new zzaep(this, hashMap, next, atomicInteger, googleApiClient, zzajg));
        }
    }
}
