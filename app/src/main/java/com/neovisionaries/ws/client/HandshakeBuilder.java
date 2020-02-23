package com.neovisionaries.ws.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

class HandshakeBuilder {
    private static final String[] CONNECTION_HEADER = {"Connection", "Upgrade"};
    private static final String RN = "\r\n";
    private static final String[] UPGRADE_HEADER = {"Upgrade", "websocket"};
    private static final String[] VERSION_HEADER = {"Sec-WebSocket-Version", "13"};
    private List<WebSocketExtension> mExtensions;
    private List<String[]> mHeaders;
    private final String mHost;
    private String mKey;
    private final String mPath;
    private Set<String> mProtocols;
    private boolean mSecure;
    private final URI mUri;
    private String mUserInfo;

    public HandshakeBuilder(boolean secure, String userInfo, String host, String path) {
        this.mSecure = secure;
        this.mUserInfo = userInfo;
        this.mHost = host;
        this.mPath = path;
        Object[] objArr = new Object[3];
        objArr[0] = secure ? "wss" : "ws";
        objArr[1] = host;
        objArr[2] = path;
        this.mUri = URI.create(String.format("%s://%s%s", objArr));
    }

    public HandshakeBuilder(HandshakeBuilder source) {
        this.mSecure = source.mSecure;
        this.mUserInfo = source.mUserInfo;
        this.mHost = source.mHost;
        this.mPath = source.mPath;
        this.mUri = source.mUri;
        this.mKey = source.mKey;
        this.mProtocols = copyProtocols(source.mProtocols);
        this.mExtensions = copyExtensions(source.mExtensions);
        this.mHeaders = copyHeaders(source.mHeaders);
    }

    public void addProtocol(String protocol) {
        if (!isValidProtocol(protocol)) {
            throw new IllegalArgumentException("'protocol' must be a non-empty string with characters in the range U+0021 to U+007E not including separator characters.");
        }
        synchronized (this) {
            if (this.mProtocols == null) {
                this.mProtocols = new LinkedHashSet();
            }
            this.mProtocols.add(protocol);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeProtocol(java.lang.String r2) {
        /*
            r1 = this;
            if (r2 != 0) goto L_0x0003
        L_0x0002:
            return
        L_0x0003:
            monitor-enter(r1)
            java.util.Set<java.lang.String> r0 = r1.mProtocols     // Catch:{ all -> 0x000a }
            if (r0 != 0) goto L_0x000d
            monitor-exit(r1)     // Catch:{ all -> 0x000a }
            goto L_0x0002
        L_0x000a:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x000a }
            throw r0
        L_0x000d:
            java.util.Set<java.lang.String> r0 = r1.mProtocols     // Catch:{ all -> 0x000a }
            r0.remove(r2)     // Catch:{ all -> 0x000a }
            java.util.Set<java.lang.String> r0 = r1.mProtocols     // Catch:{ all -> 0x000a }
            int r0 = r0.size()     // Catch:{ all -> 0x000a }
            if (r0 != 0) goto L_0x001d
            r0 = 0
            r1.mProtocols = r0     // Catch:{ all -> 0x000a }
        L_0x001d:
            monitor-exit(r1)     // Catch:{ all -> 0x000a }
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: com.neovisionaries.ws.client.HandshakeBuilder.removeProtocol(java.lang.String):void");
    }

    public void clearProtocols() {
        synchronized (this) {
            this.mProtocols = null;
        }
    }

    private static boolean isValidProtocol(String protocol) {
        if (protocol == null || protocol.length() == 0) {
            return false;
        }
        int len = protocol.length();
        for (int i = 0; i < len; i++) {
            char ch = protocol.charAt(i);
            if (ch < '!' || '~' < ch || Token.isSeparator(ch)) {
                return false;
            }
        }
        return true;
    }

    public boolean containsProtocol(String protocol) {
        boolean contains;
        synchronized (this) {
            if (this.mProtocols == null) {
                contains = false;
            } else {
                contains = this.mProtocols.contains(protocol);
            }
        }
        return contains;
    }

    public void addExtension(WebSocketExtension extension) {
        if (extension != null) {
            synchronized (this) {
                if (this.mExtensions == null) {
                    this.mExtensions = new ArrayList();
                }
                this.mExtensions.add(extension);
            }
        }
    }

    public void addExtension(String extension) {
        addExtension(WebSocketExtension.parse(extension));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeExtension(com.neovisionaries.ws.client.WebSocketExtension r2) {
        /*
            r1 = this;
            if (r2 != 0) goto L_0x0003
        L_0x0002:
            return
        L_0x0003:
            monitor-enter(r1)
            java.util.List<com.neovisionaries.ws.client.WebSocketExtension> r0 = r1.mExtensions     // Catch:{ all -> 0x000a }
            if (r0 != 0) goto L_0x000d
            monitor-exit(r1)     // Catch:{ all -> 0x000a }
            goto L_0x0002
        L_0x000a:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x000a }
            throw r0
        L_0x000d:
            java.util.List<com.neovisionaries.ws.client.WebSocketExtension> r0 = r1.mExtensions     // Catch:{ all -> 0x000a }
            r0.remove(r2)     // Catch:{ all -> 0x000a }
            java.util.List<com.neovisionaries.ws.client.WebSocketExtension> r0 = r1.mExtensions     // Catch:{ all -> 0x000a }
            int r0 = r0.size()     // Catch:{ all -> 0x000a }
            if (r0 != 0) goto L_0x001d
            r0 = 0
            r1.mExtensions = r0     // Catch:{ all -> 0x000a }
        L_0x001d:
            monitor-exit(r1)     // Catch:{ all -> 0x000a }
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: com.neovisionaries.ws.client.HandshakeBuilder.removeExtension(com.neovisionaries.ws.client.WebSocketExtension):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeExtensions(java.lang.String r5) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x0003
        L_0x0002:
            return
        L_0x0003:
            monitor-enter(r4)
            java.util.List<com.neovisionaries.ws.client.WebSocketExtension> r2 = r4.mExtensions     // Catch:{ all -> 0x000a }
            if (r2 != 0) goto L_0x000d
            monitor-exit(r4)     // Catch:{ all -> 0x000a }
            goto L_0x0002
        L_0x000a:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x000a }
            throw r2
        L_0x000d:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x000a }
            r1.<init>()     // Catch:{ all -> 0x000a }
            java.util.List<com.neovisionaries.ws.client.WebSocketExtension> r2 = r4.mExtensions     // Catch:{ all -> 0x000a }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x000a }
        L_0x0018:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x000a }
            if (r3 == 0) goto L_0x0032
            java.lang.Object r0 = r2.next()     // Catch:{ all -> 0x000a }
            com.neovisionaries.ws.client.WebSocketExtension r0 = (com.neovisionaries.ws.client.WebSocketExtension) r0     // Catch:{ all -> 0x000a }
            java.lang.String r3 = r0.getName()     // Catch:{ all -> 0x000a }
            boolean r3 = r3.equals(r5)     // Catch:{ all -> 0x000a }
            if (r3 == 0) goto L_0x0018
            r1.add(r0)     // Catch:{ all -> 0x000a }
            goto L_0x0018
        L_0x0032:
            java.util.Iterator r2 = r1.iterator()     // Catch:{ all -> 0x000a }
        L_0x0036:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x000a }
            if (r3 == 0) goto L_0x0048
            java.lang.Object r0 = r2.next()     // Catch:{ all -> 0x000a }
            com.neovisionaries.ws.client.WebSocketExtension r0 = (com.neovisionaries.ws.client.WebSocketExtension) r0     // Catch:{ all -> 0x000a }
            java.util.List<com.neovisionaries.ws.client.WebSocketExtension> r3 = r4.mExtensions     // Catch:{ all -> 0x000a }
            r3.remove(r0)     // Catch:{ all -> 0x000a }
            goto L_0x0036
        L_0x0048:
            java.util.List<com.neovisionaries.ws.client.WebSocketExtension> r2 = r4.mExtensions     // Catch:{ all -> 0x000a }
            int r2 = r2.size()     // Catch:{ all -> 0x000a }
            if (r2 != 0) goto L_0x0053
            r2 = 0
            r4.mExtensions = r2     // Catch:{ all -> 0x000a }
        L_0x0053:
            monitor-exit(r4)     // Catch:{ all -> 0x000a }
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: com.neovisionaries.ws.client.HandshakeBuilder.removeExtensions(java.lang.String):void");
    }

    public void clearExtensions() {
        synchronized (this) {
            this.mExtensions = null;
        }
    }

    public boolean containsExtension(WebSocketExtension extension) {
        boolean z = false;
        if (extension != null) {
            synchronized (this) {
                if (this.mExtensions != null) {
                    z = this.mExtensions.contains(extension);
                }
            }
        }
        return z;
    }

    public boolean containsExtension(String name) {
        boolean z = false;
        if (name != null) {
            synchronized (this) {
                if (this.mExtensions != null) {
                    Iterator<WebSocketExtension> it = this.mExtensions.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().getName().equals(name)) {
                                z = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return z;
    }

    public void addHeader(String name, String value) {
        if (name != null && name.length() != 0) {
            if (value == null) {
                value = "";
            }
            synchronized (this) {
                if (this.mHeaders == null) {
                    this.mHeaders = new ArrayList();
                }
                this.mHeaders.add(new String[]{name, value});
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeHeaders(java.lang.String r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0008
            int r2 = r5.length()
            if (r2 != 0) goto L_0x0009
        L_0x0008:
            return
        L_0x0009:
            monitor-enter(r4)
            java.util.List<java.lang.String[]> r2 = r4.mHeaders     // Catch:{ all -> 0x0010 }
            if (r2 != 0) goto L_0x0013
            monitor-exit(r4)     // Catch:{ all -> 0x0010 }
            goto L_0x0008
        L_0x0010:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0010 }
            throw r2
        L_0x0013:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0010 }
            r1.<init>()     // Catch:{ all -> 0x0010 }
            java.util.List<java.lang.String[]> r2 = r4.mHeaders     // Catch:{ all -> 0x0010 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x0010 }
        L_0x001e:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0010 }
            if (r3 == 0) goto L_0x0037
            java.lang.Object r0 = r2.next()     // Catch:{ all -> 0x0010 }
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ all -> 0x0010 }
            r3 = 0
            r3 = r0[r3]     // Catch:{ all -> 0x0010 }
            boolean r3 = r3.equals(r5)     // Catch:{ all -> 0x0010 }
            if (r3 == 0) goto L_0x001e
            r1.add(r0)     // Catch:{ all -> 0x0010 }
            goto L_0x001e
        L_0x0037:
            java.util.Iterator r2 = r1.iterator()     // Catch:{ all -> 0x0010 }
        L_0x003b:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0010 }
            if (r3 == 0) goto L_0x004d
            java.lang.Object r0 = r2.next()     // Catch:{ all -> 0x0010 }
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ all -> 0x0010 }
            java.util.List<java.lang.String[]> r3 = r4.mHeaders     // Catch:{ all -> 0x0010 }
            r3.remove(r0)     // Catch:{ all -> 0x0010 }
            goto L_0x003b
        L_0x004d:
            java.util.List<java.lang.String[]> r2 = r4.mHeaders     // Catch:{ all -> 0x0010 }
            int r2 = r2.size()     // Catch:{ all -> 0x0010 }
            if (r2 != 0) goto L_0x0058
            r2 = 0
            r4.mHeaders = r2     // Catch:{ all -> 0x0010 }
        L_0x0058:
            monitor-exit(r4)     // Catch:{ all -> 0x0010 }
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.neovisionaries.ws.client.HandshakeBuilder.removeHeaders(java.lang.String):void");
    }

    public void clearHeaders() {
        synchronized (this) {
            this.mHeaders = null;
        }
    }

    public void setUserInfo(String userInfo) {
        synchronized (this) {
            this.mUserInfo = userInfo;
        }
    }

    public void setUserInfo(String id, String password) {
        if (id == null) {
            id = "";
        }
        if (password == null) {
            password = "";
        }
        setUserInfo(String.format("%s:%s", new Object[]{id, password}));
    }

    public void clearUserInfo() {
        synchronized (this) {
            this.mUserInfo = null;
        }
    }

    public URI getURI() {
        return this.mUri;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String buildRequestLine() {
        return String.format("GET %s HTTP/1.1", new Object[]{this.mPath});
    }

    public List<String[]> buildHeaders() {
        List<String[]> headers = new ArrayList<>();
        headers.add(new String[]{"Host", this.mHost});
        headers.add(CONNECTION_HEADER);
        headers.add(UPGRADE_HEADER);
        headers.add(VERSION_HEADER);
        headers.add(new String[]{"Sec-WebSocket-Key", this.mKey});
        if (!(this.mProtocols == null || this.mProtocols.size() == 0)) {
            headers.add(new String[]{"Sec-WebSocket-Protocol", Misc.join(this.mProtocols, ", ")});
        }
        if (!(this.mExtensions == null || this.mExtensions.size() == 0)) {
            headers.add(new String[]{"Sec-WebSocket-Extensions", Misc.join(this.mExtensions, ", ")});
        }
        if (!(this.mUserInfo == null || this.mUserInfo.length() == 0)) {
            headers.add(new String[]{"Authorization", "Basic " + Base64.encode(this.mUserInfo)});
        }
        if (!(this.mHeaders == null || this.mHeaders.size() == 0)) {
            headers.addAll(this.mHeaders);
        }
        return headers;
    }

    public static String build(String requestLine, List<String[]> headers) {
        StringBuilder builder = new StringBuilder();
        builder.append(requestLine).append(RN);
        for (String[] header : headers) {
            builder.append(header[0]).append(": ").append(header[1]).append(RN);
        }
        builder.append(RN);
        return builder.toString();
    }

    private static Set<String> copyProtocols(Set<String> protocols) {
        if (protocols == null) {
            return null;
        }
        Set<String> newProtocols = new LinkedHashSet<>(protocols.size());
        newProtocols.addAll(protocols);
        return newProtocols;
    }

    private static List<WebSocketExtension> copyExtensions(List<WebSocketExtension> extensions) {
        if (extensions == null) {
            return null;
        }
        List<WebSocketExtension> newExtensions = new ArrayList<>(extensions.size());
        for (WebSocketExtension extension : extensions) {
            newExtensions.add(new WebSocketExtension(extension));
        }
        return newExtensions;
    }

    private static List<String[]> copyHeaders(List<String[]> headers) {
        if (headers == null) {
            return null;
        }
        List<String[]> newHeaders = new ArrayList<>(headers.size());
        for (String[] header : headers) {
            newHeaders.add(copyHeader(header));
        }
        return newHeaders;
    }

    private static String[] copyHeader(String[] header) {
        return new String[]{header[0], header[1]};
    }
}
