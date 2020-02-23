package com.tencent.qqgamemi.api;

public enum RecordingStartStatus {
    Fail(0),
    Starting(1),
    Recording(2),
    Success(3);
    
    private int value;

    private RecordingStartStatus(int value2) {
        this.value = 0;
        this.value = value2;
    }

    public static RecordingStartStatus valueOf(int value2) {
        switch (value2) {
            case 0:
                return Fail;
            case 1:
                return Starting;
            case 2:
                return Recording;
            case 3:
                return Success;
            default:
                return null;
        }
    }

    public int value() {
        return this.value;
    }
}
