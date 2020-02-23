package com.neovisionaries.ws.client;

import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

class SocketConnector {
    private final Address mAddress;
    private final int mConnectionTimeout;
    private final String mHost;
    private final int mPort;
    private final ProxyHandshaker mProxyHandshaker;
    private final SSLSocketFactory mSSLSocketFactory;
    private Socket mSocket;

    SocketConnector(Socket socket, Address address, int timeout) {
        this(socket, address, timeout, (ProxyHandshaker) null, (SSLSocketFactory) null, (String) null, 0);
    }

    SocketConnector(Socket socket, Address address, int timeout, ProxyHandshaker handshaker, SSLSocketFactory sslSocketFactory, String host, int port) {
        this.mSocket = socket;
        this.mAddress = address;
        this.mConnectionTimeout = timeout;
        this.mProxyHandshaker = handshaker;
        this.mSSLSocketFactory = sslSocketFactory;
        this.mHost = host;
        this.mPort = port;
    }

    public Socket getSocket() {
        return this.mSocket;
    }

    public int getConnectionTimeout() {
        return this.mConnectionTimeout;
    }

    public void connect() throws WebSocketException {
        try {
            doConnect();
        } catch (WebSocketException e) {
            try {
                this.mSocket.close();
            } catch (IOException e2) {
            }
            throw e;
        }
    }

    private void doConnect() throws WebSocketException {
        boolean proxied;
        if (this.mProxyHandshaker != null) {
            proxied = true;
        } else {
            proxied = false;
        }
        try {
            this.mSocket.connect(this.mAddress.toInetSocketAddress(), this.mConnectionTimeout);
            if (this.mSocket instanceof SSLSocket) {
                verifyHostname((SSLSocket) this.mSocket, this.mAddress.getHostname());
            }
            if (proxied) {
                handshake();
            }
        } catch (IOException e) {
            Object[] objArr = new Object[3];
            objArr[0] = proxied ? "the proxy " : "";
            objArr[1] = this.mAddress;
            objArr[2] = e.getMessage();
            throw new WebSocketException(WebSocketError.SOCKET_CONNECT_ERROR, String.format("Failed to connect to %s'%s': %s", objArr), e);
        }
    }

    private void verifyHostname(SSLSocket socket, String hostname) throws HostnameUnverifiedException {
        if (!OkHostnameVerifier.INSTANCE.verify(hostname, socket.getSession())) {
            throw new HostnameUnverifiedException(socket, hostname);
        }
    }

    private void handshake() throws WebSocketException {
        try {
            this.mProxyHandshaker.perform();
            if (this.mSSLSocketFactory != null) {
                try {
                    this.mSocket = this.mSSLSocketFactory.createSocket(this.mSocket, this.mHost, this.mPort, true);
                    try {
                        ((SSLSocket) this.mSocket).startHandshake();
                        if (this.mSocket instanceof SSLSocket) {
                            verifyHostname((SSLSocket) this.mSocket, this.mProxyHandshaker.getProxiedHostname());
                        }
                    } catch (IOException e) {
                        throw new WebSocketException(WebSocketError.SSL_HANDSHAKE_ERROR, String.format("SSL handshake with the WebSocket endpoint (%s) failed: %s", new Object[]{this.mAddress, e.getMessage()}), e);
                    }
                } catch (IOException e2) {
                    throw new WebSocketException(WebSocketError.SOCKET_OVERLAY_ERROR, "Failed to overlay an existing socket: " + e2.getMessage(), e2);
                }
            }
        } catch (IOException e3) {
            throw new WebSocketException(WebSocketError.PROXY_HANDSHAKE_ERROR, String.format("Handshake with the proxy server (%s) failed: %s", new Object[]{this.mAddress, e3.getMessage()}), e3);
        }
    }

    /* access modifiers changed from: package-private */
    public void closeSilently() {
        try {
            this.mSocket.close();
        } catch (Throwable th) {
        }
    }
}
