package com.garena.pay.android.exception;

public class ValidationException extends GGException {
    public ValidationException() {
    }

    public ValidationException(String detailMessage) {
        super(detailMessage);
    }
}
