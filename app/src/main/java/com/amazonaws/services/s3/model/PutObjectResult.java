package com.amazonaws.services.s3.model;

import com.amazonaws.services.s3.internal.ObjectExpirationResult;
import com.amazonaws.services.s3.internal.SSEResultBase;
import java.util.Date;

public class PutObjectResult extends SSEResultBase implements ObjectExpirationResult {
    private String contentMd5;
    private String eTag;
    private Date expirationTime;
    private String expirationTimeRuleId;
    private String versionId;

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId2) {
        this.versionId = versionId2;
    }

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String eTag2) {
        this.eTag = eTag2;
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

    public void setContentMd5(String contentMd52) {
        this.contentMd5 = contentMd52;
    }

    public String getContentMd5() {
        return this.contentMd5;
    }
}
