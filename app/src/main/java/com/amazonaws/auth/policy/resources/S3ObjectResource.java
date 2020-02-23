package com.amazonaws.auth.policy.resources;

import com.amazonaws.auth.policy.Resource;
import com.appsflyer.share.Constants;

public class S3ObjectResource extends Resource {
    public S3ObjectResource(String bucketName, String keyPattern) {
        super("arn:aws:s3:::" + bucketName + Constants.URL_PATH_DELIMITER + keyPattern);
    }
}
