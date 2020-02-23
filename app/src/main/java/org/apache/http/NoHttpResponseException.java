package org.apache.http;

import java.io.IOException;

@Deprecated
public class NoHttpResponseException extends IOException {
    public NoHttpResponseException(String message) {
        throw new RuntimeException("Stub!");
    }
}
