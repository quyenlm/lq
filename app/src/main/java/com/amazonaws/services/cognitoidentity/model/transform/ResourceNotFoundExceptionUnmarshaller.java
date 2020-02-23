package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.cognitoidentity.model.ResourceNotFoundException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ResourceNotFoundExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ResourceNotFoundExceptionUnmarshaller() {
        super(ResourceNotFoundException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("ResourceNotFoundException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        ResourceNotFoundException e = (ResourceNotFoundException) super.unmarshall(error);
        e.setErrorCode("ResourceNotFoundException");
        return e;
    }
}
