package com.tencent.imsdk.tool.net;

import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class MySSLSocketFactory extends SSLSocketFactory {
    SSLContext sslContext = SSLContext.getInstance("TLS");

    public MySSLSocketFactory(KeyStore trustStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException, NoSuchProviderException {
        super(trustStore);
        X509TrustManager localCertTM = null;
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);
        TrustManager[] tms = tmf.getTrustManagers();
        int i = 0;
        while (true) {
            if (i >= tms.length) {
                break;
            } else if (tms[i] instanceof X509TrustManager) {
                localCertTM = (X509TrustManager) tms[i];
                break;
            } else {
                i++;
            }
        }
        final X509TrustManager localCertTMDelegate = localCertTM;
        X509TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                if (localCertTMDelegate != null) {
                    localCertTMDelegate.checkClientTrusted(chain, authType);
                }
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                if (localCertTMDelegate != null) {
                    localCertTMDelegate.checkServerTrusted(chain, authType);
                }
            }

            public X509Certificate[] getAcceptedIssuers() {
                if (localCertTMDelegate != null) {
                    return localCertTMDelegate.getAcceptedIssuers();
                }
                return null;
            }
        };
        this.sslContext.init((KeyManager[]) null, new TrustManager[]{tm}, (SecureRandom) null);
        setHostnameVerifier(STRICT_HOSTNAME_VERIFIER);
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return this.sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    public Socket createSocket() throws IOException {
        return this.sslContext.getSocketFactory().createSocket();
    }

    public void fixHttpsURLConnection() {
        HttpsURLConnection.setDefaultSSLSocketFactory(this.sslContext.getSocketFactory());
    }

    public static KeyStore getKeyStore(String fileName) {
        X509Certificate cert;
        InputStream inputStream = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            inputStream = IMConfig.getCertificateFile(fileName);
            cert = (X509Certificate) cf.generateCertificate(inputStream);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    IMLogger.w(e.getMessage());
                }
            }
        } catch (CertificateException e2) {
            cert = null;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    IMLogger.w(e3.getMessage());
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    IMLogger.w(e4.getMessage());
                }
            }
            throw th;
        }
        KeyStore keyStore = null;
        if (cert == null) {
            return null;
        }
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load((InputStream) null, (char[]) null);
            keyStore.setCertificateEntry("ca", cert);
            return keyStore;
        } catch (Exception e5) {
            IMLogger.w(e5.getMessage());
            return keyStore;
        }
    }

    public static KeyStore getKeystore() {
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load((InputStream) null, (char[]) null);
            return trustStore;
        } catch (Throwable t) {
            t.printStackTrace();
            return trustStore;
        }
    }

    public static SSLSocketFactory getFixedSocketFactory(String fileName) {
        SSLSocketFactory socketFactory;
        try {
            socketFactory = new MySSLSocketFactory(getKeyStore(fileName));
        } catch (Throwable t) {
            t.printStackTrace();
            socketFactory = SSLSocketFactory.getSocketFactory();
        }
        socketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        return socketFactory;
    }
}
