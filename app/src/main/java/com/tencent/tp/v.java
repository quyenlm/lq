package com.tencent.tp;

import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class v {
    private static String a(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(str.getBytes(), 0, str.length());
        return b(messageDigest.digest());
    }

    public static String a(byte[] bArr) {
        int i = 0;
        try {
            String obj = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey().toString();
            String str = null;
            if (obj.contains("modulus=")) {
                Matcher matcher = Pattern.compile("\\{[^,]+").matcher(obj);
                while (matcher.find()) {
                    String[] split = matcher.group().split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                    int i2 = 0;
                    while (true) {
                        if (i2 >= split.length) {
                            break;
                        } else if (split[i2].length() > 100) {
                            str = split[i2];
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            } else if (obj.contains("modulus:")) {
                String[] split2 = obj.split(" ");
                while (true) {
                    if (i >= split2.length) {
                        break;
                    } else if (split2[i].length() > 100) {
                        str = split2[i];
                        break;
                    } else {
                        i++;
                    }
                }
            }
            if (str == null) {
                str = obj;
            }
            return a(str.trim().toLowerCase(Locale.US));
        } catch (Throwable th) {
            th.printStackTrace();
            return "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE";
        }
    }

    private static String b(byte[] bArr) {
        String str = "";
        for (int i = 0; i < bArr.length; i++) {
            str = str + Integer.toString((bArr[i] & 255) + 256, 16).substring(1);
        }
        return str.toUpperCase(Locale.US);
    }
}
