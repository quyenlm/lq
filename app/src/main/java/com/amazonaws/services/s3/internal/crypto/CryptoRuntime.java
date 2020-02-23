package com.amazonaws.services.s3.internal.crypto;

import java.security.Provider;
import java.security.Security;
import javax.crypto.Cipher;
import org.apache.commons.logging.LogFactory;

public class CryptoRuntime {
    private static final String BC_PROVIDER_FQCN = "org.bouncycastle.jce.provider.BouncyCastleProvider";
    static final String BOUNCY_CASTLE_PROVIDER = "BC";

    public static boolean isBouncyCastleAvailable() {
        return Security.getProvider(BOUNCY_CASTLE_PROVIDER) != null;
    }

    public static void enableBouncyCastle() {
        try {
            Security.addProvider((Provider) Class.forName(BC_PROVIDER_FQCN).newInstance());
        } catch (Exception e) {
            LogFactory.getLog(CryptoRuntime.class).debug("Bouncy Castle not available", e);
        }
    }

    static void recheck() {
        recheckAesGcmAvailablility();
        recheckRsaKeyWrapAvailablility();
    }

    public static boolean isAesGcmAvailable() {
        return AesGcm.isAvailable;
    }

    private static void recheckAesGcmAvailablility() {
        AesGcm.recheck();
    }

    static boolean isRsaKeyWrapAvailable() {
        return RsaEcbOaepWithSHA256AndMGF1Padding.isAvailable;
    }

    private static void recheckRsaKeyWrapAvailablility() {
        RsaEcbOaepWithSHA256AndMGF1Padding.recheck();
    }

    private static final class AesGcm {
        static volatile boolean isAvailable = check();

        private AesGcm() {
        }

        static boolean recheck() {
            boolean check = check();
            isAvailable = check;
            return check;
        }

        private static boolean check() {
            try {
                Cipher.getInstance(ContentCryptoScheme.AES_GCM.getCipherAlgorithm(), CryptoRuntime.BOUNCY_CASTLE_PROVIDER);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    private static final class RsaEcbOaepWithSHA256AndMGF1Padding {
        static volatile boolean isAvailable = check();

        private RsaEcbOaepWithSHA256AndMGF1Padding() {
        }

        static boolean recheck() {
            boolean check = check();
            isAvailable = check;
            return check;
        }

        private static boolean check() {
            try {
                Cipher.getInstance(S3KeyWrapScheme.RSA_ECB_OAEPWithSHA256AndMGF1Padding, CryptoRuntime.BOUNCY_CASTLE_PROVIDER);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
