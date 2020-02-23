package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.Request;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.AbstractAWSSigner;
import com.amazonaws.auth.SigningAlgorithm;
import com.amazonaws.services.s3.Headers;
import java.util.Date;

public class S3QueryStringSigner extends AbstractAWSSigner {
    private final Date expiration;
    private final String httpVerb;
    private final String resourcePath;

    public S3QueryStringSigner(String httpVerb2, String resourcePath2, Date expiration2) {
        this.httpVerb = httpVerb2;
        this.resourcePath = resourcePath2;
        this.expiration = expiration2;
        if (resourcePath2 == null) {
            throw new IllegalArgumentException("Parameter resourcePath is empty");
        }
    }

    public void sign(Request<?> request, AWSCredentials credentials) throws AmazonClientException {
        AWSCredentials sanitizedCredentials = sanitizeCredentials(credentials);
        if (sanitizedCredentials instanceof AWSSessionCredentials) {
            addSessionCredentials(request, (AWSSessionCredentials) sanitizedCredentials);
        }
        String expirationInSeconds = Long.toString(this.expiration.getTime() / 1000);
        String signature = super.signAndBase64Encode(RestUtils.makeS3CanonicalString(this.httpVerb, this.resourcePath, request, expirationInSeconds), sanitizedCredentials.getAWSSecretKey(), SigningAlgorithm.HmacSHA1);
        request.addParameter("AWSAccessKeyId", sanitizedCredentials.getAWSAccessKeyId());
        request.addParameter(Headers.EXPIRES, expirationInSeconds);
        request.addParameter("Signature", signature);
    }

    /* access modifiers changed from: protected */
    public void addSessionCredentials(Request<?> request, AWSSessionCredentials credentials) {
        request.addParameter(Headers.SECURITY_TOKEN, credentials.getSessionToken());
    }
}
