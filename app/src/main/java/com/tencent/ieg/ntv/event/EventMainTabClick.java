package com.tencent.ieg.ntv.event;

public class EventMainTabClick extends Event {
    public int mIndex;

    public EventMainTabClick(int index) {
        this.mIndex = index;
    }
}
