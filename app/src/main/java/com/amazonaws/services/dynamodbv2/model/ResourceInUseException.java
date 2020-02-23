package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonServiceException;

public class ResourceInUseException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ResourceInUseException(String message) {
        super(message);
    }
}
