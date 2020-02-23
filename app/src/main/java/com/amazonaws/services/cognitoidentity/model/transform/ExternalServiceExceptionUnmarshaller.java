package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.cognitoidentity.model.ExternalServiceException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ExternalServiceExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ExternalServiceExceptionUnmarshaller() {
        super(ExternalServiceException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("ExternalServiceException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        ExternalServiceException e = (ExternalServiceException) super.unmarshall(error);
        e.setErrorCode("ExternalServiceException");
        return e;
    }
}
