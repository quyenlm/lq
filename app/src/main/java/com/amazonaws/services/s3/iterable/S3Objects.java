package com.amazonaws.services.s3.iterable;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.Iterator;

public class S3Objects implements Iterable<S3ObjectSummary> {
    private Integer batchSize = null;
    private String bucketName;
    private String prefix = null;
    private AmazonS3 s3;

    private S3Objects(AmazonS3 s32, String bucketName2) {
        this.s3 = s32;
        this.bucketName = bucketName2;
    }

    public static S3Objects inBucket(AmazonS3 s32, String bucketName2) {
        return new S3Objects(s32, bucketName2);
    }

    public static S3Objects withPrefix(AmazonS3 s32, String bucketName2, String prefix2) {
        S3Objects objects = new S3Objects(s32, bucketName2);
        objects.prefix = prefix2;
        return objects;
    }

    public S3Objects withBatchSize(int batchSize2) {
        this.batchSize = Integer.valueOf(batchSize2);
        return this;
    }

    public Integer getBatchSize() {
        return this.batchSize;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public AmazonS3 getS3() {
        return this.s3;
    }

    private class S3ObjectIterator implements Iterator<S3ObjectSummary> {
        private Iterator<S3ObjectSummary> currentIterator;
        private ObjectListing currentListing;

        private S3ObjectIterator() {
            this.currentListing = null;
            this.currentIterator = null;
        }

        public boolean hasNext() {
            prepareCurrentListing();
            return this.currentIterator.hasNext();
        }

        public S3ObjectSummary next() {
            prepareCurrentListing();
            return this.currentIterator.next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void prepareCurrentListing() {
            while (true) {
                if (this.currentListing == null || (!this.currentIterator.hasNext() && this.currentListing.isTruncated())) {
                    if (this.currentListing == null) {
                        ListObjectsRequest req = new ListObjectsRequest();
                        req.setBucketName(S3Objects.this.getBucketName());
                        req.setPrefix(S3Objects.this.getPrefix());
                        req.setMaxKeys(S3Objects.this.getBatchSize());
                        this.currentListing = S3Objects.this.getS3().listObjects(req);
                    } else {
                        this.currentListing = S3Objects.this.getS3().listNextBatchOfObjects(this.currentListing);
                    }
                    this.currentIterator = this.currentListing.getObjectSummaries().iterator();
                } else {
                    return;
                }
            }
        }
    }

    public Iterator<S3ObjectSummary> iterator() {
        return new S3ObjectIterator();
    }
}
