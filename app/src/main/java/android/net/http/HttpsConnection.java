package android.net.http;

import android.content.Context;
import java.io.File;
import org.apache.http.HttpHost;

public class HttpsConnection extends Connection {
    protected SslCertificate mCertificate;
    protected AndroidHttpClientConnection mHttpClientConnection;

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    HttpsConnection() {
        super((Context) null, (HttpHost) null, (RequestFeeder) null);
        throw new RuntimeException("Stub!");
    }

    public static void initializeEngine(File sessionDir) {
        throw new RuntimeException("Stub!");
    }
}
