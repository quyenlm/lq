package com.subao.common;

/* compiled from: SwitchState */
public enum h {
    UNKNOWN(-1),
    OFF(0),
    ON(1);
    
    private final int d;

    private h(int i) {
        this.d = i;
    }

    public int a() {
        return this.d;
    }
}
