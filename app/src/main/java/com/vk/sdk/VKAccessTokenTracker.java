package com.vk.sdk;

import android.support.annotation.Nullable;

public abstract class VKAccessTokenTracker {
    private boolean isTracking = false;

    public abstract void onVKAccessTokenChanged(@Nullable VKAccessToken vKAccessToken, @Nullable VKAccessToken vKAccessToken2);

    public boolean isTracking() {
        return this.isTracking;
    }

    public void startTracking() {
        VKSdk.addVKTokenTracker(this);
        this.isTracking = true;
    }

    public void stopTracking() {
        VKSdk.removeVKTokenTracker(this);
        this.isTracking = false;
    }
}
