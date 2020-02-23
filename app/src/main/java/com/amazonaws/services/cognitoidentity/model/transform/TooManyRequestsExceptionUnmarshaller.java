package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.cognitoidentity.model.TooManyRequestsException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class TooManyRequestsExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public TooManyRequestsExceptionUnmarshaller() {
        super(TooManyRequestsException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("TooManyRequestsException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        TooManyRequestsException e = (TooManyRequestsException) super.unmarshall(error);
        e.setErrorCode("TooManyRequestsException");
        return e;
    }
}
