package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class S3MetadataResponseHandler extends AbstractS3ResponseHandler<ObjectMetadata> {
    public AmazonWebServiceResponse<ObjectMetadata> handle(HttpResponse response) throws Exception {
        ObjectMetadata metadata = new ObjectMetadata();
        populateObjectMetadata(response, metadata);
        AmazonWebServiceResponse<ObjectMetadata> awsResponse = parseResponseMetadata(response);
        awsResponse.setResult(metadata);
        return awsResponse;
    }
}
