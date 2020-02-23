package com.tencent.kgvmp.a;

public enum e {
    FREQUENCY_SIGNAL(1),
    DEVICE_TEMP(2),
    FPS_COUNT_TIME(3),
    FREQUENCY_LEVEL(4);
    
    private int key;

    private e(int i) {
        this.key = i;
    }

    public int getKey() {
        return this.key;
    }

    public String getKeyStr() {
        return String.valueOf(this.key);
    }
}
