package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.AmazonClientException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

abstract class ContentCryptoScheme {
    static final ContentCryptoScheme AES_CBC = new AesCbc();
    static final ContentCryptoScheme AES_CTR = new AesCtr();
    static final ContentCryptoScheme AES_GCM = new AesGcm();
    static final long MAX_CBC_BYTES = 4503599627370496L;
    static final long MAX_CTR_BYTES = -1;
    static final long MAX_GCM_BLOCKS = 4294967294L;
    static final long MAX_GCM_BYTES = 68719476704L;

    /* access modifiers changed from: package-private */
    public abstract int getBlockSizeInBytes();

    /* access modifiers changed from: package-private */
    public abstract String getCipherAlgorithm();

    /* access modifiers changed from: package-private */
    public abstract int getIVLengthInBytes();

    /* access modifiers changed from: package-private */
    public abstract String getKeyGeneratorAlgorithm();

    /* access modifiers changed from: package-private */
    public abstract int getKeyLengthInBits();

    /* access modifiers changed from: package-private */
    public abstract long getMaxPlaintextSize();

    ContentCryptoScheme() {
    }

    /* access modifiers changed from: package-private */
    public String getSpecificCipherProvider() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getTagLengthInBits() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public byte[] adjustIV(byte[] iv, long startingBytePos) {
        return iv;
    }

    public String toString() {
        return "cipherAlgo=" + getCipherAlgorithm() + ", blockSizeInBytes=" + getBlockSizeInBytes() + ", ivLengthInBytes=" + getIVLengthInBytes() + ", keyGenAlgo=" + getKeyGeneratorAlgorithm() + ", keyLengthInBits=" + getKeyLengthInBits() + ", specificProvider=" + getSpecificCipherProvider() + ", tagLengthInBits=" + getTagLengthInBits();
    }

    static byte[] incrementBlocks(byte[] counter, long blockDelta) {
        if (blockDelta != 0) {
            if (counter == null || counter.length != 16) {
                throw new IllegalArgumentException();
            } else if (blockDelta > MAX_GCM_BLOCKS) {
                throw new IllegalStateException();
            } else {
                ByteBuffer bb = ByteBuffer.allocate(8);
                for (int i = 12; i <= 15; i++) {
                    bb.put(i - 8, counter[i]);
                }
                long val = bb.getLong() + blockDelta;
                if (val > MAX_GCM_BLOCKS) {
                    throw new IllegalStateException();
                }
                bb.rewind();
                byte[] result = bb.putLong(val).array();
                for (int i2 = 12; i2 <= 15; i2++) {
                    counter[i2] = result[i2 - 8];
                }
            }
        }
        return counter;
    }

    static ContentCryptoScheme fromCEKAlgo(String cekAlgo) {
        return fromCEKAlgo(cekAlgo, false);
    }

    static ContentCryptoScheme fromCEKAlgo(String cekAlgo, boolean isRangeGet) {
        if (AES_GCM.getCipherAlgorithm().equals(cekAlgo)) {
            return isRangeGet ? AES_CTR : AES_GCM;
        }
        if (cekAlgo == null || AES_CBC.getCipherAlgorithm().equals(cekAlgo)) {
            return AES_CBC;
        }
        throw new UnsupportedOperationException("Unsupported content encryption scheme: " + cekAlgo);
    }

    /* access modifiers changed from: package-private */
    public CipherLite createCipherLite(SecretKey cek, byte[] iv, int cipherMode, Provider securityProvider) {
        Cipher cipher;
        Exception e;
        String specificProvider = getSpecificCipherProvider();
        if (specificProvider != null) {
            try {
                cipher = Cipher.getInstance(getCipherAlgorithm(), specificProvider);
            } catch (Exception e2) {
                if (e2 instanceof RuntimeException) {
                    e = (RuntimeException) e2;
                } else {
                    e = new AmazonClientException("Unable to build cipher: " + e2.getMessage() + "\nMake sure you have the JCE unlimited strength policy files installed and configured for your JVM", e2);
                }
                throw e;
            }
        } else if (securityProvider != null) {
            cipher = Cipher.getInstance(getCipherAlgorithm(), securityProvider);
        } else {
            cipher = Cipher.getInstance(getCipherAlgorithm());
        }
        cipher.init(cipherMode, cek, new IvParameterSpec(iv));
        return newCipherLite(cipher, cek, cipherMode);
    }

    /* access modifiers changed from: protected */
    public CipherLite newCipherLite(Cipher cipher, SecretKey cek, int cipherMode) {
        return new CipherLite(cipher, this, cek, cipherMode);
    }

    /* access modifiers changed from: package-private */
    public CipherLite createAuxillaryCipher(SecretKey cek, byte[] iv, int cipherMode, Provider securityProvider, long startingBytePos) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        return null;
    }

    /* access modifiers changed from: package-private */
    public CipherLite createCipherLite(SecretKey cek, byte[] iv, int cipherMode) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        return createCipherLite(cek, iv, cipherMode, (Provider) null);
    }
}
