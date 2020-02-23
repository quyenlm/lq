package android.support.v7.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.media.MediaRouterJellybean;
import android.util.Log;
import android.view.Display;
import com.facebook.internal.ServerProtocol;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(17)
@RequiresApi(17)
final class MediaRouterJellybeanMr1 {
    private static final String TAG = "MediaRouterJellybeanMr1";

    public interface Callback extends MediaRouterJellybean.Callback {
        void onRoutePresentationDisplayChanged(Object obj);
    }

    MediaRouterJellybeanMr1() {
    }

    public static Object createCallback(Callback callback) {
        return new CallbackProxy(callback);
    }

    public static final class RouteInfo {
        public static boolean isEnabled(Object routeObj) {
            return ((MediaRouter.RouteInfo) routeObj).isEnabled();
        }

        public static Display getPresentationDisplay(Object routeObj) {
            try {
                return ((MediaRouter.RouteInfo) routeObj).getPresentationDisplay();
            } catch (NoSuchMethodError ex) {
                Log.w(MediaRouterJellybeanMr1.TAG, "Cannot get presentation display for the route.", ex);
                return null;
            }
        }
    }

    public static final class ActiveScanWorkaround implements Runnable {
        private static final int WIFI_DISPLAY_SCAN_INTERVAL = 15000;
        private boolean mActivelyScanningWifiDisplays;
        private final DisplayManager mDisplayManager;
        private final Handler mHandler;
        private Method mScanWifiDisplaysMethod;

        public ActiveScanWorkaround(Context context, Handler handler) {
            if (Build.VERSION.SDK_INT != 17) {
                throw new UnsupportedOperationException();
            }
            this.mDisplayManager = (DisplayManager) context.getSystemService(ServerProtocol.DIALOG_PARAM_DISPLAY);
            this.mHandler = handler;
            try {
                this.mScanWifiDisplaysMethod = DisplayManager.class.getMethod("scanWifiDisplays", new Class[0]);
            } catch (NoSuchMethodException e) {
            }
        }

        public void setActiveScanRouteTypes(int routeTypes) {
            if ((routeTypes & 2) != 0) {
                if (this.mActivelyScanningWifiDisplays) {
                    return;
                }
                if (this.mScanWifiDisplaysMethod != null) {
                    this.mActivelyScanningWifiDisplays = true;
                    this.mHandler.post(this);
                    return;
                }
                Log.w(MediaRouterJellybeanMr1.TAG, "Cannot scan for wifi displays because the DisplayManager.scanWifiDisplays() method is not available on this device.");
            } else if (this.mActivelyScanningWifiDisplays) {
                this.mActivelyScanningWifiDisplays = false;
                this.mHandler.removeCallbacks(this);
            }
        }

        public void run() {
            if (this.mActivelyScanningWifiDisplays) {
                try {
                    this.mScanWifiDisplaysMethod.invoke(this.mDisplayManager, new Object[0]);
                } catch (IllegalAccessException ex) {
                    Log.w(MediaRouterJellybeanMr1.TAG, "Cannot scan for wifi displays.", ex);
                } catch (InvocationTargetException ex2) {
                    Log.w(MediaRouterJellybeanMr1.TAG, "Cannot scan for wifi displays.", ex2);
                }
                this.mHandler.postDelayed(this, 15000);
            }
        }
    }

    public static final class IsConnectingWorkaround {
        private Method mGetStatusCodeMethod;
        private int mStatusConnecting;

        public IsConnectingWorkaround() {
            if (Build.VERSION.SDK_INT != 17) {
                throw new UnsupportedOperationException();
            }
            try {
                this.mStatusConnecting = MediaRouter.RouteInfo.class.getField("STATUS_CONNECTING").getInt((Object) null);
                this.mGetStatusCodeMethod = MediaRouter.RouteInfo.class.getMethod("getStatusCode", new Class[0]);
            } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException e) {
            }
        }

        public boolean isConnecting(Object routeObj) {
            MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) routeObj;
            if (this.mGetStatusCodeMethod != null) {
                try {
                    if (((Integer) this.mGetStatusCodeMethod.invoke(route, new Object[0])).intValue() == this.mStatusConnecting) {
                        return true;
                    }
                    return false;
                } catch (IllegalAccessException | InvocationTargetException e) {
                }
            }
            return false;
        }
    }

    static class CallbackProxy<T extends Callback> extends MediaRouterJellybean.CallbackProxy<T> {
        public CallbackProxy(T callback) {
            super(callback);
        }

        public void onRoutePresentationDisplayChanged(MediaRouter router, MediaRouter.RouteInfo route) {
            ((Callback) this.mCallback).onRoutePresentationDisplayChanged(route);
        }
    }
}
