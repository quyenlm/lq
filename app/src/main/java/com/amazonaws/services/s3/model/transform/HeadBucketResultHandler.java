package com.amazonaws.services.s3.model.transform;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.AbstractS3ResponseHandler;
import com.amazonaws.services.s3.model.HeadBucketResult;

public class HeadBucketResultHandler extends AbstractS3ResponseHandler<HeadBucketResult> {
    public AmazonWebServiceResponse<HeadBucketResult> handle(HttpResponse response) throws Exception {
        AmazonWebServiceResponse<HeadBucketResult> awsResponse = new AmazonWebServiceResponse<>();
        HeadBucketResult result = new HeadBucketResult();
        result.setBucketRegion(response.getHeaders().get(Headers.S3_BUCKET_REGION));
        awsResponse.setResult(result);
        return awsResponse;
    }
}
