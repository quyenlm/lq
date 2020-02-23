package org.apache.http.conn;

import java.net.ConnectException;
import org.apache.http.HttpHost;

@Deprecated
public class HttpHostConnectException extends ConnectException {
    public HttpHostConnectException(HttpHost host, ConnectException cause) {
        throw new RuntimeException("Stub!");
    }

    public HttpHost getHost() {
        throw new RuntimeException("Stub!");
    }
}
