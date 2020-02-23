package android.net.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.http.HttpConnection;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpRequest;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.params.HttpParams;

public class AndroidHttpClientConnection implements HttpConnection, HttpInetConnection {
    public AndroidHttpClientConnection() {
        throw new RuntimeException("Stub!");
    }

    public void bind(Socket socket, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }

    public boolean isOpen() {
        throw new RuntimeException("Stub!");
    }

    public InetAddress getLocalAddress() {
        throw new RuntimeException("Stub!");
    }

    public int getLocalPort() {
        throw new RuntimeException("Stub!");
    }

    public InetAddress getRemoteAddress() {
        throw new RuntimeException("Stub!");
    }

    public int getRemotePort() {
        throw new RuntimeException("Stub!");
    }

    public void setSocketTimeout(int timeout) {
        throw new RuntimeException("Stub!");
    }

    public int getSocketTimeout() {
        throw new RuntimeException("Stub!");
    }

    public void shutdown() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void sendRequestHeader(HttpRequest request) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }

    public void sendRequestEntity(HttpEntityEnclosingRequest request) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void doFlush() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void flush() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public StatusLine parseResponseHeader(Headers headers) throws IOException, ParseException {
        throw new RuntimeException("Stub!");
    }

    public HttpEntity receiveResponseEntity(Headers headers) {
        throw new RuntimeException("Stub!");
    }

    public boolean isStale() {
        throw new RuntimeException("Stub!");
    }

    public HttpConnectionMetrics getMetrics() {
        throw new RuntimeException("Stub!");
    }
}
