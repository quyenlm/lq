package com.amazonaws.services.s3.model;

import com.amazonaws.services.s3.internal.ObjectExpirationResult;
import com.amazonaws.services.s3.internal.SSEResultBase;
import java.util.Date;

public class CopyObjectResult extends SSEResultBase implements ObjectExpirationResult {
    private String etag;
    private Date expirationTime;
    private String expirationTimeRuleId;
    private Date lastModifiedDate;
    private String versionId;

    public String getETag() {
        return this.etag;
    }

    public void setETag(String etag2) {
        this.etag = etag2;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate2) {
        this.lastModifiedDate = lastModifiedDate2;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId2) {
        this.versionId = versionId2;
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
}
