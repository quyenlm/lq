package com.amazonaws.services.s3.internal.crypto;

import javax.crypto.SecretKey;

public class EncryptedUploadContext extends MultipartUploadContext {
    private final SecretKey envelopeEncryptionKey;
    private byte[] firstIV;
    private byte[] nextIV;

    public EncryptedUploadContext(String bucketName, String key, SecretKey envelopeEncryptionKey2) {
        super(bucketName, key);
        this.envelopeEncryptionKey = envelopeEncryptionKey2;
    }

    public SecretKey getEnvelopeEncryptionKey() {
        return this.envelopeEncryptionKey;
    }

    public void setNextInitializationVector(byte[] nextIV2) {
        this.nextIV = nextIV2;
    }

    public byte[] getNextInitializationVector() {
        return this.nextIV;
    }

    public void setFirstInitializationVector(byte[] firstIV2) {
        this.firstIV = firstIV2;
    }

    public byte[] getFirstInitializationVector() {
        return this.firstIV;
    }
}
