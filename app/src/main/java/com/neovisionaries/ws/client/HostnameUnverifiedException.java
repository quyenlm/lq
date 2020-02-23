package com.neovisionaries.ws.client;

import javax.net.ssl.SSLSocket;

public class HostnameUnverifiedException extends WebSocketException {
    private static final long serialVersionUID = 1;
    private final String mHostname;
    private final SSLSocket mSSLSocket;

    public HostnameUnverifiedException(SSLSocket socket, String hostname) {
        super(WebSocketError.HOSTNAME_UNVERIFIED, String.format("The certificate of the peer%s does not match the expected hostname (%s)", new Object[]{stringifyPrincipal(socket), hostname}));
        this.mSSLSocket = socket;
        this.mHostname = hostname;
    }

    private static String stringifyPrincipal(SSLSocket socket) {
        try {
            return String.format(" (%s)", new Object[]{socket.getSession().getPeerPrincipal().toString()});
        } catch (Exception e) {
            return "";
        }
    }

    public SSLSocket getSSLSocket() {
        return this.mSSLSocket;
    }

    public String getHostname() {
        return this.mHostname;
    }
}
