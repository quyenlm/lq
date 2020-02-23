package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;

public enum AppInviteDialogFeature implements DialogFeature {
    APP_INVITES_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140701);
    
    private int minVersion;

    private AppInviteDialogFeature(int minVersion2) {
        this.minVersion = minVersion2;
    }

    public String getAction() {
        return NativeProtocol.ACTION_APPINVITE_DIALOG;
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
