package com.amazonaws.services.s3.internal;

public abstract class SSEResultBase implements ServerSideEncryptionResult {
    private String sseAlgorithm;
    private String sseCustomerAlgorithm;
    private String sseCustomerKeyMD5;
    private String sseKMSKeyId;

    public final String getSSEAlgorithm() {
        return this.sseAlgorithm;
    }

    public final void setSSEAlgorithm(String algorithm) {
        this.sseAlgorithm = algorithm;
    }

    public final String getSSECustomerAlgorithm() {
        return this.sseCustomerAlgorithm;
    }

    public final void setSSECustomerAlgorithm(String algorithm) {
        this.sseCustomerAlgorithm = algorithm;
    }

    public final String getSSECustomerKeyMd5() {
        return this.sseCustomerKeyMD5;
    }

    public final void setSSECustomerKeyMd5(String md5) {
        this.sseCustomerKeyMD5 = md5;
    }

    public void setSSEKMSKeyId(String kmsKeyId) {
        this.sseKMSKeyId = kmsKeyId;
    }

    public String getSSEKMSKeyId() {
        return this.sseKMSKeyId;
    }

    @Deprecated
    public final String getServerSideEncryption() {
        return this.sseAlgorithm;
    }
}
