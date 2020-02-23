package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class ResponseHeaderOverrides extends AmazonWebServiceRequest {
    private static final String[] PARAMETER_ORDER = {RESPONSE_HEADER_CACHE_CONTROL, RESPONSE_HEADER_CONTENT_DISPOSITION, RESPONSE_HEADER_CONTENT_ENCODING, RESPONSE_HEADER_CONTENT_LANGUAGE, RESPONSE_HEADER_CONTENT_TYPE, RESPONSE_HEADER_EXPIRES};
    public static final String RESPONSE_HEADER_CACHE_CONTROL = "response-cache-control";
    public static final String RESPONSE_HEADER_CONTENT_DISPOSITION = "response-content-disposition";
    public static final String RESPONSE_HEADER_CONTENT_ENCODING = "response-content-encoding";
    public static final String RESPONSE_HEADER_CONTENT_LANGUAGE = "response-content-language";
    public static final String RESPONSE_HEADER_CONTENT_TYPE = "response-content-type";
    public static final String RESPONSE_HEADER_EXPIRES = "response-expires";
    private String cacheControl;
    private String contentDisposition;
    private String contentEncoding;
    private String contentLanguage;
    private String contentType;
    private String expires;

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType2) {
        this.contentType = contentType2;
    }

    public ResponseHeaderOverrides withContentType(String contentType2) {
        setContentType(contentType2);
        return this;
    }

    public String getContentLanguage() {
        return this.contentLanguage;
    }

    public void setContentLanguage(String contentLanguage2) {
        this.contentLanguage = contentLanguage2;
    }

    public ResponseHeaderOverrides withContentLanguage(String contentLanguage2) {
        setContentLanguage(contentLanguage2);
        return this;
    }

    public String getExpires() {
        return this.expires;
    }

    public void setExpires(String expires2) {
        this.expires = expires2;
    }

    public ResponseHeaderOverrides withExpires(String expires2) {
        setExpires(expires2);
        return this;
    }

    public String getCacheControl() {
        return this.cacheControl;
    }

    public void setCacheControl(String cacheControl2) {
        this.cacheControl = cacheControl2;
    }

    public ResponseHeaderOverrides withCacheControl(String cacheControl2) {
        setCacheControl(cacheControl2);
        return this;
    }

    public String getContentDisposition() {
        return this.contentDisposition;
    }

    public void setContentDisposition(String contentDisposition2) {
        this.contentDisposition = contentDisposition2;
    }

    public ResponseHeaderOverrides withContentDisposition(String contentDisposition2) {
        setContentDisposition(contentDisposition2);
        return this;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public void setContentEncoding(String contentEncoding2) {
        this.contentEncoding = contentEncoding2;
    }

    public ResponseHeaderOverrides withContentEncoding(String contentEncoding2) {
        setContentEncoding(contentEncoding2);
        return this;
    }
}
