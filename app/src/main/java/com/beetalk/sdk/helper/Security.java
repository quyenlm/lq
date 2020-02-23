package com.beetalk.sdk.helper;

import com.garena.network.AsyncNetworkRequest;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Security {
    private static final int BLOCK_SIZE = 4096;
    public static final String HMACSHA_256 = "HMACSHA256";
    public static final String SHA256_WITH_RSA = "SHA256withRSA";
    private static final char[] symbols;

    public static byte[] generateSHA256(String baseString) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(baseString.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ch = (char) (ch + 1)) {
            tmp.append(ch);
        }
        for (char ch2 = 'a'; ch2 <= 'z'; ch2 = (char) (ch2 + 1)) {
            tmp.append(ch2);
        }
        for (int i = 0; i < "!@#$%^&*()".length(); i++) {
            tmp.append("!@#$%^&*()".charAt(i));
        }
        symbols = tmp.toString().toCharArray();
    }

    public static String generateRandomString(int length) {
        char[] buf = new char[length];
        Random random = new Random();
        for (int index = 0; index < length; index++) {
            buf[index] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

    public static String generate64charpassword() {
        return HexUtils.printHexBinary(generateSHA256(generateRandomString(64)));
    }

    public static String getHTTPRequestSignature(String key, AsyncNetworkRequest request) {
        String body = null;
        try {
            body = generateHTTPRequestSignatureBody(request.getRequestMethod(), request.getUri().toString(), request.getQuery());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = HMAC256Digest(key, body);
        BBLogger.d("HMAC %s", result);
        return result;
    }

    public static String generateHTTPRequestSignatureBody(String verb, String URL, String paramBody) {
        return verb + "|" + URL + "|" + paramBody;
    }

    public static String generateHTTPRequestSignatureBody(String verb, String URL, Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        builder.append(verb).append("|").append(URL).append("|");
        int i = 0;
        for (String key : params.keySet()) {
            builder.append(key).append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(params.get(key));
            i++;
            if (i < params.keySet().size()) {
                builder.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
        }
        String signatureBody = builder.toString();
        BBLogger.d("Signature Body: %s", signatureBody);
        return signatureBody;
    }

    public static String HMAC256Digest(String key, String message) {
        return HMACDigest(message, key, "HMACSHA256");
    }

    private static String HMACDigest(String msg, String keyString, String algo) {
        try {
            SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);
            byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));
            StringBuilder hash = new StringBuilder();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(aByte & 255);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            return hash.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static String generateMd5(String content) {
        try {
            return HexUtils.printHexBinary(MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            BBLogger.e(e);
        } catch (UnsupportedEncodingException e2) {
            BBLogger.e(e2);
        }
        return "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0053 A[SYNTHETIC, Splitter:B:30:0x0053] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean verifyFile(java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.String r13) {
        /*
            r7 = 0
            java.io.File r2 = new java.io.File
            r2.<init>(r10)
            boolean r9 = r2.exists()
            if (r9 == 0) goto L_0x0037
            r3 = 0
            java.security.PublicKey r5 = com.garena.pay.android.inappbilling.Security.generatePublicKey(r11)     // Catch:{ Exception -> 0x005f }
            java.security.Signature r8 = java.security.Signature.getInstance(r13)     // Catch:{ Exception -> 0x005f }
            r8.initVerify(r5)     // Catch:{ Exception -> 0x005f }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x005f }
            r4.<init>(r10)     // Catch:{ Exception -> 0x005f }
            r9 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r9]     // Catch:{ Exception -> 0x002d, all -> 0x005c }
        L_0x0021:
            int r6 = r4.read(r0)     // Catch:{ Exception -> 0x002d, all -> 0x005c }
            r9 = -1
            if (r6 == r9) goto L_0x0038
            r9 = 0
            r8.update(r0, r9, r6)     // Catch:{ Exception -> 0x002d, all -> 0x005c }
            goto L_0x0021
        L_0x002d:
            r1 = move-exception
            r3 = r4
        L_0x002f:
            com.beetalk.sdk.helper.BBLogger.e(r1)     // Catch:{ all -> 0x0050 }
            if (r3 == 0) goto L_0x0037
            r3.close()     // Catch:{ IOException -> 0x004b }
        L_0x0037:
            return r7
        L_0x0038:
            byte[] r9 = com.beetalk.sdk.helper.HexUtils.parseHexBinary(r12)     // Catch:{ Exception -> 0x002d, all -> 0x005c }
            boolean r7 = r8.verify(r9)     // Catch:{ Exception -> 0x002d, all -> 0x005c }
            if (r4 == 0) goto L_0x0037
            r4.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x0037
        L_0x0046:
            r1 = move-exception
            com.beetalk.sdk.helper.BBLogger.e(r1)
            goto L_0x0037
        L_0x004b:
            r1 = move-exception
            com.beetalk.sdk.helper.BBLogger.e(r1)
            goto L_0x0037
        L_0x0050:
            r9 = move-exception
        L_0x0051:
            if (r3 == 0) goto L_0x0056
            r3.close()     // Catch:{ IOException -> 0x0057 }
        L_0x0056:
            throw r9
        L_0x0057:
            r1 = move-exception
            com.beetalk.sdk.helper.BBLogger.e(r1)
            goto L_0x0056
        L_0x005c:
            r9 = move-exception
            r3 = r4
            goto L_0x0051
        L_0x005f:
            r1 = move-exception
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beetalk.sdk.helper.Security.verifyFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String):boolean");
    }
}
