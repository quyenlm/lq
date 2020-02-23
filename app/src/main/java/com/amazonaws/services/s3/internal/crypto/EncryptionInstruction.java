package com.amazonaws.services.s3.internal.crypto;

import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class EncryptionInstruction {
    private final byte[] encryptedSymmetricKey;
    private final Map<String, String> materialsDescription;
    private final Cipher symmetricCipher;
    private final CipherFactory symmetricCipherFactory;

    public EncryptionInstruction(Map<String, String> materialsDescription2, byte[] encryptedSymmetricKey2, SecretKey symmetricKey, Cipher symmetricCipher2) {
        this.materialsDescription = materialsDescription2;
        this.encryptedSymmetricKey = encryptedSymmetricKey2;
        this.symmetricCipher = symmetricCipher2;
        this.symmetricCipherFactory = null;
    }

    public EncryptionInstruction(Map<String, String> materialsDescription2, byte[] encryptedSymmetricKey2, SecretKey symmetricKey, CipherFactory symmetricCipherFactory2) {
        this.materialsDescription = materialsDescription2;
        this.encryptedSymmetricKey = encryptedSymmetricKey2;
        this.symmetricCipherFactory = symmetricCipherFactory2;
        this.symmetricCipher = symmetricCipherFactory2.createCipher();
    }

    public CipherFactory getCipherFactory() {
        return this.symmetricCipherFactory;
    }

    public Map<String, String> getMaterialsDescription() {
        return this.materialsDescription;
    }

    public byte[] getEncryptedSymmetricKey() {
        return this.encryptedSymmetricKey;
    }

    public Cipher getSymmetricCipher() {
        return this.symmetricCipher;
    }
}
