package org.apache.http.impl.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;

@Deprecated
public class BasicCredentialsProvider implements CredentialsProvider {
    public BasicCredentialsProvider() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setCredentials(AuthScope authscope, Credentials credentials) {
        throw new RuntimeException("Stub!");
    }

    public synchronized Credentials getCredentials(AuthScope authscope) {
        throw new RuntimeException("Stub!");
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void clear() {
        throw new RuntimeException("Stub!");
    }
}
