package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class S3ObjectResponseHandler extends AbstractS3ResponseHandler<S3Object> {
    public AmazonWebServiceResponse<S3Object> handle(HttpResponse response) throws Exception {
        S3Object object = new S3Object();
        AmazonWebServiceResponse<S3Object> awsResponse = parseResponseMetadata(response);
        if (response.getHeaders().get(Headers.REDIRECT_LOCATION) != null) {
            object.setRedirectLocation(response.getHeaders().get(Headers.REDIRECT_LOCATION));
        }
        if (response.getHeaders().get(Headers.REQUESTER_CHARGED_HEADER) != null) {
            object.setRequesterCharged(true);
        }
        populateObjectMetadata(response, object.getObjectMetadata());
        object.setObjectContent(new S3ObjectInputStream(response.getContent()));
        awsResponse.setResult(object);
        return awsResponse;
    }

    public boolean needsConnectionLeftOpen() {
        return true;
    }
}
