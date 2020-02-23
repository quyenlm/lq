package com.facebook.login;

import com.facebook.internal.NativeProtocol;

public enum DefaultAudience {
    NONE((String) null),
    ONLY_ME(NativeProtocol.AUDIENCE_ME),
    FRIENDS("friends"),
    EVERYONE(NativeProtocol.AUDIENCE_EVERYONE);
    
    private final String nativeProtocolAudience;

    private DefaultAudience(String protocol) {
        this.nativeProtocolAudience = protocol;
    }

    public String getNativeProtocolAudience() {
        return this.nativeProtocolAudience;
    }
}
