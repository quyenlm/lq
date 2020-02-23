package com.neovisionaries.ws.client;

import java.net.InetSocketAddress;

class Address {
    private final String mHost;
    private final int mPort;
    private transient String mString;

    Address(String host, int port) {
        this.mHost = host;
        this.mPort = port;
    }

    /* access modifiers changed from: package-private */
    public InetSocketAddress toInetSocketAddress() {
        return new InetSocketAddress(this.mHost, this.mPort);
    }

    /* access modifiers changed from: package-private */
    public String getHostname() {
        return this.mHost;
    }

    public String toString() {
        if (this.mString == null) {
            this.mString = String.format("%s:%d", new Object[]{this.mHost, Integer.valueOf(this.mPort)});
        }
        return this.mString;
    }
}
