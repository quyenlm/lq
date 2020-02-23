package com.tencent.component.utils.clock;

import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 6)
public abstract class Clock {
    private int clockId = -1;
    private long interval = 10000;
    private OnClockListener listener;

    public abstract void cancel();

    protected Clock(int clockId2, long interval2, OnClockListener listener2) {
        setInterval(interval2);
        setClockId(clockId2);
        setListener(listener2);
    }

    public long getInterval() {
        return this.interval;
    }

    public void setInterval(long interval2) {
        this.interval = interval2;
    }

    public int getClockId() {
        return this.clockId;
    }

    private void setClockId(int clockId2) {
        this.clockId = clockId2;
    }

    public OnClockListener getListener() {
        return this.listener;
    }

    private void setListener(OnClockListener listener2) {
        this.listener = listener2;
    }
}
