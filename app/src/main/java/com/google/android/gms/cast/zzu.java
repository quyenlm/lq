package com.google.android.gms.cast;

import com.google.android.gms.cast.CastRemoteDisplayLocalService;

final class zzu implements Runnable {
    private /* synthetic */ CastRemoteDisplayLocalService zzapJ;
    private /* synthetic */ CastRemoteDisplayLocalService.NotificationSettings zzapM;

    zzu(CastRemoteDisplayLocalService castRemoteDisplayLocalService, CastRemoteDisplayLocalService.NotificationSettings notificationSettings) {
        this.zzapJ = castRemoteDisplayLocalService;
        this.zzapM = notificationSettings;
    }

    public final void run() {
        this.zzapJ.zza(this.zzapM);
    }
}
