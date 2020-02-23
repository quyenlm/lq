package com.amazonaws.services.s3.internal;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.ServerSideEncryptionResult;

public class ServerSideEncryptionHeaderHandler<T extends ServerSideEncryptionResult> implements HeaderHandler<T> {
    public void handle(T result, HttpResponse response) {
        result.setSSEAlgorithm(response.getHeaders().get(Headers.SERVER_SIDE_ENCRYPTION));
        result.setSSECustomerAlgorithm(response.getHeaders().get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM));
        result.setSSECustomerKeyMd5(response.getHeaders().get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5));
        result.setSSEKMSKeyId(response.getHeaders().get(Headers.SERVER_SIDE_ENCRYPTION_KMS_KEY_ID));
    }
}
