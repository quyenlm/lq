package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;

public enum ShareStoryFeature implements DialogFeature {
    SHARE_STORY_ASSET(NativeProtocol.PROTOCOL_VERSION_20170417);
    
    private int minVersion;

    private ShareStoryFeature(int minVersion2) {
        this.minVersion = minVersion2;
    }

    public String getAction() {
        return NativeProtocol.ACTION_SHARE_STORY;
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
