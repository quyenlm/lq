package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.impl.SocketHttpClientConnection;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.HttpParams;

@Deprecated
public class DefaultClientConnection extends SocketHttpClientConnection implements OperatedClientConnection {
    public DefaultClientConnection() {
        throw new RuntimeException("Stub!");
    }

    public final HttpHost getTargetHost() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isSecure() {
        throw new RuntimeException("Stub!");
    }

    public final Socket getSocket() {
        throw new RuntimeException("Stub!");
    }

    public void opening(Socket sock, HttpHost target) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void openCompleted(boolean secure, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void shutdown() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public SessionInputBuffer createSessionInputBuffer(Socket socket, int buffersize, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public SessionOutputBuffer createSessionOutputBuffer(Socket socket, int buffersize, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public HttpMessageParser createResponseParser(SessionInputBuffer buffer, HttpResponseFactory responseFactory, HttpParams params) {
        throw new RuntimeException("Stub!");
    }

    public void update(Socket sock, HttpHost target, boolean secure, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }

    public void sendRequestHeader(HttpRequest request) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }
}
