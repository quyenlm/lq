package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.panorama.PanoramaApi;

final class zzcqj extends zzcqb {
    private final zzbaz<PanoramaApi.PanoramaResult> zzaIz;

    public zzcqj(zzbaz<PanoramaApi.PanoramaResult> zzbaz) {
        this.zzaIz = zzbaz;
    }

    public final void zza(int i, Bundle bundle, int i2, Intent intent) {
        this.zzaIz.setResult(new zzcqm(new Status(i, (String) null, bundle != null ? (PendingIntent) bundle.getParcelable("pendingIntent") : null), intent));
    }
}
