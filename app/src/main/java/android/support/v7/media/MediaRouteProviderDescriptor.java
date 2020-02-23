package android.support.v7.media;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class MediaRouteProviderDescriptor {
    static final String KEY_ROUTES = "routes";
    final Bundle mBundle;
    List<MediaRouteDescriptor> mRoutes;

    MediaRouteProviderDescriptor(Bundle bundle, List<MediaRouteDescriptor> routes) {
        this.mBundle = bundle;
        this.mRoutes = routes;
    }

    public List<MediaRouteDescriptor> getRoutes() {
        ensureRoutes();
        return this.mRoutes;
    }

    /* access modifiers changed from: package-private */
    public void ensureRoutes() {
        if (this.mRoutes == null) {
            ArrayList<Bundle> routeBundles = this.mBundle.getParcelableArrayList(KEY_ROUTES);
            if (routeBundles == null || routeBundles.isEmpty()) {
                this.mRoutes = Collections.emptyList();
                return;
            }
            int count = routeBundles.size();
            this.mRoutes = new ArrayList(count);
            for (int i = 0; i < count; i++) {
                this.mRoutes.add(MediaRouteDescriptor.fromBundle(routeBundles.get(i)));
            }
        }
    }

    public boolean isValid() {
        ensureRoutes();
        int routeCount = this.mRoutes.size();
        for (int i = 0; i < routeCount; i++) {
            MediaRouteDescriptor route = this.mRoutes.get(i);
            if (route == null || !route.isValid()) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("MediaRouteProviderDescriptor{ ");
        result.append("routes=").append(Arrays.toString(getRoutes().toArray()));
        result.append(", isValid=").append(isValid());
        result.append(" }");
        return result.toString();
    }

    public Bundle asBundle() {
        return this.mBundle;
    }

    public static MediaRouteProviderDescriptor fromBundle(Bundle bundle) {
        if (bundle != null) {
            return new MediaRouteProviderDescriptor(bundle, (List<MediaRouteDescriptor>) null);
        }
        return null;
    }

    public static final class Builder {
        private final Bundle mBundle;
        private ArrayList<MediaRouteDescriptor> mRoutes;

        public Builder() {
            this.mBundle = new Bundle();
        }

        public Builder(MediaRouteProviderDescriptor descriptor) {
            if (descriptor == null) {
                throw new IllegalArgumentException("descriptor must not be null");
            }
            this.mBundle = new Bundle(descriptor.mBundle);
            descriptor.ensureRoutes();
            if (!descriptor.mRoutes.isEmpty()) {
                this.mRoutes = new ArrayList<>(descriptor.mRoutes);
            }
        }

        public Builder addRoute(MediaRouteDescriptor route) {
            if (route == null) {
                throw new IllegalArgumentException("route must not be null");
            }
            if (this.mRoutes == null) {
                this.mRoutes = new ArrayList<>();
            } else if (this.mRoutes.contains(route)) {
                throw new IllegalArgumentException("route descriptor already added");
            }
            this.mRoutes.add(route);
            return this;
        }

        public Builder addRoutes(Collection<MediaRouteDescriptor> routes) {
            if (routes == null) {
                throw new IllegalArgumentException("routes must not be null");
            }
            if (!routes.isEmpty()) {
                for (MediaRouteDescriptor route : routes) {
                    addRoute(route);
                }
            }
            return this;
        }

        public MediaRouteProviderDescriptor build() {
            if (this.mRoutes != null) {
                int count = this.mRoutes.size();
                ArrayList<Bundle> routeBundles = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    routeBundles.add(this.mRoutes.get(i).asBundle());
                }
                this.mBundle.putParcelableArrayList(MediaRouteProviderDescriptor.KEY_ROUTES, routeBundles);
            }
            return new MediaRouteProviderDescriptor(this.mBundle, this.mRoutes);
        }
    }
}
