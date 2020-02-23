package com.amazonaws.services.s3.model;

public class PartETag {
    private String eTag;
    private int partNumber;

    public PartETag(int partNumber2, String eTag2) {
        this.partNumber = partNumber2;
        this.eTag = eTag2;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(int partNumber2) {
        this.partNumber = partNumber2;
    }

    public PartETag withPartNumber(int partNumber2) {
        this.partNumber = partNumber2;
        return this;
    }

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String eTag2) {
        this.eTag = eTag2;
    }

    public PartETag withETag(String eTag2) {
        this.eTag = eTag2;
        return this;
    }
}
