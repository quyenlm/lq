package com.google.android.gms.cast;

final class zzr implements Runnable {
    private /* synthetic */ CastRemoteDisplayLocalService zzapJ;

    zzr(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zzapJ = castRemoteDisplayLocalService;
    }

    public final void run() {
        this.zzapJ.zzbp(new StringBuilder(59).append("onCreate after delay. The local service been started: ").append(this.zzapJ.zzapF).toString());
        if (!this.zzapJ.zzapF) {
            this.zzapJ.zzbs("The local service has not been been started, stopping it");
            this.zzapJ.stopSelf();
        }
    }
}
