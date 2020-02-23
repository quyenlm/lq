package com.beetalk.sdk;

public enum SessionStatus {
    CREATED(1),
    OPENING(2),
    TOKEN_AVAILABLE(3),
    CLOSED(4),
    CLOSED_WITH_ERROR(5),
    INSPECTION_WITH_ERROR(6),
    CLOSED_WITH_BIND_FAIL(7);
    
    private int value;

    private SessionStatus(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
