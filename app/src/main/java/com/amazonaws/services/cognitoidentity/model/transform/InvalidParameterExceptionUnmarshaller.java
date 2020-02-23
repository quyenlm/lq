package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.cognitoidentity.model.InvalidParameterException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidParameterExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidParameterExceptionUnmarshaller() {
        super(InvalidParameterException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("InvalidParameterException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        InvalidParameterException e = (InvalidParameterException) super.unmarshall(error);
        e.setErrorCode("InvalidParameterException");
        return e;
    }
}
