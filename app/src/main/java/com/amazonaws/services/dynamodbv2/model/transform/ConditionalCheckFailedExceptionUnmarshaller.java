package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ConditionalCheckFailedExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ConditionalCheckFailedExceptionUnmarshaller() {
        super(ConditionalCheckFailedException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("ConditionalCheckFailedException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) throws Exception {
        ConditionalCheckFailedException e = (ConditionalCheckFailedException) super.unmarshall(error);
        e.setErrorCode("ConditionalCheckFailedException");
        return e;
    }
}
