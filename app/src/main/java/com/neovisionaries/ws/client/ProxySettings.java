package com.neovisionaries.ws.client;

import com.vk.sdk.api.VKApiConst;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpHost;

public class ProxySettings {
    private final Map<String, List<String>> mHeaders = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    private String mHost;
    private String mId;
    private String mPassword;
    private int mPort;
    private boolean mSecure;
    private final SocketFactorySettings mSocketFactorySettings = new SocketFactorySettings();
    private final WebSocketFactory mWebSocketFactory;

    ProxySettings(WebSocketFactory factory) {
        this.mWebSocketFactory = factory;
        reset();
    }

    public WebSocketFactory getWebSocketFactory() {
        return this.mWebSocketFactory;
    }

    public ProxySettings reset() {
        this.mSecure = false;
        this.mHost = null;
        this.mPort = -1;
        this.mId = null;
        this.mPassword = null;
        this.mHeaders.clear();
        return this;
    }

    public boolean isSecure() {
        return this.mSecure;
    }

    public ProxySettings setSecure(boolean secure) {
        this.mSecure = secure;
        return this;
    }

    public String getHost() {
        return this.mHost;
    }

    public ProxySettings setHost(String host) {
        this.mHost = host;
        return this;
    }

    public int getPort() {
        return this.mPort;
    }

    public ProxySettings setPort(int port) {
        this.mPort = port;
        return this;
    }

    public String getId() {
        return this.mId;
    }

    public ProxySettings setId(String id) {
        this.mId = id;
        return this;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public ProxySettings setPassword(String password) {
        this.mPassword = password;
        return this;
    }

    public ProxySettings setCredentials(String id, String password) {
        return setId(id).setPassword(password);
    }

    /* Debug info: failed to restart local var, previous not found, register: 1 */
    public ProxySettings setServer(String uri) {
        return uri == null ? this : setServer(URI.create(uri));
    }

    /* Debug info: failed to restart local var, previous not found, register: 2 */
    public ProxySettings setServer(URL url) {
        if (url == null) {
            return this;
        }
        try {
            return setServer(url.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 4 */
    public ProxySettings setServer(URI uri) {
        return uri == null ? this : setServer(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort());
    }

    private ProxySettings setServer(String scheme, String userInfo, String host, int port) {
        setByScheme(scheme);
        setByUserInfo(userInfo);
        this.mHost = host;
        this.mPort = port;
        return this;
    }

    private void setByScheme(String scheme) {
        if (HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(scheme)) {
            this.mSecure = false;
        } else if (VKApiConst.HTTPS.equalsIgnoreCase(scheme)) {
            this.mSecure = true;
        }
    }

    private void setByUserInfo(String userInfo) {
        String id;
        String pw;
        if (userInfo != null) {
            String[] pair = userInfo.split(":", 2);
            switch (pair.length) {
                case 1:
                    id = pair[0];
                    pw = null;
                    break;
                case 2:
                    id = pair[0];
                    pw = pair[1];
                    break;
                default:
                    return;
            }
            if (id.length() != 0) {
                this.mId = id;
                this.mPassword = pw;
            }
        }
    }

    public Map<String, List<String>> getHeaders() {
        return this.mHeaders;
    }

    public ProxySettings addHeader(String name, String value) {
        if (!(name == null || name.length() == 0)) {
            List<String> list = this.mHeaders.get(name);
            if (list == null) {
                list = new ArrayList<>();
                this.mHeaders.put(name, list);
            }
            list.add(value);
        }
        return this;
    }

    public SocketFactory getSocketFactory() {
        return this.mSocketFactorySettings.getSocketFactory();
    }

    public ProxySettings setSocketFactory(SocketFactory factory) {
        this.mSocketFactorySettings.setSocketFactory(factory);
        return this;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return this.mSocketFactorySettings.getSSLSocketFactory();
    }

    public ProxySettings setSSLSocketFactory(SSLSocketFactory factory) {
        this.mSocketFactorySettings.setSSLSocketFactory(factory);
        return this;
    }

    public SSLContext getSSLContext() {
        return this.mSocketFactorySettings.getSSLContext();
    }

    public ProxySettings setSSLContext(SSLContext context) {
        this.mSocketFactorySettings.setSSLContext(context);
        return this;
    }

    /* access modifiers changed from: package-private */
    public SocketFactory selectSocketFactory() {
        return this.mSocketFactorySettings.selectSocketFactory(this.mSecure);
    }
}
