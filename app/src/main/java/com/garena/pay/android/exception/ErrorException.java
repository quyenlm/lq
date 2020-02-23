package com.garena.pay.android.exception;

public class ErrorException extends InvalidOperationException {
    private Integer errorCode;

    public ErrorException(String s, Integer errorCode2) {
        super(errorCode2 + ":" + s);
        this.errorCode = errorCode2;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }
}
