package com.garena.pay.android.exception;

public class InvalidOperationException extends RuntimeException {
    private String message;

    public InvalidOperationException(String s) {
        super(s);
        this.message = s;
    }

    public String getMessage() {
        return this.message;
    }
}
