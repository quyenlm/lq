package com.amazonaws.services.s3.model;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

public class EncryptionMaterials {
    private final KeyPair keyPair;
    private final SecretKey symmetricKey;

    public EncryptionMaterials(KeyPair keyPair2) {
        this(keyPair2, (SecretKey) null);
    }

    public EncryptionMaterials(SecretKey symmetricKey2) {
        this((KeyPair) null, symmetricKey2);
    }

    protected EncryptionMaterials(KeyPair keyPair2, SecretKey symmetricKey2) {
        this.keyPair = keyPair2;
        this.symmetricKey = symmetricKey2;
    }

    public KeyPair getKeyPair() {
        return this.keyPair;
    }

    public SecretKey getSymmetricKey() {
        return this.symmetricKey;
    }

    public Map<String, String> getMaterialsDescription() {
        return new HashMap();
    }

    public EncryptionMaterialsAccessor getAccessor() {
        return null;
    }
}
