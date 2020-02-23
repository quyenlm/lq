package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonServiceException;

public class ResourceNotFoundException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
