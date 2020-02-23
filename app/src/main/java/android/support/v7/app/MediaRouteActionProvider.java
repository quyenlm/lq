package android.support.v7.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

public class MediaRouteActionProvider extends ActionProvider {
    private static final String TAG = "MediaRouteActionProvider";
    private MediaRouteButton mButton;
    private final MediaRouterCallback mCallback;
    private MediaRouteDialogFactory mDialogFactory = MediaRouteDialogFactory.getDefault();
    private final MediaRouter mRouter;
    private MediaRouteSelector mSelector = MediaRouteSelector.EMPTY;

    public MediaRouteActionProvider(Context context) {
        super(context);
        this.mRouter = MediaRouter.getInstance(context);
        this.mCallback = new MediaRouterCallback(this);
    }

    @NonNull
    public MediaRouteSelector getRouteSelector() {
        return this.mSelector;
    }

    public void setRouteSelector(@NonNull MediaRouteSelector selector) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (!this.mSelector.equals(selector)) {
            if (!this.mSelector.isEmpty()) {
                this.mRouter.removeCallback(this.mCallback);
            }
            if (!selector.isEmpty()) {
                this.mRouter.addCallback(selector, this.mCallback);
            }
            this.mSelector = selector;
            refreshRoute();
            if (this.mButton != null) {
                this.mButton.setRouteSelector(selector);
            }
        }
    }

    @NonNull
    public MediaRouteDialogFactory getDialogFactory() {
        return this.mDialogFactory;
    }

    public void setDialogFactory(@NonNull MediaRouteDialogFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("factory must not be null");
        } else if (this.mDialogFactory != factory) {
            this.mDialogFactory = factory;
            if (this.mButton != null) {
                this.mButton.setDialogFactory(factory);
            }
        }
    }

    @Nullable
    public MediaRouteButton getMediaRouteButton() {
        return this.mButton;
    }

    public MediaRouteButton onCreateMediaRouteButton() {
        return new MediaRouteButton(getContext());
    }

    public View onCreateActionView() {
        if (this.mButton != null) {
            Log.e(TAG, "onCreateActionView: this ActionProvider is already associated with a menu item. Don't reuse MediaRouteActionProvider instances! Abandoning the old menu item...");
        }
        this.mButton = onCreateMediaRouteButton();
        this.mButton.setCheatSheetEnabled(true);
        this.mButton.setRouteSelector(this.mSelector);
        this.mButton.setDialogFactory(this.mDialogFactory);
        this.mButton.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
        return this.mButton;
    }

    public boolean onPerformDefaultAction() {
        if (this.mButton != null) {
            return this.mButton.showDialog();
        }
        return false;
    }

    public boolean overridesItemVisibility() {
        return true;
    }

    public boolean isVisible() {
        return this.mRouter.isRouteAvailable(this.mSelector, 1);
    }

    /* access modifiers changed from: package-private */
    public void refreshRoute() {
        refreshVisibility();
    }

    private static final class MediaRouterCallback extends MediaRouter.Callback {
        private final WeakReference<MediaRouteActionProvider> mProviderWeak;

        public MediaRouterCallback(MediaRouteActionProvider provider) {
            this.mProviderWeak = new WeakReference<>(provider);
        }

        public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo info) {
            refreshRoute(router);
        }

        public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo info) {
            refreshRoute(router);
        }

        public void onRouteChanged(MediaRouter router, MediaRouter.RouteInfo info) {
            refreshRoute(router);
        }

        public void onProviderAdded(MediaRouter router, MediaRouter.ProviderInfo provider) {
            refreshRoute(router);
        }

        public void onProviderRemoved(MediaRouter router, MediaRouter.ProviderInfo provider) {
            refreshRoute(router);
        }

        public void onProviderChanged(MediaRouter router, MediaRouter.ProviderInfo provider) {
            refreshRoute(router);
        }

        private void refreshRoute(MediaRouter router) {
            MediaRouteActionProvider provider = (MediaRouteActionProvider) this.mProviderWeak.get();
            if (provider != null) {
                provider.refreshRoute();
            } else {
                router.removeCallback(this);
            }
        }
    }
}
