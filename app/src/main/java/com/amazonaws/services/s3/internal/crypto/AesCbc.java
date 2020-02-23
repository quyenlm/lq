package com.amazonaws.services.s3.internal.crypto;

class AesCbc extends ContentCryptoScheme {
    AesCbc() {
    }

    /* access modifiers changed from: package-private */
    public String getKeyGeneratorAlgorithm() {
        return "AES";
    }

    /* access modifiers changed from: package-private */
    public String getCipherAlgorithm() {
        return "AES/CBC/PKCS5Padding";
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
        return 16;
    }

    /* access modifiers changed from: package-private */
    public long getMaxPlaintextSize() {
        return 4503599627370496L;
    }
}
