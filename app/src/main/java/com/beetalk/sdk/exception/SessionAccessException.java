package com.beetalk.sdk.exception;

import com.beetalk.sdk.helper.BBLogger;

public class SessionAccessException extends RuntimeException implements LoggableException {
    public SessionAccessException(String s) {
        super(s);
        BBLogger.e(this);
    }

    public boolean mustLog() {
        return true;
    }
}
