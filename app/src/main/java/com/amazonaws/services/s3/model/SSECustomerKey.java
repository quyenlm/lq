package com.amazonaws.services.s3.model;

import com.amazonaws.util.Base64;
import javax.crypto.SecretKey;

public class SSECustomerKey {
    private String algorithm;
    private final String base64EncodedKey;
    private String base64EncodedMd5;

    public SSECustomerKey(String base64EncodedKey2) {
        if (base64EncodedKey2 == null || base64EncodedKey2.length() == 0) {
            throw new IllegalArgumentException("Encryption key must be specified");
        }
        this.algorithm = ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION;
        this.base64EncodedKey = base64EncodedKey2;
    }

    public SSECustomerKey(byte[] rawKeyMaterial) {
        if (rawKeyMaterial == null || rawKeyMaterial.length == 0) {
            throw new IllegalArgumentException("Encryption key must be specified");
        }
        this.algorithm = ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION;
        this.base64EncodedKey = Base64.encodeAsString(rawKeyMaterial);
    }

    public SSECustomerKey(SecretKey key) {
        if (key == null) {
            throw new IllegalArgumentException("Encryption key must be specified");
        }
        this.algorithm = ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION;
        this.base64EncodedKey = Base64.encodeAsString(key.getEncoded());
    }

    public String getKey() {
        return this.base64EncodedKey;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(String algorithm2) {
        this.algorithm = algorithm2;
    }

    public SSECustomerKey withAlgorithm(String algorithm2) {
        setAlgorithm(algorithm2);
        return this;
    }

    public String getMd5() {
        return this.base64EncodedMd5;
    }

    public void setMd5(String md5Digest) {
        this.base64EncodedMd5 = md5Digest;
    }

    public SSECustomerKey withMd5(String md5Digest) {
        setMd5(md5Digest);
        return this;
    }
}
