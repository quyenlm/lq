package com.tencent.qqgamemi.api;

public enum SDKFeature {
    Moment(1),
    Manual(2),
    InGameAudio(4),
    Maintaining(8),
    Report(16);
    
    private int mode;

    private SDKFeature(int mode2) {
        this.mode = mode2;
    }

    public boolean isEnable(int sdkFeature) {
        return (this.mode & sdkFeature) == this.mode;
    }
}
