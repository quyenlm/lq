package org.apache.http.message;

import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;

@Deprecated
public class BasicRequestLine implements RequestLine {
    public BasicRequestLine(String method, String uri, ProtocolVersion version) {
        throw new RuntimeException("Stub!");
    }

    public String getMethod() {
        throw new RuntimeException("Stub!");
    }

    public ProtocolVersion getProtocolVersion() {
        throw new RuntimeException("Stub!");
    }

    public String getUri() {
        throw new RuntimeException("Stub!");
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }

    public Object clone() throws CloneNotSupportedException {
        throw new RuntimeException("Stub!");
    }
}
