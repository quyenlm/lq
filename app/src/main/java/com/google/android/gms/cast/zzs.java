package com.google.android.gms.cast;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.cast.CastRemoteDisplayLocalService;
import com.google.android.gms.common.api.Status;

final class zzs implements ServiceConnection {
    private /* synthetic */ String zzaoR;
    private /* synthetic */ CastDevice zzapK;
    private /* synthetic */ CastRemoteDisplayLocalService.Options zzapL;
    private /* synthetic */ CastRemoteDisplayLocalService.NotificationSettings zzapM;
    private /* synthetic */ Context zzapN;
    private /* synthetic */ CastRemoteDisplayLocalService.Callbacks zzapO;

    zzs(String str, CastDevice castDevice, CastRemoteDisplayLocalService.Options options, CastRemoteDisplayLocalService.NotificationSettings notificationSettings, Context context, CastRemoteDisplayLocalService.Callbacks callbacks) {
        this.zzaoR = str;
        this.zzapK = castDevice;
        this.zzapL = options;
        this.zzapM = notificationSettings;
        this.zzapN = context;
        this.zzapO = callbacks;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        CastRemoteDisplayLocalService castRemoteDisplayLocalService = CastRemoteDisplayLocalService.this;
        if (castRemoteDisplayLocalService != null) {
            if (castRemoteDisplayLocalService.zza(this.zzaoR, this.zzapK, this.zzapL, this.zzapM, this.zzapN, this, this.zzapO)) {
                return;
            }
        }
        CastRemoteDisplayLocalService.zzapq.zzc("Connected but unable to get the service instance", new Object[0]);
        this.zzapO.onRemoteDisplaySessionError(new Status(CastStatusCodes.ERROR_SERVICE_CREATION_FAILED));
        CastRemoteDisplayLocalService.zzapt.set(false);
        try {
            this.zzapN.unbindService(this);
        } catch (IllegalArgumentException e) {
            CastRemoteDisplayLocalService.zzapq.zzb("No need to unbind service, already unbound", new Object[0]);
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        CastRemoteDisplayLocalService.zzapq.zzb("onServiceDisconnected", new Object[0]);
        this.zzapO.onRemoteDisplaySessionError(new Status(CastStatusCodes.ERROR_SERVICE_DISCONNECTED, "Service Disconnected"));
        CastRemoteDisplayLocalService.zzapt.set(false);
        try {
            this.zzapN.unbindService(this);
        } catch (IllegalArgumentException e) {
            CastRemoteDisplayLocalService.zzapq.zzb("No need to unbind service, already unbound", new Object[0]);
        }
    }
}
