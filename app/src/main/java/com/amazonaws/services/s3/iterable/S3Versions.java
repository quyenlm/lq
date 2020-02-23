package com.amazonaws.services.s3.iterable;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;
import java.util.Iterator;

public class S3Versions implements Iterable<S3VersionSummary> {
    private Integer batchSize;
    private String bucketName;
    private String key;
    private String prefix;
    private AmazonS3 s3;

    private S3Versions(AmazonS3 s32, String bucketName2) {
        this.s3 = s32;
        this.bucketName = bucketName2;
    }

    public static S3Versions inBucket(AmazonS3 s32, String bucketName2) {
        return new S3Versions(s32, bucketName2);
    }

    public static S3Versions withPrefix(AmazonS3 s32, String bucketName2, String prefix2) {
        S3Versions versions = new S3Versions(s32, bucketName2);
        versions.prefix = prefix2;
        return versions;
    }

    public static S3Versions forKey(AmazonS3 s32, String bucketName2, String key2) {
        S3Versions versions = new S3Versions(s32, bucketName2);
        versions.key = key2;
        return versions;
    }

    public S3Versions withBatchSize(int batchSize2) {
        this.batchSize = Integer.valueOf(batchSize2);
        return this;
    }

    public Integer getBatchSize() {
        return this.batchSize;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getKey() {
        return this.key;
    }

    public AmazonS3 getS3() {
        return this.s3;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    private class VersionIterator implements Iterator<S3VersionSummary> {
        private Iterator<S3VersionSummary> currentIterator;
        private VersionListing currentListing;
        private S3VersionSummary nextSummary;

        private VersionIterator() {
            this.currentListing = null;
            this.currentIterator = null;
            this.nextSummary = null;
        }

        public boolean hasNext() {
            prepareCurrentListing();
            return nextMatchingSummary() != null;
        }

        public S3VersionSummary next() {
            prepareCurrentListing();
            S3VersionSummary returnValue = nextMatchingSummary();
            this.nextSummary = null;
            return returnValue;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private S3VersionSummary nextMatchingSummary() {
            if (S3Versions.this.getKey() == null || (this.nextSummary != null && this.nextSummary.getKey().equals(S3Versions.this.getKey()))) {
                return this.nextSummary;
            }
            return null;
        }

        private void prepareCurrentListing() {
            while (true) {
                if (this.currentListing == null || (!this.currentIterator.hasNext() && this.currentListing.isTruncated())) {
                    if (this.currentListing == null) {
                        ListVersionsRequest req = new ListVersionsRequest();
                        req.setBucketName(S3Versions.this.getBucketName());
                        if (S3Versions.this.getKey() != null) {
                            req.setPrefix(S3Versions.this.getKey());
                        } else {
                            req.setPrefix(S3Versions.this.getPrefix());
                        }
                        req.setMaxResults(S3Versions.this.getBatchSize());
                        this.currentListing = S3Versions.this.getS3().listVersions(req);
                    } else {
                        this.currentListing = S3Versions.this.getS3().listNextBatchOfVersions(this.currentListing);
                    }
                    this.currentIterator = this.currentListing.getVersionSummaries().iterator();
                }
            }
            if (this.nextSummary == null && this.currentIterator.hasNext()) {
                this.nextSummary = this.currentIterator.next();
            }
        }
    }

    public Iterator<S3VersionSummary> iterator() {
        return new VersionIterator();
    }
}
