package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.dynamodbv2.model.LimitExceededException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class LimitExceededExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public LimitExceededExceptionUnmarshaller() {
        super(LimitExceededException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("LimitExceededException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        LimitExceededException e = (LimitExceededException) super.unmarshall(error);
        e.setErrorCode("LimitExceededException");
        return e;
    }
}
