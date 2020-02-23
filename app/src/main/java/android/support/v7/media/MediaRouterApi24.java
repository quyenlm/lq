package android.support.v7.media;

import android.annotation.TargetApi;
import android.media.MediaRouter;
import android.support.annotation.RequiresApi;

@TargetApi(24)
@RequiresApi(24)
final class MediaRouterApi24 {
    MediaRouterApi24() {
    }

    public static final class RouteInfo {
        public static int getDeviceType(Object routeObj) {
            return ((MediaRouter.RouteInfo) routeObj).getDeviceType();
        }
    }
}
