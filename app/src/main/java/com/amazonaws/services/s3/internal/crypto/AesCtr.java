package com.amazonaws.services.s3.internal.crypto;

class AesCtr extends ContentCryptoScheme {
    AesCtr() {
    }

    /* access modifiers changed from: package-private */
    public String getKeyGeneratorAlgorithm() {
        return AES_GCM.getKeyGeneratorAlgorithm();
    }

    /* access modifiers changed from: package-private */
    public String getCipherAlgorithm() {
        return "AES/CTR/NoPadding";
    }

    /* access modifiers changed from: package-private */
    public int getKeyLengthInBits() {
        return AES_GCM.getKeyLengthInBits();
    }

    /* access modifiers changed from: package-private */
    public int getBlockSizeInBytes() {
        return AES_GCM.getBlockSizeInBytes();
    }

    /* access modifiers changed from: package-private */
    public int getIVLengthInBytes() {
        return 16;
    }

    /* access modifiers changed from: package-private */
    public long getMaxPlaintextSize() {
        return -1;
    }

    /* access modifiers changed from: package-private */
    public byte[] adjustIV(byte[] iv, long byteOffset) {
        if (iv.length != 12) {
            throw new UnsupportedOperationException();
        }
        int blockSize = getBlockSizeInBytes();
        long blockOffset = byteOffset / ((long) blockSize);
        if (((long) blockSize) * blockOffset == byteOffset) {
            return incrementBlocks(computeJ0(iv), blockOffset);
        }
        throw new IllegalArgumentException("Expecting byteOffset to be multiple of 16, but got blockOffset=" + blockOffset + ", blockSize=" + blockSize + ", byteOffset=" + byteOffset);
    }

    private byte[] computeJ0(byte[] nonce) {
        int blockSize = getBlockSizeInBytes();
        byte[] J0 = new byte[blockSize];
        System.arraycopy(nonce, 0, J0, 0, nonce.length);
        J0[blockSize - 1] = 1;
        return incrementBlocks(J0, 1);
    }
}
