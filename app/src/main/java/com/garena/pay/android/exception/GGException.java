package com.garena.pay.android.exception;

public class GGException extends Exception {
    private int errorCode;
    private String errorType;

    public GGException() {
        this.errorCode = 1;
        this.errorType = "A Runtime Exception has occurred";
    }

    public GGException(String detailMessage) {
        super(detailMessage);
        this.errorType = detailMessage;
        this.errorCode = 2;
    }

    public String getMessage() {
        return this.errorType;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
