package com.amazonaws;

import com.vk.sdk.api.VKApiConst;
import org.apache.http.HttpHost;

public enum Protocol {
    HTTP(HttpHost.DEFAULT_SCHEME_NAME),
    HTTPS(VKApiConst.HTTPS);
    
    private final String protocol;

    private Protocol(String protocol2) {
        this.protocol = protocol2;
    }

    public String toString() {
        return this.protocol;
    }
}
