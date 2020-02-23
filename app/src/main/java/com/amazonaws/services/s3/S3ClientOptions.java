package com.amazonaws.services.s3;

public class S3ClientOptions {
    public static final boolean DEFAULT_ACCELERATE_MODE_ENABLED = false;
    public static final boolean DEFAULT_PATH_STYLE_ACCESS = false;
    private boolean accelerateModeEnabled;
    private boolean pathStyleAccess;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean accelerateModeEnabled;
        private boolean pathStyleAccess;

        private Builder() {
            this.pathStyleAccess = false;
            this.accelerateModeEnabled = false;
        }

        public S3ClientOptions build() {
            return new S3ClientOptions(this.pathStyleAccess, this.accelerateModeEnabled);
        }

        public Builder setPathStyleAccess(boolean pathStyleAccess2) {
            this.pathStyleAccess = pathStyleAccess2;
            return this;
        }

        public Builder setAccelerateModeEnabled(boolean accelerateModeEnabled2) {
            this.accelerateModeEnabled = accelerateModeEnabled2;
            return this;
        }
    }

    @Deprecated
    public S3ClientOptions() {
    }

    @Deprecated
    public S3ClientOptions(S3ClientOptions other) {
        this.pathStyleAccess = other.pathStyleAccess;
        this.accelerateModeEnabled = other.accelerateModeEnabled;
    }

    private S3ClientOptions(boolean pathStyleAccess2, boolean accelerateModeEnabled2) {
        this.pathStyleAccess = pathStyleAccess2;
        this.accelerateModeEnabled = accelerateModeEnabled2;
    }

    public boolean isPathStyleAccess() {
        return this.pathStyleAccess;
    }

    public boolean isAccelerateModeEnabled() {
        return this.accelerateModeEnabled;
    }

    @Deprecated
    public void setPathStyleAccess(boolean pathStyleAccess2) {
        this.pathStyleAccess = pathStyleAccess2;
    }

    @Deprecated
    public S3ClientOptions withPathStyleAccess(boolean pathStyleAccess2) {
        setPathStyleAccess(pathStyleAccess2);
        return this;
    }
}
