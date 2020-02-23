package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonServiceException;

public class ProvisionedThroughputExceededException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ProvisionedThroughputExceededException(String message) {
        super(message);
    }
}
