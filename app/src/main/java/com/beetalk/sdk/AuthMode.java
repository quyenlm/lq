package com.beetalk.sdk;

import com.facebook.internal.AnalyticsEvents;

public enum AuthMode {
    NATIVE_ONLY(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE),
    LEGACY_ONLY("legacy_only"),
    LEGACY_ENABLED("legacy");
    
    String value;

    private AuthMode(String value2) {
        this.value = value2;
    }

    public String getValue() {
        return this.value;
    }
}
