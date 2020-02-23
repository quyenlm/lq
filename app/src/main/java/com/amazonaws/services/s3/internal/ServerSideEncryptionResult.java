package com.amazonaws.services.s3.internal;

public interface ServerSideEncryptionResult {
    String getSSEAlgorithm();

    String getSSECustomerAlgorithm();

    String getSSECustomerKeyMd5();

    String getSSEKMSKeyId();

    void setSSEAlgorithm(String str);

    void setSSECustomerAlgorithm(String str);

    void setSSECustomerKeyMd5(String str);

    void setSSEKMSKeyId(String str);
}
