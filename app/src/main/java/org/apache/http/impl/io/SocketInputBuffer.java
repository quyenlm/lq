package org.apache.http.impl.io;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.params.HttpParams;

@Deprecated
public class SocketInputBuffer extends AbstractSessionInputBuffer {
    public SocketInputBuffer(Socket socket, int buffersize, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isDataAvailable(int timeout) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isStale() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
