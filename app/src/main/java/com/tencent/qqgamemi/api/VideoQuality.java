package com.tencent.qqgamemi.api;

public enum VideoQuality {
    High(0),
    Low(1);
    
    private int value;

    private VideoQuality(int value2) {
        this.value = value2;
    }

    public int intValue() {
        return this.value;
    }
}
