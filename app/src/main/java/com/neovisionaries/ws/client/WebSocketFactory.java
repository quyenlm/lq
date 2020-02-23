package com.neovisionaries.ws.client;

import com.appsflyer.share.Constants;
import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpHost;

public class WebSocketFactory {
    private int mConnectionTimeout;
    private final ProxySettings mProxySettings = new ProxySettings(this);
    private final SocketFactorySettings mSocketFactorySettings = new SocketFactorySettings();

    public SocketFactory getSocketFactory() {
        return this.mSocketFactorySettings.getSocketFactory();
    }

    public WebSocketFactory setSocketFactory(SocketFactory factory) {
        this.mSocketFactorySettings.setSocketFactory(factory);
        return this;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return this.mSocketFactorySettings.getSSLSocketFactory();
    }

    public WebSocketFactory setSSLSocketFactory(SSLSocketFactory factory) {
        this.mSocketFactorySettings.setSSLSocketFactory(factory);
        return this;
    }

    public SSLContext getSSLContext() {
        return this.mSocketFactorySettings.getSSLContext();
    }

    public WebSocketFactory setSSLContext(SSLContext context) {
        this.mSocketFactorySettings.setSSLContext(context);
        return this;
    }

    public ProxySettings getProxySettings() {
        return this.mProxySettings;
    }

    public int getConnectionTimeout() {
        return this.mConnectionTimeout;
    }

    public WebSocketFactory setConnectionTimeout(int timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout value cannot be negative.");
        }
        this.mConnectionTimeout = timeout;
        return this;
    }

    public WebSocket createSocket(String uri) throws IOException {
        return createSocket(uri, getConnectionTimeout());
    }

    public WebSocket createSocket(String uri, int timeout) throws IOException {
        if (uri == null) {
            throw new IllegalArgumentException("The given URI is null.");
        } else if (timeout >= 0) {
            return createSocket(URI.create(uri), timeout);
        } else {
            throw new IllegalArgumentException("The given timeout value is negative.");
        }
    }

    public WebSocket createSocket(URL url) throws IOException {
        return createSocket(url, getConnectionTimeout());
    }

    public WebSocket createSocket(URL url, int timeout) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("The given URL is null.");
        } else if (timeout < 0) {
            throw new IllegalArgumentException("The given timeout value is negative.");
        } else {
            try {
                return createSocket(url.toURI(), timeout);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Failed to convert the given URL into a URI.");
            }
        }
    }

    public WebSocket createSocket(URI uri) throws IOException {
        return createSocket(uri, getConnectionTimeout());
    }

    public WebSocket createSocket(URI uri, int timeout) throws IOException {
        if (uri == null) {
            throw new IllegalArgumentException("The given URI is null.");
        } else if (timeout < 0) {
            throw new IllegalArgumentException("The given timeout value is negative.");
        } else {
            return createSocket(uri.getScheme(), uri.getUserInfo(), Misc.extractHost(uri), uri.getPort(), uri.getRawPath(), uri.getRawQuery(), timeout);
        }
    }

    private WebSocket createSocket(String scheme, String userInfo, String host, int port, String path, String query, int timeout) throws IOException {
        boolean secure = isSecureConnectionRequired(scheme);
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("The host part is empty.");
        }
        return createWebSocket(secure, userInfo, host, port, determinePath(path), query, createRawSocket(host, port, secure, timeout));
    }

    private static boolean isSecureConnectionRequired(String scheme) {
        if (scheme == null || scheme.length() == 0) {
            throw new IllegalArgumentException("The scheme part is empty.");
        } else if ("wss".equalsIgnoreCase(scheme) || VKApiConst.HTTPS.equalsIgnoreCase(scheme)) {
            return true;
        } else {
            if ("ws".equalsIgnoreCase(scheme) || HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(scheme)) {
                return false;
            }
            throw new IllegalArgumentException("Bad scheme: " + scheme);
        }
    }

    private static String determinePath(String path) {
        if (path == null || path.length() == 0) {
            return Constants.URL_PATH_DELIMITER;
        }
        return !path.startsWith(Constants.URL_PATH_DELIMITER) ? Constants.URL_PATH_DELIMITER + path : path;
    }

    private SocketConnector createRawSocket(String host, int port, boolean secure, int timeout) throws IOException {
        int port2 = determinePort(port, secure);
        if (this.mProxySettings.getHost() != null) {
            return createProxiedRawSocket(host, port2, secure, timeout);
        }
        return createDirectRawSocket(host, port2, secure, timeout);
    }

    private SocketConnector createProxiedRawSocket(String host, int port, boolean secure, int timeout) throws IOException {
        int proxyPort = determinePort(this.mProxySettings.getPort(), this.mProxySettings.isSecure());
        Socket socket = this.mProxySettings.selectSocketFactory().createSocket();
        return new SocketConnector(socket, new Address(this.mProxySettings.getHost(), proxyPort), timeout, new ProxyHandshaker(socket, host, port, this.mProxySettings), secure ? (SSLSocketFactory) this.mSocketFactorySettings.selectSocketFactory(secure) : null, host, port);
    }

    private SocketConnector createDirectRawSocket(String host, int port, boolean secure, int timeout) throws IOException {
        return new SocketConnector(this.mSocketFactorySettings.selectSocketFactory(secure).createSocket(), new Address(host, port), timeout);
    }

    private static int determinePort(int port, boolean secure) {
        if (port >= 0) {
            return port;
        }
        if (secure) {
            return 443;
        }
        return 80;
    }

    private WebSocket createWebSocket(boolean secure, String userInfo, String host, int port, String path, String query, SocketConnector connector) {
        if (port >= 0) {
            host = host + ":" + port;
        }
        if (query != null) {
            path = path + "?" + query;
        }
        return new WebSocket(this, secure, userInfo, host, path, connector);
    }
}
