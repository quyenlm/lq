package com.amazonaws.services.s3.model;

import com.amazonaws.services.s3.internal.SSEResultBase;

public class UploadPartResult extends SSEResultBase {
    private String eTag;
    private int partNumber;

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(int partNumber2) {
        this.partNumber = partNumber2;
    }

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String eTag2) {
        this.eTag = eTag2;
    }

    public PartETag getPartETag() {
        return new PartETag(this.partNumber, this.eTag);
    }
}
