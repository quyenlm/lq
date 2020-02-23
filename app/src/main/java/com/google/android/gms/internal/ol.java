package com.google.android.gms.internal;

import org.apache.http.HttpHost;

public final class ol {
    private final String host;
    private final boolean secure;
    private final String zzbxU;

    public ol(String str, String str2, boolean z) {
        this.host = str;
        this.zzbxU = str2;
        this.secure = z;
    }

    public final String getHost() {
        return this.host;
    }

    public final String getNamespace() {
        return this.zzbxU;
    }

    public final boolean isSecure() {
        return this.secure;
    }

    public final String toString() {
        String str = this.secure ? "s" : "";
        String str2 = this.host;
        return new StringBuilder(String.valueOf(str).length() + 7 + String.valueOf(str2).length()).append(HttpHost.DEFAULT_SCHEME_NAME).append(str).append("://").append(str2).toString();
    }
}
