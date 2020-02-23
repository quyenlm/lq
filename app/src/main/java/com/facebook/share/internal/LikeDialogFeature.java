package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;

@Deprecated
public enum LikeDialogFeature implements DialogFeature {
    LIKE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140701);
    
    private int minVersion;

    private LikeDialogFeature(int minVersion2) {
        this.minVersion = minVersion2;
    }

    public String getAction() {
        return NativeProtocol.ACTION_LIKE_DIALOG;
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
