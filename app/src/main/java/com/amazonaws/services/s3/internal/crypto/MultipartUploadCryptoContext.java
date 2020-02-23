package com.amazonaws.services.s3.internal.crypto;

final class MultipartUploadCryptoContext extends MultipartUploadContext {
    private final ContentCryptoMaterial cekMaterial;

    MultipartUploadCryptoContext(String bucketName, String key, ContentCryptoMaterial cekMaterial2) {
        super(bucketName, key);
        this.cekMaterial = cekMaterial2;
    }

    /* access modifiers changed from: package-private */
    public CipherLite getCipherLite() {
        return this.cekMaterial.getCipherLite();
    }

    /* access modifiers changed from: package-private */
    public ContentCryptoMaterial getContentCryptoMaterial() {
        return this.cekMaterial;
    }
}
