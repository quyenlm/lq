package com.amazonaws.services.s3.internal.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

class AesGcm extends ContentCryptoScheme {
    AesGcm() {
    }

    /* access modifiers changed from: package-private */
    public String getKeyGeneratorAlgorithm() {
        return "AES";
    }

    /* access modifiers changed from: package-private */
    public String getCipherAlgorithm() {
        return "AES/GCM/NoPadding";
    }

    /* access modifiers changed from: package-private */
    public int getKeyLengthInBits() {
        return 256;
    }

    /* access modifiers changed from: package-private */
    public int getBlockSizeInBytes() {
        return 16;
    }

    /* access modifiers changed from: package-private */
    public int getIVLengthInBytes() {
        return 12;
    }

    /* access modifiers changed from: package-private */
    public long getMaxPlaintextSize() {
        return 68719476704L;
    }

    /* access modifiers changed from: package-private */
    public int getTagLengthInBits() {
        return 128;
    }

    /* access modifiers changed from: package-private */
    public String getSpecificCipherProvider() {
        return "BC";
    }

    /* access modifiers changed from: package-private */
    public CipherLite createAuxillaryCipher(SecretKey cek, byte[] ivOrig, int cipherMode, Provider securityProvider, long startingBytePos) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        return AES_CTR.createCipherLite(cek, AES_CTR.adjustIV(ivOrig, startingBytePos), cipherMode, securityProvider);
    }

    /* access modifiers changed from: protected */
    public CipherLite newCipherLite(Cipher cipher, SecretKey cek, int cipherMode) {
        return new GCMCipherLite(cipher, cek, cipherMode);
    }
}
