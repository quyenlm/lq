package com.neovisionaries.ws.client;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

class SocketFactorySettings {
    private SSLContext mSSLContext;
    private SSLSocketFactory mSSLSocketFactory;
    private SocketFactory mSocketFactory;

    SocketFactorySettings() {
    }

    public SocketFactory getSocketFactory() {
        return this.mSocketFactory;
    }

    public void setSocketFactory(SocketFactory factory) {
        this.mSocketFactory = factory;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return this.mSSLSocketFactory;
    }

    public void setSSLSocketFactory(SSLSocketFactory factory) {
        this.mSSLSocketFactory = factory;
    }

    public SSLContext getSSLContext() {
        return this.mSSLContext;
    }

    public void setSSLContext(SSLContext context) {
        this.mSSLContext = context;
    }

    public SocketFactory selectSocketFactory(boolean secure) {
        if (secure) {
            if (this.mSSLContext != null) {
                return this.mSSLContext.getSocketFactory();
            }
            if (this.mSSLSocketFactory != null) {
                return this.mSSLSocketFactory;
            }
            return SSLSocketFactory.getDefault();
        } else if (this.mSocketFactory != null) {
            return this.mSocketFactory;
        } else {
            return SocketFactory.getDefault();
        }
    }
}
