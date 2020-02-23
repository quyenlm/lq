package com.tencent.midas.oversea.network.http;

import com.tencent.component.net.download.multiplex.http.HttpHeader;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APLog;
import com.vk.sdk.api.VKApiConst;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public abstract class APHttpsReqPost extends APBaseHttpReq {

    private static class a implements X509TrustManager {
        private X509TrustManager a;

        a() {
            X509Certificate x509Certificate;
            try {
                APAppDataInterface.singleton().getEnv();
                String cert = APNetCfg.getCert();
                if (APNetCfg.mFileCfg) {
                    APMidasPayAPI.singleton();
                    InputStream open = APMidasPayAPI.applicationContext.getAssets().open(cert);
                    x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(open);
                    open.close();
                } else {
                    CertificateFactory instance = CertificateFactory.getInstance("X.509");
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cert.getBytes());
                    x509Certificate = (X509Certificate) instance.generateCertificate(byteArrayInputStream);
                    byteArrayInputStream.close();
                }
                KeyStore.TrustedCertificateEntry trustedCertificateEntry = new KeyStore.TrustedCertificateEntry(x509Certificate);
                KeyStore instance2 = KeyStore.getInstance(KeyStore.getDefaultType());
                instance2.load((InputStream) null, (char[]) null);
                instance2.setEntry("ca_root", trustedCertificateEntry, (KeyStore.ProtectionParameter) null);
                TrustManagerFactory instance3 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                instance3.init(instance2);
                TrustManager[] trustManagers = instance3.getTrustManagers();
                for (int i = 0; i < trustManagers.length; i++) {
                    if (trustManagers[i] instanceof X509TrustManager) {
                        this.a = (X509TrustManager) trustManagers[i];
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
            APLog.i("APBaseHttpReq", "checkServerTrusted");
            this.a.checkServerTrusted(x509CertificateArr, str);
        }

        public X509Certificate[] getAcceptedIssuers() {
            return this.a.getAcceptedIssuers();
        }
    }

    public APHttpsReqPost() {
        this.httpParam.setReqWithHttps();
        this.httpParam.setSendWithPost();
    }

    private void a() {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            if (isIPAddress(this.httpParam.domain)) {
                APLog.i("APHttpsReqPost", "ssl check init");
                instance.init((KeyManager[]) null, new TrustManager[]{new a()}, new SecureRandom());
                HttpsURLConnection.setDefaultHostnameVerifier(new d(this));
            } else {
                APLog.i("APHttpsReqPost", "ssl system check init");
                instance.init((KeyManager[]) null, (TrustManager[]) null, new SecureRandom());
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(instance.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void preCreateConnection() {
        if (this.httpParam.reqType.startsWith(VKApiConst.HTTPS)) {
            a();
        }
        super.preCreateConnection();
    }

    /* access modifiers changed from: protected */
    public void setBody() {
        super.setBody();
    }

    public void setHeader() {
        try {
            this.httpURLConnection.setDoInput(true);
            this.httpURLConnection.setDoOutput(true);
            this.httpURLConnection.setRequestMethod("POST");
            this.httpURLConnection.setRequestProperty(HttpHeader.RSP.CHARSET, "UTF-8");
            this.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        } catch (Exception e) {
        }
    }
}
