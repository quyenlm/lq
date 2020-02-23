package com.google.firebase.remoteconfig;

public class FirebaseRemoteConfigSettings {
    private final boolean zzcnA;

    public static class Builder {
        /* access modifiers changed from: private */
        public boolean zzcnA = false;

        public FirebaseRemoteConfigSettings build() {
            return new FirebaseRemoteConfigSettings(this);
        }

        public Builder setDeveloperModeEnabled(boolean z) {
            this.zzcnA = z;
            return this;
        }
    }

    private FirebaseRemoteConfigSettings(Builder builder) {
        this.zzcnA = builder.zzcnA;
    }

    public boolean isDeveloperModeEnabled() {
        return this.zzcnA;
    }
}
