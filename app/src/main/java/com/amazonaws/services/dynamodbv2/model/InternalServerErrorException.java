package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonServiceException;

public class InternalServerErrorException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public InternalServerErrorException(String message) {
        super(message);
    }
}
