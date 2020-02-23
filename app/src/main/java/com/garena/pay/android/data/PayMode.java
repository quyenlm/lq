package com.garena.pay.android.data;

public enum PayMode {
    WEB_VIEW(1),
    NATIVE(2);
    
    private int value;

    private PayMode(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
