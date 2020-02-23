package com.amazonaws.services.s3.model;

import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.ObjectExpirationResult;
import com.amazonaws.services.s3.internal.ObjectRestoreResult;
import com.amazonaws.services.s3.internal.ServerSideEncryptionResult;
import com.appsflyer.share.Constants;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ObjectMetadata implements ObjectExpirationResult, ObjectRestoreResult, ServerSideEncryptionResult, Cloneable {
    public static final String AES_256_SERVER_SIDE_ENCRYPTION = "AES256";
    public static final String KMS_SERVER_SIDE_ENCRYPTION = "aws:kms";
    private Date expirationTime;
    private String expirationTimeRuleId;
    private Date httpExpiresDate;
    private Map<String, Object> metadata;
    private Boolean ongoingRestore;
    private Date restoreExpirationTime;
    private Map<String, String> userMetadata;

    public Map<String, String> getUserMetadata() {
        return this.userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata2) {
        this.userMetadata = userMetadata2;
    }

    public void setHeader(String key, Object value) {
        this.metadata.put(key, value);
    }

    public void addUserMetadata(String key, String value) {
        this.userMetadata.put(key, value);
    }

    public Map<String, Object> getRawMetadata() {
        return Collections.unmodifiableMap(new HashMap(this.metadata));
    }

    public Object getRawMetadataValue(String key) {
        return this.metadata.get(key);
    }

    public Date getLastModified() {
        return (Date) this.metadata.get("Last-Modified");
    }

    public void setLastModified(Date lastModified) {
        this.metadata.put("Last-Modified", lastModified);
    }

    public long getContentLength() {
        Long contentLength = (Long) this.metadata.get("Content-Length");
        if (contentLength == null) {
            return 0;
        }
        return contentLength.longValue();
    }

    public long getInstanceLength() {
        int pos;
        String contentRange = (String) this.metadata.get("Content-Range");
        if (contentRange == null || (pos = contentRange.lastIndexOf(Constants.URL_PATH_DELIMITER)) < 0) {
            return getContentLength();
        }
        return Long.parseLong(contentRange.substring(pos + 1));
    }

    public void setContentLength(long contentLength) {
        this.metadata.put("Content-Length", Long.valueOf(contentLength));
    }

    public String getContentType() {
        return (String) this.metadata.get("Content-Type");
    }

    public void setContentType(String contentType) {
        this.metadata.put("Content-Type", contentType);
    }

    public String getContentEncoding() {
        return (String) this.metadata.get("Content-Encoding");
    }

    public void setContentEncoding(String encoding) {
        this.metadata.put("Content-Encoding", encoding);
    }

    public String getCacheControl() {
        return (String) this.metadata.get("Cache-Control");
    }

    public void setCacheControl(String cacheControl) {
        this.metadata.put("Cache-Control", cacheControl);
    }

    public void setContentMD5(String md5Base64) {
        if (md5Base64 == null) {
            this.metadata.remove(Headers.CONTENT_MD5);
        } else {
            this.metadata.put(Headers.CONTENT_MD5, md5Base64);
        }
    }

    public String getContentMD5() {
        return (String) this.metadata.get(Headers.CONTENT_MD5);
    }

    public void setContentDisposition(String disposition) {
        this.metadata.put("Content-Disposition", disposition);
    }

    public String getContentDisposition() {
        return (String) this.metadata.get("Content-Disposition");
    }

    public String getETag() {
        return (String) this.metadata.get(Headers.ETAG);
    }

    public String getVersionId() {
        return (String) this.metadata.get(Headers.S3_VERSION_ID);
    }

    public String getSSEAlgorithm() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION);
    }

    public String getSSEKMSKeyId() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION_KMS_KEY_ID);
    }

    @Deprecated
    public String getServerSideEncryption() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION);
    }

    public void setSSEAlgorithm(String algorithm) {
        this.metadata.put(Headers.SERVER_SIDE_ENCRYPTION, algorithm);
    }

    public void setSSEKMSKeyId(String kmsKeyId) {
        this.metadata.put(Headers.SERVER_SIDE_ENCRYPTION_KMS_KEY_ID, kmsKeyId);
    }

    @Deprecated
    public void setServerSideEncryption(String algorithm) {
        this.metadata.put(Headers.SERVER_SIDE_ENCRYPTION, algorithm);
    }

    public String getSSECustomerAlgorithm() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM);
    }

    public void setSSECustomerAlgorithm(String algorithm) {
        this.metadata.put(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM, algorithm);
    }

    public String getSSECustomerKeyMd5() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5);
    }

    public void setSSECustomerKeyMd5(String md5Digest) {
        this.metadata.put(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5, md5Digest);
    }

    public Date getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(Date expirationTime2) {
        this.expirationTime = expirationTime2;
    }

    public String getExpirationTimeRuleId() {
        return this.expirationTimeRuleId;
    }

    public void setExpirationTimeRuleId(String expirationTimeRuleId2) {
        this.expirationTimeRuleId = expirationTimeRuleId2;
    }

    public Date getRestoreExpirationTime() {
        return this.restoreExpirationTime;
    }

    public void setRestoreExpirationTime(Date restoreExpirationTime2) {
        this.restoreExpirationTime = restoreExpirationTime2;
    }

    public void setOngoingRestore(boolean ongoingRestore2) {
        this.ongoingRestore = Boolean.valueOf(ongoingRestore2);
    }

    public Boolean getOngoingRestore() {
        return this.ongoingRestore;
    }

    public void setHttpExpiresDate(Date httpExpiresDate2) {
        this.httpExpiresDate = httpExpiresDate2;
    }

    public Date getHttpExpiresDate() {
        return this.httpExpiresDate;
    }

    public String getUserMetaDataOf(String key) {
        if (this.userMetadata == null) {
            return null;
        }
        return this.userMetadata.get(key);
    }

    public ObjectMetadata() {
        this.userMetadata = new HashMap();
        this.metadata = new HashMap();
    }

    private ObjectMetadata(ObjectMetadata from) {
        HashMap hashMap = null;
        this.userMetadata = from.userMetadata == null ? null : new HashMap(from.userMetadata);
        this.metadata = from.metadata != null ? new HashMap(from.metadata) : hashMap;
        this.expirationTime = from.expirationTime;
        this.expirationTimeRuleId = from.expirationTimeRuleId;
        this.httpExpiresDate = from.httpExpiresDate;
        this.ongoingRestore = from.ongoingRestore;
        this.restoreExpirationTime = from.restoreExpirationTime;
    }

    public ObjectMetadata clone() {
        return new ObjectMetadata(this);
    }
}
