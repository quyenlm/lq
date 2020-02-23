package android.net.http;

import android.content.Context;
import org.apache.http.HttpHost;

abstract class Connection {
    protected SslCertificate mCertificate;
    protected AndroidHttpClientConnection mHttpClientConnection;

    protected Connection(Context context, HttpHost host, RequestFeeder requestFeeder) {
        throw new RuntimeException("Stub!");
    }

    public synchronized String toString() {
        throw new RuntimeException("Stub!");
    }
}
