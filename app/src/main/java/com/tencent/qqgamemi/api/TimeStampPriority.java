package com.tencent.qqgamemi.api;

public enum TimeStampPriority {
    None(0),
    Level_1(1),
    Level_2(2),
    Level_3(3),
    Level_4(4),
    Level_5(5);
    
    private int code;

    private TimeStampPriority(int code2) {
        this.code = code2;
    }

    public int getCode() {
        return this.code;
    }
}
