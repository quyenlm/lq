package org.apache.http.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpParams;

@Deprecated
public final class MultihomePlainSocketFactory implements SocketFactory {
    MultihomePlainSocketFactory() {
        throw new RuntimeException("Stub!");
    }

    public static MultihomePlainSocketFactory getSocketFactory() {
        throw new RuntimeException("Stub!");
    }

    public Socket createSocket() {
        throw new RuntimeException("Stub!");
    }

    public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public final boolean isSecure(Socket sock) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
}
