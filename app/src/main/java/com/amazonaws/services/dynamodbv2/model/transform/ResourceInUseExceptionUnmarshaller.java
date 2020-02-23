package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ResourceInUseExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ResourceInUseExceptionUnmarshaller() {
        super(ResourceInUseException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("ResourceInUseException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        ResourceInUseException e = (ResourceInUseException) super.unmarshall(error);
        e.setErrorCode("ResourceInUseException");
        return e;
    }
}
