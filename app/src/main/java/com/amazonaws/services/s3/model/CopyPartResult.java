package com.amazonaws.services.s3.model;

import com.amazonaws.services.s3.internal.SSEResultBase;
import java.util.Date;

public class CopyPartResult extends SSEResultBase {
    private String etag;
    private Date lastModifiedDate;
    private int partNumber;
    private String versionId;

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(int partNumber2) {
        this.partNumber = partNumber2;
    }

    public String getETag() {
        return this.etag;
    }

    public void setETag(String etag2) {
        this.etag = etag2;
    }

    public PartETag getPartETag() {
        return new PartETag(this.partNumber, this.etag);
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
}
