package com.amazonaws.services.s3.internal.crypto;

import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class CipherFactory {
    private final int cipherMode;
    private final Provider cryptoProvider;
    private byte[] initVectorBytes;
    private final SecretKey symmetricKey;

    public CipherFactory(SecretKey symmetricKey2, int cipherMode2, byte[] initVectorBytes2, Provider cryptoProvider2) {
        this.symmetricKey = symmetricKey2;
        this.cipherMode = cipherMode2;
        this.initVectorBytes = initVectorBytes2;
        this.cryptoProvider = cryptoProvider2;
    }

    public Cipher createCipher() {
        Cipher cipher = EncryptionUtils.createSymmetricCipher(this.symmetricKey, this.cipherMode, this.cryptoProvider, this.initVectorBytes);
        if (this.initVectorBytes == null) {
            this.initVectorBytes = cipher.getIV();
        }
        return cipher;
    }

    public Provider getCryptoProvider() {
        return this.cryptoProvider;
    }

    public int getCipherMode() {
        return this.cipherMode;
    }

    public byte[] getIV() {
        if (this.initVectorBytes == null) {
            return null;
        }
        return (byte[]) this.initVectorBytes.clone();
    }
}
