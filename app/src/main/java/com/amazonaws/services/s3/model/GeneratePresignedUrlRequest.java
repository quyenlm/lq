package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.HttpMethod;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GeneratePresignedUrlRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String contentMd5;
    private String contentType;
    private Date expiration;
    private String key;
    private HttpMethod method;
    private Map<String, String> requestParameters;
    private ResponseHeaderOverrides responseHeaders;
    private SSECustomerKey sseCustomerKey;

    public GeneratePresignedUrlRequest(String bucketName2, String key2) {
        this(bucketName2, key2, HttpMethod.GET);
    }

    public GeneratePresignedUrlRequest(String bucketName2, String key2, HttpMethod method2) {
        this.requestParameters = new HashMap();
        this.bucketName = bucketName2;
        this.key = key2;
        this.method = method2;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setMethod(HttpMethod method2) {
        this.method = method2;
    }

    public GeneratePresignedUrlRequest withMethod(HttpMethod method2) {
        setMethod(method2);
        return this;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public GeneratePresignedUrlRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public GeneratePresignedUrlRequest withKey(String key2) {
        setKey(key2);
        return this;
    }

    public Date getExpiration() {
        return this.expiration;
    }

    public void setExpiration(Date expiration2) {
        this.expiration = expiration2;
    }

    public GeneratePresignedUrlRequest withExpiration(Date expiration2) {
        setExpiration(expiration2);
        return this;
    }

    public void addRequestParameter(String key2, String value) {
        this.requestParameters.put(key2, value);
    }

    public Map<String, String> getRequestParameters() {
        return this.requestParameters;
    }

    public ResponseHeaderOverrides getResponseHeaders() {
        return this.responseHeaders;
    }

    public void setResponseHeaders(ResponseHeaderOverrides responseHeaders2) {
        this.responseHeaders = responseHeaders2;
    }

    public GeneratePresignedUrlRequest withResponseHeaders(ResponseHeaderOverrides responseHeaders2) {
        setResponseHeaders(responseHeaders2);
        return this;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType2) {
        this.contentType = contentType2;
    }

    public GeneratePresignedUrlRequest withContentType(String contentType2) {
        setContentType(contentType2);
        return this;
    }

    public String getContentMd5() {
        return this.contentMd5;
    }

    public void setContentMd5(String contentMd52) {
        this.contentMd5 = contentMd52;
    }

    public GeneratePresignedUrlRequest withContentMd5(String contentMd52) {
        this.contentMd5 = contentMd52;
        return this;
    }

    public SSECustomerKey getSSECustomerKey() {
        return this.sseCustomerKey;
    }

    public void setSSECustomerKey(SSECustomerKey sseKey) {
        this.sseCustomerKey = sseKey;
    }

    public GeneratePresignedUrlRequest withSSECustomerKey(SSECustomerKey sseKey) {
        setSSECustomerKey(sseKey);
        return this;
    }
}
