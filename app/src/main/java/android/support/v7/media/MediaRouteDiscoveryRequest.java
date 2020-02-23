package android.support.v7.media;

import android.os.Bundle;

public final class MediaRouteDiscoveryRequest {
    private static final String KEY_ACTIVE_SCAN = "activeScan";
    private static final String KEY_SELECTOR = "selector";
    private final Bundle mBundle;
    private MediaRouteSelector mSelector;

    public MediaRouteDiscoveryRequest(MediaRouteSelector selector, boolean activeScan) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        this.mBundle = new Bundle();
        this.mSelector = selector;
        this.mBundle.putBundle(KEY_SELECTOR, selector.asBundle());
        this.mBundle.putBoolean(KEY_ACTIVE_SCAN, activeScan);
    }

    private MediaRouteDiscoveryRequest(Bundle bundle) {
        this.mBundle = bundle;
    }

    public MediaRouteSelector getSelector() {
        ensureSelector();
        return this.mSelector;
    }

    private void ensureSelector() {
        if (this.mSelector == null) {
            this.mSelector = MediaRouteSelector.fromBundle(this.mBundle.getBundle(KEY_SELECTOR));
            if (this.mSelector == null) {
                this.mSelector = MediaRouteSelector.EMPTY;
            }
        }
    }

    public boolean isActiveScan() {
        return this.mBundle.getBoolean(KEY_ACTIVE_SCAN);
    }

    public boolean isValid() {
        ensureSelector();
        return this.mSelector.isValid();
    }

    public boolean equals(Object o) {
        if (!(o instanceof MediaRouteDiscoveryRequest)) {
            return false;
        }
        MediaRouteDiscoveryRequest other = (MediaRouteDiscoveryRequest) o;
        if (!getSelector().equals(other.getSelector()) || isActiveScan() != other.isActiveScan()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (isActiveScan() ? 1 : 0) ^ getSelector().hashCode();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("DiscoveryRequest{ selector=").append(getSelector());
        result.append(", activeScan=").append(isActiveScan());
        result.append(", isValid=").append(isValid());
        result.append(" }");
        return result.toString();
    }

    public Bundle asBundle() {
        return this.mBundle;
    }

    public static MediaRouteDiscoveryRequest fromBundle(Bundle bundle) {
        if (bundle != null) {
            return new MediaRouteDiscoveryRequest(bundle);
        }
        return null;
    }
}
