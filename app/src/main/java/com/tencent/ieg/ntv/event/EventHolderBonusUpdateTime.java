package com.tencent.ieg.ntv.event;

public class EventHolderBonusUpdateTime extends Event {
    public boolean openBox;
    public String timeText;

    public EventHolderBonusUpdateTime(String text, boolean open) {
        this.timeText = text;
        this.openBox = open;
    }
}
