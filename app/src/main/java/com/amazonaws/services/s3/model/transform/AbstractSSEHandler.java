package com.amazonaws.services.s3.model.transform;

import com.amazonaws.services.s3.internal.ServerSideEncryptionResult;

abstract class AbstractSSEHandler extends AbstractHandler implements ServerSideEncryptionResult {
    /* access modifiers changed from: protected */
    public abstract ServerSideEncryptionResult sseResult();

    AbstractSSEHandler() {
    }

    public final String getSSEAlgorithm() {
        ServerSideEncryptionResult result = sseResult();
        if (result == null) {
            return null;
        }
        return result.getSSEAlgorithm();
    }

    public final void setSSEAlgorithm(String serverSideEncryption) {
        ServerSideEncryptionResult result = sseResult();
        if (result != null) {
            result.setSSEAlgorithm(serverSideEncryption);
        }
    }

    public void setSSEKMSKeyId(String kmsKeyId) {
        ServerSideEncryptionResult result = sseResult();
        if (result != null) {
            result.setSSEKMSKeyId(kmsKeyId);
        }
    }

    public String getSSEKMSKeyId() {
        ServerSideEncryptionResult result = sseResult();
        if (result == null) {
            return null;
        }
        return result.getSSEKMSKeyId();
    }

    public final String getSSECustomerAlgorithm() {
        ServerSideEncryptionResult result = sseResult();
        if (result == null) {
            return null;
        }
        return result.getSSECustomerAlgorithm();
    }

    public final void setSSECustomerAlgorithm(String algorithm) {
        ServerSideEncryptionResult result = sseResult();
        if (result != null) {
            result.setSSECustomerAlgorithm(algorithm);
        }
    }

    public final String getSSECustomerKeyMd5() {
        ServerSideEncryptionResult result = sseResult();
        if (result == null) {
            return null;
        }
        return result.getSSECustomerKeyMd5();
    }

    public final void setSSECustomerKeyMd5(String md5Digest) {
        ServerSideEncryptionResult result = sseResult();
        if (result != null) {
            result.setSSECustomerKeyMd5(md5Digest);
        }
    }
}
