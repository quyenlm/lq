package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.util.ArrayList;
import java.util.List;

public class DeleteObjectsRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private final List<KeyVersion> keys = new ArrayList();
    private MultiFactorAuthentication mfa;
    private boolean quiet;

    public DeleteObjectsRequest(String bucketName2) {
        setBucketName(bucketName2);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public DeleteObjectsRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public MultiFactorAuthentication getMfa() {
        return this.mfa;
    }

    public void setMfa(MultiFactorAuthentication mfa2) {
        this.mfa = mfa2;
    }

    public DeleteObjectsRequest withMfa(MultiFactorAuthentication mfa2) {
        setMfa(mfa2);
        return this;
    }

    public void setQuiet(boolean quiet2) {
        this.quiet = quiet2;
    }

    public boolean getQuiet() {
        return this.quiet;
    }

    public DeleteObjectsRequest withQuiet(boolean quiet2) {
        setQuiet(quiet2);
        return this;
    }

    public void setKeys(List<KeyVersion> keys2) {
        this.keys.clear();
        this.keys.addAll(keys2);
    }

    public DeleteObjectsRequest withKeys(List<KeyVersion> keys2) {
        setKeys(keys2);
        return this;
    }

    public List<KeyVersion> getKeys() {
        return this.keys;
    }

    public DeleteObjectsRequest withKeys(String... keys2) {
        List<KeyVersion> keyVersions = new ArrayList<>(keys2.length);
        for (String key : keys2) {
            keyVersions.add(new KeyVersion(key));
        }
        setKeys(keyVersions);
        return this;
    }

    public static class KeyVersion {
        private final String key;
        private final String version;

        public KeyVersion(String key2) {
            this(key2, (String) null);
        }

        public KeyVersion(String key2, String version2) {
            this.key = key2;
            this.version = version2;
        }

        public String getKey() {
            return this.key;
        }

        public String getVersion() {
            return this.version;
        }
    }
}
