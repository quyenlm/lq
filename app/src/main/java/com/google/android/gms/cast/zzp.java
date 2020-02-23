package com.google.android.gms.cast;

import android.support.v7.media.MediaRouter;

final class zzp extends MediaRouter.Callback {
    private /* synthetic */ CastRemoteDisplayLocalService zzapJ;

    zzp(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zzapJ = castRemoteDisplayLocalService;
    }

    public final void onRouteUnselected(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        this.zzapJ.zzbp("onRouteUnselected");
        if (this.zzapJ.zzapB == null) {
            this.zzapJ.zzbp("onRouteUnselected, no device was selected");
        } else if (!CastDevice.getFromBundle(routeInfo.getExtras()).getDeviceId().equals(this.zzapJ.zzapB.getDeviceId())) {
            this.zzapJ.zzbp("onRouteUnselected, device does not match");
        } else {
            CastRemoteDisplayLocalService.stopService();
        }
    }
}
