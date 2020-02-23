package android.net.http;

import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

public class DelegatingSSLSession implements SSLSession {

    public static class CertificateWrap extends DelegatingSSLSession {
        public CertificateWrap(Certificate certificate) {
            throw new RuntimeException("Stub!");
        }

        public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
            throw new RuntimeException("Stub!");
        }
    }

    protected DelegatingSSLSession() {
        throw new RuntimeException("Stub!");
    }

    public int getApplicationBufferSize() {
        throw new RuntimeException("Stub!");
    }

    public String getCipherSuite() {
        throw new RuntimeException("Stub!");
    }

    public long getCreationTime() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getId() {
        throw new RuntimeException("Stub!");
    }

    public long getLastAccessedTime() {
        throw new RuntimeException("Stub!");
    }

    public Certificate[] getLocalCertificates() {
        throw new RuntimeException("Stub!");
    }

    public Principal getLocalPrincipal() {
        throw new RuntimeException("Stub!");
    }

    public int getPacketBufferSize() {
        throw new RuntimeException("Stub!");
    }

    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        throw new RuntimeException("Stub!");
    }

    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        throw new RuntimeException("Stub!");
    }

    public String getPeerHost() {
        throw new RuntimeException("Stub!");
    }

    public int getPeerPort() {
        throw new RuntimeException("Stub!");
    }

    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        throw new RuntimeException("Stub!");
    }

    public String getProtocol() {
        throw new RuntimeException("Stub!");
    }

    public SSLSessionContext getSessionContext() {
        throw new RuntimeException("Stub!");
    }

    public Object getValue(String name) {
        throw new RuntimeException("Stub!");
    }

    public String[] getValueNames() {
        throw new RuntimeException("Stub!");
    }

    public void invalidate() {
        throw new RuntimeException("Stub!");
    }

    public boolean isValid() {
        throw new RuntimeException("Stub!");
    }

    public void putValue(String name, Object value) {
        throw new RuntimeException("Stub!");
    }

    public void removeValue(String name) {
        throw new RuntimeException("Stub!");
    }
}
