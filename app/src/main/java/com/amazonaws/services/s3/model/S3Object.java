package com.amazonaws.services.s3.model;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class S3Object implements Closeable {
    private String bucketName = null;
    private boolean isRequesterCharged;
    private String key = null;
    private ObjectMetadata metadata = new ObjectMetadata();
    private S3ObjectInputStream objectContent;
    private String redirectLocation;

    public ObjectMetadata getObjectMetadata() {
        return this.metadata;
    }

    public void setObjectMetadata(ObjectMetadata metadata2) {
        this.metadata = metadata2;
    }

    public S3ObjectInputStream getObjectContent() {
        return this.objectContent;
    }

    public void setObjectContent(S3ObjectInputStream objectContent2) {
        this.objectContent = objectContent2;
    }

    public void setObjectContent(InputStream objectContent2) {
        setObjectContent(new S3ObjectInputStream(objectContent2, this.objectContent != null ? this.objectContent.getHttpRequest() : null));
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public String getRedirectLocation() {
        return this.redirectLocation;
    }

    public void setRedirectLocation(String redirectLocation2) {
        this.redirectLocation = redirectLocation2;
    }

    public String toString() {
        return "S3Object [key=" + getKey() + ",bucket=" + (this.bucketName == null ? "<Unknown>" : this.bucketName) + "]";
    }

    public void close() throws IOException {
        if (getObjectContent() != null) {
            getObjectContent().close();
        }
    }

    public boolean isRequesterCharged() {
        return this.isRequesterCharged;
    }

    public void setRequesterCharged(boolean isRequesterCharged2) {
        this.isRequesterCharged = isRequesterCharged2;
    }
}
