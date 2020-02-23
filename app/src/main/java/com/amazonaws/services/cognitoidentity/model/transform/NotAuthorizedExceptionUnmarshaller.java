package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.cognitoidentity.model.NotAuthorizedException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class NotAuthorizedExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public NotAuthorizedExceptionUnmarshaller() {
        super(NotAuthorizedException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("NotAuthorizedException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        NotAuthorizedException e = (NotAuthorizedException) super.unmarshall(error);
        e.setErrorCode("NotAuthorizedException");
        return e;
    }
}
