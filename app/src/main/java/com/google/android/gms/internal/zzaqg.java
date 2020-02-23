package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.api.Status;

final class zzaqg extends zzapz {
    private /* synthetic */ zzaqf zzakd;

    zzaqg(zzaqf zzaqf) {
        this.zzakd = zzaqf;
    }

    public final void zza(Status status, Intent intent) {
        Activity activity;
        this.zzakd.setResult(new zzaqi(status, intent));
        if (AppInviteReferral.hasReferral(intent) && this.zzakd.zzakb && this.zzakd.zzaka != null && (activity = (Activity) this.zzakd.zzaka.get()) != null) {
            activity.startActivity(intent);
        }
    }
}
