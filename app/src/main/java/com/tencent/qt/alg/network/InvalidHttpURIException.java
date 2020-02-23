package com.tencent.qt.alg.network;

public class InvalidHttpURIException extends IllegalArgumentException {
    private static final long serialVersionUID = -5766058678007111790L;

    public InvalidHttpURIException() {
    }

    public InvalidHttpURIException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidHttpURIException(Throwable cause) {
        super(cause);
    }

    public InvalidHttpURIException(String message, Throwable cause) {
        super(message, cause);
    }
}
