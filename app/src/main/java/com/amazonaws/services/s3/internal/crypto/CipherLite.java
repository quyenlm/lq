package com.amazonaws.services.s3.internal.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.NullCipher;
import javax.crypto.SecretKey;

class CipherLite {
    static final CipherLite Null = new CipherLite() {
        /* access modifiers changed from: package-private */
        public CipherLite createAuxiliary(long startingBytePos) {
            return this;
        }

        /* access modifiers changed from: package-private */
        public CipherLite createInverse() {
            return this;
        }
    };
    private final Cipher cipher;
    private final int cipherMode;
    private final ContentCryptoScheme scheme;
    private final SecretKey secreteKey;

    private CipherLite() {
        this.cipher = new NullCipher();
        this.scheme = null;
        this.secreteKey = null;
        this.cipherMode = -1;
    }

    CipherLite(Cipher cipher2, ContentCryptoScheme scheme2, SecretKey secreteKey2, int cipherMode2) {
        this.cipher = cipher2;
        this.scheme = scheme2;
        this.secreteKey = secreteKey2;
        this.cipherMode = cipherMode2;
    }

    /* access modifiers changed from: package-private */
    public CipherLite createAuxiliary(long startingBytePos) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        return this.scheme.createAuxillaryCipher(this.secreteKey, this.cipher.getIV(), this.cipherMode, this.cipher.getProvider(), startingBytePos);
    }

    /* access modifiers changed from: package-private */
    public CipherLite createInverse() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        int inversedMode;
        if (this.cipherMode == 2) {
            inversedMode = 1;
        } else if (this.cipherMode == 1) {
            inversedMode = 2;
        } else {
            throw new UnsupportedOperationException();
        }
        return this.scheme.createCipherLite(this.secreteKey, this.cipher.getIV(), inversedMode, this.cipher.getProvider());
    }

    /* access modifiers changed from: package-private */
    public byte[] doFinal() throws IllegalBlockSizeException, BadPaddingException {
        return this.cipher.doFinal();
    }

    /* access modifiers changed from: package-private */
    public byte[] doFinal(byte[] input) throws IllegalBlockSizeException, BadPaddingException {
        return this.cipher.doFinal(input);
    }

    /* access modifiers changed from: package-private */
    public byte[] doFinal(byte[] input, int inputOffset, int inputLen) throws IllegalBlockSizeException, BadPaddingException {
        return this.cipher.doFinal(input, inputOffset, inputLen);
    }

    /* access modifiers changed from: package-private */
    public byte[] update(byte[] input, int inputOffset, int inputLen) {
        return this.cipher.update(input, inputOffset, inputLen);
    }

    /* access modifiers changed from: package-private */
    public final String getCipherAlgorithm() {
        return this.cipher.getAlgorithm();
    }

    /* access modifiers changed from: package-private */
    public final Provider getCipherProvider() {
        return this.cipher.getProvider();
    }

    /* access modifiers changed from: package-private */
    public final String getSecretKeyAlgorithm() {
        return this.secreteKey.getAlgorithm();
    }

    /* access modifiers changed from: package-private */
    public final Cipher getCipher() {
        return this.cipher;
    }

    /* access modifiers changed from: package-private */
    public final ContentCryptoScheme getContentCryptoScheme() {
        return this.scheme;
    }

    /* access modifiers changed from: package-private */
    public final byte[] getIV() {
        return this.cipher.getIV();
    }

    /* access modifiers changed from: package-private */
    public final int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    /* access modifiers changed from: package-private */
    public final int getCipherMode() {
        return this.cipherMode;
    }

    /* access modifiers changed from: package-private */
    public boolean markSupported() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public long mark() {
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        throw new IllegalStateException("mark/reset not supported");
    }

    /* access modifiers changed from: package-private */
    public int getOutputSize(int inputLen) {
        return this.cipher.getOutputSize(inputLen);
    }
}
