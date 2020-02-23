package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;

public final class InstallReferrerReceiver extends CampaignTrackingReceiver {
    /* access modifiers changed from: protected */
    public final Class<? extends CampaignTrackingService> zzjm() {
        return InstallReferrerService.class;
    }

    /* access modifiers changed from: protected */
    public final void zzu(Context context, String str) {
        zzcx.zzfn(str);
        zzcx.zzK(context, str);
    }
}
