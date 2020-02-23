package com.amazonaws.services.s3.internal.crypto;

import com.tencent.imsdk.IMConfig;
import java.security.Key;

class S3KeyWrapScheme {
    public static final String AESWrap = "AESWrap";
    static final S3KeyWrapScheme NONE = new S3KeyWrapScheme() {
        /* access modifiers changed from: package-private */
        public String getKeyWrapAlgorithm(Key key) {
            return null;
        }

        public String toString() {
            return IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT;
        }
    };
    public static final String RSA_ECB_OAEPWithSHA256AndMGF1Padding = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    S3KeyWrapScheme() {
    }

    /* access modifiers changed from: package-private */
    public String getKeyWrapAlgorithm(Key key) {
        String algorithm = key.getAlgorithm();
        if ("AES".equals(algorithm)) {
            return AESWrap;
        }
        if (!"RSA".equals(algorithm) || !CryptoRuntime.isRsaKeyWrapAvailable()) {
            return null;
        }
        return RSA_ECB_OAEPWithSHA256AndMGF1Padding;
    }
}
