package com.tencent.qqgamemi.api;

public class TimeStamp {
    public long endTime;
    public TimeStampPriority priority;
    public long startTime;
    public String title;

    public TimeStamp(long startTime2, long endTime2) {
        this.startTime = startTime2;
        this.endTime = endTime2;
        this.priority = TimeStampPriority.None;
        this.title = null;
    }

    public TimeStamp(String title2, TimeStampPriority priority2, long startTime2, long endTime2) {
        this.startTime = startTime2;
        this.endTime = endTime2;
        this.priority = priority2;
        this.title = title2;
    }
}
