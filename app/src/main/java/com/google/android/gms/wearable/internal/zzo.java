package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import com.appsflyer.share.Constants;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.CapabilityApi;

public final class zzo implements CapabilityApi {
    private static PendingResult<Status> zza(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, IntentFilter[] intentFilterArr) {
        return zzb.zza(googleApiClient, new zzt(intentFilterArr), capabilityListener);
    }

    public final PendingResult<Status> addCapabilityListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, String str) {
        String str2;
        zzbo.zzb(str != null, (Object) "capability must not be null");
        zzv zzv = new zzv(capabilityListener, str);
        IntentFilter zzgl = zzez.zzgl(CapabilityApi.ACTION_CAPABILITY_CHANGED);
        if (!str.startsWith(Constants.URL_PATH_DELIMITER)) {
            String valueOf = String.valueOf(str);
            str2 = valueOf.length() != 0 ? Constants.URL_PATH_DELIMITER.concat(valueOf) : new String(Constants.URL_PATH_DELIMITER);
        } else {
            str2 = str;
        }
        zzgl.addDataPath(str2, 0);
        return zza(googleApiClient, zzv, new IntentFilter[]{zzgl});
    }

    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, Uri uri, int i) {
        zzbo.zzb(uri != null, (Object) "uri must not be null");
        zzbo.zzb(i == 0 || i == 1, (Object) "invalid filter type");
        return zza(googleApiClient, capabilityListener, new IntentFilter[]{zzez.zza(CapabilityApi.ACTION_CAPABILITY_CHANGED, uri, i)});
    }

    public final PendingResult<CapabilityApi.AddLocalCapabilityResult> addLocalCapability(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zzd(new zzr(this, googleApiClient, str));
    }

    public final PendingResult<CapabilityApi.GetAllCapabilitiesResult> getAllCapabilities(GoogleApiClient googleApiClient, int i) {
        boolean z = true;
        if (!(i == 0 || i == 1)) {
            z = false;
        }
        zzbo.zzaf(z);
        return googleApiClient.zzd(new zzq(this, googleApiClient, i));
    }

    public final PendingResult<CapabilityApi.GetCapabilityResult> getCapability(GoogleApiClient googleApiClient, String str, int i) {
        boolean z = true;
        if (!(i == 0 || i == 1)) {
            z = false;
        }
        zzbo.zzaf(z);
        return googleApiClient.zzd(new zzp(this, googleApiClient, str, i));
    }

    public final PendingResult<Status> removeCapabilityListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, String str) {
        return googleApiClient.zzd(new zzz(googleApiClient, new zzv(capabilityListener, str), (zzp) null));
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener) {
        return googleApiClient.zzd(new zzz(googleApiClient, capabilityListener, (zzp) null));
    }

    public final PendingResult<CapabilityApi.RemoveLocalCapabilityResult> removeLocalCapability(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zzd(new zzs(this, googleApiClient, str));
    }
}
