package org.apache.http.auth;

import java.security.Principal;

@Deprecated
public class NTCredentials implements Credentials {
    public NTCredentials(String usernamePassword) {
        throw new RuntimeException("Stub!");
    }

    public NTCredentials(String userName, String password, String workstation, String domain) {
        throw new RuntimeException("Stub!");
    }

    public Principal getUserPrincipal() {
        throw new RuntimeException("Stub!");
    }

    public String getUserName() {
        throw new RuntimeException("Stub!");
    }

    public String getPassword() {
        throw new RuntimeException("Stub!");
    }

    public String getDomain() {
        throw new RuntimeException("Stub!");
    }

    public String getWorkstation() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(Object o) {
        throw new RuntimeException("Stub!");
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
