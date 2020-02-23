package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonServiceException;

public class ConditionalCheckFailedException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ConditionalCheckFailedException(String message) {
        super(message);
    }
}
