package com.amazonaws.auth.policy.resources;

import com.amazonaws.auth.policy.Resource;

public class S3BucketResource extends Resource {
    public S3BucketResource(String bucketName) {
        super("arn:aws:s3:::" + bucketName);
    }
}
