package com.tencent.tp.c;

import android.content.Context;
import android.content.pm.Signature;
import com.tencent.tp.TssSdkRuntime;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class c {
    private X509Certificate a;

    private void d() throws Exception {
        if (this.a == null) {
            e();
        }
    }

    private void e() throws Exception {
        String packageName;
        Context appContext = TssSdkRuntime.getAppContext();
        if (appContext != null && (packageName = TssSdkRuntime.getPackageName()) != null) {
            Signature[] signatureArr = appContext.getPackageManager().getPackageInfo(packageName, 64).signatures;
            if (signatureArr.length == 1) {
                this.a = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(signatureArr[0].toByteArray()));
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0008, code lost:
        r0 = r4.a.getPublicKey();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String a() {
        /*
            r4 = this;
            r1 = 0
            r4.d()     // Catch:{ Throwable -> 0x0028 }
            java.security.cert.X509Certificate r0 = r4.a     // Catch:{ Throwable -> 0x0028 }
            if (r0 == 0) goto L_0x002b
            java.security.cert.X509Certificate r0 = r4.a     // Catch:{ Throwable -> 0x0028 }
            java.security.PublicKey r0 = r0.getPublicKey()     // Catch:{ Throwable -> 0x0028 }
            java.lang.String r2 = r0.getAlgorithm()     // Catch:{ Throwable -> 0x0028 }
            if (r2 == 0) goto L_0x002b
            java.lang.String r3 = "RSA"
            int r2 = r2.indexOf(r3)     // Catch:{ Throwable -> 0x0028 }
            r3 = -1
            if (r2 == r3) goto L_0x002b
            java.security.interfaces.RSAPublicKey r0 = (java.security.interfaces.RSAPublicKey) r0     // Catch:{ Throwable -> 0x0028 }
            java.math.BigInteger r0 = r0.getModulus()     // Catch:{ Throwable -> 0x0028 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0028 }
        L_0x0027:
            return r0
        L_0x0028:
            r0 = move-exception
            r0 = r1
            goto L_0x0027
        L_0x002b:
            r0 = r1
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tp.c.c.a():java.lang.String");
    }

    public String b() {
        try {
            d();
            if (this.a != null) {
                return this.a.getIssuerDN().toString();
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public String c() {
        try {
            d();
            if (this.a != null) {
                return this.a.getSubjectDN().toString();
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }
}
