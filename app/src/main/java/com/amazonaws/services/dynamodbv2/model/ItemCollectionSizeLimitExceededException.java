package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonServiceException;

public class ItemCollectionSizeLimitExceededException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ItemCollectionSizeLimitExceededException(String message) {
        super(message);
    }
}
